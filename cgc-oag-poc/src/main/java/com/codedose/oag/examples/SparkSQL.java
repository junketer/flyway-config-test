package com.codedose.oag.examples;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;

public class SparkSQL {

    public static void main(String[] args) {
        // Define spark context
        // Uncomment master local to run it in intellij
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.sql.shuffle.partitions", "6")
                //.master("local")
                .getOrCreate();

        // read data  to RDD
        JavaRDD<Word> data = spark.read()
                .textFile("loremipsum.txt")
                .javaRDD()
                .flatMap(text -> Arrays.asList(text.split(" ")).iterator())
                .map(word -> new Word(word));

        // Create DataFrame
        Dataset<Row> wordDS = spark.createDataFrame(data, Word.class);
        wordDS.createOrReplaceTempView("words");

        // Run SQL
        Dataset<Row> countResult = spark.sql("SELECT word, count(*) as count " +
                                                    "FROM words " +
                                                    "GROUP BY word " +
                                                    "ORDER BY word");
        // Save data
        countResult.coalesce(1).write().csv("result-spark-sql");
    }

    public static class Word{
        private String word;

        public Word(String word) {
            this.word = word;
        }
        public String getWord() {
            return word;
        }
        public void setWord(String word) {
            this.word = word;
        }
    }
}
