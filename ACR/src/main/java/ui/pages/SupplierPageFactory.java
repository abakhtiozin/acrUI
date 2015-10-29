package main.java.ui.pages;

import main.java.ApplicationStorage;
import main.java.model.Supplier;
import main.java.ui.pages.bookFormPage.*;
import main.java.ui.pages.bookingDetailsPage.*;
import main.java.ui.pages.refundDetailsPage.*;

import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 02.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SupplierPageFactory {
    private static SupplierPageFactory instance;
    private ApplicationStorage applicationStorage = ApplicationStorage.getInstance();

    private SupplierPageFactory() {
    }

    public static SupplierPageFactory getInstance() {
        if (instance == null) {
            instance = new SupplierPageFactory();
        }
        return instance;
    }

    public RefundDetailsPage createRefundDetailsPage(Supplier supplier) {
        switch (supplier) {
            case UFS:
                return page(RefundDetailsUfsPage.class);
            case IP:
                return page(RefundDetailsIpPage.class);
            case ACP:
                return page(RefundDetailsAcpPage.class);
            case TF:
                return page(RefundDetailsTfPage.class);
        }
        throw new IllegalArgumentException("Can't create RefundDetailsPage for such supplier " + applicationStorage.getSupplier());
    }

    public BookingDetailsPage createBookingDetailsPage(Supplier supplier) {
        switch (supplier) {
            case UFS:
                return page(BookingDetailsUfsPage.class);
            case ACP:
                return page(BookingDetailsAcpPage.class);
            case TF:
                return page(BookingDetailsTfPage.class);
            case IP:
                return page(BookingDetailsIpPage.class);
            case HEX:
                return page(BookingDetailsHexPage.class);
        }
        throw new IllegalArgumentException("Can't create BookingDetailsPage for such supplier " + applicationStorage.getSupplier());
    }

    public BookFormPage createBookFormPage(Supplier supplier) {
        switch (supplier) {
            case UFS:
                return page(new BookFormUfsPage());
            case ACP:
                return page(new BookFormAcpPage());
            case TF:
                return page(new BookFormTfPage());
            case IP:
                return page(new BookFormIpPage());
            case HEX:
                return page(new BookFormHexPage());
        }
        throw new IllegalArgumentException("Can't create BookFormPage for such supplier " + supplier);
    }
}

