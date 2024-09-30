package com.sentMessageApi.DTO;

import lombok.Data;

@Data
public class ShortUrlDto {

	private long id;
	private String longUrl;
	private String shortUrl;
	private int clientid;
	private String msisdn;
	private String ua;
	private String ip;
	private int clicks;
	private String trackingId;
	private String expiryAt;
	private String transactionId;
	private String clickId;

}

//{
//    "id": 821921594,
//    "longUrl": "https://www.paisabazaar.com/cards/marketing/preapproved?partnerProductId=305&pageSource=pacampaign&tokenId=[ID]&utm_source=crmsms&utm_medium=sms&utm_term=crmisms_PR&utm_campaign=D_5Apr2024_CCIBL_PRIME",
//    "shortUrl": "http://f49.biz/WbwKYKl",
//    "clientid": 41,
//    "msisdn": "9859859858",
//    "ua": "",
//    "ip": null,
//    "clicks": 0,
//    "trackingId": "2024040515572085",
//    "expiryAt": null,
//    "transactionId": "ce340a04-cb93-4bdf-946f-938db5c71579",
//    "clickId": null
//}