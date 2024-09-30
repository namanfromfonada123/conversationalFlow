package com.sentMessageApi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sentMessageApi.DTO.CallBackReq;
import com.sentMessageApi.Repository.FirstApiRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*", maxAge = 3600)
@RequestMapping("/sentMessage/Api")
public class CallBackController {

	@Autowired
	FirstApiRepository faRepository;
	
	@PostMapping("/saveCallback")
	public void storeCallBack(@RequestBody CallBackReq callBack )
	{
		System.out.println("callback : "+ callBack);
		
		faRepository.saveUniqueData(callBack.getBlacklistMsisdn(), callBack.getDisplayText(),
				callBack.getMsgId(), callBack.getTimestamp(), callBack.getUserName(), 0, callBack.getClickid(),
				callBack.getBotId(), callBack.getCampaignName(), callBack.getData(), callBack.getEvent(), callBack.getLeadName(), callBack.getStatus());
				
	}
}
