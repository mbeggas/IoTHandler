package iothandler.core.iot.component;

public class ThingNotActuatorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8016502567647206291L;

	public ThingNotActuatorException() {
		// TODO Auto-generated constructor stub
		super("IoT desired thing is not an actuator");
	}

	public ThingNotActuatorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}


}
