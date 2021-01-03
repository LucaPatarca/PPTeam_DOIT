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
    @Id
    private String ID;
    private String name;
    private String description;
    private String organizationName;
    private String creatorMail;
    private boolean isClosed;
    private Set<Skill> neededSkills;
    private Team team;
    private Set<Role> candidates;

    public Project(String organizationName, String creatorMail, String name, String description) throws IllegalArgumentException {
        Objects.requireNonNull(creatorMail, "Creator is Null");
        Objects.requireNonNull(organizationName, "Organization is Null");
        setID(organizationName+"."+name);
        this.candidates = new HashSet<>();
        setName(name);
        setDescription(description);
        this.team = new Team(this.getName());
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

    public void setID(String ID) throws IllegalArgumentException {
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

    //TODO eccezione se lo stato è già nello stato che si vuole settare?
    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    /**
     * To be successful each project needs a set of {@link Skill}s, {@link User}s can than use
     * their skills to work for the project.
     * @return all the needed skills for this project grouped by name, each one associated
     * with it's count.
     */
    public Set<Skill> getNeededSkills() {
        return neededSkills;
    }

    public void setNeededSkills(Set<Skill> neededSkills) {
        Objects.requireNonNull(neededSkills, "NeededSkills is Null");
        for(Skill s: neededSkills) Objects.requireNonNull(s, "NeededSkills contains a null skill");
        this.neededSkills = neededSkills;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = Objects.requireNonNull(team, "Team is Null");
    }

    /**
     * When a {@link User} apply to a {@link Project} he is put on a list
     * waiting to be accepted (or rejected) by the project's {@link Organization}.
     * This method returns the set of candidates.
     * @return the set of candidates.
     */
    public Set<Role> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Role> candidates) {
        Objects.requireNonNull(candidates, "Candidates is Null");
        for(Role r: candidates) Objects.requireNonNull(r, "Candidates contains a null role");
        this.candidates = candidates;
    }


    /**
     * Accepts the {@link Role} of a {@link User} who applied for this project.
     * After this operation the {@link Role} becomes part of the project's {@link Team}.
     * The {@link User} should be notified for this operation.
     * @param role the role to accept
     */
    public void acceptCandidate(Role role) throws RuntimeException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(!team.addRole(Objects.requireNonNull(role, "role is null"))) throw new RuntimeException("unable to add role to team");
    }

    /**
     * Rejects the {@link Role} of a {@link User} who applied for this project.
     * @param role the role to be rejected
     */
    public void rejectCandidate(Role role) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        candidates.remove(Objects.requireNonNull(role, "role is null"));
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
    public boolean submit(User user, Skill skill) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        Role role = new Role(Objects.requireNonNull(skill, "skill is null"), Objects.requireNonNull(user, "user is null").getMail(), this.getName());
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
        candidates.removeIf(role -> role.getUserMail().equals(Objects.requireNonNull(user, "user is null").getMail()));
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
