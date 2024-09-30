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
import com.sentMessageApi.Modal.CallBackMsgStatusEntity;
import com.sentMessageApi.Modal.FirstApiData;
import com.sentMessageApi.Repository.CallBackMsgStatusRepository;
import com.sentMessageApi.Repository.FirstApiRepository;
import com.sentMessageApi.apiCall.RestApiCall;

@Service
public class FirstApiService {

	Logger logger = LoggerFactory.getLogger(FirstApiService.class);

	@Autowired
	FirstApiRepository faRepository;
	
	@Autowired
	CallBackMsgStatusRepository callBackMsgStatusRepository;
	
	@Autowired
	ApplicationProperties aProperties;

	@Autowired
	RestApiCall restApiCall;

	public void insertDtoDataToDatabase() {
		
		if (!aProperties.getUsername().isEmpty()) {
			
			logger.info("aProperties.getUsername().toString() :" +aProperties.getUsername().toString());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			formatter.setTimeZone(TimeZone.getDefault());
			
			String start = formatter.format(Date.from(LocalDateTime.now().minusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()));
			String end = formatter.format(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			
			
			
			for (Map.Entry<String, String> entry : aProperties.getUsername().entrySet()) {
				logger.info("username :" +entry.getValue() );
//				 RsponseDto rsponseDto = restApiCall.FirstapiCall(entry.getValue());
				
				 List<CallBackMsgStatusEntity> callBackMsgStatuslist= callBackMsgStatusRepository.findByTextMessageNotNullAndIsCompleteIsOne(entry.getValue(), start, end);
				
				
				   
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
	
	
	public void SaveBlacklistData(List<CallBackMsgStatusEntity> blkDtoList) {
		logger.info(" Save FirstApi data to database!!");
		ObjectMapper objectMapper = new ObjectMapper();

		for (CallBackMsgStatusEntity blkDto :blkDtoList) {
			
			try {
				FirstApiData fApiData = objectMapper.convertValue(blkDto, FirstApiData.class);

	
				faRepository.saveUniqueData(fApiData.getBlacklistMsisdn(), fApiData.getDisplayText(),
						fApiData.getMsgId(), fApiData.getTimestamp(), fApiData.getUserName(), 0,fApiData.getClickid(),
						fApiData.getBotId(), fApiData.getCampaignName(), fApiData.getData(), fApiData.getEvent(),
						fApiData.getLeadName(), fApiData.getStatus());
				
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}

		}
	}
}
