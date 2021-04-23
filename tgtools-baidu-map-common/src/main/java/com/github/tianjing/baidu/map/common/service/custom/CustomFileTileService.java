package com.github.tianjing.baidu.map.common.service.custom;

import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.service.TileService;
import com.github.tianjing.baidu.map.common.util.FolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tgtools.exceptions.APPErrorException;
import tgtools.util.FileUtil;
import tgtools.util.StringUtil;

import java.io.File;

/**
 * @author 田径
 * @date 2021-04-07 10:08
 * @desc
 **/
public class CustomFileTileService implements TileService {
    protected TgtoolsBaiduMapProperty tgtoolsBaiduMapProperty;
    protected byte[] noPic;
    protected Logger logger = LoggerFactory.getLogger(CustomFileTileService.class);

    @Override
    public void setDownloadBaiduConfigBean(TgtoolsBaiduMapProperty pTgtoolsBaiduMapProperty) {
        tgtoolsBaiduMapProperty = pTgtoolsBaiduMapProperty;
    }

    @Override
    public byte[] getTile(int pX, int pY, int pZoom, String pTheme) {
        String vPath = tgtoolsBaiduMapProperty.getPath();
        File vDir = FolderUtil.createFolder(vPath, pX, pZoom);
        File vFile = initFile(vDir, pY);
        if (vFile.exists()) {
            try {
                return FileUtil.readFileToByte(vFile.toString());
            } catch (Exception e) {
                logger.error(String.format("获取切片地图出错！x:%s y:%s zoom:%s theme:%s", pX, pY, pZoom, pTheme), e);
                return getNoPic();
            }
        }
        return getNoPic();
    }

    protected byte[] getNoPic() {
        if (null != noPic && noPic.length > 0) {
            return noPic;
        }

        if (StringUtil.isNotEmpty(tgtoolsBaiduMapProperty.getNoPic())) {
            return new byte[0];
        }

        File vFile = new File(tgtoolsBaiduMapProperty.getNoPic());
        if (vFile.exists() && vFile.isFile()) {
            try {
                noPic = FileUtil.readFileToByte(tgtoolsBaiduMapProperty.getNoPic());
                return noPic;
            } catch (Throwable e) {
                return new byte[0];
            }
        }
        return new byte[0];
    }

    @Override
    public void saveTile(int pX, int pY, int pZoom, String pTheme, byte[] pImage) {
        String vPath = tgtoolsBaiduMapProperty.getPath();
        File vDir = FolderUtil.createFolder(vPath, pX, pZoom);
        File vFile = initFile(vDir, pY);

        try {
            FileUtil.writeFile(vFile.toString(), pImage);
        } catch (APPErrorException e) {
            logger.error(String.format("保存切片出错！x:%s y:%s zoom:%s theme:%s", pX, pY, pZoom, pTheme), e);
        }
    }

    @Override
    public boolean hasTile(int pX, int pY, int pZoom, String pStyle) {
        String vPath = tgtoolsBaiduMapProperty.getPath();
        File vDir = FolderUtil.createFolder(vPath, pX, pZoom);
        File vFile = initFile(vDir, pY);
        return vFile.exists();
    }


    protected File initFile(File pLocalFileDir, int pY) {
        return new File(pLocalFileDir + "/" + pY + ".png");
    }
}
