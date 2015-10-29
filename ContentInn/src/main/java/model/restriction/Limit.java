package main.java.model.restriction;

public interface Limit {
	public static final byte MIN = 0;
	public static final byte MAX = 1;
	
	/* Time conversion */
	public static final long SECOND	= 1000;			//milliseconds
	public static final long MINUTE	= 60 * SECOND;
	public static final long HOUR	= 60 * MINUTE;
	
	public static long LONG_WAIT_TIME	= 5 * MINUTE;
	public static long MIDDLE_WAIT_TIME	= 3 * MINUTE;
	public static long SHORT_WAIT_TIME	= 1 * MINUTE;
	
	public static long SEARCH_MAX_WAIT_TIME	= MIDDLE_WAIT_TIME;
	public static long BOOK_MAX_WAIT_TIME	= LONG_WAIT_TIME;
	public static long ELM_VISIBILITY_MAX_WAIT_TIME	= (long) (0.5 * MINUTE);
	
	public static long AJAX_EXECUTION_MAX_TIME = 5 * MINUTE;
	
	
}
