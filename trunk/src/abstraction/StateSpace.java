package abstraction;

import tokens.*;
import prover.*;

import java.lang.*;
import java.util.*;

/**
*StateSpace类表示状态空间
*
*@author Li Jiang
*/
public class StateSpace
{
	/**
	*状态空间包含的所有谓词向量集合
	*/
	private ArrayList<PredicateVector> predVectorArray;

	/**
	*状态空间的状态，有两种：STATE_TRUE，表示此状态空间非bottom；
	*STATE_FALSE，表示此状态空间为bottom
	*/
	private State stateSign;	
	
	/**
	*构造函数，构造空的状态空间
	*
	*@see #StateSpace(PredicateVector)
	*@see #StateSpace(ArrayList)
	*@see #StateSpace(StateSpace)
	*/
	public StateSpace()
	{
		predVectorArray = new ArrayList<PredicateVector>();
		stateSign = State.STATE_TRUE;
	}

	/**
	*构造函数，根据一个谓词向量构造状态空间
	*
	*@see #StateSpace()
	*@see #StateSpace(ArrayList)
	*@see #StateSpace(StateSpace)
	*/
	public StateSpace(PredicateVector pv)
	{
		predVectorArray = new ArrayList<PredicateVector>();
		predVectorArray.add(pv.clone());
		stateSign = State.STATE_TRUE;
	}

	/**
	*构造函数，根据谓词列表构造状态空间
	*
	*@see #StateSpace()
	*@see #StateSpace(PredicateVector)
	*@see #StateSpace(StateSpace)
	*/
	public StateSpace(ArrayList<Predicate> predArray)
	{
		predVectorArray = new ArrayList<PredicateVector>();
		for (Predicate p : predArray)
		{
			predVectorArray.add(new PredicateVector(p.clone(), State.STATE_TRUE));
		}
		stateSign = State.STATE_TRUE;
	}

	/**
	*构造函数，根据原状态空间构造新状态空间
	*
	*@see #StateSpace()
	*@see #StateSpace(PredicateVector)
	*@see #StateSpace(ArrayList)
	*/
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

	/**
	*判断当前状态空间是否为bottom
	*
	*@return 返回true如果当前谓词空间为bottom，否则返回false
	*/
	public boolean isFalse()
	{
		return (stateSign==State.STATE_FALSE);
	}
	
	/**
	*将当前状态空间设置为bottom
	*/
	public void setFalse()
	{
		stateSign = State.STATE_FALSE;
	}
	
	/**
	*获取当前状态空间的状态
	*
	*@return 当前状态空间的状态
	*/
	public State getStateSign()
	{
		return stateSign;
	}
	
	/**
	*设置当前状态空间的状态
	*
	*@param sg 需要设置的状态
	*/
	public void setStateSign(State sg)
	{
		stateSign = sg;
	}
	
	/**
	*向当前状态空间添加新的谓词向量
	*
	*@param pv 待添加的谓词向量
	*/
	public void addPredicateVector(PredicateVector pv)
	{
		predVectorArray.add(pv.clone());
	}
	
	/**
	*移除谓词向量列表中的第i个谓词向量，返回移除的谓词向量
	*
	*@param i 需要移除的谓词向量序号
	*@return 移除的谓词向量
	*/
	public PredicateVector removePredicateVector(int i)
	{
		return predVectorArray.remove(i);
	}
	
	/**
	*获取谓词向量列表中的第i个谓词向量
	*
	*@param i 需要获取的谓词向量的序号
	*@return 需要获取的谓词向量
	*/
	public PredicateVector getPredicateVector(int i)
	{
		return predVectorArray.get(i);
	}
	
	/**
	*清空谓词向量列表
	*/
	public void clearPredicateVector()
	{
		predVectorArray.clear();
	}
	
	/**
	*获取状态空间中谓词向量的数量
	*
	*@return 状态空间中谓词向量的数量
	*/
	public int size()
	{
		return predVectorArray.size();
	}
	
	/**
	*将当前状态空间与另一个状态空间合并
	*
	*@param ss2 需要合并的另一个状态空间
	*@return 合并后的当前空间
	*@see #merge(ArrayList)
	*@see #merge(StateSpace, StateSpace)
	*@see #merge(StateSpace, ArrayList)
	*/
	public StateSpace merge(StateSpace ss2)
	{		
		for (PredicateVector pv : ss2.predVectorArray)
		{
			this.predVectorArray.add(pv.clone());
		}
		return this;		
	}
	
	/**
	*将当前状态空间与另一个状态空间列表合并
	*
	*@param ssList 需要合并的另一个状态空间列表
	*@return 合并后的当前空间
	*@see #merge(StateSpace)
	*@see #merge(StateSpace, StateSpace)
	*@see #merge(StateSpace, ArrayList)
	*/
	public StateSpace merge(ArrayList<StateSpace> ssList)
	{
		for (StateSpace newss : ssList)
		{
			this.merge(newss);
		}		
		return this;
	}

	/**
	*将两个状态空间合并
	*
	*@param ss1 需要合并的第一个状态空间
	*@param ss2 需要合并的第二个状态空间
	*@return 合并后的新状态空间
	*@see #merge(StateSpace)
	*@see #merge(ArrayList)
	*@see #merge(StateSpace, ArrayList)
	*/
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
	
	/**
	*将一个状态空间和一个状态空间列表合并
	*
	*@param ss1 需要合并的状态空间
	*@param ssList 需要合并的状态空间列表
	*@return 合并后的新状态空间
	*@see #merge(StateSpace)
	*@see #merge(ArrayList)
	*@see #merge(StateSpace, StateSpace)
	*/
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
	
	/**
	*根据状态空间和边计算下一状态空间
	*
	*@param preSs 前一状态空间
	*@param edge 边
	*@return 后一状态空间
	*/
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

	/**
	*如果边为赋值语句，计算谓词通过赋值语句后的状态
	*
	*@param pre 前一状态空间所有谓词向量的交
	*@param cPred 需要计算新状态空间中状态的谓词，此处谓词不带状态
	*@param label 边上的语句
	*@return 谓词在新状态空间中的状态
	*/
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

	/**
	*如果边为条件判断语句，计算谓词通过条件语句后的状态
	*
	*@param pred 需要计算新状态空间中状态的谓词，此处谓词不带状态
	*@param predState 需要计算的谓词的前一状态
	*@param label 边上的语句
	*@return 谓词在新状态空间中的状态
	*/
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

	/**
	*判断当前状态空间是否蕴含另一状态空间
	*
	*@param rightSs 蕴含符号右侧的状态空间
	*@return 如果蕴含，返回true；否则返回false
	*/
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
			System.exit(1);
		}
		return result;
	}

	/**
	*显示状态空间，测试用
	*/
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
