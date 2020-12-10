package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Expert;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;

import java.util.Set;

/**
 * This interface is responsible for managing all organizations of the application,
 * it knows how to get every organization by it's ID and can can create new organizations or 
 * delete old ones.
 */
public interface OrganizationsManager {

    /**
     * Retrieves the organization with the given ID and returns an instance of it.
     * @param organizationID the organization's ID
     * @return an instance of the organization
     */
    Organization getOrganizationInstance(int organizationID);

    /**
     * Creates a new {@link Organization} with the given parameters
     * @param creator the {@link User} who wants to create the new organization
     * @param title the organization's title
     * @param description the organization's description
     * @return the ID of the newly created organization
     */
    int createNewOrganization(User creator, String title, String description);

    /**
     * Removes an {@link Organization} from the system.
     * @param organizationID the organization's ID
     * @return true if the organization is successfully removed, false instead
     */
    boolean deleteOrganization(int organizationID);
}
