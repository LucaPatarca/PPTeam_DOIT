package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface EntityManager <T,I extends CharSequence>{
    /**
     * Retrieves the organization with the given ID and returns an instance of it.
     * @return an instance of the organization
     */
    T getInstance(@Valid @NotNull @NotBlank I id) throws EntityNotFoundException;

    /**
     * Creates a new {@link Organization} with the given parameters
     * @param  organization the organization
     * @return the newly created organization
     */
    T create(T object) throws EntityNotFoundException, IdConflictException;

    /**
     * Updates the {@link Organization} passed as parameter and saves it.
     * @param organization the organization to be saved
     * @return true if the organization is stored successfully, false instead.
     */
    T update(T object) throws EntityNotFoundException, IdConflictException;

    /**
     * Removes an {@link Organization} from the system.
     * @return true if the organization is successfully removed, false instead
     */
    boolean delete(I id);
    boolean exists(I id);
}
