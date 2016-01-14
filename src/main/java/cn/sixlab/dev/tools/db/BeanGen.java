/*
 * Copyright (c) 1995 Sixlab. All rights reserved.
 *
 * Under the GPLv3(AKA GNU GENERAL PUBLIC LICENSE Version 3).
 * see http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * For more information, please see
 * http://sixlab.cn/
 */
package cn.sixlab.dev.tools.db;

import cn.sixlab.dev.tools.db.bean.TableColumn;
import cn.sixlab.dev.tools.db.generator.GenBeanFile;
import cn.sixlab.dev.tools.db.generator.NutzGenBeanFile;
import cn.sixlab.dev.tools.db.generator.OrmLiteGenBeanFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 六楼的雨/loki
 * @since 1.0.0
 */
public class BeanGen {

    private static String url = "jdbc:mysql://localhost:3306/blog?useUnicode=true&amp;characterEncoding=UTF-8";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "root";
    private static String[] tables = null;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        tables = new String[]{"sixlab_time_line"};
        String type = "ormlite";
        String packageName = "cn.sixlab.web.common.ormlite.bean";

        try {
            for (String tableName : tables) {
                new Thread(() -> {
                    try {
                        Class.forName(driver);
                        Connection con = DriverManager.getConnection(url, username, password);
                        List<TableColumn> columnList = getColumnType(con, null, tableName);
                        GenBeanFile genBeanFile = null;
                        if ("nutz".equals(type)) {
                            genBeanFile = new NutzGenBeanFile();
                        } else if ("ormlite".equals(type)) {
                            genBeanFile = new OrmLiteGenBeanFile();
                        }
                        genBeanFile.genBeanFile(columnList, tableName, packageName);
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<TableColumn> getColumnType(Connection conn, String schema, String tableName)
            throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(" SELECT * FROM " + tableName + " limit 0,1");
        ResultSetMetaData columnMeta = rs.getMetaData();

        List<TableColumn> columnList = new ArrayList<>();
        for (int i = 1; i <= columnMeta.getColumnCount(); i++) {
            TableColumn column = new TableColumn();
            column.setColumnTypeName(columnMeta.getColumnTypeName(i));
            column.setColumnType(columnMeta.getColumnType(i));
            column.setColumnName(columnMeta.getColumnName(i));
            if(i==1){
                column.setKey(true);
            }
            columnList.add(column);
        }
        conn.close();
        return columnList;
    }

}