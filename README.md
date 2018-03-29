# IoTHandler

IoT Handler is a middleware thet can integrate to the web of things any not connected objet (sensor or actuator that can be accessible via low level code or with special protocol).

IoT Handler has two main parts: middleware part and core package part

A middleware is a running java application that can load IoT thing coponent jar files. It loads the component and makes CoAP REST interfaces for all operations offered by the thing. For that, IoT handler uses Calfornium CoAP Server

 The secand part is the core pakage. It allows to define thing by implementing the Interface IoThingHandler to create a thing, specify their operations, parameters and the code that interacts with the physical thing.