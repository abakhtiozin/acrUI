package util.other;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Andrii.Bakhtiozin on 19.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class RandomDataGenerator {
    private static Random random = new Random();

    public static String getRandomLiteralsString(int min, int max) {
        return getRandomString(randomNumber(min, max));
    }

    public static String getRandomLiteralsRussianString(int min, int max) {
        return getRandomRussianString(randomNumber(min, max));
    }

    public static String getRandomLiteralsString() {
        return getRandomString(randomNumber(4, 9));
    }

    public static String getRandomEmail() {
        return getRandomString(randomNumber(4, 9)) + "@test.com";
    }

    public static String getRandomEmail(String customDomain) {
        int atIndex = customDomain.indexOf("@");
        if (atIndex == 0) {
            return getRandomString(randomNumber(4, 9)) + customDomain;
        } else if (atIndex > 0) {
            return getRandomString(randomNumber(4, 9)) + "." + customDomain;
        } else {
            return getRandomString(randomNumber(4, 9)) + "@" + customDomain;
        }
    }

    public static String getRandomLiteralsString(int countOfNumbersInString) {
        return getRandomString(countOfNumbersInString);
    }

    public static String getRandomNumericString(int countOfNumbersInString) {
        final int max = (int) Math.pow(10, countOfNumbersInString);
        final int min = (int) Math.pow(10, countOfNumbersInString - 1);
        return Integer.toString(random.nextInt(max - min - 1) + min);
    }


    /**
     * The Method returns a random date within range from the current date
     *
     * @param lowerLimit Days number from the current date as start day for the range
     * @param upperLimit Days number from the current date as end day for the range
     * <pre>
     *         Now        lowerLimit          upperLimit
     * ---------|--------------|*******************|---------
     *                                   ^
     *                              Random Date
     * </pre>
     * @return Calendar date
     * @author Alexander Isko (alexander.isko@viaamadeus.com)
     */
    public static Calendar getRndCalendarDate(int lowerLimit, int upperLimit) {
        Calendar date = Calendar.getInstance();
        int daysFrom = randomNumber(lowerLimit, upperLimit);
        date.add(Calendar.DATE, daysFrom);
        return date;
    }

    private static String getRandomString(int top) {
        String dat = "";
        for (int i = 0; i < top; i++) {
            dat = (char) (int) ((random.nextInt(25) + 97)) + dat;
        }
        return dat;
    }

    private static String getRandomRussianString(int top) {
        String dat = "";
        for (int i = 0; i < top; i++) {
            dat = (char) (int) (randomNumber(1072, 1103)) + dat;
        }
        return dat;
    }

    public static Integer randomNumber(int min, int max) {
    	if (max - min == 0)	return min;								// Avoid thrown exception when the min and the max values are equal
    	else	return random.nextInt(max - min) + min;
    }
}
