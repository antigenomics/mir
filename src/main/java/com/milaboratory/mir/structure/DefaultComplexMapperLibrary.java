package com.milaboratory.mir.structure;

import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.mhc.MhcAlleleLibraryUtils;
import com.milaboratory.mir.mhc.MhcClassType;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;

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
