package com.codedose.oag.utils;

import scala.collection.JavaConversions;
import scala.collection.Seq;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Utils for Scala-specific Spark APIs
 */
public final class ScalaUtils {

    private ScalaUtils() {}

    @SafeVarargs
    public static <T> Seq<T> seq(T... elements) {
        return JavaConversions.asScalaBuffer(asList(elements));
    }
    public static <T> Seq<T> seq(List<T> elements) {
        return JavaConversions.asScalaBuffer(elements);
    }
}
