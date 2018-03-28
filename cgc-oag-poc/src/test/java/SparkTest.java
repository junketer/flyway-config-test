import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.junit.Test;

import com.holdenkarau.spark.testing.JavaRDDComparisons;
import com.holdenkarau.spark.testing.SharedJavaSparkContext;

import scala.Serializable;
import scala.Tuple2;
import scala.reflect.ClassTag;

public class SparkTest extends SharedJavaSparkContext implements
        Serializable {
    private static final long serialVersionUID = -5681683598336701496L;
    private static final ClassTag<Tuple2<String, Integer>> tag =
            scala.reflect.ClassTag$.MODULE$
                    .apply(Tuple2.class);

    // Test for ETL data
    @Test
    public void verifyMapTest() {
        // Create and run the test
        List<String> input = Arrays.asList("1\tHeart", "2\tDiamonds");
        JavaRDD<String> inputRDD = jsc().parallelize(input);
        JavaPairRDD<String, Integer> result = UnitTestSample
                .runETL(inputRDD);

        System.out.println(result.toString());

        // Create the expected output
        List<Tuple2<String, Integer>> expectedInput = Arrays.asList(
                new Tuple2<String, Integer>("Heart", 1),
                new Tuple2<String, Integer>("Diamonds", 2));
        JavaPairRDD<String, Integer> expectedRDD = jsc()
                .parallelizePairs(expectedInput);

        // Run the assertions on the result and expected
        JavaRDDComparisons.assertRDDEquals(
                JavaRDD.fromRDD(JavaPairRDD.toRDD(result), tag),
                JavaRDD.fromRDD(JavaPairRDD.toRDD(expectedRDD), tag));
    }

    // Test for counting
    @Test
    public void verifyDataProcess() {
        List<Tuple2<String, Integer>> inputList = Arrays.asList(
                new Tuple2<String, Integer>("Heart", 1),
                new Tuple2<String, Integer>("Diamonds", 2),
                new Tuple2<String, Integer>("Diamonds", 5));
        JavaPairRDD<String, Integer> input = jsc()
                .parallelizePairs(inputList);
       // expected result
        List<Tuple2<String, Integer>> expectedOutputList = Arrays.asList(
                new Tuple2<String, Integer>("Heart", 1),
                new Tuple2<String, Integer>("Diamonds", 7));
        JavaPairRDD<String, Integer> expectedResult = jsc()
                .parallelizePairs(expectedOutputList);
        JavaPairRDD<String, Integer> result = UnitTestSample.processData(input);

        // compare
        // Run the assertions on the result and expected
        JavaRDDComparisons.assertRDDEquals(
                JavaRDD.fromRDD(JavaPairRDD.toRDD(expectedResult), tag),
                JavaRDD.fromRDD(JavaPairRDD.toRDD(result), tag));

    }
}