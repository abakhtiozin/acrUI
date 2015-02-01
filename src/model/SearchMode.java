package model;

/**
 * Created by AA on 11.01.2015.
 */
public class SearchMode {
    private boolean toRussianSystem = false;
    private boolean toInternationalSystem = false;
    private boolean toLowCostSystem = false;

    public boolean isRussianSystem() {
        return toRussianSystem;
    }

    public boolean isInternationalSystem() {
        return toInternationalSystem;
    }

    public boolean isLowCostSystem() {
        return toLowCostSystem;
    }

    public SearchMode toRussianSystem() {
        this.toRussianSystem = true;
        return this;
    }

    public SearchMode toInternationalSystem() {
        this.toInternationalSystem = true;
        return this;
    }

    public SearchMode toLowCostSystem() {
        this.toLowCostSystem = true;
        return this;
    }


}
