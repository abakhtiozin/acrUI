package main.java.model;

import org.joda.time.DateTime;

import static main.java.utils.DateTimeHelper.FORMAT_PATTERN;
import static main.java.utils.DateTimeHelper.toJodaTime;

/**
 * Created by Andrii.Bakhtiozin on 27.12.2014.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Journey {

    private String originLocation;
    private String destinationLocation;
    private DateTime originDate;
    private int originTimeFrom;
    private int originTimeTo;

    public Journey(String originLocation, String destinationLocation, String originDate) {
        this.originLocation = originLocation;
        this.originDate = toJodaTime(originDate);
        this.destinationLocation = destinationLocation;
    }

    public Journey(String originLocation, String destinationLocation, String originDate, int originTimeFrom, int originTimeTo) {
        this.originLocation = originLocation;
        this.originDate = toJodaTime(originDate);
        this.originTimeFrom = originTimeFrom;
        this.originTimeTo = originTimeTo;
        this.destinationLocation = destinationLocation;
    }

    public DateTime getOriginDate() {
        return originDate;
    }

    public int getOriginTimeFrom() {
        return originTimeFrom;
    }

    public int getOriginTimeTo() {
        return originTimeTo;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    @Override
    public String toString() {
        if (originTimeFrom > 0 && originTimeTo > 0) {
            return originLocation + " - " + destinationLocation + " : " + originDate.toString(FORMAT_PATTERN) + " : " + originTimeFrom + "-" + originTimeTo;
        }
        return originLocation + " - " + destinationLocation + " : " + originDate.toString(FORMAT_PATTERN);
    }
}
