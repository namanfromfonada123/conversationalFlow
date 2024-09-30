package com.sentMessageApi.Service;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sentMessageApi.DTO.AuthTokenResponse;
import com.sentMessageApi.DTO.LongUrlResponseDto;
import com.sentMessageApi.DTO.ShortUrlDto;
import com.sentMessageApi.DTO.longUrlDto;
import com.sentMessageApi.Modal.FirstApiData;
import com.sentMessageApi.Modal.MessageDetails;
import com.sentMessageApi.Modal.SecondApiData;
import com.sentMessageApi.Repository.FirstApiRepository;
import com.sentMessageApi.Repository.MessageRepository;
import com.sentMessageApi.Repository.SecondApiRepository;
import com.sentMessageApi.apiCall.RestApiCall;
import com.sentMessageApi.utility.RegexFilter;

@Service
public class SecondApiService {

	@Value("${paisaUserName}")
	private String paisaUserName;
	@Value("${user.shorturl}")
	private String paisaShorturl;
	
	
	Logger logger = LoggerFactory.getLogger(SecondApiService.class);

	@Autowired
	FirstApiRepository faRepository;

	@Autowired
	SecondApiRepository saRepository;

	@Autowired
	ApplicationProperties aProperties;

	@Autowired
	MessageRepository mRepository;

	@Autowired
	RestApiCall restApiCall;

