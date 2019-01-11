import java.util.Scanner;
import java.io.File;

/**
 * A Splay Code extension of the Tree class. Implements the Splay tree algorithm
 * using a binary tree.
 * <p>
 * 
 * Unlike traditional splay tree algorithms, this one does not perform and extra zig
 * operation (if needed) to push the node up the root if it is just below it (i.e.
 * a child of the root) when it ends up there. This has no practical use of course
 * other than being cosmetically different than traditional implementations.
 *
 * @see Tree
 * @see TreeNode
 * @see SplayNode
 */
public class SplayTree extends Tree {
    /**
     * Number of comparison operations.
     */
    private int compareCount = 0;

    /**
     * Number of zigzig operations.
     */
    private int zigzigCount = 0;

    /**
     * Number of zigzag operations.
     */
    private int zigzagCount = 0;

    /**
     * Constructs a new SplayTree with default attributes.
     */
    public SplayTree() {
        root = null;
    }

    /**
     * Inserts new node in tree with provided data value.
     * 
     * @param data the data value
     */
    public void insert(int data) {
        TreeNode node = root;
        TreeNode parent = null;

        // Increment tree size if not already in it.
        if (!search(data))
            size++;

        // Descend tree to find which node should contain new node as its child.
        while (node != null) {
            parent = node;
            if (data < parent.data)
                node = node.left;
            else
                node = node.right;
        }

        // Position the new node as either the root or left or right child of parent
        // node just found.
        node = new SplayNode();
        node.data = data;
        node.parent = parent;

        if (parent == null)
            root = node;
        else if (data < parent.data)
            parent.left = node;
        else // (data >= parent.data) This take care of duplicates.
            parent.right = node;

        // Splay the node.
        splay(node);
    }

    /**
     * Removes the first node from the tree with the given data value.
     * 
     * @param data the data value
     */
    public void remove(int data) {
        // No splaying needed to be done here since it will be done in the
        // call to `searchNode` method.
        remove(searchNode(data));
    }

    /**
     * Searches for a node with given data value and returns if it is found.
     * 
     * @param data the data value
     * @return whether node with data value was found
     */
    public boolean search(int data) {
        return searchNode(data) != null;
    }

    /**
     * Returns size (total number of nodes) of tree.
     * 
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Traverses the tree in post-order and prints to the console the number
     * comparisons, zig-zig, and zig-zag operations at the provided number of operations.
     * 
     * @param stepCount the number of completed operations
     */
    public void postOrderTraverse(int stepCount) {
        System.out.print("Traversal at " + stepCount + ": ");

        postOrderTraverse(root);

        System.out.println();
        System.out.println(compareCount + (compareCount > 1 ? " compares" : " compare"));
        System.out.println(zigzigCount + (zigzigCount > 1 ? " Zig-Zigs" : " Zig-Zig"));
        System.out.println(zigzagCount + (zigzagCount > 1 ? " Zig-Zags" : " Zig-Zag"));
    }

    /**
     * Removes specified node from the tree.
     *
     * @param node the node to remove
     */
    private void remove(TreeNode node) {
        if (node == null)
            return;

        TreeNode temp;
        TreeNode child;
        TreeNode parent;

        // Account for the 3 cases in which the node to remove has children.
        if (node.left != null && node.right != null) {
            // Node has both left and right children.
            temp = node.left;

            // Find the rightmost leaf node.
            while (temp.right != null)
                temp = temp.right;

            // Reassign the value to the rightmost leaf node value.
            child = temp.left;
            node.data = temp.data;
        } else if (node.left != null) {
            // Node to remove has only a left child.
            temp = node;
            child = node.left;
        } else {
            // Node to remove has only a right child.
            temp = node;
            child = node.right;
        }

        // Take care of link to parent branch.
        parent = temp.parent;
        if (child != null)
            child.parent = parent;

        if (parent == null) {
            // Case where the node is the root or a child of the root.
            root = child;
            return;
        }

        // Link parent's child to appropriate node.
        if (temp == parent.left)
            parent.left = child;
        else
            parent.right = child;

        size--; // Decrement the tree size.
    }

    /**
     * Searches for a node with given data value and returns it (or null).
     * 
     * @param data the data value
     * @return the found node (or null)
     */
    private TreeNode searchNode(int data) {
        TreeNode node = root;

        while (node != null) {
            if (data < node.data) {
                node = node.left;
            } else if (data > node.data) {
                node = node.right;
            } else {
                splay(node);
                return node;
            }
        }

        return null;
    }

