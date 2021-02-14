package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface EntityManager <T,I extends CharSequence>{
    T getInstance(@Valid @NotNull @NotBlank I id) throws EntityNotFoundException;
    T create(T object) throws EntityNotFoundException, IdConflictException;
    T update(T object) throws EntityNotFoundException;
    boolean delete(I id) throws EntityNotFoundException;
    boolean exists(I id);
}
