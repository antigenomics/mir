package com.antigenomics.mir.summary;

import org.junit.Test;

public class ClonotypeSummaryTableHelperTest {

    @Test
    public void getHeader() {
        for (ClonotypeSummaryType clonotypeSummaryType : ClonotypeSummaryType.values()) {
            ClonotypeSummaryTableHelper.getHeader(clonotypeSummaryType);
        }
    }

    @Test
    public void create() {
        for (ClonotypeSummaryType clonotypeSummaryType : ClonotypeSummaryType.values()) {
            ClonotypeSummaryTableHelper.create(clonotypeSummaryType);
        }
    }

    @Test
    public void create1() {
        for (ClonotypeSummaryType clonotypeSummaryType : ClonotypeSummaryType.values()) {
            ClonotypeSummaryTableHelper.create(clonotypeSummaryType, true);
        }
    }
}