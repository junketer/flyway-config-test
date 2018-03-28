package com.codedose.oag.examples;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;


public class SparkSample {

    public static void main(String[] args) {
        // Define spark context
        // Uncomment master local to run it in intellij
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("Hello Spark");
        //sparkConf.setMaster("local");
        JavaSparkContext context = new JavaSparkContext(sparkConf);

        // word-count
        context.textFile("loremipsum.txt", 6)
                .flatMap(text -> Arrays.asList(text.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b)
               // .mapToPair(t -> new Tuple2<Integer , String>(t._2, t._1))
                .sortByKey()
              //  .mapToPair(t -> new Tuple2<String , Integer>(t._2, t._1))
                .map((a) -> a._1() + ","+a._2())
                .coalesce(1).saveAsTextFile("result-java-rdd");
    }



}