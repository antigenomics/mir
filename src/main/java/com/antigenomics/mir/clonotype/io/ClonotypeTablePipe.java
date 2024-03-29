package com.antigenomics.mir.clonotype.io;

import com.antigenomics.mir.clonotype.parser.ClonotypeTableParser;
import com.antigenomics.mir.clonotype.parser.ClonotypeTableParserFactory;
import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;
import com.antigenomics.mir.pipe.SinglePassPipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClonotypeTablePipe<T extends Clonotype>
        implements SinglePassPipe<ClonotypeCall<T>>, AutoCloseable {
    public static final String TOKEN = "\t";
    public static final int DEFAULT_READER_BUFFER_SIZE = 8192;

    private final BufferedReader bufferedReader;
    private final ClonotypeTableParser<T> parser;
    private String line;

    public ClonotypeTablePipe(InputStream inputStream,
                              ClonotypeTableParserFactory<T> parserFactory) {
        this(inputStream, parserFactory, DEFAULT_READER_BUFFER_SIZE);
    }

    public ClonotypeTablePipe(InputStream inputStream,
                              ClonotypeTableParserFactory<T> parserFactory,
                              int readerBufferSize) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClonotypeCall<T> next() {
        return parser.parse(line.split(TOKEN));
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }
}