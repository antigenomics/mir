package com.milaboratory.mir.pipe;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface BufferedPipe<T> extends SinglePassPipe<T> {
    T getPoison();

    int getBufferSize();

    @Override
    default Iterable<T> iterable() {
        return () -> new BufferedIterator<>(this, getPoison(), getBufferSize());
    }

    // This override appears to be required for anything related to operations with buffers that
    // are concurrently filled from other thread
    @Override
    default Stream<T> parallelStream() {
        /*
        TODO
         Despite their obvious utility in parallel algorithms, spliterators are not expected to be thread-safe;
         instead, implementations of parallel algorithms using spliterators should ensure that the spliterator
         is only used by one thread at a time. This is generally easy to attain via serial thread-confinement,
         which often is a natural consequence of typical parallel algorithms that work by recursive decomposition.
         */

        var spliterator = Spliterators.spliterator(this,
                // If set to > 0 will get stuck when size of input is less than 'size'
                0,
                // Report characteristics: we will concurrently modify underlying supplier
                Spliterator.CONCURRENT | Spliterator.NONNULL | Spliterator.ORDERED);

        return StreamSupport.stream(spliterator, true);
    }
}
