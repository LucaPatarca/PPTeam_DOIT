package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
@Document(collection = "project")
public class Project {
    @Id
    private String id;
    private String title;
    private String description;
    private String organizationId;
    private String creatorMail;
    private boolean isClosed;
    private Set<Skill> neededSkills;
    private Set<Role> team;
    private Set<Role> candidates;

    public Project(String organizationId, String creatorMail, String title, String description) throws IllegalArgumentException {
        this.candidates = new HashSet<>();
        setTitle(title);
        setDescription(description);
        this.team = new HashSet<>();
        this.isClosed = false;
        this.neededSkills = new HashSet<>();
        setOrganizationId(organizationId);
        setCreatorMail(creatorMail);
    }

    public Project() {
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(Objects.requireNonNull(title,"Name is null").isBlank())
            throw new IllegalArgumentException("Name is empty");
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(Objects.requireNonNull(description,"Description is null").isBlank())
            throw new IllegalArgumentException("Description is empty");
        this.description=description;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(Objects.requireNonNull(organizationId,"Organization Id is null").isBlank())
            throw new IllegalArgumentException("Organization Id is empty");
        this.organizationId=organizationId;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public void setCreatorMail(String creatorMail) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(Objects.requireNonNull(creatorMail,"CreatorMail is null").isBlank())
            throw new IllegalArgumentException("CreatorMail is empty");
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
     * To be successful each project needs a set of {@link Skill}s, {@link UserEntity}s can than use
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

    public Set<Role> getTeam() {
        return team;
    }

    public void setTeam(Set<Role> team) {
        this.team = Objects.requireNonNull(team, "Team is Null");
    }

    /**
     * When a {@link UserEntity} apply to a {@link Project} he is put on a list
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
     *
     */
    public void acceptCandidate(Role role) {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(!candidates.contains(role)) throw new IllegalArgumentException("Role is not in the candidates list");
        if(!team.add(Objects.requireNonNull(role, "role is null"))) throw new RuntimeException("unable to add role to team");
        candidates.remove(role);
        neededSkills.remove(role.getSkill());
    }

    /**
     * Rejects the {@link Role} of a {@link UserEntity} who applied for this project.
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
        if(isClosed) throw new IllegalStateException("Project is closed");
        isClosed=true;
    }

    /**
     * Adds the {@link UserEntity} to the list of candidates of this project. A user can submit
     * to the same project more than once but only for different {@link Skill}s.
     * @return true if the user was added to candidates, false instead.
     * @throws IllegalArgumentException if the user does not have this {@link Skill} or if
     * the skill is not needed for this project
     */
    public boolean submit(Role role) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(team.contains(role)) throw new IllegalStateException("Il candidato è già nel team");
        if(!candidates.contains(role) && neededSkills.contains(role.getSkill())) {
            candidates.add(role);
            return true;
        } else return false;
    }

    public boolean removeTeamMember(Role userRole){
        neededSkills.add(userRole.getSkill());
        return team.remove(userRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return isClosed == project.isClosed && id.equals(project.id) && title.equals(project.title) && Objects.equals(description, project.description) && organizationId.equals(project.organizationId) && creatorMail.equals(project.creatorMail) && Objects.equals(neededSkills, project.neededSkills) && Objects.equals(team, project.team) && Objects.equals(candidates, project.candidates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, organizationId, creatorMail, isClosed, neededSkills, team, candidates);
    }
}
