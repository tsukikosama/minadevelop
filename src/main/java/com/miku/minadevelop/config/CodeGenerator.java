package com.miku.minadevelop.config;



import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.sql.Types;
import java.util.Collections;




public class CodeGenerator {
    /**
     * 数据源配置
     */
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/minadevelop?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateT" +
                "imeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8" ;

        // 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create(url, "root", "2270398619")
                .globalConfig(builder -> {
                    builder.author("miku") // 设置作者
                            .outputDir("src/main/java")// 输出目录
                            .enableSwagger();
                    ;

                })
                .packageConfig(builder -> {
                    builder.parent("com.miku.minadevelop") // 设置父包名
                            .entity("entity") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .xml("mappers"); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.likeTable(new LikeTable("cc_")).addTablePrefix("cc_") // 设置需要生成的表名
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle(); // 启用 REST 风格

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}
