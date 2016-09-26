package com.jason.trade.server;


import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by jason on 2016/9/27.
 */
public class ConversationSaveHandler extends IoHandlerAdapter {
    private static Logger logger = Logger.getLogger(ConversationSaveHandler.class.getName());

    public static void main(String[] args) {

    }


    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        logger.error("ConversationSaveHandler error ", cause);
    }

    //TODO:
    public void messageReceived(IoSession session, Object message) throws Exception {

    }

}
