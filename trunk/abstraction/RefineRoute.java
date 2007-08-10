package abstraction;

import prover.*;
import tokens.*;

import java.util.*;

public class RefineRoute
{
	int numBack;
	ArrayList<Predicate> predToAdd;
	ArrayList<Edge> edgeTrace;
	ArrayList<StateSpace> ssTrace;
	ArrayList<StateSpace> primitiveSsTrace; 	
		
	RefineRoute(ArrayList<Edge> eTrace)
	{
		//clone route
		numBack = 0;
		predToAdd = new ArrayList<Predicate>();
		edgeTrace = new ArrayList<Edge>();
		ssTrace = new ArrayList<StateSpace>();
		primitiveSsTrace = new ArrayList<StateSpace>();
		ArrayList<StateSpace> temp = new ArrayList<StateSpace>();
		
		int i;		
		Edge e, newEdge;
		
		for (i=0; i<eTrace.size(); i++)
		{
			e = eTrace.get(i);
			newEdge = new Edge(e.id, e.label);			
			edgeTrace.add(newEdge);
		}		
		
		temp.add(eTrace.get(eTrace.size()-1).tailNode.popStateSpace());
		for (i=eTrace.size()-1; i>=0; i--)
		{
			temp.add(eTrace.get(i).headNode.popStateSpace());
		}
		for (i=0; i<temp.size()-1; i++)
		{
			StateSpace tmpSs = temp.get(temp.size()-1-i);
			primitiveSsTrace.add(tmpSs);
			eTrace.get(i).headNode.pushStateSpace(tmpSs);
		}
		primitiveSsTrace.add(temp.get(0));
		eTrace.get(i-1).tailNode.pushStateSpace(temp.get(0));
	}
	
	void refine()
	{	      
		boolean reachTop=true;
		Prover p = CommonMethod.getProverInstance();
		
		//initial last node's state space by last node's state space in CFA tree
		AdvCondition lastAdvCondition = new AdvCondition(new Condition(true));
		
		ArrayList<AdvCondition> advConditionList = new ArrayList<AdvCondition>();
		
		for (int i = edgeTrace.size()-1;  i >= 0;  i--)
		{
			
			//empty advConditionList
			advConditionList.clear();			

			//recall
			EdgeLabel label = edgeTrace.get(i).label;			
			if (label instanceof AdvCondition)
			{
				//if (lastAdvCondition.isTrue(p)) lastAdvCondition = (AdvCondition)label;
				//else 
				lastAdvCondition = AdvCondition.intersect(lastAdvCondition, (AdvCondition)label);
			}
			else if (label instanceof EvaluationSentence)
			{
				lastAdvCondition = lastAdvCondition.getWeakestPrecondition((EvaluationSentence)label);			
			}
			advConditionList.add(lastAdvCondition);
			
			//add AdvCondition of state space of primitive node
			//that is recall state space intersect primitive state space
			StateSpace pSs = primitiveSsTrace.get(i);
			for (PredicateVector pv : pSs.predVectorArray)
			{
				//if (pv.getAdvConditionByState().isTrue(p)) continue;
				advConditionList.add(pv.getAdvConditionByState());
			}
		
			//test if it is satifiable.
			if ( ! p.isSatisfiable(advConditionList) )
			{
				reachTop=false;
				List<Predicate> pList = calInterpolation(i, pSs);				
				refineFromHead(pList);
				//pseudo counter instance can't be judged after all predicates used, treat it as true counter instance
				if (numBack > 0) break;	
			}
		}
		
		if (reachTop)
		{
		System.out.println("*********************************************");
		System.out.println("reach top in refine() from bottom in back trace, real counter instance discovered");	
		System.out.println("*********************************************");
		}
		
		//numBack = -1; //-1 means real counter instance
	}
	
	List<Predicate> calInterpolation(int numStopNode, StateSpace pSs)
	{
		Prover p = CommonMethod.getProverInstance();
		
		ArrayList<EdgeLabel> linkLabel = new ArrayList<EdgeLabel>();

		//add state space of false node
		for (PredicateVector pv : pSs.predVectorArray)
		{
			linkLabel.add((EdgeLabel)(pv.getAdvConditionByState()));
		}
		//add edge labels		
		for (int j = numStopNode;  j < edgeTrace.size();  j++)
		{
			linkLabel.add(edgeTrace.get(j).label);
		}		

		//calculate interpolation
		List<Predicate> pList = null;
		try
		{
			pList = p.getInterpolation(linkLabel);			
		}
		catch(Exception e)
		{
		    System.err.println("Fatal error: fail to calculate interpolation.");
		}
		
		return pList;		
	}
	
	void refineFromHead(List<Predicate> pList)
	{		
		//refine route for each interpolation predicate
		ArrayList<Predicate> pTemp = new ArrayList<Predicate>();
		for (int j=0; j<pList.size(); j++)
		{			
			pTemp.add(pList.get(j));
			StateSpace preSs = StateSpace.createInitialStateSpace(pTemp);
			
			for (int k = 0; k < edgeTrace.size(); k++)
			{
				StateSpace nextSs = StateSpace.calStateSpace(preSs, edgeTrace.get(k));
				
				if( nextSs.isFalse() )
				{
					numBack = edgeTrace.size() - k;
					predToAdd = pTemp;					
					return;
				}
				
				preSs = nextSs;
			}
		}
				
		predToAdd = null;	//for every predicates, can't refine, end		
	}
	
	int numBack()
	{
		return numBack;
	}
	
	ArrayList<Predicate> predToAdd()
	{
		return predToAdd;
	}
}
