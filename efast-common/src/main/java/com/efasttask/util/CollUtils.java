package com.efasttask.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class CollUtils {
    public static List<Integer> rangeInt(int start, int to) {
        return IntStream.rangeClosed(start, to + 1).boxed().collect(Collectors.toList());
    }

    public static List<Long> rangeLong(long start, long to) {
        return LongStream.rangeClosed(start, to + 1).boxed().collect(Collectors.toList());
    }
}
