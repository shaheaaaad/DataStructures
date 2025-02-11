import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Shahd Bargouthi
 * @version 1.0
 * @userid sbargouthi3
 * @GTID 903876889
 *
 * Collaborators:
 *
 * Resources:
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input is nulL!");
        }
        this.size = data.size();
        this.backingArray = (T[]) new Comparable[2 * size + 1];


        int k = 0;
        for (T erm : data) {
            if (erm == null) {
                throw new IllegalArgumentException("Input contains null element.");
            }
            backingArray[1 + k] = erm;

            k++;
        }
        if (size == 1) {
            return;
        }
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }




    }









    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null!");
        }
        if (backingArray.length - 1 == size) {
            T[] replace = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i <= size; i++) {
                replace[i] = backingArray[i];
            }
            backingArray = replace;
        }
        backingArray[size + 1] = data;
        size++;
        if (size == 1) {
            return;
        }
        int z = size;
        while (backingArray[z].compareTo(backingArray[z / 2]) < 0) {
            T temp = backingArray[z];
            backingArray[z] = backingArray[z / 2];
            backingArray[z / 2] = temp;
            z = z / 2;
            if (z / 2 == 0) {
                break;
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty!");
        }
        if (size == 1) {
            size--;
            T remmie = backingArray[1];
            backingArray[1] = null;
            return remmie;
        }
        T remmie = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return remmie;

    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }




    /**
     * downheaps from given index downwards
     *
     *
     * @param i index at which downheaping begins.
     */
    private void downHeap(int i) {
        if (2 * i + 1 <= size && 2 * i <= size) {

            T leftChild = backingArray[2 * i];
            T rightChild = backingArray[2 * i + 1];
            if (rightChild.compareTo(leftChild) > 0) {
                if (backingArray[i].compareTo(leftChild) > 0) {
                    backingArray[2 * i] = backingArray[i];
                    backingArray[i] = leftChild;
                    downHeap(2 * i);
                }


            } else {
                if (backingArray[i].compareTo(rightChild) > 0) {
                    backingArray[2 * i + 1] = backingArray[i];
                    backingArray[i] = rightChild;
                    downHeap(2 * i + 1);
                }

            }
            return;
        }

        if (2 * i <= size) {
            T leftChild = backingArray[2 * i];
            if (backingArray[i].compareTo(leftChild) > 0) {
                backingArray[2 * i] = backingArray[i];
                backingArray[i] = leftChild;
                downHeap(2 * i);
            }

        }
    }












}
