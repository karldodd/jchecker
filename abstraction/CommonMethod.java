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
	
	public static void display(ArrayList<Edge> eTrace, ArrayList<Node> nTrace)
	{
		for (int i=0; i<eTrace.size(); i++)
		{
			System.out.print(nTrace.get(i).id + "  " + eTrace.get(i).id);
			System.out.println("");
		}
		System.out.println(nTrace.get(nTrace.size()-1).id);
	}

	public static void display(ArrayList<Edge> eTrace)
	{
		System.out.print(eTrace.get(0).headNode.id);
		for (Edge e : eTrace)
		{
			System.out.print(" " + e.tailNode.id);
		}
	}
	
	public static void display(ArrayList<Predicate> list)
	{
		for (Predicate l : list)
		{
			l.display();
		}
	}
	
	public static void display(ArrayList<StateSpace> list)
	{
		for (StateSpace l : list)
		{
			l.display();
		}
	}
	
	public static void print(String s)
	{
		System.out.println(s);
	}
	
	public static println()
	{
		System.out.println();
	} 
}
