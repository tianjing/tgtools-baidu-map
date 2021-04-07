package com.github.tianjing.baidu.map.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author 田径
 * @date 2021-04-05 11:12
 * @desc
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DownloaderApplication {

    protected static final Logger log = LoggerFactory.getLogger(DownloaderApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(DownloaderApplication.class);
        log.info("=============================================================================================");
    }

}
