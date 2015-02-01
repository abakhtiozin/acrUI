package ui.pages.BookFormPage;

/**
 * Created by bakhtiozin on 29.01.2015.
 */
public abstract class BookFormPageBuilder {

    // ------------------------------ FIELDS ------------------------------
    protected BookFormPage BookFormPage;


    // ------------------------------ METHODS -----------------------------

    protected BookFormPage getBookFormPage() {
        return BookFormPage;
    }

    protected void setBookFormPage(BookFormPage bookFormPage) {
        BookFormPage = bookFormPage;
    }

    protected void CreateNewBookFormPage() {
        BookFormPage = new BookFormPage();
    }

    protected abstract void setPassengerLocators();
}
