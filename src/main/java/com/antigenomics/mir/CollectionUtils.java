package com.antigenomics.mir;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class CollectionUtils {
    public static <T extends Comparable<? super T>> List<T> getKFirst(List<T> input, int k,
                                                                      boolean sortedInput, boolean fair) {
        if (k < 1) {
            throw new IllegalArgumentException("Number of top hits should be greater than zero");
        }

        if (input.size() <= k) {
            return input;
        }

        if (!sortedInput) {
            input = new ArrayList<>(input);
            input.sort(Comparator.naturalOrder());
        }

        if (!fair) {
            return input.subList(0, k);
        } else {
            var prev = input.get(0);
            int i = 1;
            int counter = 1;
            for (; i < input.size(); i++) {
                var current = input.get(i);
                if (current.compareTo(prev) != 0 &&
                        ++counter > k) {
                    break;
                }
                prev = current;
            }

            if (i == input.size() && sortedInput) {
                return input; // don't copy once more
            }

            return input.subList(0, i);
        }
    }
}
