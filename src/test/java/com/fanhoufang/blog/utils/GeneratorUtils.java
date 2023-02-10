package com.fanhoufang.blog.utils;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import com.fanhoufang.blog.entity.DbProperties;
import com.fanhoufang.blog.entity.ModuleInfo;
import com.fanhoufang.blog.entity.TableInfo;

/**
 * @author fan
 * @Date 2022/7/14 19:22
 */
public class GeneratorUtils {

    /**
     * 读取数据库配置
     *
     * @return
     */
    public static DbProperties getDbProperties() {

        String regex = "\\$\\{\\s*[^:]*\\s*:\\s*([^}\\s]*)\\s*}";

        DbProperties dbProperties = new DbProperties();

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application-dev.yml"));
        Properties properties = yaml.getObject();
        if (properties==null) {
            throw new RuntimeException("未找到application-dev.yml");
        }
        String url = properties.getProperty("spring.datasource.url");
        if (!StringUtils.hasText(url)) {
            throw new RuntimeException("未配置spring.datasource.url");
        }
        // 去除 ${xxx:yyy} 格式的内容
        url = url.replaceAll(regex, "$1");

        Pattern pattern = Pattern.compile("jdbc:mysql://(.+):(\\d+)/(\\w+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String host = matcher.group(1);
            if (host.startsWith("$")) {
                host = host.substring(host.indexOf(":") + 1, host.length() - 1);
            }
            dbProperties.setHost(host);
            dbProperties.setPort(matcher.group(2));
            dbProperties.setDefaultSchema(matcher.group(3));
        } else {
            throw new RuntimeException("url格式不正确");
        }
        String username = properties.getProperty("spring.datasource.username");
        if (!StringUtils.hasText(username)) {
            throw new RuntimeException("未配置spring.datasource.username");
        }
        // 去除 ${xxx:yyy} 格式的内容
        username = username.replaceAll(regex, "$1");
        String password = properties.getProperty("spring.datasource.password");
        if (!StringUtils.hasText(password)) {
            throw new RuntimeException("未配置spring.datasource.password");
        }
        // 去除 ${xxx:yyy} 格式的内容
        password = password.replaceAll(regex, "$1");
        dbProperties.setPassword(password);
        dbProperties.setUsername(username);

        return dbProperties;
    }

    private static Pattern pattern = Pattern.compile("((\\w+)\\.)?(\\w+)((\\s+(AS|As|as|aS)\\s+(\\w+))|(\\s+(\\w+)))?");

    /**
     * 解析文本为 TableEntity对象
     *
     * @return
     */
    public static TableInfo parseTableName(String str) {
        if (!StringUtils.hasText(str)) {
            throw new RuntimeException("表名称不能为null");
        }
        str = str.trim();

        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            throw new RuntimeException("表名称格式不正确");
        }
        TableInfo entity = new TableInfo();
        entity.setSchema(matcher.group(2));
        entity.setTableName(matcher.group(3));
        String alias = matcher.group(7);
        entity.setAlias(alias != null ? alias : matcher.group(9));

        return entity;
    }


    /**
     * 获取当前模块的信息
     *
     * @return
     */
    public static ModuleInfo getModuleInfo() {
        String path = GeneratorUtils.class.getResource("/").getPath();
        path = path.replaceFirst("\\/target\\/[\\w-]+\\/$", "/src/main/java");

        File moduleDir = new File(path);

        ModuleInfo moduleInfo = new ModuleInfo();
        String moduleDirAbsolutePath = moduleDir.getAbsolutePath();
        moduleInfo.setModulePath(moduleDirAbsolutePath);

        File mainClassFile = findMainClassFile(moduleDir);
        if (mainClassFile == null) {
            throw new RuntimeException("没用找到启动类文件");
        }
        String parentPackage = mainClassFile.getParent().replace(moduleDirAbsolutePath + File.separator, "").replace(File.separator, ".");

        moduleInfo.setParentPackage(parentPackage);

        return moduleInfo;
    }

    /**
     * 获取MainClass文件
     *
     * @param dir
     * @return
     */
    private static File findMainClassFile(File dir) {
        List<File> files = Arrays.stream(dir.listFiles()).sorted((v1, v2) -> {
            if (v2.isFile()) {
                return 1;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                return file;
            }

            if (file.isDirectory()) {
                File file1 = findMainClassFile(file);
                if (file1 != null) {
                    return file1;
                }
            }
        }
        return null;
    }
}
