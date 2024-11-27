package com.chuanglian.mingpin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan("com.chuanglian.mingpin.mapper")
public class MyBatisConfig {

//    @Bean
//    public PageInterceptor pageInterceptor() {
//        PageInterceptor pageInterceptor = new PageInterceptor();
//
//        // 配置 PageInterceptor 的属性
//        Properties properties = new Properties();
//        properties.setProperty("helperDialect", "sqlserver2012");     // 设置数据库方言
//        properties.setProperty("reasonable", "true");                 // 启用合理化分页
//        properties.setProperty("supportMethodsArguments", "true");    // 支持通过方法参数传递分页参数
//        properties.setProperty("params", "count=countSql");           // 设置 count 属性
//
//        pageInterceptor.setProperties(properties);
//        return pageInterceptor;
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//
//        // 其他配置，添加 PageInterceptor 插件等
//        sqlSessionFactoryBean.setPlugins(pageInterceptor());
//
//        return sqlSessionFactoryBean.getObject();
//    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页拦截器，支持多数据库
        System.out.println("MyBatisConfig.mybatisPlusInterceptor");
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQL_SERVER)); // 替换为对应的数据库类型
        return interceptor;
    }
}
