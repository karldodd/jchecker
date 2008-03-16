package abstraction;

import tokens.*;
import prover.*;

import java.util.*;

/**
*CFATree类表示根据CFAGraph搜索时的展开树，并包含前向搜索和回溯计算方法
*
*@author Li Jiang
*/
public class CFATree
{
	/**
	*搜索经过的路径
	*/
	private ArrayList<Edge> edgeTrace;

	/**
	*需要验证的谓词
	*/
	private ArrayList<Predicate> predicatesForSearch;

	/**
	*使用到的谓词
	*/
	private HashSet<Predicate> predicatesOnUse;

	/**
	*搜索时用到的CFA图
	*/
	private CFAGraph cg;

	/**
	*是否结束搜索的标志
	*/
	private boolean endSearch;
	
	/**
	*构造函数，根据CFA图初始化CFA树
	*
	*@param g 与CFA树关联的CFA图
	*/
	public CFATree(CFAGraph g)
	{
		edgeTrace = new ArrayList<Edge>();
		predicatesForSearch = new ArrayList<Predicate>();
		predicatesOnUse=new HashSet<Predicate>();
		cg = g;
		endSearch = true;
	}
	
	/**
	*开始前向搜索，所有验证过程的开始
	*
	*@param predArray 需要验证的谓词列表
	*/
	public void beginForwardSearch(ArrayList<Predicate> predArray)
	{
		Node firstNode = cg.firstNode();
		for (Predicate p : predArray)
		{
			predicatesForSearch.add(p.clone());
			predicatesOnUse.add(p.clone());
		}

		do
		{
			endSearch = true;
			StateSpace ssInit = new StateSpace(predicatesForSearch);
			firstNode.pushStateSpace(ssInit);
			forwardSearch(firstNode);
			firstNode.popStateSpace();
		} while ( !endSearch );
	}

	/**
	*用递归的方法对树进行深度优先搜索
	*
	*@param curNode 当前结点
	*@return 需要回退的结点数
	*/
	private int forwardSearch(Node curNode)
	{
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
			return numBack;
		}
		else
		{
			//iterate whole tree
			for (int i=0; i<curNode.getOutEdge().size(); i++)
			{
				preSs = curNode.peekStateSpace();
				outEdge = curNode.getOutEdge().get(i);
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
				if (numBack > 0) 
				{	
					return numBack-1;
				}
				//endSearch == false means now is in backtrace process, numBack == 0 means now getting right place
				if (numBack == 0 && endSearch == false)
				{
					endSearch = true;
					i = -1;		//after i++, i=0, now search can start with the first edge
				}
				//if only numBack == 0, means it's only leaf node
			}			
		}

