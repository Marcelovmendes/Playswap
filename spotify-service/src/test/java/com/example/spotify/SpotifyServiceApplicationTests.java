package com.example.spotify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Import(TestConfig.class)
class SpotifyServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
