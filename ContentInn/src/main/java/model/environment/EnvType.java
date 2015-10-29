package main.java.model.environment;

public enum EnvType {
	TEST	("testing.contentinn.local"),
	RC		("rc.contentinn.local"),
	STABLE	("stable.contentinn.local"),
	PROD	("contentinn.com");
	
	private final String fullRef;
	
	EnvType (String fRef) { 
		this.fullRef	= fRef;		
	}
	
	public String baseRef(){ return this.fullRef; }
	
}
