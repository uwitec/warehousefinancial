package com.wfms.common.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.wfms.common.orm.BaseEntity;

@Entity
@Table(name = "MEM_GENINF", uniqueConstraints = {})
public class User extends BaseEntity {

	private String loginid;
	private String loginpwd;
	private String phone;
	private String email;
	private String officephone;
	private String workplace;
	private String fax;
	private String brithday;
	private String brithplace;
	private String mobilephone;
	private String homephone;
	private String address;
	private String registeredaddress;
	private String gender;
	private String marriage;
	private String deniedlogin;
	private String lastlogintime;
	private String username;
	private String roleId;
	private String identityid;
	private String memo;

	// Constructors

	@Column(name = "LOGINID", nullable = false, length = 16)
	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	@Column(name = "LOGINPWD", nullable = false, length = 16)
	public String getLoginpwd() {
		return this.loginpwd;
	}

	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}

	@Column(name = "PHONE", length = 16)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "EMAIL", length = 32)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "OFFICEPHONE", length = 16)
	public String getOfficephone() {
		return this.officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	@Column(name = "WORKPLACE", length = 64)
	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	@Column(name = "FAX", length = 1)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "BRITHDAY", length = 32)
	public String getBrithday() {
		return this.brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	@Column(name = "BRITHPLACE", length = 64)
	public String getBrithplace() {
		return this.brithplace;
	}

	public void setBrithplace(String brithplace) {
		this.brithplace = brithplace;
	}

	@Column(name = "MOBILEPHONE", length = 16)
	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	@Column(name = "HOMEPHONE", length = 16)
	public String getHomephone() {
		return this.homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	@Column(name = "ADDRESS", length = 64)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "REGISTEREDADDRESS", length = 64)
	public String getRegisteredaddress() {
		return this.registeredaddress;
	}

	public void setRegisteredaddress(String registeredaddress) {
		this.registeredaddress = registeredaddress;
	}

	@Column(name = "GENDER", nullable = false, length = 1)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "MARRIAGE", length = 1)
	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	@Column(name = "DENIEDLOGIN", length = 1)
	public String getDeniedlogin() {
		return this.deniedlogin;
	}

	public void setDeniedlogin(String deniedlogin) {
		this.deniedlogin = deniedlogin;
	}

	@Column(name = "LASTLOGINTIME", length = 32)
	public String getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	@Column(name = "USERNAME", nullable = false, length = 16)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "ROLE_ID", nullable = false, length = 32)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "IDENTITYID", length = 18)
	public String getIdentityid() {
		return this.identityid;
	}

	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}

	@Column(name = "MEMO", length = 128)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


}