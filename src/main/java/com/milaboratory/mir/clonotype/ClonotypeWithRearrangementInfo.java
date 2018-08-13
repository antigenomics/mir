package com.milaboratory.mir.clonotype;

public interface ClonotypeWithRearrangementInfo extends Clonotype {
    SegmentTrimming getSegmentTrimming();

    JunctionMarkup getJunctionMarkup();
}
