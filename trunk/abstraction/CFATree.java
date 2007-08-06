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

	//karldodd: what does this function return? what does numOfCallBack mean?
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
					StateSpace cycleSs = nextNode.stateSpaceStack.peek();//karldodd: why only peek , not all?
					if ( nextSs.imply(cycleSs) )
					{
						continue;
					}
				}
				recordTrace(edge);
				nextNode.stateSpaceStack.push(nextSs);

				numOfCallback = forwardSearch(nextnode);

				//karldodd: what is num?
				if ( numOfCallback > 0 )
				{
					nextNode.stateSpaceStack.pop();
					removeTrace(edge); //karldodd: what does revomeTrace(edge) mean?
					return numOfCallback - 1;
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

	//karldodd: what is truePred and pred?
	State calAssignment(Predicate truePred, Predicate pred, State predState, Sentence sen)
	{
		if (truePred.imply(sen.getNegativeCopy)
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

	//karldodd: but where is the parameter e?
	void removeTrace(Edge e)
	{
		edgeTrace.remove( edgeTrace.size()-1 );	//last edge
	}

//	void expandTree() {}

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

				int numOfCallback = 0;
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
							numOfCallback = cloneEdgeTrace.size() - i;
							endCycle = true;
							break;
						}
					}
					if (endCycle)  break;
				}

				return numOfCallback;
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
/*
	ArrayList<Edge> clone(ArrayList<Edge> preList)
	{
		ArrayList<Edge> newList = new ArrayList<Edge>();
		for (Edge e : preList)
		{
			Node newHeadNode = new Node(e.headNode.id);
			Node newTailNode = new Node(e.tailNode.id);
			//karldodd: what does this mean?
			Edge cloneEdge = new Edge
			//karldodd: I cannot understand Edge.clone()
			newList.add(e.clone());
		}
		return newList;
	}


	//karldodd: Node.headNode is invalid
	ArrayList<Node> clone(ArrayList<Node> preList)
	{
		int i;
		ArrayList<Node> newList = new ArrayList<Node>();
		for (i=0; i<preList.size(); i++ )
		{
			newList.add(preList.get(i).headNode);
		}
		newList.add(preList.get(i-1).tailNode);
		return newList;
	}
*/
//	boolean endCycle() {}
}
