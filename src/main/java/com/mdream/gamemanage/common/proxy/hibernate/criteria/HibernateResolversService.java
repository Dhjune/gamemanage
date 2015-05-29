package com.mdream.gamemanage.common.proxy.hibernate.criteria;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 
 * @ClassName: HibernateResolversService 
 * @Description: TODO 用于常规动态查询,主被动关系明确的查询 
 * @author june
 * @date 2015年4月1日
 */
//@Service  
//@Transactional 声明式事务层  ，因为具体封装在各个业务层Service,此处还是还是Component以免事务层叠出现错误
@Component
public class HibernateResolversService extends ResolverServiceAbstract{
		
	@Autowired
	private  HibernateResolvers hibernateResolvers;
	
	
	
	
	public <T> T find(Class clazz,int id){
		
		return hibernateResolvers.find(clazz, id);
		
	}
	
	
	public <T> void save(T t) throws Exception{
		
		hibernateResolvers.save(t);
		
	}
	
	public <T> void update(T t) throws Exception{
		
		hibernateResolvers.update(t);
		
	}
	
	public <T> void merge(T t) throws Exception{
		hibernateResolvers.merge(t);
	}
	
	public <T> void delete(T t)throws Exception{
		
		hibernateResolvers.delete(t);
		
	}
	
