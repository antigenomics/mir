package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.StringArrayIndexer;
import com.antigenomics.mir.clonotype.ClonotypeCall;
import com.antigenomics.mir.clonotype.ClonotypeHelper;
import com.antigenomics.mir.clonotype.annotated.VdjdbClonotype;
import com.antigenomics.mir.clonotype.rearrangement.JunctionMarkup;
import com.antigenomics.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.antigenomics.mir.mhc.MhcAlelleLibraryBundle;
import com.antigenomics.mir.segment.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class VDJdbClonotypeParser implements ClonotypeTableParser<VdjdbClonotype> {
    private final HeaderInfo headerInfo;
    private final String[] header;
    private final AtomicInteger idCounter = new AtomicInteger();
    private final SegmentLibraryBundle segmentLibraryBundle;
    private final MhcAlelleLibraryBundle mhcAlelleLibraryBundle;

    public VDJdbClonotypeParser(String[] header,
                                SegmentLibraryBundle segmentLibraryBundle,
                                MhcAlelleLibraryBundle mhcAlelleLibraryBundle) {
        this.header = header;
        this.headerInfo = new HeaderInfo(header);
        this.segmentLibraryBundle = segmentLibraryBundle;
        this.mhcAlelleLibraryBundle = mhcAlelleLibraryBundle;
    }

    @Override
    public String[] getHeader() {
        return header.clone();
    }

    @Override
    public ClonotypeCall<VdjdbClonotype> parse(String[] splitLine) {
        int id = idCounter.incrementAndGet();
        int count = 1;
        double freq = 1.0;

        AminoAcidSequence cdr3Aa = new AminoAcidSequence(splitLine[headerInfo.cdr3AaColIndex]);
        NucleotideSequence cdr3Nt = ClonotypeHelper.clonotypeFromAa(cdr3Aa.toString()).getCdr3Nt();

        List<SegmentCall<VariableSegment>> vCalls = Collections.singletonList(getV(splitLine[headerInfo.vSegmColIndex], 1.0f));
        List<SegmentCall<JoiningSegment>> jCalls = Collections.singletonList(getJ(splitLine[headerInfo.jSegmColIndex], 1.0f));

        Gson gson = new Gson();

        Map fix = gson.fromJson(splitLine[headerInfo.cdr3FixColIndex], Map.class);

        int jStart = fix.containsKey("jStart") ? Double.valueOf((double) fix.get("jStart")).intValue() : -1;
        int vEnd = fix.containsKey("vEnd") ? Double.valueOf((double) fix.get("vEnd")).intValue() : -1;

        JunctionMarkup junctionMarkup = new JunctionMarkup(vEnd, jStart, -1, -1);

        HashMap<String, String> annotations = new HashMap<>() {{
            put("complex.id", splitLine[headerInfo.complexIdColIndex]);
            put("gene", splitLine[headerInfo.geneColIndex]);
            put("species", splitLine[headerInfo.geneColIndex]);
            put("mhc.a", splitLine[headerInfo.mhcAColIndex]);
            put("mhc.b", splitLine[headerInfo.mhcBColIndex]);
            put("mhc.class", splitLine[headerInfo.mhcClassColIndex]);
            put("antigen.epitope", splitLine[headerInfo.antigenEpitopeColIndex]);
            put("antigen.gene", splitLine[headerInfo.antigenGeneColIndex]);
            put("antigen.species", splitLine[headerInfo.antigenSpeciesColIndex]);
            put("reference.id", splitLine[headerInfo.referenceIdColIndex]);
            put("vdjdb.score", splitLine[headerInfo.vdjdbScoreColIndex]);
            put("web.method", splitLine[headerInfo.webMethodColIndex]);
            put("web.method.seq", splitLine[headerInfo.webMethodSeqColIndex]);
            put("web.cdr3fix.nc", splitLine[headerInfo.webCdr3FixNcColIndex]);
            put("web.cdr3fix.unmp", splitLine[headerInfo.webCdr3FixUnmpColIndex]);
        }};

        // todo: epitope -> aa sequence, mhc -> MhcAlleleWithSequence, gene -> Gene, species -> Species

        Arrays.asList(headerInfo.methodColIndex, headerInfo.metaColIndex, headerInfo.cdr3FixColIndex).forEach(index -> {
            HashMap<String, String> g = gson.fromJson(splitLine[index], new TypeToken<HashMap<String, String>>() {
            }.getType());

            g.forEach(annotations::put);
        });

        return new ClonotypeCall<>(id, count, freq,
                new ReadlessClonotypeImpl(cdr3Nt,
                        vCalls, dCalls, jCalls, cCalls,
                        annotations,
                        segmentTrimming, junctionMarkup,
                        cdr3Aa));
    }

    private static class HeaderInfo {
        final int complexIdColIndex, geneColIndex, cdr3AaColIndex,
                vSegmColIndex, jSegmColIndex,
                speciesColIndex, mhcAColIndex, mhcBColIndex, mhcClassColIndex,
                antigenEpitopeColIndex, antigenGeneColIndex, antigenSpeciesColIndex,
                referenceIdColIndex, methodColIndex, metaColIndex, cdr3FixColIndex,
                vdjdbScoreColIndex, webMethodColIndex, webMethodSeqColIndex,
                webCdr3FixNcColIndex, webCdr3FixUnmpColIndex;

        HeaderInfo(String[] header) {
            StringArrayIndexer headerParser = new StringArrayIndexer(header);
            this.complexIdColIndex = headerParser.getIndexOf("complex.id");
            this.geneColIndex = headerParser.getIndexOf("gene");
            this.cdr3AaColIndex = headerParser.getIndexOf("cdr3");
            this.vSegmColIndex = headerParser.getIndexOf("v.segm");
            this.jSegmColIndex = headerParser.getIndexOf("j.segm");
            this.speciesColIndex = headerParser.getIndexOf("species");
            this.mhcAColIndex = headerParser.getIndexOf("mhc.a");
            this.mhcBColIndex = headerParser.getIndexOf("mhc.b");
            this.mhcClassColIndex = headerParser.getIndexOf("mhc.class");
            this.antigenEpitopeColIndex = headerParser.getIndexOf("antigen.epitope");
            this.antigenGeneColIndex = headerParser.getIndexOf("antigen.gene");
            this.antigenSpeciesColIndex = headerParser.getIndexOf("antigen.species");
            this.referenceIdColIndex = headerParser.getIndexOf("reference.id");
            this.methodColIndex = headerParser.getIndexOf("method");
            this.metaColIndex = headerParser.getIndexOf("meta");
            this.cdr3FixColIndex = headerParser.getIndexOf("cdr3fix");
            this.vdjdbScoreColIndex = headerParser.getIndexOf("vdjdb.score");
            this.webMethodColIndex = headerParser.getIndexOf("web.method");
            this.webMethodSeqColIndex = headerParser.getIndexOf("web.method.seq");
            this.webCdr3FixNcColIndex = headerParser.getIndexOf("web.cdr3fix.nc");
            this.webCdr3FixUnmpColIndex = headerParser.getIndexOf("web.cdr3fix.unmp");
        }
    }
}
