package com.github.tianjing.baidu.map.common.config;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.controller.CustomImageController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 田径
 * @date 2021-04-07 10:03
 * @desc
 **/
@Configuration
public class TgtoolsBaiduMapViewRestConfig {

    @Bean
    public CustomImageController customImageController(TgtoolsBaiduMapProperty pTgtoolsBaiduMapProperty) {
        CustomImageController vCustomImageController = new CustomImageController();
        vCustomImageController.setDownloadBaiduConfig(pTgtoolsBaiduMapProperty);
        return vCustomImageController;
    }

}
