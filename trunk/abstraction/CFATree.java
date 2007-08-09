package abstraction;

import tokens.*;
import prover.*;

import java.util.*;

public class CFATree
{
	public static void print(String s)
	{
		System.out.println(s);
	}

	ArrayList<Edge> edgeTrace;
	CFAGraph cg;

	public CFATree(CFAGraph g)
	{
		edgeTrace = new ArrayList<Edge>();
		cg = g;
	}
        
        public static Prover getProverInstance()
	{
	    try
	    {
	    	Prover p = ProverFactory.getProverByName("focivampyre");
		return p;
	    }
	    catch(Exception e)
	    {
		System.err.println("Error: Prover not found...");
		return null;
	    }
	}

	public void beginForwardSearch(ArrayList<Predicate> predArray)
	{
		//initial state space of first node and start searching
		Node firstNode = cg.firstNode();
		StateSpace ssInit = StateSpace.createInitialStateSpace(predArray);
		firstNode.pushStateSpace(ssInit);
		firstNode.display();
		print("");
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
		
		System.out.println("current node id: " + node.id);
		
		if (node.isError())
		{
			print("error node detected");
			//encounter error node
			Predicate predToAdd = null;		//new predicate should be added
			numBack = backTrace(predToAdd);

			print("starting back trace...");
			print("find error node:");
			print("route is:");
			display(edgeTrace);
			print("predicate to add:");
			predToAdd.display();
			print("");

			addNewPredicate(numBack, predToAdd);

			return numBack;
		}
		else
		{
			//iterate whole tree
			for (Edge edge : node.outEdge)
			{
				nextNode = edge.tailNode;
				nextSs = calStateSpace(preSs, edge);

				nextSs.display();

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

				print("");
				nextNode.display();
				//edge.display();
				//nextSs.display();
				print("");

				numBack = forwardSearch(nextNode);

				System.out.println("current node id: " + node.id);
				System.out.println("numBack: " + numBack);

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

	StateSpace calStateSpace(StateSpace preSs, Edge edge)
	{
 		StateSpace nextSs = new StateSpace();
		EdgeLabel label = edge.label;

		for ( PredicateVector predVect : preSs.predVectorArray)
		{
			//calculate each predicate in state space
			Predicate pred = predVect.getPredicate();		//(primitive predicate, predicate's state) and predicate
			State predState = predVect.getState();			//i.e. (x>4, STATE_NEG) and x<=4
			Predicate predWithState = predVect.getPredicateByState();
			State nextState;

			if (label instanceof EvaluationSentence)
			{
				nextState = calAssignment(predWithState, pred, predState, label);
				System.out.println("next state in evaluation sentence is " + nextState);
			}
			else if (label instanceof AdvCondition)
			{
				nextState = calCondition(pred, predState, label);
			}
			else
			{
				System.err.println("Unexpected EdgeLabel (neither Evaluation nor AdvCondition");
				nextState=null;
			}	
			if (nextState == State.STATE_FALSE)		//if next state space is false, return
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
		//predWithState.display();
		//pred.display();
		//print(label.toString());

		AdvCondition cPredWithState = predWithState.getAdvCondition();
		AdvCondition cPred = pred.getAdvCondition();
		Prover p = getProverInstance();
		
		//method to calculate evaluation sentence
//		if (p.imply(cPredWithState, label.getNegativeCopy()))
//		{
//			System.out.println(cPredWithState.toString());
//			System.out.println(label.getNegativeCopy().toString());
//			return State.STATE_FALSE;
//		}
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

		//method to calculate condition sentence
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
		edgeTrace.remove( edgeTrace.size()-1 );	//remove last edge
	}

	int backTrace(Predicate predToAdd)
	{
		ArrayList<Edge> cloneEdgeTrace = new ArrayList<Edge>();
		ArrayList<Node> cloneNodeTrace = new ArrayList<Node>();
		ArrayList<StateSpace> cloneSsTrace = new ArrayList<StateSpace>();
		ArrayList<AdvCondition> advConditionList = new ArrayList<AdvCondition>();
		Prover p=getProverInstance();
	
		//first, clone the route of CFA tree
		cloneRefineRoute(cloneEdgeTrace, cloneNodeTrace, cloneSsTrace);

		print("now in back trace");
		display(cloneEdgeTrace);
		print("");

		//initial last node's state space by last node's state space in CFA tree
		AdvCondition lastAdvCondition = new AdvCondition(new Condition(true));
		//cloneNodeTrace.get(cloneNodeTrace.size()-1).display();
		//cloneNodeTrace.get(cloneNodeTrace.size()-1).popStateSpace();

		//print("here here");


		//cloneNodeTrace.get(cloneNodeTrace.size()-1).initStateSpace(edgeTrace.get(edgeTrace.size()-1).tailNode.peekStateSpace());
		//print("display last node");
		//cloneNodeTrace.get(cloneNodeTrace.size()-1).display();
		//print("");

		for (int i = cloneEdgeTrace.size()-1;  i >= 0;  i--)
		{
			Node curNode = cloneNodeTrace.get(i);

			//empty advConditionList
			for (int j=0; j<advConditionList.size(); j++)
			{
				advConditionList.remove(j);
			}

			//recall
			if (cloneEdgeTrace.get(i).label instanceof AdvCondition)
			{
				lastAdvCondition = (AdvCondition)(cloneEdgeTrace.get(i).label);
			}
			else if (cloneEdgeTrace.get(i).label instanceof EvaluationSentence)
			{
				lastAdvCondition = lastAdvCondition.getWeakestPrecondition((EvaluationSentence)cloneEdgeTrace.get(i).label);
			}
			advConditionList.add(lastAdvCondition);
			
			//add AdvCondition of state space of primitive node
			//that is recall state space intersect primitive state space
			StateSpace primitiveSs = cloneSsTrace.get(i);
			for (PredicateVector pv : primitiveSs.predVectorArray)
			{
				if (pv.getState() == State.STATE_TRUE) continue;					
				else advConditionList.add(pv.getAdvConditionByState());
			}

			print("");
			System.out.println(i);
			for (int l=0; l<advConditionList.size(); l++)
			{
				print(advConditionList.get(l).toString());
			}
			print("");
			if (advConditionList.size()<=1) continue;

			//test if it is satifiable.
			if ( ! p.isSatisfiable(advConditionList) )
			{
				//print("exit");
				//System.exit(0);
				//refinement
				int numBack = 0;
				numBack = refineRoute(predToAdd, cloneEdgeTrace, cloneNodeTrace, i, primitiveSs);
				if (numBack > 0) return numBack;
				break;	//pseudo counter instance can't be judged after all predicates used, treat it as true counter instance
			}
		}

		//this is true counter instance, end
		display(cloneEdgeTrace, cloneNodeTrace);
		return cloneEdgeTrace.size();	//pop out whole route
	}

	void cloneRefineRoute(ArrayList<Edge> eTrace, ArrayList<Node> nTrace, ArrayList<StateSpace> ssTrace)
	{
		int i;
		Node n = new Node(edgeTrace.get(0).headNode.id);
		Edge e, newEdge;
		nTrace.add(n);
		for (i=0; i<edgeTrace.size(); i++)
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
		
		//display(eTrace, nTrace);
		//System.exit(0);

		ArrayList<StateSpace> temp = new ArrayList<StateSpace>();
		temp.add(edgeTrace.get(edgeTrace.size()-1).tailNode.popStateSpace());
		for (i=edgeTrace.size()-1; i>=0; i--)
		{
			temp.add(edgeTrace.get(i).headNode.popStateSpace());
		}
		for (i=0; i<temp.size()-1; i++)
		{
			ssTrace.add(temp.get(temp.size()-1-i));
			edgeTrace.get(i).headNode.pushStateSpace(ssTrace.get(i));
		}
		ssTrace.add(temp.get(0));
		edgeTrace.get(i-1).tailNode.pushStateSpace(ssTrace.get(0));
//		print("here here here");

/*
		for (i=0; i<nTrace.size(); i++)
		{
			//nTrace.get(i).display();
			ssTrace.get(i).display();
		}
			System.exit(0);
*/			
	}

	int refineRoute(Predicate predToAdd, ArrayList<Edge> cloneEdgeTrace, ArrayList<Node> cloneNodeTrace, int numStopNode, StateSpace primitiveSs)
	{
		Prover p = getProverInstance();
		ArrayList<EdgeLabel> linkLabel = new ArrayList<EdgeLabel>();

		//add state space of false node
		for (PredicateVector pv : primitiveSs.predVectorArray)
		{
			linkLabel.add((EdgeLabel)(pv.getAdvConditionByState()));
		}
		//add edge labels
		//EdgeLabel newLabel = cloneEdgeTrace.get(cloneEdgeTrace.size()-1).label;
		//linkLabel.add(newLabel);
		for (int j = numStopNode;  j < cloneEdgeTrace.size();  j++)
		{
			linkLabel.add(cloneEdgeTrace.get(j).label);
		}

		print("here here here");

		print("");
		for (int l=0; l<linkLabel.size(); l++)
		{
			print(linkLabel.get(l).toString());
		}
		print("");

		//calculate interpolation
		List<Predicate> pList = null;
		try
		{
			pList = p.getInterpolation(linkLabel);
			System.out.println("size is: " + pList.size());
		}
		catch(Exception e)
		{
		    System.err.println("Fatal error: fail to calculate interpolation.");
		}

		print("");
		print("interpolation is:");
/*		
		for (Predicate predi : pList)
		{
			if (predi.getAdvCondition().isTrue())
			{
				pList.remove(predi);
			}
		}
*/
		for (Predicate predi : pList)
		{
			predi.display();
		}
		print("");
		//refine route for each interpolation predicate
		int numBack = 0;
		for (Predicate pBack : pList)
		{
			//from head node of clone node trace
			cloneNodeTrace.get(0).initStateSpace(pBack);
			StateSpace preSs = cloneNodeTrace.get(0).ss;
			for (int k = 0; k < cloneEdgeTrace.size(); k++)
			{
				StateSpace nextSs = calStateSpace(preSs, cloneEdgeTrace.get(k));
				if( nextSs.isFalse() )
				{
					numBack = cloneEdgeTrace.size() - k;
					predToAdd = pBack;
					print("new predicate");
					predToAdd.display();
					return numBack;
				}
			}
		}
		
		predToAdd = null;
		return numBack;	//for every predicates, can't refine, end
	}

	void addNewPredicate(int numBack, Predicate predToAdd)
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
		StateSpace addSs = new StateSpace();
		addSs.add( new PredicateVector(predToAdd, State.STATE_TRUE) );	//initial state space of new predicate
		ss = StateSpace.merge(reverseStack.pop(), addSs);		//merge
		edgeTrace.get(0).headNode.pushStateSpace(ss);
		for (int i=0; i<edgeTrace.size()-numBack; i++)
		{
			addSs = calStateSpace(addSs, edgeTrace.get(i));		//calculate only new predicate
			ss = StateSpace.merge(reverseStack.pop(), addSs);	//merge
			edgeTrace.get(i).tailNode.pushStateSpace(ss);		//pour back
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
		print("");
	}
}
