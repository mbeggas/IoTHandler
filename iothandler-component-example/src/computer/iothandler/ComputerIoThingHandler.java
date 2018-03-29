/**
 * This is an example of a thing
 * For testing IoT Handler, we consider our system as a thing
 * Actuator and sensor functions are defined using lambda functions
 */
package computer.iothandler;

import java.util.ArrayList;
import java.util.logging.Logger;

import iothandler.core.iot.component.IoTOpType;
import iothandler.core.iot.component.IoTOperation;
import iothandler.core.iot.component.IoTParam;
import iothandler.core.iot.component.IoTParamIdNotFoundException;
import iothandler.core.iot.component.IoTValue;
import iothandler.core.iot.component.IoThing;
import iothandler.core.iot.component.IoThingHandler;


public class ComputerIoThingHandler implements IoThingHandler {		
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public IoThing getIoThing() {
		IoThing thing = new IoThing("systemThing", "for testing our iot, we consider a system as a thing");
		IoTOperation op = new IoTOperation("processorCores");
		op.setType(IoTOpType.SENSOR_OP);
		op.setParamList(null);
		op.setSensorFunction((oper)->{ 
			LOGGER.info("*** I'm a sensor function requesting : /" + thing.getName() + "/" + oper.getName());
			Integer nbCore = new ComputerTools().getProcessorCores();
			ArrayList<IoTValue> returnValue=new ArrayList<IoTValue>();
			returnValue.add(new IoTValue(Integer.class, nbCore));
			return returnValue;
		});
		thing.addOperation(op);		
		
		IoTOperation op2 = new IoTOperation("freeMemory");
		thing.addOperation(op2);
		op2.setType(IoTOpType.SENSOR_OP);
		op2.setSensorFunction((oper)->{
			LOGGER.info("*** I'm a sensor function requesting /" + thing.getName() + "/" + oper.getName());
			Long freeMemory = new ComputerTools().getFreeMemory();
			ArrayList<IoTValue> returnValue=new ArrayList<IoTValue>();
			returnValue.add(new IoTValue(Long.class, freeMemory));
			return returnValue;
		});
		
		IoTOperation op3 = new IoTOperation("totalMemory");
		thing.addOperation(op3);
		op3.setType(IoTOpType.SENSOR_OP);
		
		op3.setSensorFunction((oper)->{
			LOGGER.info("*** I'm a sensor function requesting /" + thing.getName() + "/" + oper.getName());
			Long totalMemory = new ComputerTools().getTotalMemory();
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
				LOGGER.info("*** I'm an Actuator function executing action \n\t I post to the actuator this data :\n\t\t "				
				 		+ param.getParamValue().getValueClass().cast(param.getParamValue().getIotValue()) );
			} catch (IoTParamIdNotFoundException e) {
				e.printStackTrace();
			}
		});
		
		return thing;
	}		
}
