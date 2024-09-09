import {AxiosHeaders} from "axios";

export function authHeader(): AxiosHeaders {
    // return authorization header with jwt token
    let user = localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token') ?? "") : "";
    if (user && user.token) {
        return new AxiosHeaders({Authorization: 'Bearer ' + user.token});
    } else {
        return new AxiosHeaders({});
    }
}
