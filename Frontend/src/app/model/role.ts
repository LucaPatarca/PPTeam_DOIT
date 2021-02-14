import { Skill } from "./skill";

export class Role {
    constructor(
        public userMail: string,
        public skill: Skill,
        public asExpert: boolean,
    ){}
}
