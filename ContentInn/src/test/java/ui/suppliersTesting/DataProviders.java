package test.java.ui.suppliersTesting;

import static util.other.RandomDataGenerator.getRndCalendarDate;
import static util.other.RandomDataGenerator.randomNumber;


import java.util.Calendar;
import java.util.HashMap;


import main.java.model.City;
import main.java.model.Supplier;
import main.java.model.searchParameters.HotelLocation;
import main.java.model.searchParameters.Period;
import main.java.model.searchParameters.RoomsOptions;

public class DataProviders {
	
	public static HashMap<Supplier, City[]> supplierPossibleLocations = new HashMap<Supplier, City[]>() {
		private static final long serialVersionUID = 1L;
		{
			put(Supplier.Acase,				new City[] {City.KYIV,		City.MOSCOW	});
			put(Supplier.ContentHellas,		new City[] {City.PARIS,		City.WARSAW	});
			put(Supplier.ContentLink,		new City[] {City.PARIS,		City.LONDON	});
			put(Supplier.Dotw,				new City[] {City.LONDON,	City.WARSAW	});
			put(Supplier.GoGlobal,			new City[] {City.NEW_YORK,	City.LONDON	});
			put(Supplier.HotelBeds,			new City[] {City.PARIS,					});
			put(Supplier.JacTravel,			new City[] {City.LONDON,	City.ROMA	});
			put(Supplier.Tourico,			new City[] {City.NEW_YORK,	City.PARIS	});
			put(Supplier.Travco,			new City[] {City.LONDON,				});
		
		}
	};
		
	public Object[][] composeSearchParams(Supplier supplier) {
		
		return new Object[][] {
			/* Nearest dates(booking in penalty), one room one traveler no children */
			{	new HotelLocation(supplierPossibleLocations.get(supplier)[0]), 
				new Period(Calendar.getInstance(), randomNumber(1, 3)),
				new RoomsOptions(1, 1, 0)
			},
			
			/* Nearest dates(booking in penalty) */
			{
				new HotelLocation(supplierPossibleLocations.get(supplier)[0]),
				new Period(Calendar.getInstance(), randomNumber(1, 7)),
				new RoomsOptions(randomNumber(1, 3),																				//Rooms number
						randomNumber(supplier == Supplier.ContentHellas ? 2 : 1, supplier == Supplier.ContentHellas ? 2 : 4),		//Adults number
						randomNumber(supplier == Supplier.ContentHellas ? 2 : 1, 10))												//Child age
			},
			
			/* Future dates, several rooms several travelers */
			{
				new HotelLocation(supplierPossibleLocations.get(supplier)[0]),
				new Period(getRndCalendarDate(40, 80), randomNumber(1, 5)),
				new RoomsOptions(randomNumber(1, 3),																				//Rooms number
						randomNumber(supplier == Supplier.ContentHellas ? 2 : 1, supplier == Supplier.ContentHellas ? 2 : 4), 		//Adults number
						randomNumber(supplier == Supplier.ContentHellas ? 2 : 1, 10))												//Child age
			},
			
			/* Future dates, several rooms several travelers no children */
			{
				new HotelLocation(supplierPossibleLocations.get(supplier)[0]),
				new Period(getRndCalendarDate(40, 80), randomNumber(1, 5)),
				new RoomsOptions(randomNumber(1, 3), 
						randomNumber(1, 4), 
						0)				
			}			
		};
		
	
	
	
	
	
		
	}
	
	
	
	
	
	
	
	
}
