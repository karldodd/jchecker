import java.util.ArrayList;

public class CFAGraph
{
	public ArrayList<Node> nodeList = new ArrayList<Node>();

	CFAGraph()
	{
		//h0 is head node
		Node h0 = new Node();
		nodeList.add(h0);
		//redundance
		Node h1 = new Node(1, false, null, null, null);
		nodeList.add(h1);
	}

	void addNode(Node n)
	{
		nodeList.add(n);
	}

	Node firstNode()
	{
		return nodeList.get(1);
	}

	void changeNodeStateSpace(int index, StateSpace ss)
	{
		nodeList.get(index).changeStateSpace(ss);
	}
	
	void display()
	{
		//This function is only for test
		for (Node n : nodeList)
		{
			n.display();
			System.out.println("");
		}
	}
}
