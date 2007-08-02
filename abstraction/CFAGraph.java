package abstraction;

import parsers.*;
import tokens.*;
import java.io.File;
import java.lang.*;
import java.util.*;

public class CFAGraph
{
	public ArrayList<Node> nodeList = new ArrayList<Node>();

	CFAGraph()
	{
		//h0 is head node
		Node h0 = new Node();
		nodeList.add(h0);
		//redundance
		Node h1 = new Node(false, null, null, null);
		nodeList.add(h1);
	}

	void addNode(Node n)
	{
		nodeList.add(n);
	}

	Node firstNode()
	{
		return nodeList.get(1);
	}

	void changeNodeStateSpace(int index, StateSpace ss)
	{
		nodeList.get(index).changeStateSpace(ss);
	}
	
	void display()
	{
		//This function is only for test
		for (Node n : nodeList)
		{
			n.display();
			System.out.println("");
		}
	}

    public static CFAGraph createCFAGraphFromCode(File sourceFile){
	SourceParser par=new SourceParser(false);
	par.parseFile(sourceFile);
	ArrayList<Sentence> sentences=par.getSentencePool();

	CFAGraph graph=new CFAGraph();
	
	graph.createGraphFromSentenceArray(sentences,graph.firstNode(),null);

	return graph;
    }

    public Node createGraphFromSentenceArray(ArrayList<Sentence> sentences, Node lastActiveNode,Node intendedEndNode){
	int size=sentences.size();
	for(int i=0;i<size;i++){
	    if(i<size-1)
		lastActiveNode=createGraphFromSentence(sentences.get(i),lastActiveNode,null);
	    else
		lastActiveNode=createGraphFromSentence(sentences.get(i),lastActiveNode,intendedEndNode);
	}
	return lastActiveNode;
	//There is a situation that intendedEndNode is not satisfied: size==0 && lastActiveNode!=null
    }

    //create graph. the node returned is the new "last active node". 
    //For those RETURN/ERROR sentences, return null as the last active node, for they are not active any more.
    //if the intendedEndNode is null, create a new node.
    public Node createGraphFromSentence(Sentence s, Node lastActiveNode,Node intendedEndNode){
	if(lastActiveNode==null)return null;
	if(s instanceof ReturnSentence){
	    //donothing
	    //add a RETURN MARK may be considered
	    return null;
	}
	else if (s instanceof ErrorSentence){
	    lastActiveNode.setErrorNode(true);
	    return null;
	}
	else if(s instanceof EvaluationSentence){
	    if(intendedEndNode==null){
		Node newNode=new Node(false);
		Edge newEdge=new Edge(lastActiveNode,newNode,s);
		lastActiveNode.addOutEdge(newEdge);
		newNode.addInEdge(newEdge);
		this.addNode(newNode);
		return newNode;
	    }
	    else{
		Edge newEdge=new Edge(lastActiveNode,intendedEndNode,s);
		lastActiveNode.addOutEdge(newEdge);
		intendedEndNode.addInEdge(newEdge);
		return intendedEndNode;
	    }
	}
	else if(s instanceof DecisionSentence){
	    DecisionSentence ds=(DecisionSentence)s;
	    Node l,r;
	    if(ds.sentences.size()==0&&intendedEndNode!=null)
		l=intendedEndNode;
	    else
		l=new Node(false);
	    if(ds.elsesentences.size()==0&&intendedEndNode!=null)
		r=intendedEndNode;
	    else
		r=new Node(false);
	    Edge lEdge=new Edge(lastActiveNode,l,(EdgeLabel)(ds.c));
	    Edge rEdge=new Edge(lastActiveNode,r,(EdgeLabel)(ds.c));
	    lastActiveNode.addOutEdge(lEdge);
	    lastActiveNode.addOutEdge(rEdge);
	    l.addInEdge(lEdge);
	    r.addInEdge(rEdge);
	    this.addNode(l);
	    this.addNode(r);
	    Node lastActiveNodeFromLeft=createGraphFromSentenceArray(ds.sentences,l,intendedEndNode);
	    Node lastActiveNodeFromRight=createGraphFromSentenceArray(ds.elsesentences,r,intendedEndNode);
	    if(lastActiveNodeFromLeft==null)return lastActiveNodeFromRight;
	    if(lastActiveNodeFromRight==null)return lastActiveNodeFromLeft;
	    if(lastActiveNodeFromLeft==lastActiveNodeFromRight&&lastActiveNodeFromLeft==intendedEndNode)
		return lastActiveNodeFromLeft;
	    else{
		System.out.println("Unexpected Node got when creating CFA graph... Returning intendedEndNode");
		return intendedEndNode;
	    }
	}
	else{
		System.out.println("Unexpected Sentence got when creating CFA graph... Ignoring...");
		return lastActiveNode;
	}
    }
}
