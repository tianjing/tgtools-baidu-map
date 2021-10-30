package com.github.tianjing.baidu.map.common.config;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.controller.CustomImageController;
import com.github.tianjing.baidu.map.common.controller.DownloadImageController;
import com.github.tianjing.baidu.map.common.util.LogHelper;
import org.springframework.context.annotation.Bean;

/**
 * @author 田径
 * @date 2021-04-07 10:03
 * @desc
 **/
public class TgtoolsBaiduMapViewRestConfig {

    @Bean
    public CustomImageController customImageController(TgtoolsBaiduMapProperty pTgtoolsBaiduMapProperty) {
        System.out.println("customImageController");
        LogHelper.info("customImageController");
        tgtools.util.LogHelper.info("", "customImageController", "");
        CustomImageController vCustomImageController = new CustomImageController();
        vCustomImageController.setDownloadBaiduConfig(pTgtoolsBaiduMapProperty);
        return vCustomImageController;
    }

    @Bean
    public DownloadImageController downloadImageController(TgtoolsBaiduMapProperty pTgtoolsBaiduMapProperty) {
        DownloadImageController vDownloadImageController = new DownloadImageController();
        vDownloadImageController.setDownloadBaiduConfig(pTgtoolsBaiduMapProperty);
        return vDownloadImageController;
    }
}
