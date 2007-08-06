package abstraction;

import tokens.*;
import prover.*;

import java.lang.*;
import java.util.*;

public class StateSpace
{
	ArrayList<PredicateVector> predVectorArray;
	State stateSign;

	public static StateSpace createInitialStateSpace(ArrayList<Predicate> predArray)
	{
		StateSpace ss = new StateSpace();

		for (Predicate p : predArray)
		{
			ss.predVectorArray.add(new PredicateVector(p, State.STATE_TRUE));
		}
		ss.stateSign = State.STATE_TRUE;

		return ss;
	}

	public static StateSpace createInitialStateSpace(StateSpace preSs)
	{
		StateSpace ss = new StateSpace();

		for (PredicateVector pv : preSs.predVectorArray)
		{
			ss.predVectorArray.add(new PredicateVector(pv.getPredicate(), State.STATE_TRUE));
		}
		ss.stateSign = State.STATE_TRUE;

		return ss;
	}

	StateSpace()
	{
		predVectorArray = new ArrayList<PredicateVector>();
		stateSign = State.STATE_TRUE;
	}

	boolean isFalse()
	{
		return (stateSign==State.STATE_FALSE);
	}
	
	boolean imply(StateSpace rightSs)
	{
		List<AdvCondition> leftAdvList = new List<AdvCondition>();
		List<AdvCondition> rightAdvList = new List<AdvCondition>();

		for (PredicateVector pv : predVectorArray)
		{
			leftAdvList.add(pv.getAdvConditionByState());
		}
		leftAdvCondition = AdvCondition.intersectAll(leftAdvList);
		for (PredicateVector pv : rightSs.predVectorArray)
		{
			rightAdvList.add(pv.getAdvConditionByState());
		}
		rightAdvCondition = AdvCondition.intersectAll(rightAdvCondition);

		Prover p = ProverFatory.getProverByName("focivampyre");
		return p.imply(leftAdvCondition, rightAdvCondition);
	}
}
