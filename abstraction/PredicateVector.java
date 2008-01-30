package abstraction;

import tokens.*;

/**
*PredicateVector类表示谓词向量，即带状态的谓词，可以用二元组(Predicate,State)表示，
*举例：谓词是 x&lt;1 状态是STATE_NEG,那么表示的就是x>1
*
*@author Li Jiang
*/
public class PredicateVector
{
	/**
	*谓词向量中的谓词元素
	*/
	private Predicate p;

	/**
	*谓词向量中的状态元素
	*/
	private State s;

	/**
	*构造函数，根据谓词和状态构造谓词向量
	*
	*@param pred 谓词向量中的谓词元素
	*@param stat 谓词向量中的状态元素
	*/
	public PredicateVector(Predicate pred, State stat)
	{
		p = pred;
		s = stat;
	}
	
	/**
	*深拷贝函数，对谓词向量深拷贝
	*
	*@return 谓词向量的拷贝
	*/
	public PredicateVector clone()
	{
		return new PredicateVector(p.clone(), s);
	}

	/**
	*获取谓词向量中的谓词元素
	*
	*@return 谓词向量中谓词元素的拷贝
	*/
	public Predicate getPredicate()
	{
		return p.clone();
	}
	
	/**
	*设置谓词向量中的谓词元素
	*
	*@param pred 待设置的谓词元素
	*/
	public void setPredicate(Predicate pred)
	{
		p = pred.clone();
	}

	/**
	*获取谓词向量中的状态元素
	*
	*@return 谓词向量中的状态元素
	*/
	public State getState()
	{
		return s;
	}
	
	/**
	*设置谓词向量中的状态元素
	*
	*@param stat 待设置的状态元素
	*/
	public void setState(State stat)
	{
		s = stat;
	}

	/**
	*将谓词向量表示的谓词以高级条件式的形式获取
	*
	*@return 由高级条件式表示的谓词向量
	*/
	public AdvCondition getAdvConditionByState()
	{
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

	/**
	*将谓词向量表示的谓词以谓词的形式获取
	*
	*@return 由谓词形式表示的谓词向量
	*/
	public Predicate getPredicateByState()
	{
		return new Predicate(this.getAdvConditionByState());
	}

	/**
	*显示谓词向量，测使用
	*/
	void display()
	{
		System.out.println("predicate vector display:");
		p.display();
		System.out.println("state is: " + s);
	}
}
