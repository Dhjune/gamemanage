package com.mdream.gamemanage.common.proxy.hibernate.criteria;



public class Expression {
	
	public  String name;
		
	public String value;
	
	//本来是使用Operate的，但是不知道为什么嵌套2层后，第二层operate就会出现无法赋值的 情况.google gson情况
	//Jackson不会出现这样的情况，但是嵌套集合上有问题。。。。。
	//不得已改为String
	public String operate;
	

}
