package abstraction;

public class PredicateVector
{
	Predicate p;
	State s;

	PredicateVector(Predicate pred, State stat)
	{
		p = pred;
		s = stat;
	}

	Predicate getPredicate()
	{
		return p;
	}

	State getState()
	{
		return s;
	}
}
