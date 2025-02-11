import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author SHAHD BARGOUTHI
 * @version 1.0
 * @userid sbargouthi3
 * @GTID 903876889
 *
 * Collaborators:
 *
 * Resources:
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data inputted is null!");
        }
        for (T x : data) {
            if (x == null) {
                throw new IllegalArgumentException("Data inputted contains null data!");
            }
            add(x);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null smh");
        }
        root = helpMeAdd(data, root);
    }

    /**
     *  Helper method which recursively adds data to the tree.
     * @param data the data to add
     * @param curr cursor which allows traversal of tree recursively
     * @return returns node in the lower stack for pointer reinforcement
     */

    private AVLNode<T> helpMeAdd(T data, AVLNode<T> curr) {
        if (curr == null) { // get data?
            size += 1;
            AVLNode<T> toAdd = new AVLNode<T>(data);
            return toAdd;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helpMeAdd(data, curr.getLeft()));

        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helpMeAdd(data, curr.getRight()));
        }
        updater(curr);
        return balancer(curr);

    }

    /**
     *  Helper method which updates a nodes height and balance factor.
     * @param node to be updated
     */

    private void updater(AVLNode<T> node) {
        int rightHeight = -1;
        int leftHeight = -1;

        if (node.getRight() != null) {
            rightHeight = node.getRight().getHeight();
        }
        if (node.getLeft() != null) {
            leftHeight = node.getLeft().getHeight();
        }
        node.setHeight(Math.max(rightHeight, leftHeight) + 1);

        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     *  Helper method which balances a node through proper rotations.
     * @param node head os subtree to be balanced
     * @return updated subtree
     */

    private AVLNode<T> balancer(AVLNode<T> node) {
        if (node.getBalanceFactor() >= 2) { //left heavy
            if (node.getLeft().getBalanceFactor() == -1) {  ///
                node.setLeft(leftRotator(node.getLeft()));
            }
            return rightRotator(node);
        } else if (node.getBalanceFactor() <= -2) { // right heavy
            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotator(node.getRight()));
            }

            return leftRotator(node);
        } else {
            return node;
        }
    }

    /**
     *  Helper method which balances a node through left rotation.
     * @param node head of subtree to be balanced
     * @return updated subtree
     */

    private AVLNode<T> leftRotator(AVLNode<T> node) {  //right heavy
        AVLNode<T> ref = node.getRight();
        node.setRight(ref.getLeft());
        ref.setLeft(node);
        updater(node);
        updater(ref);
        return ref;
    }

    /**
     *  Helper method which balances a node through right rotation.
     * @param node head of subtree to be balanced
     * @return updated subtree
     */

    private AVLNode<T> rightRotator(AVLNode<T> node) {  //left heavy
        AVLNode<T> ref = node.getLeft();
        node.setLeft(ref.getRight());
        ref.setRight(node);
        updater(node);
        updater(ref);
        return ref;
    }





    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeH(data, root, dummy);
        return dummy.getData();
    }

    /**
     *  Helper method which recursively removes data from the tree.
     * @param data the data to be removed
     * @param curr cursor which allows traversal of tree recursively
     * @param dummy a node which stores removed data so it can be stored then returned
     * @return returns node in the lower stack for pointer reinforcement
     */


    private AVLNode<T> removeH(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null.");
        }
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree!");
        }
        if (data.compareTo(curr.getData()) == 0) {
            size -= 1;
            dummy.setData(curr.getData());
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setLeft(removePred(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
                updater(curr);

            }
        } else if (data.compareTo(curr.getData()) < 0) { ///data < curr.data
            curr.setLeft(removeH(data, curr.getLeft(), dummy));
            updater(curr);
        } else { //
            curr.setRight(removeH(data, curr.getRight(), dummy));
            updater(curr);
        }
        return balancer(curr);

    }

    /**
     *  Helper method which recursively removes node to be removed preddescir from the tree.
     * @param curr cursor which allows traversal of tree recursively
     * @param dummy2 a node which stores removed data so it can be stored then returned
     * @return returns node in the lower stack for pointer reinforcement
     */

    private AVLNode<T> removePred(AVLNode<T> curr, AVLNode<T> dummy2) {
        if (curr.getRight() == null) {
            dummy2.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePred(curr.getRight(), dummy2));
            return curr;
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        return getHelp(data, root);
    }

    /**
     *  Helper method which recursively retrieves data from the AVL tree.
     * @param curr cursor which allows traversal of AVL tree recursively
     * @param data data to be retrieved
     * @return returns data retrieved
     */

    private T getHelp(T data, AVLNode<T> curr) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null bro.");
        }
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree!");
        }
        if (data.compareTo(curr.getData()) == 0) {
            return (curr.getData());
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelp(data, curr.getLeft());
        } else {
            return getHelp(data, curr.getRight());
        }
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        return containsH(data, root);
    }

    /**
     *  Helper method which recursively checks if data is in AVL tree.
     * @param curr cursor which allows traversal of AVL tree recursively
     * @param data data to be compared against
     * @return returns confirmation of whether data in AVL tree or not
     */

    private boolean containsH(T data, AVLNode<T> curr) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null bro.");
        }
        if (curr == null) {
            return false;
        }
        if (data.compareTo(curr.getData()) == 0) {
            return true;
        } else if (data.compareTo(curr.getData()) < 0) {
            return containsH(data, curr.getLeft());
        } else {
            return containsH(data, curr.getRight());
        }

    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the deepest
     * ancestor of the node holding data that contains a value less than data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data inputted is null!");
        }
        return finder(null, root, data);


    }

    /**
     *  Helper method which recursively finds the predecessor
     * @param maxSmaller keeps track of the largest data smaller than data
     * @param data data to be compared against
     * @param curr cursor of current node to be examined
     * @return data of predecessor
     */


    private T finder(T maxSmaller, AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (data.compareTo(curr.getData()) == 0) {
            if (curr.getLeft() == null) {
                return maxSmaller;
            } else {
                return predHelp(curr.getLeft());
            }
        } else if (data.compareTo(curr.getData()) < 0) {
            return finder(maxSmaller, curr.getLeft(), data);
        } else {

            return finder(curr.getData(), curr.getRight(), data);
        }
    }

    /**
     *  Helper method which recursively find predecessor.
     * @param curr cursor which allows traversal of AVL tree recursively
     *
     * @return returns predecessor data
     */

    private T predHelp(AVLNode<T> curr) {
        if (curr.getRight() == null) {
            return curr.getData();
        } else {
            return predHelp(curr.getRight());
        }
    }


    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        return maxHelp(root);
    }


    /**
     *  Helper method which recursively finds deepest node in AVL.
     * @param curr cursor which allows traversal of AVL tree recursively
     * @return returns deepest node data
     */

    private T maxHelp(AVLNode<T> curr) {
        if (curr.getRight() == null && curr.getLeft() == null) {
            return curr.getData();
        } else if (curr.getRight() == null) {
            return maxHelp(curr.getLeft());
        } else if (curr.getLeft() == null) {
            return maxHelp(curr.getRight());
        } else {
            AVLNode<T> leftMAX =  curr.getLeft();
            AVLNode<T> rightMAX = curr.getRight();
            if (leftMAX.getHeight() > rightMAX.getHeight()) {
                return maxHelp(leftMAX);
            } else {
                return maxHelp(rightMAX);
            }

        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
