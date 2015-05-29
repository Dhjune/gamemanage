package com.mdream.gamemanage.model.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "slide_show")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class SlideShow {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Expose
	@Column(name="type")
	private Integer type;  //0  文字，1 图片
	
	@Expose
	@Column(name="name")
	private String name ;
	
	
	@Expose
	@Column(name="icon")
	private String icon; //如果是文字轮播字幕，加入一个icon
	
	@Expose
	@Column(name="status")
	private Integer status ;
	@Expose
	@Column(name="scene")
	private Integer scene;
	
	@Expose
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	@Expose
	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	
	@OneToMany(mappedBy = "pk.slideShow")
	private List<SlideRef> slideRefs = new ArrayList<SlideRef>();

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public List<SlideRef> getSlideRefs() {
		return slideRefs;
	}

	public void setSlideRefs(List<SlideRef> slideRefs) {
		this.slideRefs = slideRefs;
	}

	
	
	

}
