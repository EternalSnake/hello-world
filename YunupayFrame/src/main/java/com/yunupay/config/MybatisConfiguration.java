package com.yunupay.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


/**
 * 
 * @Package com.yunupay.config 	
 * @ClassName: MybatisConfiguration 
 * @Description: mybatis配置
 * @author qijiaxv
 * @time   2017年10月30日 下午1:36:00
 * @version V
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.yunupay.*.*.mapper")
public class MybatisConfiguration implements TransactionManagementConfigurer{
	@Autowired
	private DataSource dataSource;

	/**
	 * 
	 * @Title: createSqlSessionFactoryBean 
	 * @Description: 创建sqlSessionFactoryBean 实例 并且设置configtion 如驼峰命名.等等 设置mapper 映射路径    设置datasource数据源
	 * @author qijiaxv
	 * @return
	 * @throws Exception 
	 * @throws
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 设置datasource 
		sqlSessionFactoryBean.setDataSource(dataSource);
		return sqlSessionFactoryBean;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
