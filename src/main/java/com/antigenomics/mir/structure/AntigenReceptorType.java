package com.antigenomics.mir.structure;

import com.antigenomics.mir.segment.Gene;

import java.util.HashMap;
import java.util.Map;

public enum AntigenReceptorType {
    TRAB, TRGD, IGLH, IGKH, CHIMERIC;


    private static Map<String, AntigenReceptorType> mapping = new HashMap<>();

    static {
        mapping.put("TRA TRB", TRAB);
        mapping.put("TRB TRA", TRAB);
        mapping.put("TRG TRD", TRGD);
        mapping.put("TRD TRG", TRGD);
        mapping.put("IGL IGH", IGLH);
        mapping.put("IGH IGL", IGLH);
        mapping.put("IGK IGH", IGKH);
        mapping.put("IGH IGK", IGKH);
    }

    public static AntigenReceptorType combine(Gene a, Gene b) {
        return mapping.getOrDefault(a + " " + b, CHIMERIC);
    }
}
