package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public class Skill {

    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli)

    private String name;
    //se uno e Esperto in generale (isGloballyExpert) expertInOrganization e vuoto
    private Set<String> expertInOrganization;
    private boolean isGloballyExpert;

    public Skill(String name, boolean isExpert){
        setName(name);
        this.expertInOrganization = new HashSet<>();
        this.isGloballyExpert = isExpert;
    }

    public Skill(String name, Set<String> expertInOrganization, boolean isGloballyExpert) {
        this.name = name;
        this.expertInOrganization = expertInOrganization;
        this.isGloballyExpert = isGloballyExpert;
    }

    public void setName(String name){
        if(name.isBlank()) throw new IllegalArgumentException("Name is empty");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<String> getExpertInOrganization() {
        return expertInOrganization;
    }

    public void setExpertInOrganization(Set<String> expertInOrganization) {
        this.expertInOrganization = expertInOrganization;
    }

    public boolean isGloballyExpert() {
        return isGloballyExpert;
    }

    public void setGloballyExpert(boolean globallyExpert) {
        isGloballyExpert = globallyExpert;
    }

    public boolean isExpertFor(String organizationName){
        return this.isGloballyExpert() || this.expertInOrganization.contains(organizationName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return name.equalsIgnoreCase(skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toUpperCase(Locale.ROOT));
    }
}
