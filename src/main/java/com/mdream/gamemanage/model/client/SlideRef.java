package com.mdream.gamemanage.model.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "slide_show_ref")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class SlideRef implements Serializable{
	
	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = -7743829035698497292L;

	@EmbeddedId
	@Expose
	private SlideRefPK pk;
	
	@Column(name="status")
	@Expose
	private Integer status ;
	
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date  modifyTime ;
	
	@Column(name="sort")
	@Expose
	private Integer sort;

	public SlideRefPK getPk() {
		return pk;
	}

	public void setPk(SlideRefPK pk) {
		this.pk = pk;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	
}
