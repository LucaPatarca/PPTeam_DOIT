package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Set;

public interface Expert extends User{
    void addExpertSKill(Skill skill);
    void removeExpertSkill(Skill skill);
    Set<Skill> getExpertSkills();
}
