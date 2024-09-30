package com.sentMessageApi.DTO;

import lombok.Data;

@Data
public class longUrlDto {

	private int smsUrlId;
	private String title;
	private String url;
	private String trackingId;
	private String createdBy;
	private String isActive;
	private String createdDate;
	private int clientId;
	private String messageType;
}


//{
//    "smsUrlId": 30,
//    "title": "MobiKwik",
//    "url": "https://www.fonada.com/",
//    "trackingId": "2024042211300481",
//    "createdBy": "Admin",
//    "isActive": "1",
//    "createdDate": "2024-04-22 11:30:04",
//    "clientId": 11,
//    "messageType": "promotional"
//}