package com.sentMessageApi.DTO;

import lombok.Data;

@Data
public class AuthTokenResponse {

	private int status;
	private String role;
	private String userType;
	private String msisdn;
	private int userId;
	private long dailyUsageLimit;
	private AuthToken result;
	private String botId;
}
