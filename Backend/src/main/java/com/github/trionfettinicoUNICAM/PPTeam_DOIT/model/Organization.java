package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Set;

public interface Organization {

    boolean changeDescription(String desciption);

    int createProject(String name, String desciption);

    void updateProject(Project project);

    void removeProject(Project project);

    void addExpert(Expert expert);

    void AddExpert(User user, Role role);

    void updateExpert(User user, Role role);

    void removeExpert(Expert expert);

    Set<Project> getOpenProjects();

    Set<Project> getCloseProjects();

}