	/**
	 * 
	 * @Title: getList 
	 * @Description: 通过前端传递的“特定”查询条件，以及service层传入需要的对象.
	 * @param list 前端查询条件封装的list数组
	 * @param target  需要查询的对象-table 的实例
	 * @param pageIndex 分页相关
	 * @param pageSize 分页相关
	 * @param url    分页相关 -url
	 * @param bool  -暂时无意义
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @return List<T>
	 * @throws ParseException 
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> getList(List<ExpressionGroup> list , T target,int pageIndex,int pageSize,String url,Boolean bool) throws NoSuchFieldException, SecurityException, ParseException{
		//超复杂关系之间的查询,切记所有createCriteria  必须建立在当前表-criteria为主控方，以及包含“createCriteria("String") 中的--String ”
		//除了createCriteria将使用层级 中的-criteria(tempC),添加条件时全部使用根节点的criteria
		//目前的设计，需要保存所有creteria ，HashMap<criteria>	
		
		Criteria criteria = null;
		Criteria tempC = null;
		//保存Criteria
		Map<String,Criteria> cMaps =new HashMap<String,Criteria>();
		//保存关联关系中对象Class
		Map<String,Class> clses =new HashMap<String,Class>();
		
		if(list!=null&&list.size()>0){
			if(list.size()>1){
				//将操作序列进行排序 
				Collections.sort(list,new Comparator<ExpressionGroup>(){
	    		public int compare(ExpressionGroup arg0, ExpressionGroup arg1) {
		                return arg0.getGroup().compareTo(arg1.getGroup());
		            }
		        });	
				if(list.get(0).getGroup()>1&&list.get(0).getGroup()<3){
					criteria = hibernateResolvers.createCriteria(target, list.get(0).getUpalias());
					tempC = criteria;
					cMaps.put(list.get(0).getUpalias(),tempC);
					clses.put(list.get(0).getUpalias(), target.getClass());	
				}else if(list.get(0).getGroup()>2){
					System.out.println("大于3了，我表示罢工了");
					criteria = hibernateResolvers.createCriteria(target, null);
					return criteria.list();
					
				}
			}	
			ExpressionGroup eg = null;
			
			
			Class  clazz = null;
			for(int i=0;i<list.size();i++){
				ExpressionGroup temp = list.get(i);
				if(eg == null&&temp.getGroup()<=1){
					
					criteria = hibernateResolvers.initCriteria(temp, target);
					tempC = criteria;
					cMaps.put(temp.getAlias(),tempC);
					clses.put(temp.getAlias(), target.getClass());					
				}
				else if(eg!=null && eg.getGroup()==temp.getGroup()&&temp.getGroup() <=1){
					
					hibernateResolvers.paddingCriteria(criteria, temp, target.getClass());
					
				}else if(eg!=null && eg.getGroup()==temp.getGroup()&&temp.getGroup() > 1){
					
					hibernateResolvers.paddingCriteria(criteria, temp, clazz);
					
				}
				
				else{
					
					Criteria c =  cMaps.get(temp.getUpalias());
					Class tempClass = clses.get(temp.getUpalias());
					
					if(c!=null && tempClass!=null){	
						
						clazz =  checkClass(tempClass, temp.getAlias());					
						tempC = hibernateResolvers.addCriteria(c,criteria,temp.getAlias(), temp, clazz);
						clses.put(temp.getAlias(), clazz);
						cMaps.put(temp.getAlias(), tempC);
						
					}else{
						
						clazz =  checkClass(target, temp.getAlias());					
						tempC = hibernateResolvers.addCriteria(tempC,criteria,temp.getAlias(), temp, clazz);
						clses.put(temp.getAlias(), clazz);
						cMaps.put(temp.getAlias(), tempC);
						
					}
					
					
				}
				
				eg = temp ;
				
			}
		}else{
			
			criteria = hibernateResolvers.createCriteria(target, null);
			
		}
		//消除root基础类重复复制封装.
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setFetchSize((pageIndex-1)*pageSize);
		criteria.setMaxResults(4);	
		List<T> result = criteria.list();
		System.out.println(criteria.getAlias());
		criteria.setProjection( Projections.projectionList()
				.add( Projections.countDistinct(criteria.getAlias()+".id"))				
			);
		 List total = criteria.list();
		 for(int i=0;i<total.size();i++){
			 System.out.println(total.get(i));
		 }
        return result;
	}	
	
	public <T> Criteria getCriteria(List<ExpressionGroup> list , T target) throws NoSuchFieldException, SecurityException, ParseException{

		Criteria criteria = null;
		Criteria tempC = null;
		//保存Criteria
		Map<String,Criteria> cMaps =new HashMap<String,Criteria>();
		//保存关联关系中对象Class
		Map<String,Class> clses =new HashMap<String,Class>();		
		//将操作序列进行排序 
		if(list!=null&&list.size()>0){
			if(list.size()>1){
				//将操作序列进行排序 
				Collections.sort(list,new Comparator<ExpressionGroup>(){
	    		public int compare(ExpressionGroup arg0, ExpressionGroup arg1) {
		                return arg0.getGroup().compareTo(arg1.getGroup());
		            }
		        });	
				
			}
			
			if(list.get(0).getGroup()>1&&list.get(0).getGroup()<3){
		//		System.out.println("错误在此处  1111");
				criteria = hibernateResolvers.createCriteria(target, list.get(0).getUpalias());
				tempC = criteria;
				cMaps.put(list.get(0).getUpalias(),tempC);
				clses.put(list.get(0).getUpalias(), target.getClass());	
			}else if(list.get(0).getGroup()>2){
		//		System.out.println("错误在此处  2222");
				criteria = hibernateResolvers.createCriteria(target, null);
				return criteria;
				
			}
			
			
			ExpressionGroup eg = null;			
			Class  clazz = null;
			for(int i=0;i<list.size();i++){
				ExpressionGroup temp = list.get(i);
				if(eg == null&&temp.getGroup()<=1){
					
					criteria = hibernateResolvers.initCriteria(temp, target);
					tempC = criteria;
					cMaps.put(temp.getAlias(),tempC);
					clses.put(temp.getAlias(), target.getClass());					
					
				}
				else if(eg!=null && eg.getGroup()==temp.getGroup()&&temp.getGroup() <=1){
					
					if(cMaps.get(temp.getAlias())!=null){
						
						hibernateResolvers.paddingCriteria(criteria, temp, target.getClass());
						
					}
				}else if(eg!=null && eg.getGroup()==temp.getGroup()&&temp.getGroup() > 1){
					if(cMaps.get(temp.getAlias())!=null){
						
						hibernateResolvers.paddingCriteria(criteria, temp, clazz);
						
					}
					
					
				}
				
				else{
					
					Criteria c =  cMaps.get(temp.getUpalias());
					Class tempClass = clses.get(temp.getUpalias());
					
					if(c!=null && tempClass!=null){	
						
						clazz =  checkClass(tempClass, temp.getAlias());					
						tempC = hibernateResolvers.addCriteria(c,criteria,temp.getAlias(), temp, clazz);
						clses.put(temp.getAlias(), clazz);
						cMaps.put(temp.getAlias(), tempC);
						
					}else{	
						
//						clazz =  checkClass(target, temp.getAlias());					
//						tempC = hibernateResolvers.addCriteria(tempC,criteria,temp.getAlias(), temp, clazz);
//						clses.put(temp.getAlias(), clazz);
//						cMaps.put(temp.getAlias(), tempC);
						
					}
					
					
				}
				
				eg = temp ;
				
			}
		}else{
		//	System.out.println("错误在此处  3333");
			criteria = hibernateResolvers.createCriteria(target, null);
			
		}
		//消除root基础类重复复制封装.
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);	
	//	System.out.println(criteria);		
        return criteria;
	} 
	
	/**
	 * 
	 * @Title: checkClass 
	 * @Description: 用于把class中指定"属性:fieldString" -对象检索出来。如果是集合类型，则将集合中的泛型类型检索出来
	 * @param target  传入的Object对象实例 类型
	 * @param fString  当前target。getClass()（对象,类型信息中）中包含的字段
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @return Class
	 */
	@SuppressWarnings("rawtypes")
	public  <T>  Class checkClass(T target,String fString) throws NoSuchFieldException, SecurityException{
		Class clazz = null;	
		
		Field field = target.getClass().getDeclaredField(fString);	
		
		Type fc = field.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型  	
		
        if(fc == null) return null;  
        
        if(fc instanceof ParameterizedType) // 如果是泛型参数的类型   
        {   
               ParameterizedType pt = (ParameterizedType) fc;  
               clazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。  
                
         }else{//不是泛型类型的参数，直接返回当前Type;
        	 
        	 clazz = field.getClass();
         }   
		
		return clazz;
	}
	
	/**
	 * 
	 * @Title: checkClass 
	 * @Description: 用于把class中指定"属性:fieldString" -对象检索出来。如果是集合类型，则将集合中的泛型类型检索出来
	 * @param cls  传入的Class 类型
	 * @param fString  当前Class（对象）中包含的字段
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @return Class
	 */
	@SuppressWarnings("rawtypes")
	public  <T>  Class checkClass(Class cls,String fString) throws NoSuchFieldException, SecurityException{
		Class clazz = null;
		
		Field field = cls.getDeclaredField(fString);
		
		Type fc = field.getGenericType(); //  关键的地方，如果是List类型，得到其Generic的类型  
		  
        if(fc == null) return null;  
        
        if(fc instanceof ParameterizedType) // 如果是泛型参数的类型   
        {   
               ParameterizedType pt = (ParameterizedType) fc;  

               clazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。  
                
         }else{//不是泛型类型的参数，直接返回当前Type;
        	 clazz = field.getClass();
         }
		
		return clazz;
	}

	

	
}
