package com.jason.trade.server;

import com.jason.trade.po.Task;
import com.jason.trade.po.UrlRequest;
import com.jason.trade.po.UrlState;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jason on 2016/8/29.
 */
public class TaskCenter {

    public static final int QUEUEPORT = 30010;
    public static final int STATEPORT = 30020;
    public static final int URLSAVEPORT = 30030;
    public static final int WEBPORT = 30090;
    public static Map<Task, Integer> taskCount = new ConcurrentHashMap<Task, Integer>(); // 任务运行计数，在taskQueue队列进出时计数
    public static Queue<UrlRequest> taskQueue = new ConcurrentLinkedQueue<UrlRequest>(); // URL队列，包括了任务和后续URL
    public static List<UrlState> urlState = Collections
            .synchronizedList(new ArrayList<UrlState>()); // 当前运行的URL
    public static Map<Task, List<UrlState>> taskState = new ConcurrentHashMap<Task, List<UrlState>>(); // 当前运行的Task状态
    private static Logger logger = Logger.getLogger(TaskCenter.class.getName());

    public static void main(String[] args) {
        // 启动任务中心端
        startUrlStateCenter();
        startUrlGetterCenter();
        startUrlSaveCenter();
        startWebCenter();

        // 启动任务&状态管理线程[守护]
        TaskManagerHandler taskHandler = new TaskManagerHandler();
        taskHandler.setDaemon(true);
        taskHandler.start();
    }

    public static void startUrlStateCenter() {

    }

    public static void startUrlGetterCenter() {

    }

    public static void startUrlSaveCenter() {

    }

    public static void startWebCenter() {

    }
}
