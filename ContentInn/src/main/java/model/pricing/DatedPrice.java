package main.java.model.pricing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import test.java.base.TestHelper;


public class DatedPrice extends Price {
    private Date date;

    private DateFormat getDateFormatter(String pattern) {
    	return new SimpleDateFormat(pattern);
    }    
    
    public DatedPrice(Date date, Price price) {
		this(date, price.amount(), price.currency());
	}
    public DatedPrice(Date date, double amount, Currency currency){
    	super(amount, currency);
    	this.date = date;
    }
    public DatedPrice(String date, String amount, String currIsoCode, String datePattern){
    	super(amount, currIsoCode);
    	try {
			this.date = getDateFormatter(datePattern).parse(date);
		} catch (ParseException e) {
			TestHelper.Log.fatal("DatedPrice: Couldn't parse date (" + date + ") with pattern " + datePattern + ": " + e);
			e.printStackTrace();
		}
    }
    
    // Getters
    public Date date(){
        return date;
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof DatedPrice) {
    		return this.date.equals(((DatedPrice) obj).date) && super.equals(obj);    				
    	}
    	return false;
    }
    
    @Override
    public String toString() {
    	return super.toString() + "@" + getDateFormatter("yyyy-MM-dd HH:mm:ss").format(date());
    }
    
}
