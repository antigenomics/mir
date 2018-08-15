package com.milaboratory.mir;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestUtils {
    public static boolean testStream(InputStream inputStream) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return !buffer.lines().collect(Collectors.joining("\n")).isEmpty();
        }
    }
}
