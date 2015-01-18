package ui.util;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Created by AA on 15.01.2015.
 */
public class JQueryWorker {


    //метод для удобной работы с jQuery datepicker
    public void setDatepicker(String cssSelector, String date) {
        $(cssSelector).waitUntil(appear, 5000);
        executeJavaScript(String.format("$('%s').datepicker('setDate', '%s')", cssSelector, date));
    }

    public void setCheckBoxValue(String cssSelector, boolean checked){
        executeJavaScript("$( \""+ cssSelector +"\" ).prop( \"checked\", "+ checked +" );");
    }
//    public void setUnchecked(String cssSelector){
//        executeJavaScript("$( \"#"+ cssSelector +"\" ).prop( \"checked\", false );");
//    }


}
