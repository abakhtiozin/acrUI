package ui.pages.BookFormPage;

/**
 * Created by bakhtiozin on 30.01.2015.
 */
public class GetBookFormPage {

    private BookFormPageBuilder bookFormPageBuilder;

    public void SetBookFormBuilder(BookFormPageBuilder bookFormPageBuilder){
        this.bookFormPageBuilder = bookFormPageBuilder;
    }
    public BookFormPage getBookFormPage(){
        return bookFormPageBuilder.getBookFormPage();
    }
    public void constructBookFormPage(){
        bookFormPageBuilder.CreateNewBookFormPage();
        bookFormPageBuilder.setPassengerLocators();
    }

}
