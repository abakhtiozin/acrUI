package main.java.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import main.java.model.Traveler;
import main.java.model.pricing.Price;
import main.java.model.reservation.ReservationStatus;
import main.java.model.restriction.Limit;
import static main.java.util.LogUtils.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import ru.yandex.qatools.allure.annotations.Step;
import test.java.base.TestHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class BookingPage extends CommonPage implements Limit{
    private static String fldTermsAndConditionsXpath = "//input[contains(@name, '[accept_terms]')]";
    private static String btnFinishBookingXpath = "//button[@title='Finish booking']";
    private static String alertDialogXpath = "//div[@id='alertDialog']";
    private static String btnOkAlertWarningXpath = "//input[@class='alert_warning_button']";
    private static String btnOkAlertSuccessXpath = "//input[@class='alert_success_button']";

    private static String cancellationPenaltiesXpath = "//fieldset/legend[text()='Cancellation penalties']/../div/table[@class='grid-data']";
    private static String divCancellationPenaltiesXpath = ("//div[@class='booking-info'  and contains(@id, 'cancellationPolicyContainer')]/table/tbody/tr");
    private static String priceBreakdownXpath = "//fieldset/legend[text()='Price breakdown']/../div/table[@class='grid-data']";
    private static String divPriceBreakdownXpath = "//div[@class='booking-info' and contains(@id, 'priceBreakdownContainer')]/table/tbody/tr/td";

    private static String totalBookingPriceXpath = "//td[@class = 'pre-booking-info-container']/fieldset//table/tfoot/tr/td[2]";
    private static String bookingPricePerRoomXpath = "//td[@class = 'pre-booking-info-container']/fieldset//table/tbody/tr/td[4]/div";

    private ReservationStatus bookingStatus;
    
    public BookingPage() {
		super();
	}
    
    @Step
    public void setTraveler(Traveler traveler) {
        Traveler[][] travelers = {{traveler}};
        setTravelers(travelers);
    }

    @Step
    public void setTravelers(Traveler[][] travelers) {
       List<SelenideElement>  trs = $$(By.xpath("//tbody[@id='travellers']/tr"));
        int numberRoom = 0, numberAdult = 0;

        for (int i = 0; i < trs.size(); i++) {
            SelenideElement tr = trs.get(i);
            String nomer = tr.$(By.xpath("./td[1]")).innerHtml();
            if (nomer.trim().isEmpty() || nomer.trim().equals("&nbsp;") ) {
                numberAdult++;
            }
            else {
                numberAdult = 0;
                numberRoom++;
            }

            Traveler traveler = travelers[numberRoom-1][numberAdult];
            boolean isChildRow = false;
            SelenideElement child = tr.$(By.xpath("./td[3]"));
            if (child.innerHtml().indexOf("child") >= 0){
                  isChildRow  = true;
            }

            if (!isChildRow){
                //Set Owner
                if (traveler.owner() == true) {
                    SelenideElement ownerEl = tr.$(By.xpath("./td[2]/input"));
                    ownerEl.click();
                }

                //Set Titulation
                SelenideElement titulEl = tr.$(By.xpath("./td[3]/select"));
                titulEl.selectOptionByValue(traveler.titulation().toString());

                //Set E-mail
                SelenideElement emailEl = tr.$(By.xpath("./td[6]/input"));
                emailEl.setValue(traveler.email());

                //Set Phone
                if(traveler.phone() != null) {
                    SelenideElement phoneEl = tr.$(By.xpath("./td[7]/input"));
                    phoneEl.setValue(traveler.phone());
                }
            }

            //Set First Name
            SelenideElement firstNameEl = tr.$(By.xpath("./td[4]/input"));
            firstNameEl.setValue(traveler.firstName());

            //Set Last Name
            SelenideElement lastNameEl = tr.$(By.xpath("./td[5]/input"));
            lastNameEl.setValue(traveler.lastName());


        }

    }

    @Step
    public void clickTermsAndConditions() {
        $(By.xpath(fldTermsAndConditionsXpath)).click();
    }

    public ReservationStatus convertBookingStatus(String massage){
        ReservationStatus status = null;
        String s;
        //Booking completed. Status is «RQ».
        s = massage.substring(massage.indexOf("Status is") + 11, massage.indexOf("Status is") + 13);
        switch (s) {
            case "OK":
                status = ReservationStatus.OK;
                break;
            case "RQ":
                status = ReservationStatus.RQ;
                break;
            case "PO":
                status = ReservationStatus.POK;
                break;
            case "PR":
                status = ReservationStatus.PR;
                break;
            case "RJ":
                status = ReservationStatus.RJ;
                break;
            default:
                break;
        }
        return status;
    }

    private void waitCancellationPenaltiesInfo() {
        $(By.xpath(cancellationPenaltiesXpath)).waitUntil(Condition.visible, SHORT_WAIT_TIME);

    }

    @Step
    public List<Map<String, String>> getCancellationPenalty() {
        waitCancellationPenaltiesInfo();
        List<Map<String, String>> cp =  new ArrayList<Map<String, String>>();
        List<SelenideElement>  penalty = $$(By.xpath(divCancellationPenaltiesXpath));
        for (int i=0; i < penalty.size(); i++) {
            Map<String, String> canPenalty = new HashMap<String, String>();
            String dt = penalty.get(i).$(By.xpath("./td[1]")).text();
            canPenalty.put("DateTime", dt.substring(0, dt.indexOf('(') - 1));
            String amt = penalty.get(i).$(By.xpath("./td[2]")).text();
            canPenalty.put("Amount", amt.substring(0, amt.length() - 4));
            canPenalty.put("Currency", amt.substring(amt.length() - 3));
            cp.add(canPenalty);
        }

//        for (Map<String, String> canPenaltyRow : cp) {
//            for (Map.Entry entry : canPenaltyRow.entrySet()) {
//                System.out.println("Key: " + entry.getKey() + " Value: "
//                        + entry.getValue());
//            }
//            System.out.println("============================================");
//        }

        return cp;

    }

    private void waitPriceBreakdownInfo() {
        $(By.xpath(priceBreakdownXpath)).waitUntil(Condition.visible, SHORT_WAIT_TIME);

    }

    @Step
    public List<Map<String, String>> getPriceBreakdown() {
        waitPriceBreakdownInfo();
        List<Map<String, String>> pb = new ArrayList<Map<String, String>>();
        List<SelenideElement>  price = $$(By.xpath(divPriceBreakdownXpath));
        for (int i=0; i < price.size(); i++) {
            if(price.get(i).text().length() > 6 ) {
                Map<String, String> priceB = new HashMap<String, String>();
                String str = price.get(i).text();
                priceB.put("Date", str.substring(0, 6));
                priceB.put("Price", str.substring(7, str.length() - 4));
                priceB.put("Currency", str.substring(str.length() - 3));
                pb.add(priceB);
            }
        }
        return pb;
    }

    @Step
    public Boolean clickFinishBooking(){
        waitCancellationPenaltiesInfo();
        waitPriceBreakdownInfo();
        $(By.xpath(btnFinishBookingXpath)).click();
        try{
            Alert pastCancellationDeadline = switchTo().alert();
            if(pastCancellationDeadline != null){
                pastCancellationDeadline.accept();
            }
        } catch (NoAlertPresentException exception){
        	TestHelper.Log.debug("No alert");
        }
        SelenideElement dialog = $(By.xpath(alertDialogXpath)).waitUntil(Condition.exist, BOOK_MAX_WAIT_TIME);
        if (dialog.$(By.xpath(btnOkAlertWarningXpath)).exists()) {
            takeScreenshot("Finish booking screenshot");
            TestHelper.Log.debug(dialog.$(By.xpath(btnOkAlertWarningXpath + "/preceding::td[1]")).text());            
            dialog.$(By.xpath(btnOkAlertWarningXpath)).click();
            return false;
        }
        if($(dialog.$(By.xpath(btnOkAlertSuccessXpath))).exists()){
            bookingStatus = convertBookingStatus(dialog.$(By.xpath(btnOkAlertSuccessXpath + "/preceding::td[1]")).text());
            dialog.$(By.xpath(btnOkAlertSuccessXpath)).click();
            return true;
        }
        return false;
    }

    @Step
    public Price getTotalBookingPrice() {
        Price totalPrice; String str;
        str = $(By.xpath(totalBookingPriceXpath)).text().trim();
        totalPrice = Price.parse(str);
        //System.out.println("totalPrice " + totalPrice);
        return totalPrice;
    }

    @Step
    public Price getAlternateTotalBookingPrice() {
        Price totalPrice; String str;
        str = $(By.xpath(totalBookingPriceXpath)).text().trim();
        if (str.lastIndexOf("\n") > 0 && str.lastIndexOf("(") > 0){
            totalPrice = Price.parse(str.substring(str.lastIndexOf("("), str.length() - 1).trim());
        } else {
            totalPrice = null;
        }
        return totalPrice;
    }

    @Step
    public Price getBookingPricePerRoom() {
        String str = $(By.xpath(bookingPricePerRoomXpath)).text().trim();
        return Price.parse(str);
    }

    @Step
    public ReservationStatus getBookingStatus(){
        return bookingStatus;
    }
    
    @Step
    public boolean isOfferAvailable() {
    	String p = "Currently this offer is unavailable, you must renew search.";
    	return !$(By.xpath("//*[@id='middlebar']/.//*[contains(text(), '" + p + "')]")).isDisplayed();
    }
    
    
    @Override
    public boolean onThisPage() {
		return $(".offer_item_container").waitUntil(Condition.exist, 10000).isDisplayed();
	}

}
