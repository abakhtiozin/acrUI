package main.java.ui.pages.bookingDetailsPage;

import com.codeborne.selenide.SelenideElement;
import main.java.ui.pages.ModalWindow;
import main.java.ui.pages.refundPage.RefundPage;
import util.ui.JQueryWorker;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static main.java.utils.Utils.switchDriverToMainWindow;

/**
 * Created by Andrii.Bakhtiozin on 08.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookingDetailsUfsPage extends BookingDetailsPage implements RefundThroughForm {
    public BookingDetailsUfsPage() {
        super();
    }

    public boolean isDoubleDeckCarriage() {
        return $("").exists();
    }

    @Override
    public RefundPage clickOnRefundBooking() {
        refundButtonElement().click();
        return page(RefundPage.class);
    }

    public BookingDetailsPage openRequestChangeERegistration() {
        $("#requestChangeERegister").click();
        switchDriverToMainWindow();
        return this;
    }

    private SelenideElement eRegisterChangeModalElement() {
        return $("#eRegisterChangeModal");
    }

    public BookingDetailsPage changeERegistrationStatus() {
        setChangeERegistrationButtonEnabled();
        $("#changeERegister").click();
        new ModalWindow(eRegisterChangeModalElement()).pressYes();
        return this;
    }

    public boolean isERegistrationBtnEnabled() {
        return eRegisterChangeModalElement().isEnabled();
    }

    private void setChangeERegistrationButtonEnabled() {
        JQueryWorker.removeClass("#" + eRegisterChangeModalElement().getAttribute("id"), "disabled");
    }
}
