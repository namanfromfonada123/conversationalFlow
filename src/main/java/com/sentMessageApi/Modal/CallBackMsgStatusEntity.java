package com.sentMessageApi.Modal;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name = "demo_rcs.call_back_msg_status")
@Data
public class CallBackMsgStatusEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;
	@Column(name = "blacklist_msisdn")
	private String blacklistMsisdn;
	private String msgId;
	private String textMessage;
	private Date timestamp;
	private String displayText;
	private String data;
	private String event;
	private String status;
	private String botId;
	private String leadName;
	private String campaignName;
	private String dataSourceName;
	private String campaignType;
	private String templateCode;
	private String userName;
	private String isComplete;
	private Integer sendDlr;
	private String request;
	private String response;
	private String updateCallback;
	private String clickid;
	
}
