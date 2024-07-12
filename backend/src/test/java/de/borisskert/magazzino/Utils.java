package de.borisskert.magazzino;

import java.util.List;
import java.util.stream.StreamSupport;

public class Utils {
    private Utils() {
        // utility class
    }

    // https://stackoverflow.com/a/23996861/13213024
    public static <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(
                        iterable.spliterator(),
                        false
                )
                .toList();
    }
}
