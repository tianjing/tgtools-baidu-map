package com.github.tianjing.baidu.map.common.util;

import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * @author 田径
 * @date 2021-08-08 15:48
 * @desc
 **/
public class StompUtil {
    public static SimpMessagingTemplate simpMessagingTemplate;

    public static SimpMessagingTemplate getMessagingTemplate() {
        return simpMessagingTemplate;
    }

    public static void setMessagingTemplate(SimpMessagingTemplate pMessagingTemplate) {
        simpMessagingTemplate = pMessagingTemplate;
    }

    public static void sendBroadcast(String pDestination,String pMesspage) {
        simpMessagingTemplate.convertAndSend(pDestination,pMesspage);
    }
    public static void sendDefaultBroadcast(String pMesspage) {
        sendBroadcast("/topic/broadcast",pMesspage);
    }
}
