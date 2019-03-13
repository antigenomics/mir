package com.antigenomics.mir.clonotype.rearrangement;

import com.antigenomics.mir.clonotype.Clonotype;

public interface ClonotypeWithRearrangementInfo extends Clonotype {
    SegmentTrimming getSegmentTrimming();

    JunctionMarkup getJunctionMarkup();
}
