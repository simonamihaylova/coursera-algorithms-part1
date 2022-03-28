import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INITIAL_SIZE = 4;
    private int size = 0;
    private Item randomizedQueue[];
    private int head = -1;
    private int tail = -1;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randomizedQueue = (Item[]) new Object[INITIAL_SIZE];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == randomizedQueue.length) {
            resize(2 * randomizedQueue.length);
        }

        randomizedQueue[++tail] = item;

        if (tail == 0) {
            head = 0;
        }

        size++;

    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            copy[i] = randomizedQueue[(head + i) % randomizedQueue.length];
        }
        randomizedQueue = copy;
        head = 0;
        tail = size - 1;
    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = generateRandomValue();
        Item removed = randomizedQueue[index];

        randomizedQueue[index] = randomizedQueue[tail];
        randomizedQueue[tail] = null;

        this.tail -= 1;
        this.size -= 1;

        if (head == randomizedQueue.length) {
            head = 0;
        }

        if (size > 0 && size == randomizedQueue.length / 4) {
            resize(randomizedQueue.length / 2);
        }

        return removed;
    }

    private int generateRandomValue() {

        if (size == 0) {
            return 0;
        }

        return StdRandom.uniform(this.size);
    }

    // return a random item (but do not remove it)
    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = generateRandomValue();
        return randomizedQueue[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        return new RandomizedQueueIterator();

    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int index = 0;
        int maxIndex = 0;
        int firstIndex = 0;

        public RandomizedQueueIterator() {
            index = generateRandomValue();
            firstIndex = index;
            if (index < randomizedQueue.length) {
                maxIndex = size;
            }
        }

        @Override
        public boolean hasNext() {

            return index < maxIndex;
        }

        @Override
        public Item next() {

            if (index >= maxIndex) {
                throw new NoSuchElementException();
            }

            Item item = randomizedQueue[(index + head) % randomizedQueue.length];
            index++;

            if (index == size) {
                maxIndex = firstIndex;
                index = 0;
            }

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();

        Iterator<Integer> iE = randomizedQueue.iterator();
        while (iE.hasNext()) {
            System.out.println(iE.next());
        }

        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(9);
        randomizedQueue.enqueue(4);
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();

        Iterator<Integer> i = randomizedQueue.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }

    }

}