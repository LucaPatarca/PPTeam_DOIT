import { Role } from "./role";

export class UserSubmissionInformation {
    organizationId:string;
    organizationName:string;
    projectId:string ;
    projectName:String ;
    role:Role[];
    

    constructor(){
       this.organizationId="";
       this.organizationName="";
       this.projectId="";
       this.projectName="";
       this.role=new Array
    }
}
