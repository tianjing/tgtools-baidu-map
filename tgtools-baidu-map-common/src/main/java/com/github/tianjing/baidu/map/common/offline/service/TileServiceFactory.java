package com.github.tianjing.baidu.map.common.offline.service;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.offline.service.custom.CustomFileTileService;
import com.github.tianjing.baidu.map.common.offline.service.custom.CustomSqliteTileService;

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
