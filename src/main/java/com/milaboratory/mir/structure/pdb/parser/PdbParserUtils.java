package com.milaboratory.mir.structure.pdb.parser;

import com.milaboratory.mir.structure.pdb.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PdbParserUtils {
    private static final PdbFieldCache<AtomName> atomNameCache = new PdbFieldCache<>(AtomName::new);
    private static final PdbFieldCache<ResidueName> residueNameCache = new PdbFieldCache<>(ResidueName::new);
    private static final PdbFieldCache<ElementSymbolWithCharge> elementSymbolWithChargePdbFieldCache = new PdbFieldCache<>(ElementSymbolWithCharge::new);

    private static final int PDB_LINE_SZ = 80;

    public static PdbStructure parseStructure(InputStream stream) throws IOException {
        var atoms = new ArrayList<Atom>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (getAtomType(line) != null) {
                    atoms.add(parseAtom(line));
                }
            }
        }
        return new PdbStructure(atoms);
    }

    public static void writeStructure(PdbStructure structure, PrintWriter pw) {
        structure.getChains()
                .stream()
                .sorted()
                .flatMap(
                        pdbChain -> pdbChain.getAtoms()
                                .stream()
                                .sorted()
                )
                .forEach(PdbParserUtils::writeAtom);
        pw.write("END");
        pw.close();
    }

    public static Atom parseAtom(String line) {
        AtomType atomType = getAtomType(line);

        if (atomType == null) {
            throw new IllegalArgumentException("Line '" + line + "' is not a valid atom line: ATOM/TER required");
        }

        line = new String(padRight(line.trim(), PDB_LINE_SZ));

        return new Atom(
                atomType,
                Short.parseShort(line.substring(6, 11).trim()),
                atomNameCache.createField(line.substring(12, 16)),
                line.charAt(16),
                residueNameCache.createField(line.substring(17, 20)),
                line.charAt(21),
                Short.parseShort(line.substring(22, 26).trim()),
                line.charAt(26),
                parseFloat(line.substring(30, 38).trim()),
                parseFloat(line.substring(38, 46).trim()),
                parseFloat(line.substring(46, 54).trim()),
                parseFloat(line.substring(54, 60).trim()),
                parseFloat(line.substring(60, 66).trim()),
                elementSymbolWithChargePdbFieldCache.createField(line.substring(76, 80))
        );
    }

    private static float parseFloat(String string) {
        return string.isEmpty() ? Float.NaN : Float.parseFloat(string);
    }

    public static String writeAtom(Atom atom) {
        return atom.getAtomType().getId() +
                String.valueOf(writeShort(atom.getAtomSerialNumber(), 5)) +
                " " +
                atom.getAtomName() +
                atom.getAlternativeLocationIdentifier() +
                atom.getResidueName() +
                " " +
                atom.getChainIdentifier() +
                String.valueOf(writeShort(atom.getResidueSequenceNumber(), 4)) +
                atom.getResidueInsertionCode() +
                "   " +
                String.valueOf(writeFloat(atom.getX(), 8, 3)) +
                String.valueOf(writeFloat(atom.getY(), 8, 3)) +
                String.valueOf(writeFloat(atom.getZ(), 8, 3)) +
                String.valueOf(writeFloat(atom.getOccupancy(), 6, 2)) +
                String.valueOf(writeFloat(atom.getTemperatureFactor(), 6, 2)) +
                //     0123456789
                /* */ "          " +
                atom.getElementSymbolWithCharge();
    }

    private static AtomType getAtomType(String line) {
        if (line.startsWith(AtomType.ATOM.getId())) {
            return AtomType.ATOM;
        } else if (line.startsWith(AtomType.TER.getId())) {
            return AtomType.TER;
        } else {
            return null;
        }
    }

    public static char[] writeFloat(float value, int a, int b) {
        if (Float.isNaN(value)) {
            char[] res = new char[a];
            Arrays.fill(res, ' ');
            return res;
        } else {
            String formatted = String.format("%." + b + "f", value);
            return padLeft(formatted, a);
        }
    }

    public static char[] writeShort(short value, int a) {
        return padLeft(Short.toString(value), a);
    }

    public static char[] padLeft(String str, int a) {
        int delta = a - str.length();
        if (delta < 0) {
            throw new IllegalArgumentException("Cannot pad '" + str + "' to " + a + " chars.");
        }
        char[] res = new char[a];
        for (int i = 0; i < delta; i++) {
            res[i] = ' ';
        }
        for (int i = 0; i < str.length(); i++) {
            res[i + delta] = str.charAt(i);
        }
        return res;
    }

    public static char[] padRight(String str, int a) {
        int delta = a - str.length();
        if (delta < 0) {
            throw new IllegalArgumentException("Cannot pad '" + str + "' to " + a + " chars.");
        }
        char[] res = new char[a];
        for (int i = 0; i < str.length(); i++) {
            res[i] = str.charAt(i);
        }
        for (int i = 0; i < delta; i++) {
            res[i + str.length()] = ' ';
        }
        return res;
    }
}