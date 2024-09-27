import axios, {AxiosResponse, AxiosError, InternalAxiosRequestConfig, AxiosRequestConfig} from 'axios';
import jwtDefaultConfig from './jwtDefaultConfig';

export interface JwtConfig {
    loginEndpoint: string;
    registerEndpoint: string;
    refreshEndpoint: string;
    logoutEndpoint: string;
    tokenType: string;
    storageTokenKeyName: string;
    storageRefreshTokenKeyName: string;
}

export default class JwtService {
    jwtConfig: JwtConfig = {...jwtDefaultConfig};
    private isAlreadyFetchingAccessToken = false;
    private subscribers: Array<(accessToken: string) => void> = [];

    constructor(jwtOverrideConfig: Partial<JwtConfig>) {
        this.jwtConfig = {...this.jwtConfig, ...jwtOverrideConfig};

        // Request Interceptor
        axios.interceptors.request.use(
            (config: InternalAxiosRequestConfig<any>) => {
                const accessToken = this.getToken();

                if (accessToken && config.headers) {
                    config.headers.set('Authorization', `${this.jwtConfig.tokenType} ${accessToken}`);
                }

                return config;
            },
            (error: AxiosError) => Promise.reject(error)
        );


        // Response Interceptor
        axios.interceptors.response.use(
            (response: AxiosResponse) => response,
            async (error: AxiosError) => {
                const {config, response} = error;
                const originalRequest = config;

                // Check if response exists and has a status of 401
                if (response && response.status === 401) {
                    if (!this.isAlreadyFetchingAccessToken) {
                        this.isAlreadyFetchingAccessToken = true;
                        try {
                            const refreshTokenResponse = await this.refreshToken();
                            this.isAlreadyFetchingAccessToken = false;

                            const {accessToken, refreshToken} = refreshTokenResponse.data;
                            this.setToken(accessToken);
                            this.setRefreshToken(refreshToken);

                            this.onAccessTokenFetched(accessToken);
                        } catch (err) {
                            this.isAlreadyFetchingAccessToken = false;
                            return Promise.reject(err);
                        }
                    }

                    return new Promise(resolve => {
                        this.addSubscriber((accessToken: string) => {
                            if (originalRequest && originalRequest.headers) {
                                originalRequest.headers.Authorization = `${this.jwtConfig.tokenType} ${accessToken}`;
                                // Ensure originalRequest is not undefined
                                resolve(axios(originalRequest as AxiosRequestConfig));
                            }
                        });
                    });
                }

                return Promise.reject(error);
            }
        );

    }

    private onAccessTokenFetched(accessToken: string) {
        this.subscribers = this.subscribers.filter(callback => callback(accessToken));
    }

    private addSubscriber(callback: (accessToken: string) => void) {
        this.subscribers.push(callback);
    }

    private getToken(): string | null {
        return localStorage.getItem(this.jwtConfig.storageTokenKeyName);
    }

    private getRefreshToken(): string | null {
        return localStorage.getItem(this.jwtConfig.storageRefreshTokenKeyName);
    }

    private setToken(value: string) {
        localStorage.setItem(this.jwtConfig.storageTokenKeyName, value);
    }

    private setRefreshToken(value: string) {
        localStorage.setItem(this.jwtConfig.storageRefreshTokenKeyName, value);
    }

    public login(...args: any[]): Promise<AxiosResponse> {
        return axios.post(this.jwtConfig.loginEndpoint, ...args);
    }

    public register(...args: any[]): Promise<AxiosResponse> {
        return axios.post(this.jwtConfig.registerEndpoint, ...args);
    }

    public refreshToken() {
        return axios.post(this.jwtConfig.refreshEndpoint, {
            refreshToken: this.getRefreshToken()
        });
    }
}
