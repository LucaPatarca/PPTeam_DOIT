export class Organization {
    public id:string = "";
    public membersMails: Array<string> = new Array();
    public name:string;
    public description:string;
    public creatorMail:string;

    constructor(
        private nameOrg: string,
        private descriptionOrg: string,
        private creatorMailOrg: string,
    ){
        this.id = "";
        this.creatorMail = creatorMailOrg;
        this.name = nameOrg;
        this.description = descriptionOrg;
    }
}
