package com.sentMessageApi.utility.pojos;

import java.util.Date;

import lombok.Data;

@Data
public class LeadInfoDetail {
    public String createdDate;
    public String lastModifiedDate;
    public String status;
    public String createdBy;
    public String lastModifiedBy;
    public String phoneNumber;
    public String additonalDataInfoText;
    public Object additonalDataInfoText2;
}
