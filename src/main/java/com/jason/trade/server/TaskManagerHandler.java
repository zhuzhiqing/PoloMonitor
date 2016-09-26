package com.jason.trade.server;

import com.jason.trade.po.Task;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by jason on 2016/9/27.
 */
public class TaskManagerHandler extends Thread {

    private static Logger logger = Logger.getLogger(TaskManagerHandler.class.getName());

    private final int EXPIRYTIME = 120;

    private List<Task> tasks = Collections.synchronizedList(new ArrayList<Task>());

    @Override
    public void run() {
        addTasks();
        for (; ; ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e);
            }
            int second = Calendar.getInstance().get(Calendar.SECOND);

            if (second % EXPIRYTIME == 0) {
                checkExpiryUrl();
                stopTasks();
            }

            if (second % 5 == 0) {             //每隔5秒添加一次任务
                addTasks();
            }
        }
    }

    /**
     * 检查运行URL中是否有过期，过期需要重新运行
     */
    private void checkExpiryUrl() {

    }

    /**
     * 移除已经完成的任务
     */
    private void stopTasks() {

        for (Task t : TaskCenter.taskCount.keySet()) {

        }
    }


    /**
     * 添加任务
     */
    private void addTasks() {

    }
}
