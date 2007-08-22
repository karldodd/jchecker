package abstraction;

import tokens.*;
import prover.*;

import java.util.*;

public class CFATree
{
	private ArrayList<Edge> edgeTrace;
	//private ArrayList<Predicate> predicatesForSearch;
	//private HashSet<Predicate> predicatesOnUse;
	private CFAGraph cg;
	private boolean endSearch;
	//boolean sign;
	
	public CFATree(CFAGraph g)
	{
		edgeTrace = new ArrayList<Edge>();
		//predicatesForSearch = new ArrayList<Predicate>();
		//predicatesOnUse=new HashSet<Predicate>();
		cg = g;
		endSearch = true;
		//sign = false;
	}
	
	public void beginForwardSearch(ArrayList<Predicate> predArray)
	{
		Node firstNode = cg.firstNode();
		/*for (Predicate p : predArray)
		{
			predicatesForSearch.add(p.clone());
			predicatesOnUse.add(p.clone());
		}*/

		StateSpace ssInit = new StateSpace(predArray);
		firstNode.pushStateSpace(ssInit);
		//do
		//{
		//	endSearch = true;
			//StateSpace ssInit = new StateSpace(predicatesForSearch);
			forwardSearch(firstNode);
		//} while ( !endSearch );
		firstNode.popStateSpace();
	}

