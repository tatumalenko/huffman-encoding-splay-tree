// import java.util.Comparator;

/**
 * Priority based implementation of a queue data structure.
 * <p>
 * 
 * Implements the queue using an ArrayList of generic type E.
 */
public class PriorityQueue<E> {
    /**
     * ArrayList of generic elements to represent the PriorityQueue data.
     */
    private ArrayList<E> data;

    /**
     * 
     */
    private Comparator<E> comparator;

    /**
     * Stores the number of non-null elements.
     */
    private int filled;

    /**
     * Constructs a new PriorityQueue using default parameters. Assigns provided
     * Comparator to use to determine priority of generic elements.
     * 
     * @param comparator the comparator object
     */
    public PriorityQueue(Comparator<E> comparator) {
        this.filled = 0;
        this.data = new ArrayList<E>();
        this.comparator = comparator;
    }

    /**
     * Adds the provided element at the next non-null position in queue.
     * 
     * @param e the element
     */
    public void add(E e) {
        // Prevent adding a null object.
        if (e == null)
            throw new NullPointerException();

        // Locate next null index of data ArrayList to put element in and insert
        // element at that position.
        insert(findNextNullIndex(), e);
    }

    /**
     * Polls the next element with highest priority and removes it from the
     * ArrayList.
     * 
     * @return the removed element
     */
    public E poll() {
        if (filled == 0)
            return null;

        return remove(0);
    }

    /**
     * Removes the element at the provided index position and returns it.
     * 
     * @param index the index position
     * @return the removed element
     */
    public E remove(int index) {
        // Store element to end up returning it later.
        E removed = data.get(index);

        while (data.get(index) != null) {
            int left = 2 * index + 1;
            int right = left + 1;

            // Check if the end of tree is reached.
            if (left >= data.totalLength()) {
                data.set(index, null);
                break;
            }

            // Check which child to promote. If there is a right child, see which
            // of the children has a higher priority and select it to promote.
            int promoted = right >= data.totalLength() || data.get(right) == null ? left
                    : data.get(left) == null || (comparator.compare(data.get(left), data.get(right)) > 0) ? right
                            : left;

            data.set(index, data.get(promoted));
            index = promoted;
        }
        --filled;

        return removed;
    }

    /**
     * Returns the size (number of non-null elements inside) of ArrayList.
     * 
     * @return the size
     */
    public int size() {
        return filled;
    }

    /**
     * Inserts the element at the provided index position and ensures it is then
     * placed in the proper priority location.
     * 
     * @param index the index position
     * @param e     the element
     */
    private void insert(int index, E e) {
        // Set the element at index position and increment used size.
        data.set(index, e);
        filled++;
        prioritize(index);
    }

    /**
     * Performs the priority lookup for the element at the specified index position
     * by looking up the tree until its priority is no longer higher than its
     * parent.
     * 
     * @param index the index position
     */
    private void prioritize(int index) {
        // Starting at index position, go up tree swapping as needed to ensure
        // proper priority placement.
        while (index > 0) {
            int parent = (index - 1) / 2;

            // If element is not higher priority than parent, it's in proper spot.
            if (comparator.compare(data.get(parent), data.get(index)) <= 0) {
                break;
            }

            // Swap the element with its parent if element has higher priority.
            E temp = data.get(index);
            data.set(index, data.get(parent));
            data.set(parent, temp);

            // Assign to parent index to continue looking up the tree.
            index = parent;
        }
    }

    /**
     * Returns the index position of the next non-null element in the array.
     * 
     * @return the index position
     */
    private int findNextNullIndex() {
        int index;

        // Ensure data has room for one more element.
        data.ensureCapacity();

        // Find the earliest index position of a null element.
        for (index = 0; index < data.totalLength(); index++) {
            if (data.get(index) == null)
                break;
        }

        return index;
    }

    /**
     * Overrides the `toString` method.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        return data.toString();
    }

    /**
     * Main class entry point. Performs simple tests to assert proper behaviour of
     * class.
     * 
     * @param args the command-line arguments passed (not used)
     */
    public static void main(String[] args) {
        HuffmanNode[] nodes = new HuffmanNode[12];
        nodes[0] = new HuffmanNode(';', 1, 33, "0");
        nodes[1] = new HuffmanNode('?', 1, 32, "10");
        nodes[2] = new HuffmanNode('x', 1, 30, "111");
        nodes[3] = new HuffmanNode(' ', 2, 34, "111");
        nodes[4] = new HuffmanNode(':', 2, 20, "111");
        nodes[5] = new HuffmanNode('\'', 2, 0, "111");
        nodes[6] = new HuffmanNode(' ', 3, 35, "111");
        nodes[7] = new HuffmanNode('-', 3, 31, "111");
        nodes[8] = new HuffmanNode('p', 3, 29, "111");
        nodes[9] = new HuffmanNode(' ', 4, 36, "111");
        nodes[10] = new HuffmanNode('\"', 4, 23, "111");
        nodes[11] = new HuffmanNode('.', 5, 22, "111");

        Comparator<HuffmanNode> comparator = new HuffmanNode();
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>(comparator);
        for (int i = nodes.length - 1; i >= 0; i--) {
            pq.add(nodes[i]);
            System.out.println(pq);
        }

        int counter = 0;
        while (pq.size() > 1 && counter < 100) {
            counter++;
            System.out.println(pq.poll());
            System.out.println();
            // System.out.println(pq.size());
            System.out.println(pq);
        }
        // System.out.println(pq);
    }
}