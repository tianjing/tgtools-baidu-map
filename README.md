# 本项目主要 百度图层下载 展示 项目结合的组件

本项目主要是解决 离线地图下载和浏览一个整体方案。
下载离线地图
然后通过 leaflet 的百度扩展进行离线地图浏览


##模块说明
###tgtools-baidu-map-common
下载和浏览的主要实现，包含了 java 和 js （webjars方式）


###tgtools-baidu-map-downloader
只是一个一个下载工具，同时提过浏览，但是下载和浏览是可以分开的。这只是个demo


##注意
地图主要还是通过sqlite 进行存储比较好，如果图片文件过多，复制会很麻烦。
sqlite文件复制比较容易。



##鸣谢
前端参考：  
https://blog.csdn.net/qq_34107571/article/details/90262632

下载器参考：  
https://github.com/zhoun945/BaiduMapDownloader


