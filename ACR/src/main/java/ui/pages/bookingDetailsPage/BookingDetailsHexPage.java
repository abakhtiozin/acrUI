package main.java.ui.pages.bookingDetailsPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import main.java.model.CreditCardInfo;
import util.ui.JQueryWorker;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 16.10.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookingDetailsHexPage extends BookingDetailsPage implements PrintableBookingDetails {


    private SelenideElement passengerCardHolderCheckboxElement() {
        return $("#credit_card_isCardholder");
    }

    public BookingDetailsHexPage addCreditCardInfo(CreditCardInfo creditCardInfo) {
        setCardType(creditCardInfo.getCardType());
        setCardNumber(creditCardInfo.getCardNumber());
        setCardCVV(creditCardInfo.getCVV());
        setCardExpiryMonth(creditCardInfo.getExpiryMonth());
        setCardExpiryYear(creditCardInfo.getExpiryYear());
        setCardAddressLine(creditCardInfo.getAddressLine());
        setCardPostCode(creditCardInfo.getPostCode());
        setCardCountry(creditCardInfo.getCountry());
        return this;
    }

    public BookingDetailsHexPage setPassengerIsCardHolder(boolean isCardHolder) {
        passengerCardHolderCheckboxElement().shouldBe(Condition.visible);
        JQueryWorker.setCheckBoxValue(passengerCardHolderCheckboxElement().getAttribute("id"), isCardHolder);
        return this;
    }

    public BookingDetailsHexPage addCardHolderName(String cardHolderName) {
        setCardHolderName(cardHolderName);
        return this;
    }

    private BookingDetailsHexPage setCardCountry(String cardCountry) {
        $("#credit_card_country").selectOptionByValue(cardCountry);
        return this;
    }

    private BookingDetailsHexPage setCardPostCode(String postCode) {
        $("#credit_card_postcode").setValue(postCode);
        return this;
    }

    private BookingDetailsHexPage setCardAddressLine(String cardAddressLine) {
        $("#credit_card_addressLine").setValue(cardAddressLine);
        return this;
    }

    private BookingDetailsHexPage setCardExpiryYear(String cardExpiryYear) {
        $("#credit_card_expiryDate_year").selectOptionByValue(cardExpiryYear);
        return this;
    }

    private BookingDetailsHexPage setCardExpiryMonth(String cardExpiryMonth) {
        $("#credit_card_expiryDate_month").selectOptionByValue(cardExpiryMonth);
        return this;
    }

    private BookingDetailsHexPage setCardCVV(String cvv) {
        $("#credit_card_cvv").setValue(cvv);
        return this;
    }

    private BookingDetailsHexPage setCardNumber(String cardNumber) {
        $("#credit_card_cardNumber").setValue(cardNumber);
        return this;
    }

    private BookingDetailsHexPage setCardType(String cardType) {
        $("#credit_card_cardType").selectOption(cardType);
        return this;
    }

    private BookingDetailsHexPage setCardHolderName(String cardHolderName) {
        $("#credit_card_cardholderName").setValue(cardHolderName);
        return this;
    }

    @Override
    public BookingDetailsPage confirmOrder() {
        $(".btn.btn-primary.confirmHexOrder").shouldHave(visible).click();
        waitForModalWindow().pressClose();
        return this;
    }
}
