package com.sentMessageApi.apiCall;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentMessageApi.DTO.AuthTokenResponse;
import com.sentMessageApi.DTO.LongUrlResponseDto;
import com.sentMessageApi.DTO.RsponseDto;
import com.sentMessageApi.DTO.ShortUrlDto;
import com.sentMessageApi.Modal.FirstApiData;
import com.sentMessageApi.Repository.FirstApiRepository;
import com.sentMessageApi.Repository.SecondApiRepository;
import com.sentMessageApi.utility.pojos.LeadInfoDetail;
import com.sentMessageApi.utility.pojos.LeadSchedule;
import com.sentMessageApi.utility.pojos.leadInfoRoot;

import net.bytebuddy.asm.Advice.This;

@Component
public class RestApiCall {

	Logger logger = LoggerFactory.getLogger(RestApiCall.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	FirstApiRepository faRepository;

	@Autowired
	SecondApiRepository saRepository;
	
	@Value("${conversationaldata.FirstApi}")
	private String conversationaldataApi;
	
	@Value("${conversationaldata_Response.SecondApi}")
	private String conversationaldata_RequestApi;
	
	@Value("${conversationaldata_Response.loginUrl}")
	private String conversationaldata_Request_loginUrlApi;
	
	@Value("${conversationaldata_Response.longUrl}")
	private String conversationaldata_Request_longUrlApi;

	@Value("${conversationaldata_Response.slackUrl}")
	private String conversationaldata_Request_slackUrlApi;

	@Value("${conversationaldata_Response.shortUrl}")
	private String conversationaldata_Request_shortUrlApi;
	
	public RsponseDto FirstapiCall(String UserName) {

		if (!conversationaldataApi.isEmpty()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", " application/json, text/plain");
			headers.add("Accept-Language", " en-US,en;q=0.9");
			headers.add("Connection", "keep-alive");
			headers.add("Content-Type", "application/json");

			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			
			String firstApiString = conversationaldataApi.replace("{userName}", UserName).replace("{startDate}", LocalDate.now().toString()).replace("{endDate}", LocalDate.now().toString());

			try {
				logger.info("Make Api Call to -->"+firstApiString);
				ResponseEntity<String> responseEntity = restTemplate.exchange(firstApiString,HttpMethod.POST, httpEntity, String.class);
				logger.info("Successfully calling the Above Api !!");
				
				ObjectMapper objectMapper = new ObjectMapper();
				String firstApiRes = responseEntity.getBody();
				if (firstApiRes== null) {
					
					return null;
				}
				RsponseDto rsponseDto = objectMapper.readValue( firstApiRes, RsponseDto.class);
				return rsponseDto;
				
			} catch (HttpClientErrorException | HttpServerErrorException e) {
				e.printStackTrace();
				logger.info(e.getMessage().replace("\"", "").replace("/", "//"));
				logger.error("Getting Exception During First Api Call :  !! " + e.getStatusCode() +" "+e.getMessage().replace("\"", ""));
				this.CallToSlack(e.getMessage().replace('"', ' ').replace("/", "//"),
						"Getting Exception During First Api Call");
				
				return null;
			} catch (Exception e) {
				e.printStackTrace();

				logger.info(e.getMessage().replace("\"", "").replace("/", "//"));
				logger.error("Getting Exception During First Api Call :  !! " + e.getMessage().replace("\"", ""));
				this.CallToSlack(e.getMessage().replace("\"", "").replace("/", "//"),
						"Getting Exception During First Api Call");
				return null;
			}
		}
		else {
			logger.error("Please Configure First Api");
			return null;
		}
		
	}

	public Map<String, String> SecondapiCall(FirstApiData fData, String campaignId,String templateKey, String templateValue, String ukey) throws JsonProcessingException {

		if (!conversationaldata_RequestApi.isEmpty()) {
			String LeadName = "lead_chat_"+fData.getUserName()+"_" + LocalDate.now().toString();

			HttpHeaders headers = new HttpHeaders();

			headers.add("Content-Type", "application/json");

			
			Map<String, String> bodyMap = new HashMap<>();
			bodyMap.put("campaignId", campaignId);
			bodyMap.put("leadName", LeadName);
			bodyMap.put("checkRcs", "N");
			bodyMap.put("phoneNumber", fData.getBlacklistMsisdn());
			bodyMap.put("templateKey", templateKey);
			bodyMap.put("templateValue", templateValue);
			bodyMap.put("uKey", ukey);

			ObjectMapper objectMapper = new ObjectMapper();
			String secondApiRequest = objectMapper.writeValueAsString(bodyMap);
			
			logger.info("secondApiRequest : "+ secondApiRequest);
						
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(secondApiRequest, headers);

			try {
				
				logger.info("Make Api Call to --> "+conversationaldata_RequestApi);
				ResponseEntity<String> responseEntity = restTemplate.exchange(
						conversationaldata_RequestApi, HttpMethod.POST, httpEntity,
						String.class);
				logger.info("Successfully calling the Above Api !!");


				Map<String, String> responseEntityMap = new HashMap<>();
				responseEntityMap.put("req", httpEntity.getBody().toString());
				responseEntityMap.put("res", responseEntity.getBody());

				return responseEntityMap;

			} catch (HttpClientErrorException | HttpServerErrorException e) {
				String excpString=e.getMessage().replace("\"", "");

				logger.error("Getting Exception During Second Api Call :  !! " + e.getStatusCode() +" "+excpString);
				this.CallToSlack(e.getMessage().replace("\"", ""),
						"Exception Genrated During Second Api Call "+ "Exception Genrated During Second Api Call For \n Username : "+fData.getUserName() +" Campaign name : "+ fData.getCampaignName()+"  blacklist_msisdn : "+ fData.getBlacklistMsisdn());
				return null;
			} catch (Exception e) {
				String excpString=e.getMessage().replace("\"", "");
				logger.error("Getting Exception During Second Api Call :  !! " + excpString);
				this.CallToSlack(e.getMessage().replace("\"", ""),
						"Exception Genrated During Second Api Call For \n Username : "+fData.getUserName() +" Campaign name : "+ fData.getCampaignName()+"  blacklist_msisdn : "+ fData.getBlacklistMsisdn());
				return null;
			}
			
		}else {
			logger.error("Please Configure Second Api !!");
			return null;
		}
		

	}

	public void CallToSlack(String error, String Description) {
		if (!conversationaldata_Request_slackUrlApi.isEmpty()) {

			String dataString ="{\"text\" :\" AppName : RCSMessageFlow - error: " + error
					+ " - Description:  " + Description + " \" }";
			
			logger.info("Inside Slack: "+ dataString);

			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				HttpEntity<Object> httpEntity = new HttpEntity<Object>(dataString, headers);
				logger.info("Trying to Generate Alert over slack !! ");
				ResponseEntity<String> response = restTemplate.exchange(conversationaldata_Request_slackUrlApi,
						HttpMethod.POST, httpEntity, String.class);
				logger.info("Alert over slack Generated Successfully !! | StatusCode: " + response.getStatusCodeValue());
				


			} catch (Exception e) {
				logger.error("Getting error During Alert Generation !!" + e.getLocalizedMessage() + " " + dataString);
			}
		}else {
			logger.info("Please Configure Slack url conversationaldata_Request_slackUrlApi");
		}
	}

