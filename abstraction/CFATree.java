package abstraction;

import tokens.*;
import prover.*;

import java.util.*;

public class CFATree
{
	ArrayList<Edge> edgeTrace;
	CFAGraph cg;
        
        public static Prover getProverInstance(){
	    try{
	    	Prover p = ProverFactory.getProverByName("focivampyre");
		return p;
	    }
	    catch(Exception e){
		System.err.println("Error: Prover not found...");
		return null;
	    }
	}

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
		int numBack = 0;	//default, delete end node
		
		if (node.isError())
		{
			//numBack means how many nodes should back, predBack returns predicate to be added
			Predicate predBack = null;
			numBack = backTrace(predBack);
			Stack<StateSpace> reverseStack = new Stack<StateSpace>();
			StateSpace ss = null;
			for (int i=edgeTrace.size()-1-numBack; i>=0; i--)
			{
				ss = edgeTrace.get(i).tailNode.popStateSpace();
				reverseStack.push(ss);
			}
			reverseStack.push( edgeTrace.get(0).headNode.popStateSpace()  );

			StateSpace addSs = new StateSpace();
			addSs.add( new PredicateVector(predBack, State.STATE_TRUE) );
			ss = StateSpace.merge(reverseStack.pop(), addSs);
			edgeTrace.get(0).headNode.pushStateSpace(ss);
			for (int i=0; i<edgeTrace.size()-1-numBack; i++)
			{
				addSs = calStateSpace(addSs, edgeTrace.get(i));
				ss = StateSpace.merge(reverseStack.pop(), addSs);
				edgeTrace.get(i).tailNode.pushStateSpace(ss);
			}

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
					if ( node.implyBy(nextSs) )
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

		for ( PredicateVector predVect : preSs.predVectorArray)
		{
			Predicate pred = predVect.getPredicate();
			State predState = predVect.getState();
			Predicate predWithState = new Predicate(predVect.getAdvConditionByState());
			State nextState;

			if (label instanceof EvaluationSentence)
			{
				nextState = calAssignment(predWithState, pred, predState, label);
			}
			else if (label instanceof AdvCondition)
			{
				nextState = calCondition(pred, predState, label);
			}
			else{
				System.err.println("Unexpected EdgeLabel (neither Evaluation nor AdvCondition");
				nextState=null;
			}	
			if (nextState == State.STATE_FALSE)
			{
				nextSs.stateSign = State.STATE_FALSE;
				return nextSs;
			}
			nextSs.add(new PredicateVector(pred, nextState));
		}

		return nextSs;
	}

	State calAssignment(Predicate predWithState, Predicate pred, State predState, EdgeLabel label)
	{
		AdvCondition cPredWithState = predWithState.getAdvCondition();
		AdvCondition cPred = pred.getAdvCondition();
		Prover p=getProverInstance();

		if (p.imply(cPredWithState, label.getNegativeCopy()))
		{
			return State.STATE_FALSE;
		}
		if (p.imply( cPredWithState, cPred.getWeakestPrecondition((EvaluationSentence)label) ))
		{
			return State.STATE_POS;
		}
		if (p.imply( cPredWithState, cPred.getNegativeCopy().getWeakestPrecondition((EvaluationSentence)label) ))
		{
			return State.STATE_NEG;
		}
		return State.STATE_TRUE;
	}

	State calCondition(Predicate pred, State predState, EdgeLabel label)
	{
		AdvCondition cPred = pred.getAdvCondition();
		Prover p =getProverInstance();

		if ( p.imply((AdvCondition)label, cPred) )
		{
			if (predState == State.STATE_NEG) return State.STATE_FALSE;
			else return predState;
		}
		if ( p.imply((AdvCondition)label, cPred.getNegativeCopy()) )
		{
			if (predState == State.STATE_POS) return State.STATE_FALSE;
			else return predState;
		}
		return predState;	//inherit
	}
	
	void recordTrace(Edge e)
	{
		edgeTrace.add(e);
	}

