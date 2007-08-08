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

	Node(int id)
	{
		this.id = id;
		this.isErrorNode = false;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();
		ss = null;
		stateSpaceStack = new Stack<StateSpace>();
	}

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

	StateSpace popStateSpace()
	{
		return stateSpaceStack.pop();	
	}

	StateSpace peekStateSpace()
	{
		return stateSpaceStack.peek();	
	}

	void initStateSpace(StateSpace preSs)
	{
		ss = StateSpace.createInitialStateSpace(preSs);	
	}

	void initStateSpace(Predicate p)
	{
		ss = new StateSpace();
		ss.add(new PredicateVector(p, State.STATE_TRUE));
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
			e.display();
		}
		System.out.println("outEdge:");
		for (Edge e : outEdge)
		{
			e.display();
		}
		if (ss != null)
		{
			System.out.println("single state space: ");
			ss.display();
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
				System.out.println(i + " state space:");
				stateSpaceStack.get(i).display();
			}
			System.out.println("end state space stack");
		}
		System.out.println("");
	}

}
