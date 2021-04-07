package com.github.tianjing.baidu.map.downloader.config;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.dowloader.MapDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 田径
 * @date 2021-04-05 11:20
 * @desc
 **/

/**
 * 测试下载任务
 */
@Configuration
public class DownloadStarterConfig {

    @Bean
    public DownloadStarterApplicationRunner downloadStarterApplicationRunner() {
        return new DownloadStarterApplicationRunner();
    }


    public static class DownloadStarterApplicationRunner implements ApplicationRunner {

        @Autowired
        private TgtoolsBaiduMapProperty tgtoolsBaiduMapProperty;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            MapDownloader vMapDownloader = new MapDownloader();
            vMapDownloader.setDownloadConfigBean(tgtoolsBaiduMapProperty);
            vMapDownloader.start();
        }
    }
}
