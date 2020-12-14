package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * Represents a project, it has a {@link Team}, a list of candidates, and a set of {@link Skill}s
 * that are needed to complete this project's task.
 */
@Document(collection = "project")
public class Project {
    //TODO cambiare l'ID dal nome a una combinazione di nome e organizzazione.
    @Id
    private String ID;
    private String name;
    private String description;
    private String organizationName;
    private String creatorMail;
    private boolean isClosed;
    private HashSet<Skill> neededSkills;
    private RealTeam team;
    private HashSet<Role> candidates;

    public Project(String organizationName, String creatorMail, String name, String description) throws IllegalArgumentException {
        Objects.requireNonNull(creatorMail, "Creator is Null");
        Objects.requireNonNull(organizationName, "Organization is Null");
        setID(organizationName+"."+name);
        this.candidates = new HashSet<>();
        this.name = name;
        this.description = description;
        this.team = new RealTeam(this.getName());
        this.isClosed = false;
        this.neededSkills = new HashSet<>();
        setOrganizationName(organizationName);
        setCreatorMail(creatorMail);
    }

    public Project() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        if(ID.indexOf('.') == -1) throw new IllegalArgumentException();
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(name.length() == 0) throw new IllegalArgumentException("Name is empty");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(description.length() == 0) throw new IllegalArgumentException("Description is empty");
        this.description=description;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(organizationName.length() == 0) throw new IllegalArgumentException("OrganizationName is empty");
        this.organizationName=organizationName;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public void setCreatorMail(String creatorMail) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(creatorMail.length() == 0) throw new IllegalArgumentException("CreatorMail is empty");
        this.creatorMail=creatorMail;
    }

    /**
     * @return true if the project is closed, false instead
     */
    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    /**
     * To be successful each project needs a set of {@link Skill}s, {@link User}s can than use
     * their skills to work for the project.
     * @return all the needed skills for this project grouped by name, each one associated
     * with it's count.
     */
    public HashSet<Skill> getNeededSkills() {
        return neededSkills;
    }

    public void setNeededSkills(HashSet<Skill> neededSkills) {
        this.neededSkills = neededSkills;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(RealTeam team) {
        this.team = team;
    }

    /**
     * When a {@link User} apply to a {@link Project} he is put on a list
     * waiting to be accepted (or rejected) by the project's {@link Organization}.
     * This method returns the set of candidates.
     * @return the set of candidates.
     */
    public HashSet<Role> getCandidates() {
        return candidates;
    }

    public void setCandidates(HashSet<Role> candidates) {
        this.candidates = candidates;
    }


    /**
     * Accepts the {@link Role} of a {@link User} who applied for this project.
     * After this operation the {@link Role} becomes part of the project's {@link Team}.
     * The {@link User} should be notified for this operation.
     * @param role the role to accept
     */
    public void acceptCandidate(RealRole role) throws IllegalStateException, RuntimeException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(!team.addRole(role)) throw new RuntimeException("unable to add role to team");
    }

    /**
     * Rejects the {@link Role} of a {@link User} who applied for this project.
     * @param role the role to be rejected
     */
    public void rejectCandidate(RealRole role) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        candidates.remove(role);
    }

    /**
     * Marks this project as closed. After this operation no one can apply to this project
     */
    public void close() {
        isClosed=true;
    }

    /**
     * Adds the {@link User} to the list of candidates of this project. A user can submit
     * to the same project more than once but only for different {@link Skill}s.
     * @param user the user who wants to apply to this project
     * @param skill the skill of the user
     * @return true if the user was added to candidates, false instead.
     * @throws IllegalArgumentException if the user does not have this {@link Skill} or if
     * the skill is not needed for this project
     */
    public boolean submit(User user, RealSkill skill) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        RealRole role = new RealRole(skill, user.getMail(), this.getName());
        if(!candidates.contains(role)) {
            candidates.add(role);
            return true;
        } else return false;
    }

    /**
     * Removes the {@link User} from the list of candidates of this project
     * @param user the user who wants to be removed from the list of candidates
     */
    public void removeSubmission(User user) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        candidates.removeIf(role -> role.getUserMail().equals(user.getMail()));
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Project that = (Project) object;
        return name.equals(that.name) && description.equals(that.description) && organizationName.equals(that.organizationName) && creatorMail.equals(that.creatorMail);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, organizationName, creatorMail);
    }
}