	private int forwardSearch(Node curNode)
	{
		//System.out.println("(((((((((((((((((     "+curNode.getID()+"                 ))))))))))))");
		Node nextNode = null;
		Edge outEdge = null;
		StateSpace preSs = null;
		StateSpace nextSs = null;
		int numBack = 0;	//numBack means how many nodes should back, default value is 0, stands for leaf node

		if (curNode.isError())
		{
			//if find error node, there're two cases: find real counter instance, or add new predicates.
			//so now endSearch should be set false to do another forward search
			endSearch = false;
			//find error node, back trace
			numBack = backTrace();
			//back to top, begin another forward search
			return numBack-1;
		}
		else
		{
			//iterate whole tree
			for (int i=0; i<curNode.getOutEdge().size(); i++)
			{
				preSs = curNode.peekStateSpace();
				outEdge = curNode.getOutEdge().get(i);
				nextNode = outEdge.getTailNode();
			
				/*
				System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
				System.out.println("current id: " + curNode.getID() + " current edge: " + i);
				if (sign) {System.out.println(outEdge.getLabel().toString());preSs.display();}
				System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
				*/
				nextSs = StateSpace.getNextStateSpace(preSs, outEdge);
				/*
				System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
				if (sign) {System.out.println(outEdge.getLabel().toString());nextSs.display();}
				System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
				*/

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
				if (numBack > 0) 
				{	
					return numBack-1;
				}
				//endSearch == false means now is in backtrace process, numBack == 0 means now getting right place
				if (numBack == 0 && endSearch == false)
				{
					/*
					System.out.println("--------------------------------------------------------------");
					System.out.println("display current node");
					curNode.display();
					System.out.println("--------------------------------------------------------------");
					*/
					endSearch = true;
					//sign=true;
					i = -1;		//after i++, i=0, now search can start with the first edge
				}
				//if only numBack == 0, means it's only leaf node
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
		//if next state space implies previous state spaces, end cycle
		for (int i=nextNode.stackSize()-1; i>=0; i--)
		{
			StateSpace preSs = nextNode.getStack(i);
			if (preSs.size() < nextSs.size()) return false;	//if nextSs's predicates are more than preSs's, can't end
			else if (nextSs.imply(preSs)) return true;
		}
		return false;
	}

	private int backTrace()	
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

		//back trace

		//lastAdvCondition is the pivot AdvCondition
		//now initial error node's AdvCondition
		AdvCondition lastAdvCondition = new AdvCondition(new Condition(true));

		//backTraceAdvConditionList records the AdvConditions to calculate interpolation
		ArrayList<AdvCondition> backTraceAdvConditionList = new ArrayList<AdvCondition>();

		for (i=edgeTrace.size()-1; i>=0; i--)
		{
			backTraceAdvConditionList.clear();

			//calculate AdvCondition from bottom to top 
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
				//remove identical predicates, the last node's state space has all predicates, so choose its state space
				int numOfNewPredicates = filterNewPredicates(newPredicateList, originSsTrace.get(originSsTrace.size()-1));

				/*
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				for (int l=0; l<newPredicateList.size(); l++)
				{
					newPredicateList.get(l).display();
				}
				System.out.println("num of new predicates are " + numOfNewPredicates);
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				*/

				if (numOfNewPredicates == 0)
				{
					//if no new prdicate, back to the nearest parent node and continue search
					System.out.println("No new predicate found. This ERROR is not real counter instance.");
					endSearch = true;
					//return state spaces to edgeTrace's nodes
					edgeTrace.get(0).getHeadNode().pushStateSpace(originSsTrace.get(0));
					for (i=0; i<edgeTrace.size(); i++)
					{
						edgeTrace.get(i).getTailNode().pushStateSpace(originSsTrace.get(i+1));
					}		
					return 0;	//return 0 means we can't judge, so back to ERROR node's parent node.
				}
				else
				{
					//if there're new predicates, refine from head using new predicates, and back to appropriate node
					int numBack = refineFromHead(originSsTrace, newPredicateList);
					return numBack;
				}
			}
		}

		//coming here means reach the top, real counter instance is found
		endWithRealCounterInstanceFound();

		//return state spaces to edgeTrace's nodes
		edgeTrace.get(0).getHeadNode().pushStateSpace(originSsTrace.get(0));
		for (i=0; i<edgeTrace.size(); i++)
		{
			edgeTrace.get(i).getTailNode().pushStateSpace(originSsTrace.get(i+1));
		}		
		return edgeTrace.size();
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

		for (EdgeLabel el : labelList) {System.out.println(el.toString());}
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
	
	private int filterNewPredicates(List<Predicate> newPredicateList, StateSpace preSs)
	{
		HashSet<Predicate> predicatesOnUse = new HashSet<Predicate>();

		/*
		System.out.println("==================================================================================");
		for (Predicate p : newPredicateList)
		{
			p.display();
		}
		System.out.println("==================================================================================");
		for (int i=0; i<preSs.size(); i++)
		{
			preSs.getPredicateVector(i).getPredicate().display();
		}
		System.out.println("==================================================================================");
		*/

		for (int i=0; i<preSs.size(); i++)
		{
			predicatesOnUse.add(preSs.getPredicateVector(i).getPredicate());
		}

		for (int i=newPredicateList.size()-1; i>=0; i--)
		{
			//remove identical predicate from tail to node, so list shrink will not affect the result
			if (!predicatesOnUse.add(newPredicateList.get(i)))
			{
				newPredicateList.remove(i);	//remove identical predicate
			}
		}

		return newPredicateList.size();
	}

	private int refineFromHead(ArrayList<StateSpace> originSsTrace, List<Predicate> newPredicateList)
	{
		int validEdge = 0;
		int numBack = 0;
		StateSpace ss = new StateSpace(newPredicateList);
		ArrayList<StateSpace> newSsTrace = new ArrayList<StateSpace>();

		//refine from head, finding which node is the right search restarting place
		newSsTrace.add(ss);
		for (validEdge=0; validEdge<edgeTrace.size(); validEdge++)
		{
			ss = StateSpace.getNextStateSpace(ss, edgeTrace.get(validEdge));
			System.out.println("0000000000000000000000000000   Edge label: "); edgeTrace.get(validEdge).display(); ss.display();
			if (ss.isFalse())
			{
				numBack = edgeTrace.size()-validEdge;
				break;
			}
			else newSsTrace.add(ss);
		}

		//return state spaces to edgeTrace's nodes
		//validEdge can't smaller than 1, because the initial state space is universal
		if (validEdge == 0)
		{
			System.err.println("Error: Error occurred in refineFromHead. The second state space is false, impossible while the initial state space is universal.");
			System.exit(1);
		}
		//lazy algorithm. use new predicates only on the last but false node.
		edgeTrace.get(0).getHeadNode().pushStateSpace(originSsTrace.get(0));
		for (int i=0; i<validEdge-1; i++)
		{
			edgeTrace.get(i).getTailNode().pushStateSpace(originSsTrace.get(i+1));
		}
		edgeTrace.get(validEdge-1).getTailNode().pushStateSpace(StateSpace.merge(originSsTrace.get(validEdge), newSsTrace.get(validEdge)));	//merge origin state space and new state space as local state space

		System.out.println("This is back node");
		edgeTrace.get(validEdge-1).getTailNode().peekStateSpace().display();
		System.out.println("This is back node........");
		edgeTrace.get(validEdge-1).getTailNode().display();
		System.out.println("NUMBACK......  " + numBack);
		//for (StateSpace dss : newSsTrace) {System.out.println(""); dss.display();}

		for (int i=validEdge; i<edgeTrace.size(); i++)
		{
			edgeTrace.get(i).getTailNode().pushStateSpace(originSsTrace.get(i+1));
		}

		return numBack;
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
		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
		System.out.println("//  The program ends with real counter instance found.");
		System.out.println("//  The edge route is:");
		int count = 0;
		for (Edge e : edgeTrace)
		{
			if (count % 3 == 0)
				System.out.print("//  ");
			if (count == 0)
				System.out.print("(ID: " + e.getID() + ", Label: " + e.getLabel().toString() + ") ");
			else
				System.out.print("=> (ID: " + e.getID() + ", Label: " + e.getLabel().toString() + ") ");
			if (count % 3 == 2)
				System.out.println("");
			count++;
		}
		if (count % 3 == 0) System.out.println("//  => end");
		else System.out.println("=> end");
		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
	}

	private void endWithNoNewPredicate()
	{
		endSearch = true;

		System.out.println("");
		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
		System.out.println("//  The program ends with no new predicate found.");
		System.out.println("//  The edge route is:");
		int count = 0;
		for (Edge e : edgeTrace)
		{
			if (count % 3 == 0)
				System.out.print("//  ");
			if (count == 0)
				System.out.print("(ID: " + e.getID() + ", Label: " + e.getLabel().toString() + ") ");
			else
				System.out.print("=> (ID: " + e.getID() + ", Label: " + e.getLabel().toString() + ") ");
			if (count % 3 == 2)
				System.out.println("");
			count++;
		}
		if (count % 3 == 0) System.out.println("//  => end");
		else System.out.println("=> end");
		System.out.println("//  The predicates are:");
		/*for (Predicate p : predicatesForSearch)
		{
			System.out.println("//  " + p.toString());
		}*/
		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
	}
	
}
