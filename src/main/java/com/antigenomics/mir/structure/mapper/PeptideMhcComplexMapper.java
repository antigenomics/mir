package com.antigenomics.mir.structure.mapper;

import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.VariableSegment;
import com.antigenomics.mir.structure.PeptideChain;
import com.antigenomics.mir.structure.PeptideMhcComplex;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mhc.MhcAllele;
import com.antigenomics.mir.structure.TcrPeptideMhcComplex;
import com.antigenomics.mir.structure.pdb.Structure;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;

public class PeptideMhcComplexMapper {
    private final AntigenReceptorMapper antigenReceptorMapper;
    private final MhcComplexMapper mhcComplexMapper;

    // todo: rewrite simple mapper to kmer based
    public static final PeptideMhcComplexMapper DEFAULT = new PeptideMhcComplexMapper(
            new SimpleExhaustiveMapperFactory<>(
                    AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62)
            )
    );

    public PeptideMhcComplexMapper(SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        this(DefaultComplexMapperLibrary.INSTANCE.getMhcAlleles(),
                DefaultComplexMapperLibrary.INSTANCE.getVariableSegments(),
                DefaultComplexMapperLibrary.INSTANCE.getJoiningSegments(),
                mapperFactory);
    }

    public PeptideMhcComplexMapper(Collection<MhcAllele> mhcAlleles,
                                   Collection<VariableSegment> variableSegments,
                                   Collection<JoiningSegment> joiningSegments,
                                   SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        this.antigenReceptorMapper = new AntigenReceptorMapper(
                variableSegments, joiningSegments, mapperFactory
        );
        this.mhcComplexMapper = new MhcComplexMapper(
                mhcAlleles, mapperFactory
        );
    }

    public Optional<TcrPeptideMhcComplex> map(Structure structure) {
        var chains = structure.getChains();

        //if (chains.size() != 5) {
        //    throw new IllegalArgumentException("Structure must have exactly 5 chains");
        //}

        var tcrComplexOpt = antigenReceptorMapper.map(chains);

        if (!tcrComplexOpt.isPresent()) {
            return Optional.empty();
        }

        var mhcComplexOpt = mhcComplexMapper.map(chains);

        if (!mhcComplexOpt.isPresent()) {
            return Optional.empty();
        }

        var antigenReceptorComplex = tcrComplexOpt.get();
        var mhcComplex = mhcComplexOpt.get();

        var mappedChains = new HashSet<Character>();

        mappedChains.add(antigenReceptorComplex.getFirstChain().getStructureChain().getChainIdentifier());
        mappedChains.add(antigenReceptorComplex.getSecondChain().getStructureChain().getChainIdentifier());
        mappedChains.add(mhcComplex.getFirstChain().getStructureChain().getChainIdentifier());
        mappedChains.add(mhcComplex.getSecondChain().getStructureChain().getChainIdentifier());

        if (mappedChains.size() != 4) {
            return Optional.empty();
        }

        var antigenChainOpt = chains.stream()
                .filter(x -> !mappedChains.contains(x.getChainIdentifier()))
                .min(Comparator.comparingInt(x -> x.getSequence().size()));

        PeptideChain peptide;
        if (antigenChainOpt.isPresent()) {
            var antigenChain = antigenChainOpt.get();
            peptide = new PeptideChain(antigenChain.getSequence(),
                    antigenChain);
        } else {
            peptide = PeptideChain.DUMMY;
        }


        return Optional.of(new TcrPeptideMhcComplex(
                antigenReceptorComplex,
                new PeptideMhcComplex(peptide, mhcComplex),
                structure
        ));
    }
}
