package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a registered user inside the application. It has the ability to join a project's team
 * and to do so, it needs a list of {@link Skill}s.
 */
@Document(collection = "user")
public class UserEntity {

    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli)

    @Id
    private String mail;
    private String name;
    private Set<Skill> skills;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String secret;

    public UserEntity(String mail, String name) throws IllegalArgumentException {
        setMail(mail);
        setName(name);
        skills = new HashSet<>();
    }

    public UserEntity(UserAdapter userAdapter){
        this.setMail(userAdapter.getMail());
        this.setName(userAdapter.getName());
        this.setSkills(userAdapter.getSkills());
    }

    public UserEntity() {
    }

    /**
     * Adds a skill to the user's list.
     * @param skill the skill to add
     */
    public void addSkill(Skill skill) {
        skills.stream().filter(it->it.equals(skill)).findAny().ifPresent(userSkill->
                skill.getExpertInOrganization().forEach(
                        org->userSkill.getExpertInOrganization().add(org)
                )
        );
        skills.add(Objects.requireNonNull(skill, "skill is null"));
    }

    public void addSkills(Set<Skill> skills) {
        Objects.requireNonNull(skills, "Skills is Null");
        for(Skill s: skills) Objects.requireNonNull(s, "Skills contain a null skill");
        this.skills.addAll(skills);
    }

    /**
     * Removes a skill from the user's list.
     * @param skill the skill to remove
     */
    public void removeSkill(Skill skill) throws IllegalArgumentException{
        if(!skills.contains(skill)) throw new IllegalArgumentException("skill not found");
        skills.remove(Objects.requireNonNull(skill, "Skill is Null"));
    }

    /**
     * @return the set of skills this user has.
     */
    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        Objects.requireNonNull(skills, "Skills is Null");
        for(Skill s: skills) Objects.requireNonNull(s, "Skills contain a null skill");
        this.skills = skills;
    }

    /**
     * The {@link UserEntity} mail is unique for each user so a user can be identified by it's mail.
     * @return the user's mail
     */
    @Id
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws IllegalArgumentException {
        if(mail.isBlank()) throw new IllegalArgumentException("Mail is empty");
        this.mail = mail;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if(name.isBlank()) throw new IllegalArgumentException("Name is empty");
        this.name = name;
    }

    public void setExpert(Skill skill, String organizationId){
        Optional<Skill> optionalSkill = this.getSkills().stream().filter(it->it.equals(skill)).findAny();
        if(optionalSkill.isPresent()){
            optionalSkill.get().getExpertInOrganization().add(organizationId);
        } else{
            skill.getExpertInOrganization().add(organizationId);
            this.addSkill(skill);
        }
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean hasSkill(Skill skill){
        return skills.stream().anyMatch(it->it.equals(skill) && it.getLevel()>=skill.getLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return Objects.equals(mail, user.mail) && Objects.equals(name, user.name) && Objects.equals(skills, user.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, name, skills);
    }
}
