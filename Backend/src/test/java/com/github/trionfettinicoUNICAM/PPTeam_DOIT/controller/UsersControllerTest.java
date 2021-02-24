package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser
public class UsersControllerTest extends ControllerTest {

    private UserEntity user;
    private Skill skill1;
    private Skill skill2;
    private Skill skill3;

    @Test
    @Order(1)
    void createNewUser() throws Exception {
        //deletes possible existing users to make sure test is not affected by something that is
        //already in the database
        mvc.perform(MockMvcRequestBuilders.delete("/api/users/test@test.com")).andReturn();

        //creates the new user
        String uri = "/api/users/createNew";
        UserEntity user = new UserEntity("test@test.com","Test JUnit");
        user.setSecret("password");
        skill1 = new Skill("skill1");
        skill2 = new Skill("skill2", "fakeOrgID");
        skill2.setLevel(3);
        skill3 = new Skill("skill3");
        skill3.setLevel(10);
        user.addSkill(skill1);
        user.addSkill(skill2);
        user.addSkill(skill3);

        this.user = post(uri,user,200,user, UserEntity.class);

        //attempt to create the same user again
        post(uri,user,409,"Esiste già un utente con questa mail");
    }

    @Test
    @Order(2)
    void getUser() throws Exception {
        //should return the user if user exist
        String uri = "/api/users/"+user.getMail();
        get(uri, 200,user, UserEntity.class);
        //should return error 404 if user do not exist
        uri = "/api/users/noUser";
        get(uri,404,"Nessun utente");
    }

    @Test
    @Order(2)
    void getUserSkill() throws Exception {
        //should return the skill set if the user exist
        String uri = "/api/users/getUserSkills/"+user.getMail();
        String content = get(uri,200,null);
        Set<Skill> responseUser = toObjectSet(content,Skill.class);
        assertEquals(user.getSkills(), responseUser);
        //should return 404 if user do not exist
        uri = "/api/users/getUserSkills/noUser";
        get(uri,404,"Nessun utente");
    }

    @Test
    @Order(2)
    void existSkill() throws Exception {
        //should return true for existing skills
        String baseUri = "/api/users/existSkill/";
        String uri = baseUri + skill1.getName() + "/" + user.getMail();
        get(uri,200,"true");
        uri = baseUri + skill2.getName() + "/" + user.getMail();
        get(uri,200,"true");
        uri = baseUri + skill3.getName() + "/" + user.getMail();
        get(uri,200,"true");
        //should return false for non existing skills
        uri = baseUri + "noSkill" + "/" + user.getMail();
        get(uri,200,"false");
    }

    @Test
    @Order(99)
    void deleteUser() throws Exception {
        //should delete existing user
        String uri = "/api/users/"+user.getMail();
        delete(uri,200,"true");
        //should return 404 for non existing users
        delete(uri,404,"Nessun utente");
    }

    @Test
    @Order(2)
    void modifyUser() throws Exception {
        //should modify existing users
        String uri = "/api/users/update";
        user.setName("newName");
        user.setExpert(new Skill("skill99"),"fakeOrgId");
        user.getSkills().remove(skill3);
        put(uri,user,200,user, UserEntity.class);
        //should return 404 for non existing users
        UserEntity user = new UserEntity("non.existing@user.com","non existing user");
        put(uri,user,404,"Nessun utente");
    }

    @Test
    @Order(2)
    void existsUser() throws Exception {
        //should return true for existing users
        String uri = "/api/users/exist/"+user.getMail();
        get(uri,200,"true");
        //should return false for non existing users
        uri = "/api/users/exist/noUser";
        get(uri,200,"false");
    }
}
