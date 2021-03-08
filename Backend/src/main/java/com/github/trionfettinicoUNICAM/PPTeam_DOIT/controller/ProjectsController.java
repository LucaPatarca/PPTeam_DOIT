package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.BasicProjectInformation;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProjectsController extends EntityController<Project, String> {

    boolean closeProject(String projectID) throws EntityNotFoundException;

    boolean submit(String projectId, Role role) throws EntityNotFoundException;

    boolean acceptCandidate(String projectId, Role userRole) throws EntityNotFoundException;

    boolean rejectCandidate(String projectId, Role userRole) throws EntityNotFoundException;

    List<Role> getUserSubmissions(String userMail) throws EntityNotFoundException;

    Page<BasicProjectInformation> getPage(int page) throws EntityNotFoundException;

}
