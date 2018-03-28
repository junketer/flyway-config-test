import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class UnitTestSample {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();

        sparkConf.setAppName("Hello Spark");
        sparkConf.setMaster("local");

        JavaSparkContext context = new JavaSparkContext(sparkConf);

        JavaRDD<String> input = context.textFile("cards-unit-test.txt");

        JavaPairRDD<String, Integer> suitsAndValues = runETL(input);
        JavaPairRDD<String, Integer> counts = processData(suitsAndValues);
        counts.foreach((result -> System.out.println(result)));
    }

    public static JavaPairRDD<String, Integer> runETL(
            JavaRDD<String> input) {
        JavaPairRDD<String, Integer> suitsAndValues = input.mapToPair(w -> {
            String[] split = w.split("\t");

            int cardValue = Integer.parseInt(split[0]);
            String cardSuit = split[1];

            return new Tuple2<String, Integer>(cardSuit, cardValue);
        });

        return suitsAndValues;
    }

    public static JavaPairRDD<String, Integer> processData(JavaPairRDD<String, Integer> data) {
        JavaPairRDD<String, Integer> counts = data.reduceByKey((x,y) -> x + y);
        return counts;
    }
}
