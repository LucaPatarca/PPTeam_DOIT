import { Skill } from "./skill";

export class User {
    mail:string;
    name:string;
    skills:Skill[];   
    secret:string;

    constructor(){
        this.mail = "";
        this.name = "guest";
        this.skills = new Array();
        this.secret="";
    }
}
