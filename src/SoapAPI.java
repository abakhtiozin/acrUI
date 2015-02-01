import model.Reseller;
import model.Journey;
import model.passenger.Passenger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.soap.*;
import java.util.List;

/**
 * Created by AA on 20.12.2014.
 */
public class SoapAPI {

    private String url;
    private SOAPConnection soapConnection;
    private SOAPMessage soapResponse;

    private Reseller reseller;
    private Journey journey;
    private List<Passenger> passengers;

    public void setReseller(Reseller reseller) {
        this.reseller = reseller;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //Constructor
    // Create SOAP Connection
    public SoapAPI() {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    // Send SOAP Message to SOAP Server
    public void sendRequest(SoapOperations operation) {
        if (operation.equals(SoapOperations.JourneySearch)) {
            try {
                soapResponse = soapConnection.call(journeySearchRequest(reseller, journey, passengers), url);
                soapResponse.writeTo(System.out);
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getResponseValueByTagName(String tagName, int index) {
        String result = null;
        try {
            SOAPBody soapBody = soapResponse.getSOAPBody();
            Document document = soapBody.extractContentAsDocument();
            Element root = document.getDocumentElement();
            Element message = (Element) root.getElementsByTagName(tagName).item(index);
            result = message.getTextContent();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void closeConnection() {
        try {
            soapConnection.close();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    /*
    <access>
     <resellerCode>andrew</resellerCode>
     <userName>user</userName>
     <password>1234</password>
     <lang>RU</lang>
    </access>
    */
    private SOAPElement addAccess(SOAPElement actionRootElement, Reseller reseller) throws SOAPException {
        SOAPElement soapElement = actionRootElement.addChildElement("access");
        addElementWithValue(soapElement, "resellerCode", reseller.getResellerCode());
        addElementWithValue(soapElement, "userName", reseller.getUserName());
        addElementWithValue(soapElement, "password", reseller.getPassword());
        addElementWithValue(soapElement, "lang", reseller.getLang());
        return soapElement;
    }

    /*
    <originDestinationSearch>
     <!--You may enter the following 5 items in any order-->
     <originCode>2000000</originCode>
     <originDate>15.01.2015</originDate>
     <originTimeFrom>0</originTimeFrom>
     <originTimeTo>23</originTimeTo>
     <destinationCode>2004000</destinationCode>
    </originDestinationSearch>
    */
    private void addOriginDestinationSearch(SOAPElement actionRootElement) throws SOAPException {
        SOAPElement originDestinationSearchElement = actionRootElement.addChildElement("originDestinationSearch");
        originDestinationSearchElement.addChildElement("originCode").addTextNode("2000000");
        originDestinationSearchElement.addChildElement("originDate").addTextNode("15.01.2015");
        originDestinationSearchElement.addChildElement("originTimeFrom").addTextNode("0");
        originDestinationSearchElement.addChildElement("originTimeTo").addTextNode("23");
        originDestinationSearchElement.addChildElement("destinationCode").addTextNode("2004000");
    }
    /*
    <passenger>
     <!--You may enter the following 10 items in any order-->
     <birthDate>13.12.1990</birthDate>
    </passenger>
     */

//    private void addPassenger(SOAPElement passengersElement, Passenger passenger) throws SOAPException {
//        SOAPElement passengerElement = passengersElement.addChildElement("passenger");
//        if (passenger.getBirthDate() != null) {
//            addElementWithValue(passengerElement, "birthDate", passenger.getBirthDate().toString());
//        }
//        if (passenger.getSalutation() != null) {
//            addElementWithValue(passengerElement, "salutation", passenger.getSalutation());
//        }
//        if (passenger.getSalutation() != null) {
//            addElementWithValue(passengerElement, "name", passenger.getName());
//        }
//        if (passenger.getSurname() != null) {
//            addElementWithValue(passengerElement, "surname", passenger.getSurname());
//        }
//        if (passenger.getDocumentType() > 0) {
//            addElementWithValue(passengerElement, "documentType", Integer.toString(passenger.getDocumentType()));
//        }
//        if (passenger.getDocumentNumber() != null) {
//            addElementWithValue(passengerElement, "documentNumber", passenger.getDocumentNumber());
//        }
//        if (passenger.getBirthPlace() != null) {
//            addElementWithValue(passengerElement, "birthPlace", passenger.getBirthPlace());
//        }
//        if (passenger.getNationality() != null) {
//            addElementWithValue(passengerElement, "nationality", passenger.getNationality());
//        }
//        if (passenger.getResidence() != null) {
//            addElementWithValue(passengerElement, "residence", passenger.getResidence());
//        }
//    }

    private void addElementWithValue(SOAPElement parentElement, String childElementName, String value) throws SOAPException {
        parentElement.addChildElement(childElementName).addTextNode(value);
    }

    private SOAPMessage journeySearchRequest(Reseller reseller, Journey journey, List<Passenger> passengers) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ns", url);
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        // root element (in out case  - name of the function)
        SOAPElement soapBodyElem = soapBody.addChildElement("acr_JourneySearch", "ns");
        addAccess(soapBodyElem, reseller);
        addOriginDestinationSearch(soapBodyElem);
        SOAPElement passengersElement = soapBodyElem.addChildElement("passengers");
        for (int i = 0; i < passengers.size(); i++) {
//            addPassenger(passengersElement, passengers.get(i));
        }

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("soapAction", url + "0.1/acr_JourneySearch");
        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);
        System.out.println();
        return soapMessage;
    }


}
