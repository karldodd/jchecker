package prover;

import tokens.*;

import java.lang.*;
import java.util.*;

/**
*定理证明器的工厂类，Prover类的实例由这里获得
*/
public class ProverFactory{

	/**
	 * 根据给定的名字构造并返回定理证明器的方法
	 * @param name 定理证明器的名字
	 * @return 定理证明器的实例
	 * @throws Exception 未找到指定定理证明器的异常
	 */
	public static Prover getProverByName(String name) throws Exception{
		if(name.equals("focivampyre")){
			return ProverImplFociVampyre.getInstance();
		}
		else throw new Exception("No specified prover found.");
	}
}

