package com.mdream.gamemanage.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "app_base")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class Application {

	@Id
    @Column(name = "id")   
    @GeneratedValue()
	private Integer id  ;
	
	@Column(name="appName")
	private String appName ;
	
	@Column(name="appKey")
	private String appKey;
	
	@Column(name="appLogo")
	private String appLogo;
	
	
	@Column(name="appVersion")
	private String appVersion;
	
	@Column(name="appFileSize")
	private Integer appFileSize;
	
	@Column(name="versionType")
	private Integer versionType;
	
	@Column(name="pubTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pubTime; //发布时间
	
	
	@Column(name="pubPlat")
	private String pubPlat;  //发布平台
	
	@Column(name="pubId")
	private String  pubId ;
	
	@Column(name="appUrl")
	private String appUrl ;
	
	@Column(name="updateType")
	private Integer updateType;
	
	@Column(name="createTime",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime ;
	@Column(name="status")
	private Integer status;
	
	

	@Column(name="modifyTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public Integer getAppFileSize() {
		return appFileSize;
	}

	public void setAppFileSize(Integer appFileSize) {
		this.appFileSize = appFileSize;
	}

	public Integer getVersionType() {
		return versionType;
	}

	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public String getPubPlat() {
		return pubPlat;
	}

	public void setPubPlat(String pubPlat) {
		this.pubPlat = pubPlat;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Integer getUpdateType() {
		return updateType;
	}

	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}
	public String getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
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

	
}
