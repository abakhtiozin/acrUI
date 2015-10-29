package main.java.ui.webElements.ReservationsPage;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;

import main.java.model.reservation.ReservationRecord;
import main.java.model.reservation.ReservationStatus;
import main.java.model.restriction.Limit;
import ru.yandex.qatools.allure.annotations.Step;
import test.java.base.TestHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import main.java.util.DateUtils;

import static com.codeborne.selenide.Selenide.*;


/**
 * Created by trofimenko on 04.06.2015.
 */
public class Reservation extends ReservationRecord implements Limit {
    private SelenideElement reservationContainer;
    private long reservationInternalId;

    public Reservation(SelenideElement mainContainer) {
        this.reservationContainer = mainContainer;
        reservationInternalId = Long.parseLong(reservationContainer.$(By.xpath("./td[2]/div[3]/strong")).text().trim());
    }

    private void renewReservationContainer() {
        reservationContainer = $(By.xpath(".//tr[contains(@id, 'grid-data-row')]/.//strong[contains(text(), '" + reservationInternalId + "')]/ancestor::tr"));
    }

    public long getId() {
        return reservationInternalId;
    }

    public ReservationStatus getStatus() {
        return super.getStatus(reservationInternalId);
    }

    @Step
    public boolean cancelBooking() {
        if (!reservationContainer.$(By.xpath("./td[7]/ul/li/a[contains(@class, 'icon img-delete')]")).getAttribute("class").equals("icon img-delete16-disabled")) {
            reservationContainer.$(By.xpath("./td[7]/ul/li/a[@class='icon img-delete']")).click();
            $(By.xpath("//div[@id='ModalWindow']")).waitUntil(Condition.visible, SHORT_WAIT_TIME);
            $(By.xpath("//*[@id='confirmCancelPolicy']")).sendKeys("yes");
            $(By.xpath("//*[@id='ModalWindow_buttons']/input[1]")).click();
            $(By.xpath("//*[@id='dataMask']")).waitUntil(Condition.visible, MIDDLE_WAIT_TIME);
            $(By.xpath("//*[@id='dataMask']")).waitUntil(Condition.hidden, MIDDLE_WAIT_TIME);
            renewReservationContainer();
            if (reservationContainer.$(By.xpath("./td[6]/div/span")).text().trim().equals("Cancelled")) {
                return true;
            } else return false;
        } else return false;

    }

    @Step
    public boolean getVoucher() {
        SelenideElement voucherBtnContainer = reservationContainer.$(By.xpath("./td[7]/ul/li[@title='Voucher']"));
        List<SelenideElement> voucherBtnContainerElement = voucherBtnContainer.$$(By.xpath("*"));
        if (!voucherBtnContainerElement.get(0).getAttribute("class").equals("icon img-download-voucher-disabled")) {
            voucherBtnContainer.$(By.xpath(".//div")).click();
            SelenideElement header = $(By.xpath("//div[@class='tooltip']")).waitUntil(Condition.visible, SHORT_WAIT_TIME);
            File voucherPdfFile = null;
            try {
                SelenideElement pdfIcon = header.$(By.xpath(".//tr[2]/td[2]/ul/li[1]/a[@title='PDF']"));
                voucherPdfFile = pdfIcon.download();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            SelenideElement pdfIcon = header.$(By.xpath(".//tr[2]/td[2]/ul/li[1]/a[@title='PDF']"));
//            pdfIcon.click();
//            Selenide.sleep(10000);
//            Robot robot = new Robot();
//            robot.keyPress(KeyEvent.VK_ENTER);
            Selenide.sleep(SECOND);
            refresh();
            renewReservationContainer();
            if (null != voucherPdfFile && voucherPdfFile.length() > 0L) {
                return true;
            } else {
                TestHelper.Log.error("Voucher " + voucherPdfFile.getName() + "wasn't downloaded successfully; ");
                return false;
            }
        } else {
            TestHelper.Log.warning("Can't generate voucher: Voucher button is  disabled; ");
            return false;
        }

    }



    @Step
    public Date getAddedDate() {
        return getDateFor("AD");
    }

    @Step
    public Date getModifiedDate() {
        return getDateFor("MD");
    }

    @Step
    public Date getUpdatedDate() {
        return getDateFor("UD");
    }

    @Step
    public Date getPaidDate() {
        return getDateFor("PD");
    }

    @Step
    public Date getVoucherDate() {
        return getDateFor("VR");
    }

    private Date getDateFor(String typeDate){
        Date date = null;
        SelenideElement dateContainer = reservationContainer.$(By.xpath("./td[5]"));
        List <SelenideElement> dateContainerElements = dateContainer.$$(By.xpath("./span"));
        for(int i = 0 ; i < dateContainerElements.size(); i++){
            if(dateContainerElements.get(i).text().indexOf( typeDate + ":") >= 0){
                String dateVR = dateContainerElements.get(i).text(); // in the span element
                String dateTime = dateVR.substring(dateVR.indexOf(typeDate + ":") + typeDate.length() + 1).trim();
                if(dateTime.equals("-") || dateTime.equals("not generated") ){
                    date = null;
                    TestHelper.Log.error("Date " + typeDate + " wasn't generated; ");
                } else {
                    date = DateUtils.convertToDate("yyyy-MM-dd HH:mm", dateTime);
                }
                break;
            }
        }
        return date;
    }

    private String inOut() {
       SelenideElement dateContainer = reservationContainer.$(By.xpath("./td[2]/div[2]/strong"));
       return dateContainer.text().trim(); // in the strong element
    }


    @Step
    public Date getCheckInDate() {
        String inOutDate = inOut(); // in the strong element
        String dateTime = inOutDate.substring(0, inOutDate.indexOf("/")).trim();
        return DateUtils.convertToDate("yyyy-MM-dd", dateTime);
    }

    @Step
    public Date getCheckOutDate() {
        String inOutDate = inOut(); // in the strong element
        String dateTime = inOutDate.substring(inOutDate.indexOf("/") + 1).trim();
        return DateUtils.convertToDate("yyyy-MM-dd", dateTime);
    }

    @Step
    public Date getCancellationDeadline() {
        SelenideElement dateContainer = reservationContainer.$(By.xpath("./td[2]/div[4]/strong[2]"));
        String dateTime = dateContainer.text().trim();
        return DateUtils.convertToDate("yyyy-MM-dd HH:mm", dateTime);
    }

    @Step
    public String getConfirmationID() {
        SelenideElement dateContainer = reservationContainer.$(By.xpath("./td[2]/div[4]/strong[1]"));
        return dateContainer.text().trim();
    }

    @Step
    public String getHotelName() {
        SelenideElement dateContainer = reservationContainer.$(By.xpath("./td[2]/a"));
        return dateContainer.text().trim();
    }


}
