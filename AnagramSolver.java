import java.io.*;
import java.util.*;

/**
 * The anagram solver class
 */
class AnagramSolver {
    private static final int ONE = 1;

    //This will holds the anagrams solution
    private static Set<String> anagrams;

    public static AnagramNode readDictionary(String path) {
        System.out.println("Reading in dictionary");
        AnagramNode anagramNode = new AnagramNode();

        //the idea here is to build a tree data structure for fast anagram solving. Each node holds a single letter.
        //Each child node holds a letter which comes after this letter. When solving the anagram we'll recurse through the
        //combinations of letters and iterate down the tree. This speeds up the lookup because we can stop the recursion
        //as soon as it doesn't match the tree.
        try {
            //read in the dictionary file line by line and create the anagram tree
            Scanner input = new Scanner(new File(path));
            while (input.hasNext()) {
                AnagramNode tempAnagramNode = anagramNode;

                String word = input.nextLine().toLowerCase().trim();

                //make a node for each letter for efficient lookup later
                for(int i = 0; i < word.length(); i++) {
                    char letter = word.charAt(i);
                    boolean endOfWord = (word.length() - ONE == i);
                    tempAnagramNode = tempAnagramNode.addLetter(letter, endOfWord);
                }
            }

            System.out.println("Finished reading in dictionary");
        }

        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            anagramNode = null;
        }

        return anagramNode;
    }

    //display the results
    public static void printAnagrams() {
        if(anagrams != null) {
            int length = anagrams.size();
            if(length == ONE) {
                System.out.println("Found " + length + " result");
            }
            else {
                 System.out.println("Found " + length + " results");
            }

            for (String anagram : anagrams) {
                System.out.println(anagram);
            }

            System.out.println();
        }
    }

    //this method finds the anagrams within the given input
    public static void getAnagrams(AnagramNode anagramNode, String input) {
        String[] words = input.split(" ");

        //find the anagrams for each word
        for(int i = 0; i < words.length; i++) {
            anagrams = new HashSet<String>();
            String word = words[i].toLowerCase().trim();
            System.out.println("Anagrams for " + word + ":");
            getAnagramsHelper(word, word, anagramNode);
            printAnagrams();
        }
    }

    //recursively search for the anagrams
    public static void getAnagramsHelper(String word, String userWord, AnagramNode anagramNode) {
        //if end of word, this is the end case for recursion
        if(word.length() == 0) {
            String currentWord = anagramNode.getCurrentWord();

            //if we've reached the end of a word and the word we found is not the same as the user input word
            //we've found a solution
            if(anagramNode.isEndOfWord() && !currentWord.trim().equals(userWord.trim())) {
                anagrams.add(currentWord);
            }
        }

        else {
            //iterate through the word and remove a single letter each time
            for(int i = 0; i < word.length(); i++) {
                char letter = word.charAt(i);

                //if the child node has the next letter, remove it, iterate down the tree and recurse again
                if(anagramNode.childrenHasLetter(letter)) {
                    String subWord = word.substring(0, i) + word.substring(i + ONE);

                    getAnagramsHelper(subWord, userWord, (AnagramNode) anagramNode.getChildNode(letter));
                }
            }
        }
    }

    public static void main(String[] args) {
        String dictionaryPath = "dictionary.txt";

        //read in the dictionary and build the anagram node tree
        AnagramNode anagramNode = readDictionary(dictionaryPath);
        anagrams = null;

        //parse user input and find the anagrams
        if(anagramNode != null) {
            while(true) {
                System.out.println("Please enter a sentence...   (type 'exit' to quit)");
                Console console = System.console();
                String input = console.readLine();
                if(input.trim().equals("exit")) {
                    break;
                }

                getAnagrams(anagramNode, input);
            }
        }
    }
}