package main.java.ui.pages.bookFormPage;

import util.ui.JQueryWorker;

/**
 * Created by Andrii.Bakhtiozin on 22.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
class BookFormPageActions {


    void setWithElectronicRegistration(boolean withElectronicRegistration) {
        JQueryWorker.setCheckBoxValue("#book_request_withElectronicRegistration", withElectronicRegistration);
    }
}
