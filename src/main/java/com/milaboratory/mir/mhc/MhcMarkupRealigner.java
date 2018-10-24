package com.milaboratory.mir.mhc;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.*;
import com.milaboratory.mir.mappers.markup.GenericMarkupRealigner;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.segment.SegmentWithMarkup;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

import java.util.Optional;

public class MhcMarkupRealigner
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
