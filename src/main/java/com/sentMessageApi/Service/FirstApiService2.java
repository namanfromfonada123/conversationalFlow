package com.sentMessageApi.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentMessageApi.DTO.RsponseDto;
import com.sentMessageApi.DTO.blacklistDto;
import com.sentMessageApi.DTO.callBackPojo;
import com.sentMessageApi.Modal.CallBackMsgStatusEntity;
import com.sentMessageApi.Modal.FirstApiData;
import com.sentMessageApi.Repository.CallBackMsgStatusRepository;
import com.sentMessageApi.Repository.FirstApiRepository;
import com.sentMessageApi.apiCall.RestApiCall;

@Service
public class FirstApiService2 {

	Logger logger = LoggerFactory.getLogger(FirstApiService2.class);

	@Autowired
	FirstApiRepository faRepository;
	
	@Autowired
	CallBackMsgStatusRepository callBackMsgStatusRepository;
	
	@Autowired
	ApplicationProperties aProperties;

	@Autowired
	RestApiCall restApiCall;

	
	@Autowired
	ConvObjToPojo convObjToPojo;
	
	
	public void insertDtoDataToDatabase2() {
		
		if (!aProperties.getUsername().isEmpty()) {
			
			logger.info("aProperties.getUsername().toString() :" +aProperties.getUsername().toString());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
//			formatter.setTimeZone(TimeZone.getDefault());
			
//			String start = startDate + " 00:00:00";
//			String end = endDate + " 23:59:59";
			
			String start = formatter.format(Date.from(LocalDateTime.now().minusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()));
			String end = formatter.format(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			
			
//			LocalDateTime.now().minusMinutes(2);
			
			for (Map.Entry<String, String> entry : aProperties.getUsername().entrySet()) {
				logger.info("username :" +entry.getValue() );
//				 RsponseDto rsponseDto = restApiCall.FirstapiCall(entry.getValue());
				
				 List<callBackPojo> callBackMsgStatuslist= convObjToPojo.getCallBacklist();
				
				
				   
				if (!callBackMsgStatuslist.isEmpty())
					this.SaveBlacklistData(callBackMsgStatuslist);
				else 
					logger.info("No Data Found From first Api Found ");
			}
		}
		else {
			logger.info("No User Configure to first api call");
		}
		
	
	}
	
	
	public void SaveBlacklistData(List<callBackPojo> blkDtoList) {
		logger.info(" Save FirstApi data to database!!");
		ObjectMapper objectMapper = new ObjectMapper();

		for (callBackPojo blkDto :blkDtoList) {
			
			try {
//				FirstApiData fApiData = objectMapper.convertValue(blkDto, FirstApiData.class);

//				logger.info("fApiData.getUserName() : "+fApiData.getUserName() );
//				logger.info("fApiData.getBlacklistMsisdn() : "+ fApiData.getBlacklistMsisdn());
//				
//				faRepository.saveUniqueData(fApiData.getBlacklistMsisdn(), fApiData.getDisplayText(),
//						fApiData.getMsgId(), fApiData.getTimestamp(), fApiData.getUserName(), 0,fApiData.getClickid(),
//						fApiData.getBotId(), fApiData.getCampaignName(), fApiData.getData(), fApiData.getEvent(),
//						fApiData.getLeadName(), fApiData.getStatus());
//					
				faRepository.saveUniqueData(blkDto.getBlacklist_msisdn(), blkDto.getDisplay_text(), null, null, blkDto.getCreated_by(), 0, null, blkDto.getBot_id(), blkDto.getCampaign_name(), null, null, blkDto.getLead_name(), null);
				
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}

		}
	}
}
