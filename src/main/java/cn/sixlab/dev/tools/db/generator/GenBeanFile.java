/*
 * Copyright (c) 1995 Sixlab. All rights reserved.
 *
 * Under the GPLv3(AKA GNU GENERAL PUBLIC LICENSE Version 3).
 * see http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * For more information, please see
 * http://sixlab.cn/
 */
package cn.sixlab.dev.tools.db.generator;

import cn.sixlab.dev.tools.db.FieldConstants;
import cn.sixlab.dev.tools.db.BeanGen;
import cn.sixlab.dev.tools.db.bean.TableColumn;
import cn.sixlab.sixutil.StrUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author 六楼的雨/loki
 * @since 1.0.0
 */
public abstract class GenBeanFile {


    public void genBeanFile(List<TableColumn> columnList, String tableName, String packageName)
            throws IOException {
        StringBuffer fileContent = new StringBuffer();
        String className = StrUtil.getCamel(tableName, false);
        fileContent.append("package " + packageName + ";\n\n");

        fileContent.append("import java.util.Date;\n");
        fileContent.append("import java.math.BigInteger;\n");
        fileContent.append("import java.math.BigDecimal;\n");
        fileContent.append("import java.util.Date;\n");
        fileContent.append("import java.util.Date;\n");

        addFileHeader(fileContent, tableName);
        fileContent.append("public class " + className + " {\n\n");

        genFields(columnList, fileContent);

        genTer(columnList, fileContent);

        fileContent.append("\n}");
        String parentFilePath = new File(BeanGen.class.getResource("/").getPath()).getParentFile().getParent() + File.separator + "temp" + File.separator;
        File file = new File(parentFilePath);
        if (!file.exists()) {
            file.mkdir();
        }
        String filePath = parentFilePath + className + ".java";
        FileWriter writer = new FileWriter(filePath);
        writer.write(fileContent.toString());
        writer.flush();
        writer.close();
    }

    private void genTer(List<TableColumn> columnList, StringBuffer fileContent) {
        for (TableColumn tableColumn : columnList) {
            String name = tableColumn.getColumnName();
            String type = tableColumn.getColumnTypeName();
            switch (type) {
                case FieldConstants.INT:
                case FieldConstants.INTEGER:
                case FieldConstants.TINYINT:
                case FieldConstants.SMALLINT:
                case FieldConstants.MEDIUMINT:
                    appendGetterSetter(fileContent, "int", name);
                    break;
                case FieldConstants.BIGINT:
                case FieldConstants.BIGINT_UNSIGNED:
                    appendGetterSetter(fileContent, "BigInteger", name);
                    break;
                case FieldConstants.BIT:
                case FieldConstants.REAL:
                    appendGetterSetter(fileContent, "boolean", name);
                    break;
                case FieldConstants.DOUBLE:
                    appendGetterSetter(fileContent, "double", name);
                    break;
                case FieldConstants.FLOAT:
                    appendGetterSetter(fileContent, "float", name);
                    break;
                case FieldConstants.DECIMAL:
                    appendGetterSetter(fileContent, "BigDecimal", name);
                    break;
                case FieldConstants.DATE:
                case FieldConstants.YEAR:
                case FieldConstants.TIME:
                case FieldConstants.DATETIME:
                case FieldConstants.TIMESTAMP:
                    appendGetterSetter(fileContent, "Date", name);
                    break;
                case FieldConstants.BLOB:
                case FieldConstants.TINYBLOB:
                case FieldConstants.LONGBLOB:
                case FieldConstants.MEDIUMBLOB:
                case FieldConstants.BINARY:
                case FieldConstants.VARBINARY:
                    appendGetterSetter(fileContent, "byte[]", name);
                    break;
                case FieldConstants.CHAR:
                case FieldConstants.VARCHAR:
                case FieldConstants.TEXT:
                case FieldConstants.TINYTEXT:
                case FieldConstants.MEDIUMTEXT:
                case FieldConstants.LONGTEXT:
                case FieldConstants.ENUM:
                case FieldConstants.SET:
                    appendGetterSetter(fileContent, "String", name);
                    break;
            }
        }
    }

    private void genFields(List<TableColumn> columnList, StringBuffer fileContent) {
        for (TableColumn column : columnList) {
            String name = column.getColumnName();
            String type = column.getColumnTypeName();

            addFieldAnnotation(fileContent, name, column.isKey());

            switch (type) {
                case FieldConstants.INT:
                case FieldConstants.INTEGER:
                case FieldConstants.TINYINT:
                case FieldConstants.SMALLINT:
                case FieldConstants.MEDIUMINT:
                    fileContent.append("    private int " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.BIGINT:
                case FieldConstants.BIGINT_UNSIGNED:
                    fileContent.append("    private BigInteger " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.BIT:
                case FieldConstants.REAL:
                    fileContent.append("    private boolean " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.DOUBLE:
                    fileContent.append("    private double " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.FLOAT:
                    fileContent.append("    private float " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.DECIMAL:
                    fileContent.append("    private BigDecimal " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.DATE:
                case FieldConstants.YEAR:
                case FieldConstants.TIME:
                case FieldConstants.DATETIME:
                case FieldConstants.TIMESTAMP:
                    fileContent.append("    private Date " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.BLOB:
                case FieldConstants.TINYBLOB:
                case FieldConstants.LONGBLOB:
                case FieldConstants.MEDIUMBLOB:
                case FieldConstants.BINARY:
                case FieldConstants.VARBINARY:
                    fileContent.append("    private byte[] " + StrUtil.getCamel(name) + ";\n");
                    break;
                case FieldConstants.CHAR:
                case FieldConstants.VARCHAR:
                case FieldConstants.TEXT:
                case FieldConstants.TINYTEXT:
                case FieldConstants.MEDIUMTEXT:
                case FieldConstants.LONGTEXT:
                case FieldConstants.ENUM:
                case FieldConstants.SET:
                    fileContent.append("    private String " + StrUtil.getCamel(name) + ";\n");
                    break;
                default:
                    System.out.println(type);
            }
        }
    }

    protected abstract void addFieldAnnotation(StringBuffer fileContent, String name,
            boolean isKey);

    protected abstract void addFileHeader(StringBuffer fileContent, String tableName);

    private void appendGetterSetter(StringBuffer javaFile, String type, String name) {
        String upperName = StrUtil.getCamel(name, false);
        String lowerName = StrUtil.getCamel(name);

        javaFile.append("\n");
        javaFile.append("    public " + type + " get" + upperName + " () {\n");
        javaFile.append("        return " + lowerName + ";\n    }\n");

        javaFile.append("\n");
        javaFile.append("    public void set" + upperName + "(" + type + " " + lowerName + ") {\n");
        javaFile.append("        this." + lowerName + " = " + lowerName + ";\n    }\n");
    }
}
