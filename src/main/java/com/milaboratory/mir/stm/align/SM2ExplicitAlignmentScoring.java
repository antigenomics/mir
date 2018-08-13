package com.milaboratory.mir.stm.align;

import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.alignment.LinearGapAlignmentScoring;
import com.milaboratory.core.mutations.Mutation;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.Sequence;

public class SM2ExplicitAlignmentScoring<S extends Sequence<S>> implements ExplicitAlignmentScoring<S> {
    private final double[][] substitutionMatrix;
    private final double gapFactor;

    public static final SM2ExplicitAlignmentScoring<AminoAcidSequence> SM2_AA_BLOSUM62;

    static {
        int N = AminoAcidSequence.ALPHABET.size();
        double[][] sm = new double[N][N];
        LinearGapAlignmentScoring<AminoAcidSequence> s =
                LinearGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62);
        int maxScore = 0;
        for (byte i = 0; i < N; i++) {
            for (byte j = 0; j < N; j++) {
                int score = s.getScore(i, j);
                sm[i][j] = score;
                maxScore = Math.max(maxScore, Math.abs(score));
            }
        }
        SM2_AA_BLOSUM62 = new SM2ExplicitAlignmentScoring<>(sm,
                -maxScore - 1,
                AminoAcidSequence.ALPHABET);
    }

    public SM2ExplicitAlignmentScoring(double[][] substitutionMatrix,
                                       double gapFactor,
                                       Alphabet<S> alphabet) {
        assert substitutionMatrix.length == alphabet.size();
        assert substitutionMatrix[0].length == alphabet.size();
        assert gapFactor <= 0;

        this.substitutionMatrix = substitutionMatrix;
        this.gapFactor = gapFactor;
    }

    @Override
    public double computeScore(S query, Mutations<S> mutations) {
        float queryScore = 0;
        for (int i = 0; i < query.size(); i++) {
            byte base = query.codeAt(i);
            queryScore += substitutionMatrix[base][base];
        }
        float targetScore = queryScore, score = queryScore;
        int indels = 0;

        for (int i = 0; i < mutations.size(); i++) {
            int code = mutations.getMutation(i);

            if (Mutation.isSubstitution(code)) {
                byte from = Mutation.getFrom(code),
                        to = Mutation.getTo(code);
                double matchScore = substitutionMatrix[from][from];
                // replace match by mismatch
                score -= matchScore;
                score += substitutionMatrix[from][to];
                // target has different base:
                // remove score for query base add score for this base
                targetScore -= matchScore;
                targetScore += substitutionMatrix[to][to];
            } else {
                indels++;
                if (Mutation.isDeletion(code)) {
                    // Query has additional base not found in target
                    byte from = Mutation.getFrom(code);
                    double matchScore = substitutionMatrix[from][from];
                    // remove base match score from alignment
                    score -= matchScore;
                    // also remove base score from target
                    targetScore -= matchScore;
                } else {
                    // Query has missing base found in target
                    byte to = Mutation.getTo(code);
                    double matchScore = substitutionMatrix[to][to];
                    // do not remove base match score from alignment

                    // add score to target
                    targetScore += matchScore;
                }
            }
        }

        return score - Math.max(queryScore, targetScore) + gapFactor * indels;
    }

    public double[][] getSubstitutionMatrix() {
        return substitutionMatrix;
    }

    public double getGapFactor() {
        return gapFactor;
    }
}