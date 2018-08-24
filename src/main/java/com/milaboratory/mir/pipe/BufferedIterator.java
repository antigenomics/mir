package com.milaboratory.mir.pipe;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public final class BufferedIterator<T> implements Iterator<T> {
    private final LinkedBlockingQueue<T> queue;
    private final T poison;
    private T next;
    private final int bufferSize;

    public BufferedIterator(Iterator<T> iterator,
                            T poison,
                            int bufferSize) {
        this.queue = new LinkedBlockingQueue<>(bufferSize);
        this.poison = poison;
        this.bufferSize = bufferSize;

        // TODO: Consider running this thread in supplied or common fork join pool
        new Thread(() -> {
            while (iterator.hasNext()) {
                try {
                    queue.put(iterator.next());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                queue.put(poison);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public int getBufferSize() {
        return bufferSize;
    }

    @Override
    public boolean hasNext() {
        try {
            next = queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return next != poison;
    }

    @Override
    public T next() {
        return next;
    }
}
