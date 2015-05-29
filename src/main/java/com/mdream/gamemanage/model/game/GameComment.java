package com.mdream.gamemanage.model.game;

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
@Table(name = "game_comment")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class GameComment {
	@Id
	@Expose
	@GeneratedValue
	private Integer id ;
	
	@Expose
	@Column(name="content")
	private String content;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
