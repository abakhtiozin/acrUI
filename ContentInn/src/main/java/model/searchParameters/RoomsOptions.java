package main.java.model.searchParameters;

import main.java.model.restriction.Limit;


public class RoomsOptions implements Limit {
	private static final int[] ROOMS_NUMBER_RANGE	= {1, 3};
	private static final int[] ADULTS_NUMBER_RANGE	= {1, 4};
	private static final int[] CHILD_AGE_RANGE		= {0, 12};
	
	private int roomsNumber = 1;
	private int adultsNumber = 1;
	private int childAge = 0;

	public RoomsOptions(int rooms, int adults, int age) {
		if (rooms < ROOMS_NUMBER_RANGE[MIN] || rooms > ROOMS_NUMBER_RANGE[MAX]) {
			throw new IllegalArgumentException("Argument rooms is out of range");
		}
		else {
			roomsNumber = rooms;
		}
		
		if (adults < ADULTS_NUMBER_RANGE[MIN] || adults > ADULTS_NUMBER_RANGE[MAX]) {
			throw new IllegalArgumentException("Argument adults is out of range");
		}
		else {
			adultsNumber = adults;
		}
		
		if (age < CHILD_AGE_RANGE[MIN] || age > CHILD_AGE_RANGE[MAX]) {
			throw new IllegalArgumentException("Argument age is out of range");
		}
		else {
			childAge = age;
		}		
	}
	
	public int roomsNumber() {
		return roomsNumber;
	}	
	public int adultsNumber() {
		return adultsNumber;
	}	
	public int childAge() {
		return childAge;
	}
	
	
	@Override
	public String toString() {
		return roomsNumber() + ":" + adultsNumber() + ":" + childAge();	
	}
	
}
