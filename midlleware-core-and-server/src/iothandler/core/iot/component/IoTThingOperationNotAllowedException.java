package iothandler.core.iot.component;

public class IoTThingOperationNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3325965909178034390L;

	public IoTThingOperationNotAllowedException() {
		// TODO Auto-generated constructor stub
		super("IoT Thing operation not allowed");
	}

	public IoTThingOperationNotAllowedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
