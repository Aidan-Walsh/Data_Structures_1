import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // node connected to last item
    private Node last;

    // node connected to first item
    private Node first;

    private class Node {

        // item object of a node
        private Item item;

        // next node to current
        private Node next;

        // previous node to one to allow for doubly linked list
        private Node previous;
    }

    // count of objects
    private int countDeque;

    // construct an empty deque with first and last pointing to null elements
    public Deque() {
        countDeque = 0;
        first = null;
        last = null;


    }

    // is the deque empty?
    public boolean isEmpty() {
        return countDeque == 0;
    }

    // return the number of items on the deque
    public int size() {
        return countDeque;
    }

    // add the item to the front
    public void addFirst(Item item) {
         /* @citation Adapted from: https://www.cs.princeton.edu/courses/
        archive/fall21/cos226/lectures/13StacksAndQueues.pdf accessed 9/18/2021 */

        // test to see if valid argument received
        if (item == null)
            throw new IllegalArgumentException("Cannot have null argument");

        // make new node that points to original first, to then point first
        // to the next node
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

        // make the last first point back to the first so that it is doubly linked
        // if it had a non-null node before
        if (oldFirst != null) oldFirst.previous = first;

        // increment count
        countDeque++;

        // if it is first element added, make last point to first
        if (countDeque == 1) last = first;


    }


    // add the item to the back
    public void addLast(Item item) {
        /* @citation Adapted from: https://www.cs.princeton.edu/courses/
        archive/fall21/cos226/lectures/13StacksAndQueues.pdf accessed 9/18/2021 */

        // check to see if valid argument received
        if (item == null)
            throw new IllegalArgumentException("Cannot have null argument");

        // make new node that points to original last and have last point to the
        // new one, then have last point previously to the previous last
        Node oldLast = last;
        last = new Node();
        last.item = item;

        // prevent loitering
        last.next = null;
        last.previous = oldLast;

        // make first point to last if this is the first thing added,
        // otherwise, doubly link the previous last and new last
        if (isEmpty()) first = last;
        else oldLast.next = last;

        // increment count
        countDeque++;


    }

    // remove and return the item from the front
    public Item removeFirst() {
    /* @citation Adapted from: https://www.cs.princeton.edu/courses/
        archive/fall21/cos226/lectures/13StacksAndQueues.pdf accessed 9/18/2021 */

        // make sure there is enough to remove
        if (size() < 1)
            throw new java.util.NoSuchElementException("Deque is empty");

        // put first into an object to be returned
        // if first and last point to each other (this is the last node),
        // be sure to null both of them so no loitering
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;

            // decrement count
            countDeque--;
            return item;
        }

        // first points to the next node and make sure previous is null
        first = first.next;
        first.previous = null;
        countDeque--;
        return item;


    }


    // remove and return the item from the back
    public Item removeLast() {
     /* @citation Adapted from: https://www.cs.princeton.edu/courses/
        archive/fall21/cos226/lectures/13StacksAndQueues.pdf accessed 9/18/2021 */
        
        // make sure size is large enough to remove
        if (size() < 1)
            throw new java.util.NoSuchElementException("Deque is empty");

        // put object into item to be removed
        // if it is last item, then null first and last to prevent loitering
        Item item;
        if (last == first) {
            item = last.item;
            first = null;
            last = null;
            countDeque--;
            return item;
        }

        // have last point to its previous node and make sure to null what it was
        // before to prevent loitering
        item = last.item;
        last = last.previous;
        last.next = null;
        countDeque--;
        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new StraightArrayIterator();
    }

    // iterator that iterates over items in order from front to back
    private class StraightArrayIterator implements Iterator<Item> {

        // this is the index of the iteration
        private Node current;

        // initialize instance variables
        public StraightArrayIterator() {
            current = first;
        }

        // it can progress as long as it is not currently null
        public boolean hasNext() {
            return current != null;
        }

        public Item next() {

            // make sure it has next node to iterate
            if (!hasNext()) throw new NoSuchElementException("No next element");
            Item x = current.item;

            // make it go to next for next node
            current = current.next;
            return x;
        }


    }

    public static void main(String[] args) {

        // test methods
        Deque<Integer> test = new Deque<>();
        StdOut.println(" 0 through 10 are added to front, last 5 are removed, "
                               + "0 through 5 are added to the end, then first "
                               + "5 are removed: ");
        for (int i = 0; i < 10; i++) test.addFirst(i);
        for (int i = 0; i < 5; i++) test.removeLast();
        for (int i = 0; i < 5; i++) test.addLast(i);
        for (int i = 0; i < 5; i++) test.removeFirst();
        for (int x : test) {
            StdOut.print(x);
        }
        StdOut.println();
        StdOut.println("Is it empty? : " + test.isEmpty());
        StdOut.println("Size: " + test.size());
        StdOut.println(test.removeLast());
        StdOut.println(test.removeFirst());


    }
}

