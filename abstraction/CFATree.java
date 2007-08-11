package abstraction;

import tokens.*;
import prover.*;

import java.util.*;

public class CFATree
{
	private ArrayList<Edge> edgeTrace;
	private ArrayList<Predicate> predicatesForSearch;
	private CFAGraph cg;
	private boolean endSearch;
	
	public CFATree(CFAGraph g)
	{
		edgeTrace = new ArrayList<Edge>();
		predicatesForSearch = new ArrayList<Predicate>();
		cg = g;
		endSearch = false;
	}
	
	public void beginForwardSearch(ArrayList<Predicate> predArray)
	{
		endSearch = false;
		Node firstNode = cg.firstNode();
		for (Predicate p : predArray)
		{
			predicatesForSearch.add(p.clone());
		}

		do
		{
			StateSpace ssInit = new StateSpace(predicatesForSearch);
			firstNode.pushStateSpace(ssInit);
			forwardSearch(firstNode);
			firstNode.popStateSpace();
		} while ( !endSearch );
	}

	private int forwardSearch(Node curNode)
	{
		Node nextNode = null;
		StateSpace preSs = curNode.peekStateSpace();
		StateSpace nextSs = null;
		int numBack = 0;	//numBack means how many nodes should back, default value is 0, stands for leaf node

		if (curNode.isError())
		{
			//find error node, back trace
			backTrace();
			//back to top, begin another forward search
			return edgeTrace.size();
		}
		else
		{
			//iterate whole tree
			for (Edge outEdge : curNode.getOutEdge())
			{
				nextNode = outEdge.getTailNode();
				nextSs = StateSpace.getNextStateSpace(preSs, outEdge);

				if (nextSs.isFalse())
				{
					//this edge can't walk, continue to choose next edge
					continue;
				}
				if (isCycleBack(curNode, nextNode))
				{
					if (canEndCycle(nextNode, nextSs))
					{
						//if cycle can end, continue next edge
						continue;
					}
				}
				recordTrace(outEdge);
				nextNode.pushStateSpace(nextSs);

				numBack = forwardSearch(nextNode);

				nextNode.popStateSpace();
				removeTrace();

				//numBack > 0 means it's not the end of recall
				if (numBack > 0) return numBack-1;
			}			
		}

		return numBack;
	}

	private boolean isCycleBack(Node curNode, Node nextNode)
	{
		return (nextNode.getID() < curNode.getID());
	}

	private boolean canEndCycle(Node nextNode, StateSpace nextSs)
	{
		//if next state space can imply previous state spaces, the cycle should end
		for (int i=0; i<nextNode.stackSize(); i++)
		{
			StateSpace preSs = nextNode.getStack(i);
			if (nextSs.imply(preSs)) return true;
		}
		return false;
	}

	private void backTrace()	
	{
		int i = 0;
		Prover p = CommonMethod.getProverInstance();

		//initial state space trace of edgeTrace
		//originSsTrace: original state space of edgeTrace, from top to bottom
		ArrayList<StateSpace> originSsTrace = new ArrayList<StateSpace>();
		//reverseSsTrace: original state space of edgeTrace, from bottom to top
		ArrayList<StateSpace> reverseSsTrace = new ArrayList<StateSpace>();

		//copy state spaces of edgeTrace's nodes
		for (i=edgeTrace.size()-1; i>=0; i--)
		{
			reverseSsTrace.add(edgeTrace.get(i).getTailNode().popStateSpace());
		}
		reverseSsTrace.add(edgeTrace.get(0).getHeadNode().popStateSpace());

		for (i=reverseSsTrace.size()-1; i>=0; i--)
		{
			originSsTrace.add(reverseSsTrace.get(i));
		}

		//return state spaces to edgeTrace's nodes
		edgeTrace.get(0).getHeadNode().pushStateSpace(originSsTrace.get(0));
		for (i=0; i<edgeTrace.size(); i++)
		{
			edgeTrace.get(i).getTailNode().pushStateSpace(originSsTrace.get(i+1));
		}		

		//back trace
		//lastAdvCondition is the AdvCondition just before current edge
		//now initial error node's AdvCondition
		AdvCondition lastAdvCondition = new AdvCondition(new Condition(true));
		//backTraceAdvConditionList records the AdvConditions to calculate interpolation
		ArrayList<AdvCondition> backTraceAdvConditionList = new ArrayList<AdvCondition>();

		for (i=edgeTrace.size()-1; i>=0; i--)
		{
			backTraceAdvConditionList.clear();

			EdgeLabel label = edgeTrace.get(i).getLabel();
			if (label instanceof AdvCondition)
			{
				lastAdvCondition = AdvCondition.intersect(lastAdvCondition, (AdvCondition)label);
			}
			else if (label instanceof EvaluationSentence)
			{
				lastAdvCondition = lastAdvCondition.getWeakestPrecondition((EvaluationSentence)label);
			}
			backTraceAdvConditionList.add(lastAdvCondition);

			//add original state space of current node
			for (int j=0; j<originSsTrace.get(i).size(); j++)
			{
				backTraceAdvConditionList.add(originSsTrace.get(i).getPredicateVector(j).getAdvConditionByState());
			}

			//test if it is satifiable
			if ( !p.isSatisfiable(backTraceAdvConditionList) )
			{
				List<Predicate> newPredicateList = getInterpolation(p, i, originSsTrace.get(i));
				addNewPredicates(newPredicateList);
				return;
			}
		}

		//coming here means reach the top, real counter instance is found
		endWithRealCounterInstanceFound();
	}

