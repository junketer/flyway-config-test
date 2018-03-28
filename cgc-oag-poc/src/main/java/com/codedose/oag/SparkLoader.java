package com.codedose.oag;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import static com.codedose.oag.SparkLoader.Profile.CLUSTER;
import static com.codedose.oag.SparkLoader.Profile.LOCAL;

@SuppressWarnings("WeakerAccess")
public class SparkLoader {

    public enum Profile {
        LOCAL,
        CLUSTER,
        CLUSTER_PERF;

        public String code() {
            return name().toLowerCase();
        }

        public static Profile get(String code) {
            for (Profile profile : values()) {
                if (profile.name().equalsIgnoreCase(code)) {
                    return profile;
                }
            }
            throw new RuntimeException("unknown profile " + code);
        }
    }

    public static SparkSession createOrGetSession() {
        Profile profile = activeProfile();
        SparkSession.Builder confBuilder = SparkSession.builder();

        SparkConf sparkConf = new SparkConf();

        if(profile == LOCAL) {
            sparkConf.set("spark.sql.shuffle.partitions", "" + Runtime.getRuntime().availableProcessors());
            confBuilder = confBuilder
                .master("local[3]");
        }

        return confBuilder
                .config(sparkConf)
                .getOrCreate();
    }

    public static Profile activeProfile() {
        return Profile.get(System.getProperty("profile", CLUSTER.code()));
    }
}
