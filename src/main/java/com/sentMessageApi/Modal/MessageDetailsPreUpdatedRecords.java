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

import lombok.Data;

@Entity
@Data
@Table(name = "pre_updated_Message_Records")
public class MessageDetailsPreUpdatedRecords {
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
