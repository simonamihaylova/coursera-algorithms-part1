import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String args[]) {

        int number = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomizedQueueOfStrings = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            randomizedQueueOfStrings.enqueue(StdIn.readString());
        }

        Iterator<String> iterator = randomizedQueueOfStrings.iterator();
        int index = 0;
        while (iterator.hasNext() && index < number) {
            System.out.println(iterator.next());
            index++;
        }
    }
}
