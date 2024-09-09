import {BaseModel} from "../base/BaseModel";
import {BaseSearchDTO} from "../base/BaseSearchDTO";
import {SortOrder} from "../base/SortOrderDTO";

export enum UserRole {
    SystemOwner = 'SYSTEM_OWNER',
    SystemAdmin = 'SYSTEM_ADMIN',
    Customer = 'CUSTOMER'
}

export interface AppUserModel extends BaseModel {
    username?: string;
    password?: string;
    role?: UserRole;
}

export interface AppUserSearchDTO extends BaseSearchDTO {
    sortOrder?: SortOrder;
}