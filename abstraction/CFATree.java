package abstraction;

import tokens.*;
import prover.*;

import java.util.*;

public class CFATree
{
	ArrayList<Edge> edgeTrace;
	CFAGraph cg;

	CFATree(CFAGraph g)
	{
		edgeTrace = new ArrayList<Edge>();
		cg = g;
	}

	public void beginForwardSearch(ArrayList<Predicate> predArray)
	{
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
		int numBack = 1;	//default, delete end node
		
		if (node.isError())
		{
			//numBack means how many nodes should back
			numBack = backTrace();
			return numBack;
		}
		else
		{
			for (Edge edge : node.outEdge)
			{
				nextNode = edge.tailNode;
				nextSs = calStateSpace(preSs, edge);

				if ( nextSs.isFalse() )
				{
					//can't walk, continue next route
					continue;
				}
				if ( nextNode.id < node.id )	//cycle back
				{
					if ( node.impliedBy(nextSs) )
					{
						continue;
					}
				}
				recordTrace(edge);
				nextNode.pushStateSpace(nextSs);

				numBack = forwardSearch(nextNode);

				if ( numBack > 0 )
				{
					nextNode.popStateSpace();
					removeTrace();
					return numBack - 1;
				}
			}
		}

		return numBack;

	}

	StateSpace calStateSpace(StateSpace preSs, Edge edge)
	{
 		StateSpace nextSs = new StateSpace();
		EdgeLabel label = edge.label;

		for ( PredicateVector predVect : preSs.predVectArray)
		{
			Predicate pred = predVect.getPredicate();
			State predState = predVect.getState();
			Predicate predWithState = new Predicate(predVect.getAdvConditionByState());
			State nextState;

			if (label instanceof EvaluationSentence)
			{
				nextState = calAssignment(predWithState, pred, predState, label);
			}
			if (label instanceof AdvCondition)
			{
				nextState = calCondition(pred, predState, label);
			}
			
			if (nextState == STATE_FALSE)
			{
				nextSs.stateSign = STATE_FALSE;
				return nextSs;
			}
			nextSs.add(new PredicateVector(pred, nextState));
		}

		return nextSs;
	}

