package com.github.tianjing.baidu.map.common.dowloader;

import com.github.tianjing.baidu.map.common.bean.Position;
import com.github.tianjing.baidu.map.common.bean.TgtoolsBaiduMapProperty;
import com.github.tianjing.baidu.map.common.bean.Tile;
import com.github.tianjing.baidu.map.common.util.ThreadPool;
import com.github.tianjing.baidu.map.common.util.TileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tgtools.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 田径
 * @date 2021-04-05 11:52
 * @desc
 **/
public class MapDownloader {

    private final Logger logger = LoggerFactory.getLogger(MapDownloader.class);
    private BlockingQueue<Tile> tileQueue = new LinkedBlockingQueue<>();
    private int maxQueueSize = 1000000;
    private ThreadPool threadPool;
    private TileDownloader downloader;
    private int threadSize;
    private TgtoolsBaiduMapProperty tgtoolsBaiduMapProperty;

    public TgtoolsBaiduMapProperty getTgtoolsBaiduMapProperty() {
        return tgtoolsBaiduMapProperty;
    }

    public void setDownloadConfigBean(TgtoolsBaiduMapProperty pTgtoolsBaiduMapProperty) {
        tgtoolsBaiduMapProperty = pTgtoolsBaiduMapProperty;
    }

    public void start() {
        threadSize = tgtoolsBaiduMapProperty.getThread();
        threadPool = new ThreadPool(threadSize);
        downloader = new TileDownloader(tgtoolsBaiduMapProperty);

        try {
            addQueue();

            while (!threadPool.isShutdown() && !tileQueue.isEmpty()) {
                threadPool.execute(() -> {
                    Tile tile = tileQueue.poll();
                    if (tileQueue.size() % 1000 == 0) {
                        logger.info("下载队列 size：{}", tileQueue.size());
                    }
                    if (tile != null) {
                        downloader.download(tile, 0);
                    } else {
                        logger.info("本节点任务下载完成");
                    }
                });
            }
            threadPool.shutdown();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    private void addQueue() throws InterruptedException {
        String model = tgtoolsBaiduMapProperty.getScale();// ConfigUtil.getInstance().getString("download.scale");
        if (model.equals("world")) {
            new Thread(() -> {
                addWorldTileQueue();
            }).start();
        } else if (model.equals("china")) {
            new Thread(() -> {
                addChinaTileQueue();
            }).start();
        } else {
            logger.error("download.scale 配置错误");
        }
        Thread.sleep(3000);
    }

    private void addWorldTileQueue() {
        String levelConfig = tgtoolsBaiduMapProperty.getLevel();
        List<String> levelList = null;
        if (levelConfig.indexOf(",") >= 0) {
            levelList = parseLevelByComma(levelConfig);
        } else if (levelConfig.indexOf("-") >= 0) {
            levelList = parseLevelByMinus(levelConfig);
        }

        if (null == levelList || levelList.size() < 1) {
            return;
        }

        try {
            double r = 20037508.34278924;
            for (String level : levelList) {
                int z = Integer.parseInt(level);
                double px = Math.pow(2, (18 - z));
                double mx = r / (px * 256);
                int n = new BigDecimal(Math.ceil(mx)).intValue();
                int m = 0 - n;

                for (long x = m; x < n; x++) {
                    while (tileQueue.size() > maxQueueSize) {
                        Thread.sleep(3000);
                    }

                    for (long y = m; y < n; y++) {
                        tileQueue.add(new Tile(z, (int) x, (int) y));
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected List<String> parseLevelByComma(String pLevel) {
        if (StringUtil.isEmpty(pLevel)) {
            return new ArrayList<>();
        }

        return Arrays.asList(pLevel.split(","));
    }

    protected List<String> parseLevelByMinus(String pLevel) {
        ArrayList<String> vResult = new ArrayList<>();
        if (StringUtil.isEmpty(pLevel)) {
            return vResult;
        }

        String[] vLevels = pLevel.split("-");
        if (vLevels.length != 2) {
            return vResult;
        }
        Integer vStart = Integer.valueOf(vLevels[0]);
        Integer vEnd = Integer.valueOf(vLevels[1]);

        for (; vStart <= vEnd; vStart++) {
            vResult.add(String.valueOf(vStart));
        }
        return vResult;
    }

    private List<Position> getDownloadPosition() {
        if (StringUtil.isNotEmpty(tgtoolsBaiduMapProperty.getRegion())) {
            String regionConfig = tgtoolsBaiduMapProperty.getRegion();
            List<String> regionList = Arrays.asList(regionConfig.split(","));
            return TileUtil.getPositionList(regionList);
        } else if (null != tgtoolsBaiduMapProperty.getDownloadPosition() && tgtoolsBaiduMapProperty.getDownloadPosition().size() > 0) {
            return tgtoolsBaiduMapProperty.getDownloadPosition();
        }

        return new ArrayList<>();
    }

    private void addChinaTileQueue() {
        try {
            String levelConfig = tgtoolsBaiduMapProperty.getLevel();
            List<String> levelList = null;
            if (levelConfig.indexOf(",") >= 0) {
                levelList = parseLevelByComma(levelConfig);
            } else if (levelConfig.indexOf("-") >= 0) {
                levelList = parseLevelByMinus(levelConfig);
            }

            if (null == levelList || levelList.size() < 1) {
                return;
            }

            List<Position> positionList = getDownloadPosition();

            for (String level : levelList) {
                int z = Integer.parseInt(level);
                for (Position p : positionList) {
                    int sx = TileUtil.getTileNum(p.getSwlng(), z);
                    int sy = TileUtil.getTileNum(p.getSwlat(), z);
                    int ex = TileUtil.getTileNum(p.getNelng(), z);
                    int ey = TileUtil.getTileNum(p.getNelat(), z);

                    for (int x = sx; x < ex; x++) {
                        while (tileQueue.size() > maxQueueSize) {
                            Thread.sleep(3000);
                        }
                        for (int y = sy; y < ey; y++) {
                            tileQueue.add(new Tile(z, x, y));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("tileQueue size:" + tileQueue.size());
    }
}
