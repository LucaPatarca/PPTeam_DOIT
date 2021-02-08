package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;


public interface UsersManager extends EntityManager<User, String>,BasicJsonInformation<User>{
    User getInstance(String userMail) throws EntityNotFoundException;
    User create(User user) throws IdConflictException;
    boolean delete(String mail) throws EntityNotFoundException;
    User update(User user) throws EntityNotFoundException;
    boolean exists(String userMail);
    boolean existSkill(Skill skill, String userMail) throws EntityNotFoundException;
    boolean hasSkillExpertFor(Skill skill, String userMail, String organizationId) throws EntityNotFoundException;
}
