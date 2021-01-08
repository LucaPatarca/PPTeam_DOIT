package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Expert;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;


public interface ExpertsManager {
    Expert getExpertInstance(String userMail);
    Expert createExpert(User user);
    boolean deleteExpert(String mail);
    boolean removeSkill(String mail,Skill skill);
    boolean addSkill(String mail,Skill skill);
    boolean exists(String userMail);
}
