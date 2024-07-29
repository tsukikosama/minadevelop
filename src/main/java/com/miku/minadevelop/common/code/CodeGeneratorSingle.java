package com.miku.minadevelop.common.code;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodeGeneratorSingle {
    public static void main(String[] args) {
        String outputDir = Paths.get(System.getProperty("user.dir")) + "/src/main/java";
        String xmlDir = Paths.get(System.getProperty("user.dir")) + "/src/main/resources";
        List<String> tables = new ArrayList<>();
        tables.add("tb_filter_order");
        String url = "jdbc:mysql://127.0.0.1:3306/minadevelop2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowMultiQueries=true&rewriteBatchedStatements=true" ;
        // 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create(url, "root", "2270398619")
                .globalConfig(builder -> {
                    builder.author("miku") // 设置作者
                            .outputDir(outputDir)// 输出目录
                            .enableSwagger();
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT || typeCode == Types.TINYINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.miku.minadevelop") // 设置父包名
                            .moduleName("modules")
                            .entity("entity") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .xml(xmlDir) // 设置 Mapper XML 文件包名.
                            .pathInfo(Collections.singletonMap(OutputFile.xml,xmlDir+"/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("cc_").entityBuilder().formatFileName("%sEntity").enableLombok().idType(IdType.AUTO); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }

}
