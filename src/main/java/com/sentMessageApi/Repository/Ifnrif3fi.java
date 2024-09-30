package com.sentMessageApi.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class Ifnrif3fi {

	public static void main(String[] args) {
		
		String paisaUserName="PaisabazaarPrsnl,PaisabazzarPro,Paisabazaaroffers,Affipedia";
			String	paisaShorturl="https://pb2.psbzr.in/";
			String shortDomain="";
			String shorturl ="http://qz6.in/abxgcv";
		String username = "PaisabazaarPrsnl";
			
		if (paisaUserName.contains(username)) {
			System.out.println(paisaShorturl);
			shortDomain = shorturl.replace("http://qz6.in/", paisaShorturl);
			System.out.println("shortDomain : "+shortDomain);

		} else {
			
			System.out.println("shorturl : "+shorturl);

		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				
		String start = formatter.format(Date.from(LocalDateTime.now().minusMonths(11).atZone(ZoneId.systemDefault()).toInstant()));
		String end = formatter.format(Date.from(LocalDateTime.now().plusMonths(1).minusDays(7).atZone(ZoneId.systemDefault()).toInstant()));

		
		System.out.println("end :"+end);
		System.out.println("Start :" + start);
		
	}
}
