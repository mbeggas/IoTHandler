/**
 * This is an example of a thing component
 * It will be compiled separately, generate a jar file
 * A MANIDEST file should be defined for this component
 * 	 In the attribute "Implementation-Title", classes implementing IoThingHandler should be added
 *   Jar component loader will search classes in "Implementation-Title" and load them as
 *   an implementation of IoThingHandler interface.
 *   It calls the method getIoThing() to have a IoThing instance and adds any operation as a REST resource in CoAP server 
 * For testing IoT Handler, we consider our system as a thing
 * Actuator and sensor functions are defined using lambda functions
 */
package coap.iot.handler;

import java.util.ArrayList;

import iothandler.core.iot.component.IoTOpType;
import iothandler.core.iot.component.IoTOperation;
import iothandler.core.iot.component.IoTParam;
import iothandler.core.iot.component.IoTParamIdNotFoundException;
import iothandler.core.iot.component.IoTValue;
import iothandler.core.iot.component.IoThing;
import iothandler.core.iot.component.IoThingHandler;

public class ComputerIoThingHandler implements IoThingHandler {		
	
	public IoThing getIoThing() {
		IoThing thing = new IoThing("systemThing", "for testing our iot, we consider a system as a thing");
		IoTOperation op = new IoTOperation("processorCores");
		op.setType(IoTOpType.SENSOR_OP);
		op.setParamList(null);
		op.setSensorFunction((oper)->{ 
			System.out.println("I'm a sensor function requesting /" + thing.getName() + "/" + oper.getName());
			Integer nbCore = Runtime.getRuntime().availableProcessors();
			ArrayList<IoTValue> returnValue=new ArrayList<IoTValue>();
			returnValue.add(new IoTValue(Integer.class, nbCore));
			return returnValue;
		});
		thing.addOperation(op);		
		
		IoTOperation op2 = new IoTOperation("freeMemory");
		thing.addOperation(op2);
		op2.setType(IoTOpType.SENSOR_OP);
		op2.setSensorFunction((oper)->{
			System.out.println("I'm a sensor function requesting /" + thing.getName() + "/" + oper.getName());
			Long freeMemory = Runtime.getRuntime().freeMemory();
			ArrayList<IoTValue> returnValue=new ArrayList<IoTValue>();
			returnValue.add(new IoTValue(Long.class, freeMemory));
			return returnValue;
		});
		
		IoTOperation op3 = new IoTOperation("totalMemory");
		thing.addOperation(op3);
		op3.setType(IoTOpType.SENSOR_OP);
		op3.setSensorFunction((oper)->{
			System.out.println("I'm a sensor function requesting /" + thing.getName() + "/" + oper.getName());
			Long totalMemory = Runtime.getRuntime().totalMemory();
			ArrayList<IoTValue> returnValue=new ArrayList<IoTValue>();
			returnValue.add(new IoTValue(Long.class, totalMemory));
			return returnValue;			
		});
		
		IoTOperation op4 = new IoTOperation("postData");
		thing.addOperation(op4);
		op4.setType(IoTOpType.ACTUATOR_OP);
		op4.addParam(new IoTValue(String.class));
		
		op4.setActuatorFunction((oper)->{
			IoTParam param;
			try {
				param = oper.getParamById(0);
				System.out.println("I'm an Actuator function executing action doing /"+ thing.getName() + "/" + oper.getName() +
								"\n\t I post to the actuator this data : ");				
				System.out.println(	param.getParamValue().getValueClass().cast(param.getParamValue().getIotValue()) );
			} catch (IoTParamIdNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		return thing;
	}
		

	

}
