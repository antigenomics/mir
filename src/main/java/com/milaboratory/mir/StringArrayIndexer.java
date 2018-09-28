package com.milaboratory.mir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringArrayIndexer {
    private final List<String> headerList;
    private final String[] headerArray;
    private final boolean ignoreCase;

    public StringArrayIndexer(List<String> header) {
        this(header, true);
    }

    public StringArrayIndexer(List<String> header, boolean ignoreCase) {
        this.headerList = ignoreCase ?
                header.stream().map(String::toLowerCase).collect(Collectors.toList()) :
                new ArrayList<>(header);
        this.headerArray = new String[header.size()];
        for (int i = 0; i < header.size(); i++) {
            headerArray[i] = header.get(i);
        }
        this.ignoreCase = ignoreCase;
    }

    public StringArrayIndexer(String[] header) {
        this(header, true);
    }

    public StringArrayIndexer(String[] header, boolean ignoreCase) {
        this.headerArray = header;
        this.headerList = ignoreCase ?
                Arrays.stream(header).map(String::toLowerCase).collect(Collectors.toList()) :
                Arrays.asList(header);
        this.ignoreCase = ignoreCase;
    }

    public int getIndexOf(String name) {
        return getIndexOf(name, true);
    }

    public int getIndexOf(String name, boolean strict) {
        int res = headerList.indexOf(ignoreCase ? name.toLowerCase() : name);
        if (strict && res < 0) {
            throw new RuntimeException("Failed to parse header, " +
                    "missing column '" + name + "'");
        }
        return res;
    }

    public int getIndexOf(String[] names, boolean strict) {
        for (String name : names) {
            int res = getIndexOf(name, false);
            if (res > -1) {
                return res;
            }
        }
        if (strict) {
            throw new RuntimeException("Failed to parse header, " +
                    "missing column '" + Arrays.toString(names) + "'");
        }
        return -1;
    }

    public int getIndexOfS(String name) {
        return getIndexOfS(name, true);
    }

    public int getIndexOfS(String name, boolean strict) {
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.get(i).startsWith(ignoreCase ? name.toLowerCase() : name)) {
                return i;
            }
        }
        if (strict) {
            throw new RuntimeException("Failed to parse header, " +
                    "missing column '" + name + "'");
        }
        return -1;
    }

    public int getIndexOfS(String[] names, boolean strict) {
        for (String name : names) {
            int res = getIndexOfS(name, false);
            if (res > -1) {
                return res;
            }
        }
        if (strict) {
            throw new RuntimeException("Failed to parse header, " +
                    "missing column '" + Arrays.toString(names) + "'");
        }
        return -1;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public <T> T[] reorder(T[] row, String[] otherHeader) {
        if (!CommonUtils.matchIgnoreOrder(headerArray, otherHeader)) {
            throw new IllegalArgumentException("Different names in current header " +
                    Arrays.toString(headerArray) + " and input header " + Arrays.toString(otherHeader));
        }

        var res = row.clone();

        for (int i = 0; i < otherHeader.length; i++) {
            res[i] = row[getIndexOf(otherHeader[i], false)];
        }

        return res;
    }

    public <T> T[] reorder(T[] row, List<String> otherHeader) {
        if (!CommonUtils.matchIgnoreOrder(headerList, otherHeader)) {
            throw new IllegalArgumentException("Different names in current header " +
                    headerList + " and input header " + otherHeader);
        }

        var res = row.clone();

        for (int i = 0; i < otherHeader.size(); i++) {
            res[i] = row[getIndexOf(otherHeader.get(i), false)];
        }

        return res;
    }
}
