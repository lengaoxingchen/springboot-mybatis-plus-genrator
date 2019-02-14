package com.lqf.springbootmybatisplusgenrator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Description
 * @Author lujichao
 * @Date 2019/2/14
 */
;

/**
 * <p>
 * 代码生成器
 * </p>
 */
public class MpGenerator {

    public static void main(String[] args)  {
        String[] models = {"book_server_ssm"};
        for (String model : models) {
            shell(model);
        }
    }

    private static void shell(String model) {
        //用来获取Mybatis-Plus.properties文件的配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus");

        File file = new File(model);
        String path = file.getAbsolutePath();
        System.out.println(path);
        path = path.substring(0, path.lastIndexOf(File.separator));
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(rb.getString("OutputDir"));
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor(rb.getString("author"));
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(rb.getString("url"));
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(rb.getString("parent"));
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("bean");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return rb.getString("OutputDirXml") + "/mapper/" + tableInfo.getEntityName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new

                TemplateConfig().

                setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        //如果指定表名则根据表名生成对应的文件.否则生成全部表对应的文件
        if(!rb.getString("tableName").isEmpty()) {
            strategy.setInclude(new String[]{rb.getString("tableName")});
        }else
        {
            strategy.setInclude(new String[]{});
        }
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }
}
