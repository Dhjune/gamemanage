package com.mdream.gamemanage.common.proxy.hibernate.mapper;

public class StatementTemplate {
	
	private TYPE type;
	
	private String statement;	
	
	public enum TYPE{
		HQL,SQL;
	}

	
	public StatementTemplate(TYPE sql, String value) {
		this.statement = value;
		this.type =  sql;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
	
}
