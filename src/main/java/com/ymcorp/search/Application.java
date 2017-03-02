package com.ymcorp.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ymcorp.search.core.ElasticsearchService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private ElasticsearchService service;

	@Bean
	public CommandLineRunner run() {
		return (args -> {
			service.search();

			service.aggregation();

			service.scoreSearch();
		});
	}
}