package com.github.tianjing.baidu.map.downloader.config;

import com.github.tianjing.baidu.map.common.bean.DownloadBaiduConfigBean;
import com.github.tianjing.baidu.map.common.controller.CustomImageController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 田径
 * @date 2021-04-07 10:03
 * @desc
 **/
@Configuration
public class MapRestConfig {

    @Bean
    public CustomImageController customImageController(DownloadBaiduConfigBean pDownloadBaiduConfig) {
        CustomImageController vCustomImageController = new CustomImageController();
        vCustomImageController.setDownloadBaiduConfig(pDownloadBaiduConfig);
        return vCustomImageController;
    }

}
