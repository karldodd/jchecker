package abstraction;

import tokens.*;

public class Edge
{
	static int num = 0;

	int id;
	Node headNode;
	Node tailNode;
        EdgeLabel label;

	Edge(int id, EdgeLabel l)
	{
		this.id = id;
		headNode = null;
		tailNode = null;
		label = l;
	}

	Edge(Node hn, Node tn, EdgeLabel l)
	{
		id = num++;
		headNode = hn;
		tailNode = tn;
		label = l;
	}

	void changeHeadNode(Node n)
	{
		headNode = n;
	}
	
	void changeTailNode(Node n)
	{
		tailNode = n;
	}
	
	void display()
	{
//		System.out.println("come here");
		System.out.print("Edge id: " + id + " headnode: " + headNode.id + " tailnode: " + tailNode.id);
		System.out.println(" Label: " + label.toString());
	}
}
