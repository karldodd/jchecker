package abstraction;

import java.util.*;

/**
*Node类表示CFA图和CFA树中的结点
*/
public class Node
{
	/**
	*结点的数量
	*/
	private static int num = 0;

	/**
	*结点序号
	*/
	private int id;

	/**
	*该结点是否是错误结点
	*/
	private boolean isErrorNode;

	/**
	*结点的入边集合
	*/
	private ArrayList<Edge> inEdge;

	/**
	*结点的出边集合
	*/
	private ArrayList<Edge> outEdge;

	/**
	*结点的状态空间栈。因为一个结点可能要被经过n次，所以每次经过的状态
	*空间需要用栈保存起来
	*/
	private Stack<StateSpace> stateSpaceStack;

	/**
	*构造函数，根据是否错误结点构造结点
	*
	*@param isErrorNode 错误结点标记
	*/
	public Node(boolean isErrorNode)
	{
		id = num++;
		this.isErrorNode = isErrorNode;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();		
		stateSpaceStack = new Stack<StateSpace>();
	}
	

	/**
	*添加结点入边
	*
	*@param e 结点入边
	*/
	public void addInEdge(Edge e)
	{
		inEdge.add(e);
	}
	
	/**
	*添加结点出边
	*
	*@param e 结点出边
	*/
	public void addOutEdge(Edge e)
	{
		outEdge.add(e);
	}

	/**
	*判断该结点是否错误结点
	*
	*@return 如果是错误结点返回true，否则返回false
	*/
	public boolean isError()
	{
		return isErrorNode;
	}

	/**
	*设置当前结点的错误标记
	*
	*@param boolval 错误标记
	*/
	public void setErrorNode(boolean boolval)
	{
		this.isErrorNode = boolval;
	}

	/**
	*将结点的状态空间压栈
	*
	*@param ss 待压栈的状态空间
	*/
	public void pushStateSpace(StateSpace ss)
	{
		stateSpaceStack.push(ss);	
	}

	/**
	*将结点的状态空间出栈
	*
	*@return 出栈的状态空间
	*/
	public StateSpace popStateSpace()
	{
		if (stateSpaceStack.isEmpty())
		{
			System.out.println("Error: state space of node " + id + " is empty.");
			return null;
		}
		return stateSpaceStack.pop();	
	}

	/**
	*获取栈中最上层的状态空间
	*
	*@return 栈的最上层状态空间
	*/
	public StateSpace peekStateSpace()
	{
		if (stateSpaceStack.isEmpty())
		{
			System.out.println("Error: state space of node " + id + " is empty.");
			return null;
		}
		return stateSpaceStack.peek();	
	}
	
	/**
	*获取结点序号
	*
	*@return 结点序号
	*/
	public int getID()
	{
		return id;
	}	
	
	/**
	*获取结点所有入边
	*
	*@return 结点入边列表
	*/
	public ArrayList<Edge> getInEdge()
	{
		return inEdge;
	}
	
	/**
	*获取结点所有出边
	*
	*@return 结点出边列表
	*/
	public ArrayList<Edge> getOutEdge()
	{
		return outEdge;
	}
	
	/**
	*获取结点状态空间栈的大小
	*
	*@return 状态空间栈大小
	*/
	public int stackSize()
	{
		return stateSpaceStack.size();
	}

	/**
	*获取结点空间栈中自上而下序号为i的状态空间
	*
	*@param i 待获取的状态空间在栈中的序号
	*@return 状态空间栈中序号为i的状态空间
	*/	
	public StateSpace getStack(int i)
	{
		return stateSpaceStack.get(i);
	}

	/**
	*显示结点，测使用
	*/
	void display()
	{
		System.out.println("node id: " + id + "   isErrorNode: " + isErrorNode);
		System.out.println("inEdge:");
		for (Edge e : inEdge)
		{
			e.display();
		}
		System.out.println("outEdge:");
		for (Edge e : outEdge)
		{
			e.display();
		}		
		if (stateSpaceStack.empty())
		{
			System.out.println("state space stack is empty");
		}
		else
		{
			System.out.println("state space stack: ");
			for (int i=0; i<stateSpaceStack.size(); i++)
			{
				System.out.println("##################   " + i + "   ##################");
				stateSpaceStack.get(i).display();
			}
			System.out.println("end state space stack");
		}
		System.out.println("");
	}

}
