/**
 * IoThing class
 * It's a class that represent the IoT Thing
 * The name will be used a parent resource name in REST interface
 * operation is a definition of operations where the thing can do
 * they will be exposed as CoAP REST web services
 */
package iothandler.core.iot.component;

import java.util.ArrayList;
import java.util.List;

public class IoThing {
	private String name;
	private String description;
	List<IoTOperation> operations;
	
	public IoThing() { 
		
	}
	
	public IoThing(String name) {
		this.name = name;
	}
	
	public IoThing(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public IoThing(String name, String description, List<IoTOperation> operations) {
		this.name = name;
		this.description = description;
		this.operations = operations;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public void addOperation(IoTOperation op) {
		if(this.operations == null)
			operations = new ArrayList<IoTOperation>();
		
		operations.add(op);
	}

	public List<IoTOperation> getOperations() {
		return operations;
	}

	public void setOperations(List<IoTOperation> operations) {
		this.operations = operations;
	}

}
