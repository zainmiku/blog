package com.fanhoufang.blog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.fanhoufang.blog.entity.DbProperties;
import com.fanhoufang.blog.entity.ModuleInfo;
import com.fanhoufang.blog.entity.TableInfo;
import com.fanhoufang.blog.utils.EntityNameConvert;
import com.fanhoufang.blog.utils.GeneratorUtils;

/**
 * @author fan
 * @Date 2022/7/14 14:25
 */
public class CodeGenerator {

    /**
     * 数据库信息
     */
    private DbProperties dbProperties;
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String RESOURCE_PATH = "/src/main/resources";

    /**
     * 当前模块信息 ， 包括模块路径，父包的路径
     */
    private ModuleInfo currentModule ;

    private boolean isGenerateController = true;
    private boolean isGenerateService = true;


    /**
     * 保存数据库及表关系
     */
    private Map<String, List<TableInfo>> schemaTableMap;

    private CodeGenerator(){
        dbProperties = GeneratorUtils.getDbProperties();
        currentModule = GeneratorUtils.getModuleInfo();
    }

    public static CodeGenerator build(String... tableNames){
        CodeGenerator instance = new CodeGenerator();

        // 解析成 TableInfo
        List<TableInfo> list = new ArrayList<>(tableNames.length);
        for (String tableName : tableNames) {
            list.add(GeneratorUtils.parseTableName(tableName));
        }

        // 默认为当前模块配置的数据库
        final String defaultSchema = instance.dbProperties.getDefaultSchema();

        instance.schemaTableMap = list.stream().collect(Collectors.groupingBy(v -> {
            String schema = v.getSchema();
            if (StringUtils.hasText(schema)) {
                return schema;
            }else {
                return defaultSchema;
            }
        }));


        return instance;
    }

    /**
     * 关闭生成Controller文件
     * @return
     */
    public CodeGenerator disableController(){
        this.isGenerateController = false;
        return this;
    }

    /**
     * 关闭生成Service文件
     * @return
     */
    public CodeGenerator disableService(){
        this.isGenerateService = false;
        return this;
    }


    public void generate() {
        schemaTableMap.forEach((k , v)->{
            generateBySchema(dbProperties, currentModule, k, v);
        });
    }


    private void generateBySchema(DbProperties dbProperties, ModuleInfo moduleInfo, String schema, List<TableInfo> tableInfoList) {

        Map<String, String> tableNameMap = tableInfoList.stream().collect(Collectors.toMap(v -> v.getTableName(), v -> {
            String alias = v.getAlias();
            if (StringUtils.hasText(alias)){
                return alias;
            }
            return v.getTableName();
        }));

        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder("jdbc:mysql://" + dbProperties.getHost() + ":" + dbProperties.getPort() + "/" + schema + "?useUnicode=true&useSSL=false&characterEncoding=utf8", dbProperties.getUsername(), dbProperties.getPassword());
        dataSourceConfigBuilder.schema(schema);

        FastAutoGenerator.create(dataSourceConfigBuilder)
                .globalConfig(builder -> builder.author("fan")
                        //使用Swagger注解
                        // .enableSwagger()
                        // 设置输出文件夹
                        .outputDir(moduleInfo.getModulePath())
                        // 不打开输出文件夹
                        .disableOpenDir()
                ).packageConfig(builder ->
                        // 设置父包名
                        builder.parent(moduleInfo.getParentPackage())
                                // 设置 实体类存放的位置
                                .entity("entity.po")
                                .service("service") // Service 包名
                                .serviceImpl("serviceImpl") // ***ServiceImpl 包名
                                .mapper("mapper") // Mapper 包名
                                .xml("mapper") // Mapper XML 包名
                                .controller("controller") // Controller 包名
                                .other("utils") // 自定义文件包名
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                PROJECT_PATH + RESOURCE_PATH + "/mapper"))
                ).strategyConfig(builder -> {
                    StrategyConfig strategyConfig = builder.build();

                    EntityNameConvert entityNameConvert = new EntityNameConvert(strategyConfig);
                    entityNameConvert.setTableNameAliasMap(tableNameMap);

                    builder.addInclude(tableInfoList.stream().map(TableInfo::getTableName).collect(Collectors.toList())) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_", "v_","m_")
                            .enableSchema()
                            .entityBuilder()
                                // 设置自定义实体类名转换器
                                .nameConvert(entityNameConvert)
                                // 使用lombok注解
                                .enableLombok()
                                // 下划线转驼峰
                                .columnNaming(NamingStrategy.underline_to_camel)
                                .logicDeleteColumnName("is_deleted")
                            .serviceBuilder()
                                .formatServiceFileName("%sService")
                            .controllerBuilder()
                                .enableRestStyle();
                })
                .templateConfig(builder -> {
                    if (!isGenerateController){
                        builder.disable(TemplateType.CONTROLLER);
                    }
                    if (!isGenerateService){
                        builder.disable(TemplateType.SERVICE, TemplateType.SERVICEIMPL);
                    }
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                // .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }


}
