package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    /* Inner class, we implement Deque using a DOUBLY linked list */
    private class Node {
        private Item value;
        private Node prev;
        private Node next;

        private Node(Node prev, Item value, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    // First and last node, and size of LinkedList
    private Node first = null;
    private Node last = null;
    private int elements = 0;

    /* Construct an empty deque */
    public Deque() { }

    /* Is the deque empty? */
    public boolean isEmpty() {
        return elements == 0;
    }

    /* Return the number of items on the deque */
    public int size() {
        return elements;
    }

    /* Add the item to the front */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();

        // If list is empty, first and last nodes are the same
        if (isEmpty()) {
            first = new Node(null, item, null);
            last = first;   // autograder doesn't like inner assignments
        } else {
            Node oldFirst = first;
            Node newFirst = new Node(null, item, oldFirst);
            oldFirst.prev = newFirst;
            first = newFirst;
        }

        elements++;
    }

    /* Add the item to the end */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();

        // If list is empty, first and last nodes are the same
        if (isEmpty()) {
            first = new Node(null, item, null);
            last = first;   // autograder doesn't like inner assignments
        } else {
            Node oldLast = last;
            Node newLast = new Node(oldLast, item, null);
            oldLast.next = newLast;
            last = newLast;
        }

        elements++;
    }

    /* Remove and return the item from the front */
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        // Get value of Node we are removing
        Item item = first.value;

        if (size() == 1) {
            first = null;
            last = first;   // autograder doesn't like inner assignments
        } else {
            first = first.next;
            first.prev = null;
        }

        elements--;
        return item;
    }

    /* Remove and return the item from the end */
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        // Get value of Node we are removing
        Item item = last.value;

        if (size() == 1) {
            first = null;
            last = first;   // autograder doesn't like inner assignments
        } else {
            last = last.prev;
            last.next = null;
        }

        elements--;
        return item;
    }

    /* Return an iterator over items in order from front to end */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /* Here we create our iterator using an inner class */
    private class DequeIterator implements Iterator<Item> {
        private Node node;

        private DequeIterator() {
            // Set node to first node in the Deque
            this.node = Deque.this.first;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            // Set instance node to next node and return its value
            Item item = node.value;
            node = node.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
