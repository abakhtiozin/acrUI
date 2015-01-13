package model;

/**
 * Created by AA on 27.12.2014.
 */
public class Journey {

    private String originLocation;
    private String originDate;
    private String originTimeFrom;
    private String originTimeTo;
    private String destinationLocation;

    public String getOriginDate() {
        return originDate;
    }

    public String getOriginTimeFrom() {
        return originTimeFrom;
    }

    public String getOriginTimeTo() {
        return originTimeTo;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public String getOriginLocation() {
        return originLocation;
    }

//    public Journey(String originLocation, String destinationLocation, String originDate) {
//        this.originDate = originDate;
//        this.originLocation = originLocation;
//        this.destinationLocation = destinationLocation;
//    }

    public Journey(String originLocation, String destinationLocation, String originDate, String originTimeFrom, String originTimeTo) {
        this.originLocation = originLocation;
        this.originDate = originDate;
        this.originTimeFrom = originTimeFrom;
        this.originTimeTo = originTimeTo;
        this.destinationLocation = destinationLocation;
    }
}
