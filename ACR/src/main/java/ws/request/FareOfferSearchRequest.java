package main.java.ws.request;

import main.java.ws.response.FareOfferSearchResponse;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Created by Andrii.Bakhtiozin on 30.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class FareOfferSearchRequest extends Request {

    public FareOfferSearchRequest() {
        super();
        addAcrMethodName("FareOfferSearch");
    }

    public void addJourneyId(String journeyId) {
        try {
            soapBodyElement.addChildElement("journeyId").addTextNode(journeyId);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public FareOfferSearchResponse sendRequest() {
        SOAPMessage soapResponse = null;
        try {
            soapResponse = soapConnection.call(this.createRequest(), url);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return new FareOfferSearchResponse(soapResponse);
    }

}
