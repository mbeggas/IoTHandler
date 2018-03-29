package iothandler.core.iot.component;

public class IoTOperationParamListException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7754939047091727877L;

	public IoTOperationParamListException() {
		// TODO Auto-generated constructor stub
		super("IoT operation not allowed for the current IoT thing");
	}

	public IoTOperationParamListException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
