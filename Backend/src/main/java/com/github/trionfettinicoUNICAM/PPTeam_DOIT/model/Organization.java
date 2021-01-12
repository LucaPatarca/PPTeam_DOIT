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
    private Set<String> expertsMails;
    private Set<String> collaboratorsMails;
    private String description;
    private String creatorMail;

    public Organization(String name, String description, String creatorMail) throws IllegalArgumentException {
        setName(name);
        setDescription(description);
        setCreatorMail(creatorMail);
        this.membersMails = new HashSet<>();
        if(creatorMail.length() == 0) throw new IllegalArgumentException("creatorMail is empty");
        this.membersMails.add(creatorMail);
        this.expertsMails = new HashSet<>();
        this.collaboratorsMails = new HashSet<>();
    }

    public void addExpert(String expertMail) throws IllegalArgumentException {
        if(expertMail.length() == 0) throw new IllegalArgumentException("expertMail is empty");
        expertsMails.add(expertMail);
    }

    public void addCollaborator(String collaboratorMail, Skill skill) throws IllegalArgumentException {
        if(collaboratorMail.length() == 0) throw new IllegalArgumentException("collaboratorMail is empty");
        Set<Skill> skills = new HashSet<>();
        skills.add(skill);
        collaboratorsMails.add(collaboratorMail);
    }

    public void addCollaboratorSkill(String collaboratorMail, Skill skill) throws IllegalArgumentException {
        if(collaboratorMail.length() == 0) throw new IllegalArgumentException("collaboratorMail is empty");
        //collaboratorsMails.add(collaboratorMail);
    }

    public void removeCollaboratorSkill(String collaboratorMail, Skill skill) throws IllegalArgumentException {
        if(collaboratorMail.length() == 0) throw new IllegalArgumentException("collaboratorMail is empty");
        collaboratorsMails.remove(collaboratorMail);
    }

    public void removeExpert(String expertMail) throws IllegalArgumentException {
        if(expertMail.length() == 0) throw new IllegalArgumentException("expertMail is empty");
        expertsMails.remove(expertMail);
    }

    public void removeCollaborator(String collaboratorMail) throws IllegalArgumentException {
        if(collaboratorMail.length() == 0) throw new IllegalArgumentException("collaboratorMail is empty");
        collaboratorsMails.remove(collaboratorMail);
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

    public Set<String> getExpertsMails() {
        return expertsMails;
    }

    public void setExpertsMails(Set<String> expertsMails) {
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

    public Set<String> getCollaboratorsMails() {
        return collaboratorsMails;
    }

    public void setCollaboratorsMails(Set<String> collaboratorsMails) {
        this.collaboratorsMails = collaboratorsMails;
    }

}
