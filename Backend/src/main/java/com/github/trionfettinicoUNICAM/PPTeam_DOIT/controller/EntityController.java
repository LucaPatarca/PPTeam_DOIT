package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import org.springframework.data.domain.Page;

public interface EntityController <T,I>{

    T getInstance(I id) throws EntityNotFoundException;

    T create(T object) throws EntityNotFoundException, IdConflictException;

    T update(T object) throws EntityNotFoundException;

    boolean delete(I id) throws EntityNotFoundException;

    boolean exists(I id);

}
