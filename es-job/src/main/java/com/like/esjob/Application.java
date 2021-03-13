package com.like.esjob;

import com.like.task.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableElasticJob
@SpringBootApplication()
@ComponentScan(basePackages = {"com.like.esjob.*", "com.like.esjob.service.*", "com.like.task.annotation", "com.like.esjob.task.*"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
