package com.suems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SuemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuemsApplication.class, args);
	}

// this can be done for just render
	/*
	Your backend and database are now working, but the data simulator (DataSimulator.java) is throwing an exception because it’s trying to find a User with ID = 1, which doesn’t exist yet in your PostgreSQL DB.

	This line from your logs:

java.lang.RuntimeException: Simulator target user not found with ID: 1
	at com.suems.simulator.DataSimulator.lambda$tick$0(DataSimulator.java:57)


means your simulator is doing something like:

User user = userRepository.findById(targetUserId)
    .orElseThrow(() -> new RuntimeException("Simulator target user not found with ID: " + targetUserId));


But since your PostgreSQL database is empty (freshly created), there’s no user yet with id=1.
The simulator runs automatically every few seconds (because of @Scheduled) and keeps failing.
	*/
	@Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setEmail("admin@suems.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
                System.out.println("✅ Default admin user created with ID: " + admin.getId());
            }
        };
    }

	
}
