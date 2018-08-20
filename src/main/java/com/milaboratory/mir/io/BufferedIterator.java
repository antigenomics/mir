package com.milaboratory.mir.io;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferedIterator<T> implements Iterator<T> {
    private final LinkedBlockingQueue<T> queue;
    private final T poison;
    private volatile T next;
    private final int bufferSize;

    public BufferedIterator(Iterator<T> iterator,
                            T poison,
                            int bufferSize) {
        this.queue = new LinkedBlockingQueue<>(bufferSize);
        this.poison = poison;
        this.bufferSize = bufferSize;

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
