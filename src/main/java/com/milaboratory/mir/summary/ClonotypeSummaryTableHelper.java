package com.milaboratory.mir.summary;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.summary.binning.DummyGroupWrapper;
import com.milaboratory.mir.summary.binning.VJLGroup;
import com.milaboratory.mir.summary.binning.VJLGroupWrapper;
import com.milaboratory.mir.summary.impl.*;

import java.util.Collection;
import java.util.stream.Stream;

public final class ClonotypeSummaryTableHelper {
    private ClonotypeSummaryTableHelper() {
    }

    public static String getHeader(ClonotypeSummaryType clonotypeSummaryType) {
        switch (clonotypeSummaryType) {
            case AA_CDR3_PWM:
                return VJLGroup.HEADER + "\t" + PwmGroupSummaryEntry.HEADER;
            case NT_SPECTRATYPE:
            case AA_SPECTRATYPE:
                return Cdr3LenSummaryEntry.HEADER;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static <G extends ClonotypeGroup, E extends GroupSummaryEntry<G>> Stream<String> countersAsRows(
            Collection<E> counters) {
        return counters
                .stream()
                .map(x -> x.getClonotypeGroup().asRow() + "\t" + x.asRow());
    }

    public static ClonotypeSummaryTable create(ClonotypeSummaryType clonotypeSummaryType) {
        return create(clonotypeSummaryType, false);
    }

    public static ClonotypeSummaryTable create(ClonotypeSummaryType clonotypeSummaryType,
                                               boolean weightByFrequency) {
        switch (clonotypeSummaryType) {
            case AA_CDR3_PWM:
                return new ClonotypeSummaryTable(
                        new VJLGroupWrapper(),
                        PwmGroupSummary::new,
                        weightByFrequency
                );
            case NT_SPECTRATYPE:
                return new ClonotypeSummaryTable(
                        new DummyGroupWrapper(),
                        Cdr3NtLenSummary::new,
                        weightByFrequency
                );
            case AA_SPECTRATYPE:
                return new ClonotypeSummaryTable(
                        new DummyGroupWrapper(),
                        Cdr3AaLenSummary::new,
                        weightByFrequency
                );
            default:
                throw new UnsupportedOperationException();
        }
    }
}
