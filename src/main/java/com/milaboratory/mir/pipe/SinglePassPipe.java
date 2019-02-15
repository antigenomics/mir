package com.milaboratory.mir.pipe;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface SinglePassPipe<T> extends Iterator<T>, Pipe<T> {
    // todo: ensure we only call this once?
    default Iterable<T> iterable() {
        return () -> this;
    }

    @Override
    default Stream<T> stream() {
        return StreamSupport.stream(iterable().spliterator(), false);
    }

    @Override
    default Stream<T> parallelStream() {
        /*
        TODO
         Despite their obvious utility in parallel algorithms, spliterators are not expected to be thread-safe;
         instead, implementations of parallel algorithms using spliterators should ensure that the spliterator
         is only used by one thread at a time. This is generally easy to attain via serial thread-confinement,
         which often is a natural consequence of typical parallel algorithms that work by recursive decomposition.
         */
        return StreamSupport.stream(iterable().spliterator(), true);
    }
}
