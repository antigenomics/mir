package com.milaboratory.mir.clonotype.io;

import com.milaboratory.mir.pipe.BufferedPipe;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParser;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClonotypeTableIterator<T extends Clonotype> implements BufferedPipe<ClonotypeCall<T>> {
    public static final String TOKEN = "\t";
    private final BufferedReader bufferedReader;
    private final ClonotypeTableParser<T> parser;
    private final int entityBufferSize;
    private String line;

    public ClonotypeTableIterator(InputStream inputStream,
                                  ClonotypeTableParserFactory<T> parserFactory) {
        this(inputStream, parserFactory, 8192, 2048);
    }

    public ClonotypeTableIterator(InputStream inputStream,
                                  ClonotypeTableParserFactory<T> parserFactory,
                                  int readerBufferSize, int entityBufferSize) {
        this.entityBufferSize = entityBufferSize;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream), readerBufferSize);

        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (line != null) {
            this.parser = parserFactory.create(line.split(TOKEN));
        } else {
            this.parser = null; // empty file
        }
    }

    @Override
    public boolean hasNext() {
        try {
            if (line == null) {
                bufferedReader.close();
                return false;
            } else {
                line = bufferedReader.readLine();
                return line != null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClonotypeCall<T> next() {
        return parser.parse(line.split(TOKEN));
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