package com.mdream.gamemanage.model.game;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "game_type")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class GameType {
	
	@Expose
	@Id
	@GeneratedValue
	private Integer id ;
	
	@Expose
	@Column(name="name")
	private String name ;
	
	@Expose
	@Column(name="status")
	private Integer status ;
	
	@Expose
	@Column(name="sort")
	private Integer sort;
	
	@Expose
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	@Expose
	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	
	@ManyToMany(mappedBy="gametypes",fetch = FetchType.LAZY)
	//序列化忽略
    private transient Set<Game> games =  new HashSet<Game>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="type_tag_ref",
    joinColumns=@JoinColumn(name="gameTypeId", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="tagId", referencedColumnName="id"))
	private  Set<Tag> tags =  new HashSet<Tag>();

	public Integer getId() {
		return id;
	}

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

}
