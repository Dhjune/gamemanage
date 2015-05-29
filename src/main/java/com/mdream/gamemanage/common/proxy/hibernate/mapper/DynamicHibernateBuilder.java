package com.mdream.gamemanage.common.proxy.hibernate.mapper;

import java.io.IOException;
import java.util.Map;

public interface DynamicHibernateBuilder {
	/** 
     * hql语句map 
     * @return 
     */  
    public Map<String,String> getNamedHQLQueries();  
    /** 
     * sql语句map 
     * @return 
     */  
    public Map<String,String> getNamedSQLQueries();  
    /** 
     * 初始化 
     * @throws IOException  
     */  
    public void init() throws IOException;  
}
