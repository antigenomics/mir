package com.antigenomics.mir.graph;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.pipe.Pipe;

import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphUtils {
    public static <T extends Clonotype, V extends ClonotypeEdge<T>>
    Stream<V> flatMap(Pipe<T> clonotypes1, Pipe<T> clonotypes2,
                      BiFunction<T, T, V> mapper, boolean parallel) {
        var clonotype2Lst = clonotypes2.stream().collect(Collectors.toList());

        return (parallel ? clonotypes1.stream() : clonotypes1.parallelStream())
                .flatMap(from -> clonotype2Lst.stream().map(to -> mapper.apply(from, to)));
    }
}
