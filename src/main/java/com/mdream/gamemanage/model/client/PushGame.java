package com.mdream.gamemanage.model.client;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="push_game",uniqueConstraints={@UniqueConstraint(columnNames={"pg_type","game_id"})})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class PushGame {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name="pg_id")
	private Integer id;
	
	@Expose
	@Column(name="pg_type")
	private Integer type;  //1，推荐2，热门3。最新
	
	@Expose
	@Column(name="game_id")
	private Integer gameId;
	
	@Expose
	@Column(name="game_name")
	private String gameName;
	
//	@Expose
//	@Column(name="game_types")
//	private String gameTypes;
//	
//	@Expose
//	@Column(name="game_tags")
//	private String gameTags;
//	
	@Expose
	@Column(name="sort")
	private Integer sort;
	
	@Expose
	@Column(name="status")
	private Integer status;
	
	@Expose
	@Column(name="create_time")
	private Date  createTime;
	
	@Expose
	@Column(name="modify_time")
	private Date  modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

//	public String getGameTypes() {
//		return gameTypes;
//	}
//
//	public void setGameTypes(String gameTypes) {
//		this.gameTypes = gameTypes;
//	}
//
//	public String getGameTags() {
//		return gameTags;
//	}
//
//	public void setGameTags(String gameTags) {
//		this.gameTags = gameTags;
//	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
		
}
