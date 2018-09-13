package com.milaboratory.mir;

import com.milaboratory.core.sequence.NucleotideSequence;
import org.junit.Assert;
import org.junit.Test;


public class SequenceUtilsTest {

    @Test
    public void getSequenceRangeSafe() {
        //                                0123456789012345
        var seq = new NucleotideSequence("ATTAGACAATTACACA");

        Assert.assertEquals(new NucleotideSequence("ATTAGA"), SequenceUtils.getSequenceRangeSafe(seq, -100, 6));
        Assert.assertEquals(new NucleotideSequence("TTAGA"), SequenceUtils.getSequenceRangeSafe(seq, 1, 6));
        Assert.assertEquals(new NucleotideSequence("A"), SequenceUtils.getSequenceRangeSafe(seq, 0, 1));
        Assert.assertEquals(NucleotideSequence.EMPTY, SequenceUtils.getSequenceRangeSafe(seq, 0, 0));
        Assert.assertEquals(NucleotideSequence.EMPTY, SequenceUtils.getSequenceRangeSafe(seq, 0, -1));
        Assert.assertEquals(new NucleotideSequence("TTACACA"), SequenceUtils.getSequenceRangeSafe(seq, 9, 1000));
    }

    @Test
    public void getReverse() {
        Assert.assertEquals(new NucleotideSequence("ATTAGACA"), SequenceUtils.getReverse(new NucleotideSequence("ACAGATTA")));
        Assert.assertEquals(new NucleotideSequence("A"), SequenceUtils.getReverse(new NucleotideSequence("A")));
        Assert.assertEquals(NucleotideSequence.EMPTY, SequenceUtils.getReverse(new NucleotideSequence("")));
    }
}