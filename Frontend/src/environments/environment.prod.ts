const serverUrl = "http://www.lucapatarca.cloud";
const projectApiUrl = serverUrl+"/api/projects/";
const organizationApiUrl = serverUrl+"/api/organizations/";
const userApiUrl = serverUrl+"/api/users/";

export const environment = {
  production: true,
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
  getOrganizationUserCreatorApiUrl: organizationApiUrl+"byUser/",
  getOrganizationMember: organizationApiUrl+"getUsers/",
  getUserSkills: userApiUrl+"getUserSkills/",
  modifyOrganizationApiUrl: organizationApiUrl+"update/",
  getUserSubmissions: userApiUrl+"getUserSubmissions/",
  rejectSubmission:projectApiUrl+"rejectCandidate/",
  listOfUsersApiUrl: userApiUrl+"list/",
  existUserApiUrl: userApiUrl + "exist/",
  addNewSkill:userApiUrl+"addNewSkill/",
  removeSkill:userApiUrl+"removeSkill/",
  getExpertsPage:userApiUrl+"listExperts/",
  removeMember:organizationApiUrl+"removeMember/",
  addMember:organizationApiUrl+"addMember/",
};
