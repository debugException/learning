package cn.ixuehui.learning.dao.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import cn.ixuehui.learning.core.annotation.MyDataSource.DBTypeEnum;
import cn.ixuehui.learning.core.db.DynamicDataSource;

@EnableTransactionManagement
@Configuration
@MapperScan("cn.ixuehui.**.mapper")
public class MyBatisPlusConfig {

	
	/**
	 * 分页插件
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
	
	/**
	 * sql注入
	 * @return
	 */
	@Bean
	public ISqlInjector iSqlInjector() {
		return new LogicSqlInjector();
	}
	
	/**
     * sql性能分析插件，输出sql语句及所需时间
     * @return
     */
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
    
    /**
     * 执行分析插件，防止全表delete或update操作
     * @return
     */
    @Bean
    @Profile({"dev","test"})
    public SqlExplainInterceptor sqlExplainInterceptor() {
    	return new SqlExplainInterceptor();
    }
    
    /**
     * 主数据库
     * @return
     */
    @Bean(name="master")
    @ConfigurationProperties(prefix="spring.datasource.master")
    public DataSource masterDB() {
    	return DruidDataSourceBuilder.create().build();
    }
    
    /**
     * 日志数据库
     * @return
     */
    @Bean(name="common")
    @ConfigurationProperties(prefix="spring.datasource.common")
    public DataSource commonDB() {
    	return DruidDataSourceBuilder.create().build();
    }
    
    
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("master") DataSource masterDB, @Qualifier("common") DataSource commonDB) {
    	DynamicDataSource dataSource = new DynamicDataSource();
    	Map<Object, Object> targetDataSources = new HashMap<>();
    	targetDataSources.put(DBTypeEnum.master.getValue(), masterDB);
    	targetDataSources.put(DBTypeEnum.common.getValue(), commonDB);
    	dataSource.setTargetDataSources(targetDataSources);
    	dataSource.setDefaultTargetDataSource(masterDB);
    	return dataSource;
    }
    
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
    	MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(masterDB(), commonDB()));
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(mybatisConfiguration);
        sqlSessionFactory.setPlugins(new Interceptor[] {
        		paginationInterceptor(),
        		sqlExplainInterceptor(),
        		performanceInterceptor()
        });
        return sqlSessionFactory.getObject();
    }
    

}
