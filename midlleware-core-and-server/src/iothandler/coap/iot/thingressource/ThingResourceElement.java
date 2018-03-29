/**
 * It is instantiated for each entry in the Map of IoThingResources class
 * It contains a reference of the IoThing, and
 * a Map with the name of child CoAP resource as a key
 * For each resource, it has a reference to the IoTOperation
 */
package iothandler.coap.iot.thingressource;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import iothandler.core.iot.component.IoTOpType;
import iothandler.core.iot.component.IoTOperation;
import iothandler.core.iot.component.IoThing;

public class ThingResourceElement {
	private IoThing thing;
	private Map<String, IoTOperation> coapReseForOp;

	public ThingResourceElement(IoThing thing) throws NullIoTOperationsException {
		this.thing = thing;
		if(thing == null) throw new NullIoTOperationsException("IoThing is null");
		
		if(thing.getOperations() == null)
			throw new NullIoTOperationsException("IoT Thing does not offer any operation");
		
		coapReseForOp = new HashMap<String, IoTOperation>();
		for(IoTOperation op: thing.getOperations()) {
			coapReseForOp.put(op.getName(), op);
		}
	}
	
	public IoTOperation getCoapResOperation(String subResourceName) {		
		return coapReseForOp.get(subResourceName);		
	}
	
	public IoTOpType getOperationType(String subResourceName) {
		return coapReseForOp.get(subResourceName).getType();
	}
	
	public IoThing getIoThing() {
		return thing;
	}
	
	public String[] getCoapOpRessources() {
		String[] xoaoOpres= new String[coapReseForOp.size()];
		int i=0;
		for(Entry<String, IoTOperation> resOp : coapReseForOp.entrySet())
			xoaoOpres[i++] =  resOp.getKey();			
		return xoaoOpres;
	}
	
}
