import java.util.*;

/**
 * The anagram node class
 */
public class AnagramNode {

    //each node represents a letter
    private char letter;

    //this is set if the sequence of letters up to this node is a full word
    private boolean endOfWord;

    //hash of the next sequential letters after this letter
    private HashMap<Character, AnagramNode> children;

    //the word so far
    private String currentWord;
    private final int ONE = 1;

    //main constructor
    public AnagramNode(char letter, boolean endOfWord, String currentWord) {
        this.letter = letter;
        this.endOfWord = endOfWord;
        this.children = new HashMap<Character, AnagramNode>();
        this.currentWord = currentWord + letter;
    }

    public AnagramNode() {
        this('\0', false, "");
    }

    //add a letter as a child node
    public AnagramNode addLetter(char letter, boolean endOfWord) {
        if(!children.containsKey(letter)) {
            children.put(letter, new AnagramNode(letter, endOfWord, currentWord));
        }

        return (AnagramNode) children.get(letter);
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public boolean childrenHasLetter(char letter)
    {
        return children.containsKey(letter);
    }

    public AnagramNode getChildNode(char letter)
    {
        AnagramNode result = null;
        if(childrenHasLetter(letter))
        {
            result = children.get(letter);
        }

        return result;
    }
}