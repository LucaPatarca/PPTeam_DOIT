package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RealProject implements Project{

    private final Set<Role> candidates;
    private final Team team;
    private boolean isClosed;
    private final Map<Skill, Integer> neededSkills;
    @Id private String name;
    private String description;
    private final String organizationName;
    private final String creatorMail;

    public RealProject(Organization organization, User creator) throws IllegalArgumentException {

        Objects.requireNonNull(creator, "Creator is Null");
        Objects.requireNonNull(organization, "Organization is Null");

        if(!organization.getMembersMails().contains(creator.getMail()))
            throw new IllegalArgumentException("Creator user must be a member of the organization");

        this.candidates = new HashSet<>();
        this.team = new RealTeam(this.getName());
        this.isClosed = false;
        this.neededSkills = new HashMap<>();
        setOrganizationName(organization.getName());
        setCreatorMail(creator.getMail());
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
        if(isClosed) throw new IllegalStcateException("Project is closed");
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

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        RealProject that = (RealProject) object;
        return name.equals(that.name) && description.equals(that.description) && organizationName.equals(that.organizationName) && creatorMail.equals(that.creatorMail);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, organizationName, creatorMail);
    }
}
