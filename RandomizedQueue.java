import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // minimum capacity of resizing array
    private static final int INIT_CAPACITY = 8;

    // array of items to be iterated through
    private Item[] a;

    // variable to keep count of deque
    private int countDeque;

    // keep track of index of rear
    private int rear;


    // construct an empty randomized queue minimum capacity
    public RandomizedQueue() {
        countDeque = 0;
        rear = 0;
        a = (Item[]) new Object[INIT_CAPACITY];
    }

    // changes size of array into capacity
    private void resize(int capacity) {
        /* @citation Copied from: https://www.cs.princeton.edu/courses/archive/
        fall21/cos226/lectures/13StacksAndQueues.pdf Accessed 9/18/2021 */
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            copy[i] = a[i];
        }
        // prevent loitering
        a = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return countDeque == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return countDeque;
    }

    // add the item
    public void enqueue(Item item) {

        // test to see that it is valid argument
        if (item == null)
            throw new IllegalArgumentException("Cannot have null argument");

        // in case there is nothing, make rear less so that it cancels with
        // addition to rear so that it remains
        if (size() == 0) rear--;

        // double size if reach size of array
        if (rear == a.length - 1) resize(2 * a.length);

        // increment rear and add item, then increment count
        rear++;
        a[rear] = item;
        countDeque++;

    }


    // remove and return a random item
    public Item dequeue() {
        Item removed;
        // make sure there is something to remove
        if (isEmpty()) throw new NoSuchElementException("Nothing in queue");

        // if length is 0, the only value returned will be the last one
        // then null it to prevent loitering
        if (rear == 0) {
            removed = a[0];
            a[0] = null;
            countDeque--;
            return removed;
        }
        // get random value, remove that value, then place rear value into the
        // removed slot, then adjust size and count
        int randIndex = StdRandom.uniform(0, rear + 1);
        removed = a[randIndex];
        a[randIndex] = a[rear];
        a[rear] = null;
        rear--;
        countDeque--;
        if (countDeque == (a.length / 4) && a.length > 8) resize(a.length / 2);
        return removed;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        // make sure there is something to sample
        if (size() < 1)
            throw new java.util.NoSuchElementException("Queue is empty");

        // get random value and return value of the random index
        int randIndex = StdRandom.uniform(0, rear + 1);
        return a[randIndex];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new StraightArrayIterator();
    }

    // iterator that iterates over items in order from front to back
    private class StraightArrayIterator implements Iterator<Item> {

        // this is the index of the iteration
        private int i;

        // copy for a
        private Item[] b;

        // initialize instance variables
        public StraightArrayIterator() {
            i = 0;
            b = (Item[]) new Object[size()];

            // put all of a into b, so that there are no null values
            for (int j = 0; j < size(); j++) {
                b[j] = a[j];
            }

            // shuffle b using method
            StdRandom.shuffle(b);
        }

        // it has a next as long as it has an index within b
        public boolean hasNext() {
            return (i < b.length);
        }

        // while there is a next value, return the value of index, then increment
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No next element");
            return b[i++];
        }


    }

    // unit testing (required)
    public static void main(String[] args) {

        // testing all methods
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        StdOut.println("5x5 grid is formed using two independent iterations: ");
        int n = 5;
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        StdOut.println("Three random values are removed, 10 are sampled: ");
        for (int i = 0; i < 3; i++) StdOut.println(queue.dequeue());
        for (int i = 0; i < 10; i++) StdOut.print(queue.sample());
        StdOut.println();
        StdOut.println("Is it empty?: " + queue.isEmpty());
        StdOut.println("Size: " + queue.size());


    }

}
