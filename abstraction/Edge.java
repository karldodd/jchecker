package abstraction;

import tokens.*;

/**
*Edge类表示在CFA图和CFA树中的边
*/
public class Edge
{
	/**
	*边的数量
	*/
	private static int num = 0;

	/**
	*边的序号
	*/
	private int id;

	/**
	*与边连接的头结点
	*/
	private Node headNode;

	/**
	*与边连接的尾结点
	*/
	private Node tailNode;

	/**
	*边上的标记，也就是边所代表的语句，如x==1
	*/
	private EdgeLabel label;

	/**
	*构造函数，根据序号和标记构造边
	*
	*@param id 边的序号
	*@param l 边的标记
	*/
	public Edge(int id, EdgeLabel l)
	{
		this.id = id;
		headNode = null;
		tailNode = null;
		label = l;
	}

	/**
	*构造函数，根据头结点，尾结点和标记构造边
	*
	*@param hn 边的头结点
	*@param tn 边的尾结点
	*@param l 边的标记
	*/
	public Edge(Node hn, Node tn, EdgeLabel l)
	{
		id = num++;
		headNode = hn;
		tailNode = tn;
		label = l;
	}
	
	/**
	*获取边的序号
	*
	*@return 边的序号
	*/
	public int getID()
	{
		return id;
	}

	/**
	*获取边的头结点
	*
	*@return 边的头结点
	*/
	public Node getHeadNode()
	{
		return headNode;
	}
	
	/**
	*获取边的尾结点
	*
	*
	*@return 边的尾结点
	*/
	public Node getTailNode()
	{
		return tailNode;
	}
	
	/**
	*获取边的标记
	*
	*@return 边的标记
	*/
	public EdgeLabel getLabel()
	{
		return label;
	}
	
	/**
	*显示边，测试用
	*/
	void display()
	{
		System.out.print("Edge id: " + id + " headnode: " + headNode.getID() + " tailnode: " + tailNode.getID());
		System.out.println(" Label: " + label.toString());		
	}
}
