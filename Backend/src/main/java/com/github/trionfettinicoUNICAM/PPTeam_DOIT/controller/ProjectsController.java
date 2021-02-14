package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Role;

import java.util.List;

public interface ProjectsController extends EntityController<Project, String> {

    boolean closeProject(String projectID) throws EntityNotFoundException;

    boolean submit(String projectId, String userMail, Role role) throws EntityNotFoundException;

    boolean acceptCandidate(String projectId, Role userRole) throws EntityNotFoundException;

    boolean rejectCandidate(String projectId, Role userRole) throws EntityNotFoundException;

    List<Role> getUserSubmissions(String userMail) throws EntityNotFoundException;

}
