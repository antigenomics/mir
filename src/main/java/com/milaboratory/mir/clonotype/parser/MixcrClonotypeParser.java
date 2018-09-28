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

public class MixcrClonotypeParser extends AbstractClonotypeTableParser<ReadlessClonotypeImpl> {
    private final HeaderInfo headerInfo;
    private final RefPointColInfo refPointsColInfo;
    private final AtomicInteger idCounter = new AtomicInteger();

    public MixcrClonotypeParser(String[] header,
                                   SegmentLibrary segmentLibrary) {
        super(header, segmentLibrary);
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

        List<SegmentCall<VariableSegment>> vCalls = new ArrayList<>();;
        for (String v : splitLine[headerInfo.vColIndex].split(",")) {
            String[] vInfo = v.split("\\(");
            VariableSegment variableSegment = segmentLibrary.getOrCreateV(vInfo[0]);
            vCalls.add(SegmentCall.asCall(variableSegment));
        }

        List<SegmentCall<DiversitySegment>> dCalls = new ArrayList<>();
        if (headerInfo.dColIndex >= 0) {
            for (String d : splitLine[headerInfo.dColIndex].split(",")) {
                String[] dInfo = d.split("\\(");
                DiversitySegment diversitySegment = segmentLibrary.getOrCreateD(dInfo[0]);
                dCalls.add(SegmentCall.asCall(diversitySegment));
            }
        }

        List<SegmentCall<JoiningSegment>> jCalls = new ArrayList<>();
        for (String j : splitLine[headerInfo.jColIndex].split(",")) {
            String[] jInfo = j.split("\\(");
            JoiningSegment joiningSegment = segmentLibrary.getOrCreateJ(jInfo[0]);
            jCalls.add(SegmentCall.asCall(joiningSegment));
        }

        List<SegmentCall<ConstantSegment>> cCalls = new ArrayList<>();
        if (headerInfo.cColIndex >= 0) {
            for (String c : splitLine[headerInfo.cColIndex].split(",")) {
                String[] cInfo = c.split("\\(");
                ConstantSegment constantSegment = segmentLibrary.getOrCreateC(cInfo[0]);
                cCalls.add(SegmentCall.asCall(constantSegment));
            }
        }

        String[] refPoints = splitLine[headerInfo.refPointColIndex].split(",")[0].split(":", -1);

        int vTrim = -1, dTrim5 = -1, dTrim3 = -1, jTrim = -1;
        if (!refPoints[refPointsColInfo.VEndTrimmed].isEmpty()) {
            vTrim = Integer.parseInt(refPoints[refPointsColInfo.VEndTrimmed]);
        }
        if (!refPoints[refPointsColInfo.DBeginTrimmed].isEmpty()) {
            dTrim5 = Integer.parseInt(refPoints[refPointsColInfo.DBeginTrimmed]);
        }
        if (!refPoints[refPointsColInfo.DEndTrimmed].isEmpty()) {
            dTrim3 = Integer.parseInt(refPoints[refPointsColInfo.DEndTrimmed]);
        }
        if (!refPoints[refPointsColInfo.JBeginTrimmed].isEmpty()) {
            jTrim = Integer.parseInt(refPoints[refPointsColInfo.JBeginTrimmed]);
        }
        SegmentTrimming segmentTrimming = new SegmentTrimming(vTrim, jTrim, dTrim5, dTrim3);

        int vEnd = -1, dStart = -1, dEnd = -1, jStart = -1;
        if (headerInfo.vEndColIndex >= 0) {
            vEnd = Integer.parseInt(splitLine[headerInfo.vEndColIndex]);
        } else {
            if (!vCalls.get(0).getSegment().isDummy()){
                vEnd = Integer.parseInt(refPoints[refPointsColInfo.CDR3Begin]) +
                        vCalls.get(0).getSegment().getCdr3Part().size();
            }
        }
        if (headerInfo.dStartColIndex >= 0) {
            dStart = Integer.parseInt(splitLine[headerInfo.dStartColIndex]);
        }
        if (headerInfo.dEndColIndex >= 0) {
            dEnd = Integer.parseInt(splitLine[headerInfo.dEndColIndex]);
        }
        if (headerInfo.jStartColIndex >= 0) {
            jStart = Integer.parseInt(refPoints[refPointsColInfo.CDR3Begin]) +
                    Integer.parseInt(splitLine[headerInfo.jStartColIndex]);
        } else {
            if (!jCalls.get(0).getSegment().isDummy() && jTrim != -1){
                jStart = cdr3Nt.size() - jCalls.get(0).getSegment().getCdr3Part().size();
            }
        }
        JunctionMarkup junctionMarkup = new JunctionMarkup(vEnd, jStart, dStart, dEnd);

        return new ClonotypeCall<>(id, count, freq,
                new ReadlessClonotypeImpl(cdr3Nt,
                        vCalls, dCalls, jCalls, cCalls,
                        segmentTrimming, junctionMarkup,
                        cdr3Aa));
    }


    private static class RefPointColInfo{
        final int V5UTRBeginTrimmed, V5UTREnd, L1Begin,
                L1End, VIntronBegin, VIntronEnd, L2Begin,
                L2End, FR1Begin, FR1End, CDR1Begin,
                CDR1End, FR2Begin, FR2End, CDR2Begin,
                CDR2End, FR3Begin, FR3End, CDR3Begin,
                VEndTrimmed, DBeginTrimmed, DEndTrimmed, JBeginTrimmed,
                CDR3End, FR4Begin, FR4End,
                CBegin, CExon1End, Num3V, Num5D, Num3D, Num3J;
        RefPointColInfo(){                        //
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
        final int countColIndex, freqColIndex,
                cdr3NtColIndex, cdr3AaColIndex,
                vColIndex, dColIndex, jColIndex, cColIndex,
                vTrimColIndex, dTrim5ColIndex, dTrim3ColIndex, jTrimColIndex,
                vEndColIndex, dStartColIndex, dEndColIndex, jStartColIndex, refPointColIndex;

        HeaderInfo(String[] header) {
            StringArrayIndexer headerParser = new StringArrayIndexer(header);
            this.countColIndex = headerParser.getIndexOf("cloneCount");
            this.freqColIndex = headerParser.getIndexOfS("cloneFraction");
            this.cdr3NtColIndex = headerParser.getIndexOf("nSeqCDR3");
            this.cdr3AaColIndex = headerParser.getIndexOf("aaSeqCDR3", false);
            this.vColIndex = headerParser.getIndexOf("allVHitsWithScore", false);
            this.dColIndex = headerParser.getIndexOf("allDHitsWithScore", false);
            this.jColIndex = headerParser.getIndexOf("allJHitsWithScore", false);
            this.cColIndex = headerParser.getIndexOf("allCHitsWithScore", false);
            this.vTrimColIndex = headerParser.getIndexOf("positionOfVEndTrimmed", false);
            this.dTrim5ColIndex = headerParser.getIndexOf("positionOfDBeginTrimmed", false);
            this.dTrim3ColIndex = headerParser.getIndexOf("positionOfDEndTrimmed", false);
            this.jTrimColIndex = headerParser.getIndexOf("positionOfJBeginTrimmed", false);
            this.vEndColIndex = headerParser.getIndexOf("positionOfVEnd", false);
            this.dStartColIndex = headerParser.getIndexOf("positionOfDBegin", false);
            this.dEndColIndex = headerParser.getIndexOf("positionOfDEnd", false);
            this.jStartColIndex = headerParser.getIndexOf("positionOfJBegin", false);
            this.refPointColIndex = headerParser.getIndexOf("refPoints");
        }
    }
}
