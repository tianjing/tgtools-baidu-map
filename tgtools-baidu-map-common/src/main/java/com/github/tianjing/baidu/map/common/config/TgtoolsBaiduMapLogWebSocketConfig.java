package com.github.tianjing.baidu.map.common.config;

import com.github.tianjing.baidu.map.common.util.StompUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author 田径
 * @date 2021-08-08 14:49
 * @desc
 **/
@Configuration
@EnableWebSocketMessageBroker
public class TgtoolsBaiduMapLogWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册stomp端点，主要是起到连接作用
     *
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry
                //端点名称
                .addEndpoint("/websocket/log")
                //.setHandshakeHandler() 握手处理，主要是连接的时候认证获取其他数据验证等
                //.addInterceptors() 拦截处理，和http拦截类似
                //跨域
                .setAllowedOrigins("*")
                .withSockJS(); //使用sockJS

    }

    @Autowired
    public void getSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        StompUtil.setMessagingTemplate(simpMessagingTemplate);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 配置消息代理
         * 启动简单Broker，消息的发送的地址符合配置的前缀来的消息才发送到这个broker
         */
        registry.enableSimpleBroker("/topic", "/queue");
    }


}
