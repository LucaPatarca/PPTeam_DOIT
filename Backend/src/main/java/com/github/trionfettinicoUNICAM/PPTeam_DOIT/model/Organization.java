package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a group of {@link User}s who works together on a list of {@link Project}s. An organization
 * can be created by any {@link User}.
 */
public interface Organization {
    // TODO: 15/12/20 far diventare una classe come fatto con project
    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli)
    void addExpert(String expertMail, Skill skill);
    void removeExpert(String expertMail);
    void addMember(String userMail);
    void removeMember(String userMail);

    Set<String> getMembersMails();
    void setMembersMails(Set<String> membersMails);
    Map<String,Skill> getExpertsMails();
    void setExpertsMails(Map<String,Skill> expertsMails);
    @Id
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    String getCreatorMail();
    void setCreatorMail(String creatorMail);
}
