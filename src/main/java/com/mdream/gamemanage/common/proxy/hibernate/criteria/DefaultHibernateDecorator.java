package com.mdream.gamemanage.common.proxy.hibernate.criteria;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

@Component
public class DefaultHibernateDecorator {
	
	public void addDesc(Criteria criteria,String desc){
		
		criteria.addOrder(Order.desc(desc));
		
	}
	
	public void addAsc(Criteria criteria,String asc){
		
		criteria.addOrder(Order.asc(asc));
		
	}
	
}
