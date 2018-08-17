package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.Misc;

public interface JoiningSegment extends Cdr3GermlineSegment {
    NucleotideSequence getTrimmedCdr3Part(int trimmingSize);// {
    //    int len = getCdr3PartSize();
    //    return Misc.getSequenceRangeSafe(getCdr3PartWithP(), len + trimmingSize, 2 * len);
    //}
}
