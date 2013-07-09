public class DictData {
	private int leftId, rightId, emissionCost;
	private String word;

	public DictData(int leftId, int rightId, int emissionCost, String word) {
		this.leftId = leftId;
		this.rightId = rightId;
		this.emissionCost = emissionCost;
		this.word = word;
	}

	public int getLeftId() {
		return leftId;
	}

	public int getRightId() {
		return rightId;
	}

	public int getEmissionCost() {
		return emissionCost;
	}

	public String getWord() {
		return word;
	}

	public void printData() {
		System.out.println(leftId + "\t" + rightId + "\t" + emissionCost + "\t"
				+ word);
	}
}
