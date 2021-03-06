package com.github.tianjing.baidu.map.common.util;

import com.zaxxer.hikari.HikariDataSource;
import tgtools.db.DataSourceDataAccess;
import tgtools.exceptions.APPErrorException;

/**
 * @author 田径
 * @date 2021-04-07 11:07
 * @desc
 **/
public class H2Util {

    public static void initDB(String pFilePath) throws APPErrorException {
        createDB(pFilePath);
        createCustomTable();
    }

    protected static void createDB(String pFilePath) throws APPErrorException {
        FolderUtil.createFolder(pFilePath);

        HikariDataSource vHikariDataSource = new HikariDataSource();
        vHikariDataSource.setPoolName("SQLiteConnectionPool");
        vHikariDataSource.setDriverClassName("org.h2.Driver");
        vHikariDataSource.setJdbcUrl("jdbc:h2:" + pFilePath + "/map.db");
        vHikariDataSource.setAutoCommit(true);
        vHikariDataSource.setMinimumIdle(8);
        vHikariDataSource.setMaximumPoolSize(32);


        DataSourceDataAccess vDataSourceDataAccess = new DataSourceDataAccess();
        vDataSourceDataAccess.init(vHikariDataSource);
        //创建和测试数据库是否存在
        vDataSourceDataAccess.executeQuery("select 1");
        tgtools.db.DataBaseFactory.add("H2DATAACCESS", vDataSourceDataAccess);
        createCustomTable();
    }

    protected static void createCustomTable() throws APPErrorException {
        String vTable = "CREATE TABLE CUSTOM(  \n" +
                "   X INTEGER NOT NULL, \n" +
                "   Y INTEGER NOT NULL, \n" +
                "   ZOOM INTEGER NOT NULL, \n" +
                "   THEME VARCHAR(256) NOT NULL, \n" +
                "   FILE        BLOB, \n" +
                "primary key (X, Y, ZOOM,THEME));";
        try {
            tgtools.db.DataBaseFactory.get("H2DATAACCESS").executeUpdate(vTable);
        } catch (Exception e) {
            if (e.toString().indexOf("Table \"CUSTOM\" already exists") < 1) {
                throw e;
            } else {
                System.out.println("表已存在");
            }
        }
    }

}
