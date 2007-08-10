package abstraction;

import tokens.*;
import prover.*;

import java.util.*;

public class CFATree
{
	ArrayList<Edge> edgeTrace;
	CFAGraph cg;

	public CFATree(CFAGraph g)
	{
		edgeTrace = new ArrayList<Edge>();
		cg = g;
	}
        
   public void beginForwardSearch(ArrayList<Predicate> predArray)
	{
		//initial state space of first node and start searching
		Node firstNode = cg.firstNode();
		StateSpace ssInit = StateSpace.createInitialStateSpace(predArray);
		firstNode.pushStateSpace(ssInit);
		forwardSearch(firstNode);

		firstNode.popStateSpace();
		
		System.out.println("Forward search has finished successfully.");
		System.exit(0);
	}

	int forwardSearch(Node node)
	{
/*	
		System.out.println("*********************************************");
		System.out.println("Now is node " + node.id);
		System.out.println("*********************************************");
*/		
		Node nextNode;
		StateSpace preSs = node.peekStateSpace();
		StateSpace nextSs;
		//numBack means how many nodes should back, predToAdd returns predicate to be added
		int numBack = 0;	//default value is 0, stands for leaf node		
		
		if (node.isError())
		{
			//encounter error node
/*			
			int i;
			System.out.println("*********************************************");
			System.out.println("BEGIN before backTrace...... The route is:");
			System.out.println("*********************************************");
			for (i=0; i<edgeTrace.size(); i++)
			{
				System.out.println("Node " + i + " of trace:");
				edgeTrace.get(i).headNode.display();
				System.out.println("Edge " + i + " of trace:");
				//edgeTrace.get(i).display();
				//System.out.println("");
			}
			System.out.println("Node " + i + " of trace:");
			edgeTrace.get(i-1).tailNode.display();
			System.out.println("*********************************************");
			System.out.println("End before backTrace......");
			System.out.println("*********************************************");
			System.out.println("");
			//System.exit(0);
*/						
			numBack = backTrace();		
			return numBack;
		}
		else
		{
			//iterate whole tree
			for (Edge edge : node.outEdge)
			{
				nextNode = edge.tailNode;
/*				
				if (nextNode.id == 4)
				{
					System.out.println("*********************************************");
					System.out.println("Now coming node 4");
					System.out.println("*********************************************");
					preSs.display();
					edge.display();
					nextSs = StateSpace.calStateSpace(preSs, edge);
					nextSs.display();
					System.exit(0);
				}
*/				
				nextSs = StateSpace.calStateSpace(preSs, edge);

				if ( nextSs.isFalse() )
				{
					//can't walk, continue next route
					continue;
				}
				if ( nextNode.id < node.id )	//cycle back
				{
					if ( nextNode.implyBy(nextSs) )	//next state space implies previous state space of this same node
					{
						continue;
					}
				}
				recordTrace(edge);
				nextNode.pushStateSpace(nextSs);
/*				
				System.out.println("*********************************************");
				System.out.println("next node is " + nextNode.id);
				System.out.println("*********************************************");
				nextNode.display();
				System.out.println("");
*/				
				numBack = forwardSearch(nextNode);
				
				nextNode.popStateSpace();
				removeTrace();

				if ( numBack > 0 )			//numBack > 0 means it's not the end of recall
				{					
					return numBack - 1;
				}
			}
		}
	
		return numBack;
	}		

	int backTrace()
	{
		ErrorRoute rr = new ErrorRoute(edgeTrace);
		rr.refine();	
		int numBack = rr.numBack();
		ArrayList<Predicate> predToAdd = rr.predToAdd();
		
		//this is true counter instance, end
		if (numBack == 0)
		{
			display(edgeTrace);
			return edgeTrace.size();	//pop out whole route
		}
		
		addNewPredicate(predToAdd);		
		return numBack;
	}
	
	void addNewPredicate(ArrayList<Predicate> pToAdd)
	{
		for (int i=edgeTrace.size()-1; i>=0; i--)
		{
			edgeTrace.get(i).tailNode.popStateSpace();
		}
		StateSpace ss = edgeTrace.get(0).headNode.popStateSpace();
		ArrayList<Predicate> pList = new ArrayList<Predicate>();
		for (PredicateVector pv : ss.predVectorArray)
		{
			pList.add(pv.getPredicate());
		}
		for (Predicate p : pToAdd)
		{
			pList.add(p);
		}
		System.out.println("*********************************************");
		System.out.println("New predicate list:");
		System.out.println("*********************************************");
		for (Predicate p : pList)		p.display();
		System.out.println("");
		edgeTrace = new ArrayList<Edge>();
		beginForwardSearch(pList);
	}
	
	void addNewPredicate(int numBack, ArrayList<Predicate> pToAdd)
	{
		//add new predicate and state space
		//first "pour" previous state space to another stack and then "pour" back, facilitating the calculation

		//pour out
		Stack<StateSpace> reverseStack = new Stack<StateSpace>();
		StateSpace ss = null;
		for (int i=edgeTrace.size()-1; i>=0; i--)
		{
			ss = edgeTrace.get(i).tailNode.popStateSpace();
			reverseStack.push(ss);
		}
		reverseStack.push( edgeTrace.get(0).headNode.popStateSpace()  );

		//merge old and new state space and pour in		
		ArrayList<StateSpace> addSs = new ArrayList<StateSpace>();
		ArrayList<StateSpace> temp = null;
		//head node
		for (Predicate predToAdd : pToAdd)
		{
			//initial state space of new predicate
			addSs.add(new StateSpace(new PredicateVector(predToAdd, State.STATE_TRUE)));
		}	
		ss = StateSpace.merge(reverseStack.pop(), addSs);		//merge
		edgeTrace.get(0).headNode.pushStateSpace(ss);
		
		//calculate state space and merge
		for (int i=0; i<edgeTrace.size()-numBack; i++)
		{
			Edge e = edgeTrace.get(i);
			temp = new ArrayList<StateSpace>();	
			for (StateSpace oldSs : addSs)
			{							
				temp.add(StateSpace.calStateSpace(oldSs, e));	//calculate only new predicate				
			}
			addSs = temp;
			ss = StateSpace.merge(reverseStack.pop(), addSs);	//merge
			e.tailNode.pushStateSpace(ss);		//pour back
		}
		
		//remain state spaces pour back
		for (int i=edgeTrace.size()-numBack; i<edgeTrace.size(); i++)
		{
			edgeTrace.get(i).tailNode.pushStateSpace(reverseStack.pop());
		}
		
		for (Edge ee : edgeTrace)
		{
			ee.headNode.display();
			System.out.println("");
		}
		edgeTrace.get(edgeTrace.size()-1).tailNode.display();
	
	}
	
	void recordTrace(Edge e)
	{
		edgeTrace.add(e);
	}

	void removeTrace()
	{
		edgeTrace.remove( edgeTrace.size()-1 );	//remove last edge
	}

	void display(ArrayList<Edge> eTrace, ArrayList<Node> nTrace)
	{
		for (int i=0; i<eTrace.size(); i++)
		{
			System.out.print(nTrace.get(i).id + "  " + eTrace.get(i).id);
			System.out.println("");
		}
		System.out.println(nTrace.get(nTrace.size()-1).id);
	}

	void display(ArrayList<Edge> eTrace)
	{
		System.out.println("");
		System.out.println("The counter instance is:");
		System.out.print(eTrace.get(0).headNode.id);
		for (Edge e : eTrace)
		{
			System.out.print(" " + e.tailNode.id);
		}
		System.out.println("");
	}
}
