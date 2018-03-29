/**
 * A class loader for component jar file
 * Extends URLClassLoader to load classes and jar libraries from jar file
 * Loads all classes and jar libraries
 * Uses MANIFEST file to identify IoThingHandler implementation class, it instantiates them and return
 * a list of them to be used the CoAP server
 */
package iothandler.coap.iot.server;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import iothandler.core.iot.component.IoThingHandler;

public class MyJarURLClassLoader extends URLClassLoader {
	
	private String jarFilePath;

	public MyJarURLClassLoader(String jarFilePath, ClassLoader parent) throws MalformedURLException {
		super(new URL[] {new URL("jar:file:" + jarFilePath +"!/")}, parent);
		this.jarFilePath = jarFilePath;
		//cl = new MyURLClassLoader(new URL[] {jarUrl}, this.getClass().getClassLoader());
	}

    /**
     * Load all classes in the jar file
     * return a list of IoTHing handlers
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
	@SuppressWarnings("unchecked")
	public List<IoThingHandler> loadIotHandlers() throws IOException, 
													ClassNotFoundException, InstantiationException,
													IllegalAccessException {
		List<IoThingHandler> iotHandList = null;
		
		try {	    
	        Class<IoThingHandler> iotHandlerClass = null;
	        List<String> iotHandClassnameList = Arrays.asList(getIotHandersFromManifest());
	        List<String> jarClassNemes= classNamesFromJar();
	        if(jarClassNemes != null)
	        	for(String className : jarClassNemes) {
	        		if(iotHandClassnameList.contains(className)) {
	        			if(iotHandList == null)
	        				iotHandList = new ArrayList<IoThingHandler>();
	        			iotHandlerClass = (Class<IoThingHandler>) loadClass(className);
	        			iotHandList.add(iotHandlerClass.newInstance());
	        		}else
	        			loadClass(className);
	        	}        
	        loadJarlibraries();	        
	    }finally{
	        if(this != null)
	            this.close();
	    } 		    
	    return iotHandList;
	}

	public void loadJarlibraries() throws IOException, ClassNotFoundException, InstantiationException,
										IllegalAccessException {
		MyJarURLClassLoader jarloader=null;
		List<String> jarLibNames = jarLibNamesFromJar();
		for(String jarlib : jarLibNames) {
			try {
				jarloader = new MyJarURLClassLoader(jarlib, getClass().getClassLoader()); 
				List<String> jarClassNames = jarloader.classNamesFromLibJar();
				
				if(jarClassNames != null)
					for(String className : jarClassNames) 
						jarloader.loadClass(className);					

			} finally {
				if(jarloader != null)
					jarloader.close();
			} 
		}
	    
	}	
	
	protected List<String> classNamesFromJar() throws IOException {
		
		JarFile jarfile = null;
		List<String> classList = null;		
		
		try {
			jarfile = new JarFile(jarFilePath);					
			
	        Enumeration<JarEntry> jarentry = jarfile.entries();
	        while (jarentry.hasMoreElements()) {
	        	JarEntry je = jarentry.nextElement();
	            if(je.isDirectory() || !je.getName().endsWith(".class")){
	                continue;
	            }
	         String className = je.getName().substring(0,je.getName().length()-6);//remove ".class"
	         className = className.replace('/', '.');
	         if(classList == null)
	        	 classList = new ArrayList<String>();
	         classList.add(className);
	        }
		} finally {
			if(jarfile != null)
				jarfile.close();			
		}
		return classList;
	}

	protected List<String> classNamesFromLibJar() throws IOException {
		
		JarFile jarfile = null;
		List<String> classList = null;		
		
		try {
			jarfile = new JarFile(jarFilePath);					
			
	        Enumeration<JarEntry> jarentry = jarfile.entries();
	        while (jarentry.hasMoreElements()) {
	        	JarEntry je = jarentry.nextElement();
	            if(je.isDirectory() || !je.getName().endsWith(".class")){
	                continue;
	            }
	         String className = je.getName().substring(0,je.getName().length()-6);//remove ".class"
	         className = className.replace('/', '.');
	         if(classList == null)
	        	 classList = new ArrayList<String>();
	         classList.add(className);
	        }
		} finally {
			if(jarfile != null)
				jarfile.close();			
		}
		return classList;
	}

	protected List<String> jarLibNamesFromJar() throws IOException {
		
		JarFile jarfile = null;
		List<String> classList = null;		
		
		try {
			jarfile = new JarFile(jarFilePath);					
			
	        Enumeration<JarEntry> jarentry = jarfile.entries();
	        while (jarentry.hasMoreElements()) {
	        	JarEntry je = jarentry.nextElement();
	            if(!je.getName().endsWith(".jar")){
	                continue;
	            }
	         String jarlibPath = je.getName();
	         if(classList == null)
	        	 classList = new ArrayList<String>();
	         classList.add(jarlibPath);
	        }
		} finally {
			if(jarfile != null)
				jarfile.close();			
		}
		return classList;
	}

	protected String[] getIotHandersFromManifest() throws IOException {
		JarFile jarfile = null;
		String[] nameStrs;
		try {
			jarfile = new JarFile(jarFilePath);
			
			String  iotHnalClsName = jarfile.getManifest().getMainAttributes().getValue("Implementation-Title");
		
			nameStrs = iotHnalClsName.replaceAll("\\s","").split(",");
		}finally {
			if(jarfile != null)
				jarfile.close();
		}
		
		return nameStrs;
	}

	/**
     * Closes all open jar files
     * @throws MalformedURLException 
     */	
    @SuppressWarnings("rawtypes")
	public void close() {
        try {
            Class clazz = java.net.URLClassLoader.class;
            Field ucp = clazz.getDeclaredField("ucp");
            ucp.setAccessible(true);
            Object sunMiscURLClassPath = ucp.get(this);
            Field loaders = sunMiscURLClassPath.getClass().getDeclaredField("loaders");
            loaders.setAccessible(true);
            Object collection = loaders.get(sunMiscURLClassPath);
            for (Object sunMiscURLClassPathJarLoader : ((Collection) collection).toArray()) {
                try {
                    Field loader = sunMiscURLClassPathJarLoader.getClass().getDeclaredField("jar");
                    loader.setAccessible(true);
                    Object jarFile = loader.get(sunMiscURLClassPathJarLoader);
                    ((JarFile) jarFile).close();
                } catch (Throwable t) {
                    // if we got this far, this is probably not a JAR loader so skip it
                }
            }
        } catch (Throwable t) {
            // probably not a SUN VM
        }
        return;
    }
}