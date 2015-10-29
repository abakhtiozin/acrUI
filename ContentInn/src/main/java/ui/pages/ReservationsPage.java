package main.java.ui.pages;

import main.java.ui.webElements.NavigationBar;
import main.java.ui.webElements.ReservationsPage.Reservation;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

public class ReservationsPage extends CommonPage {
	
	public ReservationsPage() {
		super();
	}

	private SelenideElement reservartionsGrid() {
		return $(By.xpath(".//tbody[@id='single-grid-reservations']"));
	}
	
	public List<Reservation> visibleReservationsList() {
		List<Reservation> reservations  = new ArrayList<Reservation>();
		List<SelenideElement> reservationsElement = reservartionsGrid().$$(By.xpath("./tr"));
		for (SelenideElement reserv : reservationsElement) {
			reservations.add(new Reservation(reserv));
		}
		return reservations;
	}

	public Reservation reservation(long id){
		List<Reservation> res  = visibleReservationsList();
		for (int i = 0; i < res.size(); i++) {
			Reservation r = res.get(i);
			if(r.getId() == id) {
				return r;
			}

		}
		return null;
	}

	public NavigationBar navigationOnPage(){
		return page(NavigationBar.class);
		//return new NavigationBar(sdfdsfsfds)

	}

	@Override
	public boolean onThisPage() {
		return reservartionsGrid().isDisplayed();
	}
}
