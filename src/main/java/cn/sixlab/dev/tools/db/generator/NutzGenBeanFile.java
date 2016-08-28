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
public class NutzGenBeanFile extends GenBeanFile {

    @Override
    protected void addFileHeader(StringBuffer fileContent, String tableName) {

        fileContent.append("import org.nutz.dao.entity.annotation.Table;\n");
        fileContent.append("import org.nutz.dao.entity.annotation.Id;\n");
        fileContent.append("import org.nutz.dao.entity.annotation.Column;\n");

        fileContent.append("\n");
        fileContent.append("@Table(\"" + tableName + "\")\n");
    }

    @Override
    protected void addFieldAnnotation(StringBuffer fileContent, String name, boolean isKey) {
        if (isKey) {
            fileContent.append("    @Id\n");
        } else {
            fileContent.append("    @Column(\"" + name + "\")\n");
        }
    }

}
