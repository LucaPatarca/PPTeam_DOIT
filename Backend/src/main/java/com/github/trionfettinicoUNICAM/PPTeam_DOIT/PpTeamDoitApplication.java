package com.github.trionfettinicoUNICAM.PPTeam_DOIT;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PpTeamDoitApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PpTeamDoitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
