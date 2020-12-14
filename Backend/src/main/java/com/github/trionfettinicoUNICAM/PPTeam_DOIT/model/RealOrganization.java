package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

@Document(collection = "organization")
public class RealOrganization implements Organization{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealOrganization that = (RealOrganization) o;
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

    public RealOrganization(String name, String description, String creatorMail){
        this.name = name;
        this.description = description;
        this.creatorMail = creatorMail;
        this.membersMails = new HashSet<>();
        this.membersMails.add(creatorMail);
        this.expertsMails = new HashMap<>();
    }

    @Override
    public void addExpert(String expertMail, Skill skill) {
        expertsMails.put(expertMail, skill);
    }

    @Override
    public void removeExpert(String expertMail) {
        expertsMails.remove(expertMail);
    }

    @Override
    public void addMember(String userMail) {
        membersMails.add(userMail);
    }

    @Override
    public void removeMember(String userMail) {
        membersMails.remove(userMail);
    }

    @Override
    public Set<String> getMembersMails() {
        return membersMails;
    }

    @Override
    public void setMembersMails(Set<String> membersMails) {
        this.membersMails=membersMails;
    }

    @Override
    public Map<String, Skill> getExpertsMails() {
        return expertsMails;
    }

    @Override
    public void setExpertsMails(Map<String,Skill> expertsMails) {
        this.expertsMails=expertsMails;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description=description;
    }

    @Override
    public String getCreatorMail() {
        return creatorMail;
    }

    @Override
    public void setCreatorMail(String creatorMail) {
        this.creatorMail=creatorMail;
    }
}
