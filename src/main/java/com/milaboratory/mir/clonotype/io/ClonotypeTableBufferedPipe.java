package com.milaboratory.mir.clonotype.io;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParser;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParserFactory;
import com.milaboratory.mir.pipe.BufferedPipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClonotypeTableBufferedPipe<T extends Clonotype> extends ClonotypeTablePipe<T>
        implements BufferedPipe<ClonotypeCall<T>> {
    public static final int DEFAULT_ENTITY_BUFFER_SIZE = 2048;
    private final int entityBufferSize;

    public ClonotypeTableBufferedPipe(InputStream inputStream,
                                      ClonotypeTableParserFactory<T> parserFactory) {
        this(inputStream, parserFactory, DEFAULT_READER_BUFFER_SIZE);
    }

    public ClonotypeTableBufferedPipe(InputStream inputStream,
                                      ClonotypeTableParserFactory<T> parserFactory,
                                      int readerBufferSize) {
        this(inputStream, parserFactory, readerBufferSize, DEFAULT_ENTITY_BUFFER_SIZE);
    }

    public ClonotypeTableBufferedPipe(InputStream inputStream, ClonotypeTableParserFactory<T> parserFactory,
                                      int readerBufferSize, int entityBufferSize) {
        super(inputStream, parserFactory, readerBufferSize);
        this.entityBufferSize = entityBufferSize;
    }


    @Override
    public ClonotypeCall<T> getPoison() {
        return ClonotypeCall.getDummy();
    }

    @Override
    public int getBufferSize() {
        return entityBufferSize;
    }
}