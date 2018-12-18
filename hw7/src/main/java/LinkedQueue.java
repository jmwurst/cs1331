import java.util.Iterator;
import java.util.NoSuchElementException;
import javafx.collections.ModifiableObservableListBase;

/**
 * An implementation of a queue with linked elements. This is used to keep
 * track of students in the office hour queue.
 *
 * @author Justin Wurst
 * @version 11-27-18
 * @param <E> The type of object stored in the LinkedQueue.
 */
public class LinkedQueue<E> extends ModifiableObservableListBase<E>
                            implements Iterable<E>, SimpleQueue<E> {
    /**
     * The private inner class used by the iterator() method which represents
     * an iterator for a LinkedQueue.
     *
     * @author Justin Wurst
     * @version 11-27-18
     */
    private class LinkedQueueIterator implements Iterator<E> {
        private int cursor = 0;

        /**
         * Determines if there is another element for the iterator to reach
         * in the queue.
         *
         * @return a boolean representing if there is another element in the
         * queue
         */
        public boolean hasNext() {
            return cursor < size();
        }

        /**
         * Returns the next element in the queue, if one exists.
         *
         * @return the next element in the queue
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(cursor++);
        }
    }

    private LinkedQueueNode<E> head;

    /**
     * Initializes an instance of LinkedQueue given an element to place at the
     * head of the queue.
     *
     * @param element The element to place at the head of the queue.
     */
    public LinkedQueue(E element) {
        head = new LinkedQueueNode<>(element, null);
    }

    /**
     * Initializes an empty instance of LinkedQueue.
     */
    public LinkedQueue() {
        head = null;
    }

    /**
     * Add an element to the back of the queue.
     *
     * @param element The element to add to the queue.
     */
    public void enqueue(E element) {
        super.add(element);
    }

    /**
     * Add an element to the queue at a specific index.
     *
     * @param index The index to add the element at.
     * @param element The element to add to the queue.
     */
    public void doAdd(int index, E element) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head = new LinkedQueueNode<>(element, head);
            return;
        }
        if (index == this.size()) {
            LinkedQueueNode<E> lastNode = head;
            while (lastNode.getNext() != null) {
                lastNode = lastNode.getNext();
            }
            LinkedQueueNode<E> newNode = new LinkedQueueNode<>(element, null);
            lastNode.setNext(newNode);
            return;
        }

        LinkedQueueNode<E> curNode = head;
        while (index > 1) {
            curNode = curNode.getNext();
            index--;
        }
        LinkedQueueNode<E> newNode = new LinkedQueueNode<>(element,
                                                curNode.getNext());
        curNode.setNext(newNode);
    }

    /**
     * Remove an element from the front of the queue.
     *
     * @return the element removed
     */
    public E dequeue() {
        if (this.size() == 0) {
            return null;
        }
        return super.remove(0);
    }

    /**
     * Remove an element at a specific index from the queue.
     *
     * @param index The index of the element to remove.
     * @return the element removed from the queue
     */
    public E doRemove(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            E temp = head.getData();
            head = head.getNext();
            return temp;
        }
        int indexC = index;
        LinkedQueueNode<E> curNode = head;
        while (indexC > 1) {
            curNode = curNode.getNext();
            indexC--;
        }
        E temp = curNode.getNext().getData();
        if (index == this.size() - 1) {
            curNode.setNext(null);
        } else {
            curNode.setNext(curNode.getNext().getNext());
        }
        return temp;
    }

    /**
     * Retrieve an element at a certain index in the queue.
     *
     * @param index The index of the element to retrieve.
     * @return the element at the given index
     */
    public E get(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        LinkedQueueNode<E> curNode = head;
        while (index > 0) {
            curNode = curNode.getNext();
            index--;
        }
        return curNode.getData();
    }

    /**
     * A method which throws an UnsupportedOperationException. It really do
     * be like that sometimes.
     *
     * @param index An index in the queue.
     * @param element An element.
     * @return an element, but nothing is returned due to the throw
     */
    public E doSet(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates an iterable object which points to elements in the queue.
     *
     * @return an instance of LinkedQueueIterator
     */
    public Iterator<E> iterator() {
        return new LinkedQueueIterator();
    }

    /**
     * Get the size of the queue.
     *
     * @return an integer representing the size of the queue
     */
    public int size() {
        int size = 0;
        LinkedQueueNode<E> curNode = head;
        while (curNode != null) {
            size++;
            curNode = curNode.getNext();
        }
        return size;
    }

    /**
     * Empty the queue.
     */
    public void clear() {
        head = null;
    }

    /**
     * Check if the queue is empty.
     *
     * @return a boolean representing if the queue is empty
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }
}
