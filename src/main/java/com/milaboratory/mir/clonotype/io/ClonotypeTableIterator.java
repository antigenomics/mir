package com.milaboratory.mir.clonotype.io;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParser;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ClonotypeTableIterator<T extends Clonotype> implements Iterator<ClonotypeCall<T>> {
    public static final String TOKEN = "\t";
    private final BufferedReader bufferedReader;
    private final ClonotypeTableParser<T> parser;
    private String line;

    public ClonotypeTableIterator(InputStream inputStream,
                                  ClonotypeTableParserFactory<T> parserFactory) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        line = bufferedReader.readLine();
        if (line != null) {
            this.parser = parserFactory.create(bufferedReader.readLine().split(TOKEN));
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
}