		return numBack;
	}

	/**
	*判断下一结点是否是循环进入结点
	*
	*@param curNode 当前结点
	*@param nextNode 下一结点
	*@return 若下一结点为循环进入结点，返回true；否则返回false
	*/
	private boolean isCycleBack(Node curNode, Node nextNode)
	{
		return (nextNode.getID() < curNode.getID());
	}

	/**
	*判断循环是否应该结束
	*
	*@param nextNode 下一结点
	*@param nextSs 下一状态空间
	*@return 如果下一状态空间蕴含之前的状态空间，循环结束，返回true；
	*	 否则返回false
	*/
	private boolean canEndCycle(Node nextNode, StateSpace nextSs)
	{
		//if next state space implies previous state spaces, end cycle
		for (int i=0; i<nextNode.stackSize(); i++)
		{
			StateSpace preSs = nextNode.getStack(i);
			if (nextSs.imply(preSs)) return true;
		}
		return false;
	}

	/**
	*对反例路径进行回溯
	*
	*@return 如果反例路径是真反例，返回整条路径长度，表示回退出搜索入口；
	*	 如果反例路径无法添加新谓词，返回整条路径长度，表示回退出搜索入口；
	*	 如果反例路径可以添加新谓词，返回回溯终止至反例路径尾部的路径长度，
	*	 表示回退至回溯终止处结点
	*/
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
		//lastAdvCondition is the AdvCondition just before current edge
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
				int numOfNewPredicates = addNewPredicates(newPredicateList);
				if (numOfNewPredicates == 0)
				{
					//if no new prdicate, back to the nearest parent node and continue search
					System.out.println("No new predicate found");
					endSearch = true;
					//return state spaces to edgeTrace's nodes
					edgeTrace.get(0).getHeadNode().pushStateSpace(originSsTrace.get(0));
					for (i=0; i<edgeTrace.size(); i++)
					{
						edgeTrace.get(i).getTailNode().pushStateSpace(originSsTrace.get(i+1));
					}		
					return 0;
				}
				else
				{
					//if there're new predicates, refine from head using new predicates, and back to appropriate node
					int numBack = refineFromHead(originSsTrace);
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

	/**
	*计算插值
	*
	*@param p 定理证明器
	*@param startNode 路径上的计算插值的起始结点
	*@param startSs 起始结点的状态空间
	*@return 计算出的插值列表
	*/
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
	
	/**
	*添加新插值
	*
	*@param newPredicateList 计算出的新插值
	*@return 添加的新插值的数量
	*/
	private int addNewPredicates(List<Predicate> newPredicateList)
	{
		//add new predicates
		//boolean equal = false;
		int oldSize = predicatesForSearch.size();
		for (Predicate newPredicate : newPredicateList)
		{
			//equal = false;
			
			/*
			for (Predicate oldPredicate : predicatesForSearch)
			{
				if (oldPredicate.equals(newPredicate))
				{
					equal = true;
					break;
				}
			}
			if (!equal)*/

			if(predicatesOnUse.add(newPredicate))
			{
				System.out.println("Adding Predicate: "+ newPredicate.toString());
				predicatesForSearch.add(newPredicate);
				//predicatesOnUse.add(newPredicate);
			}
		}
		int newSize = predicatesForSearch.size();

		return newSize - oldSize;
		//no new predicate, searh should end
		//if (oldSize == newSize) endWithNoNewPredicate();
	}

	/**
	*添加新谓词后重新对CFA数进行精化
	*
	*@param originSsTrace 精化前的反例路径上的状态空间
	*@return 需要回退的结点数，从回退到位的结点开始继续搜索
	*/
	private int refineFromHead(ArrayList<StateSpace> originSsTrace)
	{
		int validEdge = 0;
		int numBack = 0;
		StateSpace ss = new StateSpace(predicatesForSearch);
		ArrayList<StateSpace> newSsTrace = new ArrayList<StateSpace>();

		//refine from head, finding which node is the right search restarting place
		newSsTrace.add(ss);
		for (validEdge=0; validEdge<edgeTrace.size(); validEdge++)
		{
			ss = StateSpace.getNextStateSpace(ss, edgeTrace.get(validEdge));
			if (ss.isFalse())
			{
				numBack = edgeTrace.size()-validEdge;
				break;
			}
			else newSsTrace.add(ss);
		}

		//return state spaces to edgeTrace's nodes
		edgeTrace.get(0).getHeadNode().pushStateSpace(newSsTrace.get(0));
		for (int i=0; i<validEdge; i++)
		{
			edgeTrace.get(i).getTailNode().pushStateSpace(newSsTrace.get(i+1));
		}
		for (int i=validEdge; i<edgeTrace.size(); i++)
		{
			edgeTrace.get(i).getTailNode().pushStateSpace(originSsTrace.get(i+1));
		}

		return numBack;
	}

	/**
	*记录搜索路径
	*
	*@param e 经过的边
	*/
	private void recordTrace(Edge e)
	{
		edgeTrace.add(e);
	}

	/**
	*移除搜索路径
	*
	*@return 移除的边
	*/
	private Edge removeTrace()
	{
		//remove last edge
		return edgeTrace.remove( edgeTrace.size()-1 );
	}

	/**
	*如果找到真反例，显示路径并标识程序可结束
	*/
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

	/**
	*如果无法添加新谓词，显示路径并标识程序可结束
	*/
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
		for (Predicate p : predicatesForSearch)
		{
			System.out.println("//  " + p.toString());
		}
		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
	}
	
}
