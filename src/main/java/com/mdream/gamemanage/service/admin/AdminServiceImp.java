package com.mdream.gamemanage.service.admin;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mdream.gamemanage.common.api.BaseServiceItl;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.dao.admin.AdminDaoImp;
import com.mdream.gamemanage.model.admin.Admin;


@Service
@Transactional
public class AdminServiceImp implements BaseServiceItl<Admin>{
	
	@Autowired
	private AdminDaoImp adminDaoImp;
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	
	
	public Admin get(){
		
		Admin admin = null;
		
		return admin;
		
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Admin login(Admin admin){
			
		Admin newAdmin = adminDaoImp.find(admin.getName(),admin.getPassword());
		
		return newAdmin;
		
	}
	public void saveOrUpdate(Admin t) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
