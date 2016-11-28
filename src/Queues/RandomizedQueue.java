package Queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    /* We implement our own 'ArrayList' (well sort of...) */
    private static final int INITIAL_SIZE = 2;
    private static final double GROWTH_FACTOR = 2;
    private static final double SHRINK_FACTOR = 0.5;

    // Yucky Java generics
    private Item[] values = (Item[]) new Object[INITIAL_SIZE];
    private int elements = 0;

    /* Construct an empty randomized queue */
    public RandomizedQueue() { }

    /* Is the queue empty? */
    public boolean isEmpty() {
        return elements == 0;
    }

    /* Return the number of items on the queue */
    public int size() {
        return elements;
    }

    /* Resize the array up or down according to passed parameter */
    private void resize(int newSize) {
        Item[] resized = (Item[]) new Object[newSize];

        for (int i = 0; i < elements; i++)
            resized[i] = values[i];

        values = resized;
    }

    /* Add the item */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();

        // Check if whole array has been filled up, and act accordingly
        if (values.length == elements)
            resize((int) (elements * GROWTH_FACTOR));

        // Add element into values array
        values[elements++] = item;
    }

    /* Remove and return a random item */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        // Get random index [0, elements) and remove from array
        int randomIndex = StdRandom.uniform(0, elements);
        Item removed = values[randomIndex];

        // Move last added item to fill the gap
        if (randomIndex != elements - 1)
            values[randomIndex] = values[elements - 1];

        values[elements - 1] = null;
        elements--;

        // Resize the array if necessary
        if (elements < values.length / 4)
            resize(values.length / 2);

        return removed;
    }

    /* Return (but do not remove) a random item */
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        // Get random index [0, elements) and remove from array
        int randomIndex = StdRandom.uniform(0, elements);
        return values[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int currentIndex;
        private int[] randomIndexes;

        private RandomizedQueueIterator() {
            currentIndex = 0;

            // Initialise the random index array and shuffle
            randomIndexes = new int[elements];
            for (int i = 0; i < elements; i++) {
                randomIndexes[i] = i;
            }
            StdRandom.shuffle(randomIndexes);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < elements;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return values[randomIndexes[currentIndex++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}