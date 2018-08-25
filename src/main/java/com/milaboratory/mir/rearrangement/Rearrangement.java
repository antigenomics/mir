package com.milaboratory.mir.rearrangement;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.JunctionMarkup;

public class Rearrangement {
    private final RearrangementTemplate rearrangementTemplate;
    private final JunctionMarkup junctionMarkup;
    private final NucleotideSequence cdr3;

    public Rearrangement(RearrangementTemplate rearrangementTemplate,
                         JunctionMarkup junctionMarkup, NucleotideSequence cdr3) {
        this.rearrangementTemplate = rearrangementTemplate;
        this.junctionMarkup = junctionMarkup;
        this.cdr3 = cdr3;
    }

    public RearrangementTemplate getRearrangementTemplate() {
        return rearrangementTemplate;
    }

    public JunctionMarkup getJunctionMarkup() {
        return junctionMarkup;
    }

    public NucleotideSequence getCdr3() {
        return cdr3;
    }
}
