package abstraction;

import tokens.*;
import java.util.ArrayList;

public class Edge
{
	static int num = 0;

	int id;
	Node headNode;
	Node tailNode;
	Sentence sentence;

	Edge()
	{
		id = 0;
		headNode = null;
		tailNode = null;
		sentence = null;
	}

	Edge(int n, Node hn, Node tn, Sentence s)
	{
		id = n;
		headNode = hn;
		tailNode = tn;
		sentence = s;
	}

	Edge(Node hn, Node tn, Sentence s)
	{
		id = num++;
		headNode = hn;
		tailNode = tn;
		sentence = s;
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
		System.out.println(" Sentence: " + sentence.toString());
	}
}
