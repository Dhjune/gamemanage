package com.mdream.gamemanage.model.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = "user_comment")
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class UserComment {
	
	@Id
	@GeneratedValue
	@Expose
	private int id ;
	
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@Expose
	@Column(name="nickName")
	private String nickName;
	
	@Expose
	@Column(name="type")
	private int type;
	
	@Expose
	@Column(name="refId")
	private int refId;
	
	@Expose
	@Column(name="content")
	private String content;
	
	@Expose
	@Column(name="status")
	private int status ;
	
	@Expose
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	//private int replyId;
	
	@ManyToOne
	@JoinColumn(name="replayId")
	private UserComment parent ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserComment getParent() {
		return parent;
	}

	public void setParent(UserComment parent) {
		this.parent = parent;
	}
		
	
}
