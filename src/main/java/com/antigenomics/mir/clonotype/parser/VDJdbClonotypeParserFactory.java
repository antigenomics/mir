package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.clonotype.annotated.VdjdbClonotype;
import com.antigenomics.mir.mhc.MhcAlleleLibraryUtils;
import com.antigenomics.mir.segment.SegmentLibraryUtils;

public class VDJdbClonotypeParserFactory implements ClonotypeTableParserFactory<VdjdbClonotype> {
    @Override
    public VDJdbClonotypeParser create(String[] header) {
        return new VDJdbClonotypeParser(header,
                SegmentLibraryUtils.getBuiltinTcrAbLibraryBundle(),
                MhcAlleleLibraryUtils.getMockMhcLibraryBundle());
    }
}
