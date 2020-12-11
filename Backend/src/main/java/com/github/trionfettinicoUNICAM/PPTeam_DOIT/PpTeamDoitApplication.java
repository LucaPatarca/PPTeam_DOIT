package com.github.trionfettinicoUNICAM.PPTeam_DOIT;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.RealOrganization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.RealUser;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PpTeamDoitApplication implements CommandLineRunner {

	@Autowired
	private ProjectsManager manager;

	public static void main(String[] args) {
		SpringApplication.run(PpTeamDoitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Organization organization = new RealOrganization("org");
		User user = new RealUser("mail");
		organization.addMember(user);
		manager.openNewEmptyProject(organization,"title1", "description", user);
		manager.openNewEmptyProject(organization,"title2", "description", user);
		manager.openNewEmptyProject(organization,"title3", "description", user);
		manager.openNewEmptyProject(organization,"title4", "description", user);
		manager.openNewEmptyProject(organization,"title5", "description", user);
		manager.openNewEmptyProject(organization,"title6", "description", user);
		manager.openNewEmptyProject(organization,"title7", "description", user);
		manager.openNewEmptyProject(organization,"title8", "description", user);
		manager.openNewEmptyProject(organization,"title9", "description", user);
		manager.openNewEmptyProject(organization,"title10", "description", user);
		manager.openNewEmptyProject(organization,"title11", "description", user);
		manager.openNewEmptyProject(organization,"title12", "description", user);
		manager.openNewEmptyProject(organization,"title13", "description", user);
		manager.openNewEmptyProject(organization,"title14", "description", user);
		manager.openNewEmptyProject(organization,"title15", "description", user);
		manager.openNewEmptyProject(organization,"title16", "description", user);*/
	}
}
