package com.mdream.gamemanage.model.game;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "game")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class Game implements Serializable{
	
	
	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = -1047816162830139479L;
	
	@Id
	@Expose
	@GeneratedValue
	@Column(name="id")
	private Integer id ;
	
	@Expose
	@Column(name="name",nullable=false)
	private String name ;
	
	@Expose
	@Column(name="description")
	private String description ;
		
	@Expose
	@Column(name="url")
	private String url ;
	
	@Expose
	@Column(name="icon")
	private String icon ;
	
	@Expose
	@Column(name="status")
	private Integer status ;
	
	@Expose
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	@Expose
	@Column(name="img")
	private String img ;
		
	@Expose
	@Column(name="godflag")
	private Integer godflag;
	
	@Expose
	@Column(name="hotflag")
	private Integer hotflag;
	
	@Expose
	@Column(name="nowflag")
	private Integer nowflag;
	
	@Expose
	@Column(name="recommend")
	private Integer recommend;
	
	@Expose
	@Column(name="star")
	private Integer star;
	
	@Expose
	@Column(name="praise")
	private Integer praise;
	
	@Expose
	@Column(name="belittle")
	private Integer belittle;

	@Expose
	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.REMOVE)
    @JoinTable(name="game_type_ref",
    joinColumns=@JoinColumn(name="gameId", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="gameTypeId", referencedColumnName="id"))
	@Where(clause="status = 1")
    private Set<GameType> gametypes = new  HashSet<GameType>();
	
	@OneToMany(mappedBy = "pk.game")
	@Where(clause="status = 1")
	private Set<GameTypeRef> typeRefs =  new  HashSet<GameTypeRef>();
	
	@OneToMany(mappedBy = "pk.game",fetch = FetchType.LAZY)
	@Where(clause="status = 1")
	private Set<GameCommentRef> commentRefs = new HashSet<GameCommentRef>();
	
	@OneToMany(mappedBy = "game")
	private Set<GamePic> pics =  new HashSet<GamePic>();
	
	public Set<GameType> getGametypes() {
		return gametypes;
	}

	public void setGametypes(Set<GameType> gametypes) {
		this.gametypes = gametypes;
	}

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

	public String getDescription() {
		
		return description;
		
	}

	public void setDescription(String description) {
		
		this.description = description;
		
	}

	

	public String getUrl() {
		
		return url;
		
	}

	public void setUrl(String url) {
		
		this.url = url;
		
	}

	public String getIcon() {
		
		return icon;
		
	}

	public void setIcon(String icon) {
		
		this.icon = icon;
		
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

	public String getImg() {
		
		return img;
		
	}

	public void setImg(String img) {
		
		this.img = img;
		
	}
	
	public Integer getGodflag() {
		return godflag;
	}

	public void setGodflag(Integer godflag) {
		this.godflag = godflag;
	}

	public Integer getHotflag() {
		return hotflag;
	}

	public void setHotflag(Integer hotflag) {
		this.hotflag = hotflag;
	}

	
	public Integer getNowflag() {
		return nowflag;
	}

	public void setNowflag(Integer nowflag) {
		this.nowflag = nowflag;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
	
	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public int getBelittle() {
		return belittle;
	}

	public void setBelittle(Integer belittle) {
		this.belittle = belittle;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Set<GamePic> getPics() {
		return pics;
	}

	public void setPics(Set<GamePic> pics) {
		this.pics = pics;
	}

	public Set<GameCommentRef> getCommentRefs() {
		return commentRefs;
	}

	public void setCommentRefs(Set<GameCommentRef> commentRefs) {
		this.commentRefs = commentRefs;
	}

	public Set<GameTypeRef> getTypeRefs() {
		return typeRefs;
	}

	public void setTypeRefs(Set<GameTypeRef> typeRefs) {
		this.typeRefs = typeRefs;
	}

	
	
	
	
}
