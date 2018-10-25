package com.milaboratory.mir.structure.mapper;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.mappers.markup.MhcMarkupRealigner;
import com.milaboratory.mir.structure.MhcRegionType;
import com.milaboratory.mir.structure.MhcChain;
import com.milaboratory.mir.structure.MhcComplex;

import java.util.Collection;

public class MhcComplexMapper extends AbstractHeterodimerMapper<MhcRegionType, MhcChain, MhcComplex> {
    public MhcComplexMapper(Collection<MhcAllele> alleleList, SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(new MhcMarkupRealigner(alleleList, mapperFactory), MhcComplex::new);
    }

    public MhcComplexMapper(MhcMarkupRealigner mhcMarkupRealigner) {
        super(mhcMarkupRealigner, MhcComplex::new);
    }

    @Override
    protected MhcChain createChain(ChainMapperResult<MhcRegionType> result) {
        return new MhcChain((MhcAllele) result.getResult().getPayload(),
                result.getResult().getMarkup(),
                result.getChain());
    }
}
