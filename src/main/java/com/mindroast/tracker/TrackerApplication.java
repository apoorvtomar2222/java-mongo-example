package com.mindroast.tracker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address("Delhi","India","112211");
			String email = "apoorv1@java.com";
			Student student = new Student(
			 		"Apoorv","Tomar" ,email, 
			 		Gender.MALE, address,
			 		Arrays.asList("CSC")
			 		,BigDecimal.TEN, LocalDateTime.now());
			
			
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email));
			
			List<Student> students = mongoTemplate.find(query, Student.class);
			
			if(students.isEmpty()) {
				repository.save(student);
			} else {
				throw new IllegalStateException("Found email found"+ email);
			}
			
			
		};
	}
	
}
