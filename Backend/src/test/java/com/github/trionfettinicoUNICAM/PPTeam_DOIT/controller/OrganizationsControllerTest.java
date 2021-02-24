package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.BasicOrganizationInformation;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationsControllerTest extends ControllerTest{

    private Organization organization1;
    private Organization organization2;
    private UserEntity user1;
    private UserEntity user2;
    private UserEntity user3;

    @Test
    @Order(2)
    void getOrganization() throws Exception {
        String uri = "/api/organizations/"+organization1.getId();
        get(uri,200,organization1,Organization.class);

        uri = "/api/organizations/"+organization2.getId();
        get(uri,200,organization2,Organization.class);

        uri = "/api/organizations/noOrganization";
        get(uri,404,"Nessuna organizzazione");
    }

    @Test
    @Order(2)
    void getPage() throws Exception {
        String uri = "/api/organizations/list/0";
        String content = get(uri,200,"content");
        Page<BasicOrganizationInformation> page = toObjectPage(content, BasicOrganizationInformation.class);
        assertEquals(2,page.getTotalElements());
        assertTrue(page.toList().contains(new BasicOrganizationInformation(organization1,user1)));
        assertTrue(page.toList().contains(new BasicOrganizationInformation(organization2,user2)));
    }

    @Test
    @Order(1)
    void createOrganization() throws Exception {
        //creates local entities
        user1 = new UserEntity("test1@test.com","name1");
        user2 = new UserEntity("tes2@test.com","name2");
        user3 = new UserEntity("tes3@test.com","name3");
        organization1 = new Organization("name1","description", user1.getMail());
        organization2 = new Organization("name2","description", user2.getMail());
        organization1.addMember(user3.getMail());
        organization2.addMember(user3.getMail());

        //tries to create a new organization with non existing creator
        String uri = "/api/organizations/createNew";
        post(uri, organization1,404,"Nessun utente");

        //creates the new users
        post("/api/users/createNew", user1,200, user1, UserEntity.class);
        post("/api/users/createNew", user2,200, user2, UserEntity.class);

        //tries to create a new organization with non existing members
        post(uri,organization1,404,"Nessun utente");
        post(uri,organization2,404,"Nessun utente");

        post("/api/users/createNew", user3,200, user3, UserEntity.class);

        //creates and tests organization1
        String content = post(uri, organization1,200,null);
        Organization responseOrganization = toObject(content,Organization.class);
        organization1.setId(responseOrganization.getId());
        assertEquals(organization1,responseOrganization);

        post(uri, organization1,409, null);

        //creates and tests organization2
        content = post(uri, organization2,200,null);
        responseOrganization = toObject(content,Organization.class);
        organization2.setId(responseOrganization.getId());
        assertEquals(organization2,responseOrganization);

        post(uri, organization2,409, null);
    }

    @Test
    @Order(99)
    void deleteOrganization() throws Exception {
        String uri = "/api/organizations/"+ organization1.getId();
        delete(uri,200,"true");
        delete(uri,200, "false");

        uri = "/api/organizations/"+ organization2.getId();
        delete(uri,200,"true");
        delete(uri,200, "false");

        delete("/api/users/"+ user1.getMail(),200,"true");
        delete("/api/users/"+ user2.getMail(),200,"true");
        delete("/api/users/"+ user3.getMail(),200,"true");
    }

    @Test
    @Order(2)
    void getByUser() throws Exception {
        String uri = "/api/organizations/byUser/"+user1.getMail();
        String content = get(uri,200,null);
        List<Organization> list = new ArrayList<>(toObjectSet(content,Organization.class));
        assertEquals(1,list.size());
        assertTrue(list.contains(organization1));
        assertFalse(list.contains(organization2));

        uri = "/api/organizations/byUser/"+user2.getMail();
        content = get(uri,200,null);
        list = new ArrayList<>(toObjectSet(content,Organization.class));
        assertEquals(1,list.size());
        assertTrue(list.contains(organization2));
        assertFalse(list.contains(organization1));

        uri = "/api/organizations/byUser/"+user3.getMail();
        content = get(uri,200,null);
        list = new ArrayList<>(toObjectSet(content,Organization.class));
        assertEquals(2,list.size());
        assertTrue(list.contains(organization2));
        assertTrue(list.contains(organization1));
    }

    @Test
    @Order(2)
    void getUsers() throws Exception {
        String uri = "/api/organizations/getUsers/"+organization1.getId();
        List<UserEntity> list = new ArrayList<>(toObjectSet(get(uri,200,null), UserEntity.class));
        assertTrue(list.contains(user1));
        assertTrue(list.contains(user3));
        assertFalse(list.contains(user2));

        uri = "/api/organizations/getUsers/"+organization2.getId();
        list = new ArrayList<>(toObjectSet(get(uri,200,null), UserEntity.class));
        assertTrue(list.contains(user2));
        assertTrue(list.contains(user3));
        assertFalse(list.contains(user1));
    }

    @Test
    @Order(2)
    void addAndRemoveCollaborator() throws Exception {
        UserEntity user4 = new UserEntity("test4@test.com", "test4");
        String uri = "/api/organizations/addCollaborator/"+ organization1.getId()+"/"+ user4.getMail();
        Skill skill = new Skill("skillName");

        post(uri, skill, 404, "Nessun utente");

        post("/api/users/createNew", user4,200, user4, UserEntity.class);

        post(uri, skill, 200, null);

        String content = get("/api/organizations/" + organization1.getId(), 200, null);
        Organization org = toObject(content, Organization.class);
        assertTrue(org.getMembersMails().contains(user4.getMail()));

        content = get("/api/users/"+user4.getMail(), 200, null);
        UserEntity user = toObject(content, UserEntity.class);
        assertTrue(user.getSkills().contains(skill));
        assertTrue(user.getSkills().stream().filter(it->it.equals(skill)).findAny().get().isExpertFor(organization1.getId()));

        uri = "/api/organizations/removeMember/"+ organization1.getId()+"/"+ user4.getMail();
        post(uri,"", 200, null);

        content = get("/api/organizations/" + organization1.getId(), 200, null);
        org = toObject(content, Organization.class);
        assertFalse(org.getMembersMails().contains(user4.getMail()));

        content = get("/api/users/"+user4.getMail(), 200, null);
        user = toObject(content, UserEntity.class);
        assertTrue(user.getSkills().contains(skill));
        assertFalse(user.getSkills().stream().filter(it->it.equals(skill)).findAny().get().isExpertFor(organization1.getId()));


        delete("/api/users/"+ user4.getMail(),200,"true");
    }

    @Test
    @Order(2)
    void addAndRemoveMember() throws Exception {
        UserEntity user4 = new UserEntity("test4@test.com", "test4");
        String uri = "/api/organizations/addMember/"+ organization1.getId()+"/"+ user4.getMail();

        post(uri, "", 404, "Nessun utente");

        post("/api/users/createNew", user4,200, user4, UserEntity.class);

        post(uri, "", 200, "true");
        String content = get("/api/organizations/" + organization1.getId(), 200, null);
        Organization org = toObject(content, Organization.class);
        assertTrue(org.getMembersMails().contains(user4.getMail()));

        uri = "/api/organizations/removeMember/"+ organization1.getId()+"/"+ user4.getMail();
        post(uri,"", 200, null);
        content = get("/api/organizations/" + organization1.getId(), 200, null);
        org = toObject(content, Organization.class);
        assertFalse(org.getMembersMails().contains(user4.getMail()));

        delete("/api/users/"+ user4.getMail(),200,"true");
    }

    @Test
    @Order(2)
    void findByCreator() throws Exception {
        String uri = "/api/organizations/findByCreator/"+user1.getMail();
        String content = get(uri,200,null);
        List<Organization> list = new ArrayList<>(toObjectSet(content,Organization.class));
        assertEquals(1,list.size());
        assertTrue(list.contains(organization1));
        assertFalse(list.contains(organization2));

        uri = "/api/organizations/findByCreator/"+user2.getMail();
        content = get(uri,200,null);
        list = new ArrayList<>(toObjectSet(content,Organization.class));
        assertEquals(1,list.size());
        assertTrue(list.contains(organization2));
        assertFalse(list.contains(organization1));

        uri = "/api/organizations/findByCreator/"+user3.getMail();
        content = get(uri,200,null);
        list = new ArrayList<>(toObjectSet(content,Organization.class));
        assertEquals(0,list.size());
        assertFalse(list.contains(organization2));
        assertFalse(list.contains(organization1));
    }

    @Test
    @Order(2)
    void existsOrganization() throws Exception {
        String baseUri = "/api/organizations/exist/";
        get(baseUri+organization1.getId(),200, "true");
        get(baseUri+organization2.getId(),200, "true");
        get(baseUri+"noOrganization",200, "false");

    }

    @Test
    @Order(2)
    void modifyOrganization() throws Exception {
        String uri = "/api/organizations/update";
        Organization organization = new Organization("name", "description", "test@test.com");
        organization.setId("fakeId");
        put(uri,organization,404,"Nessuna organizzazione");
        String oldDescription = organization1.getDescription();
        organization1.setDescription("nuova descrizione");
        put(uri,organization1,200,organization1,Organization.class);
        organization1.setDescription(oldDescription);
        put(uri,organization1,200,organization1,Organization.class);
    }
}