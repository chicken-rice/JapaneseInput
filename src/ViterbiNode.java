import java.util.ArrayList;


public class ViterbiNode {
	public DictData getDictData() {
		return dictData;
	}

	private int calcCost;
	private DictData dictData;
	private ArrayList<ViterbiNode> previousNodeArray;
	private ViterbiNode minPreviousNode;
	private boolean calcFlag;

	public ViterbiNode(DictData dictData) {
		this.dictData = dictData;
		this.calcCost = -1;
		this.previousNodeArray = new ArrayList<ViterbiNode>();
		this.calcFlag = false;
	}

	public int getCalcCost() {
		return calcCost;
	}

	public void setCalcCost(int calcCost) {
		this.calcCost = calcCost;
	}

	public ArrayList<ViterbiNode> getPreviousNode() {
		return previousNodeArray;
	}

	public ViterbiNode getMinPreviousNode() {
		return minPreviousNode;
	}

	public void setMinPreviousNode(ViterbiNode minPreviousNode) {
		this.minPreviousNode = minPreviousNode;
	}

	public void addAllPreviousNode(ArrayList<ViterbiNode> NodeArray) {
		this.previousNodeArray.addAll(NodeArray);
	}

	public boolean isCalcFlag() {
		return calcFlag;
	}

	public void setCalcFlag(boolean calcFlag) {
		this.calcFlag = calcFlag;
	}


	


}
