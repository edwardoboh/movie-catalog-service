package com.edwardoboh.moviemicro.moviecatalogservice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class  MovieCatalogServiceApplication implements CommandLineRunner {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

	public void run(String ...args){
		System.out.println("Ran Command Line Runner");
	}
}
