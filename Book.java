package package35;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public class Book {
	static String fileName = "book";
	static List<String> book = new ArrayList<String>();
	static List<String> newWords = new ArrayList<String>();
//	String line = null;

	public static void readAndSeparateWords() {
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// process the line
				String[] words = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
//				List<String> book = Arrays.asList(words);
				for (int i = 0; i < words.length; i++) {
					if (!words[i].equals(null) && !words[i].equals("") && !words[i].equals(" ")
							&& !words[i].contentEquals("\n")) {
						book.add(words[i].toLowerCase());
					}
				}
			}
//			book.removeAll(Collections.singleton(null));
//			book.removeAll(Collections.singleton(""));
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void compareBookAndDictionary() {
		for (String m : book) {
			if (!Dictionary.postojiRec(m)) {
				newWords.add(m);
			}
		}

	}

	public static void compareBookAndDictionary2() {// this method is used
		for (String wordFromBook : book) {// try to not remember the new word if it was already found for the first time
//			if (newWords.contains(wordFromBook)) {
//				continue;
//			} else {
				if (!Dictionary.dictionary.containsKey(wordFromBook)/* && !newWords.contains(wordFromBook)*/) {
					newWords.add(wordFromBook);
				}
//			}
		}
		Collections.sort(newWords);
	}
	/*
	 * } // The name of the file to open. String fileName = "temp.txt";
	 * 
	 * // This will reference one line at a time String line = null;
	 * 
	 * try { // FileReader reads text files in the default encoding. FileReader
	 * fileReader = new FileReader(fileName);
	 * 
	 * // Always wrap FileReader in BufferedReader. BufferedReader bufferedReader =
	 * new BufferedReader(fileReader);
	 * 
	 * while((line = bufferedReader.readLine()) != null) { System.out.println(line);
	 * }
	 * 
	 * // Always close files. bufferedReader.close(); } catch(FileNotFoundException
	 * ex) { System.out.println( "Unable to open file '" + fileName + "'"); }
	 * catch(IOException ex) { System.out.println( "Error reading file '" + fileName
	 * + "'"); // Or we could just do this: // ex.printStackTrace(); } } }
	 * 
	 * String[] reci = linija.replaceAll("[^a-zA-Z ]", "").split("\\s+");
	 */
}
