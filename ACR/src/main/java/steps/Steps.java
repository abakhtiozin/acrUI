package main.java.steps;

import main.java.ApplicationStorage;

/**
 * Created by Andrii.Bakhtiozin on 23.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class Steps {

    protected ApplicationStorage applicationStorage;
    protected StepManager stepManager;
    public Steps() {
        this.applicationStorage = ApplicationStorage.getInstance();
        this.stepManager = StepManager.getInstance();
    }
}
