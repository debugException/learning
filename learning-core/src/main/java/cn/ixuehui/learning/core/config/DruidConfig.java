package cn.ixuehui.learning.core.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;

/**
 * druid连接池的配置
 * @author szq
 * Date: 2018年12月28日 上午9:46:26
 */
@Configuration
@PropertySource(value="classpath:druid.properties")
public class DruidConfig {
	
	@Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }


	@Bean
	public ServletRegistrationBean<StatViewServlet> servletRegistrationBean(){
		return new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
	}
}
