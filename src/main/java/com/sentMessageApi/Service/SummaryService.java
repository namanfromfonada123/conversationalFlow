package com.sentMessageApi.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sentMessageApi.DTO.ObdSummary;
import com.sentMessageApi.DTO.SmsSummary;
import com.sentMessageApi.Modal.Obd_summary_data;
import com.sentMessageApi.Modal.Sms_summary_data;
import com.sentMessageApi.Repository.SMSsummaryRepository;
import com.sentMessageApi.Repository.obdSummaryRepository;

@Service
public class SummaryService {

	@Autowired
	SMSsummaryRepository smSsummaryRepository;
	
	@Autowired
	obdSummaryRepository obdSummaryRepository;
	
	public List<SmsSummary> smsSummaryService(String Startdate, String Enddate) throws ParseException
	{
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date Sdate = formate.parse(Startdate);
		Date Edate = formate.parse(Enddate);
		
		Optional<List<Sms_summary_data>> sms_summary_data_list = smSsummaryRepository.findByDate(Sdate,Edate);
		List<SmsSummary>  smsSummaries = new ArrayList<>() ;
		
		if (sms_summary_data_list.isPresent()) {
			for(Sms_summary_data sms_summary_data : sms_summary_data_list.get() )
			{
				SmsSummary smsSummary = new SmsSummary();
				smsSummary.setId(sms_summary_data.getId());
				smsSummary.setValue(sms_summary_data.getValue());
				smsSummary.setDelivered(sms_summary_data.getDelivered());
				smsSummary.setSent_count(sms_summary_data.getSent_count());
				smsSummary.setDate(formate.format(sms_summary_data.getDate()));
				
				smsSummaries.add(smsSummary);
			}
		}
		
		return smsSummaries;
		
	}
	
	public List<ObdSummary> obdSummaryService(String Startdate, String Enddate) throws ParseException
	{
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date Sdate = formate.parse(Startdate);
		Date Edate = formate.parse(Enddate);
		
		 Optional<List<Obd_summary_data>> obd_summary_data_List = obdSummaryRepository.findByDate(Sdate,Edate);
		 List<ObdSummary>  obdSummaries = new ArrayList<>() ;
		 
		 
		 if (obd_summary_data_List.isPresent()) {
				for(Obd_summary_data obd_summary_data : obd_summary_data_List.get() )
				{
					 ObdSummary obdSummary = new ObdSummary();

					 obdSummary.setId(obd_summary_data.getId());
					 obdSummary.setValue(obd_summary_data.getValue());
					 obdSummary.setConnected(obd_summary_data.getConnected());
					 obdSummary.setCredit_used(obd_summary_data.getCredit_used());
					 obdSummary.setValid_number(obd_summary_data.getValid_number());
					 obdSummary.setDate(formate.format(obd_summary_data.getDate()));
					 obdSummaries.add(obdSummary);
				}
			}
		 
		return obdSummaries;
		
	}
}
