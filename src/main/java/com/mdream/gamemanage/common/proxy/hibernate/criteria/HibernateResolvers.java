package com.mdream.gamemanage.common.proxy.hibernate.criteria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;




@Repository
public class HibernateResolvers {
		
	@Autowired
	private SessionFactory sessionFactory;
	
	
	private SimpleDateFormat formatTime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private SimpleDateFormat formatDate =  new SimpleDateFormat("yyyy-MM-dd");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T find(Class clazz,int id){
		
		Session session =  sessionFactory.getCurrentSession();		
		return (T) session.get(clazz, id);
		
	}
	
	public <T> void  save(T object) throws Exception{
		
		Session session =  sessionFactory.getCurrentSession();		
		session.save(object);
		
	}
	
	public <T> void update (T object) throws Exception{
		
		Session session =  sessionFactory.getCurrentSession();	
		session.update(object);
		
	}
	
	public <T> void merge(T obj) throws Exception{
		Session session   =  sessionFactory.getCurrentSession();
		session.merge(obj);
				
	}
	 
	public <T> void delete(T object) throws Exception{
		
		Session session =  sessionFactory.getCurrentSession();	
		session.delete(object);		
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> List<T> list(T t) {
		// TODO Auto-generated method stub
		Session session =  sessionFactory.getCurrentSession();	
		Criteria criteria =  session.createCriteria(t.getClass());
		criteria.add( Restrictions.eq("status", 1)); 

		return (List<T>) criteria.list();
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public <T> List<T> listObject(T object,Map map){
		
		Session session   =  sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(object.getClass());
		
		
		return null;
		
	}
	
   
    public <T> Criteria match(ExpressionGroup eg,Criteria criteria,@SuppressWarnings("rawtypes") Class target) throws NoSuchFieldException, SecurityException, ParseException{
    	
    	if(eg!=null){
			if(eg.getExpressions()!=null && eg.getExpressions().length>0 && eg.getOpgroup()!=null){
				Expression[] expressions = eg.getExpressions();
				List<Criterion> list =null;
	        	Criterion[] simples =null;
				switch (eg.getOpgroup()) {
				
				 	case GT:
				 						 				 		
			            break;
			            
			        case GTE:
			        	
			        	break;
			        case LT:
			        	
			            break;
			        case LTE:
			        	
			            break;
			            
			        case IS:
			        	
			            break;
			            
			        case OR:
			        	list = new ArrayList<Criterion>();
			        	
			        	for(Expression e : expressions){			        		
			        		paddingExpression(list,eg.getAlias(),e,target);
			        	}
			        	
			        	simples =   list.toArray( new Criterion[list.size()]);			        	
			        	criteria.add(Restrictions.disjunction(simples));	
			        	
			            break;
			            
			        case AND:
			        	list = new ArrayList<Criterion>();
			        	for(Expression e : expressions){			        		
			        		paddingExpression(list,eg.getAlias(),e,target);
			        	}
			        	simples =  list.toArray( new Criterion[list.size()]);
			        	criteria.add(Restrictions.conjunction(simples));	
			        	
			            break;
			        case NOT:
			        	list = new ArrayList<Criterion>();
			        	for(Expression e : expressions){			        		
			        		paddingExpression(list,eg.getAlias(),e,target);
			        	}
			        	for(int i=0;i<list.size();i++){
			        		criteria.add(Restrictions.not(list.get(i)));	
			        	}					        	
			            break;
			        	
			        case ORDER:	
			        	
			        	for(Expression e : expressions){			        		
			        		Order(criteria, e.operate,eg.getAlias(), e.name);	
			        	}
			        	
			            break;
			        case LIKE:	
			        	
			        
			        	
			            break;
			        case ILIKE:	
			        				        
			            break;
			            
			        default:
			          
			            break;				
				}
				
				
			}
		}
    	
    	return  null;
    }
    
    /**
     * 
     * @Title: paddingExpression 
     * @Description: TODO 将条件进行填充，集中注入到Criteria中 
     * @param list  需要填充的条件集合List<Criterion>
     * @param alias  条件拥有者，table--object对应的对象。应对的别名
     * @param e  Ex pressionGroup
     * @param clazz 条件拥有者，table--object对应的对象
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @return void
     * @throws ParseException 
     */
    @SuppressWarnings("rawtypes")
	public void paddingExpression(List<Criterion> list,String alias,Expression e,Class clazz) throws NoSuchFieldException, SecurityException, ParseException{
    	Criterion  simple = null;
    	if(e.operate.equals("GT")){
    		
    		simple = Restrictions.gt(alias+"."+e.name, getField(clazz, e.name, e.value));
    		
    	}else if(e.operate.equals("GTE")){
    		getField(clazz, e.name, e.value).getClass();
    		simple = Restrictions.ge(alias+"."+e.name, getField(clazz, e.name, e.value));
    		
    	}else if(e.operate.equals("LT")){
    		
    		simple = Restrictions.lt(alias+"."+e.name, getField(clazz, e.name, e.value));
    		
    	}else if(e.operate.equals("LTE")){
    		
    		simple = Restrictions.le(alias+"."+e.name, getField(clazz, e.name, e.value));
    		
    	}else if(e.operate.equals("IS")){
    		
    		simple = Restrictions.eq(alias+"."+e.name, getField(clazz, e.name, e.value));
    		
    	}else if(e.operate.equals("LIKE")){
    		
    		simple = Restrictions.like(alias+"."+e.name, getField(clazz, e.name, e.value).toString(),MatchMode.ANYWHERE);
    		
    	}else if(e.operate.equals("ILIKE")){
    		
    		simple = Restrictions.ilike(alias+"."+e.name, getField(clazz, e.name, e.value).toString(),MatchMode.ANYWHERE);
    		
    	}
    	
    	if(simple!=null){
    		list.add(simple);
    	}
    	
    }
    
    /**
     * 
     * @Title: addCriteria 
     * @Description: TODO
     * @param tempC  用于创建新的Criteria，所用到层级关系中Criteria-tempC
     * @param criteria  整个查询过程中最顶层-根节点Criteria,用添加条件
     * @param cr hibernate ORM中，主控对象，包含被控对象的属性名称,由前端JS传递过来
     * @param eg  封装查询操作 
     * @param clazz 被控对象的Class
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @return Criteria
     * @throws ParseException 
     */
    @SuppressWarnings("rawtypes")
	public <T> Criteria addCriteria(Criteria tempC,Criteria criteria,String cr,ExpressionGroup eg,Class clazz) throws NoSuchFieldException, SecurityException, ParseException{
    	
    	Criteria c = tempC.createCriteria(cr,cr,JoinType.INNER_JOIN);    	
    	match(eg,criteria,clazz);   	
    	return c;
    }
    
    public <T> Criteria   createCriteria (T target,String alias) throws NoSuchFieldException, SecurityException, ParseException{
    	
    	Session session   =  sessionFactory.getCurrentSession();   
    	Criteria criteria = null;
    	if(alias!=null && !alias.equals("")){
    		criteria = session.createCriteria(target.getClass(),alias);
    	}else{
    		criteria = session.createCriteria(target.getClass());
    		
    	}
		
    	return criteria;
    }
    
    public <T> Criteria   initCriteria (ExpressionGroup eg,T target) throws NoSuchFieldException, SecurityException, ParseException{
    	
    	Session session   =  sessionFactory.getCurrentSession();    	
		Criteria criteria = session.createCriteria(target.getClass(),eg.getAlias());
		match(eg,criteria,target.getClass());
    	return criteria;
    }
    
    public <T>  void paddingCriteria (Criteria criteria ,ExpressionGroup eg,@SuppressWarnings("rawtypes") Class clazz) throws NoSuchFieldException, SecurityException, ParseException{
    	match(eg,criteria,clazz);
    }
    
    
    
    
    public void Order(Criteria criteria,String type,String alias, String column){
    	if(type.equals("ASC")){
    		criteria.addOrder(Order.asc(alias+"."+column));
    	}else{
    		criteria.addOrder(Order.desc(alias+"."+column));
    	}
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> T getField( Class t,String name,String value) throws NoSuchFieldException, SecurityException, ParseException{
		
		Class type = t.getDeclaredField(name).getType();
	
		if(type.getName().equals("java.lang.String")) { 			
			return (T) value; 
		}
		
		else if(type.getName().equals("java.util.Date")) {
			Date date = null;
			//Date date = format.parse(value);
			if(value.length()<=11){
				date = formatDate.parse(value);
			}else if(value.length()<=19){
				date = formatTime.parse(value);
			}
			return (T) date; 
		}
		
		else if(type.equals("java.lang.Boolean")) { 
			Boolean bool=true; 
			
			if(value.equals("false")) { 
				bool=false; 
			} 
			return (T) bool; 
		} 
				
		else if(type.getName().equals("int")||type.getName().equals("java.lang.Integer")) {
			
			return (T) new Integer(value);
			
		} 
		
		else if(type.getName().equals("float")||type.getName().equals("java.lang.Float")) {
			
			return (T) new Float(value);
			
		} 

		else if(type.getName().equals("long")||type.getName().equals("class java.lang.Long")) { 
			
			return (T) new Long(value);
						
		} 
		
		else if(type.getName().equals("double")||type.getName().equals("class java.lang.Double")) { 
			
			return (T) new Long(value);
			
		} 
		
		return (T) value;
	}

	
}
