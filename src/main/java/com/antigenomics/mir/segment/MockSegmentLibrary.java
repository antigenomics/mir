package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class MockSegmentLibrary extends SegmentLibraryImpl {
    public MockSegmentLibrary(Species species, Gene gene) {
        super(species, gene,
                new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>(),
                new HashMap<>(),
                true
        );
    }

    @Override
    public VariableSegment getV(String id) {
        return variableSegmentMap.computeIfAbsent(id, VariableSegmentImpl::mock);
    }

    @Override
    public JoiningSegment getJ(String id) {
        return joiningSegmentMap.computeIfAbsent(id, JoiningSegmentImpl::mock);
    }

    @Override
    public DiversitySegment getD(String id) {
        return diversitySegmentMap.computeIfAbsent(id, DiversitySegmentImpl::mock);
    }

    @Override
    public ConstantSegment getC(String id) {
        return constantSegmentMap.computeIfAbsent(id, ConstantSegmentImpl::mock);
    }

    @Override
    public VariableSegment getVMajor(String id) {
        return getV(id);
    }

    @Override
    public JoiningSegment getJMajor(String id) {
        return getJ(id);
    }

    @Override
    public DiversitySegment getDMajor(String id) {
        return getD(id);
    }

    @Override
    public ConstantSegment getCMajor(String id) {
        return getC(id);
    }
}
