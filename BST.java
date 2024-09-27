import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import static java.lang.Math.max;

/**
 * Your implementation of a BST.
 *
 * @author SHAHD BARGOUTHI
 * @version 1.0
 * @userid sbargouthi3
 * @GTID 903876889
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR! size will be auto 0 and root will be auto null!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("input is null and invalid!");
        }
        for (T x : data) {
            if (x == null) {
                throw new IllegalArgumentException("Input contains null data! Try again^^");
            }
            add(x);
        }

    }


    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
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
    private BSTNode<T> helpMeAdd(T data, BSTNode<T> curr) {
        if (curr == null) { // get data?
            size += 1;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helpMeAdd(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helpMeAdd(data, curr.getRight()));
        } else if (data.compareTo(curr.getData()) == 0) {
            return curr;
        }
        return curr;

    }



    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        BSTNode<T> dummy = new BSTNode<>(null);
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

    private BSTNode<T> removeH(T data, BSTNode<T> curr, BSTNode<T> dummy) {
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
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());

            }
        } else if (data.compareTo(curr.getData()) < 0) { ///data < curr.data
            curr.setLeft(removeH(data, curr.getLeft(), dummy));
        } else { //
            curr.setRight(removeH(data, curr.getRight(), dummy));
        }
        return curr;



    }

    /**
     *  Helper method which recursively removes node to be removed successor from the tree.
     * @param curr cursor which allows traversal of tree recursively
     * @param dummy2 a node which stores removed data so it can be stored then returned
     * @return returns node in the lower stack for pointer reinforcement
     */

    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy2) {
        if (curr.getLeft() == null) {
            dummy2.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy2));
            return curr;
        }
    }






    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        return helpMeGet(data, root);
    }

    /**
     *  Helper method which recursively retrieves data from the tree.
     * @param curr cursor which allows traversal of tree recursively
     * @param data data to be retrieved
     * @return returns data retrieved
     */
    private T helpMeGet(T data, BSTNode<T> curr) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null bro.");
        }
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree!");
        }
        if (data.compareTo(curr.getData()) == 0) {
            return (curr.getData());
        } else if (data.compareTo(curr.getData()) < 0) {
            return helpMeGet(data, curr.getLeft());
        } else {
            return helpMeGet(data, curr.getRight());
        }

    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        return containsH(data, root);
    }

    /**
     *  Helper method which recursively checks if data is in tree.
     * @param curr cursor which allows traversal of tree recursively
     * @param data data to be compared against
     * @return returns confirmation of whether data in tree or not
     */

    private boolean containsH(T data, BSTNode<T> curr) {
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
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsH(data, curr.getRight());
        }
        return false;
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> wee = new ArrayList<T>();
        preorderH(root, wee);
        return wee;
    }


    /**
     *  Helper method which generates a tree preorder traversal
     * @param curr cursor which allows traversal of tree recursively
     * @param wee  array the traversal is  added in
     */
    private void preorderH(BSTNode<T> curr, ArrayList<T> wee) {
        if (curr == null) {
            return;
        }
        wee.add(curr.getData());
        preorderH(curr.getLeft(), wee);
        preorderH(curr.getRight(), wee);
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> wee = new ArrayList<T>();
        inorderH(root, wee);
        return wee;
    }

    /**
     *  Helper method which generates a tree inorder traversal
     * @param curr cursor which allows traversal of tree recursively
     * @param wee  array the traversal is added in
     */


    private void inorderH(BSTNode<T> curr, ArrayList<T> wee) {
        if (curr == null) {
            return;
        }

        inorderH(curr.getLeft(), wee);
        wee.add(curr.getData());
        inorderH(curr.getRight(), wee);
    }



    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> wee = new ArrayList<T>();
        postorderH(root, wee);
        return wee;
    }

    /**
     *  Helper method which generates a tree postorder traversal
     * @param curr cursor which allows traversal of tree recursively
     * @param wee  array the traversal is added in
     */

    private void postorderH(BSTNode<T> curr, ArrayList<T> wee) {
        if (curr == null) {
            return;
        }

        postorderH(curr.getLeft(), wee);
        postorderH(curr.getRight(), wee);
        wee.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        LinkedList<T> list = new LinkedList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        if (root == null) {
            return list;
        }

        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.remove();
            list.add(curr.getData());
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add((curr.getRight()));
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightFinder(root);
    }

    /**
     *  Helper method which finds height of tree
     * @param curr cursor which allows traversal of tree recursively
     * @return returns integer height below each current node
     */

    private int heightFinder(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        if (curr.getRight() == null && curr.getLeft() == null) {
            return 0;
        } else {
            return (max(heightFinder(curr.getLeft()), heightFinder(curr.getRight())) + 1);
        }
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data to be found is nulL!");
        }
        if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("Either one or both of input data were not found");
        }
        LinkedList<T> path = new LinkedList<>();
        if (data1.equals(data2)) {
            path.add(root.getData());
            return path;
        }
        BSTNode<T> dca = dcaFinder(data1, data2, root);

        pathFinder(data1, dca, path, true);
        path.removeLast();
        pathFinder(data2, dca, path, false);

        return path;



    }

    /**
     *  Helper method which locates deepest common ancestor
     * @param curr cursor which allows traversal of tree recursively
     * @param data1 first data
     * @param data2  second data
     * @return returns node in the lower stack for pointer reinforcement
     */

    private BSTNode<T> dcaFinder(T data1, T data2, BSTNode<T> curr) {
        if (data1.equals(curr.getData()) || data2.equals(curr.getData())) {
            return curr;
        }

        if (data1.compareTo(curr.getData()) > 0 && data2.compareTo(curr.getData()) > 0) {
            if (curr.getRight() == null) {
                return curr;
            }
            return dcaFinder(data1, data2, curr.getRight());
        } else if (data1.compareTo(curr.getData()) < 0 && data2.compareTo(curr.getData()) < 0) {
            if (curr.getLeft() == null) {
                return curr;
            }
            return dcaFinder(data1, data2, curr.getLeft());
        } else {
            return curr;
        }


    }

    /**
     *  Helper method which finds path between data and a node
     * @param dca cursor which allows traversal of tree recursively and the maximum to be traversed under
     * @param data data to be located
     * @param orient boolean representing whether data to be added to end or front of list
     * @param path path from data to dca added here!
     */

    private void pathFinder(T data, BSTNode<T> dca, LinkedList<T> path, boolean orient) { // dca will be our cursor
        if (dca == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (orient) {
            path.addFirst(dca.getData());


        } else if (!orient) {
            path.addLast(dca.getData());

        }
        if (data.compareTo(dca.getData()) < 0) {
            pathFinder(data, dca.getLeft(), path, orient);
        } else if (data.compareTo(dca.getData()) > 0) {
            pathFinder(data, dca.getRight(), path, orient);
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
    public BSTNode<T> getRoot() {
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
