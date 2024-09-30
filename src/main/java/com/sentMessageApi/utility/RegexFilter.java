package com.sentMessageApi.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFilter {


	public static List<String> extractFromString(String regex, String input)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		List<String> captureList = new ArrayList<>();
		while (matcher.find()) {
			captureList.add(matcher.group(0));
		}
		return captureList;
	}
	
	public static String ReplaceTrackingIdWithShortUrl(String trackingID , String input, String ShortUrl)
	{
		return input.replace(trackingID, ShortUrl);
	}
	
}
