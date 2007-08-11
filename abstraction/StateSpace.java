package abstraction;

import tokens.*;
import prover.*;

import java.lang.*;
import java.util.*;

public class StateSpace
{
	private ArrayList<PredicateVector> predVectorArray;
	private State stateSign;	
	
	public StateSpace()
	{
		predVectorArray = new ArrayList<PredicateVector>();
		stateSign = State.STATE_TRUE;
	}

	public StateSpace(PredicateVector pv)
	{
		predVectorArray = new ArrayList<PredicateVector>();
		predVectorArray.add(pv.clone());
		stateSign = State.STATE_TRUE;
	}

	public StateSpace(ArrayList<Predicate> predArray)
	{
		predVectorArray = new ArrayList<PredicateVector>();
		for (Predicate p : predArray)
		{
			predVectorArray.add(new PredicateVector(p.clone(), State.STATE_TRUE));
		}
		stateSign = State.STATE_TRUE;
	}

	public StateSpace(StateSpace preSs)
	{
		predVectorArray = new ArrayList<PredicateVector>();
		for (PredicateVector pv : preSs.predVectorArray)
		{
			PredicateVector newpv = pv.clone();
			newpv.setState(State.STATE_TRUE);
			predVectorArray.add(newpv);
		}
		stateSign = State.STATE_TRUE;
	}	

	public boolean isFalse()
	{
		return (stateSign==State.STATE_FALSE);
	}
	
	public void setFalse()
	{
		stateSign = State.STATE_FALSE;
	}
	
	public State getStateSign()
	{
		return stateSign;
	}
	
	public void setStateSign(State sg)
	{
		stateSign = sg;
	}
	
	public void addPredicateVector(PredicateVector pv)
	{
		predVectorArray.add(pv.clone());
	}
	
	public PredicateVector removePredicateVector(int i)
	{
		return predVectorArray.remove(i);
	}
	
	public PredicateVector getPredicateVector(int i)
	{
		return predVectorArray.get(i);
	}
	
	public void clearPredicateVector()
	{
		predVectorArray.clear();
	}
	
	public int size()
	{
		return predVectorArray.size();
	}
	
	public StateSpace merge(StateSpace ss2)
	{		
		for (PredicateVector pv : ss2.predVectorArray)
		{
			this.predVectorArray.add(pv.clone());
		}
		return this;		
	}
	
	public StateSpace merge(ArrayList<StateSpace> ssList)
	{
		for (StateSpace newss : ssList)
		{
			this.merge(newss);
		}		
		return this;
	}

	public static StateSpace merge(StateSpace ss1, StateSpace ss2)
	{
		StateSpace ss3 = new StateSpace();
		for (PredicateVector pv : ss1.predVectorArray)
		{
			ss3.predVectorArray.add(pv.clone());
		}
		for (PredicateVector pv : ss2.predVectorArray)
		{
			ss3.predVectorArray.add(pv.clone());
		}
		return ss3;		
	}
	
	public static StateSpace merge(StateSpace ss1, ArrayList<StateSpace> ssList)
	{
		StateSpace ss3 = new StateSpace();
		ss3 = merge(ss3, ss1);
		for (StateSpace newss : ssList)
		{
			ss3 = merge(ss3, newss);
		}		
		return ss3;
	}
	
	public static StateSpace getNextStateSpace(StateSpace preSs, Edge edge)
	{
 		StateSpace nextSs = new StateSpace();
		EdgeLabel label = edge.getLabel();
		AdvCondition pre = null;
		ArrayList<AdvCondition> preList = new ArrayList<AdvCondition>();

		for ( PredicateVector pv : preSs.predVectorArray)
		{
			preList.add(pv.getAdvConditionByState());
		}
		pre = AdvCondition.intersectAll(preList);

		for ( PredicateVector predVect : preSs.predVectorArray)
		{
			//calculate each predicate in state space
			Predicate pred = predVect.getPredicate();		//(primitive predicate, predicate's state) and predicate
			State predState = predVect.getState();			//i.e. (x>4, STATE_NEG) and x<=4			
			State nextState;

			if (label instanceof EvaluationSentence)
			{
				nextState = getStateOfEvaluationSentence(pre, pred.getAdvCondition(), label);				
			}
			else if (label instanceof AdvCondition)
			{
				nextState = getStateOfAdvCondition(pred, predState, label);
			}
			else
			{
				System.err.println("Unexpected EdgeLabel (neither Evaluation nor AdvCondition");
				nextState = null;
			}	
			if (nextState == State.STATE_FALSE)		//if next state space is false, return
			{
				nextSs.setFalse();
				return nextSs;
			}
			nextSs.addPredicateVector(new PredicateVector(pred, nextState));
		}

		return nextSs;
	}

	private static State getStateOfEvaluationSentence(AdvCondition pre, AdvCondition cPred, EdgeLabel label)
	{		
		Prover p = CommonMethod.getProverInstance();
		
		//method to calculate evaluation sentence
		if (p.imply( pre, cPred.getWeakestPrecondition((EvaluationSentence)label) ))
		{
			return State.STATE_POS;
		}
		if (p.imply( pre, cPred.getNegativeCopy().getWeakestPrecondition((EvaluationSentence)label) ))
		{
			return State.STATE_NEG;
		}
		return State.STATE_TRUE;
	}

	private static State getStateOfAdvCondition(Predicate pred, State predState, EdgeLabel label)
	{
		AdvCondition cPred = pred.getAdvCondition();
		Prover p = CommonMethod.getProverInstance();

		//method to calculate condition sentence
		if ( p.imply((AdvCondition)label, cPred) )
		{
			if (predState == State.STATE_NEG) return State.STATE_FALSE;
			else return State.STATE_POS;			
		}
		else if ( p.imply((AdvCondition)label, cPred.getNegativeCopy()) )
		{
			if (predState == State.STATE_POS) return State.STATE_FALSE;
			else return State.STATE_NEG;			
		}
		return predState;	//inherit
	}

	public boolean imply(StateSpace rightSs)
	{
		ArrayList<AdvCondition> leftAdvList = new ArrayList<AdvCondition>();
		ArrayList<AdvCondition> rightAdvList = new ArrayList<AdvCondition>();
		boolean result = false;

		for (PredicateVector pv : predVectorArray)
		{
			leftAdvList.add(pv.getAdvConditionByState());
		}
		AdvCondition leftAdvCondition = AdvCondition.intersectAll(leftAdvList);
		for (PredicateVector pv : rightSs.predVectorArray)
		{
			rightAdvList.add(pv.getAdvConditionByState());
		}
		AdvCondition rightAdvCondition = AdvCondition.intersectAll(rightAdvList);
		
		try 
		{
			Prover p = ProverFactory.getProverByName("focivampyre");
			result = p.imply(leftAdvCondition, rightAdvCondition);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	void display()
	{
		System.out.println("Now display state space:");
		System.out.println("state sign is: " + stateSign);
		for (PredicateVector pv : predVectorArray)
		{
			pv.display();
		}
	}
}
