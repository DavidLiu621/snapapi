package com.snap.restapi.util;

import org.apache.commons.validator.routines.UrlValidator;

public class Utility {

	public static boolean isValidUrl(String url) {
		String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
		UrlValidator urlValidator = new UrlValidator(schemes);
		return urlValidator.isValid(url);
	}
	
	public static String numberStrToLetterStr(String numberStr){
		return numberStr.replace("0", "a").replace("1", "b").replace("2", "c").replace("3", "d")
				.replace("4", "e").replace("5", "f").replace("6", "g").replace("7", "X").replace("8", "Y")
				.replace("9", "Z");
		
	}
}
