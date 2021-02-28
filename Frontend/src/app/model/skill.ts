export class Skill {
    name:string;
    expertInOrganization:string[]= new Array();
    level:number;
    
    constructor(){
        this.name = "";
        this.expertInOrganization = new Array();
        this.level = 1;
    }
}
