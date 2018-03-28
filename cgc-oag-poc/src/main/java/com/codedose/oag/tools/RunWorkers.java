package com.codedose.oag.tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;
//TODO change command to run on LINUX!!!
/**
 * Run spark workers connected to master defined in MASTER_URL
 * WORKERS_COUNT - number of workers connected to the master
 *
 * */
public class RunWorkers {

    // Change master IP and spark bin directory!!!
    private static final String MASTER_URL = "spark://192.168.1.42:7077";
    private static final String SPARK_DIR = "C:\\Users\\daniel\\Downloads\\spark-2.2.0-bin-hadoop2.7\\bin\\";

    private static final int WORKERS_COUNT = 6;
    private static final int CORE_PER_WORKER = 4;
    private static final String MEMORY_PER_WORKER = "10g";
    private static final int THREAD_WAIT = 5000;

    public static void main(String[] args) throws Exception {
        System.out.println("HELLO");

        IntStream.range(0, WORKERS_COUNT).forEach(workerIndex -> {
            System.out.println("Start worker [" + workerIndex + "]");
            ProcessBuilder builder = createWorkerProcessBuilder();
            try {
                Process p = builder.start();
                logEvents(p, workerIndex);
                Thread.currentThread().sleep(THREAD_WAIT);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static ProcessBuilder createWorkerProcessBuilder() {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", SPARK_DIR + "spark-class org.apache.spark.deploy.worker.Worker -c " + CORE_PER_WORKER + " -m " + MEMORY_PER_WORKER + " " + MASTER_URL);
        builder.redirectErrorStream(true);
        return builder;
    }

    private static void logEvents(Process p, int index) throws IOException {
        Runnable task2 = () -> {
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                try {
                    line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    System.out.println("[" + index + "] " + line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };// start the thread
        new Thread(task2).start();

    }
}
