package com.minturtle.database_class;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DatabaseClassApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseClassApplication.class, args);
	}


	@Component
	class MyCommandLineRunner implements CommandLineRunner{

		@Override
		public void run(String... args) throws Exception {

		}
	}
}
