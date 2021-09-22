package com.blogposts.blogposts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BlogpostsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogpostsApplication.class, args);

		System.out.println("thank you");
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
