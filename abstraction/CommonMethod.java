package abstraction;

import prover.*;
import tokens.*;

import java.util.*;

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
			System.exit(1);
			return null;
	    	}
	}

	public static void print(String s)
	{
		System.out.println(s);
	}
	
	public static void display(ArrayList<Edge> eTrace, ArrayList<Node> nTrace)
	{
		for (int i=0; i<eTrace.size(); i++)
		{
			//System.out.print(nTrace.get(i).id + "  " + eTrace.get(i).getID());
			//System.out.println("");
		}
		//System.out.println(nTrace.get(nTrace.size()-1).id);
	}	
}
