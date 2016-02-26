package de.tu_darmstadt.gdi1.plumber.controller.exceptioncatcher;

import java.util.Iterator;
import java.util.Vector;

public class ExceptionCatcher{
	
	private static Vector<Exception> exceptionstack = new Vector<Exception>();
	private static boolean defaultoutput = true;
	private static DefaultOutputHandler defaultoutputhandler = new DefaultOutputHandler();
	private static Vector<ExceptionOutputHandler> outputhandlers = new Vector<ExceptionOutputHandler>();

	public static void add(Exception e)
	{
		exceptionstack.add(e);
		
		if(defaultoutput)
		{
			defaultoutputhandler.add(e);
		}
		
		//other outputhandlers
		Iterator<ExceptionOutputHandler> itr = outputhandlers.iterator();
		
		//iterate over vector
		while(itr.hasNext())
		{
			itr.next().add(e); //call add-function of every outputhandler
		}
	}
	
	public static void clear()
	{
		exceptionstack.clear();
		
		if(defaultoutput)
		{
			defaultoutputhandler.clear();
		}
		
		//other outputhandlers
		Iterator<ExceptionOutputHandler> itr = outputhandlers.iterator();
		
		//iterate over vector
		while(itr.hasNext())
		{
			itr.next().clear(); //call clear-function of every outputhandler
		}
	}
	
	public static Vector<Exception> getExceptionstack()
	{
		return exceptionstack;
	}
	
	public static boolean hasOutputHandler(ExceptionOutputHandler handler)
	{
		return outputhandlers.contains(handler);
	}
	
	public static boolean addOutputHandler(ExceptionOutputHandler handler)
	{
		if(hasOutputHandler(handler))
		{
			return false;
		}
		
		return outputhandlers.add(handler);
	}
	
	public static boolean removeOutputHandler(ExceptionOutputHandler handler)
	{
		return outputhandlers.remove(handler);
	}
	
	public static boolean isDefaultoutput() {
		return defaultoutput;
	}

	public static void setDefaultoutput(boolean defaultoutput) {
		ExceptionCatcher.defaultoutput = defaultoutput;
	}
	
}
