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
/*
	public static void display(ArrayList<Object> list)
	{
		if (list.size()==0)return;
		if (list.get(0) instanceof Edge)
		{
			System.out.print(((Edge)list.get(0)).headNode.id);
			for (Object e : list)
			{
				System.out.print(" " + ((Edge)e).tailNode.id);
			}
		}
		else if (list.get(0) instanceof Predicate)
		{			
			for (Object l : list)
			{
				((Predicate)l).display();
			}
		}
		else if (list.get(0) instanceof StateSpace)
		{			
			for (Object l : list)
			{
				((StateSpace)l).display();
			}
		}
	}
*/
/*
	public static void display(ArrayList<Edge> list)
	{
		System.out.print(list.get(0).headNode.id);
			for (Edge e : list)
			{
				System.out.print(" " + e.tailNode.id);
			}
	}
*/
/*	
	public static void display(ArrayList<StateSpace> list)
	{
		
			for (StateSpace l : list)
			{
				l.display();				
			}
	}
*/	
	public static void println()
	{
		System.out.println();
	} 
	
	public static void comehere()
	{
		System.out.println("here");
	}
}
