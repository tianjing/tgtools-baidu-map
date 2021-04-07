package com.github.tianjing.baidu.map.common.config;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author 田径
 * @date 2021-04-07 14:44
 * @desc
 **/
public class TgtoolsBaiduMapPropertyConfig {

    @Bean
    public TgtoolsBaiduMapProperty tgtoolsBaiduMapProperty() {
        return new TgtoolsBaiduMapProperty();
    }

}
