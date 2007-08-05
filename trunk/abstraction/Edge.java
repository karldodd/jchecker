package abstraction;

import tokens.*;
import java.util.ArrayList;

public class Edge
{
	static int num = 0;

	int id;
	Node headNode;
	Node tailNode;
        EdgeLabel label;
    //Sentence sentence;

	Edge()
	{
		id = 0;
		headNode = null;
		tailNode = null;
		label = null;
	}

	Edge(int i, EdgeLabel l)
	{
		id = i;
		headNode = null;
		tailNode = null;
		label = l;
	}

	Edge(int n, Node hn, Node tn, EdgeLabel l)
	{
		id = n;
		headNode = hn;
		tailNode = tn;
		label = l;
	}

	Edge(Node hn, Node tn, EdgeLabel l)
	{
		id = num++;
		headNode = hn;
		tailNode = tn;
		label = l;
	}

	//karldodd: what do newHeadNode do?
	Edge clone(Edge preEdge)
	{
		Edge newEdge = new Edge();
		newEdge.id = preEdge.id;
		Node newHeadNode = new Node();
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
