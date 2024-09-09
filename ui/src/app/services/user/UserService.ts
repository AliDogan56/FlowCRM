import axios, {AxiosResponse} from "axios";
import {authHeader} from "../../helper/requestHeader/RequestHeader";
import {
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


const userService = {
    search
};
export default userService;