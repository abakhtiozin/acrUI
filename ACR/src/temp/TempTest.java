package temp;

import main.java.model.Journey;
import main.java.model.Reseller;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.ws.request.FareOfferSearchRequest;
import main.java.ws.request.JourneySearchRequest;
import main.java.ws.response.FareOfferSearchResponse;
import main.java.ws.response.JourneySearchResponse;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 12.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TempTest {

    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }

    @Test
    public void main() throws Exception {

        System.out.println(new DateTime().minusYears(12)+"");

        System.exit(1);

        Reseller reseller = new Reseller("and-prt-ua", "andrew", "1234");
        Trip trip = new Trip().withTransporterName("easyJet").withTariffType(new String[]{"Y"})/*.withTrainNumber("030А").withTicketType("ETK")*/;

        JourneySearchRequest journeySearchRequest = new JourneySearchRequest();
        journeySearchRequest.withReseller(reseller);
        journeySearchRequest.addJourney(new Journey("BER", "LON", currentDatePlusNDays(20)));
        journeySearchRequest.addPassenger(new Passenger("12.03.1980"));
        journeySearchRequest.addPassenger(new Passenger("12.03.1982"));

        JourneySearchResponse journeySearchResponse = journeySearchRequest.searchJourney();
        journeySearchResponse.findJourneyIdByDesireTripOption(trip);

        FareOfferSearchRequest fareOfferSearchRequest = new FareOfferSearchRequest();
        fareOfferSearchRequest.addJourneyId(journeySearchResponse.getJourneyId());
        fareOfferSearchRequest.withReseller(reseller);

        FareOfferSearchResponse fareOfferSearchResponse = fareOfferSearchRequest.sendRequest();
        fareOfferSearchResponse.findOfferIdByTrip(trip);

        System.out.println(fareOfferSearchResponse.getFareOfferId());
        assert false;

    }


}
