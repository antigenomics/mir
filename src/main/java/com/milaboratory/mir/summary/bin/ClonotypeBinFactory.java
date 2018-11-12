package com.milaboratory.mir.summary.bin;

import com.milaboratory.mir.clonotype.Clonotype;

import java.util.Collection;

public interface ClonotypeBinFactory<T extends Clonotype> {
    Collection<ClonotypeBin> create(T clonotype);
}
