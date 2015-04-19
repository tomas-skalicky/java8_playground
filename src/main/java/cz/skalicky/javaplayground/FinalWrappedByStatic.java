package cz.skalicky.javaplayground;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class FinalWrappedByStatic {

    private static class WrappedFinal {

        @SuppressWarnings("unused")
        private final String value;

        private WrappedFinal(final String value) {
            this.value = value;
        }

    }

    private static final class StaticWrapper {
        private StaticWrapper() {
        }

        private static Map<String, WrappedFinal> maps = new HashMap<>();

        private static WrappedFinal getWrapped(final String value) {
            if (!maps.containsKey(value)) {
                maps.put(value, new WrappedFinal(value));
            }
            return maps.get(value);
        }
    }

    public static void main(String[] args) {

        final WrappedFinal a1 = StaticWrapper.getWrapped("A");
        final WrappedFinal b1 = StaticWrapper.getWrapped("B");
        // Comparing of references is desired.
        Assert.isTrue(a1 != b1);

        final WrappedFinal a2 = StaticWrapper.getWrapped("A");
        // Comparing of references is desired.
        Assert.isTrue(a1 == a2);

        System.out.println("success");
    }

}
