import axios from "axios";
import {jwtDecode} from "jwt-decode";
import {UserRole} from "../../model/user/AppUserModel";
import {appRoutes} from "../../../routes";


const API_URL = window.config.baseApiUrl;


const login = async (username: string | undefined, password: string | undefined) => {

    return axios
        .post(API_URL + "/auth/login", {
            username,
            password,
        })
        .then((response) => {
            if (response.data.token) {
                localStorage.setItem("token", JSON.stringify(response.data));
            }
        });

};

const isUserInRole = (role: string): boolean => {
    const user = getCurrentUser();
    if (!user)
        return false;
    const decodedJwt = jwtDecode<any>(user);
    return decodedJwt.scopes.findIndex((s: any) => s === role) !== -1;
}
const isAuthenticated = (): boolean => {
    return !!localStorage.getItem("token");
}
const isSystemAdminOrOwner = (): boolean => {
    return isUserInRole(UserRole.SystemAdmin) || isUserInRole(UserRole.SystemOwner);

}
const isCustomer = (): boolean => {
    return isUserInRole(UserRole.Customer);
}
const removeToken = () => {
    return new Promise<void>((resolve) => {
        localStorage.removeItem("token");
        resolve();
    });
};
const logout = async () => {
    try {
        await removeToken();
        window.location.href = appRoutes.auth.login;
    } catch (error) {
        console.error(error);
    }

};

const getCurrentUserName = (): string => {
    const user = getCurrentUser();
    if (!user)
        return "";
    const decodedJwt = jwtDecode<any>(user);
    return decodedJwt.userId;
}

const getCurrentUser = () => {
    const tokenJson = localStorage.getItem("token");
    if (!tokenJson)
        return null;
    return JSON.parse(tokenJson).token;
};
const authService = {
    login,
    logout,
    getCurrentUser,
    isAuthenticated,
    isUserInRole,
    isSystemAdminOrOwner,
    isCustomer,
    getCurrentUserName
};
export default authService;