package main.java.steps.reseller;

import main.java.model.Supplier;
import main.java.ui.pages.refundDetailsPage.CanDownloadRefundReceipt;
import main.java.ui.pages.refundDetailsPage.HasRefundDetails;
import main.java.ui.pages.refundDetailsPage.RefundDetailsPage;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Andrii.Bakhtiozin on 03.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtRefundDetailsPageSteps extends AtInnerPageSteps {

    private RefundDetailsPage refundDetailsPage;

    @Override
    public void shouldBeOnThisPage() {
        refundDetailsPage.onThisPage();
    }

    @Step
    public void clickOnOpenClaimPreRefund() {
        refundDetailsPage.clickOnOpenClaim();
    }

    @Step
    public void clickOnOpenRefundDetails() {
        ((HasRefundDetails) refundDetailsPage).clickOnOpenRefundDetails();
    }

    /**
     * The method only for IP and UFS suppliers
     * Clicking then downloading receipt
     */
    @Step
    public void clickOnDownloadChargesReceipt() {
        ((CanDownloadRefundReceipt) refundDetailsPage).clickOnDownloadChargesReceipt();
    }

    @Override
    public void setUpResellerAtPage() {
        refundDetailsPage = (RefundDetailsPage) applicationStorage.getCurrentPage();
    }

    /**
     * The method for IP and TF
     * There is a checking-loop for IP, because supplier update status on his system
     * in few seconds (5-20) after refundConfirm.
     * */
    @Step
    public void pressOnCheckRefundStatusButton() {
        if (applicationStorage.getSupplier().equals(Supplier.IP)) {
            int i = 0;
            do {
                i++;
                refundDetailsPage.checkRefund();
                if (i > 15) assert false; //to prevent infinitive loop
            }
            while (refundDetailsPage.shouldBeHiddenLoader() && refundDetailsPage.checkRefundButtonShouldNotBeVisible());
        } else refundDetailsPage.checkRefund();
    }

    @Step
    public void pressOnConfirmRefundButton() {
        refundDetailsPage.confirmRefund();
    }

    /**
     * The method for UFS supplier
     * UFS has rule, prohibit to confirm refund if claim blank wasn't read.
     * */
    @Step
    public void shouldBeRefundButtonDisabled() {
        boolean isDisabled = refundDetailsPage.isRefundButtonDisabled();
        Assert.assertTrue(isDisabled);
    }
}
