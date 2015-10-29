package main.java.ws.request;

import main.java.core.SearchJourney;
import main.java.model.Journey;
import main.java.model.passenger.Passenger;
import main.java.utils.DateTimeHelper;
import main.java.ws.response.JourneySearchResponse;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Created by Andrii.Bakhtiozin on 27.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class JourneySearchRequest extends Request implements SearchJourney {
    private SOAPElement passengersSOAPElement;

    public JourneySearchRequest() {
        super();
        addAcrMethodName("JourneySearch");
    }

    public void addJourney(Journey journey) {
        try {
            SOAPElement originDestinationSearch = soapBodyElement.addChildElement("originDestinationSearch");
            originDestinationSearch.addChildElement("originCode").addTextNode(journey.getOriginLocation());
            originDestinationSearch.addChildElement("originDate").addTextNode(journey.getOriginDate().toString("yyyy-MM-dd"));
            originDestinationSearch.addChildElement("originTimeFrom").addTextNode(DateTimeHelper.getHours("HH", journey.getOriginTimeFrom()));
            if (journey.getOriginTimeTo() == 0) {
                originDestinationSearch.addChildElement("originTimeTo").addTextNode("24:00");
            } else {
                originDestinationSearch.addChildElement("originTimeTo").addTextNode(DateTimeHelper.getHours("HH", journey.getOriginTimeTo()));
            }
            originDestinationSearch.addChildElement("destinationCode").addTextNode(journey.getDestinationLocation());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void addPassenger(Passenger passenger) {
        try {
            if (passengersSOAPElement == null) {
                passengersSOAPElement = soapBodyElement.addChildElement("passengers");
            }
            SOAPElement passengerElement = passengersSOAPElement.addChildElement("passenger");
            passengerElement.addChildElement("birthDate").addTextNode(passenger.getBirthDate().toString("yyyy-MM-dd"));
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public JourneySearchResponse searchJourney() {
        SOAPMessage soapResponse = null;
        try {
            soapResponse = soapConnection.call(this.createRequest(), url);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return new JourneySearchResponse(soapResponse);
    }


}
