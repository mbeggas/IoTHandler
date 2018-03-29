package iothandler.core.iot.component;

public class IoTThingNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 807457181999398873L;
	
	public IoTThingNotFoundException() {
		super("IoT Error, Thing name not found");
	}

	public IoTThingNotFoundException(String msg) {
		super(msg);
	}

}
