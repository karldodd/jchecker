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
	}

	int forwardSearch(Node node)
	{
		Node nextNode;
		StateSpace preSs = node.peekStateSpace();
		StateSpace nextSs;
		//numBack means how many nodes should back, predToAdd returns predicate to be added
		int numBack = 0;	//default value is 0, stands for leaf node		
		
		if (node.isError())
		{
			//encounter error node			
			List<Predicate> predToAdd = null;		//new predicate should be added
			numBack = backTrace(predToAdd);			
			
			if (!predToAdd.isEmpty())
			{		
				addNewPredicate(numBack, predToAdd);
			}
			
			return numBack;
		}
		else
		{
			//iterate whole tree
			for (Edge edge : node.outEdge)
			{
				nextNode = edge.tailNode;
				nextSs = StateSpace.calStateSpace(preSs, edge);

				if ( nextSs.isFalse() )
				{
					//can't walk, continue next route
					continue;
				}
				if ( nextNode.id < node.id )	//cycle back
				{
					if ( node.implyBy(nextSs) )	//next state space implies previous state space of this same node
					{
						continue;
					}
				}
				recordTrace(edge);
				nextNode.pushStateSpace(nextSs);

				numBack = forwardSearch(nextNode);

				if ( numBack > 0 )			//numBack > 0 means it's not the end of recall
				{
					nextNode.popStateSpace();
					removeTrace();
					return numBack - 1;
				}
			}
		}

		return numBack;

	}	
	
	void recordTrace(Edge e)
	{
		edgeTrace.add(e);
	}

	void removeTrace()
	{
		edgeTrace.remove( edgeTrace.size()-1 );	//remove last edge
	}

	int backTrace(List<Predicate> predToAdd)
	{
		RefineRoute rr = new RefineRoute(edgeTrace);
		rr.refine();	
		int numBack = rr.numBack();
		predToAdd = rr.predToAdd();		

		//this is true counter instance, end
		if (numBack == 0)
		{
			display(edgeTrace);
			return edgeTrace.size();	//pop out whole route
		}
		
		return numBack;
	}
	
	void addNewPredicate(int numBack, List<Predicate> pToAdd)
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
		for (Predicate predToAdd : pToAdd)
		{
			addSs.add(new StateSpace(new PredicateVector(predToAdd, State.STATE_TRUE)));	//initial state space of new predicate
			ss = StateSpace.merge(reverseStack.pop(), addSs);		//merge
			edgeTrace.get(0).headNode.pushStateSpace(ss);
		}
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
		System.out.print(eTrace.get(0).headNode.id);
		for (Edge e : eTrace)
		{
			System.out.print(" " + e.tailNode.id);
		}
	}
}
