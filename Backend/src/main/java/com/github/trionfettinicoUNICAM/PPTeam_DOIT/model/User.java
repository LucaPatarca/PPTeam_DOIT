package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Set;

public interface User {
    void addSkill(Skill skill);
    void removeSkill(Skill skill);
    Set<Skill> getSkills();
}
