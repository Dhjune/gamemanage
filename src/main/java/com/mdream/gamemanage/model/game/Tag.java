package com.mdream.gamemanage.model.game;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.google.gson.annotations.Expose;
@Entity
@Table(name = "tag")
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Tag {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name="id")
	private Integer id ;
	
	@Expose
	@Column(name="name")
	private String name;
	
	
	@Expose
	@Column(name="status")
	private Integer status ;
	
	
	@Expose
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	@Expose
	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date  modifyTime ;
	
	@ManyToMany(mappedBy="tags",fetch = FetchType.LAZY)
	private transient Set<GameType> types = new HashSet<GameType>();
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Set<GameType> getTypes() {
		return types;
	}

	public void setTypes(Set<GameType> types) {
		this.types = types;
	}
	
	
	
}
