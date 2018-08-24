package com.milaboratory.mir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class TestUtils {
    public static boolean testStream(InputStream inputStream) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return !buffer.lines().collect(Collectors.joining("\n")).isEmpty();
        }
    }

    public static Supplier<InputStream> streamSupplierFrom(String path) {
        return () -> {
            try {
                return CommonUtils.getResourceAsStream(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
