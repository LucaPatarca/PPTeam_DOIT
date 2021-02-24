package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * Represents a group of {@link UserEntity}s who works together on a list of {@link Project}s. An organization
 * can be created by any {@link UserEntity}.
 */
@Document(collection = "organization")
public class Organization {

    @Id
    private String id;
    private String name;
    private String description;
    private Set<String> membersMails;
    private String creatorMail;

    public Organization(String name, String description, String creatorMail) {
        setName(name);
        setDescription(description);
        setCreatorMail(creatorMail);
        this.membersMails = new HashSet<>();
        if(creatorMail.length() == 0) throw new IllegalArgumentException("creatorMail is empty");
        this.membersMails.add(creatorMail);
    }

    public Organization(){
        this.name = "";
        this.id = "";
        this.description = "";
        this.membersMails = new HashSet<>();
        this.creatorMail = "";
    }

    public void addMember(String expertMail) {
        if(expertMail.isBlank()) throw new IllegalArgumentException("expertMail is empty");
        membersMails.add(expertMail);
    }

    public void removeMember(String userMail) {
        if(userMail.length() == 0) throw new IllegalArgumentException("userMail is empty");
        if(membersMails.contains(userMail))
            membersMails.remove(userMail);
        else
            throw new IllegalArgumentException("userMail does not exist");
    }

    public Set<String> getMembersMails() {
        return membersMails;
    }

    public void setMembersMails(Set<String> membersMails) {
        Objects.requireNonNull(membersMails, "membersMails is null");
        for(String s: membersMails) if(s.length() == 0) throw new IllegalArgumentException("membersMails contain a empty memberMail");
        this.membersMails=membersMails;
        List<String> lista = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if(name.isBlank()) throw new IllegalArgumentException("name is empty");
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if(description.isBlank()) throw new IllegalArgumentException("description is empty");
        this.description=description;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public void setCreatorMail(String creatorMail) throws IllegalArgumentException {
        if(creatorMail.isBlank()) throw new IllegalArgumentException("creatorMail is empty");
        this.creatorMail=creatorMail;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id.equals(that.id) && name.equals(that.name) && membersMails.equals(that.membersMails) && description.equals(that.description) && creatorMail.equals(that.creatorMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, membersMails, description, creatorMail);
    }
}
