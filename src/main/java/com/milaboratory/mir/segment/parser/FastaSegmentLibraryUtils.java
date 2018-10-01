package com.milaboratory.mir.segment.parser;

import com.milaboratory.core.io.sequence.fasta.FastaRecord;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mappers.markup.SegmentMarkupRealignerNt;
import com.milaboratory.mir.segment.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class FastaSegmentLibraryUtils {
    public static SegmentLibrary createUsingTemplate(Collection<FastaRecord<NucleotideSequence>> records,
                                                     SegmentLibrary template,
                                                     SequenceMapperFactory<NucleotideSequence> mapperFactory) {
        Map<String, VariableSegment> variableSegmentMap = new HashMap<>();
        Map<String, DiversitySegment> diversitySegmentMap = new HashMap<>();
        Map<String, JoiningSegment> joiningSegmentMap = new HashMap<>();
        Map<String, ConstantSegment> constantSegmentMap = new HashMap<>();

        var variableSegmentMapper = new SegmentMarkupRealignerNt<>(template.getAllVMajor(),
                mapperFactory, true);
        var joiningSegmentMapper = new SegmentMarkupRealignerNt<>(template.getAllJMajor(),
                mapperFactory, true);

        String vToken = template.getGene().getCode() + SegmentType.V.getCode();
        String dToken = template.getGene().getCode() + SegmentType.D.getCode();
        String jToken = template.getGene().getCode() + SegmentType.J.getCode();
        String cToken = template.getGene().getCode() + SegmentType.C.getCode();

        records.stream().parallel().forEach(
                record -> {
                    String segmentName = record.getDescription().split("[ \t]+")[0].toUpperCase();

                    if (segmentName.contains(vToken)) {
                        variableSegmentMapper
                                .recomputeMarkup(record.getSequence())
                                .ifPresent(markup ->
                                        variableSegmentMap.put(segmentName,
                                                VariableSegmentImpl.fromMarkup(segmentName,
                                                        markup.getFullSequence(),
                                                        markup, true
                                                )));
                    } else if (segmentName.contains(jToken)) {
                        joiningSegmentMapper
                                .recomputeMarkup(record.getSequence())
                                .ifPresent(markup ->
                                        joiningSegmentMap.put(segmentName,
                                                JoiningSegmentImpl.fromMarkup(segmentName,
                                                        markup.getFullSequence(),
                                                        markup, true
                                                )));
                    } else if (segmentName.contains(dToken)) {
                        diversitySegmentMap.put(segmentName, new DiversitySegmentImpl(segmentName,
                                record.getSequence(), true));
                    } else if (segmentName.contains(cToken)) {
                        constantSegmentMap.put(segmentName, new ConstantSegmentImpl(segmentName,
                                record.getSequence(), true));
                    }
                }
        );

        return new SegmentLibraryImpl(template.getSpecies(), template.getGene(),
                variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap);
    }
}