	public void FetchAndCall() throws JsonProcessingException, UnsupportedEncodingException {
		List<FirstApiData> faData = faRepository.findByFlag(0);
		Map<String, String> ukeyMap = aProperties.getUkey();
		Map<String, String> authPassMap = aProperties.getAuthPass();
		Map<String, String> authUserMap = aProperties.getAuthUser();
		Map<String, String> chatcampaignid = aProperties.getChatcampaignid();
		Map<String, String> shorturldomainMap = aProperties.getShorturldomain();

		if (!faData.isEmpty()) {
			for (FirstApiData fData : faData) {
				String ukey = "";
				String campaignId = "";
				String templateValue = "";

				Optional<MessageDetails> mDetailsList = mRepository.findMessageDetails(fData.getCampaignName(),
						fData.getUserName());

				MessageDetails mDetails = null;

				if (mDetailsList.isPresent()) {
					logger.info("Message details of user: " + fData.getUserName() + " of message details : "
							+ mDetailsList.toString());
					mDetails = mDetailsList.get();
				}

				if (mDetails != null) {

					// 1. Api Call to get Jwt token
					ResponseEntity<AuthTokenResponse> authTokenRes = restApiCall.CallToGetAuthToken(
							authUserMap.get(fData.getUserName()), authPassMap.get(fData.getUserName()));

					if (authTokenRes != null) {

						String jwtToken = authTokenRes.getBody().getResult().getToken();
						logger.info("Successfully getting AuthToken : " + jwtToken);

						// 2. Api call to get long url
						LongUrlResponseDto longUrlDtoResponse = restApiCall.callToEntityGetLongUrl(mDetails.getUserId(),
								jwtToken);

						logger.info("longUrl data :" + longUrlDtoResponse.toString());

						// 3. Api Call to get short url based on long urls( find above)
						templateValue = mDetails.getText();

						if (longUrlDtoResponse.getData() != null) {
							logger.info("Successfully getting long url : ");

							longUrlDto[] lUrlDtosList = longUrlDtoResponse.getData();

							// Extract trackingIds from the textMessage(of messageDetails)
							List<String> trackingIdList = RegexFilter.extractFromString("\\[\\d{16}\\]",
									mDetails.getText());
							// map to accumlate longurls vs trackingids
							Map<String, String> trackingIDVsLongUrl = new HashMap<>();

							// loop for find long url having tracking ids same as trackingids in the text
							// message
							for (longUrlDto lud : lUrlDtosList) {
								for (String trackingId : trackingIdList) {
									if (lud.getTrackingId().equals(trackingId.replace("[", "").replace("]", ""))) {
										String longUrl = lud.getUrl();

										if (longUrl != null) {
											longUrl = longUrl.replace("{clickid}", fData.getClickid());

											trackingIDVsLongUrl.put(trackingId, longUrl);
											break;
										}

									}
								}

							}

							logger.info("long url vs trackingid " + trackingIDVsLongUrl.toString());

							for (Map.Entry<String, String> entry : trackingIDVsLongUrl.entrySet()) {

								String trackingIdString = entry.getKey().replace("[", "").replace("]", "");
								ResponseEntity<ShortUrlDto> shortUrlDtoeEntity = restApiCall.CallToShotUrl(
										entry.getValue(), fData.getBlacklistMsisdn(), mDetails.getUserId(),
										trackingIdString, fData.getData());

								if (shortUrlDtoeEntity != null) {
									String shorturl = shortUrlDtoeEntity.getBody().getShortUrl();
									String shortDomain = "";
									if (paisaUserName.contains(fData.getUserName())) {
										System.out.println(paisaShorturl);
										shortDomain = shorturl.replace("http://qz6.in/", paisaShorturl);
										System.out.println(shortDomain);
										templateValue = templateValue.replace(entry.getKey(), shortDomain);

									} else {
										templateValue = templateValue.replace(entry.getKey(), shorturl);

									}

									logger.info("Longurl: " + entry.getValue() + " " + " shorturl :" + shorturl);

								}
							}

						} else {
							logger.info("No longUrl data found for this url");

						}

						// assign campaignID extract from mDetails
//						campaignId = mDetails.getCampaignid();
						campaignId = chatcampaignid.get(fData.getUserName());

						// assign ukey taken from the application properties
						ukey = ukeyMap.get(fData.getUserName());

						// 4. call userApi At last with fdata campaignId, templateValue, ukey

						logger.info("Template Value :" + templateValue);

						templateValue = templateValue.replace("\r", "").replaceAll("[\r\n]+", "\n");
						templateValue = URLEncoder.encode(templateValue, StandardCharsets.UTF_8.toString()).replace("%0A", "%5Cn");
						
//						*********************************************************************************************************
						
						if (fData.getUserName().equals("Affipedia")) {
							
							
							if (fData.getDisplayText().equals("Below 25L")||fData.getDisplayText().equals("25L-50L")||fData.getDisplayText().equals("51L-1Cr")||fData.getDisplayText().equals("Above 1Cr")) {
								
								campaignId = chatcampaignid.get("Affipedia");
								
								Map<String, String> Resreq = restApiCall.SecondapiCall(fData, campaignId, "chat", templateValue, ukey);
								if (Resreq != null) {
									//	logger.info("Saving request and response data to second api table !!");
									this.saveReqRes(Resreq.get("req"), Resreq.get("res"), fData);
								}
								
							}
							else {
								
								if (fData.getDisplayText().equals("Interested")||fData.getDisplayText().equals("Yes I'm Interested")) {
									campaignId = chatcampaignid.get("Affipedia_Interested");
									Map<String, String> Resreq = restApiCall.SecondapiCall(fData, "2015", "clickid", "123", ukey);
									if (Resreq != null) {
										//	logger.info("Saving request and response data to second api table !!");
										this.saveReqRes(Resreq.get("req"), Resreq.get("res"), fData);
									}
								}else {
									Map<String, String> Resreq = restApiCall.SecondapiCall(fData, campaignId,"chat", templateValue, ukey);
									if (Resreq != null) {
										//	logger.info("Saving request and response data to second api table !!");
										this.saveReqRes(Resreq.get("req"), Resreq.get("res"), fData);
									}	
								}
								
								
							}
							
							
							

							
							
							

							
						}
						else {
							
							Map<String, String> Resreq = restApiCall.SecondapiCall(fData, campaignId,"chat", templateValue, ukey);
							if (Resreq != null) {
								//	logger.info("Saving request and response data to second api table !!");
								this.saveReqRes(Resreq.get("req"), Resreq.get("res"), fData);
							}							
						}
						
//						*********************************************************************************************************
//						Map<String, String> Resreq = restApiCall.SecondapiCall(fData, campaignId, templateValue, ukey);
//						if (Resreq != null) {
//							//	logger.info("Saving request and response data to second api table !!");
//							this.saveReqRes(Resreq.get("req"), Resreq.get("res"), fData);
//						}
						

					} // token api result checking if not null
					else {
						logger.info("Unable to get token for this user");
						fData.setFlag(1);
						faRepository.save(fData);
						continue;
					}
				} else {
					logger.info("No message details found for this Campaign: " + fData.getCampaignName());
					fData.setFlag(1);
					faRepository.save(fData);

				}

			}
		}

	}

	public void saveReqRes(String req, String res, FirstApiData fData) {
		try {
			fData.setFlag(1);
			faRepository.save(fData);

			SecondApiData saData = new SecondApiData();
			saData.setRequestData(req);
			saData.setResponseData(res);

			saRepository.save(saData);
			logger.info("Saving Request and Reponse of second api call to database Successfully !!");

		} catch (Exception e) {
			logger.info(e.getLocalizedMessage());
			restApiCall.CallToSlack(e.getLocalizedMessage(),
					"Exception Generated While Updating the FirstApiTable And Saving the response with request in SecondApiTable ");
		}

	}

}
