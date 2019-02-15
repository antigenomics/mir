package com.milaboratory.mir;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CollectionUtilsTest {

    @Test
    public void getKBestTest() {
        var input = Arrays.asList(1, 1, 4, 2, 3, 1, 5);

        var res1 = CollectionUtils.getKFirst(input, 3, false, true);
        Assert.assertEquals(5, res1.size());
        Assert.assertArrayEquals(new Integer[]{1, 1, 1, 2, 3}, res1.toArray(new Integer[5]));

        var res2 = CollectionUtils.getKFirst(input, 3, false, false);
        Assert.assertEquals(3, res2.size());
        Assert.assertArrayEquals(new Integer[]{1, 1, 1}, res2.toArray(new Integer[3]));
    }
}