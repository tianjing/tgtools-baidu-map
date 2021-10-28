package com.github.tianjing.baidu.map.common.service.custom;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.service.TileService;
import com.github.tianjing.baidu.map.common.util.SqliteUtil;
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
public class CustomSqliteTileService implements TileService {

    protected TgtoolsBaiduMapProperty downloadBaiduConfig;
    protected Logger logger = LoggerFactory.getLogger(CustomSqliteTileService.class);
    protected byte[] noPic;
    protected String dataAccessName ="";

    public String getDataAccessName() {
        return dataAccessName;
    }

    public void setDataAccessName(String pDataAccessName) {
        dataAccessName = pDataAccessName;
    }

    @Override
    public void setDownloadBaiduConfigBean(TgtoolsBaiduMapProperty pDownloadBaiduConfigBean) {
        downloadBaiduConfig = pDownloadBaiduConfigBean;
        setDataAccessName("SQLITEDATAACCESS");
        try {
            SqliteUtil.initDB(downloadBaiduConfig.getPath());
        } catch (APPErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getTile(int pX, int pY, int pZoom, String pTheme) {
        try {
            String vSql = String.format("select FILE FROM CUSTOM WHERE X=%s AND Y =%s AND ZOOM=%s AND THEME='%s'", pX, pY, pZoom, pTheme);
            DataTable vTable = tgtools.db.DataBaseFactory.get(dataAccessName).query(vSql);
            if (DataTable.hasData(vTable)) {
                return (byte[]) vTable.getRow(0).getValue("FILE");
            }
            logger.info(String.format("获取切片地图无效！x:%s y:%s zoom:%s theme:%s", pX, pY, pZoom, pTheme));
            return new byte[0];
        } catch (Exception e) {
            logger.error(String.format("获取切片地图出错！x:%s y:%s zoom:%s theme:%s", pX, pY, pZoom, pTheme), e);
            return new byte[0];
        }
    }

    @Override
    public void saveTile(int pX, int pY, int pZoom, String pTheme, byte[] pImage) {
        try {
            String vSql = String.format("insert into CUSTOM (X,Y,ZOOM,THEME,FILE) VALUES(%s ,%s ,%s ,'%s',?)", pX, pY, pZoom, pTheme);
            tgtools.db.DataBaseFactory.get(dataAccessName).executeBlob(vSql, pImage);
        } catch (Exception e) {
            if (e.toString().indexOf("constraint") < 1) {
                logger.error(String.format("保存切片出错！x:%s y:%s zoom:%s theme:%s", pX, pY, pZoom, pTheme), e);
            }
        }
    }


    @Override
    public boolean hasTile(int pX, int pY, int pZoom, String pTheme) {
        try {
            String vSql = String.format("select count(*) num FROM CUSTOM WHERE X=%s AND Y =%s AND ZOOM=%s AND THEME='%s'", pX, pY, pZoom, pTheme);
            DataTable vTable = tgtools.db.DataBaseFactory.get(dataAccessName).query(vSql);
            Integer vCount = (Integer) vTable.getRow(0).getValue("NUM");
            return vCount > 0;
        } catch (Exception e) {
            logger.error(String.format("查询地图切片存在出错！x:%s y:%s zoom:%s theme:%s", pX, pY, pZoom, pTheme), e);
        }
        return false;
    }

    protected byte[] getNoPic() {
        if (null != noPic && noPic.length > 0) {
            return noPic;
        }

        if (StringUtil.isNotEmpty(downloadBaiduConfig.getNoPic())) {
            return new byte[0];
        }

        File vFile = new File(downloadBaiduConfig.getNoPic());
        if (vFile.exists() && vFile.isFile()) {
            try {
                noPic = FileUtil.readFileToByte(downloadBaiduConfig.getNoPic());
                return noPic;
            } catch (Throwable e) {
                return new byte[0];
            }
        }
        return new byte[0];
    }

}
