package com.mdream.gamemanage.common.proxy.hibernate.criteria;

public class ExpressionGroup {
	//实际操作数组
	private  Expression[] expressions;
	
	//操作集合  
	private Operate opgroup;
	
	// 操作组序列号
	private Integer group;
	
	//属性包含者别名 --与POJO对象中属性名应对
	private String upalias;
	
	//属性别名--与POJO对象中属性名应对
	private  String  alias;

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Operate getOpgroup() {
		return opgroup;
	}

	public void setOpgroup(Operate opgroup) {
		this.opgroup = opgroup;
	}

	public Expression[] getExpressions() {
		return expressions;
	}

	public void setExpressions(Expression[] expressions) {
		this.expressions = expressions;
	}

	

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUpalias() {
		return upalias;
	}

	public void setUpalias(String upalias) {
		this.upalias = upalias;
	}
	  
 	
}
