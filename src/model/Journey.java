package model;

/**
 * Created by AA on 27.12.2014.
 */
public class Journey {

    private String originLocation;
    private String originDate;
    private int originTimeFrom;
    private int originTimeTo;
    private String destinationLocation;

    public String getOriginDate() {
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

    public Journey(String originLocation, String destinationLocation, String originDate, int originTimeFrom, int originTimeTo) {
        this.originLocation = originLocation;
        this.originDate = originDate;
        this.originTimeFrom = originTimeFrom;
        this.originTimeTo = originTimeTo;
        this.destinationLocation = destinationLocation;
    }
}
