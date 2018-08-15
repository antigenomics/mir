package com.milaboratory.mir.model.probability;

import java.util.*;

public class CategoricalProbabilityDistribution<T> {
    private final List<T> categories;
    private final double[] probabilities;
    private final List<CategoryProbability<T>> categoryProbabilityList;

    public CategoricalProbabilityDistribution(Collection<CategoryProbability<T>> categoryProbabilityList) {
        int n = categoryProbabilityList.size();
        this.categories = new ArrayList<>(n);
        this.probabilities = new double[n];

        // copy as sorts in-place
        this.categoryProbabilityList = new ArrayList<>(categoryProbabilityList);
        // sort descending
        this.categoryProbabilityList.sort((o1, o2) -> -Double.compare(o1.getProbability(), o2.getProbability()));
        for (int i = 0; i < n; i++) {
            var cp = this.categoryProbabilityList.get(i);
            categories.add(cp.getCategory());
            probabilities[i] = cp.getProbability();
        }
    }

    public T sample() {
        return categories.get(ProbabilityUtils.sample(probabilities));
    }

    public T sample(Random random) {
        return categories.get(ProbabilityUtils.sample(probabilities, random));
    }

    public List<CategoryProbability<T>> getCategoryProbabilityList() {
        return Collections.synchronizedList(categoryProbabilityList);
    }
}
