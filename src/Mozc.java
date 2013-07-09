import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;

public class Mozc {
	static final String DATA_FOLDER = "MozcData/";
	static final String CONNECTION_FILE = "connection.txt";
	static final String DICTIONARY = "dictionary";

	private int[][] transitionCost;
	private HashMap<String, ArrayList<DictData>> dictionary;

	public Mozc() {
		setTransitionCost(); // transitionCostのメモリ確保と値セット
		setDictionary(); // dictionaryのインスタンス生成とハッシュセット
	}

	private void setDictionary() {
		dictionary = new HashMap<String, ArrayList<DictData>>();
		ArrayList<File> dictFiles = new ArrayList<File>();
		String dictFileBaseName = DATA_FOLDER + DICTIONARY;
		File dir = new File(DATA_FOLDER);

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.toString().startsWith(dictFileBaseName)) { // "dictionary"で始まるファイルを検索
				dictFiles.add(file);
			}
		}

		for (int i = 0; i < dictFiles.size(); i++) {
			System.out.println(dictFiles.get(i) + "読み込み開始");
			try {
				FileReader in = new FileReader(dictFiles.get(i));
				BufferedReader br = new BufferedReader(in);
				String line;

				while ((line = br.readLine()) != null) {
					String[] dataSet = line.split("\t");
					DictData dictData = new DictData(
							Integer.parseInt(dataSet[1]),
							Integer.parseInt(dataSet[2]),
							Integer.parseInt(dataSet[3]), dataSet[4]);

					if (dictionary.get(dataSet[0]) == null) {
						dictionary.put(dataSet[0], new ArrayList<DictData>());
					}

					dictionary.get(dataSet[0]).add(dictData);
				}

				br.close();
				in.close();
			} catch (IOException e) {
				System.out.println(e);
			}
			System.out.println(dictFiles.get(i) + "読み込み終了");
		}

	}

	private void setTransitionCost() {
		String filePath = DATA_FOLDER + CONNECTION_FILE;
		System.out.println(filePath + "読み込み開始");
		try {
			FileReader fr = new FileReader(filePath);
			StreamTokenizer st = new StreamTokenizer(fr);
			st.nextToken();
			int rightIdOfLeftWordNum = (int) st.nval;
			st.nextToken();
			int leftIdOfRightWordNum = (int) st.nval;

			transitionCost = new int[rightIdOfLeftWordNum][leftIdOfRightWordNum];

			for (int i = 0; i < rightIdOfLeftWordNum; i++) {
				for (int j = 0; j < leftIdOfRightWordNum; j++) {
					st.nextToken(); // right_id_of_left_word読み込み
					st.nextToken(); // left_id_of_right_word読み込み
					st.nextToken(); // transition cost読み込み
					transitionCost[i][j] = (int) st.nval;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(filePath + "をオープンできませんでした。終了します。");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("st.nextToken() で次の単語が取得出来ませんでした。終了します。");
			System.exit(0);
		}
		System.out.println(filePath + "読み込み終了");
	}

	public int getTranCost(int rightIdOfLeftWord, int leftIdOfRightWord) {

		return transitionCost[rightIdOfLeftWord][leftIdOfRightWord];
	}

	public ArrayList<DictData> getDictArray(String key) {
		return dictionary.get(key);
	}
}
