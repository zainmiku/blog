package com.fanhoufang.blog;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器
 *
 * @author xiongxiaoyang
 * @date 2022/5/11
 */
public class Generator {

    private static final String USERNAME = System.getenv().get("USER");

    /**
     * 项目信息
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String JAVA_PATH = "/src/main/java";
    private static final String RESOURCE_PATH = "/src/main/resources";
    private static final String BASE_PACKAGE = "com.fanhoufang";
    private static final String MODULE_NAME = "blog";

    /**
     * 数据库信息
     */
    private static final String DATABASE_IP = "127.0.0.1";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE_NAME = "blog";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    public static void main(String[] args) {

        // 传入需要生成的表名，多个用英文逗号分隔，所有用 all 表示
        genCode("m_user,m_blog");

    }

    /**
     * 代码生成
     */
    private static void genCode(String tables) {

        // 全局配置
        FastAutoGenerator.create(String.format(
                "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai",
                DATABASE_IP, DATABASE_PORT, DATABASE_NAME), DATABASE_USERNAME, DATABASE_PASSWORD)
                .globalConfig(builder -> {
                    builder.author(USERNAME) // 设置作者
                            .disableOpenDir() // 执行完毕不打开文件夹
                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy-MM-dd hh:mm:ss") // 注释日期
                            .outputDir(PROJECT_PATH + JAVA_PATH); // 指定输出目录
                })
                // 包配置
                .packageConfig(builder -> builder.parent(BASE_PACKAGE) // 设置父包名
                .moduleName(MODULE_NAME)// 设置模块包名
                        .entity("dao.entity")
                        .service("service") // Service 包名
                        .serviceImpl("serviceImpl") // ***ServiceImpl 包名
                        .mapper("mapper") // Mapper 包名
                        .xml("mapper") // Mapper XML 包名
                        .controller("controller") // Controller 包名
                        .other("utils") // 自定义文件包名
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                PROJECT_PATH + RESOURCE_PATH + "/mapper")))
                // 模版配置
                .templateConfig(builder -> builder
                // .disable(TemplateType.SERVICE)
                        // .disable(TemplateType.SERVICEIMPL)
                        //  .disable(TemplateType.CONTROLLER)
                         .build()
                        )
                // 策略配置
                .strategyConfig(builder -> builder.addInclude(getTables(tables)) // 设置需要生成的表名
                        .addTablePrefix("m_") // 设置过滤表前缀
                        // Entity 策略配置
                        .entityBuilder()
                        .naming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略：下划线转驼峰命
                        .columnNaming(NamingStrategy.underline_to_camel) // 数据库表字段映射到实体的命名策略：下划线转驼峰命
                        // Mapper 策略配置
                        .mapperBuilder()
                        // Service 策略配置
                        .serviceBuilder()
                        .formatServiceFileName("%sService") // 格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                        .formatServiceImplFileName("%sServiceImpl") // 格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
                        // Controller 策略配置
                        .controllerBuilder()
                        

                )
                .execute();

    }

    /**
     * 处理 all 和多表情况
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
