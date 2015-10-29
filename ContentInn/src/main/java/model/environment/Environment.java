package main.java.model.environment;

public class Environment {
	private static String envFrontUrl;
	private static String envBackUrl;

	
	public static String getFronendUrl(EnvType envType, EnvMode envMode) {
		envFrontUrl = "http://" + envType.baseRef() + "/" + envMode.frontendRef();		
		return envFrontUrl;
	}
	
	public static String getBackendUrl(EnvType envType, EnvMode envMode) {
		envBackUrl = "http://" + envType.baseRef() + "/" + envMode.backendRef();		
		return envBackUrl;
	}
	
}
