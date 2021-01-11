package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * This interface is responsible for managing all organizations of the application,
 * it knows how to get every organization by it's ID and can can create new organizations or 
 * delete old ones.
 */
public interface OrganizationsManager {

    /**
     * Retrieves the organization with the given ID and returns an instance of it.
     * @param organizationName the organization's unique name
     * @return an instance of the organization
     */
    Organization getOrganizationInstance(String organizationName);

    /**
     * Creates a new {@link Organization} with the given parameters
     * @param  organization the organization
     * @return the newly created organization
     */
    Organization createNewOrganization(Organization organization);

    /**
     * Removes an {@link Organization} from the system.
     * @param organizationName the organization's unique name
     * @return true if the organization is successfully removed, false instead
     */
    boolean deleteOrganization(String organizationName);

    /**
     * Updates the {@link Organization} passed as parameter and saves it.
     * @param organization the organization to be saved
     * @return true if the organization is stored successfully, false instead.
     */
    boolean updateOrganization(Organization organization);

    List<User> getUsers(String organizationName);

    boolean exists(String organizationName);

    List<Organization> findByUser(String userMail);

    Page<Organization> getPage(int page, int i);

    boolean addCollaborator(String organizationName, String userMail, Skill skill);

}
