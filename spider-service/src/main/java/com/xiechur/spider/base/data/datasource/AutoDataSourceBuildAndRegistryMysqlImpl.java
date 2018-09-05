package com.xiechur.spider.base.data.datasource;

import com.xiechur.spider.base.data.datasource.jdbc.JdbcMysqlImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;

public class AutoDataSourceBuildAndRegistryMysqlImpl {

    Logger logger = LoggerFactory.getLogger(AutoDataSourceBuildAndRegistryMysqlImpl.class);

    AutoDatasourceConfigurationNode autoDatasourceConfigurationNode;
    BeanDefinitionRegistry registry;

    public AutoDataSourceBuildAndRegistryMysqlImpl(AutoDatasourceConfigurationNode autoDatasourceConfigurationNode,
            BeanDefinitionRegistry registry) {
        this.autoDatasourceConfigurationNode = autoDatasourceConfigurationNode;
        this.registry = registry;
    }

    public void buildAndRegistry() {

        Map<String, Object> configProperties = autoDatasourceConfigurationNode.getConfigProperties();
        String nodeName = autoDatasourceConfigurationNode.getNodeName();
        boolean supportTransaction = autoDatasourceConfigurationNode.isSupportTransaction();
        logger.debug("mysql->" + nodeName + "->" + configProperties);
        String dataSourceType = AutoDataSourceUtils.evalString(autoDatasourceConfigurationNode.getDataSourceType());
        Class<? extends DataSource> dataSourceClass = new MysqlDataSourceLoader().loadDataSource(dataSourceType, this
                .getClass().getClassLoader());

        String mysqlDataSourceBeanName = nodeName + "MysqlDataSource";
        String jdbcTempleBeanName = nodeName + "JdbcTemplate";
		String jdbcBeanName = nodeName + "Jdbc";
//        String rhinoJdbcTempleBeanName = nodeName + "RhinoJdbcTemplate";
        String mysqlTransactionManagerBeanName = nodeName + "MysqlTransactionManager";
        String mysqlTransactionTemplateBeanName = nodeName + "MysqlTransactionTemplate";

        // register DataSource
        {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(dataSourceClass);
            MutablePropertyValues properties = new MutablePropertyValues(configProperties);
            beanDefinition.getPropertyValues().addPropertyValues(properties);
            registry.registerBeanDefinition(mysqlDataSourceBeanName, beanDefinition);
        }

        // register JdbcTemple
        {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(JdbcTemplate.class);
            beanDefinition.getPropertyValues().addPropertyValue("dataSource",
                    new RuntimeBeanReference(mysqlDataSourceBeanName));
            registry.registerBeanDefinition(jdbcTempleBeanName, beanDefinition);
        }
        
        {
        	// register JdbcMysqlImpl
			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
			beanDefinition.setBeanClass(JdbcMysqlImpl.class);
			beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue( 0, new RuntimeBeanReference(jdbcTempleBeanName));
			registry.registerBeanDefinition(jdbcBeanName, beanDefinition);
        }

        {
            // register transaction
            if (supportTransaction) {
                {
                    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                    beanDefinition.setBeanClass(DataSourceTransactionManager.class);
                    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,
                            new RuntimeBeanReference(mysqlDataSourceBeanName));
                    registry.registerBeanDefinition(mysqlTransactionManagerBeanName, beanDefinition);
                }
                {
                    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                    beanDefinition.setBeanClass(TransactionTemplate.class);
                    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,
                            new RuntimeBeanReference(mysqlTransactionManagerBeanName));
                    registry.registerBeanDefinition(mysqlTransactionTemplateBeanName, beanDefinition);
                }

            }
        }

        logger.info(":::mysql:::" + mysqlDataSourceBeanName + ":::jdbc:::" + jdbcBeanName);

    }

    public void buildDefaultRegistry() {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MysqlDefaultDataSource.class);
        beanDefinition.setPrimary(true);
        registry.registerBeanDefinition("mysqlDefaultDataSource", beanDefinition);
    }

}
