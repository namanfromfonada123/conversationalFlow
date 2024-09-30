package com.sentMessageApi.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties("user")
public class ApplicationProperties {
	Map<String, String> ukey = new HashMap<>();
	Map<String, String> username = new HashMap<>();
	Map<String, String> authUser = new HashMap<>();
	Map<String, String> authPass = new HashMap<>();
	Map<String, String> chatcampaignid = new HashMap<>();
	Map<String, String> shorturldomain = new HashMap<>();

	
}

