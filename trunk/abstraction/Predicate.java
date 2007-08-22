package abstraction;

import tokens.*;
import prover.*;

import java.lang.*;
import java.util.*;

public class Predicate
{
	private AdvCondition c;

	public Predicate(AdvCondition c)
	{
		this.c = c;
	}
	
	public Predicate clone()
	{
		return new Predicate(c.clone());
	}

	public AdvCondition getAdvCondition()
	{
		return c.clone();
	}
	
	public String toString()
	{
		return c.toString();
	}

	public String toLFString()
	{
		return c.toLFString();
	}

	void display()
	{
		System.out.println("predicate is: " + c.toString());
	}
	
//	public boolean equals(Predicate p, Prover prover){
//		return this.getAdvCondition().equals(p.getAdvCondition(),prover);
//	}

	public boolean equals(Object o)
	{
		if(o instanceof Predicate)
		{
			Predicate p=(Predicate)o;
			return c.toString().equals(p.getAdvCondition().toString());
		}
		else return false;
		//return c.toString().equals(p.getAdvCondition().toString());
	}
	
	public boolean equals(Predicate p)
	{
		return c.toString().equals(p.getAdvCondition().toString());
	}

	public int hashCode()
	{
		return this.toString().hashCode();
	}
}
