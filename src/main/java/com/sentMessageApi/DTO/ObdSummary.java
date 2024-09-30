package com.sentMessageApi.DTO;



import lombok.Data;

@Data
public class ObdSummary {
	private long id;

	private long valid_number;

	private long connected;

	private long credit_used;

	private long value;

	private String date;
}
