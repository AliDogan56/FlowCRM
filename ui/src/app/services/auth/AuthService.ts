import axios from "axios";
import {jwtDecode} from "jwt-decode";
import {UserRole} from "../../model/user/AppUserModel";


const API_URL = window.config.baseApiUrl;


const login = async (username: string, password: string) => {

    const response = await axios
        .post(API_URL + "/auth/login", {
            username,
            password,
        });
    if (response.data.token) {
        localStorage.setItem("token", JSON.stringify(response.data));
        window.location.href = "/";
    }
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
const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/auth/login";

};

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
    isCustomer
};
export default authService;