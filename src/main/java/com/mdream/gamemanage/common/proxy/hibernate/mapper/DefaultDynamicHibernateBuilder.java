package com.mdream.gamemanage.common.proxy.hibernate.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.internal.util.xml.XmlDocument;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.InputSource;



public class DefaultDynamicHibernateBuilder implements DynamicHibernateBuilder, ResourceLoaderAware{
	 private Map<String, String> namedHQLQueries;  
	    private Map<String, String> namedSQLQueries;  
	    private String[] fileNames = new String[0];  
	    private ResourceLoader resourceLoader;  
	    protected SAXReader reader = new SAXReader();
 	
	    /** 
	     * 查询语句名称缓存，不允许重复 
	     */  
	    private Set<String> nameCache = new HashSet<String>();  
	  
	    public void setFileNames(String[] fileNames) {  
	        this.fileNames = fileNames;  
	    }  
	  
	    public Map<String, String> getNamedHQLQueries() {  
	        return namedHQLQueries;  
	    }  
	  
	    public Map<String, String> getNamedSQLQueries() {  
	        return namedSQLQueries;  
	    }  
	  
	    public void init() throws IOException {  
	    	System.out.println("1111111111111111111111111111111111111111111111111");
	    	reader.setEntityResolver(new IgnoreDTDEntityResolver());
	        namedHQLQueries = new HashMap<String, String>();  
	        namedSQLQueries = new HashMap<String, String>();  
	        boolean flag = this.resourceLoader instanceof ResourcePatternResolver;  
	        for (String file : fileNames) {  
	        	System.out.println("*************************"+file);
	            if (flag) {  
	                Resource[] resources = ((ResourcePatternResolver) this.resourceLoader).getResources(file);  
	                buildMap(resources);  
	            } else {  
	                Resource resource = resourceLoader.getResource(file);  
	                buildMap(resource);  
	            }  
	        }  
	        //clear name cache  
	        nameCache.clear();  
	    }  
	  
	    public void setResourceLoader(ResourceLoader resourceLoader) {  
	    	System.out.println("222222222222222222222222222222222222222");
	    	
	        this.resourceLoader = resourceLoader;  
	        try {
				init();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }  
	  
	    private void buildMap(Resource[] resources) throws IOException {  
	        if (resources == null) {  
	            return;  
	        }  
	        for (Resource resource : resources) {  
	            buildMap(resource);  
	        }  
	    }  
	  
	    @SuppressWarnings({ "rawtypes" })  
	    private void buildMap(Resource resource) throws SysException {  
	        InputSource inputSource = null;  
	        
	        try {  
	        	
	        	Document doc = reader.read(resource.getFile());
	        	Element root = doc.getRootElement();
	        	 
	        	for(Iterator it=root.elementIterator();it.hasNext();){ 
	        		
	                Element element = (Element) it.next();
	             
	                String elementName = element.getName();
	                if ("sql-query".equals(elementName)) {  
                     putStatementToCacheMap(resource, element, namedSQLQueries);  
                 } else if ("hql-query".equals(elementName)) {  
                     putStatementToCacheMap(resource, element, namedHQLQueries);  
                 }       
	         }  
	        } catch (Exception e) {  
	             
	            throw new SysException(e);  
	        } finally {  
	            if (inputSource != null && inputSource.getByteStream() != null) {  
	                try {  
	                    inputSource.getByteStream().close();  
	                } catch (IOException e) {  
	                  
	                	 throw new SysException(e);  
	                }  
	            }  
	        }  
	  
	    }  
	  
	    private void putStatementToCacheMap(Resource resource, final Element element, Map<String, String> statementMap)  
	            throws IOException {  
	        String sqlQueryName = element.attribute("name").getText();  
	        Validate.notEmpty(sqlQueryName);  
	        if (nameCache.contains(sqlQueryName)) { 
	        	 throw new SysException("重复的sql-query/hql-query语句定义在文件:" + resource.getURI() + "中，必须保证name的唯一.");  
	        }  
	        nameCache.add(sqlQueryName);  
	        String queryText = element.getText();  
	        statementMap.put(sqlQueryName, queryText);  
	    }  
	  
	    private static boolean isDynamicStatementXml(XmlDocument xmlDocument) {  
	        return "dynamic-hibernate-statement".equals(xmlDocument.getDocumentTree().getRootElement().getName());  
	    }  

}
