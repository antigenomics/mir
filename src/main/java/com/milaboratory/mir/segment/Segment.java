package com.milaboratory.mir.segment;

public interface Segment {
    String getId();

    boolean isMissingInLibrary();

    boolean isMajorAllele();
}
