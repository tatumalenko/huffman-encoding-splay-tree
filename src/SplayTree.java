import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.Set;

/**
 * A Splay Code extension of the Tree class. Implements the Splay tree algorithm
 * using a binary tree.
 * <p>
 * 
 * Uses a HashMap as a container collection to store the HuffmanNode objects for
 * fast constant time access. The hashing function is a trivial one based on the
 * conversion of the expected Character based key to ASCII integer.
 * <p>
 * 
 * The building of the tree is accomplished using a PriorityQueue that bases its
 * priority of the HuffmanNode objects based on the overriden Comparator
 * interface method `compare`.
 * 
 * @see Tree
 * @see HashMap
 * @see PriorityQueue
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

    private Set<Integer> set = new TreeSet<Integer>();

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
        else // data >= parent.data
            parent.right = node;

        // Splay the node and increment the size.
        splay(node);

    }

    /**
     * Removes the first node from the tree with the given data value.
     * 
     * @param data the data value
     */
    public void remove(int data) {
        remove(searchNode(data));
    }

    /**
     * Searches for a node with given data value and returns if it is found.
     * 
     * @param data the data value
     * @return whether node with data value was found
     */
    public boolean search(int data) {
        TreeNode node = searchNode(data);
        // System.out.println(n);
        return node != null;
        // return searchNode(data) != null;
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
     * comparisons, zigzig, zigzag operations at the provided number of operations.
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
     * @param node the node
     */
    private void remove(TreeNode node) {
        if (node == null)
            return;

        // System.out.println("\nROOT BEFORE: " + root);

        splay(node);
        boolean stopRule = node.parent != null;

        if (stopRule && node.parent.parent == null) {
            if (node == node.parent.left)
                zigRight(node, node.parent);
            else
                zigLeft(node, node.parent);
            stopRule = false;
        }

        // System.out.println("STOPRULE: " + stopRule + "\nROOT: " + root);

        if ((node.left != null) && (node.right != null)) {
            compareCount++;
            TreeNode minNode = node.left;

            while (minNode.right != null) {
                compareCount++;
                minNode = minNode.right;
            }

            minNode.right = node.right;
            node.right.parent = minNode;

            if (stopRule) {
                node.left.parent = root;
                root.left = node.left;

            } else {
                node.left.parent = null;
                root = node.left;
            }
        } else if (node.right != null) {
            compareCount++;

            if (stopRule) {
                node.right.parent = root;
                root.right = node.right;
            } else {
                node.right.parent = null;
                root = node.right;
            }
        } else if (node.left != null) {
            compareCount++;

            if (stopRule) {
                node.left.parent = root;
                root.left = node.left;

            } else {
                node.left.parent = null;
                root = node.left;
            }
        } else {
            if (stopRule) {
                if (root.left == node)
                    root.left = null;
                else
                    root.right = null;
            } else {
                root = null;
            }
        }

        size--;
        // System.out.println("ROOT AFTER: " + root);
    }

    /**
     * Searches for a node with given data value and returns it (or null).
     * 
     * @param data the data value
     */
    private TreeNode searchNode(int data) {
        TreeNode n = root;

        // System.out.println("ROOT: " + (root.data));
        // System.out.println("DATA: " + data);

        // if (data == n.data)
        // return n;

        while (n != null) {
            // System.out.println("DATA: " + n.data);
            // System.out.println("LEFT: " + n.left);
            // System.out.println("RIGHT: " + n.right);
            compareCount++;
            compareCount++;
            if (data < n.data) {
                n = n.left;
            } else if (data > n.data) {
                n = n.right;
            } else {
                splay(n);
                return n;
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
            // compareCount++;
            compareCount++;
            if (parent == parent.parent.left) // P is a left child of G
                parent.parent.left = child;
            else // P is a right child of G
                parent.parent.right = child;
        }

        if (child.right != null) {
            // compareCount++;
            child.right.parent = parent;
        }

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
            // compareCount++;
            if (parent == parent.parent.left) {
                parent.parent.left = child;
            } else
                parent.parent.right = child;
        }
        if (child.left != null) {
            child.left.parent = parent;
        }

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
                splayTree.set.add(Integer.parseInt(operation.substring(1)));
                splayTree.insert(Integer.parseInt(operation.substring(1)));
                break;
            case 'r':
                splayTree.set.remove(Integer.parseInt(operation.substring(1)));
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

        System.out.println("Size: " + splayTree.size());
        // System.out.println(splayTree.set);
        // System.out.println(Arrays.toString());
    }

    private int[] toArray(TreeNode root) {
        return new int[0];
    }
}