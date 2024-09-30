package com.sentMessageApi.Modal;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "conversationaldata", uniqueConstraints = { @UniqueConstraint(columnNames = {"blacklistMsisdn","msgId","leadName"}) })
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstApiData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 13)
	private String blacklistMsisdn;

	@Column(length = 30)
	private String msgId;

	@Column(length = 40)
	private String timestamp;

	@Column(length = 50)
	private String displayText;

	@Column(length = 50)
	private String userName;

	@Column(length = 13)
	private String data;

	@Column(length = 12)
	private String event;

	@Column(length = 10)
	private String status;

	@Column(length = 25)
	private String botId;

	@Column(length = 50)
	private String leadName;

	@Column(length = 50)
	private String campaignName;

	@Column(length = 50)
	private String clickid;

	@Column(length = 1)
	private int flag;
	@Column(length = 30)
	private LocalDateTime createdOn;

	@Column(length = 30)
	private LocalDateTime updatedOn;

	@PrePersist
	protected void onCreate() {
		createdOn = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedOn = LocalDateTime.now();
	}
}
