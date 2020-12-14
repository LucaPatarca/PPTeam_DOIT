package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

public class RealSkill implements Skill{

    private String name;

    public RealSkill(String name) {
        this.name = name;
    }

    public RealSkill() {
        this.name = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
