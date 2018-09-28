package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.StringArrayIndexer;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.rearrangement.JunctionMarkup;
import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.clonotype.rearrangement.SegmentTrimming;
import com.milaboratory.mir.segment.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MixcrClonotypeParser extends AbstractClonotypeTableParser<ReadlessClonotypeImpl> {
    private final HeaderInfo headerInfo;
    private final RefPointColInfo refPointsColInfo;
    private final AtomicInteger idCounter = new AtomicInteger();

    public MixcrClonotypeParser(String[] header,
                                SegmentLibrary segmentLibrary,
                                boolean majorAlleles) {
        super(header, segmentLibrary, majorAlleles);
        this.headerInfo = new HeaderInfo(header);
        this.refPointsColInfo = new RefPointColInfo();
    }

    @Override
    public ClonotypeCall<ReadlessClonotypeImpl> parse(String[] splitLine) {
        int id = idCounter.incrementAndGet();
        int count = Integer.parseInt(splitLine[headerInfo.countColIndex]);
        double freq = Double.parseDouble(splitLine[headerInfo.freqColIndex]);

        NucleotideSequence cdr3Nt = new NucleotideSequence(splitLine[headerInfo.cdr3NtColIndex]);

        AminoAcidSequence cdr3Aa = new AminoAcidSequence(splitLine[headerInfo.cdr3AaColIndex]);
        //todo: perhaps read in if provided?

        List<SegmentCall<VariableSegment>> vCalls = new ArrayList<>();

        for (String v : splitLine[headerInfo.vColIndex].split(",")) {
            String[] vInfo = v.split("\\(");
            var variableSegment = getV(vInfo[0], Float.parseFloat(vInfo[1].split("\\)")[0]));
            vCalls.add(variableSegment);
        }

        List<SegmentCall<DiversitySegment>> dCalls = new ArrayList<>();
        if (headerInfo.dColIndex >= 0) {
            for (String d : splitLine[headerInfo.dColIndex].split(",")) {
                String[] dInfo = d.split("\\(");
                var diversitySegment = getD(dInfo[0], Float.parseFloat(dInfo[1].split("\\)")[0]));
                dCalls.add(diversitySegment);
            }
        }

        List<SegmentCall<JoiningSegment>> jCalls = new ArrayList<>();
        for (String j : splitLine[headerInfo.jColIndex].split(",")) {
            String[] jInfo = j.split("\\(");
            var joiningSegment = getJ(jInfo[0], Float.parseFloat(jInfo[1].split("\\)")[0]));
            jCalls.add(joiningSegment);
        }

        List<SegmentCall<ConstantSegment>> cCalls = new ArrayList<>();
        if (headerInfo.cColIndex >= 0) {
            for (String c : splitLine[headerInfo.cColIndex].split(",")) {
                String[] cInfo = c.split("\\(");
                var constantSegment = getC(cInfo[0], Float.parseFloat(cInfo[1].split("\\)")[0]));
                cCalls.add(constantSegment);
            }
        }

        String[] refPoints = splitLine[headerInfo.refPointColIndex].split(",")[0].split(":", -1);

        int vEnd = -1, dStart = -1, dEnd = -1, jStart = -1;
        int cdr3Start = Integer.parseInt(refPoints[refPointsColInfo.CDR3Begin]);
        if (!refPoints[refPointsColInfo.VEndTrimmed].isEmpty()) {
            vEnd = Integer.parseInt(refPoints[refPointsColInfo.VEndTrimmed]) - cdr3Start;
        }
        if (!refPoints[refPointsColInfo.DBeginTrimmed].isEmpty()) {
            dStart = Integer.parseInt(refPoints[refPointsColInfo.DBeginTrimmed]) - cdr3Start;
        }
        if (!refPoints[refPointsColInfo.DEndTrimmed].isEmpty()) {
            dEnd = Integer.parseInt(refPoints[refPointsColInfo.DEndTrimmed]) - cdr3Start;
        }
        if (!refPoints[refPointsColInfo.JBeginTrimmed].isEmpty()) {
            jStart = Integer.parseInt(refPoints[refPointsColInfo.JBeginTrimmed]) - cdr3Start;
        }
        JunctionMarkup junctionMarkup = new JunctionMarkup(vEnd, jStart, dStart, dEnd);


        int vTrim = 0, dTrim5 = 0, dTrim3 = 0, jTrim = 0;
        if (!refPoints[refPointsColInfo.VEndTrimmed].isEmpty()) {
            vTrim = Integer.parseInt(refPoints[refPointsColInfo.Num3V]);
        }
        if (!refPoints[refPointsColInfo.DBeginTrimmed].isEmpty()) {
            dTrim5 = Integer.parseInt(refPoints[refPointsColInfo.Num5D]);
        }
        if (!refPoints[refPointsColInfo.DEndTrimmed].isEmpty()) {
            dTrim3 = Integer.parseInt(refPoints[refPointsColInfo.Num3D]);
        }
        if (!refPoints[refPointsColInfo.JBeginTrimmed].isEmpty()) {
            jTrim = Integer.parseInt(refPoints[refPointsColInfo.Num3J]);
        }
        SegmentTrimming segmentTrimming = new SegmentTrimming(vTrim, jTrim, dTrim5, dTrim3);

        return new ClonotypeCall<>(id, // todo: add clone ID
                count, freq,
                new ReadlessClonotypeImpl(cdr3Nt,
                        vCalls, dCalls, jCalls, cCalls,
                        segmentTrimming, junctionMarkup,
                        cdr3Aa));
    }


    private static class RefPointColInfo {
        final int V5UTRBeginTrimmed, V5UTREnd, L1Begin,
                L1End, VIntronBegin, VIntronEnd, L2Begin,
                L2End, FR1Begin, FR1End, CDR1Begin,
                CDR1End, FR2Begin, FR2End, CDR2Begin,
                CDR2End, FR3Begin, FR3End, CDR3Begin,
                VEndTrimmed, DBeginTrimmed, DEndTrimmed, JBeginTrimmed,
                CDR3End, FR4Begin, FR4End,
                CBegin, CExon1End, Num3V, Num5D, Num3D, Num3J;

        RefPointColInfo() {                        //
            this.V5UTRBeginTrimmed = 0;
            this.V5UTREnd = 1;
            this.L1Begin = 1;
            this.L1End = 2;
            this.VIntronBegin = 2;
            this.VIntronEnd = 3;
            this.L2Begin = 3;
            this.L2End = 4;
            this.FR1Begin = 4;
            this.FR1End = 5;
            this.CDR1Begin = 5;
            this.CDR1End = 6;
            this.FR2Begin = 6;
            this.FR2End = 7;
            this.CDR2Begin = 7;
            this.CDR2End = 8;
            this.FR3Begin = 8;
            this.FR3End = 9;
            this.CDR3Begin = 9;
            this.Num3V = 10;
            this.VEndTrimmed = 11;
            this.DBeginTrimmed = 12;
            this.Num5D = 13;
            this.Num3D = 14;
            this.DEndTrimmed = 15;
            this.JBeginTrimmed = 16;
            this.Num3J = 17;
            this.CDR3End = 18;
            this.FR4Begin = 19;
            this.FR4End = 19;
            this.CBegin = 20;
            this.CExon1End = 21;
        }
    }

    private static class HeaderInfo {
        final int cloneIdColIndex,
                countColIndex, freqColIndex,
                cdr3NtColIndex, cdr3AaColIndex,
                vColIndex, dColIndex, jColIndex, cColIndex,
                refPointColIndex;

        HeaderInfo(String[] header) {
            StringArrayIndexer headerParser = new StringArrayIndexer(header);
            // todo: add clone ID
            this.cloneIdColIndex = headerParser.getIndexOf(new String[]{"cloneId", "Clone ID"});
            this.countColIndex = headerParser.getIndexOf(new String[]{"cloneCount", "Clone count"});
            this.freqColIndex = headerParser.getIndexOfS(new String[]{"cloneFraction", "Clone fraction"});
            this.cdr3NtColIndex = headerParser.getIndexOf(new String[]{"nSeqCDR3",  "N. Seq. CDR3"});
            this.cdr3AaColIndex = headerParser.getIndexOf(new String[]{"aaSeqCDR3", "AA. Seq. CDR3"}, false);
            this.vColIndex = headerParser.getIndexOf(new String[]{"allVHitsWithScore", "All V Hits With Score ", "All V hits"}, false);
            this.dColIndex = headerParser.getIndexOf(new String[]{"allDHitsWithScore", "All D Hits With Score ", "All D hits"}, false);
            this.jColIndex = headerParser.getIndexOf(new String[]{"allJHitsWithScore", "All J Hits With Score ", "All J hits"}, false);
            this.cColIndex = headerParser.getIndexOf(new String[]{"allCHitsWithScore", "All C Hits With Score ", "All C hits"}, false);
            this.refPointColIndex = headerParser.getIndexOf(new String[]{"Ref. points"});
        }
    }
}
