package com.github.tianjing.baidu.map.common.util;

import java.io.File;

/**
 * @author 田径
 * @date 2021-04-07 15:28
 * @desc
 **/
public class FolderUtil {


    public static File createFolder(String pPath, int pX, int pZoom) {
        return createFolder(pPath + "/" + pZoom + "/" + pX);
    }


    public static File createFolder(String pPath) {
        File vLocalFileFolder = new File(pPath);
        if (!vLocalFileFolder.exists()) {
            vLocalFileFolder.mkdirs();
        }
        return vLocalFileFolder;
    }

}
