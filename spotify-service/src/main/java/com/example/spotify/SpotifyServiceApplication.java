package com.example.spotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpotifyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyServiceApplication.class, args);
	}

}
