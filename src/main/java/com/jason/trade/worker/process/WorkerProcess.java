package com.jason.trade.worker.process;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jason on 2016/9/26.
 */
public class WorkerProcess {
    private static final long SLEEPTIME = 2000L;
    private static final int MIN_POOLSIZE = 20; // 最少20个线程
    public static String TASKHOSTNAME = "localhost";
    public static String RESULTHOSTNAME = "localhost";
    private static Logger logger = Logger.getLogger(WorkerProcess.class);
//	private static final int MAX_POOLSIZE = 80; // 最多80个线程

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            if (args.length == 1) {
                TASKHOSTNAME = args[0];
                RESULTHOSTNAME = args[0];
            } else if (args.length == 2) {
                TASKHOSTNAME = args[0];
                RESULTHOSTNAME = args[1];
            }
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(MIN_POOLSIZE);

        while (true) {
            int nowSize = (50 - executor.getQueue().size());

            for (int i = 0; i < nowSize; i++) {
                Runnable worker = new WorkThread();
                executor.execute(worker);
            }

            try {
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
    }

}
