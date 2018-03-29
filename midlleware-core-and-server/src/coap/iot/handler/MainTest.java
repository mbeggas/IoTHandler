package coap.iot.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import iothandler.coap.iot.server.IotCoapServer;
import iothandler.coap.iot.thingressource.NullIoTOperationsException;

public class MainTest {

	public MainTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IotCoapServer iotcoapserv = new IotCoapServer();
		iotcoapserv.start();
		Scanner sc = new Scanner(System.in);
		boolean b = true;
		while(b) {
			//C:\Java EE\coap midleware jar\iothandler-component-example.jar
			//= "C:\\Java EE\\coap midleware jar\\computer-iot-manif.jar";
			System.out.print("IoT Component-to-load path: ");
			String filepath = sc.nextLine();
			if(filepath== null || filepath.equals(""))
				continue;
			if(!filepath.endsWith(".jar")) {
				System.out.println("enter a jar file");
				continue;
			}
			try {
				iotcoapserv.loadIotHandlerRessources(filepath);
				System.out.println("\t >>> IoT Component :" + filepath + " is loaded");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | NullIoTOperationsException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(sc != null)
			sc.close();				
	}

}
