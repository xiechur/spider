package com.xiechur.spider.base.data.datasource;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

public class AutoDataSourceSpringBootRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		List<AutoDatasourceConfigurationNode> allNodes = new AutoDataSourceConfigurationParser(this.environment)
				.parser();

		for (AutoDatasourceConfigurationNode node : allNodes) {
			String nodeType = node.getNodeType();
			if ("mysql".equals(nodeType)) {
				new AutoDataSourceBuildAndRegistryMysqlImpl(node, registry).buildAndRegistry();
			}
			if ("redis".equals(nodeType)) {
				new AutoDataSourceBuildAndRegistryRedisImpl(node, registry).buildAndRegistry();
			}
		}

		new AutoDataSourceBuildAndRegistryMysqlImpl(null, registry).buildDefaultRegistry();
		new AutoDataSourceBuildAndRegistryRedisImpl(null, registry).buildDefaultRegistry();

	}
}
