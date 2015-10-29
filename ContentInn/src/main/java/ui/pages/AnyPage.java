package main.java.ui.pages;

import main.java.Manager;

public abstract class AnyPage {
	
	public AnyPage() {
        Manager.getInstance().setCurrentPage(this);
    }
	
	protected abstract boolean onThisPage();
		
}
