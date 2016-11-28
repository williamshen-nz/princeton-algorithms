package Queues;

import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException();

        // Declare and initialise variables
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // Add the input into the Randomized Queue
        while (!StdIn.isEmpty())
            queue.enqueue(StdIn.readString());

        // Randomly take things out of the Randomized Queue
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
