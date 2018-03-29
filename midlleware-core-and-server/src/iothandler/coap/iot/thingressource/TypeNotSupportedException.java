package iothandler.coap.iot.thingressource;

public class TypeNotSupportedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7334495832759625703L;

	public TypeNotSupportedException() {
		super("Type not supported in the request");
	}

	public TypeNotSupportedException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
