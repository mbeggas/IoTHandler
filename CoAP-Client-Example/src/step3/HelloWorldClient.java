package step3;


import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;

public class HelloWorldClient {

    public static void main(String[] args) {

        CoapClient client = new CoapClient("coap://localhost/systemThing/postData");
        CoapClient client2 = new CoapClient("coap://localhost/systemThing/processorCores");
        CoapClient client3 = new CoapClient("coap://localhost/systemThing/totalMemory");        
        
        //System.out.println(": "+client.);
        CoapResponse response = client.post("{\"topost\":\"Data to POST\"}", 1);
        CoapResponse response2 = client2.get();
        CoapResponse response3 = client3.get();
        if (response!=null) { 
        	System.out.println( response.getResponseText() );        	
        } else {
        	System.out.println("POST Request failed");
        }

        if (response2!=null) {
        	System.out.println( response2.getResponseText() );
        } else {
        	System.out.println("GET Request failed");
        }
        if (response3!=null) {
        	System.out.println( response3.getResponseText() );
        } else {
        	System.out.println("GET Request failed");
        }
    }

}
