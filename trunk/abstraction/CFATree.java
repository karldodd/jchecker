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

	void beginForwardSearch(ArrayList<Predicate> predArray)
	{
		Node firstNode = cg.firstNode();
		StateSpace ssInit = new StateSpace(predArray);
//		firstNode.changeStateSpace(ssInit);
		firstNode.stateSpaceStack.push(ssInit);
		forwardSearch(firstNode);

		firstNode.stateSpaceStack.pop();
	}

	int forwardSearch(Node node)
	{
		Node nextNode;
		StateSpace preSs = node.stateSpaceStack.peek();
		StateSpace nextSs;
		int numOfCallback = 1;	//default, delete tail node
		
//		System.out.println(node.id);	//For test

		if (node.isError())
		{
			numOfCallback = backTrace();
			return numOfCallback;
			//give a sign here, how many nodes should be removed

			//There should be other process after that
		}
//		else if (endCycle(edge))
//		{
//			backTrace();
//		}

		else
		{
			for (Edge edge : node.outEdge)
			{
				nextNode = edge.tailNode;
				nextSs = calStateSpace(preSs, edge);

				if ( nextSs.stateSign == STATE_FALSE )
				{
					continue;
				}
				if ( nextNode.id < node.id )	//cycle back
				{
					StateSpace cycleSs = nextNode.stateSpaceStack.peek();
					if ( nextSs.imply(cycleSs) )
					{
						continue;
					}
				}
				recordTrace(edge);
				nextNode.stateSpaceStack.push(nextSs);

				numOfCallback = forwardSearch(nextnode);
				if ( num > 0 )
				{
					nextNode.stateSpaceStack.pop();
					removeTrace(edge);
					return num - 1;
				}
			}
//			System.out.println(node.id);	//For test
		}

		return numOfCallback;

	}

	StateSpace calStateSpace(StateSpace preSs, Edge edge)
	{
		//The process of calculating...
/*
		for (predPre in StateSpacePre, predCur in node)
		{
			sen = edge.sentence;
			if ( predPre implies !sen )
			{
				prenext = pfalse;
				return ;
			}
			else if ( predCur implies wp(sen, predPre) )
			{
				prenext = predpre;
			}
			else if ( predcur implies wp(sen, !predpre) )
			{
				prenext = !predpre;
			}
			else
			{
				prenext = ptrue;
			}
		}
*/

/*
 *Below is pseudo-code

		StateSpace ss = n.getStateSpace();
		Map<Predicate, State> pMap = ss.getNewestPredicate();
//		Predicate pNew = ss.getNewestPredicate();
		Sentence sen = e.getSentence();
		StateSpace result;
		if ( sen.isAssignment() )
		{

//			Set<Map.Entry<Predicate, State>> ssSet = ss.entrySet();
//			for ( Map.Entry<Predicate, State> me : ssSet )
//			{}

			if ( pNew.implies(sen.negtive()) ) //ss implies !condition(sen)
			{
				ssNext = false;
			}
			else if ( ss implies wp(ss, pred) )
			{
				ssNext = ss;
			}
			else if ( ss implies wp(ss, !pred) )
			{
				ssNext = !ss;
			}
			else
			{
				ssNext = true;
			}
		}
		if ( sen.isCondition() )
		{
			if ( ss implies sen )
			{
				ssNext = ss;
			}
			else if (ss implies !sen )
			{
				back();	//??
			}
		}
		StateSpace wpSs = calWpSs(sen, pre); //pre = ss?
		

		return e.tailNode;
 *
 */
 		StateSpace nextSs = new StateSpace();
		Sentence sen = edge.sentence;

		for ( PredicateVector predVect : preSs.predVectArray)
		{
			Predicate pred = predVect.getPredicate();
			State predState = predVect.getState();
			Predicate truePred = calTruePredicate(pred, predState);
			State nextState;

			if (sen.isAssignment())
			{
				nextState = calAssignment(truePred, pred, predState, sen);
			}
			if (sen.isCondition())
			{
				nextState = calCondition(pred, predState, sen);
			}
			
			if (nextState == STATE_FALSE)
			{
				nextSs.stateSign = STATE_FALSE;
				//return STATE_FALSE;	//or not?
				//backTrace();
			}
			nextSs.add(new PredicateVector(pred, nextState));
		}

		return nextSs;
	}

	State calAssignment(Predicate truePred, Predicate pred, State predState, Sentence sen)
	{
		if (truePred.imply(sen.negtive()))
		{
			return STATE_FALSE;
		}
		if (truePred.imply(weakestPrecondition(sen, pre)))
		{
			return STATE_POS;
		}
		if (truePred.imply(weakestPrecondition(sen, pre.negtive())))
		{
			return STATE_NEG;
		}
		return STAE_TRUE;
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

	void removeTrace(Edge e)
	{
		edgeTrace.remove( edgeTrace.size()-1 );	//last edge
	}

//	void expandTree() {}

	int backTrace()
	{
		ArrayList<Edge> backEdgeTrace = new ArrayList<Edge>();

		for (int i = edgeTrace.size() - 1; i >= 0; i--)
		{
			backEdgeTrace.add(edgeTrace.get(i));
		}

		ArrayList<Node> backNodeTrace = new ArrayList<Node>();
		
		for (Edge e : backEdgeTrace)
		{
			backNodeTrace.add(e.tailNode);
		}
		backNodeTrace.add(edgeTrace.get(0).headNode);

		StateSpace backInitSs = new StateSpace(backNodeTrace.get(0).predVectorArray.peek());

		for (int i = 0; i < edgeTrace.size(); i++)
		{
			StateSpace preSs = backNodeTrace.get(i);
			Edge edge = backEdge.get(i);
			StateSpace nextSs = calStateSpace(preSs, edge);

			if ( nextSs.stateSign == STATE_FALSE )
			{
				//refinement
				ArrayList<Edge> linkEdge = new ArrayList<Edge>();
				for (int j = 0; j < i; j++)
				{
					//if isAssign
					Sentence senten = backEdgeTrace.get(j);
					if ( senten.isAssignment() )
					{
						Sentence stn = wp(backEdgeTrace.get(j), backEdgeTrace.get(j-1));
						linkSen.add(backEdgeTrace.get(j));
					}
					//if iscondition
					if ( senten.isCondition() )
					{					
						linkSen.add(backEdgeTrace.get(j));
					}
					//add stateSpace of falsenode
					//foci
					//cal if reachable
					//return numOfCallback
					//change predicate
					//...
				}
				//add stateSpace of falsenode
				linkSen.add(stateSpace);
				//foci
				callFoci(linkSen);
				//cal if reachable
				//return numOfCallback
				//change predicate
				//...
				int numOfCallback = refine();
				return numOfCallback;
			}
		}

		//This is true counter-example. End.
		//display(edgeArray);
		return edgeArray.size();	//pop out whole route
	}

//	boolean endCycle() {}
}
