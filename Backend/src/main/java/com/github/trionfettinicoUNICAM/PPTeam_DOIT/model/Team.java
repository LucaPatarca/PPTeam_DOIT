package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Set;

public interface Team {

    boolean addRole(Role r);

    boolean addRoles(Set<Role> r);

    boolean removeRole(Role r);

    int size();

    Set<Role> getTeamRoles();

}
