package cz.skalicky.javaplayground.parallelstreams.notthreadsafe;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ParallelStreamsMain2 {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        testThreadSafety(ThreadUnsafeMaxinator.class);
        testThreadSafety(ThreadSafeMaxinator.class);
    }

    @SuppressWarnings("unchecked")
    private static <T, U extends Maxinator<T>> void testThreadSafety(final Class<U> clazz)
            throws InstantiationException, IllegalAccessException {

        Maxinator<Double> maxinator = null;
        do {
            maxinator = (Maxinator<Double>) clazz.newInstance();
            maxinator.setQualityFunction(new DoubleQualityFunction());
            maxinator.updateBest(Arrays.asList(1.0, 5.0, 2.0, 3.0, 6.0, 4.0, 5.0, 4.0, 3.0, 7.0, 5.0, 2.0,
                    3.0, 6.0, 4.0, 5.0, 4.0, 3.0));
            System.out.print(maxinator.getBest() + ", ");

        } while (maxinator.getBest() == 7.0);

        System.out.println("\n-------------------- !!! NOT thread safe !!! --------------------");
    }

    private static interface Maxinator<T> {

        void updateBest(List<T> population);

        T getBest();

        void setQualityFunction(QualityFunction<T> qualityFunction);
    }

    static class ThreadUnsafeMaxinator<T> implements Maxinator<T> {

        private QualityFunction<T> qualityFunction;
        private T best = null;
        private Double bestQuality = Double.NEGATIVE_INFINITY;

        @Override
        public void updateBest(List<T> population) {
            population.parallelStream().forEach(i -> {
                double quality = qualityFunction.computeQuality(i);
                if (quality > bestQuality) {
                    best = i;
                    bestQuality = quality;
                }
            });
        }

        @Override
        public T getBest() {
            return best;
        }

        @Override
        public void setQualityFunction(QualityFunction<T> qualityFunction) {
            this.qualityFunction = qualityFunction;
        }

    }

    static class ThreadSafeMaxinator<T> implements Maxinator<T> {

        private QualityFunction<T> qualityFunction;
        private T best = null;

        @Override
        public void updateBest(List<T> population) {
            best = population.parallelStream().max(new MyComparator<T>(qualityFunction)).get();
        }

        @Override
        public T getBest() {
            return best;
        }

        @Override
        public void setQualityFunction(QualityFunction<T> qualityFunction) {
            this.qualityFunction = qualityFunction;
        }

        private static class MyComparator<R> implements Comparator<R> {
            private final QualityFunction<R> qualityFunction;

            public MyComparator(QualityFunction<R> qualityFunction) {
                this.qualityFunction = qualityFunction;
            }

            @Override
            public int compare(R o1, R o2) {

                return qualityFunction.computeQuality(o1).compareTo(qualityFunction.computeQuality(o2));
            }

        }

    }

    @FunctionalInterface
    private static interface QualityFunction<T> {
        abstract Double computeQuality(T individual);
    }

    private static class DoubleQualityFunction implements QualityFunction<Double> {

        @Override
        public Double computeQuality(Double individual) {
            return individual * 2;
        }

    }

}
