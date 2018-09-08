package com.milaboratory.mir.mappers.simple;

import com.milaboratory.core.alignment.Alignment;

import java.util.Comparator;

public class AlignmentComparator implements Comparator<Alignment> {
    @Override
    public int compare(Alignment o1, Alignment o2) {
        return -Float.compare(o1.getScore(), o2.getScore());
    }
}
