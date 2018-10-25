package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.*;
import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.mhc.MhcAlleleLibrary;
import com.milaboratory.mir.structure.MhcRegionType;

public final class MhcMarkupRealigner
        extends GenericMarkupRealigner<MhcAllele, AminoAcidSequence, MhcRegionType, PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType>> {

    public MhcMarkupRealigner(SequenceMapper<MhcAllele, AminoAcidSequence> mapper) {
        super(mapper, MhcAllele::getRegionMarkup, 0.75, 20);
    }

    public MhcMarkupRealigner(Iterable<MhcAllele> alleles,
                              SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(mapperFactory.create(alleles,
                x -> x.getRegionMarkup().getFullSequence()),
                MhcAllele::getRegionMarkup, 0.75, 20);
    }

    public MhcMarkupRealigner(MhcAlleleLibrary mhcAlleleLibrary,
                              SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(mapperFactory.create(mhcAlleleLibrary.getAlleles(),
                x -> x.getRegionMarkup().getFullSequence()),
                MhcAllele::getRegionMarkup, 0.75, 20);
    }
}
