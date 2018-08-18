package com.milaboratory.mir;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {
    private static final CommonUtils THIS = new CommonUtils();

    private CommonUtils() {
    }

    public static InputStream getResourceAsStream(String path) {
        return THIS.getClass().getClassLoader().getResourceAsStream(path);
    }
}
