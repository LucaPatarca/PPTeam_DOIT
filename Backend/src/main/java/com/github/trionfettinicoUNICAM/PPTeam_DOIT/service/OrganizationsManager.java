package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import org.bson.types.ObjectId;
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
    Organization getOrganizationInstance(String organizationId);

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
    boolean deleteOrganization(String organizationId);

    /**
     * Updates the {@link Organization} passed as parameter and saves it.
     * @param organization the organization to be saved
     * @return true if the organization is stored successfully, false instead.
     */
    Organization updateOrganization(Organization organization);

    List<User> getUsers(String organizationId);

    boolean existsName(String organizationName);

    boolean exists(String organizationId);

    List<Organization> findByUser(String userMail);

    List<Organization> findCreator(String userMail);

    Page<Organization> getPage(int page, int i);

    boolean addCollaborator(String organizationId, String userMail, Skill skill);

}
