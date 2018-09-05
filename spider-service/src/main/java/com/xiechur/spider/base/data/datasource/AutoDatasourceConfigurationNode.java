package com.xiechur.spider.base.data.datasource;

import java.util.Map;

public class AutoDatasourceConfigurationNode {

    private String dataSourceType;
    private String nodeType;
    private String nodeName;
    private boolean supportTransaction = false;
    private Map<String, Object> configProperties;

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isSupportTransaction() {
        return supportTransaction;
    }

    public void setSupportTransaction(boolean supportTransaction) {
        this.supportTransaction = supportTransaction;
    }

    public Map<String, Object> getConfigProperties() {
        return configProperties;
    }

    public void setConfigProperties(Map<String, Object> configProperties) {
        this.configProperties = configProperties;
    }

}
