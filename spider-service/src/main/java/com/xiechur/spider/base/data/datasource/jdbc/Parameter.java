package com.xiechur.spider.base.data.datasource.jdbc;

/**
 * <pre>
 * Title:查询参数类，用于表示条件参数对象
 * Description: 查询参数类，用于表示条件参数对象
 * </pre>
 * @author  wolfdog
 * @version 1.00.00
 * <pre>
 * 修改记录
 * 修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class Parameter {
	
	private String field;  
    private Object value;  
    private String operator;  
    /** 
     *  
     * @param field 数据库字段名 
     * @param operator 数据库操作符 =、>=、<、like etc... 
     * @param value 参数值 Object 
     */  
    public Parameter(String field, String operator, Object value) {  
        super();  
        this.field = field;  
        this.value = value;  
        this.operator = operator;  
    }  
    
    public String getField() {  
        return field;  
    }  
    
    public Object getValue() {  
        return value;  
    }  
    
    public String getOperator() {  
        return operator;  
    }     
	
}
