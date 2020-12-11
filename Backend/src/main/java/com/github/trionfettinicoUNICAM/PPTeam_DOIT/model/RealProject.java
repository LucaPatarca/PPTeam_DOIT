package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "project")
public class RealProject implements Project{

    private Set<Role> candidates;
    private Team team;
    private boolean isClosed;
    private Map<Skill, Integer> neededSkills;
    @Id
    private String name;
    private String description;
    private String organizationName;
    private String creatorMail;

    public RealProject(String organizationName, String creatorMail, String name, String description) throws IllegalArgumentException {

        Objects.requireNonNull(creatorMail, "Creator is Null");
        Objects.requireNonNull(organizationName, "Organization is Null");

        this.candidates = new HashSet<>();
        this.name = name;
        this.description = description;
        this.team = new RealTeam(this.getName());
        this.isClosed = false;
        this.neededSkills = new HashMap<>();
        setOrganizationName(organizationName);
        setCreatorMail(creatorMail);
    }

    @Override
    public Set<Role> getCandidates() {
        return candidates;
    }

    @Override
    public void acceptCandidate(Role role) throws IllegalStateException, RuntimeException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(!team.addRole(role)) throw new RuntimeException("unable to add role to team");
    }

    @Override
    public void rejectCandidate(Role role) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        candidates.remove(role);
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() {
        isClosed=true;
    }

    @Override
    public Map<Skill, Integer> getNeededSkills() {
        return neededSkills;
    }

    @Override
    public boolean submit(User user, Skill skill) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        RealRole role = new RealRole(skill, user.getMail(), this.getName());
        if(!candidates.contains(role)) {
            candidates.add(role);
            return true;
        } else return false;
    }

    @Override
    public void removeSubmission(User user) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        candidates.removeIf(role -> role.getUserMail().equals(user.getMail()));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public String getCreatorMail() {
        return creatorMail;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void setName(String name) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(name.length() == 0) throw new IllegalArgumentException("Name is empty");
        this.name = name;
    }

    @Override
    public void setDescription(String description) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(description.length() == 0) throw new IllegalArgumentException("Description is empty");
        this.description=description;
    }

    public void setCreatorMail(String creatorMail) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(creatorMail.length() == 0) throw new IllegalArgumentException("CreatorMail is empty");
        this.creatorMail=creatorMail;
    }

    public void setOrganizationName(String organizationName) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(organizationName.length() == 0) throw new IllegalArgumentException("OrganizationName is empty");
        this.organizationName=organizationName;
    }

    public void setCandidates(Set<Role> candidates) {
        this.candidates = candidates;
    }

    public void setNeededSkills(Map<Skill, Integer> neededSkills) {
        this.neededSkills = neededSkills;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RealProject that = (RealProject) object;
        return name.equals(that.name) && description.equals(that.description) && organizationName.equals(that.organizationName) && creatorMail.equals(that.creatorMail);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, organizationName, creatorMail);
    }
}
