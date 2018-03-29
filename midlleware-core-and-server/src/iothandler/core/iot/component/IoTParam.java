/**
 * List of parameters of the IoTOperation
 */
package iothandler.core.iot.component;

public class IoTParam {
	
	private Integer id;
	private IoTValue paramValue;
	
	public IoTParam(Integer id, IoTValue value) {
		super();
		this.id = id;
		this.paramValue = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public IoTValue getParamValue() {
		return paramValue;
	}

	public void setParamValue(IoTValue paramValue) {
		this.paramValue = paramValue;
	}		

}
