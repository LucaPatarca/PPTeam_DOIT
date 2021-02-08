package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Role;

import java.util.List;

public interface ProjectsController extends EntityController<Project, String> {

    boolean closeProject(String projectID) throws EntityNotFoundException;

    boolean addNeededSkill(String projectId, String skillName);

    boolean removeNeededSkill(String projectId, String skillName);

    boolean submit(String projectId, String userMail, Role role);

    boolean acceptCandidate(String projectId, Role userRole);

    boolean rejectCandidate(String projectId, Role userRole);

    List<Role> getUserSubmissions(String userMail);

}
