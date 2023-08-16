package com.hari.electronic.store;

import com.hari.electronic.store.entities.Role;
import com.hari.electronic.store.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Value("${role.normal.id}")
	private String role_normal_id;

	@Value("${role.admin.id}")
	private String role_admin_id;
	@Override
	public void run(String... args) throws Exception {

		try {
			Role roleNormal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
			Role roleAdmin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
			roleRepository.save(roleNormal);
			roleRepository.save(roleAdmin);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
