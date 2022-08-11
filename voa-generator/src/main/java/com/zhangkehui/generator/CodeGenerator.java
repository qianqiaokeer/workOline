package com.zhangkehui.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 代码自动生成
 * 控制台输入数据库表名回车自动生成对应项目的pojo、xml
 */
public class CodeGenerator {
    /**
     * 读取控制台输入内容
     * @param tip
     * @return
     */
    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入"+tip+":");
        System.out.println(help.toString());
        if (scanner.hasNext()){
            String str = scanner.next();
            if (StringUtils.isNotEmpty(str)){
                return str;
            }
        }
        throw new MybatisPlusException("请输入正确的"+tip+"!");
    }

    public static void main(String[] args) {
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //拿到项目根目录
        String projectPath = System.getProperty("user.dir");
        //设置生成代码放置在那个目录下
        globalConfig.setOutputDir(projectPath+"/voa-generator/src/main/java");
        //设置作者
        globalConfig.setAuthor("kehui");
        //是否打开资源管理器 这里不打开
        globalConfig.setOpen(false);
        //
        globalConfig.setBaseResultMap(true);
        //
        globalConfig.setBaseColumnList(true);
        //实体属性Swagger2注解
        globalConfig.setSwagger2(true);
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3366/yeb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        //包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.zhangkehui")
                .setEntity("pojo")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller");
        //自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                //nothing
            }
        };
        //模板引擎 freemarker
        //路径
        String templatePath = "/templates/mapper.xml.ftl";
        //如果模板引擎是 velocity
        //String templatePath = "/templates/mapper.xml.vm";
        //自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //自定义输出文件名
                return projectPath + "/voa-generator/src/main/resources/mapper/" + tableInfo.getEntityName() +"Mapper"+ StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        //配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //数据库表映射到实体类的命名策略
        strategyConfig.setNaming(NamingStrategy.no_change);
        //lombok模型
        strategyConfig.setEntityLombokModel(true);
        //是否使用@RestController 控制器
        strategyConfig.setRestControllerStyle(true);
        //设置参与生成代码的表名 setInclude()方法的参数是可变参数
        strategyConfig.setInclude(scanner("表名，多个以英文逗号分割").split(","));
        strategyConfig.setControllerMappingHyphenStyle(true);
        //表前缀 表示当数据库表名是t_user 实体类的名称是去掉前缀t_的User
        strategyConfig.setTablePrefix("t_");
        //将以上配置注入代码生成器
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        //执行
        autoGenerator.execute();
    }
}
