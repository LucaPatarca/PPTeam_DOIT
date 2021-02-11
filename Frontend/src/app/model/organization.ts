export class Organization {
    public id:string;
    public membersMails: Array<string>;

    constructor(
        public name: string,
        public description: string,
        public creatorMail: string,
    ){
        this.id = "";
        this.membersMails = new Array();
    }
}
