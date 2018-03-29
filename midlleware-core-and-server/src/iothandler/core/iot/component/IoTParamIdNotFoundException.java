package iothandler.core.iot.component;

public class IoTParamIdNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1494194395545394056L;

	public IoTParamIdNotFoundException() {
		// TODO Auto-generated constructor stub
		super("IoT id pramamameter not found");
	}

	public IoTParamIdNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
