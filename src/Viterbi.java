import java.util.ArrayList;

public class Viterbi {
	private String input, result;
	private ViterbiNode bos, eos;
	private Mozc mozc;

	public Viterbi(String input, Mozc mozc) {
		this.input = input;
		DictData bosDictData = new DictData(0, 0, 0, "[bos]");
		DictData eosDictData = new DictData(0, 0, 0, "[eos]");
		bos = new ViterbiNode(bosDictData);
		eos = new ViterbiNode(eosDictData);
		result = this.input;
		this.mozc = mozc;
		bos.setCalcCost(0);
		bos.setCalcFlag(true);
	}

	public String toString() {
		return result;
	}

	public void convert() {
		ArrayList<ViterbiNode> begin = new ArrayList<ViterbiNode>();
		begin.add(bos);
		createGraph(input, begin);
		calcMinPath(eos);
		result = createResult(eos.getMinPreviousNode()).toString();
	}

	private StringBuffer createResult(ViterbiNode vNode) {
		if (vNode == bos) {
			return new StringBuffer();
		} else {
			return createResult(vNode.getMinPreviousNode()).append(
					vNode.getDictData().getWord());
		}
	}

	private void calcMinPath(ViterbiNode vNode) {
		int minCost = Integer.MAX_VALUE;
		ViterbiNode minPreviousNode = null;

		for (int i = 0; i < vNode.getPreviousNode().size(); i++) {
			ViterbiNode previousNode = vNode.getPreviousNode().get(i);
			if (!previousNode.isCalcFlag()) {
				calcMinPath(previousNode);
			}
			int cost = vNode.getDictData().getEmissionCost()
					+ previousNode.getCalcCost()
					+ mozc.getTranCost(previousNode.getDictData().getRightId(),
							vNode.getDictData().getLeftId());
			if (minCost > cost) {
				minCost = cost;
				minPreviousNode = previousNode;
			}
		}
		vNode.setCalcFlag(true);
		vNode.setCalcCost(minCost);
		vNode.setMinPreviousNode(minPreviousNode);
	}

	private void createGraph(String reminds, ArrayList<ViterbiNode> preNodeArray) {
		for (int i = 1; i <= reminds.length(); i++) {
			String firstStr = reminds.substring(0, i);
			ArrayList<DictData> dictArray = mozc.getDictArray(firstStr);

			if (dictArray != null) {
				ArrayList<ViterbiNode> nextNodeArray = new ArrayList<ViterbiNode>();

				for (int j = 0; j < dictArray.size(); j++) {
					DictData dData = dictArray.get(j);
					ViterbiNode vNode = new ViterbiNode(dData);

					vNode.addAllPreviousNode(preNodeArray);
					nextNodeArray.add(vNode);
				}

				if (i == reminds.length()) {
					ArrayList<ViterbiNode> end = new ArrayList<ViterbiNode>();
					end.add(eos);
					eos.addAllPreviousNode(nextNodeArray);
				} else {
					createGraph(reminds.substring(i, reminds.length()),
							nextNodeArray);

				}
			}
		}
	}
}
