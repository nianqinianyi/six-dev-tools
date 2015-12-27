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

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 六楼的雨/loki
 * @since 1.0.0-SNAPSHOT
 */
public class BeanGen {

    private static String url = "jdbc:mysql://localhost:3306/blog?useUnicode=true&amp;characterEncoding=UTF-8";
    private static String driver ="com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "root";
    private static String[] tables = null;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        tables = new String[]{"six_prop"};

        try {
            for (String tableName : tables) {
                new Thread(() -> {
                    try {
                        Class.forName(driver);
                        Connection con = DriverManager.getConnection(url, username, password);
                        List<TableColumn> columns = getColumnType(con, null, tableName);
                        genBeanFile(columns, tableName);
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

    private static void genBeanFile(List<TableColumn> columns, String tableName) {
        StringBuffer javaFile = new StringBuffer();
        String className = StrUtil.getCamel(tableName, false);

        appendTitle(javaFile, packageName);

        javaFile.append("@Table(\"" + tableName + "\")\n");
        javaFile.append("public class " + className + " {\n\n");

        for (TableColumn tableColumn : columnType) {
            String name = tableColumn.getColumnName();
            String type = tableColumn.getColumnType();

            if ("id".equals(name)) {
                javaFile.append("    @Id\n");
            } else {
                javaFile.append("    @Column(\"" + name + "\")\n");
            }

            switch (type) {
                case "INTEGER":
                case "INT":
                    javaFile.append("    private int " + StrUtil.getCamel(name) + ";\n");
                    break;
                case "REAL":
                    javaFile.append("    private boolean " + StrUtil.getCamel(name) + ";\n");
                    break;
                case "DATETIME":
                case "DATE":
                    javaFile.append("    private Date " + StrUtil.getCamel(name) + ";\n");
                    break;
                case "CHAR":
                case "VARCHAR":
                case "TEXT":
                case "BLOB":
                    javaFile.append("    private String " + StrUtil.getCamel(name) + ";\n");
                    break;
            }
        }

        for (TableColumn tableColumn : columnType) {
            String name = tableColumn.getColumnName();
            String type = tableColumn.getColumnType();
            switch (type) {
                case "INTEGER":
                case "INT":
                    appendGetterSetter(javaFile, "int", name);
                    break;
                case "REAL":
                    appendGetterSetter(javaFile, "boolean", name);
                    break;
                case "DATETIME":
                case "DATE":
                    appendGetterSetter(javaFile, "Date", name);
                    break;
                case "CHAR":
                case "VARCHAR":
                case "TEXT":
                case "BLOB":
                    appendGetterSetter(javaFile, "String", name);
                    break;
            }
        }

        javaFile.append("\n}");
        String parentFilePath = new File(clz.getResource("/").getPath()).getParentFile().getParent() + File.separator + "temp" + File.separator;
        File file = new File(parentFilePath);
        if (!file.exists()) {
            file.mkdir();
        }
        String filePath = parentFilePath + className + ".java";
        FileWriter writer = new FileWriter(filePath);
        writer.write(javaFile.toString());
        writer.flush();
        writer.close();
    }

    private static void appendGetterSetter(StringBuffer javaFile, String type, String name) {
        //String lowerName = StrUtil.getCamel(name);
        //String upperName = StrUtil.getCamel(name, false);
        //javaFile.append("\n");
        //javaFile.append("    public " + type + " get" + upperName + " () {\n");
        //javaFile.append("        return " + lowerName + ";\n    }\n");
        //
        //javaFile.append("\n");
        //javaFile.append("    public void set" + upperName + "(" + type + " " + lowerName + ") {\n");
        //javaFile.append("        this." + lowerName + " = " + lowerName + ";\n    }\n");
    }

    private static List<TableColumn> getColumnType(Connection conn, String schema, String tableName)
            throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getColumns(conn.getCatalog(), schema, tableName, null);
        List<TableColumn> columnList = new ArrayList<>();
        while (rs.next()) {
            TableColumn column = new TableColumn();
            column.setColumnName(rs.getString("COLUMN_NAME"));
            column.setColumnType(rs.getString("TYPE_NAME"));
            columnList.add(column);
        }
        conn.close();
        return columnList;
    }

    private static void test(Connection conn) throws SQLException {
        DatabaseMetaData dbMetData = conn.getMetaData();
        ResultSet rs = dbMetData.getTables(null,
                convertDatabaseCharsetType("root", "mysql"), null,
                new String[]{"TABLE", "VIEW"});

        while (rs.next()) {
            if (rs.getString(4) != null
                    && (rs.getString(4).equalsIgnoreCase("TABLE") || rs
                    .getString(4).equalsIgnoreCase("VIEW"))) {
                String tableName = rs.getString(3).toLowerCase();
                System.out.print(tableName + "\t");
                // 根据表名提前表里面信息：
                ResultSet colRet = dbMetData.getColumns(null, "%", tableName,
                        "%");
                while (colRet.next()) {
                    String columnName = colRet.getString("COLUMN_NAME");
                    String columnType = colRet.getString("TYPE_NAME");
                    int datasize = colRet.getInt("COLUMN_SIZE");
                    int digits = colRet.getInt("DECIMAL_DIGITS");
                    int nullable = colRet.getInt("NULLABLE");
                    // System.out.println(columnName + " " + columnType + " "+
                    // datasize + " " + digits + " " + nullable);
                }

            }
        }
    }

    public static String convertDatabaseCharsetType(String in, String type) {
        String dbUser;
        if (in != null) {
            if (type.equals("oracle")) {
                dbUser = in.toUpperCase();
            } else if (type.equals("postgresql")) {
                dbUser = "public";
            } else if (type.equals("mysql")) {
                dbUser = null;
            } else if (type.equals("mssqlserver")) {
                dbUser = null;
            } else if (type.equals("db2")) {
                dbUser = in.toUpperCase();
            } else {
                dbUser = in;
            }
        } else {
            dbUser = "public";
        }
        return dbUser;
    }

    public static void gen(Statement statement){

    }

}

class TableColumn {
    private String columnName;
    private String columnType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}