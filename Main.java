package package35;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dictionary.connect();
		Dictionary.transferFromDBtoHM();
		Book.readAndSeparateWords();
		Book.compareBookAndDictionary2();
/*		
		int i = 0;
		for(String words : Book.book ) {//test to verify how the words from the book are added to the list.
			if(i == 40) {
				break;
			}else {
				System.out.println(words);
				i++;
			}
		}
*/
		Dictionary.transferFromALtoDB();
		Dictionary.wordCounterFromDictionaryInBook();
		Dictionary.top20Words();
		String temp;
		for (int i = 1; i < Dictionary.topWords.length; i++) {
		    for (int j = i; j > 0; j--) {
		     if (Dictionary.dictionary.get(Dictionary.topWords[j]) > Dictionary.dictionary.get(Dictionary.topWords[j - 1])) {
		      temp = Dictionary.topWords[j];
		      Dictionary.topWords[j] = Dictionary.topWords[j - 1];
		      Dictionary.topWords[j - 1] = temp;
		     }
		    }
		   }
		for(String top20 : Dictionary.topWords) {
			System.out.println(top20 + " " + Dictionary.dictionary.get(top20));
		}
		Dictionary.writeNewWords();
		Dictionary.disconnect();
	}

}
