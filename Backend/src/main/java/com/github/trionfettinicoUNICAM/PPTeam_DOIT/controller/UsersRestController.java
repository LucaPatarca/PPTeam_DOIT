package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserSubmissionInformation;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.security.PermissionComponent;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.UsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UsersRestController implements UsersController {

    @Autowired
    private UsersManager manager;
    @Autowired
    private PermissionComponent permissionComponent;

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/{userID}")
    public UserEntity getInstance(@PathVariable String userID) throws EntityNotFoundException { return manager.getInstance(userID); }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity create(@RequestBody UserEntity user) throws EntityNotFoundException, IdConflictException { return manager.create(user); }

    @Override
    @PreAuthorize("@permissionComponent.sameMail(authentication, #user.mail)")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity update(@RequestBody UserEntity user) throws EntityNotFoundException { return manager.update(user); }

    @Override
    @PreAuthorize("@permissionComponent.sameMail(authentication, #userID)")
    @DeleteMapping(value = "/{userID}")
    public boolean delete(@PathVariable String userID) throws EntityNotFoundException { return manager.delete(userID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/exist/{userID}")
    public boolean exists(@PathVariable String userID) { return manager.exists(userID); }

    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<UserEntity> getPage(@PathVariable int page) throws EntityNotFoundException {
        return manager.getPage(page, 10);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/listExprts/{page}")
    public Page<UserEntity> getExpertsPage(@PathVariable int page) throws EntityNotFoundException {
        return manager.getExpertPage(page, 10);
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/getUserSkills/{userEmail}")
    public Set<Skill> getUserSkill(@PathVariable String userEmail) throws EntityNotFoundException {
        return manager.getInstance(userEmail).getSkills();
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/existSkill/{skill}/{userEmail}")
    public boolean existSkill(@PathVariable String skill, @PathVariable String userEmail) throws EntityNotFoundException {
        return manager.existSkill(new Skill(skill),userEmail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getUserSubmissions/{userMail}")
    public List<UserSubmissionInformation> getUserSubmissions(@PathVariable String userMail) throws EntityNotFoundException {
        return manager.getUserSubmissions(userMail);
    }

    @PreAuthorize("@permissionComponent.sameMail(authentication, #userID)")
    @PostMapping(value = "/addNewSkill/{skillName}")
    public boolean addNewSkill(@RequestBody String userID,@PathVariable String skillName) { return manager.addNewSkill(skillName,userID); }

    @PreAuthorize("@permissionComponent.sameMail(authentication, #userID)")
    @PutMapping(value = "/removeSkill/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeSkill(@PathVariable String userID,@RequestBody Skill skill) { return manager.removeSkill(skill,userID); }

}
