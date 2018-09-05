package com.xiechur.spider.base.data.datasource;

import com.xiechur.spider.base.data.datasource.bind.RelaxedPropertyResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class AutoDataSourceConfigurationParserRedisImpl {

    private String key;
    private RelaxedPropertyResolver rootPropertyResolver;
    private String environmentType;

    public AutoDataSourceConfigurationParserRedisImpl(String environmentType, String key,
            RelaxedPropertyResolver rootPropertyResolver) {
        this.environmentType = environmentType;
        this.key = key;
        this.rootPropertyResolver = rootPropertyResolver;
    }

    @SuppressWarnings("deprecation")
	public AutoDatasourceConfigurationNode parser() {
        String nodeType = AutoDataSourceUtils.getWhichDataSourceType(key);
        Assert.isTrue("redis".equals(nodeType));
        String nodeName = AutoDataSourceUtils.getDataSourceRootNode(key, nodeType);
        if (!StringUtils.isEmpty(nodeName)) {

            Map<String, Object> configProperties = new LinkedHashMap<String, Object>();
            configProperties.putAll(rootPropertyResolver.getSubProperties((this.environmentType + ".datasource." + nodeType
                    + "." + nodeName + ".")));
            TrimConfigBlankUtil.removeBlank(configProperties);
//            if ("rise".equals(this.environmentType)) {
//
//                String riseName = AutoDataSourceUtils.evalString(configProperties.get("value"));
//                if (StringUtils.isEmpty(riseName)) {
//                    throw new RuntimeException("请配置升龙节点[value]值");
//                }
//                configProperties.remove("value");
//
//                String[] props = AutoDataSourceUtils.getRiseRedisProperty(riseName);
//                String host = props[0];
//                String port = props[1];
//                String pass = props[2];
//
//                configProperties.put("hostName", host);
//                configProperties.put("port", port);
//                configProperties.put("password", pass);
//            }

            AutoDatasourceConfigurationNode autoDatasourceConfigurationNode = new AutoDatasourceConfigurationNode();
            autoDatasourceConfigurationNode.setNodeType(nodeType);
            autoDatasourceConfigurationNode.setNodeName(nodeName);
            autoDatasourceConfigurationNode.setConfigProperties(Collections.unmodifiableMap(configProperties));

            String transaction = AutoDataSourceUtils.evalString(configProperties.get("transaction"));
            if ("true".equals(transaction)) {
                configProperties.remove("transaction");
                autoDatasourceConfigurationNode.setSupportTransaction(true);
            }

            return autoDatasourceConfigurationNode;

        }

        throw new RuntimeException("数据源redis配置文件出");
    }

}
