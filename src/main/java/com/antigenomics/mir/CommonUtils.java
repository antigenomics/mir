package com.antigenomics.mir;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class CommonUtils {
    private static final CommonUtils THIS = new CommonUtils();

    private CommonUtils() {
    }

    public static InputStream getFileAsStream(String path) throws IOException {
        return getFileAsStream(path, isGzipped(path));
    }

    public static InputStream getFileAsStream(String path, boolean gzip) throws IOException {
        var stream = new FileInputStream(path);
        return gzip ? new GZIPInputStream(stream) : stream;
    }

    public static OutputStream createFileAsStream(String path) throws IOException {
        return createFileAsStream(path, isGzipped(path));
    }

    public static OutputStream createFileAsStream(String path, boolean gzip) throws IOException {
        var stream = new FileOutputStream(path);
        return gzip ? new GZIPOutputStream(stream) : stream;
    }

    public static InputStream getResourceAsStream(String path) throws IOException {
        return getResourceAsStream(path, isGzipped(path));
    }

    public static InputStream getResourceAsStream(String path, boolean gzip) throws IOException {
        var stream = THIS.getClass().getClassLoader().getResourceAsStream(path);
        return gzip ? new GZIPInputStream(stream) : stream;
    }

    private static boolean isGzipped(String path) {
        return path.toLowerCase().endsWith(".gz");
    }

    // todo: transfer to collection utils

    public static <K1, V1, K2, V2> Map<K2, V2> map2map(Map<K1, V1> map,
                                                       Function<Map.Entry<K1, V1>, K2> keyMapper,
                                                       Function<Map.Entry<K1, V1>, V2> valueMapper) {
        return map.entrySet().stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public static <T> boolean matchIgnoreOrder(Set<T> x, Set<T> y) {
        return x.size() == y.size() && x.containsAll(y);
    }

    public static <T> boolean matchIgnoreOrder(List<T> x, List<T> y) {
        return matchIgnoreOrder(new HashSet<>(x), new HashSet<>(y));
    }

    public static <T> boolean matchIgnoreOrder(T[] x, T[] y) {
        return matchIgnoreOrder(Arrays.asList(x), Arrays.asList(y));
    }

   /* public static <K1, V1, K2, T, U> Map<K2, Map<T, U>> map2mapSafe(Map<K1, V1> map,
                                                                    Function<Map.Entry<K1, V1>, K2> keyMapper,
                                                                    Function<Map.Entry<K1, V1>, Map<T, U>> valueMapper) {
        return map.entrySet().stream().collect(Collectors.toMap(keyMapper, valueMapper,
                (m1, m2) -> {
                    var res = new HashMap<>(m1);
                    res.putAll(m2);
                    return res;
                }));
    } */
}
