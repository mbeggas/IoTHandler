/**
 * When a jar component of an IoThing is loaded, the jar component loaded adds any thing as a parent resource
 * the parent resource has an entry in the Map with the thing name (a name of the parent resource as wall)
 * When the CoAP server receive a REST call, the handler of GET or POST request will search in the resource MAP
 * entries to fined the appropriate parent resource. For child resource it search entries in NullIoTOperationsException
 */
package iothandler.coap.iot.thingressource;

import java.util.Map;

import iothandler.core.iot.component.IoTOperation;
import iothandler.core.iot.component.IoTParamIdNotFoundException;
import iothandler.core.iot.component.IoTThingOperationNotAllowedException;
import iothandler.core.iot.component.IoTValue;
import iothandler.core.iot.component.IoThing;

import java.util.HashMap;
import java.util.List;

public class IoThingResources {
	private Map<String, ThingResourceElement> coapThingRes;
	
	public IoThingResources() {super();}
	
	public IoThingResources(List<IoThing> thingList) throws NullIoTOperationsException {
		// TODO Auto-generated constructor stub
		if(thingList == null) throw new NullIoTOperationsException("Thing list is null");
		if(thingList.size()==0) throw new NullIoTOperationsException("Thing list is empty");
		
		coapThingRes = new HashMap<String, ThingResourceElement>() ;
		for(IoThing thing : thingList) 			
			coapThingRes.put(thing.getName(), new ThingResourceElement(thing));		
	}
	
	public ThingResourceElement addThingRessources(IoThing thing) throws NullIoTOperationsException {
		if(thing == null) throw new NullIoTOperationsException("Thing list is null");
		if(coapThingRes == null) coapThingRes = new HashMap<String, ThingResourceElement>();
		ThingResourceElement thingResElem = new ThingResourceElement(thing);
		coapThingRes.put(thing.getName(), thingResElem);
		return thingResElem;
	}
		
	public ThingResourceElement getThingResByResName(String resName) {
		return coapThingRes.get(resName);
	}
	
	public String[] getSenorDataByRessourceNames(String thingResName, String opSubResourceName, String...strparams) throws IoTThingOperationNotAllowedException, 
			GetParamsNotMatchOpParamsException, NumberFormatException, IoTParamIdNotFoundException, TypeNotSupportedException {
		
		IoTOperation op = getThingResByResName(thingResName).getCoapResOperation(opSubResourceName);
		if(op.getParamList() != null) {
			if(op.getParamList().size() != strparams.length)
				throw new GetParamsNotMatchOpParamsException("Paramete list of GET not match param list op operation: " + op.getName());
		}else
			if(strparams.length != 0)
				throw new GetParamsNotMatchOpParamsException("Paramete list og GET not match param list op operation: " + op.getName());

		for(int i=0; op.getParamList() != null && i<op.getParamList().size(); i++)
			if (op.getParamById(i).getParamValue().getValueClass() == Integer.class) 
				op.getParamById(i).getParamValue().setIotValue(new Integer(strparams[i]));
			else 
				if (op.getParamById(i).getParamValue().getValueClass() == Long.class) 
					op.getParamById(i).getParamValue().setIotValue(new Long(strparams[i]));
				else 
					if (op.getParamById(i).getParamValue().getValueClass() == Double.class) 
						op.getParamById(i).getParamValue().setIotValue(new Double(strparams[i]));
					else 
						if (op.getParamById(i).getParamValue().getValueClass() == Byte.class) 
							op.getParamById(i).getParamValue().setIotValue(new Byte(strparams[i]));
						else 
							if (op.getParamById(i).getParamValue().getValueClass() == Short.class) 
								op.getParamById(i).getParamValue().setIotValue(new Short(strparams[i]));
							else 
								if (op.getParamById(i).getParamValue().getValueClass() == String.class) 
									op.getParamById(i).getParamValue().setIotValue(strparams[i]);
								else
									throw new TypeNotSupportedException("Type not supported in GET or POST request");

		List<IoTValue> vals = op.getSensorData();
		String[] result = new String[vals.size()];
		int i=0;
		for(IoTValue v : vals) {
			result[i++] = v.getIotValue().toString();
		}
		return result;				
	}

	public void setActuatorActionByRessourceNames(String thingResName, String opSubResourceName, String...strparams) throws IoTThingOperationNotAllowedException, 
	GetParamsNotMatchOpParamsException, NumberFormatException, IoTParamIdNotFoundException, TypeNotSupportedException {

		IoTOperation op = getThingResByResName(thingResName).getCoapResOperation(opSubResourceName);
		if(op.getParamList() != null) {
			if(op.getParamList().size() != strparams.length)
				throw new GetParamsNotMatchOpParamsException("Paramete list of GET not match param list op operation: " + op.getName());
		}else
			if(strparams.length != 0)
				throw new GetParamsNotMatchOpParamsException("Paramete list og GET not match param, param list is \"null\" op operation: " + op.getName());

		for(int i=0; i<op.getParamList().size(); i++)
			if (op.getParamById(i).getParamValue().getValueClass() == Integer.class) 
				op.getParamById(i).getParamValue().setIotValue(new Integer(strparams[i]));
			else 
				if (op.getParamById(i).getParamValue().getValueClass() == Long.class) 
					op.getParamById(i).getParamValue().setIotValue(new Long(strparams[i]));
				else 
					if (op.getParamById(i).getParamValue().getValueClass() == Double.class) 
						op.getParamById(i).getParamValue().setIotValue(new Double(strparams[i]));
					else 
						if (op.getParamById(i).getParamValue().getValueClass() == Byte.class) 
							op.getParamById(i).getParamValue().setIotValue(new Byte(strparams[i]));
						else 
							if (op.getParamById(i).getParamValue().getValueClass() == Short.class) 
								op.getParamById(i).getParamValue().setIotValue(new Short(strparams[i]));
							else 
								if (op.getParamById(i).getParamValue().getValueClass() == String.class) 
									op.getParamById(i).getParamValue().setIotValue(strparams[i]);
								else
									throw new TypeNotSupportedException("Type not supported in GET or POST request");

		op.executeActuatorAction();
	}
	
}
