package com.milaboratory.mir.structure.pdb.parser;

import com.milaboratory.core.sequence.AminoAcidAlphabet;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.CommonUtils;

import java.util.HashMap;
import java.util.Map;

public class ResidueName extends PdbField {
    private static final Map<String, Byte> codeConversions = new HashMap<>();
    private static final Map<String, Character> codeSingleLetterConversions = new HashMap<>();

    static {
        codeSingleLetterConversions.put("ALA", 'A');
        codeSingleLetterConversions.put("ARG", 'R');
        codeSingleLetterConversions.put("ASN", 'N');
        codeSingleLetterConversions.put("ASP", 'D');
        codeSingleLetterConversions.put("CYS", 'C');
        codeSingleLetterConversions.put("GLN", 'Q');
        codeSingleLetterConversions.put("GLU", 'E');
        codeSingleLetterConversions.put("GLY", 'G');
        codeSingleLetterConversions.put("HIS", 'H');
        codeSingleLetterConversions.put("ILE", 'I');
        codeSingleLetterConversions.put("LEU", 'L');
        codeSingleLetterConversions.put("LYS", 'K');
        codeSingleLetterConversions.put("MET", 'M');
        codeSingleLetterConversions.put("PHE", 'F');
        codeSingleLetterConversions.put("PRO", 'P');
        codeSingleLetterConversions.put("SER", 'S');
        codeSingleLetterConversions.put("THR", 'T');
        codeSingleLetterConversions.put("TRP", 'W');
        codeSingleLetterConversions.put("TYR", 'Y');
        codeSingleLetterConversions.put("VAL", 'V');

        codeSingleLetterConversions.forEach((key, value) ->
                codeConversions.put(key, AminoAcidSequence.ALPHABET.symbolToCode(value))
        );
    }

    private final byte code;
    private final Character letter;

    public ResidueName(String value) {
        super(value);
        if (value.length() != 3) {
            throw new IllegalArgumentException("Should be 3 charecters long");
        }
        this.code = codeConversions.getOrDefault(value, AminoAcidAlphabet.X);
        this.letter = codeSingleLetterConversions.getOrDefault(value, 'X');
    }

    public byte getCode() {
        return code;
    }

    public Character getLetter() {
        return letter;
    }
}
