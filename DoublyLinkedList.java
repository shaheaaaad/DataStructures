import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {

        if (data == null) {
            throw new IllegalArgumentException("You cannot create a node with null input data!");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index you are attempting to add to is out of the DLL's bounds!");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> curr = null;
            if (index < size / 2) {
                curr = getHead();
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }


            } else if (index >= size / 2) {
                curr = getTail();
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }

            }
            DoublyLinkedListNode<T> insert = new DoublyLinkedListNode<T>(data, curr.getPrevious(), curr);
            curr.getPrevious().setNext(insert);
            curr.setPrevious(insert);

            size += 1;



        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot create a node with null input data!");
        }

        if (size == 0) {
            DoublyLinkedListNode<T> new0 = new DoublyLinkedListNode<T>(data);
            head = new0;
            tail = new0;
            size += 1;
            return;

        }


        DoublyLinkedListNode<T> newHead = new DoublyLinkedListNode<T>(data, null, head);
        head.setPrevious(newHead);
        head = newHead;

        size += 1;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot create a node with null input data!");
        }
        if (size == 0) {
            DoublyLinkedListNode<T> new0 = new DoublyLinkedListNode<T>(data);
            head = new0;
            tail = new0;
            size += 1;
            return;

        }


        DoublyLinkedListNode<T> newTail = new DoublyLinkedListNode<T>(data, tail, null);
        tail.setNext(newTail);
        tail = newTail;

        size += 1;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The given indices are invalid and cannot be accessed since they are "
                    + "outside the DLL's bounds.");
        }
        if (size == 1) {
            T rem = tail.getData();
            tail = null;
            head = null;
            size -= 1;
            return rem;
        }
        if (index == 0) {
            return removeFromFront();
        }
        if (index == size - 1) {
            return removeFromBack();
        } else {
            DoublyLinkedListNode<T> curr = null;
            if (index < size / 2) {
                curr = getHead();
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }


            } else if (index >= size / 2) {
                curr = getTail();
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }

            }
            DoublyLinkedListNode<T> rem = curr;

            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());

            size -= 1;
            return curr.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove from an empty list!");
        }
        if (size == 1) {
            T temp = head.getData();
            head = null;
            tail = null;
            size -= 1;
            return temp;
        }
        DoublyLinkedListNode<T> temp = head;
        head = head.getNext();
        head.setPrevious(null);

        size -= 1;
        return temp.getData();
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove from an empty list!");
        }

        if (size == 1) {
            DoublyLinkedListNode<T> temp = head;
            head = null;
            tail = null;
            size -= 1;
            return temp.getData();
        }


        DoublyLinkedListNode<T> temp = tail;
        tail = tail.getPrevious();
        tail.setNext(null);

        size -= 1;
        return temp.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index provided cannot be accessed since out of the DLL's bounds!");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        }
        DoublyLinkedListNode<T> curr = null;
        if (index < size / 2) {
            curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }


        } else if (index >= size / 2) {
            curr = getTail();
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();

            }
        }
        return curr.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null and is an invalid input");
        }
        if (size == 0) {
            throw new NoSuchElementException("List is empty and doesnt contain input data.");
        }
        DoublyLinkedListNode<T> curr = tail;
        while (curr != null) {
            if (curr.getData().equals(data)) {
                if (curr == head) {
                    return removeFromFront();
                } else if (curr == tail) {
                    return removeFromBack();
                } else {
                    curr.getPrevious().setNext(curr.getNext());
                    curr.getNext().setPrevious(curr.getPrevious());
                    size -= 1;
                    return curr.getData();
                }

            }
            curr = curr.getPrevious();
        }

        throw new NoSuchElementException("the data to be removed is not present in the Doubly-Linked List!");

    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        if (size == 1) {
            return new Object[] {head.getData()};
        }
        DoublyLinkedListNode<T> curr = head;
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
