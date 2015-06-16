package com.mdream.gamemanage.common.proxy.hibernate.criteria;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

@Component
public class DefaultHibernateDecorator {
	
	public Criteria addDesc(Criteria criteria,String desc){
		
		return criteria.addOrder(Order.desc(desc));
		
	}
	
	public Criteria addAsc(Criteria criteria,String asc){
		
		return criteria.addOrder(Order.asc(asc));
		
	}
	
}
