package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.sequence.AminoAcidAlphabet;
import com.milaboratory.core.sequence.AminoAcidSequence;

import java.util.HashMap;
import java.util.Map;

public class ResidueName extends PdbField {
    private static final Map<String, Byte> codeConversions = new HashMap<>();

    static {
        var A = AminoAcidSequence.ALPHABET;
        codeConversions.put("ALA", A.symbolToCode('A'));
        codeConversions.put("ARG", A.symbolToCode('R'));
        codeConversions.put("ASN", A.symbolToCode('N'));
        codeConversions.put("ASP", A.symbolToCode('D'));
        codeConversions.put("CYS", A.symbolToCode('C'));
        codeConversions.put("GLN", A.symbolToCode('Q'));
        codeConversions.put("GLU", A.symbolToCode('E'));
        codeConversions.put("GLY", A.symbolToCode('G'));
        codeConversions.put("HIS", A.symbolToCode('H'));
        codeConversions.put("ILE", A.symbolToCode('I'));
        codeConversions.put("LEU", A.symbolToCode('L'));
        codeConversions.put("LYS", A.symbolToCode('K'));
        codeConversions.put("MET", A.symbolToCode('M'));
        codeConversions.put("PHE", A.symbolToCode('F'));
        codeConversions.put("PRO", A.symbolToCode('P'));
        codeConversions.put("SER", A.symbolToCode('S'));
        codeConversions.put("THR", A.symbolToCode('T'));
        codeConversions.put("TRP", A.symbolToCode('W'));
        codeConversions.put("TYR", A.symbolToCode('Y'));
        codeConversions.put("VAL", A.symbolToCode('V'));
    }

    private final byte code;

    public ResidueName(String value) {
        super(value);
        if (value.length() != 3) {
            throw new IllegalArgumentException("Should be 3 charecters long");
        }
        this.code = codeConversions.getOrDefault(value, AminoAcidAlphabet.X);
    }

    public byte getCode() {
        return code;
    }
}
