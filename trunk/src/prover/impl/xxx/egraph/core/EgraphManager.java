/**
 * 
 */
package prover.impl.xxx.egraph.core;

import java.util.ArrayList;
import java.util.List;

import prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet;
import prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSetEnvironment;
import prover.impl.xxx.egraph.utils.DisjointSetImpl.Node;

/**
 * @author KarlHe
 * 
 */
public class EgraphManager {
	// private List<IEgraphElement> elements;

	private List<IEgraphEquation> equationList;
	private List<IEgraphInequation> inequationList;

	// 注意此处的
	// private HashMap<AtomicElement, List<FunctionalElement>>
	// functionDivisionsMap;

	private DisjointSetEnvironment<IEgraphElement> dse;

	// 将各函数调用维持成一个不相交森林，其用意在于降低应用Leibniz规则的性能成本
	private DisjointSetEnvironment<FunctionalElement> dseFunc;

	public EgraphManager(List<IEgraphEquation> el, List<IEgraphInequation> il) {
		equationList = new ArrayList<IEgraphEquation>();
		inequationList = new ArrayList<IEgraphInequation>();

		dse = new DisjointSetEnvironment<IEgraphElement>();
		dseFunc = new DisjointSetEnvironment<FunctionalElement>();

		// add inequation
		for (IEgraphInequation ei : il) {
			inequationList.add(ei);

			try {
				if (!dse.containsKey(ei.getLeftElement()))
					dse.makeSet(ei.getLeftElement());
				if (!dse.containsKey(ei.getRightElement()))
					dse.makeSet(ei.getRightElement());
				if (ei.getLeftElement() instanceof FunctionalElement) {
					cacheFunctionalElement((FunctionalElement) ei
							.getLeftElement());
				}
				if (ei.getRightElement() instanceof FunctionalElement) {
					cacheFunctionalElement((FunctionalElement) ei
							.getRightElement());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (IEgraphEquation ee : el) {
			addNewEquation(ee);
		}

		applyLeibniz();
	}

	private void addNewEquation(IEgraphEquation ee) {
		equationList.add(ee);

		try {
			if (!dse.containsKey(ee.getLeftElement()))
				dse.makeSet(ee.getLeftElement());
			if (!dse.containsKey(ee.getRightElement()))
				dse.makeSet(ee.getRightElement());
			dse.union(dse.findSet(ee.getLeftElement()), dse.findSet(ee
					.getRightElement()));

			if (ee.getLeftElement() instanceof FunctionalElement) {
				cacheFunctionalElement((FunctionalElement) ee.getLeftElement());
			}
			if (ee.getRightElement() instanceof FunctionalElement) {
				cacheFunctionalElement((FunctionalElement) ee.getRightElement());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将各函数调用维持成一个不相交森林，其用意在于降低应用Leibniz规则的性能成本
	 * 
	 * @param fe
	 *            待分类的元素
	 * @throws Exception
	 */
	private void cacheFunctionalElement(FunctionalElement fe) throws Exception {
		if (dseFunc.containsKey(fe))
			return;
		DisjointSet<FunctionalElement> ds = dseFunc.makeSet(fe);
		for (DisjointSet<FunctionalElement> funcSet : dseFunc.getCurrentSets()) {
			if (ds == funcSet)
				continue;
			// 函数名和参数个数相同则合并这两个集合
			if (fe.getFunctionName().equals(
					funcSet.getHeadNode().getK().getFunctionName())
					&& fe.getParamSize() == funcSet.getHeadNode().getK()
							.getParamSize()) {
				dseFunc.union(ds, funcSet);
			}
		}
	}

	/**
	 * 使用莱布尼兹规则进行等价集合的合并
	 */
	private void applyLeibniz() {
		try {
			boolean stop;// 结束控制变量
			do {
				stop = true;
				for (DisjointSet<FunctionalElement> ds : dseFunc
						.getCurrentSets()) {
					// 此处性能可进一步优化

					// 先抽取出列表
					List<FunctionalElement> tempList = new ArrayList<FunctionalElement>();
					for (Node<FunctionalElement> n : ds) {
						tempList.add(n.getK());
					}
					for (int i = 0; i < tempList.size() - 1; i++) {
						for (int j = i + 1; j < tempList.size(); j++) {
							if (dse.findSet(tempList.get(i)) != dse
									.findSet(tempList.get(j))
									&& tempList.get(i).connectedWith(
											tempList.get(j), dse)) {
								dse.union(dse.findSet(tempList.get(i)), dse
										.findSet(tempList.get(j)));
								stop = false;
							}
						}
					}
				}
			} while (!stop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对当前e图的可满足性做检查
	 * 
	 * @return 若e图出现冲突，则返回false，意为不可满足；否则返回true，意为可满足
	 */
	public boolean checkSAT() {
		try {
			for (IEgraphInequation ie : this.inequationList) {
				if (dse.findSet(ie.getLeftElement()) == dse.findSet(ie
						.getRightElement())) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<IEgraphEquation> addNewEquationWithEquationMining(
			IEgraphEquation ee) {
		return null;
	}
}