	void removeTrace()
	{
		edgeTrace.remove( edgeTrace.size()-1 );	//last edge
	}

	int backTrace(Predicate predBack)
	{
		ArrayList<Edge> cloneEdgeTrace = new ArrayList<Edge>();
		ArrayList<Node> cloneNodeTrace = new ArrayList<Node>();
		ArrayList<AdvCondition> advConditionList = new ArrayList<AdvCondition>();
	

		cloneRefineRoute(cloneEdgeTrace, cloneNodeTrace);
		cloneNodeTrace.get(cloneNodeTrace.size()-1).initStateSpace(edgeTrace.get(edgeTrace.size()-1).tailNode.peekStateSpace());

		Prover p=getProverInstance();

		for (int i = cloneEdgeTrace.size()-1;  i >= 0;  i--)
		{
			Node curNode = cloneNodeTrace.get(i+1);
			StateSpace preSs = curNode.ss;
			Edge edge = cloneEdgeTrace.get(i);
			StateSpace nextSs = calStateSpace(preSs, edge);

			

			for (int j=0; j<advConditionList.size(); j++)
			{
				advConditionList.remove(j);
			}
			
			for (PredicateVector pv : nextSs.predVectorArray)
			{
				advConditionList.add(pv.getPredicate().getAdvCondition());
			}
			int id = curNode.id;
			Node primitiveNode = cg.getNode(id);
			StateSpace primitiveSs = primitiveNode.peekStateSpace();
			for (PredicateVector pv : primitiveSs.predVectorArray)
			{
				advConditionList.add(pv.getAdvConditionByState());
			}

			//karldodd: we should intersect the "nextSs" with the former one, and test if it is satifiable.
			if ( ! p.isSatisfiable(advConditionList) )
			{
				//refinement
				ArrayList<EdgeLabel> linkLabel = new ArrayList<EdgeLabel>();	//need remove all?
				EdgeLabel newLabel = cloneEdgeTrace.get(cloneEdgeTrace.size()-1).label;
				linkLabel.add((AdvCondition)newLabel);	//without judge?
				for (int j = cloneEdgeTrace.size()-1;  j >= i;  j--)
				{
					linkLabel.add(cloneEdgeTrace.get(j).label);
				}
				//add stateSpace of falsenode
				for (PredicateVector pv : primitiveSs.predVectorArray)
				{
					linkLabel.add((EdgeLabel)(pv.getAdvConditionByState()));
				}
				//call foci
				List<Predicate> pList=null;
				try{
				    pList = p.getInterpolation(linkLabel);
				}
				catch(Exception e){
				    System.err.println("Fatal error: fail to calculate interpolation.");
				}

				int numBack = 0;
				boolean endCycle = false;
				for (Predicate pBack : pList)
				{
					cloneNodeTrace.get(0).initStateSpace(pBack);
					StateSpace preSsBack = cloneNodeTrace.get(0).ss;
					for (int k = 0; k < cloneEdgeTrace.size(); k++)
					{
						StateSpace nextSsBack = calStateSpace(preSsBack, cloneEdgeTrace.get(k));
						if( nextSsBack.isFalse() )
						{
							numBack = cloneEdgeTrace.size() - k;
							predBack = pBack;
							endCycle = true;
							break;
						}
					}
					if (endCycle)  break;
				}

				if (endCycle) return numBack;
				
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
		Edge e, newEdge;
		nTrace.add(n);
		for (int i=0; i<edgeTrace.size(); i++)
		{
			e = edgeTrace.get(i);
			n = new Node(e.tailNode.id);
			nTrace.add(n);
			newEdge = new Edge(e.id, e.label);
			newEdge.headNode = nTrace.get(i);
			newEdge.tailNode = nTrace.get(i+1);
			nTrace.get(i).addOutEdge(newEdge);
			nTrace.get(i+1).addInEdge(newEdge);
			eTrace.add(newEdge);
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
}
