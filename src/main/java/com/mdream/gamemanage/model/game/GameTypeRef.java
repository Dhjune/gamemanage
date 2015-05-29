package com.mdream.gamemanage.model.game;

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

@Entity
@Table(name = "game_type_ref")
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class GameTypeRef implements Serializable{
	

	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = 7011400744633967413L;

	@EmbeddedId
	private GameTypeRefPK pk;
	
	@Column(name="status")
	private Integer status ;
	
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	
	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public GameTypeRefPK getPk() {
		return pk;
	}

	public void setPk(GameTypeRefPK pk) {
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
	
	
}
