package com.mdream.gamemanage.model.game;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.google.gson.annotations.Expose;
//@Entity
//@Table(name = "default_comment")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class DefaultComment {
	
	@Id
	@Expose
	@GeneratedValue
	@Column(name="comment_id")
	private Integer  id;
	
	@ManyToOne
	@JoinColumn(name="game_id")
	private Game game ;
	
	@Expose
	@Column(name="content")
	private String content;
	
	@Expose
	@Column(name="count")
	private Integer count;
	
	@Expose
	@Column(name="status")
	private Integer status;
	
	@Expose
	@Column(name="sort")
	private Integer sort;
	
	@Expose
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date  createTime;
	
	@Expose
	@Column(name="modify_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date  modifyTime;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
