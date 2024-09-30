package com.sentMessageApi.DTO;

import lombok.Data;

@Data
public class callBackPojo {

	private String bot_id;
	private String template_code;
	private long blackListid;
	private String blacklist_msisdn;
	private String created_by;
	private String lead_name;
	private String campaign_name;
	private String campaign_type;
	private String data_source_name;
	private String additional_data_info_text;
	private String additional_data_info_text2;
	private String description;
	private String display_text;
	
	
	
	
	
	public callBackPojo(String bot_id, String template_code, long blackListid, String blacklist_msisdn,
			String created_by, String lead_name, String campaign_name, String campaign_type, String data_source_name,
			String additional_data_info_text, String additional_data_info_text2, String description,
			String display_text) {
		super();
		this.bot_id = bot_id;
		this.template_code = template_code;
		this.blackListid = blackListid;
		this.blacklist_msisdn = blacklist_msisdn;
		this.created_by = created_by;
		this.lead_name = lead_name;
		this.campaign_name = campaign_name;
		this.campaign_type = campaign_type;
		this.data_source_name = data_source_name;
		this.additional_data_info_text = additional_data_info_text;
		this.additional_data_info_text2 = additional_data_info_text2;
		this.description = description;
		this.display_text = display_text;
	}





	public callBackPojo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
