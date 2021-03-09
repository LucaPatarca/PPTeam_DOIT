// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const serverUrl = "http://localhost:8080";
const projectApiUrl = serverUrl+"/api/projects/";
const organizationApiUrl = serverUrl+"/api/organizations/";
const userApiUrl = serverUrl+"/api/users/";

export const environment = {
  production: false,
  serverUrl: serverUrl,
  projectApiUrl: projectApiUrl,
  organizationApiUrl: organizationApiUrl,
  userApiUrl: userApiUrl,
  createUserApiUrl: userApiUrl + "createNew",
  login: serverUrl+"/login",
  userExistApiUrl: userApiUrl + "exist/",
  userExistSkill: userApiUrl + "existSkill/",
  addCollaborator: organizationApiUrl+"addCollaborator/",
  addExpert: organizationApiUrl+"addExpert/",
  createProjectApiUrl: projectApiUrl + "createNew",
  listOfProjectsApiUrl: projectApiUrl+"list/",
  modifyProjectApiUrl: projectApiUrl+"update/",
  existProjectApiUrl: projectApiUrl+"exist/",
  submitApiUrl: projectApiUrl+"submit/",
  listOfOrganizationsApiUrl: organizationApiUrl+"list/",
  createOrganizationApiUrl: organizationApiUrl + "createNew",
  existOrganizationApiUrl: organizationApiUrl + "exist/",
  getOrganizationByUserApiUrl: organizationApiUrl+"byUser/",
  getOrganizationMember: organizationApiUrl+"getUsers/",
  getUserSkills: userApiUrl+"getUserSkills/",
  modifyOrganizationApiUrl: organizationApiUrl+"update/",
  getUserSubmissions: userApiUrl+"getUserSubmissions/",
  rejectSubmission:projectApiUrl+"rejectCandidate/",
  listOfUsersApiUrl: userApiUrl+"list/",
  existUserApiUrl: userApiUrl + "exist/",
  addNewSkill: userApiUrl+"addNewSkill/",
  removeSkill: userApiUrl+"removeSkill/",
  getExpertsPage: userApiUrl+"listExperts/",
  removeMember: organizationApiUrl+"removeMember/",
  addMember: organizationApiUrl+"addMember/",
  rejectCandidate: projectApiUrl+"rejectCandidate/",
  acceptCandidate: projectApiUrl+"acceptCandidate/",
  removeTeamMember: projectApiUrl+"removeTeamMember/",
  getUserProjects: projectApiUrl+"byUser/",
  closeProject:projectApiUrl+"close/",
  getProjectsByOrganization:projectApiUrl+"getProjectsOfOrganization/",
  validateToken:userApiUrl+"validateToken",
  refreshToken:userApiUrl+"refreshToken",
};

//TODO spostare qui il globals service

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
