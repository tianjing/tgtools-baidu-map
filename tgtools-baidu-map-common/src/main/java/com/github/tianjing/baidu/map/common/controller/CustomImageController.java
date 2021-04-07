package com.github.tianjing.baidu.map.common.controller;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.service.TileServiceFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 田径
 * @date 2021-04-06 11:55
 * @desc
 **/

@RequestMapping("/baidu/custom")
public class CustomImageController {


    protected TgtoolsBaiduMapProperty tgtoolsBaiduMapProperty;

    public TgtoolsBaiduMapProperty getDownloadBaiduConfig() {
        return tgtoolsBaiduMapProperty;
    }

    public void setDownloadBaiduConfig(TgtoolsBaiduMapProperty pDownloadBaiduConfig) {
        tgtoolsBaiduMapProperty = pDownloadBaiduConfig;
    }

    @RequestMapping("/tile")
    public void getTile(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
        String vX = pRequest.getParameter("x");
        String vY = pRequest.getParameter("y");
        String vZ = pRequest.getParameter("z");
        String vCustomId = pRequest.getParameter("customid");
        System.out.println("x:" + vX + ";y:" + vY + ";z:" + vZ + ";customid:" + vCustomId);

        pResponse.reset();
        pResponse.setContentType("image/png");

        pResponse.getOutputStream().write(
                TileServiceFactory.getTileService(tgtoolsBaiduMapProperty).getTile(Integer.valueOf(vX), Integer.valueOf(vY), Integer.valueOf(vZ), vCustomId)
        );
    }
}
