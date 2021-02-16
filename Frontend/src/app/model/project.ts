import { newArray } from "@angular/compiler/src/util";
import { Role } from "./role";
import { Skill } from "./skill";

export class Project {
    public id:string;
    public closed:boolean;
    public team: Role[];
    public candidates: Role[];
    
    constructor(
    public title:string,
    public description:string,
    public organizationId:string,
    public creatorMail:string,
    public neededSkills:Skill[],
    ){
        this.id = "";
        this.closed = false;
        this.neededSkills = new Array();
        this.team = new Array();
        this.candidates = new Array();
        this.neededSkills = neededSkills;
    }
}
