package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.segment.Segment;
import com.milaboratory.mir.segment.SegmentLibrary;

import java.util.Map;

import static com.milaboratory.mir.CommonUtils.map2map;

public final class ConverterUtils {
    private ConverterUtils() {

    }

    public static <T extends Segment>
    Map<T, Double> convertSegment(Map<String, Double> probabilities,
                                  SegmentLibrary segmentLibrary,
                                  Class<T> segmentClass) {
        var segmentProvider = segmentLibrary.asSegmentProvider(segmentClass);
        return map2map(
                probabilities,
                e -> segmentProvider.fromId(e.getKey()),
                Map.Entry::getValue
        );
    }

    public static <T extends Segment, V extends Segment>
    Map<V, Map<T, Double>> convertSegment(Map<String, Map<String, Double>> probabilities,
                                          SegmentLibrary segmentLibrary,
                                          Class<T> segmentClassT,
                                          Class<V> segmentClassV) {
        var segmentProviderV = segmentLibrary.asSegmentProvider(segmentClassV);

        return map2map(
                probabilities,
                e -> segmentProviderV.fromId(e.getKey()),
                e -> convertSegment(e.getValue(), segmentLibrary, segmentClassT)
        );
    }

    public static <T extends Segment, V extends Segment, U extends Segment>
    Map<U, Map<V, Map<T, Double>>> convertSegment(Map<String, Map<String, Map<String, Double>>> probabilities,
                                                  SegmentLibrary segmentLibrary,
                                                  Class<T> segmentClassT,
                                                  Class<V> segmentClassV,
                                                  Class<U> segmentClassU) {
        var segmentProviderU = segmentLibrary.asSegmentProvider(segmentClassU);

        return map2map(
                probabilities,
                e -> segmentProviderU.fromId(e.getKey()),
                e -> convertSegment(e.getValue(), segmentLibrary, segmentClassT, segmentClassV)
        );
    }

    public static Map<Integer, Double> convertIndelSize(Map<String, Double> map) {
        return map2map(
                map,
                e -> Integer.parseInt(e.getKey()),
                Map.Entry::getValue
        );
    }

    public static <T extends Segment>
    Map<T, Map<Integer, Double>> convertSegmentTrimming(Map<String, Map<String, Double>> probabilities,
                                                        SegmentLibrary segmentLibrary,
                                                        Class<T> segmentClass) {
        var segmentProvider = segmentLibrary.asSegmentProvider(segmentClass);

        return map2map(
                probabilities,
                e -> segmentProvider.fromId(e.getKey()),
                e -> convertIndelSize(e.getValue())
        );
    }

    public static <T extends Segment>
    Map<T, Map<Integer, Map<Integer, Double>>> convertTwoSideTrimming(Map<String, Map<String, Map<String, Double>>> probabilities,
                                                                      SegmentLibrary segmentLibrary,
                                                                      Class<T> segmentClass) {
        var segmentProvider = segmentLibrary.asSegmentProvider(segmentClass);

        return map2map(
                probabilities,
                e -> segmentProvider.fromId(e.getKey()),
                e -> map2map(
                        e.getValue(),
                        ee -> Integer.parseInt(ee.getKey()),
                        ee -> convertIndelSize(ee.getValue())
                )
        );
    }

    public static Map<Byte, Double> convertNucleotide(Map<String, Double> probabilities) {
        return map2map(
                probabilities,
                e -> Byte.parseByte(e.getKey()),
                Map.Entry::getValue
        );
    }

    public static Map<Byte, Map<Byte, Double>> convertNucleotidePair(Map<String, Map<String, Double>> probabilities) {
        return map2map(
                probabilities,
                e -> Byte.parseByte(e.getKey()),
                e -> map2map(
                        e.getValue(),
                        ee -> Byte.parseByte(ee.getKey()),
                        Map.Entry::getValue
                )
        );
    }
}
