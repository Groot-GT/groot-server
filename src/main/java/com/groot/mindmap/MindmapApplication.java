package com.groot.mindmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MindmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindmapApplication.class, args);
	}

}
