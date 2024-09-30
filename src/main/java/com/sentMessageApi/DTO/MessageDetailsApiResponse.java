package com.sentMessageApi.DTO;

import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class MessageDetailsApiResponse {

	private HttpStatus status;
	private int statusCode;
	private Object data;
	private String message;
}
