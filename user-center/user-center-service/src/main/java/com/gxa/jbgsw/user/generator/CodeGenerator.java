package com.gxa.jbgsw.user.generator;




/**


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;



public class CodeGenerator {


    public static void main(String[] args) {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // gc.setOutputDir("G:/codeGenerator/user-center/src/main/java");
        // gc.setOutputDir(projectPath + "/basis-center/src/main/java");
        // gc.setOutputDir(projectPath + "/iot-portal/src/main/java");
        // gc.setOutputDir(projectPath + "/device-center/src/main/java");
        // gc.setOutputDir(projectPath + "/data-center/src/main/java");
        //gc.setOutputDir(projectPath + "/data-center/src/main/java");

        gc.setOutputDir("G:/codeGenerator/business-center/src/main/java");


        // 设置作者
        gc.setAuthor("huangxc");
        // 生成后是否打开资源管理器
        gc.setOpen(false);
        // 重新生成时文件是否覆盖
        gc.setFileOverride(false);
        // 去掉Service接口的首字母I
        gc.setServiceName("%sService");
        // 生成主键方式
        gc.setIdType(IdType.ID_WORKER_STR);
        // 定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        // 开启Swagger2模式
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();


        // dsc.setUrl("jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        // dsc.setUrl("jdbc:mysql://localhost:3306/base?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        // dsc.setUrl("jdbc:mysql://139.159.202.43:3306/device?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        // dsc.setUrl("jdbc:mysql://139.159.202.43:3306/portal?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        // dsc.setUrl("jdbc:mysql://139.159.202.43:3306/stat?useUnicode=true&characterEncoding=utf-8&useSSL=false");

        dsc.setUrl("jdbc:mysql://localhost:3306/business?useUnicode=true&characterEncoding=utf-8&useSSL=false");


        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Antan!@#qwe");
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                //tinyint转换成Boolean
                if (fieldType.toLowerCase().contains("tinyint")) {
                    return DbColumnType.INTEGER;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            };
        });
        mpg.setDataSource(dsc);


        // 4、包配置
        PackageConfig pc = new PackageConfig();

        // pc.setParent("com.gxa.jbgsw.user");
        // pc.setParent("com.antancorp.iot.basis");
        // pc.setParent("com.antancorp.iot.portal");
        // pc.setParent("com.antancorp.iot.device");
        // pc.setParent("com.antancorp.iot.stat");

        pc.setParent("com.gxa.jbgsw.business");

        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");

        mpg.setPackageInfo(pc);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                // return projectPath + "/user-center/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                // return projectPath + "/basis-center/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                // return projectPath + "/iot-portal/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                // return projectPath + "/device-center/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;

                return projectPath + "/business-center/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);


        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);


        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 设置要映射的表名
        //strategy.setInclude("dectionary","dictioinary_type");
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 设置表前缀不生成
        strategy.setTablePrefix("t_");
        // 数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // restful api风格控制器
        strategy.setRestControllerStyle(true);
        // url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // lombok模式
        strategy.setEntityLombokModel(true);
        strategy.setLogicDeleteFieldName("is_delete");

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }



}

*/

