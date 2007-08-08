package ui.console;

import java.io.*;
import java.util.*;

import tokens.*;
import parsers.*;
import prover.*;
import abstraction.*;

public class AppStarter{
    public static void print(String s){
	System.out.println(s);
    }

    public static void main(String[] args){
	print("Welcome to Jchecker! This project is sponsored by School of Software, Tsinghua University. ");
	if(args.length==0){
	    print("Usage:");
	    print("java ui.console.AppStarter [File]");
	    print("[File] indicates the source code file you want to check.");
	    return;
	}
	if(args.length>1){
	    print("More than 1 arguments detected. Ignore them except the 1st one...");
	}

	File sourceFile=new File(args[0]);

	if(!sourceFile.exists()){
	    print("The indicated file does not exist. Please try again.");
	}
	else{
	    CFAGraph graph=CFAGraph.createCFAGraphFromCode(sourceFile);
//	    print("CFAGraph constructed.");
//	    graph.display();

	    print("");
	    CFATree tree = new CFATree(graph);
	    ArrayList<Predicate> list = new ArrayList<Predicate>();
	    list.add(new Predicate(new AdvCondition(new Condition(new Expression(new Variable("b")), new Expression(4), ConType.smaller))));
	    tree.beginForwardSearch(list);
	}

    }
}
