import {AppUserModel, AppUserSearchDTO} from "./AppUserModel";
import {BaseSearchResponseDTO} from "../base/BaseSearchResponseDTO";

export interface CustomerUserModel extends AppUserModel {
    firstName?: string;
    lastName?: string;
    region?: string;
    email?: string;
}


export interface CustomerUserSearchDTO extends AppUserSearchDTO {
    firstName?: string;
    lastName?: string;
    region?: string;
    email?: string;
    createdAt?: string;
}

export interface CustomerUserSearchResponseDTO extends BaseSearchResponseDTO {
    data: CustomerUserModel[];
}