package com.milaboratory.mir.io;

import com.milaboratory.mir.segment.SegmentSequenceLibrary;

public interface SegmentSequenceLibraryFactory<T extends SegmentSequenceLibrary> {
    T create();
}
