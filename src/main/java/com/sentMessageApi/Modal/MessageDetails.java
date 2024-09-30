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

import lombok.Data;

@Entity
@Table(name = "conversational_message_detail", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "campaignName"}) })
@Data
public class MessageDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 7)
	private String userId;
	
	@Column(length = 7)
	private String campaignid;
	
	@Column(length = 50)
	private String username;
	
	@Column(length = 50)
	private String campaignName;
	
	@Column(length = 2550)
	private String text;
	
//	@Column(length = 2, columnDefinition = "int default 0")
//	private int deleteMark;
	
	@Column(length = 30)
	private LocalDateTime createdOn;
	
	@Column(length = 30)
	private LocalDateTime updatedOn;
	  
//	date time formate 
	  @PrePersist
	  protected void onCreate() {
	      createdOn = LocalDateTime.now();
	      
	  }
	  
	  @PreUpdate
	  protected void onUpdate() {
	      updatedOn = LocalDateTime.now();
	  }
}
