package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    //se uno e Esperto in generale (level maggiore di 10) expertInOrganization e vuoto
    private Set<String> expertInOrganization;
    private int level;
    public static final int EXPERT_LEVEL_THRESHOLD = 10;

    public Skill(String name){
        setName(name);
        this.expertInOrganization = new HashSet<>();
        this.level = 1;
    }

    public Skill(String name, String organizationId) {
        this.name = name;
        this.expertInOrganization = new HashSet<>();
        this.expertInOrganization.add(organizationId);
        this.level = 1;
    }

    //per spring boot
    public Skill(){
        this.name = "";
        this.expertInOrganization = new HashSet<>();
        this.level = 1;
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
        this.expertInOrganization = Objects.requireNonNull(expertInOrganization,"expertInOrganization nullo");
    }

    @JsonIgnore
    public boolean isGloballyExpert() {
        return level>=EXPERT_LEVEL_THRESHOLD;
    }

    public boolean isExpertFor(String organizationId){
        return this.isGloballyExpert() || this.expertInOrganization.contains(organizationId);
    }

    public void levelUp(){
        if(level < EXPERT_LEVEL_THRESHOLD) level++;
    }

    public void levelDown(){
        if(level > 0)
            level--;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level < 1 || level > 10 ) throw new IllegalArgumentException("il livello della skill deve essere compreso tra 1 e 10. valore trovato: "+level);
        this.level = level;
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
