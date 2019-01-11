import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract a binary tree data structure superclassing both Huffman and
 * SplayTree.
 *
 * @see TreeNode
 */
public abstract class Tree {
    /**
     * Root node.
     */
    protected TreeNode root;

    /**
     * Total number of nodes within tree.
     */
    protected int size;

    /**
     * Logger instance used for simple warnings found when traversing the tree.
     */
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Tree() {
        this.root = null;
        this.size = 0;
        LOGGER.setLevel(Level.INFO);
    }

    /**
     * Traverses the tree in post-order and prints to the console the node's `data`
     * attribute.
     * 
     * @param node the node representing the tip of the subtree being investigated
     */
    public void postOrderTraverse(TreeNode node) {
        if (node != null) {
            postOrderTraverse(node.left);
            postOrderTraverse(node.right);
            System.out.print(node.data + (node == root ? " " : ","));
        }
    }

    public boolean isValidSplayStructure(TreeNode node) {
        if (node != null) {
            if (node.left != null & node.right != null) {
                if (node.data < node.right.data || node.data < node.left.data) {
                    LOGGER.warning("Following node has an improper BST child: " + node.data);
                    return false;
                }
            } else if (node.left != null) {
                if (node.data < node.left.data) {
                    LOGGER.warning("Following node has an improper BST child: " + node.data);
                    return false;
                }
            } else if (node.right != null) {
                if (node.data > node.right.data) {
                    LOGGER.warning("Following node has an improper BST child: " + node.data);
                    return false;
                }
            }

            // Log simple warnings in case BST properties are violated.
            isValidSplayStructure(node.left);
            isValidSplayStructure(node.right);
        }
        return true;
    }
}