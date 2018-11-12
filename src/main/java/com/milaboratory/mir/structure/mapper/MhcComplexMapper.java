package com.milaboratory.mir.structure.mapper;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.mappers.markup.MhcMarkupRealigner;
import com.milaboratory.mir.mhc.MhcChainType;
import com.milaboratory.mir.mhc.MhcClassType;
import com.milaboratory.mir.structure.MhcRegionType;
import com.milaboratory.mir.structure.MhcChain;
import com.milaboratory.mir.structure.MhcComplex;
import com.milaboratory.mir.structure.pdb.Chain;

import java.util.Collection;

public class MhcComplexMapper extends AbstractHeterodimerMapper<MhcRegionType, MhcChain, MhcComplex> {
    private final Collection<MhcAllele> alleleList;

    public MhcComplexMapper(Collection<MhcAllele> alleleList, SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(new MhcMarkupRealigner(alleleList, mapperFactory), MhcComplex::new);
        this.alleleList = alleleList;
    }

    @Override
    protected MhcChain createChain(ChainMapperResult<MhcRegionType> result) {
        return new MhcChain((MhcAllele) result.getResult().getPayload(),
                result.getResult().getMarkup(),
                result.getChain());
    }

    @Override
    protected MhcChain createDummy(ChainMapperResult<MhcRegionType> template) {
        var allele = (MhcAllele) template.getResult().getPayload();
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
        throw new UnsupportedOperationException("Cannot createBins dummy MHC chain for class II / " +
                "dummy MHC class I alpha chain");
    }
}
