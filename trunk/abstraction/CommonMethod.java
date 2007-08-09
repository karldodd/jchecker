package abstraction;

import prover.*;
import tokens.*;

public class CommonMethod
{
	public static Prover getProverInstance()
	{
	  	try
		{
		  	Prover p = ProverFactory.getProverByName("focivampyre");
			return p;
	    	}
	 	catch(Exception e)
	    	{
			System.err.println("Error: Prover not found...");
			return null;
	    	}
	}

	public static void print(String s)
	{
		System.out.println(s);
	}
}
