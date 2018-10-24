package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mhc.MhcAlleleLibrary;
import com.milaboratory.mir.mappers.markup.MhcMarkupRealigner;

public class MhcChainMapper {
    private final MhcMarkupRealigner mhcMarkupRealigner;

    public MhcChainMapper(MhcMarkupRealigner mhcMarkupRealigner) {
        this.mhcMarkupRealigner = mhcMarkupRealigner;
    }

    public MhcChainMapper(MhcAlleleLibrary alleles,
                          SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        this.mhcMarkupRealigner = new MhcMarkupRealigner(alleles.getAlleles(),
                mapperFactory);
    }


}