	//karldodd: what is predWithState and pred?
	State calAssignment(Predicate predWithState, Predicate pred, State predState, Sentence sen)
	{
		if (predWithState.imply(sen.getNegativeCopy)
		{
			return STATE_FALSE;
		}
		if (predWithState.imply(weakestPrecondition(sen, pre)))
		{
			return STATE_POS;
		}
		if (predWithState.imply(weakestPrecondition(sen, pre.negtive())))
		{
			return STATE_NEG;
		}
		return STATE_TRUE;
	}

	State calCondition(Predicate pred, State predState, Sentence sen)
	{
		if (sen.imply(pred))
		{
			switch(predState)
			{
				case STATE_POS: return STATE_POS;
				case STATE_NEG: return STATE_FALSE;
				case STATE_TRUE: return STATE_POS;
				//case STATE_NEG: return STATE_FALSE;
				//defaults: return predState;
			}
		}
		if (sen.imply(pred.negtive()))
		{
			
			switch(predState)
			{
				case STATE_POS: return STATE_FALSE;
				case STATE_NEG: return STATE_NEG;
				case STATE_TRUE: return STATE_NEG;
				//case STATE_POS: return STATE_FALSE;
				//defaults: return predState;
			}
		}
		return predState;	//inherit
	}
	
	Predicate calTruePredicate(Predicate p, State s)
	{
		return p.calTruePredicate(s);
	}

	void recordTrace(Edge e)
	{
		edgeTrace.add(e);
	}

	void removeTrace()
	{
		edgeTrace.remove( edgeTrace.size()-1 );	//last edge
	}

	int backTrace()
	{
		ArrayList<Edge> cloneEdgeTrace = new ArrayList<edge>();
		ArrayList<Node> cloneNodeTrace = new ArrayList<Node>();

		createRefineRoute(cloneEdgeTrace, cloneNodeTrace);
/*
		//add edge trace from bottom to top
		for (int i = edgeTrace.size() - 1; i >= 0; i--)
		{
			backEdgeTrace.add(edgeTrace.get(i));
		}

		//add nodes
		ArrayList<Node> backNodeTrace = new ArrayList<Node>();
		
		for (Edge e : backEdgeTrace)
		{
			backNodeTrace.add(e.tailNode);
		}
		backNodeTrace.add(edgeTrace.get(0).headNode);
*/
//		StateSpace backInitSs = StateSpace.createInitialStateSpace(backNodeTrace.get(0).predVectorArray.peek());

		//Karldodd: There is no edge.predVectorArray...
		cloneNodeTrace.get(cloneNodeTrace.size()-1).initStateSpace(edgeTrace.get(edgeTrace.size()-1).tailNode.predVetorArray.peek());

		for (int i = cloneEdgeTrace.size()-1;  i >= 0;  i--)
		{
			StateSpace preSs = cloneNodeTrace.get(i+1).ss;
			Edge edge = cloneEdgeTrace.get(i);
			StateSpace nextSs = calStateSpace(preSs, edge);

			//karldodd: we should intersect the "nextSs" with the former one, and test if it is satifiable.
			if ( nextSs.stateSign == STATE_FALSE )
			{
				//refinement
				ArrayList<EdgeLabel> linkLabel = new ArrayList<EdgeLabel>();
				EdgeLabel newLabel = cloneEdgeTrace.get(cloneEdgeTrace.size()-1).label;
				for (int j = cloneEdgeTrace.size()-2;  j >= i;  j--)
				{
					//if isAssign
					EdgeLabel label = cloneEdgeTrace.get(j).label;
					if ( label.isAssignment() )
					{
						newlabel = wp(newLabel, Label);
					}
					//if iscondition
					if ( label.isCondition() )
					{	
						//do nothing
					}
				}
				linkLabel.add(newLabel);
				//add stateSpace of falsenode
				for (PredicateVector pv : nextSs)
				{
					linkLabel.add(pv.getPredicate());
				}
				//call foci
				Prover p = ProverFatory.getProverByName("focivampyre");
				Set<Predicate> pSet = p.getInterpolation(linkLabel);

				int numBack = 0;
				boolean endCyle = false;
				for (Predicate p : pSet)
				{
					cloneNodeTrace.get(0).createInitialSpace(p);
					StateSpae preSs = cloneNodeTrace.get(0).ss;
					for (int i = 0; i < cloneEdgeTrace.size(); i++)
					{
						StateSpace nextSs = calStateSpace(preSs, cloneEdgeTrace(i).label);
						if( nextSs == STATE_FALSE )
						{
							numBack = cloneEdgeTrace.size() - i;
							endCycle = true;
							break;
						}
					}
					if (endCycle)  break;
				}

				return numBack;
				break;	
			}
		}

		//This is true counter-example. End.
		display(cloneEdgeTrace, cloneNodeTrace);
		return cloneEdgeTrace.size();	//pop out whole route
	}

	void cloneRefineRoute(ArrayList<Edge> eTrace, ArrayList<Node> nTrace)
	{
		Node n = new Node(edgeTrace.get(0).headNode.id);
		nTrace.add(n);
		for (int i=0; i<edgeTrace.size(); i++)
		{
			Edge e = edgeTrace.get(i);
			Node n = new Node(e.tailNode.id);
			nTrace.add(n);
			Edge newEdge = new Edge(e.id, e.label);
			newEdge.headNode = nTrace.get(i);
			newEdge.tailNode = nTrace.get(i+1);
			nTrace.get(i).addOutEdge(newEdge);
			nTrace.get(i+1).addInEdge(newEdge);
			eTrace.add(newEdge);
		}
	}
}
