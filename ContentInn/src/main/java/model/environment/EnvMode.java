package main.java.model.environment;

public enum EnvMode {
	TEST	("", "admin"),
	DEV		("frontend_dev.php", "backend_dev.php");
	
	private final String frontendRef;
	private final String backendRef;
	
	EnvMode (String fRef, String bRef) { 
		this.frontendRef = fRef;
		this.backendRef = bRef;
	}
	
	public String frontendRef()	{ return this.frontendRef; }
	public String backendRef()	{ return this.backendRef; }
}
