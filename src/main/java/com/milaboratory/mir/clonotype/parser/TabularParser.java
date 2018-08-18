package com.milaboratory.mir.clonotype.parser;

public interface TabularParser<T> {
    T parse(String[] splitLine);
}
