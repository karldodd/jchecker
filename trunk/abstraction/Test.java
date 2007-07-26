public class Test
{
	CFAGraph createGraph()
	{
		CFAGraph cg = new CFAGraph();
		
		//Create my own tree
		Node h1 = cg.nodeList.get(1);
		Edge e1 = new Edge(1, h1, null, new Sentence("x=0"));
		h1.addOutEdge(e1);
		Node h2 = new Node(2, false, e1, null, null);
		cg.addNode(h2);
		e1.changeTailNode(h2);

		//We can use only one e and h, but for the reason of expliciting the process, we use index e and h
		Edge e2 = new Edge(2, h2, null, new Sentence("x<4"));
		h2.addOutEdge(e2);
		Node h3 = new Node(3, false, e2, null, null);
		cg.addNode(h3);
		e2.changeTailNode(h3);

//		Edge e3 = new Edge(3, h3, h2, new Sentence("x=x+1"));
//		h3.addOutEdge(e3);
//		h2.addInEdge(e3);

		Edge e4 = new Edge(4, h2, null, new Sentence("x>=4"));
		h2.addOutEdge(e4);
		Node h4 = new Node(4, false, e4, null, null);
		cg.addNode(h4);
		e4.changeTailNode(h4);

		Edge e5 = new Edge(5, h4, null, new Sentence("x==4"));
		h4.addOutEdge(e5);
		Node h5 = new Node(5, false, e5, null, null);
		cg.addNode(h5);
		e5.changeTailNode(h5);

		Edge e6 = new Edge(6, h4, null, new Sentence("x>4"));
		h4.addOutEdge(e6);
		Node h6 = new Node(6, true, e6, null, null);
		cg.addNode(h6);
		e6.changeTailNode(h6);

		return cg;
	}

	public static void main(String[] args)
	{
		Test t = new Test();
		CFAGraph cg = t.createGraph();
		cg.display();
		CFATree ct = new CFATree(cg);
		ct.beginForwardSearch();
	}
}
