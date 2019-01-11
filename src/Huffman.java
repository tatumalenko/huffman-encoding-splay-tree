import java.util.Scanner;
import java.io.File;

/**
 * A Huffman Code extension of the Tree class. Implements the Huffman encoding
 * algorithm for variable-length encoding scheme (prefix-free).
 * <p>
 * 
 * Uses a HashMap as a container collection to store the HuffmanNode objects for
 * fast constant time access. The hashing function is a trivial one based on the
 * conversion of the expected Character based key to ASCII integer.
 * <p>
 * 
 * The building of the tree is accomplished using a PriorityQueue that bases its
 * priority of the HuffmanNode objects based on the overridden Comparator
 * interface method `compare`.
 * 
 * @see Tree
 * @see TreeNode
 * @see HuffmanNode
 * @see HashMap
 * @see PriorityQueue
 * @see Comparator
 * @see ArrayList
 */
public class Huffman extends Tree {
    /**
     * The map used to store the encoding scheme.
     */
    private HashMap<String> codeMap;

    /**
     * Constructs a new Huffman with an empty `codeMap`.
     */
    public Huffman() {
        codeMap = new HashMap<>();
    }

    /**
     * Constructs a new Huffman using the provided `textFileName` as name of the
     * file to use to populate the `codeMap`, which is in turn used to build the
     * priority queue, which is in turn used to build the tree itself. This is used
     * to then build assign the codes to each leaf node of the tree and finally the
     * `codeMap` is populated.
     * 
     * @param textFileName the name of the text file
     */
    public Huffman(String textFileName) {
        HashMap<HuffmanNode> map = buildPriorityMap(textFileName);
        codeMap = new HashMap<String>();
        buildTree(buildPriorityQueue(map));
        assignCodes((HuffmanNode) root, "");
        buildCodeMap((HuffmanNode) root);
    }

    /**
     * Assigns the codes for each node in the tree based on its position respective
     * to its parent.
     * 
     * @param node the node representing tip of subtree investigated
     * @param code the code used to assign to HuffmanNode attribute
     */
    public void assignCodes(HuffmanNode node, String code) {
        if (node == null)
            return;
        node.code = code;
        assignCodes((HuffmanNode) node.left, code + "0");
        assignCodes((HuffmanNode) node.right, code + "1");
    }

    /**
     * Populates the `codeMap` by traversing the tree and assigning the codes of
     * each node inside a map.
     * 
     * @param node the node representing tip of subtree being investigated
     */
    public void buildCodeMap(HuffmanNode node) {
        if (node == null)
            return;

        if ((node.left == null) && (node.right == null)) {
            codeMap.put(node.character, node.code);
        }

        buildCodeMap((HuffmanNode) node.left);
        buildCodeMap((HuffmanNode) node.right);
    }

    /**
     * Encodes the given string using the Huffman encoding scheme built.
     * 
     * @param text the text string wished to be encoded
     * @return the string encoded
     */
    public String encode(String text) {
        String encoding = "";
        for (char c : text.toCharArray())
            encoding += codeMap.get(c) == null ? "" : codeMap.get(c);
        return encoding;
    }

    /**
     * Builds the Huffman tree by polling each child node off the priority queue and
     * inserting the newly constructed node joining them and adding it back to the
     * queue to ensure proper priority order is established for next loop iteration.
     *
     * @param pq the priority queue which stores tree nodes used to build tree
     */
    private void buildTree(PriorityQueue<HuffmanNode> pq) {
        int occurrenceCounter = pq.size() - 1;
        HuffmanNode internal, left, right;

        while (pq.size() > 1) {
            // Poll both children.
            left = pq.poll();
            right = pq.poll();
            // Construct new HuffmanNode acting as parent to both `left` and
            // `right` nodes with combined frequency and new occurrence index.
            internal = new HuffmanNode(' ', left.frequency + right.frequency, ++occurrenceCounter, null, null, left,
                    right);

            pq.add(internal); // Add to queue.
        }
        if (pq.size() < 1) {
            // Queue is empty, don't bother.
            root = null;
        }
        if (pq.size() == 1) {
            // Only one element in queue, pop it off and assign to root of tree.
            root = pq.poll();
        }
    }

    /**
     * Returns a priority queue by simply adding the elements found in the provided
     * map containing the HuffmanNode objects. Priority queue constructed using a
     * provided Comparator instance which defines the overriden `compare` method
     * inside the HuffmanNode class.
     *
     * @param map the character-key based map containing the HuffmanNode values
     * @return the priority queue
     */
    private PriorityQueue<HuffmanNode> buildPriorityQueue(HashMap<HuffmanNode> map) {
        Comparator<HuffmanNode> comparator = new HuffmanNode();
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>(comparator);

        for (char c : map.toKeyArray()) {
            pq.add(new HuffmanNode(map.get(c)));
        }

        return pq;
    }

    /**
     * Returns a priority map populated with character entries found in the text
     * file with the provided `fileName`. Not to be misleaded into thinking this map
     * is ordered by priority, it is simply named this way due to its specific use
     * to eventually populate a priority queue.
     *
     * @param fileName the name of the text file used to populate map
     * @return the priority based map
     */
    private static HashMap<HuffmanNode> buildPriorityMap(String fileName) {
        HashMap<HuffmanNode> map = new HashMap<HuffmanNode>();
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                for (char c : line.toCharArray()) {
                    if (map.containsKey(c)) {
                        map.get(c).frequency = map.get(c).frequency + 1;
                    } else {
                        map.put(c, new HuffmanNode(c, 1, (map.size() + 1) - 1, null));
                    }
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Main class entry point. Prompts user to entry string and prints out the
     * encoded string using the Huffman encoding sheme generated using the text file
     * provided by name in command line argument.
     * 
     * Example:
     * <p>
     * 
     * `java Huffman &#91;textFileName.txt&#93;`
     * <p>
     * 
     * `java Huffman Jabberwock.txt`
     * 
     * @param args contains the file name string used to build tree
     */
    public static void main(String[] args) {
        String textFileName = args[0];
        Huffman ht = new Huffman(textFileName);
        Scanner sc = new Scanner(System.in);
        String uncoded = sc.nextLine();
        System.out.println(ht.encode(uncoded));
        sc.close();
        // System.out.println("Total bits: " + ht.encode(uncoded).length());
        // ht.preOrderTraverse((HuffmanNode) ht.root);
        // System.out.println(((HuffmanNode) ht.root).frequency);
    }
}