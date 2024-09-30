package com.sentMessageApi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class blacklistDto{
		private long id;
		private String blacklistMsisdn;
		private String msgId;
		private String timestamp;
		private String displayText;
		private String userName;
		private String data;
		private String event;
		private String status;
		private String botId;
		private String leadName;
		private String campaignName;
		private String clickid;
		
}
