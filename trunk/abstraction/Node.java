import java.util.ArrayList;

public class Node
{
	static int num = 0;

	int id;
	boolean isErrorNode = false;
	ArrayList<Edge> inEdge = new ArrayList<Edge>();
	ArrayList<Edge> outEdge = new ArrayList<Edge>();
	SpaceState ss = null;

	Node()
	{
		id = 0;
	}

	Node(int n, boolean err, Edge ie, Edge oe, SpaceState s)
	{
		id = n;			//need?
		isErrorNode = err;
		if (ie != null)			//no need
			inEdge.add(ie);
		if (oe != null)			//no need
			outEdge.add(oe);
		ss = s;
//		cg.addNode(this);
	}

	Node(boolean err, Edge ie, Edge oe, SpaceState s)
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
