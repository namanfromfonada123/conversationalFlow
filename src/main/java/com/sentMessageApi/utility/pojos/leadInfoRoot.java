package com.sentMessageApi.utility.pojos;

import java.util.List;

import lombok.Data;

@Data
public class leadInfoRoot {
	  public int campaignId;
	    public String userId;
	    public String leadName;
	    public LeadSchedule leadSchedule;
	    public List<LeadInfoDetail> leadInfoDetails;
}

