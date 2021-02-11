import { Organization } from "./organization";
import { Skill } from "./skill";

export class User {
    mail:string;
    name:string;
    skills:Skill[];   

    constructor(){
        this.mail = "";
        this.name = "guest";
        this.skills = new Array();
    }
}
