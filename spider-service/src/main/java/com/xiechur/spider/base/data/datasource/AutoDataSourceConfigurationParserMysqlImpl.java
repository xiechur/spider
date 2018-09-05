package com.xiechur.spider.base.data.datasource;

import com.xiechur.spider.base.data.datasource.bind.RelaxedPropertyResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class AutoDataSourceConfigurationParserMysqlImpl {

    public static final String DEF_JDBC_URL = "jdbc:mysql://${host}:${port}/${default_db}?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull";

    private String key;
    private RelaxedPropertyResolver rootPropertyResolver;
    private String environmentType;

    public AutoDataSourceConfigurationParserMysqlImpl(String environmentType, String key,
            RelaxedPropertyResolver rootPropertyResolver) {
        this.environmentType = environmentType;
        this.key = key;
        this.rootPropertyResolver = rootPropertyResolver;
    }

    @SuppressWarnings("deprecation")
	public AutoDatasourceConfigurationNode parser() {
        String nodeType = AutoDataSourceUtils.getWhichDataSourceType(key);
        Assert.isTrue("mysql".equals(nodeType));
        String nodeName = AutoDataSourceUtils.getDataSourceRootNode(key, nodeType);
        if (!StringUtils.isEmpty(nodeName)) {
            Map<String, Object> configProperties = new LinkedHashMap<String, Object>();
            configProperties.putAll(rootPropertyResolver.getSubProperties((this.environmentType + ".datasource."
                    + nodeType + "." + nodeName + ".")));
            TrimConfigBlankUtil.removeBlank(configProperties);

            if ("normal".equals(this.environmentType)) {
                String url = AutoDataSourceUtils.evalString(configProperties.get("url"));
                String[] temp = url.split(":");
                if (temp.length == 3) {
                    String tempJdbcUrl = DEF_JDBC_URL.replace("${host}", temp[0]);
                    tempJdbcUrl = tempJdbcUrl.replace("${port}", temp[1]);
                    tempJdbcUrl = tempJdbcUrl.replace("${default_db}", temp[2]);
                    configProperties.put("url", tempJdbcUrl);
                }
            }

//            if ("rise".equals(this.environmentType)) {
//                String slaveStr = AutoDataSourceUtils.evalString(configProperties.get("slave"));
//                configProperties.remove("slave");
//                boolean slave = "true".equals(slaveStr);
//
//                String riseName = AutoDataSourceUtils.evalString(configProperties.get("value"));
//                if (StringUtils.isEmpty(riseName)) {
//                    throw new RuntimeException("请配置升龙节点[value]值");
//                }
//                configProperties.remove("value");
//
//                String[] props = AutoDataSourceUtils.getRiseMysqlProperty(riseName, slave);
//                String host = props[0];
//                String port = props[1];
//                String user = props[2];
//                String pass = props[3];
//                String db = props[4];
//
//                String tempJdbcUrl = DEF_JDBC_URL.replace("${host}", host);
//                tempJdbcUrl = tempJdbcUrl.replace("${port}", port);
//                tempJdbcUrl = tempJdbcUrl.replace("${default_db}", db);
//                configProperties.put("url", tempJdbcUrl);
//                configProperties.put("username", user);
//                configProperties.put("password", pass);
//            }

            String dataSourceType = AutoDataSourceUtils.evalString(configProperties.get("dataSourceType"));
            if (!StringUtils.isEmpty(dataSourceType)) {
                configProperties.remove("dataSourceType");
            }

            AutoDatasourceConfigurationNode autoDatasourceConfigurationNode = new AutoDatasourceConfigurationNode();
            autoDatasourceConfigurationNode.setNodeType(nodeType);
            autoDatasourceConfigurationNode.setNodeName(nodeName);
            autoDatasourceConfigurationNode.setDataSourceType(dataSourceType);

            DataSourceDefaultPropertiesUtil.addDefaultValue(dataSourceType, configProperties);

            String transaction = AutoDataSourceUtils.evalString(configProperties.get("transaction"));
            if ("true".equals(transaction)) {
                configProperties.remove("transaction");
                autoDatasourceConfigurationNode.setSupportTransaction(true);
            }

            autoDatasourceConfigurationNode.setConfigProperties(Collections.unmodifiableMap(configProperties));

            return autoDatasourceConfigurationNode;

        }

        throw new RuntimeException("数据源mysql配置文件出");

    }

}
