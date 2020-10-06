package com.spring.boot.reactive.initialize;

import com.spring.boot.reactive.model.Department;
import com.spring.boot.reactive.model.User;
import com.spring.boot.reactive.repository.DepartmentRepository;
import com.spring.boot.reactive.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
//@Profile("!dev")
@Slf4j
public class UserInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public void run(String... args) {
		initialDataSetup();
	}

	private List<User> getData() {
		return Arrays.asList(new User(null, "Spring User 1", 30, 120000), new User(null, "Spring User 2", 5, 130000),
				new User(null, "Spring User 3", 40, 140000));
	}

	private List<Department> getDepartments() {
		return Arrays.asList(new Department(null, "OpenSource", 1, "DotOrg"),
				new Department(null, "Spring", 2, "BootRocks"));
	}

	private void initialDataSetup() {
		userRepository.deleteAll().thenMany(Flux.fromIterable(getData())).flatMap(userRepository::save)
				.thenMany(userRepository.findAll()).subscribe(user -> {
					log.info("User Inserted from CommandLineRunner " + user);
				});

		departmentRepository.deleteAll().thenMany(Flux.fromIterable(getDepartments()))
				.flatMap(departmentRepository::save).thenMany(departmentRepository.findAll()).subscribe(user -> {
					log.info("Department Inserted from CommandLineRunner " + user);
				});

	}

}
