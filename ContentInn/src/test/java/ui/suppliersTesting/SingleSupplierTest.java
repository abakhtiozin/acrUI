package test.java.ui.suppliersTesting;

import main.java.model.Supplier;


public class SingleSupplierTest extends ReservationLifeCycleTests {
	Supplier supplier;
	
	protected void setUnblockedSupplier(Supplier supplier) {		
		this.supplier = supplier;
		reseller.setUnblockedSuppliers(this.supplier);
		//Log.info("Supplier " + supplier.toString() + " was unblocked for the test execution"); 
	}
		
	
	
	
}
