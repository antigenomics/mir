package com.milaboratory.mir.clonotype.rearrangement;

import com.milaboratory.mir.clonotype.Clonotype;

public interface ClonotypeWithRearrangementInfo extends Clonotype {
    SegmentTrimming getSegmentTrimming();

    JunctionMarkup getJunctionMarkup();
}
