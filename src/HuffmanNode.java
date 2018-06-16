// import java.util.Comparator;

/**
 * A Huffman based node extension of the TreeNode and implementation of the
 * Comparator interface.
 */

public class HuffmanNode extends TreeNode implements Comparator<HuffmanNode> {
    /**
     * Character (ASCII) value representing the node.
     */
    public char character;

    /**
     * Frequency of character appearing in a given text.
     */
    public int frequency;

    /**
     * Iccurrence index (0-based) of the character's first appearance in a given
     * text.
     */
    public int occurrence;

    /**
     * Huffman code representing the ASCII character.
     */
    public String code;

    /**
     * Constructs a new HuffmanNode without any parameters. Assigns attributes to
     * default/null values.
     */
    public HuffmanNode() {
        super(-1, null, null, null);
        frequency = -1;
        occurrence = -1;
        code = null;
    }

    /**
     * Constructs a new HuffmanNode and assigns the provided parameters to their
     * respective attributes.
     * 
     * @param character  the character
     * @param frequency  the frequency of the character
     * @param occurrence the occurrence of the character
     * @param code       the code corresponding to character
     */
    public HuffmanNode(char character, int frequency, int occurrence, String code) {
        super(-1, null, null, null);
        this.character = character;
        this.frequency = frequency;
        this.occurrence = occurrence;
        this.code = code;
    }

    /**
     * Constructs a new HuffmanNode and assigns the provided parameters to their
     * respective attributes.
     * 
     * @param character  the character
     * @param frequency  the frequency of the character
     * @param occurrence the occurrence of the character
     * @param code       the code corresponding to character
     * @param parent     the parent node
     * @param left       the left child node
     * @param right      the right child node
     */
    public HuffmanNode(char character, int frequency, int occurrence, String code, HuffmanNode parent, HuffmanNode left,
            HuffmanNode right) {
        super(-1, parent, left, right);
        this.character = character;
        this.frequency = frequency;
        this.occurrence = occurrence;
        this.code = code;
    }

    /**
     * Constructs a new HuffmanNode using the provided node to assign attributes.
     * 
     * @param node the node to use for assigning the attributes
     */
    public HuffmanNode(HuffmanNode node) {
        this(node.character, node.frequency, node.occurrence, node.code);
    }

    /**
     * Overrides the `compare` method of the Comparator interface. Used to assess
     * proper ordering between two HuffmanNode objects.
     * 
     * @param a node to compare
     * @param b node to compare
     */
    @Override
    public int compare(HuffmanNode a, HuffmanNode b) {
        return a.frequency != b.frequency ? a.frequency - b.frequency : b.occurrence - a.occurrence;
    }

    /**
     * Overrides the `toString` method.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Character: " + (char) this.character + " Frequency: " + this.frequency + " Occurrence: "
                + this.occurrence;
    }
}