package com.fanhoufang.blog.utils;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author fan
 * @Date 2022/9/23 21:01
 */
public class EntityNameConvert implements INameConvert {

    private final StrategyConfig strategyConfig;

    private Map<String, String> tableNameAliasMap;

    public EntityNameConvert(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;

    }

    public void setTableNameAliasMap(Map<String, String> tableNameAliasMap){
        this.tableNameAliasMap = tableNameAliasMap;
    }


    @Override
    public  String entityNameConvert(TableInfo tableInfo) {

        String tableName = tableInfo.getName();
        if (tableNameAliasMap!=null){
            String newName = tableNameAliasMap.get(tableName);
            if (newName!=null){
                tableName = newName;
            }
        }


        return NamingStrategy.capitalFirst(processName(tableName, strategyConfig.entity().getNaming(), strategyConfig.getTablePrefix(), strategyConfig.getTableSuffix()));
    }

    @Override
    public String propertyNameConvert(TableField field) {
        return processName(field.getName(), strategyConfig.entity().getNaming(), strategyConfig.getFieldPrefix(), strategyConfig.getFieldSuffix());
    }

    private String processName(String name, NamingStrategy strategy, Set<String> prefix, Set<String> suffix) {
        String propertyName = name;
        // 删除前缀
        if (prefix.size() > 0) {
            propertyName = NamingStrategy.removePrefix(propertyName, prefix);
        }
        // 删除后缀
        if (suffix.size() > 0) {
            propertyName = NamingStrategy.removeSuffix(propertyName, suffix);
        }
        if (StringUtils.isBlank(propertyName)) {
            throw new RuntimeException(String.format("%s 的名称转换结果为空，请检查是否配置问题", name));
        }
        // 下划线转驼峰
        if (NamingStrategy.underline_to_camel.equals(strategy)) {
            return NamingStrategy.underlineToCamel(propertyName);
        }
        return propertyName;
    }
}
