package com.sentMessageApi.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sentMessageApi.DTO.callBackPojo;
import com.sentMessageApi.Repository.CallBackMsgStatusRepository;
import com.sentMessageApi.Repository.blackListRepository;

@Component
public class ConvObjToPojo {

	@Autowired
	CallBackMsgStatusRepository callBackMsgStatusRepository;

	public List<callBackPojo> getCallBacklist() {
	        List<Object[]> results = callBackMsgStatusRepository.findByTextMessageNotNullAndIsCompleteIsOne2();
	        List<callBackPojo> callBackPojos = new ArrayList<>();
	        
	   	

	        for(Object[] row : results )
	        {
	        	callBackPojo cBackPojo = new callBackPojo(
		                (String) row[0],//bot_id;
		                (String) row[1],//template_code
		                ((Long)row[2]).longValue(),//blackListid
		                (String) row[3],//blacklist_msisdn
		                (String) row[4],//created_by
		                (String) row[5],//lead_name
		                (String) row[6],//campaign_name
		                (String) row[7],//campaign_type
		                (String) row[8],//data_source_name
		                (String) row[9],//additional_data_info_text
		                (String) row[10],//additional_data_info_text2
		                (String) row[11],//description
		                (String) row[12] //display_text
		            );
	        	callBackPojos.add(cBackPojo);       
				
	        }

	        return callBackPojos;
	    }

}
