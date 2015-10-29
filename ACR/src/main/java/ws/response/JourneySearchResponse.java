package main.java.ws.response;

import main.java.model.Trip;

import javax.xml.soap.SOAPMessage;

/**
 * Created by Andrii.Bakhtiozin on 27.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class JourneySearchResponse extends Response {

    private String sampleXpathWithJourneyId;
    private String journeyId;

    public JourneySearchResponse(SOAPMessage soapResponse) {
        super(soapResponse);
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void findJourneyIdByDesireTripOption(Trip trip) {
        String xpath = createJourneyIdXpath(trip);
        this.journeyId = getJourneyId(xpath);
        if (this.journeyId == null || this.journeyId.equals(""))
            throw new NullPointerException("JourneyId was not found for such xpath: " + xpath);
        this.sampleXpathWithJourneyId = "//journey[journeyId[text()='" + this.journeyId + "']]";

    }

    private String createJourneyIdXpath(Trip trip) {
        return "//journey[contains(journeyInfo/faresAvailable,'true') and contains(journeySegments/journeySegment/operatorName,'" + trip.getTransporterName() + "')" +
                (trip.getTransporterNumber() == null ? "" : " and contains(journeySegments/journeySegment/trainNumber,'" + trip.getTransporterNumber() + "')") +
                "]" + (trip.getTransporterNumber() == null ? "[1]" : "") + "/journeyId";
    }

    private String getJourneyId(String journeyIdXpath) {
        return evaluator.getResult(journeyIdXpath);
    }

    public String getDepartureDate() {
        return evaluator.getResult(sampleXpathWithJourneyId.concat("//departureDate"));
    }


}
