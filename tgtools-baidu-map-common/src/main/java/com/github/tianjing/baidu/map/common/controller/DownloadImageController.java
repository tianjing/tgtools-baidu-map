package com.github.tianjing.baidu.map.common.controller;

import com.github.tianjing.baidu.map.common.bean.Position;
import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.dowloader.MapDownloader;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 田径
 * @date 2021-04-06 11:55
 * @desc
 **/
@RequestMapping("/baidu/download")
public class DownloadImageController {

    protected TgtoolsBaiduMapProperty tgtoolsBaiduMapProperty;

    public TgtoolsBaiduMapProperty getDownloadBaiduConfig() {
        return tgtoolsBaiduMapProperty;
    }

    public void setDownloadBaiduConfig(TgtoolsBaiduMapProperty pDownloadBaiduConfig) {
        tgtoolsBaiduMapProperty = pDownloadBaiduConfig;
    }

    @RequestMapping("/onePosition")
    public void getTile(HttpServletRequest pRequest, HttpServletResponse pResponse
            , @RequestParam("swlng") Double pSwlng, @RequestParam("swlat") Double pSwlat
            , @RequestParam("nelng") Double pNelng, @RequestParam("nelat") Double pNelat
            , @RequestParam("level_start") Integer pLevelStart, @RequestParam("level_end") Integer pLevelEnd
            , @RequestParam("style") String pStyle) throws IOException {

        TgtoolsBaiduMapProperty vTgtoolsBaiduMapProperty = new TgtoolsBaiduMapProperty();
        BeanUtils.copyProperties(tgtoolsBaiduMapProperty, vTgtoolsBaiduMapProperty);
        vTgtoolsBaiduMapProperty.setRegion(null);
        vTgtoolsBaiduMapProperty.setLevel(pLevelStart + "-" + pLevelEnd);
        vTgtoolsBaiduMapProperty.setTheme(pStyle);
        vTgtoolsBaiduMapProperty.setDownloadPosition(new ArrayList() {{
            add(new Position("", pSwlng, pSwlat, pNelng, pNelat));
        }});


        MapDownloader vMapDownloader = new MapDownloader();
        vMapDownloader.setDownloadConfigBean(vTgtoolsBaiduMapProperty);
        vMapDownloader.start();
    }


}
