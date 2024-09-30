package com.sentMessageApi.DTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class CallBackReq {

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
