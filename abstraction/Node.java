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

	Node()
	{
		id = 0;
		isErrorNode = false;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();
		ss = null;
		stateSpaceStack = new Stack<StateSpace>();
	}

	Node(int i)
	{
		id = i;
		isErrorNode = false;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();
		ss = null;
		stateSpaceStack = new Stack<StateSpace>();
	}

	Node(int n, boolean err, Edge ie, Edge oe, StateSpace s)
	{
		id = n;			//need?
		isErrorNode = err;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();
		ss = null;
		stateSpaceStack = new Stack<StateSpace>();
		if (ie != null)			//no need
			inEdge.add(ie);
		if (oe != null)			//no need
			outEdge.add(oe);
		ss = s;
//		cg.addNode(this);
	}

	Node(boolean err, Edge ie, Edge oe, StateSpace s)
	{
		id = num++;	//need?
		isErrorNode = err;
		inEdge.add(ie);
		outEdge.add(oe);
		ss = s;
//		cg.addNode(this);
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

	void changeStateSpace(StateSpace ssCh)
	{
		ss = ssCh;
	}

	void initStateSpace(StateSpace preSs)
	{
		ss = StateSpace.initialStateSpace(preSs);	
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
	
    //Constructor created by karldodd
    Node(boolean isErrorNode)
    {
	id = num++;
	this.isErrorNode = isErrorNode;
	inEdge = new ArrayList<Edge>();
	outEdge = new ArrayList<Edge>();
	ss = null;
	stateSpaceStack = new Stack<StateSpace>();
    }

    void setErrorNode(boolean boolval){
	this.isErrorNode=boolval;
    }
}
