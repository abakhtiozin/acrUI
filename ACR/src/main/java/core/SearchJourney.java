package main.java.core;

import main.java.model.Journey;

/**
 * Created by Andrii.Bakhtiozin on 12.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface SearchJourney extends WithPassenger {
    void addJourney(Journey journey);
}
