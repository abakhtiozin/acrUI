package main.java.ui.pages.bookingDetailsPage;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 05.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface RefundButtonElement {
    default SelenideElement refundButtonElement() {
        return $(".btn.pull-right.btn-warning.mL5").waitUntil(appear, 35000);
    }
}
