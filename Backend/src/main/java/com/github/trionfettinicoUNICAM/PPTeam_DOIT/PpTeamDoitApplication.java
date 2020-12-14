package com.github.trionfettinicoUNICAM.PPTeam_DOIT;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PpTeamDoitApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PpTeamDoitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
