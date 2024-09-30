package com.sentMessageApi.Modal;

import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "demo_rcs.blacklist")
@Data
public class blacklist
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String blacklist_msisdn;
	public String created_date;
	public String data;
	public String display_text;
	public String event;
	public String msg_id;
	public String text_message;
	public Timestamp timestamp;
	public String status;
	public String bot_id;
	public String campaign_name;
	public String campaign_type;
	public String data_source_name;
	public String is_complete;
	public String lead_name;
	public String template_code;
	public String user_name;
	public int send_dlr;
	public String clickid;
	public String request;
	public String response;
	public String update_callback;

}