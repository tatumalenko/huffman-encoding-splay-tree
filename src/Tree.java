/**
 * Abstract a binary tree data structure superclassing both Huffman and
 * SplayTree.
 */

public abstract class Tree {
    /**
     * Root node.
     */
    protected TreeNode root;

    /**
     * Total number of nodes within tree.
     */
    protected int size = 0;

    /**
     * Entry point wrapper for pre-order traversal printing of the tree.
     */
    public void preOrderTraverse() {
        preOrderTraverse(root);
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

    /**
     * Traverses the tree in pre-order and prints to the console the node's `data`
     * attribute..
     * 
     * @param r the node representing the tip of the subtree being investigated
     */
    public void preOrderTraverse(TreeNode r) {
        if (r != null) {
            System.out.print((char) r.data + " ");
            preOrderTraverse(r.left);
            preOrderTraverse(r.right);
        }
    }
}