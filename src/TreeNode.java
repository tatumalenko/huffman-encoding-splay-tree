/**
 * Abstract a binary tree node data structure superclassing both HuffmanNode and
 * SplayNode.
 */

public abstract class TreeNode {
    /**
     * Data value for the given node.
     */
    protected int data;

    /**
     * Parent node.
     */
    protected TreeNode parent;

    /**
     * Left child node.
     */
    protected TreeNode left;

    /**
     * Right child node.
     */
    protected TreeNode right;

    /**
     * Constructs a new TreeNode and assigns the provided parameters to their
     * respective attributes.
     * 
     * @param data   the data value
     * @param parent the parent node
     * @param left   the left child node
     * @param right  the right child node
     */
    public TreeNode(int data, TreeNode parent, TreeNode left, TreeNode right) {
        this.data = data;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
}