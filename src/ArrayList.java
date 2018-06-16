import java.lang.reflect.Array;

/**
 * Array basedimplementation of a list data structure.
 * <p>
 * 
 * Performs dynamic resizing of itself upon adding of any new elements when size
 * does not have necessary capacity.
 */
public class ArrayList<E> {
    /**
     * Array of generic elements to represent the ArrayList data.
     */
    private E[] al;

    /**
     * Pointer to the index of last non-null element.
     */
    private int lastIndex = -1;

    /**
     * Default length used to initialize ArrayList.
     */
    private static final int DEFAULT_LENGTH = 10;

    /**
     * Constructs a new ArrayList with default parameters.
     * 
     */
    public ArrayList() {
        al = (E[]) new Object[DEFAULT_LENGTH];
        lastIndex = -1;
    }

    /**
     * Constructs a new ArrayList and fills it with the passed array of generic
     * objects. Sizes the initial length to be twice as long as the size of the
     * provided array.
     * 
     * @param c the array of generic objects
     */
    public ArrayList(E[] c) {
        al = (E[]) new Object[2 * c.length];
        for (E e : c) {
            al[++lastIndex] = e;
        }
    }

    /**
     * Constructs a new ArrayList with the provided initial capacity.
     * 
     * @param initialCapacity the initial capacity
     */
    public ArrayList(int initialCapacity) {
        al = (E[]) new Object[initialCapacity];
        lastIndex = -1;
    }

    /**
     * Adds the provided element to the new last index position.
     * 
     * @param e the element
     */
    public void add(E e) {
        ensureCapacity();
        al[++lastIndex] = e;
    }

    /**
     * Adds the provided element at the specified index position.
     * 
     * @param index the index position
     * @param e     the element to add
     */
    public void add(int index, E e) {
        ensureCapacity();
        for (int i = lastIndex; i >= index; i--) {
            al[i + 1] = al[i];
        }
        al[index] = e;
        lastIndex++;
    }

    /**
     * Ensures array has at least space for one more element.
     */
    public void ensureCapacity() {
        ensureCapacity(size() + 1);
    }

    /**
     * Ensures array has at least space for the provided number of elements.
     * 
     * @param minCapacity the minimum capacity available in array
     */
    public void ensureCapacity(int minCapacity) {
        if (capacity() <= minCapacity) {
            E[] temp = (E[]) new Object[minCapacity];
            int tempIndex = 0;
            for (int i = 0; i < size(); i++) {
                temp[tempIndex++] = al[i];
            }
            al = temp;
            lastIndex = tempIndex - 1;
        }
    }

    /**
     * Returns the element at the specified index position.
     * 
     * @param index the index position
     * @return the element
     */
    public E get(int index) {
        return al[index];
    }

    /**
     * Sets the element at the specified index position. Also returns the removed
     * element.
     * 
     * @param index the index position
     * @param e     the element
     * @return the element replaced
     */
    public E set(int index, E e) {
        E temp = al[index];
        al[index] = e;
        if (index > lastIndex)
            lastIndex = index;
        return temp;
    }

    /**
     * Returns whether the array is empty.
     * 
     * @return whether the array is empty
     */
    public boolean isEmpty() {
        return lastIndex == -1;
    }

    /**
     * Removes the element at the specified index position and returns it.
     * 
     * @param index the index index position
     * @return the removed element
     */
    public E remove(int index) {
        E temp = al[index];
        for (int i = index; i < lastIndex; i++) {
            al[i] = al[i + 1];
        }
        al[lastIndex] = null;
        lastIndex--;
        return temp;
    }

    /**
     * Returns the size of the array.
     * 
     * @return the size
     */
    public int size() {
        return lastIndex + 1;
    }

    /**
     * Returns the number of elements not occupied yet.
     * 
     * @return the capacity
     */
    public int capacity() {
        return al.length - size();
    }

    /**
     * Returns the total number of elements of the array (including those occupied
     * by null elements).
     * 
     * @return the total length
     */
    public int totalLength() {
        return al.length;
    }

    /**
     * Converts the ArrayList to a primitive array of objects.
     * 
     * @param a the ArrayList
     * @return the array
     */
    public <T> T[] toArray(T[] a) {
        // Count the number of non null elements in array.
        int nonNullSize = 0;
        for (int i = 0; i < size(); i++) {
            if (al[i] != null)
                nonNullSize++;
        }

        // Initialize an array of generics using Java's Reflection library.
        // This is the only way that it can be done in Java, array of generic
        // types is technically not usually permitted.
        a = (T[]) Array.newInstance(a.getClass().getComponentType(), nonNullSize);

        // Assign the elements into new generic array.
        int tempIndex = -1;
        for (E e : al) {
            if (e != null) {
                a[++tempIndex] = (T) e;
            }
        }
        return a;
    }

    /**
     * Overrides the `toString` method.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size() > 0 ? al[0].toString().length() * (size()) : 0);
        for (int i = 0; i < size(); i++) {
            sb.append((al[i] == null ? null : al[i].toString()) + "\n");
        }
        return sb.toString();
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
        ArrayList<HuffmanNode> al = new ArrayList<HuffmanNode>(nodes);
        System.out.println(al.get(3));
        al.add(nodes[0]);
        System.out.println(al);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(nodes[0]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(nodes[0]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(nodes[0]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(nodes[0]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(nodes[0]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(nodes[0]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(nodes[0]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);

        al = new ArrayList<HuffmanNode>();
        al.add(nodes[1]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);

        al = new ArrayList<HuffmanNode>(12);
        al.add(nodes[1]);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.set(0, nodes[2]);
        al.set(1, nodes[1]);
        System.out.println(al);
        al.set(4, nodes[0]);
        System.out.println(al);
        System.out.println(al.totalLength() + " " + al.size() + " " + al.capacity() + " " + al.lastIndex);
        al.add(4, nodes[2]);
        System.out.println(al);
        al.add(4, nodes[0]);
        al.set(8, nodes[2]);
        // System.out.println(al);
        System.out.println(al);

        al.remove(0);
        System.out.println(al);

        al.remove(4);
        System.out.println(al);

    }
}