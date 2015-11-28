package cz.skalicky.javaplayground;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlatMap {

    public static void main(String[] args) {

        final List<List<String>> fullNames = new ArrayList<>();
        fullNames.add(Arrays.asList("Tomas", "Skalický"));
        fullNames.add(Arrays.asList("Skalická", "Hanička"));

        // @formatter:off
        final List<String> skalickNames = fullNames.stream()
                .flatMap(list -> list.stream())
                .filter(Objects::nonNull)
                .filter(n -> n.startsWith("Skalick"))
                .collect(Collectors.toList());
        // @formatter:on

        assertThat(skalickNames, containsInAnyOrder("Skalický", "Skalická"));
        System.out.println("SUCCESS");
    }

}
