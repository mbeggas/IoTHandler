/**
 * Extends californium CoAP Server class by adding methods that load component jar file and
 * add CoAP resources to resource entries. 
 * It uses MyJarURLClassLoader for loading jar component, it extends URLClassLoader 
 * It implements equally handleGET and handlePOST by calling the code defined in IoTOperation sensor or actuator function
 * of CoAP resource for each operation offered by a IoThing.
 */
package iothandler.coap.iot.server;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.californium.core.CoapResource;

import iothandler.coap.iot.thingressource.GetParamsNotMatchOpParamsException;
import iothandler.coap.iot.thingressource.IoThingResources;
import iothandler.coap.iot.thingressource.NullIoTOperationsException;
import iothandler.coap.iot.thingressource.ThingResourceElement;
import iothandler.coap.iot.thingressource.TypeNotSupportedException;
import iothandler.core.iot.component.IoTOpType;
import iothandler.core.iot.component.IoTParamIdNotFoundException;
import iothandler.core.iot.component.IoTThingOperationNotAllowedException;
import iothandler.core.iot.component.IoThing;
import iothandler.core.iot.component.IoThingHandler;

import org.json.JSONObject;


public class IotCoapServer extends CoapServer{
	
	IoThingResources iotThingRessoureses;

	public IotCoapServer() {
		super();
		iotThingRessoureses = new  IoThingResources();
	}

	public IotCoapServer(int port) {
		super(port);
		iotThingRessoureses = new  IoThingResources();		
	}
	
	public void loadIotHandlerRessources(String jarfilepath) throws NullIoTOperationsException, 
											ClassNotFoundException, InstantiationException, IllegalAccessException,
											IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
											SecurityException, IOException {
		MyJarURLClassLoader cl = null;
		
	    try {
	        cl = new MyJarURLClassLoader(jarfilepath, getClass().getClassLoader());	    	
	        List<IoThingHandler> iotHandlist = cl.loadIotHandlers();
			//IoThingHandler iotHand = new ComputerIoThingHandler();
			for(IoThingHandler iotHand1 : iotHandlist) {
				IoThing thing = iotHand1.getIoThing();
				ThingResourceElement ioThhingresElem = iotThingRessoureses.addThingRessources(thing);
				addToServerResources(ioThhingresElem);
			}
	    } finally {
	        if(cl != null)
	            cl.close();
	    } 		
		
	}	
	protected void addToServerResources(String thingName) {
		ThingResourceElement thingRes= iotThingRessoureses.getThingResByResName(thingName);
		addToServerResources(thingRes);	
	}
	
	protected void addToServerResources(ThingResourceElement thingRes) {
		String thingName = thingRes.getIoThing().getName();
		CoapResource thingCoapRes = new CoapResource(thingName);
		for(String resOp : thingRes.getCoapOpRessources()) {
			//String res = thingName + "/" + resOp;
			CoapResource coapRes;
			if(thingRes.getOperationType(resOp) == IoTOpType.SENSOR_OP) { // respond to GET request
				 coapRes = new CoapResource(resOp) {
					@Override
					public void handleGET(CoapExchange excahnge) {						
						
						try {
							String[] result = iotThingRessoureses.getSenorDataByRessourceNames(thingName, resOp, getCoapParamList(excahnge, "GET" ));
							excahnge.respond(result[0]);
						} catch (NumberFormatException | IoTThingOperationNotAllowedException
								| GetParamsNotMatchOpParamsException | IoTParamIdNotFoundException
								| TypeNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
			}else {
				coapRes = new CoapResource(resOp) {
				@Override
				public void handlePOST(CoapExchange excahnge) {
					
					try {
						iotThingRessoureses.setActuatorActionByRessourceNames(thingName, resOp, getCoapParamList(excahnge, "POST"));
						excahnge.respond("success");
					} catch (NumberFormatException | IoTThingOperationNotAllowedException
							| GetParamsNotMatchOpParamsException | IoTParamIdNotFoundException
							| TypeNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				};
			}			
			thingCoapRes.add(coapRes);
			add(thingCoapRes);
						
		}
	}
	
	static String[]  getCoapParamList(CoapExchange exchange, String optype) {	
		if(optype.equals("GET")) {
			List<String> queries = exchange.getRequestOptions().getURIQueries();			
			String[] strs = new String[queries.size()];
			int i=0;
			for(String query : queries)
				strs[i++] = query;
			//System.out.print(strs);
			//System.out.print(queries);
			return strs;
		}
		JSONObject obj = new JSONObject(exchange.getRequestText());
		Iterator<String> keys= obj.keys();
		List<String> strl = new ArrayList<String>();
		while(keys.hasNext()) {
			strl.add(obj.getString(keys.next()));
		}
		String[] strs = strl.toArray(new String[strl.size()]);
		return strs;			
	}	
}
