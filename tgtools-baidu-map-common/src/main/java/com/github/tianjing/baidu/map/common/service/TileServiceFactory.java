package com.github.tianjing.baidu.map.common.service;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.service.custom.CustomFileTileService;
import com.github.tianjing.baidu.map.common.service.custom.CustomH2TileService;
import com.github.tianjing.baidu.map.common.service.custom.CustomSqliteTileService;

/**
 * @author 田径
 * @date 2021-04-07 10:33
 * @desc
 **/
public class TileServiceFactory {

    protected static TileService getTileService(String pSaveMode) {
        switch (pSaveMode) {
            case "file":
                return new CustomFileTileService();
            case "h2":
                return new CustomH2TileService();
            case "sqlite":
            default:
                return new CustomSqliteTileService();

        }
    }


    public static TileService getTileService(TgtoolsBaiduMapProperty pDownloadBaiduConfigBean) {
        TileService vTileService = getTileService(pDownloadBaiduConfigBean.getSaveMode());
        vTileService.setDownloadBaiduConfigBean(pDownloadBaiduConfigBean);
        return vTileService;
    }
}
