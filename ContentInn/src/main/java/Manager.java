package main.java;

import main.java.ui.pages.AnyPage;

public class Manager {

    private static volatile Manager instance;
    private AnyPage currentPage;

    public AnyPage getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(AnyPage currentPage) {
        this.currentPage = currentPage;
    }

    public static Manager getInstance() {
        Manager localInstance = instance;
        if (null == localInstance) {
            synchronized (Manager.class) {
                localInstance = instance;
                if (null == localInstance) {
                    instance = localInstance = new Manager();
                }
            }
        }
        return localInstance;
    }

    // --------------------------- CONSTRUCTORS ---------------------------
    private Manager() {
    }
}
