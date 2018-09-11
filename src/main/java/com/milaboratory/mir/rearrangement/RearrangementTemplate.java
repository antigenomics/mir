package com.milaboratory.mir.rearrangement;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.rearrangement.JunctionMarkup;
import com.milaboratory.mir.segment.AbsentDiversitySegment;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class RearrangementTemplate {
    private final VariableSegment variableSegment;
    private final JoiningSegment joiningSegment;
    private final DiversitySegment diversitySegment;
    private final int variableTrimming, joiningTrimming,
            diversityTrimming5, diversityTrimming3;
    private final NucleotideSequence variableDiversityInsert, diversityJoiningInsert,
            variableJoiningInsert;

    public RearrangementTemplate(VariableSegment variableSegment, JoiningSegment joiningSegment,
                                 int variableTrimming, int joiningTrimming,
                                 NucleotideSequence variableJoiningInsert) {
        this.variableSegment = variableSegment;
        this.joiningSegment = joiningSegment;
        this.diversitySegment = AbsentDiversitySegment.INSTANCE;
        this.variableTrimming = variableTrimming;
        this.joiningTrimming = joiningTrimming;
        this.diversityTrimming5 = -1;
        this.diversityTrimming3 = -1;
        this.variableDiversityInsert = NucleotideSequence.EMPTY;
        this.diversityJoiningInsert = NucleotideSequence.EMPTY;
        this.variableJoiningInsert = variableJoiningInsert;
    }

    public RearrangementTemplate(VariableSegment variableSegment, JoiningSegment joiningSegment,
                                 DiversitySegment diversitySegment,
                                 int variableTrimming, int joiningTrimming,
                                 int diversityTrimming5, int diversityTrimming3,
                                 NucleotideSequence variableDiversityInsert, NucleotideSequence diversityJoiningInsert) {
        this.variableSegment = variableSegment;
        this.joiningSegment = joiningSegment;
        this.diversitySegment = diversitySegment;
        this.variableTrimming = variableTrimming;
        this.joiningTrimming = joiningTrimming;
        this.diversityTrimming5 = diversityTrimming5;
        this.diversityTrimming3 = diversityTrimming3;
        this.variableDiversityInsert = variableDiversityInsert;
        this.diversityJoiningInsert = diversityJoiningInsert;
        this.variableJoiningInsert = NucleotideSequence.EMPTY;
    }

    public boolean hasD() {
        return diversitySegment != AbsentDiversitySegment.INSTANCE;
    }

    public Rearrangement toRearrangement() {
        if (hasD()) {
            NucleotideSequence cdr3 = variableSegment.getTrimmedCdr3Part(variableTrimming);
            int vEnd = cdr3.size();
            cdr3 = cdr3.concatenate(variableDiversityInsert);
            int dStart = cdr3.size();
            cdr3 = cdr3.concatenate(
                    diversitySegment.getTrimmedCdr3Part(diversityTrimming5, diversityTrimming3)
            );
            int dEnd = cdr3.size();
            cdr3 = cdr3.concatenate(diversityJoiningInsert);
            int jStart = cdr3.size();
            cdr3 = cdr3.concatenate(
                    joiningSegment.getTrimmedCdr3Part(joiningTrimming)
            );

            return new Rearrangement(this,
                    new JunctionMarkup(vEnd, jStart, dStart, dEnd),
                    cdr3);
        } else {
            NucleotideSequence cdr3 = variableSegment.getTrimmedCdr3Part(variableTrimming);
            int vEnd = cdr3.size();
            cdr3 = cdr3.concatenate(variableJoiningInsert);
            int jStart = cdr3.size();
            cdr3 = cdr3.concatenate(
                    joiningSegment.getTrimmedCdr3Part(joiningTrimming)
            );

            return new Rearrangement(this,
                    new JunctionMarkup(vEnd, jStart),
                    cdr3);
        }
    }

    public VariableSegment getVariableSegment() {
        return variableSegment;
    }

    public JoiningSegment getJoiningSegment() {
        return joiningSegment;
    }

    public DiversitySegment getDiversitySegment() {
        return diversitySegment;
    }

    public int getVariableTrimming() {
        return variableTrimming;
    }

    public int getJoiningTrimming() {
        return joiningTrimming;
    }

    public int getDiversityTrimming5() {
        return diversityTrimming5;
    }

    public int getDiversityTrimming3() {
        return diversityTrimming3;
    }

    public NucleotideSequence getVariableDiversityInsert() {
        return variableDiversityInsert;
    }

    public NucleotideSequence getDiversityJoiningInsert() {
        return diversityJoiningInsert;
    }

    public NucleotideSequence getVariableJoiningInsert() {
        return variableJoiningInsert;
    }
}
