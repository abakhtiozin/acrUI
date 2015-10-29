package test.java.ui.suppliersTesting;

import main.java.model.Supplier;

import org.testng.annotations.BeforeClass;

public class GoGlobalSupplierTest extends SingleSupplierTest {
	
	@BeforeClass
	public void setUnblockedSupplier() {		
		setUnblockedSupplier(Supplier.GoGlobal);		
	}	
	
	
}
