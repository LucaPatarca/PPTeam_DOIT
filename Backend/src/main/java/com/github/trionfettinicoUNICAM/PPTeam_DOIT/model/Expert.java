package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Set;

public interface Expert extends User{

    void addExpertSkill(Skill skill);

    void addExpertSkills(Set<Skill> skills);

    void updateExpertSkill(Skill oldSkill, Skill newSkill);

    void removeExpertSkill(Skill skill);

    void removeExpertSkills(Set<Skill> skills);

    Set<Skill> getExpertSkills();
}
