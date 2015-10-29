package main.java.model;

import static util.other.RandomDataGenerator.*;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by trofimenko on 16.02.2015.
 */
public class Traveler {

    public enum Titulation {
    	mr, ms, mrs;    	
    	/**
    	 * The method returns random Titulation from the available values
    	 * @return enum Titulation
    	 * @author Alexander Isko (alexander.isko@viaamadeus.com)
    	 */
    	public static Titulation getRandom() {
			return Titulation.values()[randomNumber(0, Titulation.values().length)];
		}
    
    };
    private Boolean owner = false;
    private Titulation titulation = Titulation.mr;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Traveler( Boolean owner, Titulation titulation, String firstName, String lastName, String email, String phone) {
        this(owner, titulation, firstName, lastName, email);
        this.phone = phone;
    }

    public Traveler( Boolean owner, Titulation titulation, String firstName, String lastName, String email) {
    	this(firstName, lastName, email);
    	this.owner = owner;
        this.titulation = titulation;
    }

    public Traveler(String firstName, String lastName, String email, String phone) {
        this(firstName, lastName, email);
        this.phone = phone;
    }

    public Traveler(String firstName, String lastName, String email) {
        this(firstName, lastName);
        this.email = email;
    }
    
    public Traveler(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Setters    
    public Traveler thisIsOwner(boolean owner) {
		this.owner = owner;
		return this;
	}
    public Traveler withTitulation(Titulation titul) {
		this.titulation = titul;
		return this;
	}
    public Traveler withFirstName(String name) {
		this.firstName = name;
		return this;
	}
    public Traveler withLastName(String surname) {
		this.lastName = surname;
		return this;
	}
    public Traveler withEmail(String email) {
		this.email = email;
		return this;
	}
    public Traveler withPhone(String phone) {
		this.phone = phone;
		return this;
	}
    
    
    // Getters
    public Boolean owner(){
        return owner;
    }

    public Titulation titulation() {
        return titulation;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }
    
    @Override
    public String toString() {
    	return this.titulation.name() + "," + WordUtils.capitalizeFully(this.firstName) + " " + this.lastName.toUpperCase() + "(" + (this.email != null ? this.email : "No e-mail") + ", " + (this.phone != null ? this.phone : "No phone") + ")";
    }
    
}
