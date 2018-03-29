# IoTHandler

IoT Handler is a middleware that can integrate to the web of things any not connected thing (sensor or actuator that can be accessible via low level code or with special protocols).

IoT Handler has two main parts: middleware part and core package part

A middleware is a running java application that can load IoT thing coponent jar files. It loads the component and makes CoAP REST interfaces for all operations offered by this thing. For that, IoT handler uses Calfornium CoAP Server to manage REST resources.

 The second part is the core pakage. It provides classes and interfaces that allow to define thing behavior. Each component should implement the Interface "IoThingHandler" to: create a thing, specify their operations, parameters and the code that interacts with the physical thing.
