package com.sentMessageApi.Controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentMessageApi.Service.SummaryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*", maxAge = 3600)
@RequestMapping("/sentMessage/Api")
public class SummaryController {

	@Autowired
	SummaryService summaryService;
	
	@GetMapping(value =  "/getsmsSummaryData", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getSms_summary_data(@RequestParam String Startdate, @RequestParam String Enddate)
	{
		
		try {
			return (new ObjectMapper()).writeValueAsString(summaryService.smsSummaryService(Startdate,Enddate)) ;
		} catch (ParseException e) {
			
			return e.getLocalizedMessage();
		} catch ( Exception e) {
		
		return e.getLocalizedMessage();
		}
	}
	
	@GetMapping(value =  "/getobdSummaryData", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getObd_summary_data(@RequestParam String Startdate, @RequestParam String Enddate)
	{
		
		try {
			return (new ObjectMapper()).writeValueAsString(summaryService.obdSummaryService(Startdate, Enddate)) ;
		} catch (ParseException e) {
			
			return e.getLocalizedMessage();
		} catch (Exception e) {
		
		return e.getLocalizedMessage();
		}
	}
	
}
