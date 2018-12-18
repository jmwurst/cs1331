/**
 * A representation of a list, a series of elements with variable length.
 * It implements the List interface provided.
 *
 * @author Justin Wurst
 * @version 10-31-18
 * @param <E> The generic type to be later defined.
 */
public class MyList<E> implements List<E> {
    private E[] elements;
    private int size;

    /**
     * Initializes an empty MyList with the default capacity of 10.
     */
    public MyList() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Initializes an empty MyList with the given capacity.
     *
     * @param capacity The initial capacity of the backing array.
     */
    public MyList(int capacity) {
        elements = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Insert element at the back of the list. If the list is
     * at capacity, double its capacity.
     *
     * @param e The element to be added to the list.
     * @throws IllegalArgumentException if a null value is passed to the
     *         method.
     */
    public void add(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        if (size == elements.length) {
            E[] temp = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                temp[i] = elements[i];
            }
            elements = temp;
        }
        elements[size] = e;
        size++;
    }

    /**
     * Get an element at a specified index of the list.
     *
     * @param index The index of the element to get.
     * @return E is returning an element of type E for the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    /**
     * Replace all instances of e with replaceWith.
     *
     * @param e The element to be replaced in the list.
     * @param replaceWith the element to replace e in the list
     * @throws IllegalArgumentException if a null value is passed to the
     *         method for either of the arguments.
     */
    public void replace(E e, E replaceWith) {
        if (e == null || replaceWith == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            if (e.equals(elements[i])) {
                elements[i] = replaceWith;
            }
        }
    }

    /**
     * Removes all instances of element e in the list and returns a count
     * of how many items are removed.
     *
     * @param e The element to be removed from the list.
     * @return int representing the count
     * @throws IllegalArgumentException if a null value is passed to the
     *         method.
     */
    public int remove(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        E[] temp = (E[]) new Object[elements.length];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (e.equals(elements[i])) {
                count++;
            } else {
                temp[i - count] = elements[i];
            }
        }
        elements = temp;
        size -= count;
        return count;
    }

    /**
     * Counts the number of times element e occurs in the list.
     *
     * @param e The element to be counted in the list.
     * @return int representation of the count
     * @throws IllegalArgumentException if a null element is passed to the
     *         method.
     */
    public int contains(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (e.equals(elements[i])) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns if the list is empty (size is 0) or not.
     *
     * @return boolean identifies whether list is empty or not
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears all elements from the list.
     */
    public void clear() {
        elements = (E[]) new Object[elements.length];
        size = 0;
    }

    /**
     * Counts the number of elements in the list.
     *
     * @return int representing the number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an array containing as many of the non-null elements
     * in the list as it can fit. If the passed-in array is longer than
     * the list, the extra elements are set to null.
     *
     * @param e The array to store all of the non-null elements in.
     * @return E[] an array that contains the non-null elements
     * @throws IllegalArgumentException if a null value is passed to the
     *         method.
     */
    public E[] toArray(E[] e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < e.length; i++) {
            if (i < size) {
                e[i] = elements[i];
            } else {
                e[i] = null;
            }
        }
        return e;
    }
}