/**
 * An Interface that should be implemented by each component to be be loaded by IoT Handler middleware
 * The name of class (or classes) implementing this interface should appear in the MANIFEST file 
 * in the attribute "Implementation-Title"
 * Jar component loader will search classes in "Implementation-Title" and load them as implementation of IoThingHandler
 * interface.
 * It calls the method getIoThing() to have a IoThing instance and adds any operation as a REST resource in CoAP server 
 */
package iothandler.core.iot.component;

public interface IoThingHandler {
	public IoThing getIoThing();
}
