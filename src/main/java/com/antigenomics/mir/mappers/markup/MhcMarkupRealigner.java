package com.antigenomics.mir.mappers.markup;

import com.antigenomics.mir.mappers.SequenceMapper;
import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.mhc.MhcAlleleWithSequence;
import com.antigenomics.mir.structure.MhcRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mhc.MhcAlleleLibraryImpl;

public final class MhcMarkupRealigner
        extends GenericMarkupRealigner<MhcAlleleWithSequence, AminoAcidSequence, MhcRegionType, PrecomputedSequenceRegionMarkup<AminoAcidSequence, MhcRegionType>> {

    public MhcMarkupRealigner(SequenceMapper<MhcAlleleWithSequence, AminoAcidSequence> mapper) {
        super(mapper, MhcAlleleWithSequence::getRegionMarkup, 0.75, 20);
    }

    public MhcMarkupRealigner(Iterable<MhcAlleleWithSequence> alleles,
                              SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(mapperFactory.create(alleles,
                x -> x.getRegionMarkup().getFullSequence()),
                MhcAlleleWithSequence::getRegionMarkup, 0.75, 20);
    }

    public MhcMarkupRealigner(MhcAlleleLibraryImpl<MhcAlleleWithSequence> mhcAlleleLibrary,
                              SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(mapperFactory.create(mhcAlleleLibrary.getAlleles(),
                x -> x.getRegionMarkup().getFullSequence()),
                MhcAlleleWithSequence::getRegionMarkup, 0.75, 20);
    }
}
