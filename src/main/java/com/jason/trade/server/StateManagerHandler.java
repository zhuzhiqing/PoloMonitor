package com.jason.trade.server;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jason on 2016/9/27.
 */
public class StateManagerHandler extends Thread {
    private static Logger logger = Logger.getLogger(StateManagerHandler.class.getName());

    @Override
    public void run() {
        while (true) {
//			logger.info("now running task no : "
//					+ TaskCenter.taskState.keySet().size());
            logger.info("need to run url : " + TaskCenter.taskQueue.size());
            logger.info("running url : " + TaskCenter.urlState.size());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            logger.info("=========== "+ df.format(new Date())+" =========== ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }


}
