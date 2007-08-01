import java.util.ArrayList;

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
	}

	Node forwardSearch(Node node)
	{
		Node nextNode;
		StateSpace preSs = node.stateSpaceStack.peek();
		StateSpace nextSs;
		
		System.out.println(node.id);	//For test

		if (node.isError())
		{
			backTrace();

			//There should be other process after that
		}
		else if (endCycle(edge))
		{
			backTrace();
		}

		else
		{
			for (Edge edge : node.outEdge)
			{
				recordTrace(edge);

				nextNode = edge.tailNode;
				nextSs = calStateSpace(preSs, edge);
				nextNode.stateSpaceStack.push(nextSs);

				forwardSearch(nextnode);
				removeTrace(edge);
			}
			System.out.println(node.id);	//For test
		}

		return node;
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
		edgeTrace.remove(e);
	}

//	void expandTree() {}
	void backTrace() {}
	boolean endCycle() {}
}
