package main.java.model.searchParameters;

import main.java.model.*;

public class HotelLocation {
	private Country country;
	private City city;

	public HotelLocation(Country country, City city) {
		this.country = country;
		this.city = city;
	}
	
	public HotelLocation(City city) {
		this.city = city;
		this.country = this.city.country();
	}
	
	public HotelLocation(String iata) {		// ???? Better to not use
		city = City.findByIata(iata);
		country = city.country();
	}
	
	
	public String countryIso() {
		return this.country.iso2Code();
	}
	
	public String countryName() {
		return this.country.fullName();
	}
	
	public String cityName() {
		return this.city.fullName();
	}
	
	public String iata() {
		return this.city.iataCode();
	}
	
	public City city() {
		return this.city;
	}
	
	public Country country() {
		return this.country;
	}
	
	
	@Override
	public String toString() {
		return cityName() + "@" + countryName() + "(" + countryIso() + ")";
	}
	
	
}
