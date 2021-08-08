package com.github.tianjing.baidu.map.common.service.custom;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.service.TileService;
import com.github.tianjing.baidu.map.common.util.H2Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tgtools.data.DataTable;
import tgtools.exceptions.APPErrorException;
import tgtools.util.FileUtil;
import tgtools.util.StringUtil;

import java.io.File;

/**
 * @author 田径
 * @date 2021-04-07 10:08
 * @desc
 **/
public class CustomH2TileService extends CustomSqliteTileService {

    protected TgtoolsBaiduMapProperty downloadBaiduConfig;
    protected Logger logger = LoggerFactory.getLogger(CustomH2TileService.class);
    protected byte[] noPic;


    @Override
    public void setDownloadBaiduConfigBean(TgtoolsBaiduMapProperty pDownloadBaiduConfigBean) {
        downloadBaiduConfig = pDownloadBaiduConfigBean;
        setDataAccessName("H2DATAACCESS");
        try {
            H2Util.initDB(new File(downloadBaiduConfig.getPath()).getCanonicalPath().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
