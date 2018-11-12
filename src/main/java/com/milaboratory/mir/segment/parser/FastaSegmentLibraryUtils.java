package com.milaboratory.mir.segment.parser;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.io.sequence.fasta.FastaReader;
import com.milaboratory.core.io.sequence.fasta.FastaRecord;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SegmentMarkupRealignerNt;
import com.milaboratory.mir.rearrangement.parser.MuruganModelParserUtils;
import com.milaboratory.mir.segment.*;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class FastaSegmentLibraryUtils {
    public static final SimpleExhaustiveMapperFactory<NucleotideSequence> DEFAULT_MAPPER_FACTORY =
            new SimpleExhaustiveMapperFactory<>(
                    // todo: separate scoring for V/J?
                    AffineGapAlignmentScoring.getNucleotideBLASTScoring());

    private static List<FastaRecord<NucleotideSequence>> readMuruganAsFasta(InputStream inputStream) {
        boolean geneChoiceChunk = false;
        var records = new ArrayList<FastaRecord<NucleotideSequence>>();

        try (var br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().startsWith("#genechoice")) { // chunk header
                    geneChoiceChunk = true;
                } else if (geneChoiceChunk && line.startsWith("%")) {
                    var splitLine = line
                            .substring(1) // remove prefix
                            .split(";");

                    records.add(new FastaRecord<>(records.size(),
                            splitLine[0], new NucleotideSequence(splitLine[1])));
                } else {
                    geneChoiceChunk = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    public static SegmentLibrary loadMurugan(InputStream inputStream,
                                             SegmentLibrary template,
                                             SequenceMapperFactory<NucleotideSequence> mapperFactory) {
        return createUsingTemplate(readMuruganAsFasta(inputStream), template, mapperFactory);
    }

    public static SegmentLibrary loadMurugan(InputStream inputStream,
                                             SegmentLibrary template) {
        return createUsingTemplate(readMuruganAsFasta(inputStream), template, DEFAULT_MAPPER_FACTORY);
    }

    public static SegmentLibrary loadMurugan(InputStream inputStream,
                                             Species species, Gene gene) {
        return createUsingTemplate(readMuruganAsFasta(inputStream),
                MigecSegmentLibraryUtils.getLibraryFromResources(species, gene));
    }

    public static SegmentLibrary loadMurugan(Species species, Gene gene) {
        try {
            return loadMurugan(
                    MuruganModelParserUtils.getResourceStream(species, gene).getParams(),
                    species, gene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SegmentLibrary load(InputStream inputStream,
                                      SegmentLibrary template,
                                      SequenceMapperFactory<NucleotideSequence> mapperFactory) {
        var records = new ArrayList<FastaRecord<NucleotideSequence>>();
        // todo: wrappers for ports
        try (var reader = new FastaReader<>(inputStream, NucleotideSequence.ALPHABET)) {
            FastaRecord<NucleotideSequence> record;
            while ((record = reader.take()) != null) {
                records.add(record);
            }
        }
        return createUsingTemplate(records, template, mapperFactory);
    }

    public static SegmentLibrary load(InputStream inputStream, Species species, Gene gene) {
        return load(inputStream, MigecSegmentLibraryUtils.getLibraryFromResources(species, gene));
    }

    public static SegmentLibrary load(InputStream inputStream,
                                      SegmentLibrary template) {
        return load(inputStream, template, DEFAULT_MAPPER_FACTORY);
    }

    public static SegmentLibrary createUsingTemplate(Collection<FastaRecord<NucleotideSequence>> records,
                                                     Species species, Gene gene) {
        return createUsingTemplate(records,
                MigecSegmentLibraryUtils.getLibraryFromResources(species, gene));
    }

    public static SegmentLibrary createUsingTemplate(Collection<FastaRecord<NucleotideSequence>> records,
                                                     SegmentLibrary template) {
        return createUsingTemplate(records, template, DEFAULT_MAPPER_FACTORY);
    }

    public static SegmentLibrary createUsingTemplate(Collection<FastaRecord<NucleotideSequence>> records,
                                                     SegmentLibrary template,
                                                     SequenceMapperFactory<NucleotideSequence> mapperFactory) {
        Map<String, VariableSegment> variableSegmentMap = new ConcurrentHashMap<>();
        Map<String, DiversitySegment> diversitySegmentMap = new ConcurrentHashMap<>();
        Map<String, JoiningSegment> joiningSegmentMap = new ConcurrentHashMap<>();
        Map<String, ConstantSegment> constantSegmentMap = new ConcurrentHashMap<>();

        var variableSegmentMapper = new SegmentMarkupRealignerNt<>(template.getAllVMajor(), mapperFactory);
        var joiningSegmentMapper = new SegmentMarkupRealignerNt<>(template.getAllJMajor(), mapperFactory);

        String vToken = template.getGene().getCode() + SegmentType.V.getCode();
        String dToken = template.getGene().getCode() + SegmentType.D.getCode();
        String jToken = template.getGene().getCode() + SegmentType.J.getCode();
        String cToken = template.getGene().getCode() + SegmentType.C.getCode();

        records.stream().parallel().forEach(
                record -> {
                    String segmentName = record.getDescription().split("[ \t]+")[0].toUpperCase();
                    boolean majorAllele = !segmentName.matches("^.+\\*\\d\\d$") || // all non conventional = major
                            (segmentName.endsWith("*00") || segmentName.endsWith("*01"));

                    NucleotideSequence sequence = record.getSequence();

                    if (!sequence.containsWildcards()) {
                        if (segmentName.contains(vToken)) {
                            variableSegmentMapper
                                    .recomputeMarkup(sequence)
                                    .ifPresent(vRes ->
                                            variableSegmentMap.put(segmentName,
                                                    vFromMarkup(segmentName,
                                                            vRes.getMarkup().getFullSequence(),
                                                            vRes.getMarkup(), majorAllele
                                                    )));
                        } else if (segmentName.contains(jToken)) {
                            joiningSegmentMapper
                                    .recomputeMarkup(sequence)
                                    .ifPresent(jRes ->
                                            joiningSegmentMap.put(segmentName,
                                                    jFromMarkup(segmentName,
                                                            jRes.getMarkup().getFullSequence(),
                                                            jRes.getMarkup(), majorAllele
                                                    )));
                        } else if (segmentName.contains(dToken)) {
                            diversitySegmentMap.put(segmentName, new CachedDiversitySegment(segmentName,
                                    sequence, majorAllele));
                        } else if (segmentName.contains(cToken)) {
                            constantSegmentMap.put(segmentName, new CachedConstantSegment(segmentName,
                                    sequence, majorAllele));
                        }
                    }
                }
        );

        return new SegmentLibraryImpl(template.getSpecies(), template.getGene(),
                variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap);
    }


    private static JoiningSegmentImpl jFromMarkup
            (String id,
             NucleotideSequence germlineNt,
             PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> markup,
             boolean majorAllele) {
        var cdr3Markup = markup.getRegion(AntigenReceptorRegionType.CDR3);
        return new CachedJoiningSegment(id, germlineNt,
                cdr3Markup.getEnd() - 4,
                majorAllele);
    }


    private static VariableSegmentImpl vFromMarkup
            (String id,
             NucleotideSequence germlineNt,
             PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> markup,
             boolean majorAllele) {
        var cdr1Markup = markup.getRegion(AntigenReceptorRegionType.CDR1);
        var cdr2Markup = markup.getRegion(AntigenReceptorRegionType.CDR2);
        var cdr3Markup = markup.getRegion(AntigenReceptorRegionType.CDR3);
        return new CachedVariableSegment(id, germlineNt,
                cdr1Markup.getStart(), cdr1Markup.getEnd(),
                cdr2Markup.getStart(), cdr2Markup.getEnd(),
                cdr3Markup.getStart() + 3,
                majorAllele);
    }
}
