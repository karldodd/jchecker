package abstraction;

import parsers.*;
import tokens.*;

import java.io.File;
import java.lang.*;
import java.util.*;

/**
*CFAGraph类表示的是根据源代码生成的流程图
*
*@author Li Jiang
*/
public class CFAGraph
{
	/**
	*结点列表，按照源代码顺序排列
	*/
	private ArrayList<Node> nodeList;

	/**
	*构造函数，生成结点列表的头结点
	*/
	private CFAGraph()
	{
		nodeList = new ArrayList<Node>();		
		//h0 is head node
		Node h0 = new Node(false);
		nodeList.add(h0);
	}

	/**
	*新添一个结点
	*/
	public void addNode(Node n)
	{
		nodeList.add(n);
	}

	/**
	*返回结点列表的头结点
	*
	*@return 返回头结点
	*/
	public Node firstNode()
	{
		return nodeList.get(0);
	}

	/**
	*获取序号为id的结点
	*
	*@param id 结点序号
	*/
	public Node getNode(int id)
	{
		return nodeList.get(id);
	}

	/**
	*显示CFAGraph，测使用
	*/
	public void display()
	{
		//This function is only for test
		for (Node n : nodeList)
		{
			n.display();
			System.out.println("");
		}
	}

	/**
	*从文件中生成CFAGraph
	*
	*@param sourceFile 需要生成CFAGraph的源文件
	*@return 生成的CFAGraph图
	*/
	public static CFAGraph createCFAGraphFromCode(File sourceFile)
	{
		SourceParser par=new SourceParser(false);
		par.parseFile(sourceFile);
		ArrayList<Sentence> sentences=par.getSentencePool();
		CFAGraph graph=new CFAGraph();
		graph.createGraphFromSentenceArray(sentences,graph.firstNode(),null);
		return graph;
	}

	/**
	*从语句数组生成CFAGraph
	*
	*@param sentences 需要生成CFAGraph的语句数组
	*@param lastActiveNode 最近活动的结点
	*@param intendedEndNode 待添加的结点
	*@return 图生成后返回传入的最近活动的结点
	*/
	public Node createGraphFromSentenceArray(ArrayList<Sentence> sentences, Node lastActiveNode,Node intendedEndNode)
	{
		int size=sentences.size();
		for(int i=0;i<size;i++)
		{
			if(i<size-1)
				lastActiveNode=createGraphFromSentence(sentences.get(i),lastActiveNode,null);
			else
				lastActiveNode=createGraphFromSentence(sentences.get(i),lastActiveNode,intendedEndNode);
		}
		
		return lastActiveNode;
		//There is a situation that intendedEndNode is not satisfied: size==0 && lastActiveNode!=null
	}

	/**
	*根据语句生成CFAGraph图，如果是RETURN/ERROR语句，返回null作为最近活动的结点。
	*如果待结束的结点是null，增加一个新结点。
	*
	*@param s 需要处理的语句
	*@param lastActiveNode 最后活动的结点
	*@param intendedEndNode 待添加的结点
	*@return 如果是RETURN/ERROR语句，返回null；
	*	 如果是其他语句，返回添加后的结点；
	*	 如果待结束是null，返回新增结点。
	*/
	public Node createGraphFromSentence(Sentence s, Node lastActiveNode,Node intendedEndNode)
	{
		if(lastActiveNode==null) return null;
		if(s instanceof ReturnSentence)
		{
			//do nothing
			//add a RETURN MARK may be considered
			return null;
		}
		else if (s instanceof ErrorSentence)
		{
			lastActiveNode.setErrorNode(true);
			return null;
		}
		else if(s instanceof EvaluationSentence)
		{
			if(intendedEndNode==null)
			{
				Node newNode=new Node(false);
				Edge newEdge=new Edge(lastActiveNode,newNode,(EdgeLabel)s);
				lastActiveNode.addOutEdge(newEdge);
				newNode.addInEdge(newEdge);
				this.addNode(newNode);
				return newNode;
			}
			else
			{
				Edge newEdge=new Edge(lastActiveNode,intendedEndNode,(EdgeLabel)s);
				lastActiveNode.addOutEdge(newEdge);
				intendedEndNode.addInEdge(newEdge);
				return intendedEndNode;
			}
		}
		else if(s instanceof DecisionSentence)
		{
			DecisionSentence ds=(DecisionSentence)s;
			if(ds.type==DecisionType.ifsentence)
			{
				Node l,r;
				if(ds.sentences.size()==0 && intendedEndNode!=null)
				l=intendedEndNode;
				else
					l=new Node(false);
				
				Edge lEdge=new Edge(lastActiveNode,l,(EdgeLabel)(ds.c));
				lastActiveNode.addOutEdge(lEdge);
				l.addInEdge(lEdge);
				this.addNode(l);
				Node lastActiveNodeFromLeft=createGraphFromSentenceArray(ds.sentences,l,intendedEndNode);
				
				if(ds.elsesentences.size()==0&&intendedEndNode!=null)
					r=intendedEndNode;
				else if(ds.elsesentences.size()==0&&intendedEndNode==null&&lastActiveNodeFromLeft!=null)
					r=lastActiveNodeFromLeft;
				else
					r=new Node(false);
				Edge rEdge=new Edge(lastActiveNode,r,(EdgeLabel)(ds.c.getNegativeCopy()));
				lastActiveNode.addOutEdge(rEdge);
				r.addInEdge(rEdge);
				this.addNode(r);
				
				Node lastActiveNodeFromRight=createGraphFromSentenceArray(ds.elsesentences,r,lastActiveNodeFromLeft);
				if(lastActiveNodeFromLeft==null)return lastActiveNodeFromRight;
				if(lastActiveNodeFromRight==null)return lastActiveNodeFromLeft;
				if(lastActiveNodeFromLeft==lastActiveNodeFromRight&&lastActiveNodeFromLeft==intendedEndNode)
				return lastActiveNodeFromLeft;
				else
				{
					System.out.println("Intended node is not returned.");
					System.out.println("Unexpected Node got when creating CFA graph... Returning lastActiveNode");
					return lastActiveNodeFromLeft;
				}
			}
			else
			{
				Node l,r;
				if(ds.sentences.size()==0&&intendedEndNode!=null)
					l=intendedEndNode;
				else
					l=new Node(false);
				Edge lEdge=new Edge(lastActiveNode,l,(EdgeLabel)(ds.c));
				lastActiveNode.addOutEdge(lEdge);
				l.addInEdge(lEdge);
				this.addNode(l);
				Node lastActiveNodeFromLeft=createGraphFromSentenceArray(ds.sentences,l,lastActiveNode);
				
				if(intendedEndNode!=null)
					r=intendedEndNode;
				else
					r=new Node(false);
				Edge rEdge=new Edge(lastActiveNode,r,(EdgeLabel)(ds.c.getNegativeCopy()));
				lastActiveNode.addOutEdge(rEdge);
				r.addInEdge(rEdge);
				this.addNode(r);
				
				return r;
			}
		}
		else
		{
			System.out.println("Unexpected Sentence got when creating CFA graph... Ignoring...");
			return lastActiveNode;
		}
	}
}
