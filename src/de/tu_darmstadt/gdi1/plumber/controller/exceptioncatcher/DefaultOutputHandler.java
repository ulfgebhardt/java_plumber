package de.tu_darmstadt.gdi1.plumber.controller.exceptioncatcher;

public class DefaultOutputHandler implements ExceptionOutputHandler{
	
	public void add(Exception e)
	{
		//Write to cmd-line
		System.out.println(e.toString());
	}
	
	public void clear()
	{
		//do nothing here!
	}
	
}
