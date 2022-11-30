package anagramFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AnagramFinder {

    public static void main(String[] args) {
        new AnagramFinder().run();
    }

    private void run() {
        //1. : magyar_szavak_listaja.txt-t betölteni egy memóriában lévő listába
        List<String> words = loadWordsFromFile("hungarian_words/magyar_szavak_listaja_hosszabb.txt");
        //2.: multimap készítése K: a betűstatisztika , V: lista a szavakkal
        Map<Map<Character, Integer>, List<String>> wordMultiMap = new HashMap<>();
        //3.:
        for (String word : words) {
            Map<Character, Integer> stats = calculateLetterStatistics(word);
            if(wordMultiMap.containsKey(stats)) { //ehhez a betűstatisztikához tartozik-e már szó
                List <String> wordList = wordMultiMap.get(stats);
                wordList.add(word);
            } else {
                List<String> wordList = new ArrayList<>();
                wordList.add(word);
                wordMultiMap.put(stats, wordList);
            }
        }

		//4.: kiiratás:
		Set<Map.Entry<Map<Character, Integer>, List<String>>> entrySet = wordMultiMap.entrySet();
		for (Map.Entry<Map<Character, Integer>, List<String>> entry : entrySet) {
			if(entry.getValue().size() > 1) {
			//System.out.println(entry.getKey() + " -> " + entry.getValue());
			//System.out.println(entry.getValue());
			}
		}

		//5.: sorrend felállítása

		Collection<List<String>> values = wordMultiMap.values(); //csak az értékek elkérése
		List<List<String>> anagrams = new ArrayList<>();
		for (List<String> listOfWords : values) {
			if(listOfWords.size() > 1) {
				anagrams.add(listOfWords);
			}
		}

		Collections.sort(anagrams, new ListLenghtComparator());

		for (List<String> listOfWords : anagrams) {
			System.out.println(listOfWords);
		}

        /*
        wordMultiMap.values()
                .stream()
                .filter(list -> list.size() > 1)
                .sorted((list1, list2) -> Integer.compare(list2.size(), list1.size()))
                .forEach(System.out::println);
        */
    }



    private Map<Character, Integer> calculateLetterStatistics(String word) {
        //Map<String, Integer> lettersStatistics = new LinkedHashMap<>(); //eredeti sorrend
        Map<Character, Integer> lettersStatistics = new HashMap<>(); //legyen inkább ez: nem kell semmilyen sorrend (:gyors), és nem kell a toString()** sor sem (:gyors)
        char [] letters = word.toCharArray();

        for (char letter : letters) {

            //String letterAsString = Character.toString(letter);//**

            if(lettersStatistics.containsKey(letter)) {//már szerepelt
                Integer count = lettersStatistics.get(letter);//megkérdezzük hányszor //LÉNYEG
                count++; //azt megnöveljük 1-el
                lettersStatistics.put(letter, count); //és újra eltároljuk, felülírva a párban az értéket //LÉNYEG
            } else {
                lettersStatistics.put(letter, 1); //3. (1 -> volt egy autoboxing (JAVA 5-től))
            }
        }
        return lettersStatistics;
    }

    private List<String> loadWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(fileName), "UTF-8")){ //"UTF-8" :  hungarian
            //List<String> words = new ArrayList<>();
            while(scanner.hasNext()){
                String word = scanner.nextLine();
                //System.out.println(word);
                words.add(word); //az összes szót belerakjuk a listába
            }
            System.out.println(words.size() +" words in the hungarian_words_list.txt file.");
            //System.out.println("HashSet.size(): "+new HashSet<>(words).size());//ugyanakkora mint a lista: nincs benne duplikáció
            return words;
        } catch (FileNotFoundException e) {
            System.out.println("The system cannot find the specified file.");
            //e.printStackTrace();
            //e.getMessage();
        }
        return Collections.emptyList();
    }

}
