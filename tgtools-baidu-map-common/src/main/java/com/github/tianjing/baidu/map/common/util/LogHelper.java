package com.github.tianjing.baidu.map.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tgtools.util.StringUtil;

/**
 * @author 田径
 * @date 2021-08-08 15:48
 * @desc
 **/
public class LogHelper {
    private final static Logger logger = LoggerFactory.getLogger(LogHelper.class);

    public static void info(String pMessage) {
        logger.info(pMessage);
        StompUtil.sendDefaultBroadcast(pMessage);
    }

    public static void info(String pMessage, Object... pValue) {
        pMessage = StringUtil.replace(pMessage, "{}", "%s");
        String vMessage = String.format(pMessage, pValue);
        logger.info(vMessage);
        StompUtil.sendDefaultBroadcast(vMessage);
    }

    public static void error(String pMessage) {
        logger.error(pMessage);
        StompUtil.sendDefaultBroadcast(pMessage);
    }

    public static void error(String pMessage, Object... pValue) {
        pMessage = StringUtil.replace(pMessage, "{}", "%s");
        String vMessage = String.format(pMessage, pValue);
        logger.error(vMessage);
        StompUtil.sendDefaultBroadcast(vMessage);
    }

    public static void error(String pMessage, Throwable pThrowable) {
        logger.error(pMessage, pThrowable);
        StompUtil.sendDefaultBroadcast(pMessage);
    }

}
