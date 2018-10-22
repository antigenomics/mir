package com.milaboratory.mir.structure.pdb.parser;

import com.milaboratory.mir.structure.pdb.*;

import java.io.*;
import java.util.*;

public class PdbParserUtils {
    private static final PdbFieldCache<AtomName> atomNameCache = new PdbFieldCache<>(AtomName::new);
    private static final PdbFieldCache<ResidueName> residueNameCache = new PdbFieldCache<>(ResidueName::new);
    private static final PdbFieldCache<ElementSymbolWithCharge> elementSymbolWithChargePdbFieldCache = new PdbFieldCache<>(ElementSymbolWithCharge::new);

    public static final AtomName CA_ATOM_NAME = atomNameCache.createField(" CA "),
            C_ATOM_NAME = atomNameCache.createField(" C  "),
            N_ATOM_NAME = atomNameCache.createField(" C  ");

    private static final int PDB_LINE_SZ = 80;

    public static void writeStructure(Structure structure, PrintWriter pw) {
        structure
                .getChains()
                .stream()
                .flatMap(x -> x.getResidues().stream())
                .flatMap(x -> x.getAtoms().stream())
                .sorted()
                .forEach(PdbParserUtils::writeAtom);
        pw.write("END");
        pw.close();
    }

    public static Structure parseStructure(String id, InputStream stream) throws IOException {
        return new Structure(id, parseStructureMap(stream));
    }

    private static Map<Character, Map<Short, List<RawAtom>>> parseStructureMap(InputStream stream) throws IOException {
        var atomMap = new HashMap<Character, Map<Short, List<RawAtom>>>();
        short newIndex = -1;
        short previousAaId = -1;
        char previousChain = ' ';
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (getAtomType(line) != null) {
                    var atom = parseAtom(line);
                    if (atom.getChainIdentifier() != previousChain) {
                        newIndex = -1;
                        previousAaId = -1;
                        previousChain = atom.getChainIdentifier();
                    }
                    if (atom.getResidueSequenceNumber() > previousAaId) {
                        newIndex++;
                        previousAaId = atom.getResidueSequenceNumber();
                    }
                    if (atom.getResidueSequenceNumber() == previousAaId &&
                            atom.getResidueInsertionCode() != ' ') {
                        newIndex++;
                    }

                    atom.sequentialResidueSequenceNumber = newIndex;

                    atomMap
                            .computeIfAbsent(atom.getChainIdentifier(), x -> new HashMap<>())
                            .computeIfAbsent(atom.sequentialResidueSequenceNumber, x -> new ArrayList<>())
                            .add(atom);
                }
            }
        }
        return atomMap;
    }

    public static RawAtom parseAtom(String line) {
        AtomType atomType = getAtomType(line);

        if (atomType == null) {
            throw new IllegalArgumentException("Line '" + line + "' is not a valid atom line: ATOM/TER required");
        }

        line = new String(padRight(line.trim(), PDB_LINE_SZ));

        return new RawAtom(
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

    /////
    ///// OUTPUT
    /////

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
                String.valueOf(writeFloat(atom.getCoordinates().getX(), 8, 3)) +
                String.valueOf(writeFloat(atom.getCoordinates().getY(), 8, 3)) +
                String.valueOf(writeFloat(atom.getCoordinates().getZ(), 8, 3)) +
                String.valueOf(writeFloat(atom.getOccupancy(), 6, 2)) +
                String.valueOf(writeFloat(atom.getTemperatureFactor(), 6, 2)) +
                //     0123456789
                /* */ "          " +
                atom.getElementSymbolWithCharge();
    }

    /////
    ///// TEXT UTILITIES
    /////

    private static float parseFloat(String string) {
        return string.isEmpty() ? Float.NaN : Float.parseFloat(string);
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
