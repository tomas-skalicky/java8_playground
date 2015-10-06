package cz.skalicky.javaplayground.parallelstreams.notthreadsafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ThreadPoolIssue {

    public static void main(String[] args) throws InterruptedException {

        new ThreadPoolIssue().run();
    }

    private static final int MAX = 1000;

    private void run() throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        // Simulating multiple threads in the system
        // if one of them is executing a long-running task.
        // Some of the other threads/tasks are waiting
        // for it to finish

        es.execute(() -> countPrimes(MAX, 1000)); // incorrect task
        es.execute(() -> countPrimes(MAX, 0));
        es.execute(() -> countPrimes(MAX, 0));
        es.execute(() -> countPrimes(MAX, 0));
        es.execute(() -> countPrimes(MAX, 0));
        es.execute(() -> countPrimes(MAX, 0));

        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
    }

    private void countPrimes(int max, int delay) {
        // @formatter:off
        System.out.println(IntStream.range(1, max).parallel().filter(this::isPrime).peek(i -> sleep(delay))
                .count());
        // @formatter:on
    }

    private boolean isPrime(long n) {
        return n > 1 && LongStream.rangeClosed(2, (long) Math.sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }

    private void sleep(final int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
