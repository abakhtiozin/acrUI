package main.java.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import test.java.base.TestHelper;
import test.java.base.TestHelper.Log;

/**
 * Created by trofimenko on 15.10.2015.
 */
public class DateUtils {
	
	public static String getHumanReadebleDate(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
    public static boolean compareDate(Date date1, Date date2, long deviation){
    	if (Math.abs(date1.getTime() - date2.getTime()) <= deviation) {
    		return true;
    	}
    	else {
    		Log.debug("The dates are not equal within the deviation value:: Date1: " + getHumanReadebleDate(date1) + "; Date2: " + getHumanReadebleDate(date2));
    		return false;
    	}
    }

	public static Date convertToDate(String format, String strDate){
		Date date = null;
		SimpleDateFormat inFormat = new SimpleDateFormat(format);
		try {
			date = inFormat.parse(strDate);
		} catch (ParseException e) {
			TestHelper.Log.error("Couldn't parse date: format in unacceptable; ");
		} finally {
			return date;
		}
	}
}
