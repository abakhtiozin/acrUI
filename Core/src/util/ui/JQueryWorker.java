package util.ui;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Created by Andrii.Bakhtiozin on 15.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class JQueryWorker {

    //метод для удобной работы с jQuery datepicker
    public static void setDatePickerValue(String cssSelector, String date) {
        $(cssSelector).waitUntil(Condition.appear, 5000);
        executeJavaScript(String.format("$('%s').datepicker('setDate', '%s')", cssSelector, date));
    }

    public static void removeClass(String cssSelector, String className) {
        executeJavaScript("$( \"" + cssSelector + "\" ).removeClass(\"" + className + "\");");
    }

    //локатор чекбокса и желаемое значение чекбокса true/false
    public static void setCheckBoxValue(String cssSelector, boolean checked) {
        executeJavaScript("$( \"" + cssSelector + "\" ).prop( \"checked\", " + checked + " );");
    }

    public static boolean isChecked(String inputId) {
        return executeJavaScript("return document.getElementById(\"" + inputId + "\").checked");
    }

    /**
     * Choose value from jquery autocompleter using jquery function     *
     *
     * @param cssSelector selector of autocompleter element
     * @param value       String value to choose
     * @author Andrii Bakhtiozin (andrii.bakhtiozin@viaamadeus.com)
     */
    public static void setAutoCompleteValue(String cssSelector, String value) {
        executeJavaScript("$( \"" + cssSelector + "\" ).autocomplete( \"search\", \"" + value + "\" );");
    }
}
