package abstraction;

import tokens.*;

public class Edge
{
	private static int num = 0;

	private int id;
	private Node headNode;
	private Node tailNode;
	private EdgeLabel label;

	public Edge(int id, EdgeLabel l)
	{
		this.id = id;
		headNode = null;
		tailNode = null;
		label = l;
	}

	public Edge(Node hn, Node tn, EdgeLabel l)
	{
		id = num++;
		headNode = hn;
		tailNode = tn;
		label = l;
	}
	
	public int getID()
	{
		return id;
	}

	public Node getHeadNode()
	{
		return headNode;
	}
	
	public Node getTailNode()
	{
		return tailNode;
	}
	
	public EdgeLabel getLabel()
	{
		return label;
	}
	
	void display()
	{
		System.out.print("Edge id: " + id + " headnode: " + headNode.getID() + " tailnode: " + tailNode.getID());
		System.out.println(" Label: " + label.toString());		
	}
}
