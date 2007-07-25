/*
 * Created on 2005-6-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Edward
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
import java.io.IOException;
import java.lang.System;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.FileInputStream;

public class TokenIODemo {
	public static void main(String args[]) throws IOException {
		//从文件创建输入流
		FileInputStream fileIn = new FileInputStream("source.c");
		//从字节流创建字符流
		InputStreamReader inReader = new InputStreamReader(fileIn);
		StreamTokenizer tokenStream = new StreamTokenizer(inReader);
		//设置注释风格
		tokenStream.slashStarComments(true);
		tokenStream.slashSlashComments(true);
		//识别行结束符;如果参数为假，将行结束符视作空白符
		tokenStream.eolIsSignificant(true);
		//设置引号的符号表示
		tokenStream.quoteChar('"');
		//将ASCII码为0-32的字符设为空白符
		tokenStream.whitespaceChars(0, 32);
		boolean eof = false;
		do {
			int token = tokenStream.nextToken();
			switch (token) {
			case StreamTokenizer.TT_EOF:
				System.out.println(" EOF ");
				eof = true;
				break;
			case StreamTokenizer.TT_EOL:
				System.out.println(" EOL ");
				break;
			case StreamTokenizer.TT_WORD:
				System.out.println(" Word " + tokenStream.sval);
				break;
			case StreamTokenizer.TT_NUMBER:
				System.out.println(" Number " + tokenStream.nval);
				break;
			default:
				System.out.println("char: " + (char) token);
			}
		} while (!eof);
		System.out.flush();
	}
}
