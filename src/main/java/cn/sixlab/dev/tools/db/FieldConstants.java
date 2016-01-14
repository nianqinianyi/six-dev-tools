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

/**
 * @author 六楼的雨/loki
 * @since 1.0.0
 */
public class FieldConstants {
    //int
    public static final String TINYINT = "TINYINT";
    public static final String SMALLINT = "SMALLINT";
    public static final String MEDIUMINT = "MEDIUMINT";
    public static final String INT = "INT";
    public static final String INTEGER = "INTEGER";

    //BigInteger
    public static final String BIGINT = "BIGINT";
    public static final String BIGINT_UNSIGNED = "BIGINT UNSIGNED";

    //boolean
    public static final String BIT = "BIT";
    public static final String REAL = "REAL";

    // double float
    public static final String DOUBLE = "DOUBLE";
    public static final String FLOAT = "FLOAT";

    //BigDecimal
    public static final String DECIMAL = "DECIMAL";

    //string
    public static final String CHAR = "CHAR";
    public static final String VARCHAR = "VARCHAR";
    public static final String TEXT = "TEXT";
    public static final String TINYTEXT = "TINYTEXT";
    public static final String MEDIUMTEXT = "MEDIUMTEXT";
    public static final String LONGTEXT = "LONGTEXT";
    public static final String ENUM = "ENUM";
    public static final String SET = "SET";

    //Date
    public static final String DATE = "DATE";
    public static final String YEAR = "YEAR";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String DATETIME = "DATETIME";
    public static final String TIME = "TIME";

    //byte[]
    public static final String TINYBLOB = "TINYBLOB";
    public static final String BLOB = "BLOB";
    public static final String MEDIUMBLOB = "MEDIUMBLOB";
    public static final String LONGBLOB = "LONGBLOB";
    public static final String BINARY = "BINARY";
    public static final String VARBINARY = "VARBINARY";

    //未知
    public static final String POINT = "POINT";
    public static final String LINESTRING = "LINESTRING";
    public static final String POLYGON = "POLYGON";
    public static final String GEOMETRY = "GEOMETRY";
}