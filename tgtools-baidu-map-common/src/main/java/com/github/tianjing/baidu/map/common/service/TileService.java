package com.github.tianjing.baidu.map.common.service;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;

public interface TileService {


    void setDownloadBaiduConfigBean(TgtoolsBaiduMapProperty pTgtoolsBaiduMapProperty);

    /**
     * 获取切片图层
     *
     * @param pX
     * @param pY
     * @param pZoom
     * @param pTheme
     * @return
     */
    byte[] getTile(int pX, int pY, int pZoom, String pTheme);

    /**
     * 保存切片图层
     *
     * @param pX
     * @param pY
     * @param pZoom
     * @param pStyle
     * @param pImage
     */
    void saveTile(int pX, int pY, int pZoom, String pStyle, byte[] pImage);

    /**
     * 是否存在
     * @param pX
     * @param pY
     * @param pZoom
     * @param pStyle
     * @return
     */
    boolean hasTile(int pX, int pY, int pZoom, String pStyle);

}
