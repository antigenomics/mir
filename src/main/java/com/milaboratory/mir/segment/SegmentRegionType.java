package com.milaboratory.mir.segment;

import com.milaboratory.mir.structure.AntigenReceptorRegionType;

public enum SegmentRegionType {
    FR1NT(AntigenReceptorRegionType.FR1, false),
    CDR1NT(AntigenReceptorRegionType.CDR1, false),
    FR2NT(AntigenReceptorRegionType.FR2, false),
    CDR2NT(AntigenReceptorRegionType.CDR2, false),
    FR3NT(AntigenReceptorRegionType.FR3, false),
    CDR3NT(AntigenReceptorRegionType.CDR3, false),
    FR4NT(AntigenReceptorRegionType.FR4, false),
    FR1AA(AntigenReceptorRegionType.FR1, true),
    CDR1AA(AntigenReceptorRegionType.CDR1, true),
    FR2AA(AntigenReceptorRegionType.FR2, true),
    CDR2AA(AntigenReceptorRegionType.CDR2, true),
    FR3AA(AntigenReceptorRegionType.FR3, true),
    CDR3AA(AntigenReceptorRegionType.CDR3, true),
    FR4AA(AntigenReceptorRegionType.FR4, true);

    private final AntigenReceptorRegionType regionType;
    private final boolean aminoAcid;

    SegmentRegionType(AntigenReceptorRegionType regionType, boolean aminoAcid) {
        this.regionType = regionType;
        this.aminoAcid = aminoAcid;
    }

    public AntigenReceptorRegionType getRegionType() {
        return regionType;
    }

    public boolean isAminoAcid() {
        return aminoAcid;
    }
}
