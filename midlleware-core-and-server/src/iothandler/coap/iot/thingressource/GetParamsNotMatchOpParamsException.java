package iothandler.coap.iot.thingressource;

public class GetParamsNotMatchOpParamsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6975403556884276189L;

	public GetParamsNotMatchOpParamsException() {
		// TODO Auto-generated constructor stub
		super("Parameter list of GET request not math operatoion pmaram list");
	}

	public GetParamsNotMatchOpParamsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}


}
