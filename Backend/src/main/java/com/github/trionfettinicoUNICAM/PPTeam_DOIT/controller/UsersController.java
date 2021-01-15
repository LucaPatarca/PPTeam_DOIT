package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.UsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UsersController {

    @Autowired
    private UsersManager usersManager;

    @PreAuthorize("permitAll")
    @GetMapping("/{userEmail}")
    public User getUser(@PathVariable String userEmail){
        return usersManager.getUserInstance(userEmail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getUserSkills/{userEmail}")
    public Set<Skill> getUserSkill(@PathVariable String userEmail){
        return usersManager.getUserInstance(userEmail).getSkills();
    }

    @PreAuthorize("permitAll")
    @GetMapping("/existSkill/{skill}/{userEmail}")
    public boolean existSkill( @PathVariable String skill,@PathVariable String userEmail){
        return usersManager.existSkill(new Skill(skill),userEmail);
    }

    @PreAuthorize("permitAll")
    @PutMapping("/addSkillCollaborator/{userOrganization}/{userEmail}/{skillName}")
    public boolean addCollaborator(@PathVariable String userOrganization, @PathVariable String userEmail, @PathVariable String skillName){
        Skill skill = new Skill(skillName, userOrganization);
        if(usersManager.hasSkillExpertFor(skill,userEmail,userOrganization))
            return false;
        return usersManager.addCollaborator(userEmail,skill);
    }

    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createNewUser(@RequestBody User user){
        return usersManager.createUser(user.getMail(),user.getName());
    }

    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{userMail}")
    public boolean deleteUser(@PathVariable String userMail){
        return usersManager.deleteUser(userMail);
    }


    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean modifyUser(@RequestBody User user){
        return usersManager.updateUser(user);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/exist/")
    public boolean existsUser(@RequestBody String userEmail){
        return usersManager.exists(userEmail);
    }

}
