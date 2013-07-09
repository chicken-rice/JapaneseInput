
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		long mozcStart = System.currentTimeMillis();
		Mozc mozc = new Mozc();
		long mozcStop = System.currentTimeMillis();
		System.out.println("mozc実行にかかった時間は " + (mozcStop - mozcStart) + " ミリ秒です。");
			
		long viterbiStart = System.currentTimeMillis();
		Viterbi viterbi = new Viterbi("きょうもいいてんきですね", mozc);
		viterbi.convert();
		long viterbiStop = System.currentTimeMillis();
		System.out.println("viterbi実行にかかった時間は " + (viterbiStop - viterbiStart) + " ミリ秒です。");
		
		long stop = System.currentTimeMillis();
		System.out.println("実行にかかった時間は " + (stop - start) + " ミリ秒です。");
		
		System.out.println();
		System.out.println(viterbi);
	}
}