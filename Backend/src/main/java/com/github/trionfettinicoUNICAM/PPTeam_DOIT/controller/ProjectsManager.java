package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Expert;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Role;

public interface ProjectsManager {
    Project getProjectInstance(int projectID);
    int openNewEmptyProject(Organization organization, String title, String description);
    boolean closeProject(int projectID);
    boolean deleteProject(int projectID);
    void addToTeam(int projectID, Role role);
    void addExpert(int projectID, Expert expert);
    void removeFromTeam(int projectID, Role role);
    void removeExpert(int projectID, Expert expert);
}
