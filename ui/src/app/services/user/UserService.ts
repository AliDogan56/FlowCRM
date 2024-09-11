import axios, {AxiosResponse} from "axios";
import {authHeader} from "../../helper/requestHeader/RequestHeader";
import {
    CustomerUserModel,
    CustomerUserSearchDTO,
    CustomerUserSearchResponseDTO
} from "../../model/user/CustomerUserModel";


const API_URL = window.config.baseApiUrl;

const search = (userType: string, appUserSearchDTO: CustomerUserSearchDTO | undefined): Promise<CustomerUserSearchResponseDTO> => {
    return axios
        .get<CustomerUserSearchDTO, AxiosResponse<CustomerUserSearchResponseDTO>>(`${API_URL}/${userType}/search`, {
            params: appUserSearchDTO,
            headers: authHeader()
        })
        .then((response) => {
            return response.data;
        });
};

const removeUser = (userType: string, userId: number) => {
    return axios.delete<AxiosResponse>(`${API_URL}/${userType}/${userId}`, {headers: authHeader()}).then((response) => {
        return response.data;
    });
}

const getUser = (userType: string, userId?: number) => {
    return axios.get<any, AxiosResponse<CustomerUserModel>>(`${API_URL}/${userType}/${userId}`, {
        headers: authHeader()
    }).then((response) => {
        return response.data;
    });
}

const createUser = (userType: string, customerUserModel: CustomerUserModel | undefined): Promise<CustomerUserModel> => {
    return axios
        .post<CustomerUserModel>(`${API_URL}/${userType}`, customerUserModel, {headers: authHeader()})
        .then((response: AxiosResponse<CustomerUserModel>) => {
            return response.data;
        });
};

const updateUser = (userType: string, customerUserModel: CustomerUserModel | undefined): Promise<CustomerUserModel> => {
    return axios
        .post<CustomerUserModel>(`${API_URL}/${userType}/update`, customerUserModel, {headers: authHeader()})
        .then((response: AxiosResponse<CustomerUserModel>) => {
            return response.data;
        });
};


const userService = {
    search,
    removeUser,
    getUser,
    createUser,
    updateUser
};
export default userService;