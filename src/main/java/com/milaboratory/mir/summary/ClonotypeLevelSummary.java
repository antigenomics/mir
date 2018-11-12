package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;

import java.util.function.Consumer;

public interface ClonotypeLevelSummary<T extends Clonotype> extends Consumer<T> {
}
