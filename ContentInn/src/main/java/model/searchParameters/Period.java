package main.java.model.searchParameters;


import java.text.*;
import java.util.Calendar;


public class Period {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private Calendar checkInDate = Calendar.getInstance();;
	private Calendar checkOutDate = Calendar.getInstance();
	private long nightsCount;
	
	
	/* CONSTRUCTORS */
	
	public Period (Calendar checkIn, Calendar checkOut) {
		checkInDate = checkIn;
		checkOutDate = checkOut;		
		nightsCount = (checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis()) / 1000 / 60 / 60 / 24;		
	}
	
	public Period (Calendar checkIn, int nights) {
		checkInDate = checkIn;
		nightsCount = nights;
		checkOutDate.setTime(checkInDate.getTime());
		checkOutDate.add(Calendar.DATE, (int)nightsCount);
	}
	
	public Period (String checkIn, String checkOut) {
		try {
			checkInDate.setTime(dateFormatter.parse(checkIn));
			checkOutDate.setTime(dateFormatter.parse(checkOut));
			nightsCount = (checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis()) / 1000 / 60 / 60 / 24;
		}
		catch (ParseException e) {
			System.out.println("Couldn't parse date: " + e);
		}
	}
	
	public Period (String checkIn, int nights) {
		try {
			checkInDate.setTime(dateFormatter.parse(checkIn));
			nightsCount = nights;
			checkOutDate.setTime(checkInDate.getTime());
			checkOutDate.add(Calendar.DATE, (int)nightsCount);			
		}
		catch (ParseException e) {
			System.out.println("Couldn't parse date: " + e);
		}
	}
	
	public String checkInDate() {
		return dateFormatter.format(checkInDate.getTime());
	}
	
	public String checkInDate(String format) {
		dateFormatter = new SimpleDateFormat(format);
		return checkInDate();
	}
	
	public String checkOutDate() {
		return dateFormatter.format(checkOutDate.getTime());
	}
		
	public String checkOutDate(String format) {
		dateFormatter = new SimpleDateFormat(format);
		return checkOutDate();
	} 
	
	public String nights() {
		return Long.toString(nightsCount);
	}
	
	
	@Override
	public String toString() {
		return checkInDate() + " : " + nightsCount + " : " + checkOutDate();	
	}
	
}
