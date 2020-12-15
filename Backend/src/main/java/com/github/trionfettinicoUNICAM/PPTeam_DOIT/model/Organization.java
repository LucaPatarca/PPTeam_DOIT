package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * Represents a group of {@link User}s who works together on a list of {@link Project}s. An organization
 * can be created by any {@link User}.
 */
@Document(collection = "organization")
public class Organization {

    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) && membersMails.equals(that.membersMails) && expertsMails.equals(that.expertsMails) && description.equals(that.description) && creatorMail.equals(that.creatorMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, membersMails, expertsMails, description, creatorMail);
    }

    @Id
    private String name;
    private Set<String> membersMails;
    private Map<String, Skill> expertsMails;
    private String description;
    private String creatorMail;

    public Organization(String name, String description, String creatorMail) throws IllegalArgumentException {
        setName(name);
        setDescription(description);
        setCreatorMail(creatorMail);
        this.membersMails = new HashSet<>();
        if(creatorMail.length() == 0) throw new IllegalArgumentException("creatorMail is empty");
        this.membersMails.add(creatorMail);
        this.expertsMails = new HashMap<>();
    }

    public void addExpert(String expertMail, Skill skill) throws IllegalArgumentException {
        if(expertMail.length() == 0) throw new IllegalArgumentException("expertMail is empty");
        expertsMails.put(expertMail, Objects.requireNonNull(skill, "Skill is null"));
    }

    public void removeExpert(String expertMail) throws IllegalArgumentException {
        if(expertMail.length() == 0) throw new IllegalArgumentException("expertMail is empty");
        expertsMails.remove(expertMail);
    }

    public void addMember(String userMail) throws IllegalArgumentException {
        if(userMail.length() == 0) throw new IllegalArgumentException("userMail is empty");
        membersMails.add(userMail);
    }

    public void removeMember(String userMail) throws IllegalArgumentException {
        if(userMail.length() == 0) throw new IllegalArgumentException("userMail is empty");
        membersMails.remove(userMail);
    }

    public Set<String> getMembersMails() {
        return membersMails;
    }

    public void setMembersMails(Set<String> membersMails) {
        Objects.requireNonNull(membersMails, "membersMails is null");
        for(String s: membersMails) if(s.length() == 0) throw new IllegalArgumentException("membersMails contain a empty memberMail");
        this.membersMails=membersMails;
    }

    public Map<String, Skill> getExpertsMails() {
        return expertsMails;
    }

    public void setExpertsMails(Map<String,Skill> expertsMails) {
        //TODO aggiungere controlli struttura ricevuta in input
        this.expertsMails=expertsMails;
    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if(name.length() == 0) throw new IllegalArgumentException("name is empty");
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if(description.length() == 0) throw new IllegalArgumentException("description is empty");
        this.description=description;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public void setCreatorMail(String creatorMail) throws IllegalArgumentException {
        if(creatorMail.length() == 0) throw new IllegalArgumentException("creatorMail is empty");
        this.creatorMail=creatorMail;
    }
}
