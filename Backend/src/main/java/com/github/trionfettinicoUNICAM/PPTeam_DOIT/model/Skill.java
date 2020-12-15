package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

/**
 * It's just a label that represents a skill which a {@link User} can know and an {@link Expert} can master.
 * Each {@link Project}s has a set of skills that are needed to complete the project.
 */
public class Skill {

    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli)

    private String name;

    public Skill(String name) throws IllegalArgumentException{
        setName(name);
    }

    public Skill() { this.name = ""; }

    public void setName(String name) throws IllegalArgumentException{
        if(name.length() == 0) throw new IllegalArgumentException("Name is empty");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
