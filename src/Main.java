import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.HashSet;
import java.util.Collections;
import java.io.File;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{
        ArrayList<String> commonWords = getCommonWords("src/commonWords.txt");
        ArrayList<String> x = extractText("src/textOne.txt", commonWords);
        HashMap<String, Integer> freqTable = getFrequencies(x);
        ArrayList<String> ranking = sortWordsByFreq(freqTable);
        System.out.println(ranking);

    }

    //From the arraylist of words ––> create a frequency table of each word : frequency
    public static HashMap<String, Integer> getFrequencies(ArrayList<String> words){
        HashMap<String, Integer> freqTable = new HashMap<>();
        for (String word: words){
            if (freqTable.containsKey(word)){
                freqTable.put(word, freqTable.get(word)+1);
            }
            else{
                freqTable.put(word, 1);
            }
        }
        return freqTable;
    }

    //Sorts the list of unique words by frequency
    public static ArrayList<String> sortWordsByFreq(HashMap<String, Integer> freqTable) {
        ArrayList<String> uniqueWords = new ArrayList<>();
        for (String word : freqTable.keySet()) {
            uniqueWords.add(word);
        }
        for (int j=0; j < uniqueWords.size(); j++) {
            for (int i = 0; i < uniqueWords.size() - 1; i++) {
                if (freqTable.get(uniqueWords.get(i)) > freqTable.get(uniqueWords.get(i + 1))) {
                    String temp = uniqueWords.get(i + 1);
                    uniqueWords.set(i + 1, uniqueWords.get(i));
                    uniqueWords.set(i, temp);
                }
            }
        }
        return uniqueWords;
    }





    //Read text into an arraylist
    public static ArrayList<String> extractText(String filename, ArrayList<String> commonWords) throws FileNotFoundException {
        ArrayList<String> words = new ArrayList<>();
        File text = new File(filename);
        Scanner scanner = new Scanner(text);
        while (scanner.hasNextLine()){
            String w = scanner.next();
            if (Collections.binarySearch(commonWords, w) <0){
                words.add(cleanWord(w));
            }
        }
        return words;
    }

    //Get list of common words
    public static ArrayList<String> getCommonWords(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        ArrayList<String> commonWords = new ArrayList<>();
        while (scanner.hasNextLine()){
            commonWords.add(scanner.next());
        }
        return selectionSort(commonWords);
    }

    //Remove punctuation around a word

    public static String cleanWord(String word){
        word = word.toLowerCase();
        String cleanedWord = "";
        for (int i=0; i<word.length(); i++){
            if ((int) word.charAt(i) >= 97 && (int) word.charAt(i) <= 122){
                cleanedWord += word.charAt(i);
            }
        }
        return cleanedWord;
    }

    //Sort the list of common words -- so that we can do binary search
    public static ArrayList<String> selectionSort(ArrayList<String> list) {
        String temp;
        int mIndex;
        for (int i=0; i<list.size(); i++){
            mIndex = i;
            for (int j=i+1; j<list.size(); j++){
                if (list.get(j).toLowerCase().compareTo(list.get(mIndex).toLowerCase()) < 0){
                    mIndex = j;
                }
            }
            temp = list.get(mIndex);
            list.set(mIndex, list.get(i));
            list.set(i, temp);
        }
        return list;
    }


}