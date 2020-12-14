package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.OrganizationsManager;
import org.springframework.data.annotation.Id;

import java.util.Set;

/**
 * Represents a registered user inside the application. It has the ability to join a project's team
 * and to do so, it needs a list of {@link Skill}s.
 */
public interface User {
    // TODO: 15/12/20 far diventare una classe come fatto con project
    // TODO: 14/12/20 togliere metodi di comportamento che andranno spostati in UserManager
    /**
     * Adds a skill to the user's list.
     * @param skill the skill to add
     */
    void addSkill(Skill skill);

    void addSkills(Set<Skill> skills);

    /**
     * Removes a skill from the user's list.
     * @param skill the skill to remove
     */
    void removeSkill(Skill skill);

    /**
     * @return the set of skills this user has.
     */
    Set<Skill> getSkills();

    void setSkills(Set<Skill> skills);

    /**
     * @return the list of {@link Role}s this user had submitted for.
     */
    Set<Role> getSubmissions();

    void setSubmissions(Set<Role> submissions);

    /**
     * @return the set of {@link Role}s for which this user had been accepted.
     */
    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    /**
     * The {@link User} mail is unique for each user so a user can be identified by it's mail.
     * @return the user's mail
     */
    @Id
    String getMail();

    void setMail(String mail);

    String getName();
    void setName(String name);
    int getAge();
    void setAge(int age);

}