    /**
     * Performs single clockwise rotation of subtree with root `parent`.
     * 
     * @param child  the child node
     * @param parent the parent node
     */
    private void zigRight(TreeNode child, TreeNode parent) {
        if (parent.parent != null) {
            compareCount++;
            if (parent == parent.parent.left) // P is a left child of G
                parent.parent.left = child;
            else // P is a right child of G
                parent.parent.right = child;
        }

        if (child.right != null)
            child.right.parent = parent;

        child.parent = parent.parent;
        parent.parent = child;
        parent.left = child.right;
        child.right = parent;
    }

    /**
     * Performs single counter-clockwise rotation of subtree with root `parent`.
     * 
     * @param child  the child node
     * @param parent the parent node
     */
    private void zigLeft(TreeNode child, TreeNode parent) {
        if (parent.parent != null) {
            compareCount++;
            if (parent == parent.parent.left) {
                parent.parent.left = child;
            } else
                parent.parent.right = child;
        }

        if (child.left != null)
            child.left.parent = parent;

        child.parent = parent.parent;
        parent.parent = child;
        parent.right = child.left;
        child.left = parent;
    }

    /**
     * Splays the node up the tree. Unlike traditional splay algorithms, this one
     * does not perform and extra zig operation (if needed) to push the node up the
     * root if it is just below it (i.e. a child of the root) when it ends up there.
     * 
     * @param node the node
     */
    private void splay(TreeNode node) {
        boolean stopRule = false;

        while (node.parent != null) {
            if (node.parent.parent == null) {
                stopRule = true;
                break;
            } else {
                compareCount++;
                if (node == node.parent.left) {
                    compareCount++;
                    if (node.parent == node.parent.parent.left) {
                        zigRight(node.parent, node.parent.parent);
                        zigRight(node, node.parent);
                        zigzigCount++;
                    } else {
                        zigRight(node, node.parent);
                        zigLeft(node, node.parent);
                        zigzagCount++;
                    }
                } else {
                    compareCount++;
                    if (node.parent == node.parent.parent.left) {
                        zigLeft(node, node.parent);
                        zigRight(node, node.parent);
                        zigzagCount++;
                    } else {
                        zigLeft(node.parent, node.parent.parent);
                        zigLeft(node, node.parent);
                        zigzigCount++;
                    }
                }
            }
        }
        root = stopRule ? node.parent : node;
    }

    /**
     * Processes the text file using provided file name to store each line into an
     * array of strings and returns it.
     * 
     * @param fileName the name of the text file
     * @return the string array found in text file
     */
    private static String[] processOperations(String fileName) {
        String[] operations;
        int fileLines = 0;

        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                fileLines++;
                fileScanner.nextLine();
            }
            fileScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        operations = new String[fileLines];

        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            int lineCount = 0;
            while (sc.hasNextLine()) {
                operations[lineCount] = sc.nextLine();
                lineCount++;
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return operations;
    }

    /**
     * Main class entry point. Prints out the post-order traversal of the tree
     * generated using the text file provided by name in command line argument and
     * step to traverse integer value.
     * <p>
     * 
     * Example:
     * <p>
     * `java SplayTree &lt;textFileName.txt&gt; &#91;step-to-traverse&#93;`
     * <p>
     * `java SplayTree Operations.txt 6`
     * 
     * @param args contains the file name string used to build tree
     */
    public static void main(String[] args) {
        String textFileName = args[0];
        int stepToTraverse = -1;

        if (args.length == 2)
            stepToTraverse = Integer.parseInt(args[1]);

        String[] operations = processOperations(textFileName);
        SplayTree splayTree = new SplayTree();
        int stepCount = 0;

        for (String operation : operations) {
            char action = operation.charAt(0);

            switch (action) {
            case 'a':
                splayTree.insert(Integer.parseInt(operation.substring(1)));
                break;
            case 'r':
                splayTree.remove(Integer.parseInt(operation.substring(1)));
                break;
            case 'f':
                splayTree.search(Integer.parseInt(operation.substring(1)));
                break;
            }
            stepCount++;

            if (stepToTraverse != -1 && stepToTraverse == stepCount)
                splayTree.postOrderTraverse(stepCount);
        }

        if (stepToTraverse == -1) {
            System.out.println(splayTree.compareCount + (splayTree.compareCount > 1 ? " compares" : " compare"));
            System.out.println(splayTree.zigzigCount + (splayTree.zigzigCount > 1 ? " Zig-Zigs" : " Zig-Zig"));
            System.out.println(splayTree.zigzagCount + (splayTree.zigzagCount > 1 ? " Zig-Zags" : " Zig-Zag"));
        }

        if (stepToTraverse == -2)
            splayTree.postOrderTraverse(stepCount);
    }
}