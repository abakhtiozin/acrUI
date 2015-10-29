package main.java;

import static com.codeborne.selenide.Selenide.executeJavaScript;

import javax.naming.TimeLimitExceededException;

import main.java.model.restriction.Limit;

import com.codeborne.selenide.Selenide;


/**
 * Custom conditions for waiting
 * @author Alexander.Isko@viaamadeus.com
 */
public interface CustomConditions extends Limit {
	
	/********* CUSTOM CONDITIONS *********/
	public static CustomConditions ajaxCompleted = () -> executeJavaScript("return Ajax.activeRequestCount == 0 && jQuery.active == 0;");
	
	
	/*************************************/
	
	
	
	
	
	
	/********* CUSTOM CONDITIONS CHECKING METHODS *********/	
	
	/**
	 * The method waits until the condition isn't met with the default max wait time ({@link Limit}.ELM_VISIBILITY_MAX_WAIT_TIME).
	 * @param condition {@link CustomConditions}
	 * @return true if the condition is met 
	 * @throws TimeLimitExceededException when max wait time is exceeded
	 */
	public static boolean waitFor(CustomConditions condition) throws TimeLimitExceededException {
		return waitFor(condition, ELM_VISIBILITY_MAX_WAIT_TIME);
	}	
	
	/**
	 * The method waits until the condition isn't met.
	 * @param condition {@link CustomConditions}
	 * @param timeout	the max wait time
	 * @return	true if the condition is met 
	 * @throws TimeLimitExceededException when max wait time is exceeded
	 */
	public static boolean waitFor(CustomConditions condition, long timeout) throws TimeLimitExceededException {
		final long pauseTime = 200;
		long startTime = System.currentTimeMillis();		
		while (System.currentTimeMillis() < startTime + timeout) {
			if (condition.check()) return true;
			Selenide.sleep(pauseTime);			
		}
		throw new TimeLimitExceededException("Wait time of " + timeout + "ms. for the condition " + condition.toString() + " is exceeded; ");
		
	}
	
	/*****************************************************/
	
	
	boolean check();
}
