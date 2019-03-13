package com.antigenomics.mir.mappers.stm;

import com.milaboratory.core.tree.TreeSearchParameters;

/**
 * Stores parameters for the task of sequence tree map (STM) search & STM-assisted alignment: parameters specify
 * the 'search scope' i.e. the number of allowed edits (substitutions, insertions, deletions) and
 * the best alignment selection strategy.
 */
public class SequenceSearchScope {
    private final int maxSubstitutions, maxInsertions, maxDeletions, maxIndels, maxTotal;
    private final boolean exhaustive, greedy;

    /**
     * Create sequence search filter parameter set. Specifies maximal allowed number of substitutions, indels
     * (i.e. sum of insertions and deletions, or difference in query and target sequence lengths) and
     * edit distance from query sequence. Note that resulting search is 'symmetric' as
     * number of insertions in query is the number of deletions in target.
     *
     * @param maxSubstitutions maximal number of substitutions (Hamming distance threshold)
     * @param maxIndels        maximal number of insertions plus deletions
     * @param maxTotal         maximal number of edits (Levenstein distance threshold)
     * @param exhaustive       if set to false stop with first alignment from tree search,
     *                         otherwise search all possible re-alignments for the **same** target sequence
     * @param greedy           if set to false, will also consider alignments with more mismatches than
     *                         previous hits for the **same** target sequence
     *                         (only applicable within search scope and when exhaustive search is on)
     */
    public SequenceSearchScope(int maxSubstitutions, int maxIndels, int maxTotal,
                               boolean exhaustive, boolean greedy) {
        this.maxSubstitutions = maxSubstitutions;
        this.maxTotal = maxTotal;
        this.maxDeletions = maxIndels;
        this.maxInsertions = maxIndels;
        this.maxIndels = maxIndels;
        this.exhaustive = exhaustive;
        this.greedy = greedy;
    }

    /**
     * Create sequence search filter parameter set. Specifies maximal allowed number of substitutions, indels
     * (i.e. sum of insertions and deletions, or difference in query and target sequence lengths) and
     * edit distance from query sequence. Note that resulting search is 'symmetric' as
     * number of insertions in query is the number of deletions in target. The search will only consider alignments
     * with lowest number of edits and will choose the best alignment across possible re-alignments of a given
     * target sequence based on alignment score.
     *
     * @param maxSubstitutions maximal number of substitutions (Hamming distance threshold)
     * @param maxIndels        maximal number of insertions plus deletions
     * @param maxTotal         maximal number of edits (Levenstein distance threshold)
     */
    public SequenceSearchScope(int maxSubstitutions, int maxIndels, int maxTotal) {
        this(maxSubstitutions, maxIndels, maxTotal, true, true);
    }

    /**
     * Create sequence search filter parameter set. Specifies maximal allowed number of substitutions, insertions,
     * deletions and edit distance from query sequence. Note that resulting search is not 'symmetric' as
     * number of insertions and deletions may not match.
     *
     * @param maxSubstitutions maximal number of substitutions (Hamming distance threshold)
     * @param maxDeletions     maximal number of deletions
     * @param maxInsertions    maximal number of insertions
     * @param maxTotal         maximal number of edits (Levenstein distance threshold)
     * @param exhaustive       if set to false stop with first alignment from tree search,
     *                         otherwise search all possible re-alignments for the **same** target sequence
     * @param greedy           if set to false, will also consider alignments with more mismatches than
     *                         previous hits for the **same** target sequence
     *                         (only applicable within search scope and when exhaustive search is on)
     */
    public SequenceSearchScope(int maxSubstitutions, int maxDeletions, int maxInsertions, int maxTotal,
                               boolean exhaustive, boolean greedy) {
        this.maxSubstitutions = maxSubstitutions;
        this.maxTotal = maxTotal;
        this.maxDeletions = maxDeletions;
        this.maxInsertions = maxInsertions;
        this.maxIndels = maxInsertions + maxDeletions;
        this.exhaustive = exhaustive;
        this.greedy = greedy;
    }

    /**
     * Create sequence search filter parameter set. Specifies maximal allowed number of substitutions, insertions,
     * deletions and edit distance from query sequence. Note that resulting search is not 'symmetric' as
     * number of insertions and deletions may not match. The search will only consider alignments
     * with lowest number of edits and will choose the best alignment across possible re-alignments of a given
     * target sequence based on alignment score.
     *
     * @param maxSubstitutions maximal number of substitutions (Hamming distance threshold)
     * @param maxDeletions     maximal number of deletions
     * @param maxInsertions    maximal number of insertions
     * @param maxTotal         maximal number of edits (Levenstein distance threshold)
     */
    public SequenceSearchScope(int maxSubstitutions, int maxDeletions, int maxInsertions, int maxTotal) {
        this(maxSubstitutions, maxDeletions, maxInsertions, maxTotal, true, true);
    }

    /**
     * Convert edit distance thresholds to sequence tree map search parameters
     * @return
     */
    public TreeSearchParameters getTreeSearchParameters() {
        return new TreeSearchParameters(
                maxSubstitutions,
                maxDeletions,
                maxInsertions,
                maxTotal,
                !exhaustive);
    }

    /**
     * Gets maximal number of substitutions (Hamming distance threshold)
     * @return
     */
    public int getMaxSubstitutions() {
        return maxSubstitutions;
    }

    /**
     * Gets maximal number of insertions (Hamming distance threshold)
     * @return
     */
    public int getMaxInsertions() {
        return maxInsertions;
    }

    /**
     * Gets maximal number of deletions (Hamming distance threshold)
     * @return
     */
    public int getMaxDeletions() {
        return maxDeletions;
    }

    /**
     * Gets maximal number of indels
     * @return
     */
    public int getMaxIndels() {
        return maxIndels;
    }

    /**
     * Gets maximal number of edits (Levenstein distance threshold)
     * @return
     */
    public int getMaxTotal() {
        return maxTotal;
    }

    /**
     * Perform exhaustive search. Search exhaustively across alignments with the smallest Levenstein distance
     * for the **same** target sequence.
     * If false stop with first alignment from tree search, otherwise search all possible re-alignments.
     * @return
     */
    public boolean isExhaustive() {
        return exhaustive;
    }

    /**
     * Perform greedy search. Only alignments with the smallest Levenstein distance will be considered for the **same**
     * target sequence.
     * If false, also consider alignments with more mismatches than previous hits. Only performed within search scope
     * and when exhaustive search is on.
     * @return
     */
    public boolean isGreedy() {
        return greedy;
    }
}
