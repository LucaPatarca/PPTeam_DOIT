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
        // TODO: 11/02/2021 controllare campi nulli (dentro ai vari setter)
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
        if(title.isBlank()) throw new IllegalArgumentException("Name is empty");
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(description.isBlank()) throw new IllegalArgumentException("Description is empty");
        this.description=description;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(organizationId.isBlank()) throw new IllegalArgumentException("Id is empty");
        this.organizationId=organizationId;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public void setCreatorMail(String creatorMail) throws IllegalStateException, IllegalArgumentException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(creatorMail.isBlank()) throw new IllegalArgumentException("CreatorMail is empty");
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

    public boolean addNeededSkill(Skill neededSkill) {
        Objects.requireNonNull(neededSkill, "NeededSkill is Null");
        return neededSkills.add(neededSkill);
    }

    public boolean removeNeededSkill(Skill neededSkill) {
        Objects.requireNonNull(neededSkill, "NeededSkill is Null");
        return neededSkills.remove(neededSkill);
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
    public void acceptCandidate(Role role) throws RuntimeException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        if(!team.add(Objects.requireNonNull(role, "role is null"))) throw new RuntimeException("unable to add role to team");
        candidates.remove(role);
        // TODO: 11/02/2021 controlla candidato inesistente
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
        // TODO: 04/02/2021 aggiungere il controlle se è già chiuso, in caso lancio illegal state
        isClosed=true;
    }

    /**
     * Adds the {@link UserEntity} to the list of candidates of this project. A user can submit
     * to the same project more than once but only for different {@link Skill}s.
     * @param user the user who wants to apply to this project
     * @param skill the skill of the user
     * @return true if the user was added to candidates, false instead.
     * @throws IllegalArgumentException if the user does not have this {@link Skill} or if
     * the skill is not needed for this project
     */
    public boolean submit(UserEntity user, Skill skill, boolean asExpert) throws IllegalStateException {
        // TODO: 11/02/2021 controlla che non è già nel team
        if(isClosed) throw new IllegalStateException("Project is closed");
        Role role = new Role(Objects.requireNonNull(skill, "skill is null"), Objects.requireNonNull(user, "user is null").getMail(),asExpert);
        if(!candidates.contains(role)) {
            candidates.add(role);
            return true;
        } else return false;
    }

    /**
     * Removes the {@link UserEntity} from the list of candidates of this project
     * @param user the user who wants to be removed from the list of candidates
     */
    public void removeSubmission(UserEntity user) throws IllegalStateException {
        if(isClosed) throw new IllegalStateException("Project is closed");
        candidates.removeIf(role -> role.getUserMail().equals(Objects.requireNonNull(user, "user is null").getMail()));
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Project that = (Project) object;
        return title.equals(that.title) && description.equals(that.description) && organizationId.equals(that.organizationId) && creatorMail.equals(that.creatorMail);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), title, description, organizationId, creatorMail);
    }
}
