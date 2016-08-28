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

/**
 * @author 六楼的雨/loki
 * @since 1.0.0
 */
public class OrmLiteGenBeanFile extends GenBeanFile {

    @Override
    protected void addFileHeader(StringBuffer fileContent, String tableName) {

        fileContent.append("import com.j256.ormlite.field.DatabaseField;\n");
        fileContent.append("import com.j256.ormlite.table.DatabaseTable;\n");

        fileContent.append("\n");
        fileContent.append("@DatabaseTable(tableName=\"" + tableName + "\")\n");
    }

    @Override
    protected void addFieldAnnotation(StringBuffer fileContent, String name, boolean isKey) {
        if (isKey) {
            fileContent.append("    @DatabaseField(generatedId = true)\n");
        } else {
            fileContent.append("    @DatabaseField(columnName = \"" + name + "\")\n");
        }
    }

}
