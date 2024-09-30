package com.sentMessageApi.Service;

import java.util.List;

import org.ietf.jgss.Oid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sentMessageApi.Repository.CallBackMsgStatusRepository;
import com.sentMessageApi.Repository.blackListRepository;

@Component
public class Schedular {

	@Autowired
	FirstApiService firstApiService;
	
	@Autowired
	SecondApiService secondApiService;
	
	@Autowired
	CallBackMsgStatusRepository callBackMsgStatusRepository;
	
	
	Logger logger = LoggerFactory.getLogger(Schedular.class);
	
	
	
	@Scheduled(fixedDelayString = "${FistApi.SchedularTime.milli}" )
	public void FirstApi()
	{
		firstApiService.insertDtoDataToDatabase();
	}
	
	@Scheduled(fixedDelay = 5000)
	public void SecondApi() 
	{ 
		try {
			secondApiService.FetchAndCall();

		} catch (Exception e) {

			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
//	@Scheduled(fixedDelay = 10000000)
//	public void getSchedule()
//	{
//		List<Object[]> cList = callBackMsgStatusRepository.findByTextMessageNotNullAndIsCompleteIsOne2();
//		for(Object obj: cList)
//		{
//			System.out.println((String)obj);
//		}
//	}
}