	public ResponseEntity<ShortUrlDto> CallToShotUrl( String LongUrl, String msisdn, String clientid, String trackingId, String transactionId ) {

		if (!conversationaldata_Request_shortUrlApi.isEmpty()) {

			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");

				Map<String, String> bodyMap = new HashMap<>();
				bodyMap.put("msisdn", msisdn);
				bodyMap.put("longUrl", LongUrl);
				bodyMap.put("clientid", clientid);
				bodyMap.put("ua", "");
				bodyMap.put("trackingId", trackingId);
				bodyMap.put("transactionId", transactionId);
				

				HttpEntity<Object> httpEntity = new HttpEntity<Object>(bodyMap ,headers);
				
				ResponseEntity<ShortUrlDto>  shortUrlResponse= restTemplate.exchange(conversationaldata_Request_shortUrlApi,HttpMethod.POST,httpEntity, ShortUrlDto.class);
				
				return shortUrlResponse;

			} catch (HttpClientErrorException | HttpServerErrorException e) {
				String excpString=e.getMessage().replace("\"", "");
				logger.error("Getting Exception During short url creation Api Call :  !! " + e.getStatusCode() +" "+excpString);
				this.CallToSlack(e.getMessage().replace("\"", ""),
						"Exception Genrated During short url creation Api Call having : long url /n "+ LongUrl +" And trackingId : "+ trackingId + " userId :"+ clientid);
				return null;
			} catch (Exception e) {
				String excpString=e.getMessage().replace("\"", "");
				logger.error("Getting Exception During short url creation Api Call :  !! " + excpString);
				this.CallToSlack(e.getMessage().replace("\"", ""),
						"Exception Genrated During short url creation Api Call having : long url /n "+ LongUrl +" And trackingId : "+ trackingId + " userId :"+ clientid);
				return null;
			}
			
		}
		else {
			logger.info("Please Configure ShortUrl Api conversationaldata_Request_shortUrlApi ");
			return null;
		}
	}


		public LongUrlResponseDto callToEntityGetLongUrl( String UserId, String Token)
		{
			if (!conversationaldata_Request_longUrlApi.isEmpty()) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Accept", "application/json, text/plain, */*");
				headers.add("Accept-Language", " en-US,en;q=0.9");
				headers.add("Authorization", "Bearer "+Token);
				headers.add("Connection", "keep-alive");
				headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
			
			
				HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
				
				try {
					
					String longurlfinalapiString=conversationaldata_Request_longUrlApi.replace("{userId}", UserId);
					ResponseEntity<String> longUrlResponseEntity= restTemplate.exchange(longurlfinalapiString, HttpMethod.GET, httpEntity, String.class);
					ObjectMapper objectMapper = new ObjectMapper();
					LongUrlResponseDto longUrlResponseDto = objectMapper.readValue(longUrlResponseEntity.getBody(), LongUrlResponseDto.class);

					
					return longUrlResponseDto;
					
				} catch (HttpClientErrorException | HttpServerErrorException e) {
					String excpString=e.getMessage().replace("\"", "");
					logger.error("Getting Exception During Calling long url Api Call :  !! " + e.getStatusCode() +" "+excpString);
					this.CallToSlack(e.getMessage().replace("\"", ""),
							"Exception Genrated During Calling long url Api Call for Userid "+ UserId);
					return null;
				} catch (Exception e) {
					String excpString=e.getMessage().replace("\"", "");
					logger.error("Getting Exception During Calling long url Api Call :  !! " +excpString);
					this.CallToSlack(e.getMessage().replace("\"", ""),
							"Exception Genrated During Calling long url Api Call for Userid "+ UserId);
					return null;
				}
			}
			else {
				logger.info("Please Configure conversationaldata_Request_longUrlApi");
				return null;
			}
			
		}
		
		public ResponseEntity<AuthTokenResponse> CallToGetAuthToken(String username, String Password){
			
		if (!conversationaldata_Request_loginUrlApi.isEmpty()) {
			Map<String, String> authBody = new HashMap<>();
			authBody.put("username", username);
			authBody.put("password", Password);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json, text/plain, */*");
			headers.add("Accept-Language", " en-US,en;q=0.9");
			headers.add("Content-Type", "application/json");
			headers.add("Connection", "keep-alive");

			HttpEntity< Object> httpEntity = new HttpEntity<>(authBody,headers);
			
			try {
				
				ResponseEntity<AuthTokenResponse> authResponseEntity = restTemplate.exchange(conversationaldata_Request_loginUrlApi, HttpMethod.POST,httpEntity,AuthTokenResponse.class);
				
				return authResponseEntity;
				
			}catch (HttpClientErrorException | HttpServerErrorException e) {
				String excpString=e.getMessage().replace('"', ' ');
				logger.error("Getting Exception During token creation Api Call :  !! " + e.getStatusCode() +" "+excpString);
				this.CallToSlack(e.getMessage().replace('"', '\"'),
						"Exception Genrated During short url creation Api Call of /n username : "+ username +" And Password :"+ Password);
				return null;
			} catch (Exception e) {
				String excpString=e.getMessage().replace("\"", "");
				logger.error("Getting Exception During Call To GetAuthToken Api Call :  !! " + excpString);
				this.CallToSlack(e.getMessage().replace("\"", ""),
						"Exception Genrated During Call To GetAuthToken Api Call /n username : "+ username +" And Password :"+ Password);
				return null;
			}
		}	
		else {
			logger.info("Please Configure conversationaldata_Request_loginUrlApi");
			return null;
		}
			
			
		}
		
		
		
		public String getLeadGeneratedStringApiJson (FirstApiData fData, String LeadName,  String campaignId, String userId, String templateValue, String ukey) throws JsonProcessingException {
		
			
		    // Get the current instant (point in time)
	        Instant now = Instant.now();
	        
	        // Define the formatter for the ISO 8601 format
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	                                                      .withZone(ZoneOffset.UTC);
	        
	        // Format the current instant
	        String formattedTime = formatter.format(now);
	        
	        // Print the formatted time
	        System.out.println(formattedTime);
	    
	       
	        
	        
			leadInfoRoot leadBody = new leadInfoRoot();
			LeadInfoDetail leadInfoDetail = new LeadInfoDetail();
			LeadSchedule leadSchedule = new LeadSchedule();
			List<LeadInfoDetail> leadInfoDetails = new ArrayList<>(); 
			
			// leadSchedule
			leadSchedule.setScheduleDay(String.valueOf(0));
			leadSchedule.setScheduleEndDtm(this.getCurrentZoneTime());
			leadSchedule.setScheduleStartDtm(this.getCurrentZoneTime());
			leadSchedule.setWindowRequired("N");
			
			// leadInfoDetails
			leadInfoDetail.setCreatedBy(fData.getUserName());
			leadInfoDetail.setCreatedDate(this.getCurrentInstantTime());
			leadInfoDetail.setLastModifiedDate(this.getCurrentInstantTime());
			leadInfoDetail.setLastModifiedBy(fData.getUserName());
			leadInfoDetail.setPhoneNumber(fData.getBlacklistMsisdn());
			leadInfoDetail.setStatus("Created");
			leadInfoDetail.setAdditonalDataInfoText("ClickId");
			leadInfoDetail.setAdditonalDataInfoText2(templateValue);
			leadInfoDetails.add(leadInfoDetail);
			
			//leadInfoRoot
			leadBody.setCampaignId(Integer.parseInt(campaignId));
			leadBody.setLeadInfoDetails(leadInfoDetails);
			leadBody.setLeadName(LeadName);
			leadBody.setLeadSchedule(leadSchedule);
			leadBody.setUserId(userId);
			
			
			
//			{
//			    "campaignId": 91,
//			    "userId": "11",
//			    "leadName": "aug04test2",
//			    "leadSchedule": {
//			        "scheduleStartDtm": "2024-08-04T14:21:10+05:30",
//			        "windowRequired": "N",
//			        "scheduleEndDtm": "2024-08-04T14:21:10+05:30",
//			        "scheduleDay": "0"
//			    },
//			    "leadInfoDetails": [
//			        {
//			            "createdDate": "2024-08-04T08:51:10.891Z",
//			            "lastModifiedDate": "2024-08-04T08:51:10.891Z",
//			            "status": "Created",
//			            "createdBy": "Fonada",
//			            "lastModifiedBy": "Fonada",
//			            "phoneNumber": "9560424152",
//			            "additonalDataInfoText": "",
//			            "additonalDataInfoText2": null
//			        }
//			    ]
//			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			String leadJson = objectMapper.writeValueAsString(leadBody) ;
			return leadJson;
			
		}
		
		public String getCurrentInstantTime()
		{
	        // Get the current instant (point in time) in UTC
	        Instant instantNow = Instant.now();

	        // Define the formatter for the UTC format with milliseconds
	        DateTimeFormatter instantFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	                                                            .withZone(ZoneOffset.UTC);

	        // Format the current instant
	        String formattedInstantTime = instantFormatter.format(instantNow);

	        // Print the formatted time in UTC with milliseconds
	        System.out.println("Formatted DateTime (UTC with milliseconds): " + formattedInstantTime);
	        
	        return formattedInstantTime;
		}
		
		public String getCurrentZoneTime()
		{
			   // Get the current date-time in the desired time zone
	        ZonedDateTime zonedNow = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")); // Replace with your desired time zone

	        // Define the formatter for the time zone offset format
	        DateTimeFormatter zonedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

	        // Format the current date-time with time zone offset
	        String formattedZonedTime = zonedNow.format(zonedFormatter);

	        // Print the formatted time with time zone offset
	        System.out.println("Formatted DateTime (Time Zone Offset): " + formattedZonedTime);
	        
	        return formattedZonedTime;
		}
	
		
}
