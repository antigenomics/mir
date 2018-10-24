package com.milaboratory.mir.segment.parser;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.io.sequence.fasta.FastaReader;
import com.milaboratory.core.io.sequence.fasta.FastaRecord;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.mappers.markup.SegmentMarkupRealignerNt;
import com.milaboratory.mir.rearrangement.parser.MuruganModelParserUtils;
import com.milaboratory.mir.segment.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
        Map<String, VariableSegment> variableSegmentMap = new HashMap<>();
        Map<String, DiversitySegment> diversitySegmentMap = new HashMap<>();
        Map<String, JoiningSegment> joiningSegmentMap = new HashMap<>();
        Map<String, ConstantSegment> constantSegmentMap = new HashMap<>();

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
                                    .ifPresent(markup ->
                                            variableSegmentMap.put(segmentName,
                                                    VariableSegmentImpl.fromMarkup(segmentName,
                                                            markup.getFullSequence(),
                                                            markup, majorAllele
                                                    )));
                        } else if (segmentName.contains(jToken)) {
                            joiningSegmentMapper
                                    .recomputeMarkup(sequence)
                                    .ifPresent(markup ->
                                            joiningSegmentMap.put(segmentName,
                                                    JoiningSegmentImpl.fromMarkup(segmentName,
                                                            markup.getFullSequence(),
                                                            markup, majorAllele
                                                    )));
                        } else if (segmentName.contains(dToken)) {
                            diversitySegmentMap.put(segmentName, new DiversitySegmentImpl(segmentName,
                                    sequence, majorAllele));
                        } else if (segmentName.contains(cToken)) {
                            constantSegmentMap.put(segmentName, new ConstantSegmentImpl(segmentName,
                                    sequence, majorAllele));
                        }
                    }
                }
        );

        return new SegmentLibraryImpl(template.getSpecies(), template.getGene(),
                variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap);
    }
}
