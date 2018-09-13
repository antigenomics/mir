package com.milaboratory.mir.mappers.stm;

import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.alignment.LinearGapAlignmentScoring;
import com.milaboratory.core.mutations.Mutation;
import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.Sequence;

public final class SM1ExplicitAlignmentScoring<S extends Sequence<S>> implements ExplicitAlignmentScoring<S> {
    private final float[][] substitutionPenalties;
    private final float[] gapPenalties;
    private final float gapFactor;

    public static final SM1ExplicitAlignmentScoring<AminoAcidSequence> SM1_AA_BLOSUM62;

    static {
        int N = AminoAcidSequence.ALPHABET.size();
        float[][] sm = new float[N][N];
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
        SM1_AA_BLOSUM62 = new SM1ExplicitAlignmentScoring<>(sm,
                -maxScore - 1,
                AminoAcidSequence.ALPHABET);
    }

    public SM1ExplicitAlignmentScoring(float[][] substitutionMatrix,
                                       float gapFactor,
                                       Alphabet<S> alphabet) {
        int N = alphabet.size();
        assert substitutionMatrix.length == alphabet.size();
        assert substitutionMatrix[0].length == alphabet.size();
        assert gapFactor <= 0;

        this.substitutionPenalties = new float[N][N];
        this.gapPenalties = new float[N];

        for (int i = 0; i < N; i++) {
            gapPenalties[i] = substitutionMatrix[i][i];
            for (int j = 0; j < N; j++) {
                substitutionPenalties[i][j] = substitutionMatrix[i][j] -
                        Math.max(substitutionMatrix[i][i], substitutionMatrix[j][j]);
            }
        }
        this.gapFactor = gapFactor;
    }

    @Override
    public float computeScore(S query, Mutations<S> mutations) {
        float score = 0;
        int indels = 0;

        for (int i = 0; i < mutations.size(); i++) {
            int code = mutations.getMutation(i);

            if (Mutation.isSubstitution(code)) {
                score += substitutionPenalties[Mutation.getFrom(code)][Mutation.getTo(code)];
            } else {
                indels++;
                if (Mutation.isDeletion(code)) {
                    score -= gapPenalties[Mutation.getFrom(code)];
                } else {
                    score -= gapPenalties[Mutation.getTo(code)];
                }
            }
        }

        return score + indels * gapFactor;
    }

    public float[][] getSubstitutionPenalties() {
        float[][] sp = new float[substitutionPenalties.length][];
        for (int i = 0; i < substitutionPenalties.length; i++) {
            sp[i] = substitutionPenalties[i].clone();
        }
        return sp;
    }

    public float[] getGapPenalties() {
        return gapPenalties.clone();
    }

    public float getGapFactor() {
        return gapFactor;
    }
}