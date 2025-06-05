package com.example.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class masterApplication {

	public static void main(String[] args) {
		SpringApplication.run(masterApplication.class, args);
	}

}
//EndPoint for Leaves Master =>        http://localhost:8081/leave-type                (UPDATED)
//EndPoint for User Master =>          http://localhost:8081/user-type                 (UPDATED)
//EndPoint for Designation Master =>   http://localhost:8081/designation-type          (UPDATED)
//EndPoint for Qualification Master => http://localhost:8081/qualification-type        (UPDATED)
//EndPoint for University Master =>    http://localhost:8081/university-type           (UPDATED)
//EndPoint for Institute Master =>     http://localhost:8081/institute-type            (UPDATED)
// EndPoint for H2 Database =>         http://localhost:8081/h2-console                (JDBC URL: jdbc:h2:mem:testdb)