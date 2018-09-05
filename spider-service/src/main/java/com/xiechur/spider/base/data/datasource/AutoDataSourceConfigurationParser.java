package com.xiechur.spider.base.data.datasource;

import com.xiechur.spider.base.data.datasource.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;

import java.util.*;

public class AutoDataSourceConfigurationParser {

    private Environment environment;

    public AutoDataSourceConfigurationParser(Environment environment) {
        this.environment = environment;
    }

    Set<String> nodesSet = new HashSet<String>();

    public List<AutoDatasourceConfigurationNode> parser() {

        RelaxedPropertyResolver rootPropertyResolver = new RelaxedPropertyResolver(this.environment, "spring.auto.");
        List<AutoDatasourceConfigurationNode> nodes = new ArrayList<AutoDatasourceConfigurationNode>();

        String[] datasourceEnvironmentType = { "normal", "rise" };
        for (String environmentType : datasourceEnvironmentType) {
            Map<String, Object> normalConfigProperties = rootPropertyResolver.getSubProperties(environmentType
                    + ".datasource.");

            for (Map.Entry<String, Object> entry : normalConfigProperties.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("mysql.")) {
                    String nodeName = "mysql:" + AutoDataSourceUtils.getDataSourceRootNode(key, "mysql");
                    if (!nodesSet.contains(nodeName)) {
                        nodes.add(new AutoDataSourceConfigurationParserMysqlImpl(environmentType, key,
                                rootPropertyResolver).parser());
                        nodesSet.add(nodeName);
                    }

                }
                if (key.startsWith("redis.")) {
                    String nodeName = "redis:" + AutoDataSourceUtils.getDataSourceRootNode(key, "redis");
                    if (!nodesSet.contains(nodeName)) {
                        nodes.add(new AutoDataSourceConfigurationParserRedisImpl(environmentType, key,
                                rootPropertyResolver).parser());
                        nodesSet.add(nodeName);
                    }
                }
            }
        }
        return nodes;
    }
}
