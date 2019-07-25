package com.antigenomics.mir.structure.mapper;

import com.antigenomics.mir.mhc.MhcAlleleWithSequence;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.MhcMarkupRealigner;
import com.antigenomics.mir.mhc.MhcClassType;
import com.antigenomics.mir.structure.MhcRegionType;
import com.antigenomics.mir.structure.MhcChain;
import com.antigenomics.mir.structure.MhcComplex;
import com.antigenomics.mir.structure.pdb.Chain;

import java.util.Collection;

public class MhcComplexMapper extends AbstractHeterodimerMapper<MhcRegionType, MhcChain, MhcComplex> {
    private final Collection<MhcAlleleWithSequence> alleleList;

    public MhcComplexMapper(Collection<MhcAlleleWithSequence> alleleList, SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(new MhcMarkupRealigner(alleleList, mapperFactory), MhcComplex::new);
        this.alleleList = alleleList;
    }

    @Override
    protected MhcChain createChain(ChainMapperResult<MhcRegionType> result) {
        return new MhcChain((MhcAlleleWithSequence) result.getResult().getPayload(),
                result.getResult().getMarkup(),
                result.getChain());
    }

    @Override
    protected MhcChain createDummy(ChainMapperResult<MhcRegionType> template) {
        var allele = (MhcAlleleWithSequence) template.getResult().getPayload();
        if (allele.getMhcClassType() == MhcClassType.MHCI) {
            var b2mOpt = alleleList.stream().filter(x ->
                    x.getMhcClassType() == MhcClassType.MHCI &&
                            x.getSpecies() == allele.getSpecies() &&
                            x.getId().equals("B2M")).findFirst();

            if (!b2mOpt.isPresent()) {
                throw new RuntimeException("No B2M allele in mapper");
            }
            var b2m = b2mOpt.get();
            return new MhcChain(
                    b2m,
                    new ArrayBasedSequenceRegionMarkup<>(
                            AminoAcidSequence.EMPTY,
                            new int[MhcRegionType.values().length + 1],
                            MhcRegionType.class),
                    Chain.DUMMY
            );
        }
        throw new UnsupportedOperationException("Cannot create dummy MHC chain for class II / " +
                "dummy MHC class I alpha chain");
    }
}
