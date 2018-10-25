package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.structure.pdb.Structure;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class ComplexMapper {
    private final AntigenReceptorMapper antigenReceptorMapper;
    private final MhcComplexMapper mhcComplexMapper;

    public ComplexMapper(SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        this(DefaultComplexMapperLibrary.INSTANCE.getMhcAlleles(),
                DefaultComplexMapperLibrary.INSTANCE.getVariableSegments(),
                DefaultComplexMapperLibrary.INSTANCE.getJoiningSegments(),
                mapperFactory);
    }

    public ComplexMapper(Collection<MhcAllele> mhcAlleles,
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

        if (chains.size() != 5) {
            throw new IllegalArgumentException("Structure must have exactly 5 chains");
        }

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

        var antigenChain = chains.stream()
                .filter(x -> !mappedChains.contains(x.getChainIdentifier()))
                .findFirst().get();

        var peptide = new PeptideChain(antigenChain.getSequence(),
                antigenChain);

        return Optional.of(new TcrPeptideMhcComplex(
                antigenReceptorComplex,
                new PeptideMhcComplex(peptide, mhcComplex)
        ));
    }
}
