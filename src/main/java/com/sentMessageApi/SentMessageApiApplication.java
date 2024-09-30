package com.sentMessageApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class SentMessageApiApplication {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		URLEncoder.encode("\n", StandardCharsets.UTF_8.toString());
		
		SpringApplication.run(SentMessageApiApplication.class, args);
	}

	 @Bean
     RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.setConnectTimeout(Duration.ofSeconds(60))
    		.setReadTimeout(Duration.ofSeconds(60)) 
    		.build();
    }
	 
	 
}
