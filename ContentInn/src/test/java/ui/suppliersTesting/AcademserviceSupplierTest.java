package test.java.ui.suppliersTesting;

import main.java.model.Supplier;

import org.testng.annotations.BeforeClass;

public class AcademserviceSupplierTest extends SingleSupplierTest {
	
	@BeforeClass
	public void setUnblockedSupplier() {		
		setUnblockedSupplier(Supplier.Acase);		
	}	
	
	
}
