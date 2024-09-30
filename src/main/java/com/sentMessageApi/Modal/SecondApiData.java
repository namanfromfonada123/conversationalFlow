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
@Table(name = "conversationaldata_Response")
public class SecondApiData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 5000)
	private String requestData;
	
	@Column(length = 5000)
	private String responseData;
	
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
