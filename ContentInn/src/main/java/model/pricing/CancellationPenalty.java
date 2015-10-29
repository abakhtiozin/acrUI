package main.java.model.pricing;

import java.util.Date;
import test.java.base.TestHelper;
import main.java.model.Locale;


public class CancellationPenalty extends DatedPrice {

	
	private static String getDatePattern(Locale locale) {
		switch (locale) {
			case en:	return "yyyy-MM-dd HH:mm:ss"; 
			case uk:	return "dd.MM.yy HH:mm:ss";
			case ru:	return "dd.MM.yy HH:mm:ss";
			case fr:	return "dd/MM/yy HH:mm:ss";
	
			default:	return "yyyy-MM-dd HH:mm:ss";
		}
	}    
    private static String composeTime(String strDate) {
    	strDate = strDate.trim();
    	TestHelper.Log.trace("CancellationPenalty.getDateFromStr input string date: " + strDate);
    	if (strDate.indexOf(" ") > 0) {
    		String strTime = strDate.substring(strDate.indexOf(" "));
    		if (strTime.trim().length() <= 5 ) {
    			strDate += ":00";
    		}
    	}
    	else {
    		strDate += " 00:00:00";
    	}
    	TestHelper.Log.trace("CancellationPenalty.getDateFromStr converted string date: " + strDate);
    	return strDate;
	}
       
    
    public CancellationPenalty(Date date, double amount, Currency currency ){
    	super(date, amount, currency);
    }
    public CancellationPenalty(String date, String amount, String currIsoCode, Locale locale){
    	super(composeTime(date), amount, currIsoCode, getDatePattern(locale));
    }        
    public CancellationPenalty(String date, String amount, String currIsoCode){
        this(date, amount, currIsoCode, Locale.en);	
    }
    
}
