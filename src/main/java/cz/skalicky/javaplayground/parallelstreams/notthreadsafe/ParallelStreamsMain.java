package cz.skalicky.javaplayground.parallelstreams.notthreadsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelStreamsMain {

    private static int i1 = 0;
    private static AtomicInteger i2 = new AtomicInteger(0);

    public static void main(String[] args) {
        IntStream.range(0, 1000).parallel().forEach(value -> ++i1);
        System.out.println(i1);

        IntStream.range(0, 1000).parallel().forEach(value -> i2.incrementAndGet());
        System.out.println(i2);
    }
}
