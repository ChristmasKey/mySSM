package com.stone;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class Generator {
    public static void main(String[] args) {
        // 1.获取代码生成器对象实例
        AutoGenerator autoGenerator = new AutoGenerator();

        // 设置数据库相关配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/mybatisplus_db?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("1234");
        autoGenerator.setDataSource(dataSourceConfig);

        // 设置全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/mybatisplus_04_generator/src/main/java"); // 指定生成的代码存放位置
        globalConfig.setOpen(false); // 生成后是否打开资源管理器
        globalConfig.setAuthor("stone"); // 设置作者
        globalConfig.setFileOverride(true); // 重新生成时文件是否覆盖
        globalConfig.setMapperName("%sDao"); // 设置数据层接口名，%s为占位符，指代模块名称
        globalConfig.setIdType(IdType.AUTO); // 设置主键策略
        autoGenerator.setGlobalConfig(globalConfig);

        // 设置包名相关配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.stone"); // 设置生成的包名，与src/main/java保持一致
        packageConfig.setEntity("domain"); // 设置实体类包名
        packageConfig.setMapper("dao"); // 设置数据层包名
        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("t_user"); // 设置当前参与生成的表名
        strategyConfig.setTablePrefix("t_"); // 设置数据库表的前缀，模块名 = 数据库表名 - 前缀
        strategyConfig.setRestControllerStyle(true); // 设置是否开启Restful风格
        strategyConfig.setVersionFieldName("version"); // 设置乐观锁字段名
        strategyConfig.setLogicDeleteFieldName("deleted"); // 设置逻辑删除字段名
        strategyConfig.setEntityLombokModel(true); // 设置是否启用lombok
        autoGenerator.setStrategy(strategyConfig);

        // 执行生成操作
        autoGenerator.execute();
    }
}
