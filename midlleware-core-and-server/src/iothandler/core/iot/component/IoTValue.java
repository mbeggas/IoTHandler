/**
 *  A value that will be used by the in parameter of an operation or in the returned value
 *  It has a class type and a value that will be casted according to the class type
 */
package iothandler.core.iot.component;

public class IoTValue {
	private Class<?> valueClass;
	private Object iotValue;
	
	public IoTValue(Class<?> valueClass, Object value) {
		super();
		this.valueClass = valueClass;
		iotValue = value;
	} 

	public IoTValue(Class<?> valueClass) {
		super();
		this.valueClass = valueClass;
	}

	public IoTValue() {
		super();
	}

	public Class<?> getValueClass() {
		return valueClass;
	}

	public void setValueClass(Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	public Object getIotValue() {
		return iotValue;
	}

	public void setIotValue(Object iotValue) {
		this.iotValue = iotValue;
	}
		
}
