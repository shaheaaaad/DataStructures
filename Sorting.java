import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * Your implementations must match what was taught in lecture and 
 * recitation to receive credit. Implementing a different sort or 
 * a different implementation for a sort will receive no credit even
 * if it passes comparison checks.
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
public class Sorting {

    /**
     * Implement insertion sort. You should start sorting 
     * from the beginning of the array.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null!");
        }
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null!");
        }
        int startInd = 0;
        int endInd = arr.length - 1;
        int swapped;

        while (startInd < endInd) {
            ///front direction
            swapped = startInd;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapped = i;
                }
            }
            endInd = swapped;
            for (int i = endInd; i > startInd; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    swap(arr, i, i - 1);
                    swapped = i;
                }
            }
            startInd = swapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null!");
        }
        arr = mergeSortHelper(arr, comparator);
    }

    /**
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     * @return returns the  sorted subarray.
     **/

    private static <T> T[] mergeSortHelper(T[] arr, Comparator<T> comparator) {
        if (arr.length == 1) {
            return arr;
        }
        ///we first partition and call the recursive helper on each partition
        int end = arr.length - 1;
        int midIndex = end / 2;
        T[] left = partition(arr, 0, midIndex);
        T[] right = partition(arr, midIndex + 1, end);
        right = mergeSortHelper(right, comparator);
        left = mergeSortHelper(left, comparator);

        /// next we  remerge left and right;
        int i = 0;
        int j = 0;
        int k = 0; /// third pointer

        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[k] = left[i];
                i++;
                k++;
            } else {
                arr[k] = right[j];
                j++;
                k++;
            }
        }
        while (i < left.length) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            arr[k] = right[j];
            j++;
            k++;
        }

        return arr;

    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class
     * (see Canvas Modules for reference).
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null!");
        }
        arr = quickSortHelper(arr, 0, arr.length - 1, rand, comparator);



    }
    /**
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param start      this is the start index of the current partition to be sorted
     * @param end        this is the end index of the current partition to be sorted (inclusive)
     * @return           returns the array after sorting is over.
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */

    private static <T> T[] quickSortHelper(T[] arr, int start, int end, Random rand, Comparator<T> comparator) {
        if (end - start < 1) {
            return arr;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivVal = arr[pivotIndex];
        swap(arr, start, pivotIndex);
        int i = start + 1;
        int j = end;

        while (j > i - 1)  {
            while (j > i - 1 && comparator.compare(arr[i], pivVal) <= 0) {
                i++;
            }
            while (j > i  - 1 && comparator.compare(arr[j], pivVal) >= 0) {
                j--;
            }
            if (j > i - 1) {
                swap(arr, i, j);
                i++;
                j--;
            }

        }
        swap(arr, start, j);
        arr = quickSortHelper(arr, start, j - 1, rand, comparator);
        arr = quickSortHelper(arr, j + 1, end, rand, comparator);
        return arr;
    }


    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class
     * (see Canvas Modules for reference).
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * Note: Be very careful about how Integer.MIN_VALUE is handled, especially
     * when using Math.abs().
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null!");
        }
        LinkedList<Integer>[] bucketsArray = new LinkedList[19];


        int maxDig = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                maxDig = Integer.MAX_VALUE;
                break;
            }
            if (Math.abs(arr[i]) > maxDig) {
                maxDig = arr[i];
            }

        }
        int k = 0;
        while (maxDig != 0) {
            maxDig /= 10;
            k++;
        }


        int divver = 1;
        for (int z = 0; z < k; z++) {

            for (int i = 0; i < arr.length; i++) {
                int toAdd = (arr[i] / divver) % 10 + 9;
                if (bucketsArray[toAdd] == null) {
                    bucketsArray[toAdd] = new LinkedList<Integer>();
                }
                bucketsArray[toAdd].add(arr[i]);

            }
            divver *= 10;
            int idx = 0;

            for (int p = 0; p < 19; p++) {
                while (!(bucketsArray[p] == null) && !bucketsArray[p].isEmpty()) {
                    arr[idx++] = bucketsArray[p].remove();
                }
            }
        }



    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        int capa = data.size();
        int[] ret = new int[capa];
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);

        for (int i = 0; i < capa; i++) {
            ret[i] = heap.remove();
        }

        return ret;
    }

    /**
     *
     * @param arr the array containing elements to be swapped
     * @param i first index to be swapped
     * @param j second index to be swapped
     * @param <T> The generic type of the objects to be swapped
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     *
     * @param arr the array to be partitioned
     * @param start beginning of partition to be created
     * @param end end  of partition to be created
     * @param <T> The generic type of the objects in the partitions
     * @return the partition of the input array from start to end index inclusive.
     */
    private static <T> T[] partition(T[] arr, int start, int end) {
        T[] arr2 = (T[]) new Object[end - start + 1];
        int x = start;
        for (int i = 0; i < end - start + 1; i++) {
            arr2[i] = arr[x];
            x++;
        }

        //  make sure for loop params not being updated
        return arr2;

    }


}
