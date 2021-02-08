package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;

public interface EntityManager <T,I>{
    T getInstance(I id) throws EntityNotFoundException;
    T create(T object) throws EntityNotFoundException, IdConflictException;
    T update(T object) throws EntityNotFoundException;
    boolean delete(I id) throws EntityNotFoundException;
    boolean exists(I id);
}
