package com.mdream.gamemanage.model.client;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "slide")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class Slide {
	
	@Id
	@Expose
	@GeneratedValue
	private int id;
	
	@Expose
	@Column(name="type")
	private int type;  //0  文字，1 图片
	//文字
	
	@Expose
	@Column(name="content")
	private String content;
	
	//图片
	
	@Expose
	@Column(name="imgUrl")
	private String imgUrl ;
	
	@Expose
	@Column(name="RefId")
	private Integer RefId;
	
	@Expose
	@Column(name="refUrl")
	private String refUrl;
	
	
	//公共 
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
	private Date modifyTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getRefId() {
		return RefId;
	}

	public void setRefId(Integer refId) {
		RefId = refId;
	}

	public String getRefUrl() {
		return refUrl;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
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
	
	
	
	
}
