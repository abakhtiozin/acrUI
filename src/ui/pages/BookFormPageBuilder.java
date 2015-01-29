package ui.pages;

/**
 * Created by bakhtiozin on 29.01.2015.
 */
public abstract class BookFormPageBuilder {

    public BookFormPage getBookFormPage() {
        return BookFormPage;
    }

    public void setBookFormPage(BookFormPage bookFormPage) {
        BookFormPage = bookFormPage;
    }

    public BookFormPage BookFormPage;

    public void CreateNewBookFormPage()
    {
        BookFormPage = new BookFormPage();
    }
}
