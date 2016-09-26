package com.jason.trade.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

/**
 * 结果中心（结果接收+URL接收），使用MINA框架
 * Created by jason on 2016/9/27.
 */
public class ResultCenter {

    private static Logger logger = Logger.getLogger(ResultCenter.class.getName());
    public static final int CONVERSATIONSAVEPORT = 30040;

    public static void main(String args[]) {
        //启动任务中心
        startConversationSaveCenter();
    }

    private static void startConversationSaveCenter(){
        try{
            IoAcceptor queueAcceptor = new NioSocketAcceptor();
            queueAcceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(
                            new ObjectSerializationCodecFactory()));

            queueAcceptor.setHandler(new ConversationSaveHandler());

            queueAcceptor.getSessionConfig().setReadBufferSize(2048);
            queueAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            queueAcceptor.bind(new InetSocketAddress(CONVERSATIONSAVEPORT));

            logger.info("结果保存中心启动，占用端口："+CONVERSATIONSAVEPORT);
        }catch (Exception e) {
            logger.info("结果保存中心启动失败  ",e);
        }
    }
}
