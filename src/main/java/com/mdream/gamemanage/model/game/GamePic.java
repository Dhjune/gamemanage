package com.mdream.gamemanage.model.game;

import java.io.Serializable;
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

@Entity
@Table(name = "game_pic")
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class GamePic implements Serializable{
	
	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = -7285395660461806056L;

	@Id
	@GeneratedValue
	private Integer id ;
	
	@ManyToOne
	@JoinColumn(name="gameId")
	private Game game ;
		
	@Column(name = "picUrl")
    private String picUrl;
	
	@Column(name = "picPath")
	private String picPath;
	
    @Column(name = "sort")
    private Integer sort;
	
	@Column(name="status")
	private Integer status ;
	
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime ;
	

	public Integer getId() {
		return id;
	}

	

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}



	public Date getModifyTime() {
		return modifyTime;
	}



	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}



	public Integer getSort() {
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	

}
