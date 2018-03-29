/**
 * A functional Interface for IoTOperation
 * It will be implemented by each operation offering a sensor operation
 * It will be called when one call a CoAP REST interface by GET
 */
package iothandler.core.iot.component;

import java.util.List;

@FunctionalInterface
public interface IoThingSensorFunction {
	List<IoTValue> ioTSensorFunction(IoTOperation op);
}
