package abstraction;

import prover.*;
import tokens.*;

import java.util.*;

/**
*CommomMethod类提供abstraction包中用到的公用方法
*
*@author Li Jiang
*/
public class CommonMethod
{
	/**
	*得到证明器，证明器包括vampyer和foci
	*/
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

	/**
	*显示一段字符串
	*/
	public static void print(String s)
	{
		System.out.println(s);
	}
	
	/**
	*显示CFA图，测使用
	*/
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
