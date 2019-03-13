package com.antigenomics.mir.mappers.markup;

import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.structure.MhcRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.*;
import com.antigenomics.mir.mhc.MhcAllele;
import com.antigenomics.mir.mhc.MhcAlleleLibrary;

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
