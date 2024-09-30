package com.sentMessageApi.DTO;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class SmsSummary {

	private long id;
	
	private long sent_count;
	
	private long delivered;
	
	private long value;
	
	private String date;
}
