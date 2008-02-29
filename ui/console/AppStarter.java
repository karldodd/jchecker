package ui.console;

import java.io.*;
import java.util.*;

import tokens.*;
import parsers.*;
import prover.*;
import abstraction.*;

/**
 * Jchecker程序入口
 * 
 * @author He Kaiduo
 */
public class AppStarter {

	/**
	 * 显示字符串 *
	 * 
	 * @param s
	 *            字符串
	 */
	public static void print(String s) {
		System.out.println(s);
	}

	/**
	 * 程序入口，并手工给出验证谓词
	 * 
	 * @param args
	 *            程序传入参数
	 */
	public static void main(String[] args) {
		print("Welcome to Jchecker! This project is sponsored by School of Software, Tsinghua University. ");
		if (args.length == 0) {
			print("Usage:");
			print("java ui.console.AppStarter [File]");
			print("[File] indicates the source code file you want to check.");
			return;
		}
		if (args.length > 1) {
			print("More than 1 arguments detected. Ignore them except the 1st one...");
		}

		File sourceFile = new File(args[0]);

		if (!sourceFile.exists()) {
			print("The indicated file does not exist. Please try again.");
		} else {
			CFAGraph graph = CFAGraph.createCFAGraphFromCode(sourceFile);
			// print("CFAGraph constructed.");
			// graph.display();

			CFATree tree = new CFATree(graph);
			ArrayList<Predicate> list = new ArrayList<Predicate>();
			list.add(new Predicate(new AdvCondition(new Condition(
					new Expression(new Variable("b")), new Expression(0),
					ConType.equal))));
			tree.beginForwardSearch(list);
		}

	}
}
