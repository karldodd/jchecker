package abstraction;

import tokens.*;

public class PredicateVector
{
	private Predicate p;
	private State s;

	public PredicateVector(Predicate pred, State stat)
	{
		p = pred;
		s = stat;
	}
	
	public PredicateVector clone()
	{
		return new PredicateVector(p.clone(), s);
	}

	public Predicate getPredicate()
	{
		return p.clone();
	}
	
	public void setPredicate(Predicate pred)
	{
		p = pred.clone();
	}

	public State getState()
	{
		return s;
	}
	
	public void setState(State stat)
	{
		s = stat;
	}

	public AdvCondition getAdvConditionByState()
	{
		//public enum State {STATE_FALSE, STATE_POS, STATE_NEG, STATE_TRUE}
		if (s == State.STATE_FALSE)  return new AdvCondition(new Condition(false));
		else if (s == State.STATE_POS) return p.getAdvCondition().clone();
		else if (s == State.STATE_NEG)
		{
			AdvCondition temp=p.getAdvCondition().clone();
			temp.negateSelf();
			return temp;
		}
		else if (s == State.STATE_TRUE) return new AdvCondition(new Condition(true));
		else
		{
			System.err.println("Unexpected state of a predicate, returning null");
			return null;
		}
	}

	public Predicate getPredicateByState()
	{
		return new Predicate(this.getAdvConditionByState());
	}

	void display()
	{
		System.out.println("predicate vector display:");
		p.display();
		System.out.println("state is: " + s);
	}
}
