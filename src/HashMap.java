import java.util.Arrays;

/**
 * Hash table based implementation of a map data structure.
 * <p>
 * 
 * Implements the map using an ArrayList of generic type T.
 * <p>
 * 
 * Positions the generic T element in the index of the ArrayList that
 * corresponds to the ASCII int code. Since the possible values of each key
 * within the map will be limited to the ASCII character encoding set, this will
 * be restricted to an ArrayList of maximum size of 128 keys for every possible
 * unique ASCII character values.
 */
public class HashMap<T> {
    /**
     * ArrayList of generic elements to represent the HashMap data.
     */
    private ArrayList<T> data;

    /**
     * Default length used to initialize HashMap.
     */
    private static final int DEFAULT_LENGTH = 128;

    /**
     * Stores the number of entries occupied.
     */
    private int filled;

    /**
     * Constructs a new HashMap using default parameters.
     * 
     */
    public HashMap() {
        this.data = new ArrayList<T>(DEFAULT_LENGTH);
        this.filled = 0;
    }

    /**
     * Returns element at position associated to ASCII value of character key.
     * 
     * @param key the ASCII character key
     * @return the value of the entry
     */
    public T get(Character key) {
        return data.get(hash(key));
    }

    /**
     * Sets element at position associated to ASCII value of character key to
     * provided value.
     * 
     * @param key   the ASCII character key
     * @param value the value associated to key
     */
    public void put(Character key, T value) {
        if (!containsKey(key))
            filled++;
        data.set(hash(key), value);
    }

    /**
     * Determines if the map contains the key entry.
     * 
     * @param key the ASCII character key
     * @return whether map contains entry
     */
    public boolean containsKey(Character key) {
        return data.get(hash(key)) != null;
    }

    /**
     * Returns the size of the HashMap.
     * 
     * @return the size
     */
    public int size() {
        return filled;
    }

    /**
     * Converts map entry keys to an array.
     * 
     * @return the entry keys
     */
    public Character[] toKeyArray() {
        int nonNullSize = 0;
        for (int i = 0; i < data.totalLength(); i++) {
            if (data.get(i) != null)
                nonNullSize++;
        }

        Character[] temp = new Character[nonNullSize];
        int tempIndex = -1;
        for (int i = 0; i < data.totalLength(); i++) {
            if (data.get(i) != null)
                temp[++tempIndex] = (char) i;
        }
        return temp;
    }

    /**
     * Converts map entry values to an array.
     * 
     * @return the entry values
     */
    public T[] toValueArray() {
        return data.toArray((T[]) new Object[0]);
    }

    /**
     * Overrides the `toString` method.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < data.totalLength(); i++) {
            if (data.get(i) != null)
                sb.append("Key: " + (char) i + ", Value: " + data.get(i).toString()
                        + (i != data.size() - 1 ? ",\n" : "}"));
        }
        return sb.toString();
    }

    /**
     * Returns the hashing function value of the entry key. Uses the trivial
     * approach of mapping the character key to its representative ASCII integer.
     * 
     * @param key the entry key
     * @return the ASCII integer value
     */
    private int hash(Character key) {
        return (int) key;
    }

    /**
     * Main class entry point. Performs simple tests to assert proper behaviour of
     * class.
     * 
     * @param args the command-line arguments passed (not used)
     */
    public static void main(String[] args) {
        HuffmanNode[] nodes = new HuffmanNode[3];
        nodes[0] = new HuffmanNode('a', 1, 0, "0");
        nodes[1] = new HuffmanNode('b', 2, 1, "10");
        nodes[2] = new HuffmanNode('c', 3, 2, "111");

        HashMap<HuffmanNode> map = new HashMap<HuffmanNode>();
        System.out.println("Size: " + map.size());
        map.put(nodes[0].character, nodes[0]);
        System.out.println("Size: " + map.size());
        map.put(nodes[1].character, nodes[1]);
        System.out.println("Size: " + map.size());
        System.out.println(map);
        HuffmanNode[] nodes2 = map.data.toArray(new HuffmanNode[0]);
        System.out.println("Non null size: " + nodes.length);
        map.put(nodes[2].character, nodes[2]);
        System.out.println(Arrays.toString(map.toKeyArray()));
        System.out.println(Arrays.toString(map.toValueArray()));

        System.out.println(Arrays.toString(nodes2));
        System.out.println(map.containsKey('c'));

        HashMap<Integer> map2 = new HashMap<Integer>();
        map2.put('a', 1);
        map2.put('b', 2);
        System.out.println(map2);
        System.out.println(map2.get('a'));
    }
}