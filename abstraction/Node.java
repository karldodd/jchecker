package abstraction;

import java.util.*;

public class Node
{
	static int num = 0;

	int id;
	boolean isErrorNode;
	ArrayList<Edge> inEdge;
	ArrayList<Edge> outEdge;
	StateSpace ss;
	Stack<StateSpace> stateSpaceStack;

	Node(boolean isErrorNode)
	{
		id = num++;
		this.isErrorNode = isErrorNode;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();
		ss = null;
		stateSpaceStack = new Stack<StateSpace>();
	}

	void addInEdge(Edge e)
	{
		inEdge.add(e);
	}
	
	void addOutEdge(Edge e)
	{
		outEdge.add(e);
	}

	boolean isError()
	{
		return isErrorNode;
	}

    	void setErrorNode(boolean boolval)
	{
		this.isErrorNode = boolval;
	}

	void pushStateSpace(StateSpace ss)
	{
		stateSpaceStack.push(ss);	
	
	}
	void popStateSpace()
	{
		stateSpaceStack.pop();	
	}

	StateSpace peekStateSpace(StateSpace ss)
	{
		return stateSpaceStack.peek();	
	}

	void initStateSpace(StateSpace preSs)
	{
		ss = StateSpace.createInitialStateSpace(preSs);	
	}

	boolean implyBy(StateSpace judgeSs)
	{
		for (int i=stateSpaceStack.size()-1; i>=0; i--)
		{
			if ( judgeSs.imply(stateSpaceStack.get(i)) )
				return true;
		}
		return false;
	}

	void display()
	{
//		Edge e = null;
		System.out.println("node id: " + id + " isErrorNode: " + isErrorNode);
		System.out.println("inEdge:");
		for (Edge e : inEdge)
		{
			if (e == null)
				System.out.println("null");
			else
				e.display();
		}
		System.out.println("outEdge:");
		for (Edge e : outEdge)
		{
			if (e == null)
				System.out.println("null");
			else
				e.display();
		}
	}

}
