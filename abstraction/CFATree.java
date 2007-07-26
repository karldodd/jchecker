import java.util.ArrayList;

public class CFATree
{
	ArrayList<Node> nodeTrace = new ArrayList<Node>();
	CFAGraph cg;

	CFATree(CFAGraph g)
	{
		cg = g;
	}

	void beginForwardSearch()
	{
		forwardSearch(cg.firstNode());
	}

	Node forwardSearch(Node node)
	{
		recordTrace(node);
		System.out.println(node.id);	//For test
		if (node.isError())
		{
			backTrace();

			//There should be other process after that
		}
		for (Edge edge : node.outEdge)
		{
			forwardSearch(calExpression(node, edge));
		}
		System.out.println(node.id);	//For test
		removeTrace(node);

		return node;
	}

	Node calExpression(Node n, Edge e)
	{
		//The process of calculating...

		return e.tailNode;
	}

	void recordTrace(Node n)
	{
		nodeTrace.add(n);
	}

	void removeTrace(Node n)
	{
		nodeTrace.remove(n);
	}

	void expandTree() {}
	void backTrace() {}
}
