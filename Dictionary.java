package package35;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Dictionary {
//	static HashMap<String, String> dictionary = new HashMap<String, String>();
	static HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
	static HashMap<String, Integer> dictionaryWordCounter = new HashMap<String, Integer>();
	static String[] topWords = new String[20];
	private String wordType;// maybe remove it later

	static String connectionString = "jdbc:sqlite:C:\\Users\\Srdja\\Downloads\\Java\\Zavrsni Rad 09232019\\Dictionary - Copy.db";
	static Connection connection;

	public static void connect() {
		try {
			connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void disconnect() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void transferFromDBtoHM() {
//		String query = "SELECT entries.word, entries.definition FROM entries";
		String query = "SELECT entries.word FROM entries";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				String word = rs.getString("word");
//				String definition = rs.getString("definition");

//				dictionary.put(word.toLowerCase(), definition);
				dictionary.put(word.toLowerCase(), 0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean postojiRec(String rec) {
		if (dictionary.containsKey(rec))
			return true;
		else
			return false;
	}

	public static void transferFromALtoDB() {
		List<String> newUniqueWords = Book.newWords.stream().distinct().collect(Collectors.toList());
		String query = null;

		try {
			query = "SELECT name FROM sqlite_master WHERE type='table' AND name='New Words'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.next() == true) {
				// insert into table
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < newUniqueWords.size(); i++) {
					if (i == newUniqueWords.size() - 1) {
						sb.append("('" + newUniqueWords.get(i) + "');");
						continue;
					} else {
						sb.append("('" + newUniqueWords.get(i) + "'),\n");
					}
				}
				query = "INSERT INTO \"New Words\" (word) VALUES " + sb.toString();
				statement.executeUpdate(query);

			} else {
				// create table
				query = "CREATE TABLE \"New Words\" (\n" + "	\"word\"	TEXT UNIQUE,\n" + "	PRIMARY KEY(\"word\")\n"
						+ ");";
				statement.executeUpdate(query);
				// and then insert into table
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < newUniqueWords.size(); i++) {
					if (i == newUniqueWords.size() - 1) {
						sb.append("('" + newUniqueWords.get(i) + "');");
						continue;
					} else {
						sb.append("('" + newUniqueWords.get(i) + "'),\n");
					}
				}
				query = "INSERT INTO \"New Words\" (word) VALUES " + sb.toString();
				statement.executeUpdate(query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void wordCounterFromDictionaryInBook() {
		for (String wordFromBook : Book.book) {
			if (Dictionary.dictionary.containsKey(wordFromBook)) {
				Dictionary.dictionaryWordCounter.put(wordFromBook, Dictionary.dictionary.get(wordFromBook) + 1);
			}
		}

	}

	public static void top20Words() {
/*		
		   for(String wordFromDictionary : Dictionary.dictionary.keySet()) {//throws java.util.ConcurrentModificationException
		 		if(Dictionary.dictionary.get(wordFromDictionary)==0) {
		  			Dictionary.dictionary.remove(wordFromDictionary); 
		  		} 
		   }
*/		 
boolean transfer = true;
//		Dictionary.dictionary.entrySet().removeIf(entry -> (0 == entry.getValue()));
		
		int i = 0;
		
		for (String wordFromDictionary : Dictionary.dictionaryWordCounter.keySet()) {
			if (i == 20) {
				break;
			} else {
				topWords[i] = wordFromDictionary;
				i++;
			}
		}
//		for(int l=0; l<topWords.length; l++) System.out.println(topWords[l]);
		
		for (String wordFromDictionary : Dictionary.dictionary.keySet()) {
			int k = 0;
			int min = Dictionary.dictionaryWordCounter.get(topWords[0]);
			for(int j=0; j<topWords.length; j++ ) {
		         if(Dictionary.dictionaryWordCounter.get(topWords[j])<min) {
		            min = Dictionary.dictionaryWordCounter.get(topWords[j]);
		            k = j;
		         }
		      }
			if (Dictionary.dictionaryWordCounter.get(wordFromDictionary) > min) {
				for(int m=0;m<topWords.length;m++) {
					if(topWords[m].equals(wordFromDictionary)) {
						transfer = false;
						break;
					}else transfer = true;
				}
				if(transfer)
				topWords[k]= wordFromDictionary;
			}
		}
	}
	
	public static void writeNewWords() {
		List<String> oldAndNewWords = new ArrayList<String>();
		for(String newWordsFromBook : Book.book) {
			oldAndNewWords.add(newWordsFromBook);
		}
		for(String oldWordsFromDictionary : dictionary.keySet()) {
			oldAndNewWords.add(oldWordsFromDictionary);
		}
		oldAndNewWords = oldAndNewWords.stream().distinct().collect(Collectors.toList());
		Collections.sort(oldAndNewWords);
		try {
			FileWriter fw;
			fw = new FileWriter("KnownAndNew.txt");
			for(String eachWord : oldAndNewWords) {
			fw.write(eachWord.toString()+"\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
