package com.sentMessageApi.utility.pojos;

import java.util.Date;

import lombok.Data;

@Data
public class LeadSchedule {

    public String scheduleStartDtm;
    public String windowRequired;
    public String scheduleEndDtm;
    public String scheduleDay;
}
