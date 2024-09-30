package com.sentMessageApi.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class LongUrlResponseDto {

	private String msg;
	@JsonProperty("Data")
	private longUrlDto[] Data;
	private int status;
}
