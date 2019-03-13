package com.antigenomics.mir.rearrangement.converter;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.segment.Segment;
import com.antigenomics.mir.segment.SegmentLibrary;
import com.antigenomics.mir.segment.SegmentProvider;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.antigenomics.mir.CommonUtils.*;

public final class ConverterUtils {
    private ConverterUtils() {

    }

    public static <V1, K2 extends Segment, V2> Map<K2, V2>
    map2mapSegmentSafe(Map<String, V1> map,
                       SegmentProvider<K2> segmentProvider,
                       Function<V1, V2> valueMapper) {
        return map.entrySet()
                .stream()
                .map(x -> new Entry<>(segmentProvider.fromId(x.getKey()), x.getValue()))
                .filter(x -> !x.segment.isMissingInLibrary())
                .collect(Collectors.toMap(
                        x -> x.segment,
                        x -> valueMapper.apply(x.value))
                );
    }

    private static class Entry<T extends Segment, V> {
        final T segment;
        final V value;

        public Entry(T segment, V value) {
            this.segment = segment;
            this.value = value;
        }

        public T getSegment() {
            return segment;
        }

        public V getValue() {
            return value;
        }
    }

    public static <T> Map<T, Double> normalize(Map<T, Double> map) {
        double sum = map.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue() / sum
        ));
    }

    public static <T extends Segment>
    Map<T, Double> convertSegment(Map<String, Double> probabilities,
                                  SegmentLibrary segmentLibrary,
                                  Class<T> segmentClass,
                                  boolean safe) {
        var segmentProvider = segmentLibrary.asSegmentProvider(segmentClass);

        if (safe) {
            return normalize(
                    map2mapSegmentSafe(
                            probabilities,
                            segmentProvider,
                            x -> x
                    )
            );
        } else {
            return map2map(
                    probabilities,
                    e -> segmentProvider.fromId(e.getKey()),
                    Map.Entry::getValue
            );
        }
    }

    public static <T extends Segment>
    Map<T, Double> convertSegment(Map<String, Double> probabilities,
                                  SegmentLibrary segmentLibrary,
                                  Class<T> segmentClass) {
        return convertSegment(probabilities, segmentLibrary, segmentClass, true);
    }

    public static <T extends Segment, V extends Segment>
    Map<V, Map<T, Double>> convertSegment(Map<String, Map<String, Double>> probabilities,
                                          SegmentLibrary segmentLibrary,
                                          Class<T> segmentClassT,
                                          Class<V> segmentClassV,
                                          boolean safe) {
        var segmentProviderV = segmentLibrary.asSegmentProvider(segmentClassV);

        if (safe) {
            return map2mapSegmentSafe(
                    probabilities,
                    segmentProviderV,
                    x -> convertSegment(x, segmentLibrary, segmentClassT, true)
            );
        } else {
            return map2map(
                    probabilities,
                    e -> segmentProviderV.fromId(e.getKey()),
                    e -> convertSegment(e.getValue(), segmentLibrary, segmentClassT, false));
        }
    }

    public static <T extends Segment, V extends Segment>
    Map<V, Map<T, Double>> convertSegment(Map<String, Map<String, Double>> probabilities,
                                          SegmentLibrary segmentLibrary,
                                          Class<T> segmentClassT,
                                          Class<V> segmentClassV) {
        return convertSegment(probabilities, segmentLibrary, segmentClassT, segmentClassV, true);
    }

    public static <T extends Segment, V extends Segment, U extends Segment>
    Map<U, Map<V, Map<T, Double>>> convertSegment(Map<String, Map<String, Map<String, Double>>> probabilities,
                                                  SegmentLibrary segmentLibrary,
                                                  Class<T> segmentClassT,
                                                  Class<V> segmentClassV,
                                                  Class<U> segmentClassU,
                                                  boolean safe) {
        var segmentProviderU = segmentLibrary.asSegmentProvider(segmentClassU);

        if (safe) {
            return map2mapSegmentSafe(
                    probabilities,
                    segmentProviderU,
                    x -> convertSegment(x, segmentLibrary, segmentClassT, segmentClassV, true)
            );
        } else {
            return map2map(
                    probabilities,
                    e -> segmentProviderU.fromId(e.getKey()),
                    e -> convertSegment(e.getValue(), segmentLibrary, segmentClassT, segmentClassV, false)
            );
        }
    }

    public static <T extends Segment, V extends Segment, U extends Segment>
    Map<U, Map<V, Map<T, Double>>> convertSegment(Map<String, Map<String, Map<String, Double>>> probabilities,
                                                  SegmentLibrary segmentLibrary,
                                                  Class<T> segmentClassT,
                                                  Class<V> segmentClassV,
                                                  Class<U> segmentClassU) {
        return convertSegment(probabilities, segmentLibrary, segmentClassT, segmentClassV, segmentClassU, true);
    }

    public static <T extends Segment>
    Map<T, Map<Integer, Double>> convertSegmentTrimming(Map<String, Map<String, Double>> probabilities,
                                                        SegmentLibrary segmentLibrary,
                                                        Class<T> segmentClass,
                                                        boolean safe) {
        var segmentProvider = segmentLibrary.asSegmentProvider(segmentClass);

        if (safe) {
            return map2mapSegmentSafe(
                    probabilities,
                    segmentProvider,
                    ConverterUtils::convertIndelSize
            );
        } else {
            return map2map(
                    probabilities,
                    e -> segmentProvider.fromId(e.getKey()),
                    e -> convertIndelSize(e.getValue())
            );
        }
    }

    public static <T extends Segment>
    Map<T, Map<Integer, Double>> convertSegmentTrimming(Map<String, Map<String, Double>> probabilities,
                                                        SegmentLibrary segmentLibrary,
                                                        Class<T> segmentClass) {
        return convertSegmentTrimming(probabilities, segmentLibrary, segmentClass, true);
    }

    public static <T extends Segment>
    Map<T, Map<Integer, Map<Integer, Double>>> convertTwoSideTrimming(Map<String, Map<String, Map<String, Double>>> probabilities,
                                                                      SegmentLibrary segmentLibrary,
                                                                      Class<T> segmentClass,
                                                                      boolean safe) {
        var segmentProvider = segmentLibrary.asSegmentProvider(segmentClass);

        if (safe) {
            return map2mapSegmentSafe(
                    probabilities,
                    segmentProvider,
                    x -> map2map(
                            x,
                            ee -> Integer.parseInt(ee.getKey()),
                            ee -> convertIndelSize(ee.getValue())
                    )
            );
        } else {
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
    }

    public static <T extends Segment>
    Map<T, Map<Integer, Map<Integer, Double>>> convertTwoSideTrimming(Map<String, Map<String, Map<String, Double>>> probabilities,
                                                                      SegmentLibrary segmentLibrary,
                                                                      Class<T> segmentClass) {
        return convertTwoSideTrimming(probabilities, segmentLibrary, segmentClass, true);
    }

    public static Map<Integer, Double> convertIndelSize(Map<String, Double> map) {
        return map2map(
                map,
                e -> Integer.parseInt(e.getKey()),
                Map.Entry::getValue
        );
    }

    private static byte convertBase(String str) {
        return NucleotideSequence.ALPHABET.symbolToCode(str.charAt(0));
    }

    public static Map<Byte, Double> convertNucleotide(Map<String, Double> probabilities) {
        return map2map(
                probabilities,
                e -> convertBase(e.getKey()),
                Map.Entry::getValue
        );
    }

    public static Map<Byte, Map<Byte, Double>> convertNucleotidePair(Map<String, Map<String, Double>> probabilities) {
        return map2map(
                probabilities,
                e -> convertBase(e.getKey()),
                e -> map2map(
                        e.getValue(),
                        ee -> convertBase(ee.getKey()),
                        Map.Entry::getValue
                )
        );
    }
}
