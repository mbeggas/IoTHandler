/**
 * A functional Interface for IoTOperation
 * It will be implemented by each operation offering an actuator operation
 * It will be called when one call a CoAP REST interface by POST
 */
package iothandler.core.iot.component;


@FunctionalInterface
public interface IoThingActuatorFunction {
	void ioTActuatorFunction(IoTOperation op);
}
