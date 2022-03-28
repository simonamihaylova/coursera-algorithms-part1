import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;

        if (first != null) {
            first.previous = newFirst;
        }

        first = newFirst;

        if (last == null) {
            last = first;
        }
        size++;

    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newLast = new Node();
        newLast.item = item;
        newLast.previous = last;

        if (last != null) {
            last.next = newLast;
        }

        last = newLast;

        if (first == null) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldFirst = first;
        first = first.next;

        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }

        size--;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldLast = last;
        last = last.previous;

        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }

        size--;

        return oldLast.item;
    }

    // return an iterator over items in order from front to back public
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {

            if (current == null) {
                throw new NoSuchElementException();
            }

            Item data = current.item;
            current = current.next;
            return data;
        }

        public void remove() {

            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque.isEmpty());

        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.removeFirst();
        deque.removeLast();

        Iterator<Integer> it = deque.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }

}
