package com.opentrafficsimulation.utility;

import com.opentrafficsimulation.utility.constants.AppConstants;

public class SumoUtil {
	public static String getSumoFile(String fileName) {
		return AppConstants.SUMO_PATH + "/" + fileName;
	}
}
