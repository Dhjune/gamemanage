package com.mdream.gamemanage.model.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "user")
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
public class User {
	
	@Id
	@GeneratedValue
	private int id ;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="nickName")
	private String nickName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="sex")
	private int sex;
	
	@Column(name="birthdate")
	private String birthdate;
	
	@Column(name="email")
	private String email;
	
	@Column(name="headImage")
	private String headImage;
	
	@Column(name="realName")
	private String realName;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="qq")
	private String qq;
	
	@Column(name="intro")
	private String intro;
	
	@Column(name="regSourse")
	private String regSourse;
	
	@Column(name="sourseId")
	private String sourseId;
	
	@Column(name="isVip")
	private Integer isVip;
	
	@Column(name="authFlag")  //0未认证 1认证中 2已认证 3认证被拒 
	private Integer authFlag;
	
	@Column(name="authTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date authTime;
	
	@Column(name="userType")
	private Integer userType;  //0普通用户 1资深用户  2合作用户
	
	@Column(name="userLevel")
	private Integer userLevel;
	
	@Column(name="status")
	private Integer status;  //1正常2注销3禁用4异常 
	
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name="updateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getRegSourse() {
		return regSourse;
	}

	public void setRegSourse(String regSourse) {
		this.regSourse = regSourse;
	}

	public String getSourseId() {
		return sourseId;
	}

	public void setSourseId(String sourseId) {
		this.sourseId = sourseId;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public Integer getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(Integer authFlag) {
		this.authFlag = authFlag;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
