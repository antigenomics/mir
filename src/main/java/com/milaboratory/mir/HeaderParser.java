package com.milaboratory.mir;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderParser {
    private final List<String> headerList;

    public HeaderParser(String[] header) {
        this.headerList = Arrays.stream(header).map(String::toLowerCase).collect(Collectors.toList());
    }

    public int getIndexOf(String name) {
        return getIndexOf(name, true);
    }

    public int getIndexOf(String name, boolean strict) {
        int res = headerList.indexOf(name.toLowerCase());
        if (strict && res < 0) {
            throw new RuntimeException("Failed to parse header, " +
                    "missing column '" + name + "'");
        }
        return res;
    }

    public int getIndexOfS(String name) {
        return getIndexOfS(name, true);
    }

    public int getIndexOfS(String name, boolean strict) {
        name = name.toLowerCase();
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.get(i).startsWith(name)) {
                return i;
            }
        }
        if (strict) {
            throw new RuntimeException("Failed to parse header, " +
                    "missing column '" + name + "'");
        }
        return -1;
    }
}
