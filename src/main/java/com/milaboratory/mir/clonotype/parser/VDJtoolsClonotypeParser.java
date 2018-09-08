package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.StringArrayIndexer;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.JunctionMarkup;
import com.milaboratory.mir.clonotype.ReadlessClonotypeImpl;
import com.milaboratory.mir.clonotype.SegmentTrimming;
import com.milaboratory.mir.segment.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VDJtoolsClonotypeParser extends AbstractClonotypeTableParser<ReadlessClonotypeImpl> {
    private final HeaderInfo headerInfo;
    private final AtomicInteger idCounter = new AtomicInteger();

    public VDJtoolsClonotypeParser(String[] header,
                                   SegmentLibrary segmentLibrary,
                                   boolean majorAlleles) {
        super(header, segmentLibrary, majorAlleles);
        this.headerInfo = new HeaderInfo(header);
    }

    @Override
    public ClonotypeCall<ReadlessClonotypeImpl> parse(String[] splitLine) {
        int id = idCounter.incrementAndGet();
        int count = Integer.parseInt(splitLine[headerInfo.countColIndex]);
        double freq = Double.parseDouble(splitLine[headerInfo.freqColIndex]);

        NucleotideSequence cdr3Nt = new NucleotideSequence(splitLine[headerInfo.cdr3NtColIndex]);

        AminoAcidSequence cdr3Aa = AminoAcidSequence.translateFromCenter(cdr3Nt); // todo: perhaps read in if provided?

        List<SegmentCall<VariableSegment>> vCalls = new ArrayList<>();
        for (String v : splitLine[headerInfo.vColIndex].split(",")) {
            vCalls.add(getV(v, 1.0f));
        }

        List<SegmentCall<DiversitySegment>> dCalls = new ArrayList<>();
        if (headerInfo.dColIndex >= 0) {
            for (String d : splitLine[headerInfo.dColIndex].split(",")) {
                dCalls.add(getD(d, 1.0f));
            }
        }

        List<SegmentCall<JoiningSegment>> jCalls = new ArrayList<>();
        for (String j : splitLine[headerInfo.jColIndex].split(",")) {
            jCalls.add(getJ(j, 1.0f));
        }

        List<SegmentCall<ConstantSegment>> cCalls = new ArrayList<>();
        if (headerInfo.cColIndex >= 0) {
            for (String c : splitLine[headerInfo.cColIndex].split(",")) {
                cCalls.add(getC(c, 1.0f));
            }
        }

        int vTrim = -1, dTrim5 = -1, dTrim3 = -1, jTrim = -1;
        if (headerInfo.vTrimColIndex >= 0) {
            vTrim = Integer.parseInt(splitLine[headerInfo.vTrimColIndex]);
        }
        if (headerInfo.dTrim5ColIndex >= 0) {
            dTrim5 = Integer.parseInt(splitLine[headerInfo.dTrim5ColIndex]);
        }
        if (headerInfo.dTrim3ColIndex >= 0) {
            dTrim3 = Integer.parseInt(splitLine[headerInfo.dTrim3ColIndex]);
        }
        if (headerInfo.jTrimColIndex >= 0) {
            jTrim = Integer.parseInt(splitLine[headerInfo.jTrimColIndex]);
        }
        SegmentTrimming segmentTrimming = new SegmentTrimming(vTrim, jTrim, dTrim5, dTrim3);

        int vEnd = -1, dStart = -1, dEnd = -1, jStart = -1;
        if (headerInfo.vEndColIndex >= 0) {
            vEnd = Integer.parseInt(splitLine[headerInfo.vEndColIndex]);
        }
        if (headerInfo.dStartColIndex >= 0) {
            dStart = Integer.parseInt(splitLine[headerInfo.dStartColIndex]);
        }
        if (headerInfo.dEndColIndex >= 0) {
            dEnd = Integer.parseInt(splitLine[headerInfo.dEndColIndex]);
        }
        if (headerInfo.jStartColIndex >= 0) {
            jStart = Integer.parseInt(splitLine[headerInfo.jStartColIndex]);
        }
        JunctionMarkup junctionMarkup = new JunctionMarkup(vEnd, jStart, dStart, dEnd);

        return new ClonotypeCall<>(id, count, freq,
                new ReadlessClonotypeImpl(cdr3Nt,
                        vCalls, dCalls, jCalls, cCalls,
                        segmentTrimming, junctionMarkup,
                        cdr3Aa));
    }

    private static class HeaderInfo {
        final int countColIndex, freqColIndex,
                cdr3NtColIndex, cdr3AaColIndex,
                vColIndex, dColIndex, jColIndex, cColIndex,
                vTrimColIndex, dTrim5ColIndex, dTrim3ColIndex, jTrimColIndex,
                vEndColIndex, dStartColIndex, dEndColIndex, jStartColIndex;

        HeaderInfo(String[] header) {
            StringArrayIndexer headerParser = new StringArrayIndexer(header);
            this.countColIndex = headerParser.getIndexOf("count");
            this.freqColIndex = headerParser.getIndexOfS("freq");
            this.cdr3NtColIndex = headerParser.getIndexOf("cdr3nt");
            this.cdr3AaColIndex = headerParser.getIndexOf("cdr3aa", false);
            this.vColIndex = headerParser.getIndexOf("v");
            this.dColIndex = headerParser.getIndexOf("d", false);
            this.jColIndex = headerParser.getIndexOf("j");
            this.cColIndex = headerParser.getIndexOf("c", false);
            this.vTrimColIndex = headerParser.getIndexOf("vtrim", false);
            this.dTrim5ColIndex = headerParser.getIndexOf("dtrim5", false);
            this.dTrim3ColIndex = headerParser.getIndexOf("dtrim3", false);
            this.jTrimColIndex = headerParser.getIndexOf("jtrim", false);
            this.vEndColIndex = headerParser.getIndexOf("vend", false);
            this.dStartColIndex = headerParser.getIndexOf("dstart", false);
            this.dEndColIndex = headerParser.getIndexOf("dend", false);
            this.jStartColIndex = headerParser.getIndexOf("jstart", false);
        }
    }
}
