/**
 * IoTOperation specifies an operation that could be done by a IoThing
 * It has a type : sensor or actuator
 * List of parameters
 * a sensor function (the code that will be executed when one calls this CoAP FREST web service with GET)
 * an actuator function (the code that will be executed when one calls this CoAP FREST web service with POST)
 */
package iothandler.core.iot.component;

import java.util.ArrayList;
import java.util.List;

public class IoTOperation {	
	private String name;
	private IoTOpType type;
	private List<IoTParam> params;
	private IoThingSensorFunction sensorFunction;
	private IoThingActuatorFunction actuatorFunction;
	
	public IoTOperation(String name, IoTOpType type, List<IoTParam> params) {
		this.name = name;
		this.type = type;
		this.params = params;
	}
	public IoTOperation(String name, IoTOpType type) {
		this.name = name;
		this.type = type;
	}
	public IoTOperation(String name) {
		this.name = name;
	}

	public IoTOperation() {
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IoTOpType getType() {
		return type;
	}
	public void setType(IoTOpType type) {
		this.type = type;
	}
	
	public void addParam(IoTValue value){
		if(params == null) 
			params = new ArrayList<IoTParam>();
		params.add(new IoTParam(params.size(), value));
	}
	public IoTParam getParamById(Integer id) throws IoTParamIdNotFoundException {
		for(IoTParam param : params)
			if(param.getId().equals(id))
				return param;
		
		throw new IoTParamIdNotFoundException();		
	}
	public List<IoTParam> getParamList() {
		return params;
	}
	public void setParamList(List<IoTParam> params) {
		this.params = params;
	}

	public void addParam(IoTParam param) {
		params.add(param);
	}
	public void setSensorFunction(IoThingSensorFunction sensorFunction) {
		this.sensorFunction = sensorFunction;
	}
	public void setActuatorFunction(IoThingActuatorFunction actuatorFunction) {
		this.actuatorFunction = actuatorFunction;
	}
	
	public List<IoTValue> getSensorData() throws IoTThingOperationNotAllowedException{
		if(this.type != IoTOpType.SENSOR_OP)
			throw new IoTThingOperationNotAllowedException("This operation could be done only with Sensor like operation");
		if(sensorFunction == null) 	throw new IoTThingOperationNotAllowedException("Sensor function is null");
		return sensorFunction.ioTSensorFunction(this);
	}
	
	public void executeActuatorAction() throws IoTThingOperationNotAllowedException{
		if(this.type != IoTOpType.ACTUATOR_OP)
			throw new IoTThingOperationNotAllowedException("This operation could be done only with actuaator like operation");
		if(actuatorFunction == null) 	throw new IoTThingOperationNotAllowedException("Sensor function is null");
		actuatorFunction.ioTActuatorFunction(this);
	}
	
}
