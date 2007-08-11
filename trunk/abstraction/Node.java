package abstraction;

import java.util.*;

public class Node
{
	private static int num = 0;

	private int id;
	private boolean isErrorNode;
	private ArrayList<Edge> inEdge;
	private ArrayList<Edge> outEdge;
	private Stack<StateSpace> stateSpaceStack;

	public Node(boolean isErrorNode)
	{
		id = num++;
		this.isErrorNode = isErrorNode;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();		
		stateSpaceStack = new Stack<StateSpace>();
	}

	public void addInEdge(Edge e)
	{
		inEdge.add(e);
	}
	
	public void addOutEdge(Edge e)
	{
		outEdge.add(e);
	}

	public boolean isError()
	{
		return isErrorNode;
	}

   public void setErrorNode(boolean boolval)
	{
		this.isErrorNode = boolval;
	}

	public void pushStateSpace(StateSpace ss)
	{
		stateSpaceStack.push(ss);	
	}

	public StateSpace popStateSpace()
	{
		if (stateSpaceStack.isEmpty())
		{
			System.out.println("Error: state space of node " + id + " is empty.");
			return null;
		}
		return stateSpaceStack.pop();	
	}

	public StateSpace peekStateSpace()
	{
		if (stateSpaceStack.isEmpty())
		{
			System.out.println("Error: state space of node " + id + " is empty.");
			return null;
		}
		return stateSpaceStack.peek();	
	}
	
	public int getID()
	{
		return id;
	}	
	
	public ArrayList<Edge> getInEdge()
	{
		return inEdge;
	}
	
	public ArrayList<Edge> getOutEdge()
	{
		return outEdge;
	}
	
	public int stackSize()
	{
		return stateSpaceStack.size();
	}
	
	public StateSpace getStack(int i)
	{
		return stateSpaceStack.get(i);
	}

	void display()
	{
		System.out.println("node id: " + id + "   isErrorNode: " + isErrorNode);
		System.out.println("inEdge:");
		for (Edge e : inEdge)
		{
			e.display();
		}
		System.out.println("outEdge:");
		for (Edge e : outEdge)
		{
			e.display();
		}		
		if (stateSpaceStack.empty())
		{
			System.out.println("state space stack is empty");
		}
		else
		{
			System.out.println("state space stack: ");
			for (int i=0; i<stateSpaceStack.size(); i++)
			{
				System.out.println("##################   " + i + "   ##################");
				stateSpaceStack.get(i).display();
			}
			System.out.println("end state space stack");
		}
		System.out.println("");
	}

}