	private List<Predicate> getInterpolation(Prover p, int startNode, StateSpace startSs)
	{
		ArrayList<EdgeLabel> labelList = new ArrayList<EdgeLabel>();

		//add start node's state space
		for (int i=0; i<startSs.size(); i++)
		{
			labelList.add((EdgeLabel)(startSs.getPredicateVector(i).getAdvConditionByState()));
		}
		//add edge labels
		for (int i=startNode; i<edgeTrace.size(); i++)
		{
			labelList.add(edgeTrace.get(i).getLabel());
		}

		//calulate interpolation
		List<Predicate> newPredicateList = null;
		try
		{
			newPredicateList = p.getInterpolation(labelList);
		}
		catch(Exception e)
		{
			System.err.println("Fatal error: fail to calculate interpolation.");
		}

		return newPredicateList;
	}
	
	private void addNewPredicates(List<Predicate> newPredicateList)
	{
		//add new predicates
		boolean equal = false;
		int oldSize = predicatesForSearch.size();
		for (Predicate newPredicate : newPredicateList)
		{
			equal = false;
			for (Predicate oldPredicate : predicatesForSearch)
			{
				if (oldPredicate.equals(newPredicate))
				{
					equal = true;
					break;
				}
			}
			if (!equal) predicatesForSearch.add(newPredicate);
		}
		int newSize = predicatesForSearch.size();

		//no new predicate, searh should end
		if (oldSize == newSize) endWithNoNewPredicate();
	}

	private void recordTrace(Edge e)
	{
		edgeTrace.add(e);
	}

	private Edge removeTrace()
	{
		//remove last edge
		return edgeTrace.remove( edgeTrace.size()-1 );
	}

	private void endWithRealCounterInstanceFound()
	{
		endSearch = true;

		System.out.println("");
		System.out.println("*****************************************************************");
		System.out.println("The program ends with real counter instance found.");
		System.out.println("The edge route is:");
		for (Edge e : edgeTrace)
		{
			System.out.print("( ID: " + e.getID() + ", Label: " + e.getLabel().toString() + " ) => ");
		}
		System.out.println("end");
		System.out.println("*****************************************************************");
	}

	private void endWithNoNewPredicate()
	{
		endSearch = true;

		System.out.println("");
		System.out.println("*****************************************************************");
		System.out.println("The program ends with no new predicate found.");
		System.out.println("The edge route is:");
		for (Edge e : edgeTrace)
		{
			System.out.print("( ID: " + e.getID() + ", Label: " + e.getLabel().toString() + " ) => ");
		}
		System.out.println("end");
		System.out.println("The predicates are:");
		for (Predicate p : predicatesForSearch)
		{
			System.out.println(p.toString());
		}
		System.out.println("*****************************************************************");
	}
	
}
