package com.codedose.oag.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesLoader {

    private static final int MASTER_SQL_BATCH_SIZE = 10000;
    private static final int MASTER_PROCESS_QUEUE_SIZE = 100000;

    private static final int RATIONALISE_SQL_BATCH_SIZE = 1000;
    private static final int RATIONALISE_PROCESS_THREAD_COUNT = 3;
    private static final int RATIONALISE_PROCESS_QUEUE_SIZE = 100000;
    private static final int RATIONALISE_DELETE_THREAD_COUNT = 2;
    private static final int RATIONALISE_UPDATE_THREAD_COUNT = 2;
    private static final int RATIONALISE_PRIORITY = 1;

    private static final int DUPE_SQL_BATCH_SIZE = 10000;
    private static final int DUPE_PROCESS_QUEUE_SIZE = 100000;
    private static final int DUPE_PROCESS_THREAD_COUNT = 2;
    private static final int DUPE_UPDATE_THREAD_COUNT = 2;

    private static final int SSIM_SEATSTOTAL_PERCENTAGE = 70;
    private static final int SSIM_SEATSTOTAL_UPPERLIMIT = 700;

    private static final boolean ADJUST_DEP_ARR_TIMES = true;

    static Properties prop = new Properties();

    static {
        try {

            // File jarPath = new File(MyClass.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            // String propertiesPath = jarPath.getParentFile().getAbsolutePath();
            // System.out.println(" propertiesPath-" + propertiesPath);

            System.out.println(System.getProperty("user.dir"));
            // System.out.println(ClassLoader..getProperty("user.dir"));

            prop.load(new FileInputStream("resources/config.properties"));

            // for (Object key : prop.keySet()) {
            // System.out.println(key.toString());
            //
            // System.out.println(prop.getProperty((String) key));
            //
            // }

        } catch (IOException e1) {

            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new PropertiesLoader();
    }

    public static String getDatabaseServer() {

        return (prop.getProperty("database.server"));
    }

    public static int getDatabasePort() {

        return (Integer.parseInt(prop.getProperty("database.port")));
    }

    public static String getDatabaseInstance() {

        return (prop.getProperty("database.dbname"));
    }

    public static String getDatabaseUser() {

        return (prop.getProperty("database.user"));
    }

    public static String getDatabasePassword() {

        return (prop.getProperty("database.password"));
    }

    public static int getRationalisePriority() {

        if (prop.getProperty("com.oag.wdf.rationalise.priority") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.rationalise.priority"));
            } catch (NumberFormatException e) {

                return RATIONALISE_PRIORITY;
            }
        }

        return RATIONALISE_PRIORITY;
    }

    public static int getMasterProcessThreadCount() {

        if (prop.getProperty("com.oag.wdf.master.process.threads") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.master.process.threads"));
            } catch (NumberFormatException e) {

                return 3;
            }
        }

        return 3;
    }

    public static int getMasterUpdateThreadCount() {

        if (prop.getProperty("com.oag.wdf.master.update.threads") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.master.update.threads"));
            } catch (NumberFormatException e) {

                return 2;
            }
        }

        return 2;
    }

    public static int getMasterProcessQueueSize() {

        if (prop.getProperty("com.oag.wdf.master.process.qsize") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.master.process.qsize"));
            } catch (NumberFormatException e) {

                return MASTER_PROCESS_QUEUE_SIZE;
            }
        }

        return MASTER_PROCESS_QUEUE_SIZE;
    }

    public static int getMasterProcessBatchSize() {

        if (prop.getProperty("com.oag.wdf.master.process.batch.size") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.master.process.batch.size"));
            } catch (NumberFormatException e) {

                return MASTER_SQL_BATCH_SIZE;
            }
        }

        return MASTER_SQL_BATCH_SIZE;
    }
    public static int getRationaliseProcessThreadCount() {

        if (prop.getProperty("com.oag.wdf.rationalise.process.threads") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.rationalise.process.threads"));
            } catch (NumberFormatException e) {

                return RATIONALISE_PROCESS_THREAD_COUNT;
            }
        }

        return RATIONALISE_PROCESS_THREAD_COUNT;
    }

    public static int getRationaliseBatchSize() {

        if (prop.getProperty("com.oag.wdf.rationalise.process.batch.size") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.rationalise.process.batch.size"));
            } catch (NumberFormatException e) {

                return RATIONALISE_SQL_BATCH_SIZE;
            }
        }

        return RATIONALISE_SQL_BATCH_SIZE;
    }

    public static int getRationaliseProcessQueueSize() {

        if (prop.getProperty("com.oag.wdf.rationalise.process.qsize") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.rationalise.process.qsize"));
            } catch (NumberFormatException e) {

                return RATIONALISE_PROCESS_QUEUE_SIZE;
            }
        }

        return RATIONALISE_PROCESS_QUEUE_SIZE;
    }

    public static int getRationaliseUpdateThreadCount() {

        if (prop.getProperty("com.oag.wdf.rationalise.update.threads") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.rationalise.update.threads"));
            } catch (NumberFormatException e) {

                return RATIONALISE_UPDATE_THREAD_COUNT;
            }
        }

        return RATIONALISE_UPDATE_THREAD_COUNT;
    }

    public static int getRationaliseDeleteThreadCount() {

        if (prop.getProperty("com.oag.wdf.rationalise.delete.threads") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.rationalise.delete.threads"));
            } catch (NumberFormatException e) {

                return RATIONALISE_DELETE_THREAD_COUNT;
            }
        }

        return RATIONALISE_DELETE_THREAD_COUNT;
    }

    public static int getDupeProcessThreadCount() {

        if (prop.getProperty("com.oag.wdf.dupeprocess.process.threads") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.dupeprocess.process.threads"));
            } catch (NumberFormatException e) {

                return DUPE_PROCESS_THREAD_COUNT;
            }
        }

        return DUPE_PROCESS_THREAD_COUNT;
    }

    public static int getDupeProcessUpdateThreadCount() {

        if (prop.getProperty("com.oag.wdf.dupeprocess.update.threads") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.dupeprocess.update.threads"));
            } catch (NumberFormatException e) {

                return DUPE_UPDATE_THREAD_COUNT;
            }
        }

        return DUPE_UPDATE_THREAD_COUNT;
    }

    public static int getDupeProcessBatchSize() {

        if (prop.getProperty("com.oag.wdf.dupeprocess.batch.size") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.dupeprocess.process.batch.size"));
            } catch (NumberFormatException e) {

                return DUPE_SQL_BATCH_SIZE;
            }
        }

        return DUPE_SQL_BATCH_SIZE;
    }

    public static int getDupeProcessQueueSize() {

        if (prop.getProperty("com.oag.wdf.dupeprocess.qsize") != null) {

            try {

                return Integer.parseInt(prop.getProperty("com.oag.wdf.dupeprocess.qsize"));
            } catch (NumberFormatException e) {

                return DUPE_PROCESS_QUEUE_SIZE;
            }
        }

        return DUPE_PROCESS_QUEUE_SIZE;
    }

    public static int getSSIMToatlSeatsPercentage() {
        if (prop.getProperty("ssim.seatstotal.percentage") != null) {
            try {
                return Integer.parseInt(prop.getProperty("ssim.seatstotal.percentage"));
            } catch (NumberFormatException e) {
                return SSIM_SEATSTOTAL_PERCENTAGE;
            }
        }
        return SSIM_SEATSTOTAL_PERCENTAGE;
    }

    public static int getSSIMToatlSeatsUpperLimit() {
        if (prop.getProperty("ssim.seatstotal.upperlimit") != null) {
            try {
                return Integer.parseInt(prop.getProperty("ssim.seatstotal.upperlimit"));
            } catch (NumberFormatException e) {
                return SSIM_SEATSTOTAL_UPPERLIMIT;
            }
        }
        return SSIM_SEATSTOTAL_UPPERLIMIT;
    }

    public static boolean isAdjustDepArrTimesEnabled(){
        if (prop.getProperty("adjust.dep.arr.times.around.midnight") != null) {
            try {
                return Boolean.parseBoolean(prop.getProperty("adjust.dep.arr.times.around.midnight"));
            } catch (NumberFormatException e) {
                return ADJUST_DEP_ARR_TIMES;
            }
        }
        return ADJUST_DEP_ARR_TIMES;
    }
}

