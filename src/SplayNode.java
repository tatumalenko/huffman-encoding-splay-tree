/**
 * A Splay based node extension of the TreeNode.
 */
public class SplayNode extends TreeNode {
    /**
     * Constructs a new TreeNode using default parameters.
     * 
     */
    public SplayNode() {
        this(0, null, null, null);
    }

    /**
     * Constructs a new SplayNode and assigns provided `data` to its attribute.
     * 
     * @param data the data value
     */
    public SplayNode(int data) {
        this(data, null, null, null);
    }

    /**
     * Constructs a new SplayNode and assigns the provided parameters to its
     * attributes.
     * 
     * @param data   the data value
     * @param parent the parent node
     * @param left   the left child node
     * @param right  the right child node
     */
    public SplayNode(int data, SplayNode parent, SplayNode left, SplayNode right) {
        super(data, parent, left, right);
    }

    /**
     * Overrides the `toString` method.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        return this.data + " (Left: " + this.left + ", Right: " + this.right + ")";
    }
}
