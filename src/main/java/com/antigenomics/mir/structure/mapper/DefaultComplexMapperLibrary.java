package com.antigenomics.mir.structure.mapper;

import com.antigenomics.mir.mhc.MhcAllele;
import com.antigenomics.mir.mhc.MhcAlleleLibraryUtils;
import com.antigenomics.mir.mhc.MhcClassType;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.Species;
import com.antigenomics.mir.segment.VariableSegment;
import com.antigenomics.mir.segment.parser.MigecSegmentLibraryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultComplexMapperLibrary {
    public static final DefaultComplexMapperLibrary INSTANCE = new DefaultComplexMapperLibrary();

    private final List<MhcAllele> mhcAlleles = new ArrayList<>();
    private final List<VariableSegment> variableSegments = new ArrayList<>();
    private final List<JoiningSegment> joiningSegments = new ArrayList<>();

    private DefaultComplexMapperLibrary() {
        for (Species species : Arrays.asList(Species.Human, Species.Mouse)) {
            for (MhcClassType mhcClassType : Arrays.asList(MhcClassType.MHCI, MhcClassType.MHCII)) {
                mhcAlleles.addAll(MhcAlleleLibraryUtils.load(species, mhcClassType).getAlleles());
            }
            for (Gene gene : Arrays.asList(Gene.TRA, Gene.TRB)) {
                var segmLib = MigecSegmentLibraryUtils.getLibraryFromResources(species, gene);
                variableSegments.addAll(segmLib.getAllVMajor());
                joiningSegments.addAll(segmLib.getAllJMajor());
            }
        }
    }

    public List<MhcAllele> getMhcAlleles() {
        return Collections.unmodifiableList(mhcAlleles);
    }

    public List<VariableSegment> getVariableSegments() {
        return Collections.unmodifiableList(variableSegments);
    }

    public List<JoiningSegment> getJoiningSegments() {
        return Collections.unmodifiableList(joiningSegments);
    }
}
