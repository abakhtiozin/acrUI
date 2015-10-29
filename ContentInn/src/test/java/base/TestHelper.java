package test.java.base;


import java.text.SimpleDateFormat;
import java.util.*;

import main.java.model.Traveler;
import main.java.model.Traveler.Titulation;

import org.testng.Assert;
import static util.other.RandomDataGenerator.*;


public class TestHelper extends Assert{

	public static class Log {
		public static final int TRACE		= -1;
		public static final int DEBUG		= 0;
		public static final int INFO		= 1;
		public static final int WARNING		= 2;
		public static final int ERROR		= 3;
		public static final int FATAL		= 4;
		private static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
		private static int logLevel = DEBUG;	// Default level
		
		private static String levelToString(int level, boolean align) {
			String				strLevel = "[UNKNOWN] ";
			switch (level) {
				case TRACE:		strLevel = "[TRACE]   "; break;
				case DEBUG:		strLevel = "[DEBUG]   "; break;
				case INFO:		strLevel = "[INFO]    "; break;
				case WARNING:	strLevel = "[WARNING] "; break;
				case ERROR:		strLevel = "[ERROR]   "; break;
				case FATAL:		strLevel = "[FATAL]   "; break;
				default:		strLevel = "[UNKNOWN] "; break;
			}
			return align ? strLevel : strLevel.trim();
		}
		private static void printMessage(int level, String message) {
			System.out.println(compose(level, message));						
		}		
		public static boolean setLogLevel(int level) {
			boolean st = false;
			if (level >= TRACE && level <= FATAL) {
				logLevel = level;
				st = true;
			}
			else {
				printMessage(ERROR, "Log level is out of range and couldn't be set. Allowed options from Log.TRACE to Log.FATAL");
				st = false;
			}
			return st;
		}		
		public static int getLogLevel() {
			return logLevel;
		}
		
		public static String compose(int level, String message) {
			String date = df.format(new Date());
			String outMessage = levelToString(level, true) + date + " | " + message;
			return outMessage;
		}
		
		
		public static void trace(Object message) {
			if (TRACE >= getLogLevel())		{	printMessage(TRACE, message.toString());	}
		}
		public static void debug(Object message) {
			if (DEBUG >= getLogLevel())		{	printMessage(DEBUG, message.toString());	}
		}		
		public static void info(Object message) {
			if (INFO >= getLogLevel())		{	printMessage(INFO, message.toString());	}
		}		
		public static void warning(Object message) {
			if (WARNING >= getLogLevel())	{	printMessage(WARNING, message.toString());	}
		}		
		public static void error(Object message) {
			if (ERROR >= getLogLevel())		{	printMessage(ERROR, message.toString());	}
		}		
		public static void fatal(Object message) {
			if (FATAL >= getLogLevel())		{	printMessage(FATAL, message.toString());	}
		}		
	}
	
	
	protected Traveler[][] generateRndTravelers(int roomsAmount, int adultsAmount, int childAge) {
		int travelersAmt = adultsAmount + (childAge > 0 ? 1 : 0);
		Traveler[][] offerTravelers = new Traveler[roomsAmount][travelersAmt];
		for (int i = 0; i < roomsAmount; i++) {			
			for (int j = 0; j < travelersAmt; j++) {				
				offerTravelers[i][j] = new Traveler(false, Titulation.getRandom(), "QA-" + getRandomLiteralsString(5, 10), "QA-" + getRandomLiteralsString(5, 10), getRandomEmail("qa@automation.test"), getRandomNumericString(10));
			}
			
		}		
		return offerTravelers;
	}
}
