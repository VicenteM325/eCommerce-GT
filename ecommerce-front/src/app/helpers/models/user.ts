import { Role } from "./role";

export interface User {
    userId:string;
    name:string;
    emailAddress:string;
    password:string;
    address:string;
    dpi:BigInteger;
    userStatus:string;
    role:Role
}
