package com.github.tianjing.baidu.map.common.dowloader;


import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.bean.Tile;
import com.github.tianjing.baidu.map.common.offline.service.TileService;
import com.github.tianjing.baidu.map.common.offline.service.TileServiceFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhoun945@gmail
 * @Date 2018-03-27
 */
public class TileDownloader {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Random random = new Random();
    private HttpClientGenerator httpClientGenerator;
    private Integer maxRetryTime = 3;
    private AtomicLong completeSize = new AtomicLong();

    private String downloadPath;
    private String theme;
    private boolean cover;
    private TgtoolsBaiduMapProperty tgtoolsBaiduMapProperty;
    private TileService tileService;


    public TileDownloader(TgtoolsBaiduMapProperty pTgtoolsBaiduMapProperty) {
        tgtoolsBaiduMapProperty = pTgtoolsBaiduMapProperty;
        httpClientGenerator = new HttpClientGenerator(tgtoolsBaiduMapProperty);
        downloadPath = tgtoolsBaiduMapProperty.getPath();
        theme = tgtoolsBaiduMapProperty.getTheme();
        cover = tgtoolsBaiduMapProperty.getCover();
        tileService = TileServiceFactory.getTileService(tgtoolsBaiduMapProperty);
    }

    public void download(Tile tile, int retryTime) {
        try {
            if (!cover && tileService.hasTile(tile.getX(), tile.getY(), tile.getZ(), theme)) {
                return;
            }

            RequestBuilder requestBuilder = RequestBuilder.get().setUri(createUrl(tile, theme));
            RequestConfig.Builder builder =
                    RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.IGNORE_COOKIES);
            requestBuilder.setConfig(builder.build());
            HttpResponse httpResponse = httpClientGenerator.getClient().execute(requestBuilder.build());
            HttpEntity entity = httpResponse.getEntity();

            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200 && entity != null) {
                InputStream inputStream = entity.getContent();
                byte[] vData = StreamUtils.copyToByteArray(inputStream);
                tileService.saveTile(tile.getX(), tile.getY(), tile.getZ(), theme, vData);

                //FileUtils.copyToFile(inputStream, png);
                long sz = completeSize.addAndGet(1);
                if (sz % 1000 == 0) {
                    logger.info("下载完成：{}，tile：{}", sz, tile.toString());
                }
            } else {
                if (retryTime > maxRetryTime) {
                    logger.error("下载失败，状态码：{}，tile：{}", statusCode, tile.toString());
                } else {
                    download(tile, retryTime++);
                }
            }
        } catch (IOException e) {
            if (retryTime > maxRetryTime) {
                logger.error("下载瓦片失败：{}", tile.toString());
            } else {
                download(tile, retryTime++);
            }
        }
    }

    //http://online2.map.bdimg.com/tile/?qt=tile&x=12655&y=4710&z=16&styles=pl
    //http://api.map.baidu.com/customimage/tile?customid=light
    private String createUrl(Tile tile, String theam) {
        StringBuffer url = new StringBuffer();
        if (theam.equals("normal")) {
            url.append("http://online")
                    .append(random.nextInt(10))
                    .append(".map.bdimg.com/tile/?qt=tile&styles=pl")
                    .append("&z=").append(tile.getZ())
                    .append("&x=").append(tile.getX())
                    .append("&y=").append(tile.getY());
        } else {
            url.append("http://api.map.baidu.com/customimage/tile?customid=" + theam)
                    .append("&z=").append(tile.getZ())
                    .append("&x=").append(tile.getX())
                    .append("&y=").append(tile.getY());
        }
        return url.toString();
    }

}