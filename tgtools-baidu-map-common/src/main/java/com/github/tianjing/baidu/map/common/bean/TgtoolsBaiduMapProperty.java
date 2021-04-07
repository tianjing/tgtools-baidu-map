package com.github.tianjing.baidu.map.common.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author 田径
 * @date 2021-04-05 12:07
 * @desc
 **/
public class TgtoolsBaiduMapProperty {
    /**
     * 最大线程数量
     */
    @Value("${map.baidu.download.thread:100}")
    private Integer thread;

    @Value("${map.baidu.download.path:}")
    private String path;
    /**
     * # 下载层级:[3,...,19]
     */
    @Value("${map.baidu.download.level:1,2,3,4,5,6,7,8,9,10,11,12,13}")
    private String level;
    /**
     * 下载规模:world/china
     */
    @Value("${map.baidu.download.scale:china}")
    private String scale;

    /**
     * 下载国内区域集合:参考帮助文档
     * 安徽,福建,甘肃,广东,广西,贵州,海南,河北,河南,黑龙江,湖北,湖南,江苏,江西,吉林,辽宁,内蒙古,宁夏,青海,山东,山西,陕西,四川,西藏,新疆,云南,浙江,香港,澳门,台湾
     */
    @Value("${map.baidu.download.region:江苏,安徽,上海,浙江}")
    private String region;

    /**
     * 下载主题:normal（下载最快）,light,dark,redalert,googlelite,grassgreen,midnight,pink
     */
    @Value("${map.baidu.download.theme:normal}")
    private String theme;

    @Value("${map.baidu.download.save-mode:sqlite}")
    private String saveMode;
    /**
     * 下载的瓦片是否覆盖同名文件
     */
    @Value("${map.baidu.download.cover:true}")
    private Boolean cover;


    public Integer getThread() {
        return thread;
    }

    public void setThread(Integer pThread) {
        thread = pThread;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String pPath) {
        path = pPath;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String pLevel) {
        level = pLevel;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String pScale) {
        scale = pScale;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String pRegion) {
        region = pRegion;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String pTheme) {
        theme = pTheme;
    }

    public Boolean getCover() {
        return cover;
    }

    public void setCover(Boolean pCover) {
        cover = pCover;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String pSaveMode) {
        saveMode = pSaveMode;
    }
}
