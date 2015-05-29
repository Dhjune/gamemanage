package com.mdream.gamemanage.dao.admin;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.common.api.BaseDaoItl;
import com.mdream.gamemanage.model.admin.Admin;



@Repository
public class AdminDaoImp implements BaseDaoItl{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Admin find(String name, String password) {
		Admin admin = null;
		Session session =  sessionFactory.getCurrentSession();
		String hql = "from Admin admin where admin.name = :name and admin.password = :password";		
		Query query =session.createQuery(hql);
		query.setString("name", name);
		query.setString("password", password);
		List<Admin> list = query.list();
		if(list.size()>0){
			admin =  list.get(0);
		}
		return admin;		
	}
	
	
	

}
