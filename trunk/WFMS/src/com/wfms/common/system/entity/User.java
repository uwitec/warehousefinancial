package com.wfms.common.system.entity;

// default package

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonMethod;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.common.orm.BaseEntity;

/**
 * MemGenInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEM_GENINF", uniqueConstraints = {})
@JsonAutoDetect(value = JsonMethod.GETTER, isGetterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(value = { "ry", "chargeDepart", "chargeDepart",
		"memberRights", "memberParts", "roleMembers", "objects" })
public class User extends BaseEntity {

	// Fields

	private String memid;
	private String loginid;
	private String username;
	private String password;
	private String phone;
	private String email;
	private String customerId;
	private String synopsis;
	private String signature;
	private String siblingorder;
	private String englishname;
	private String identityid;
	private String birthday;
	private String jobtitle;
	private String joblevel;
	private String jobrank;
	private String onboarddate;
	private String jobonboarddate;
	private String bloodtype;
	private String officephone;
	private String fax;
	private String workplace;
	private String areacode;
	private String resigndate;
	private String nationality;
	private String militaryservice;
	private String birthplace;
	private String association;
	private String religion;
	private String academicdegree;
	private String degreeschool;
	private String degreedepartment;
	private String experiencedcompany;
	private String experiencedjobtitle;
	private String experiencedjobperiod;
	private String pager;
	private String mobilephone;
	private String homephone;
	private String registeredphone;
	private String address;
	private String registeredaddress;
	private String ecperson;
	private String ecphone;
	private String ecrelation;
	private String gender;
	private String marriage;
	private String deniedlogin;
	private String resign;
	private String invisible;
	private String attmap;

	private int dlcs;
	private String scdlsj;
	private String dlsj;
	private List<UserModule> memberRights = new ArrayList<UserModule>(0);
	private RoleGenInfo role;
	private List<RoleGenInfo> roles = new ArrayList<RoleGenInfo>(0);
	private List<MemberPart> memberParts = new ArrayList<MemberPart>(0);

	/** default constructor */
	public User() {
	}

	public User(String memid) {
		this.memid = memid;
	}

	/** minimal constructor */
	public User(String memid, String loginid) {
		this.memid = memid;
		this.loginid = loginid;
	}

	@JsonIgnore
	@Transient
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMID")
	public List<MemberPart> getMemberParts() {
		return memberParts;
	}

	public void setMemberParts(List<MemberPart> memberParts) {
		this.memberParts = memberParts;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "pk")
	@GenericGenerator(name = "pk", strategy = "xuner.web.orm.IdGenerator")
	@Column(name = "MEMID", unique = true, nullable = false, insertable = true, updatable = true, length = 30)
	public String getMemid() {
		return this.memid;
	}

	public void setMemid(String memid) {
		this.memid = memid;
	}

	@Column(name = "LOGINID", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	@Column(name = "USERNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 250)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "PHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "EMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ID", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "SYNOPSIS", unique = false, nullable = true, insertable = true, updatable = true, length = 4000)
	public String getSynopsis() {
		return this.synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	@Column(name = "SIGNATURE", unique = false, nullable = true, insertable = true, updatable = true, length = 4000)
	@Basic(fetch = FetchType.LAZY)
	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Column(name = "SIBLINGORDER", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getSiblingorder() {
		return this.siblingorder;
	}

	public void setSiblingorder(String siblingorder) {
		this.siblingorder = siblingorder;
	}

	@Column(name = "ENGLISHNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 250)
	public String getEnglishname() {
		return this.englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	@Column(name = "IDENTITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getIdentityid() {
		return this.identityid;
	}

	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}

	@Column(name = "BIRTHDAY", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "JOBTITLE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getJobtitle() {
		return this.jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	@Column(name = "JOBLEVEL", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getJoblevel() {
		return this.joblevel;
	}

	public void setJoblevel(String joblevel) {
		this.joblevel = joblevel;
	}

	@Column(name = "JOBRANK", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getJobrank() {
		return this.jobrank;
	}

	public void setJobrank(String jobrank) {
		this.jobrank = jobrank;
	}

	@Column(name = "ONBOARDDATE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getOnboarddate() {
		return this.onboarddate;
	}

	public void setOnboarddate(String onboarddate) {
		this.onboarddate = onboarddate;
	}

	@Column(name = "JOBONBOARDDATE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getJobonboarddate() {
		return this.jobonboarddate;
	}

	public void setJobonboarddate(String jobonboarddate) {
		this.jobonboarddate = jobonboarddate;
	}

	@Column(name = "BLOODTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getBloodtype() {
		return this.bloodtype;
	}

	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	@Column(name = "OFFICEPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getOfficephone() {
		return this.officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	@Column(name = "FAX", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "WORKPLACE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	@Column(name = "AREACODE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	@Column(name = "RESIGNDATE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getResigndate() {
		return this.resigndate;
	}

	public void setResigndate(String resigndate) {
		this.resigndate = resigndate;
	}

	@Column(name = "NATIONALITY", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "MILITARYSERVICE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getMilitaryservice() {
		return this.militaryservice;
	}

	public void setMilitaryservice(String militaryservice) {
		this.militaryservice = militaryservice;
	}

	@Column(name = "BIRTHPLACE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getBirthplace() {
		return this.birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	@Column(name = "ASSOCIATION", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getAssociation() {
		return this.association;
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	@Column(name = "RELIGION", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getReligion() {
		return this.religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	@Column(name = "ACADEMICDEGREE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getAcademicdegree() {
		return this.academicdegree;
	}

	public void setAcademicdegree(String academicdegree) {
		this.academicdegree = academicdegree;
	}

	@Column(name = "DEGREESCHOOL", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDegreeschool() {
		return this.degreeschool;
	}

	public void setDegreeschool(String degreeschool) {
		this.degreeschool = degreeschool;
	}

	@Column(name = "DEGREEDEPARTMENT", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDegreedepartment() {
		return this.degreedepartment;
	}

	public void setDegreedepartment(String degreedepartment) {
		this.degreedepartment = degreedepartment;
	}

	@Column(name = "EXPERIENCEDCOMPANY", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getExperiencedcompany() {
		return this.experiencedcompany;
	}

	public void setExperiencedcompany(String experiencedcompany) {
		this.experiencedcompany = experiencedcompany;
	}

	@Column(name = "EXPERIENCEDJOBTITLE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getExperiencedjobtitle() {
		return this.experiencedjobtitle;
	}

	public void setExperiencedjobtitle(String experiencedjobtitle) {
		this.experiencedjobtitle = experiencedjobtitle;
	}

	@Column(name = "EXPERIENCEDJOBPERIOD", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getExperiencedjobperiod() {
		return this.experiencedjobperiod;
	}

	public void setExperiencedjobperiod(String experiencedjobperiod) {
		this.experiencedjobperiod = experiencedjobperiod;
	}

	@Column(name = "PAGER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getPager() {
		return this.pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	@Column(name = "MOBILEPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	@Column(name = "HOMEPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getHomephone() {
		return this.homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	@Column(name = "REGISTEREDPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRegisteredphone() {
		return this.registeredphone;
	}

	public void setRegisteredphone(String registeredphone) {
		this.registeredphone = registeredphone;
	}

	@Column(name = "ADDRESS", unique = false, nullable = true, insertable = true, updatable = true, length = 400)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "REGISTEREDADDRESS", unique = false, nullable = true, insertable = true, updatable = true, length = 400)
	public String getRegisteredaddress() {
		return this.registeredaddress;
	}

	public void setRegisteredaddress(String registeredaddress) {
		this.registeredaddress = registeredaddress;
	}

	@Column(name = "ECPERSON", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getEcperson() {
		return this.ecperson;
	}

	public void setEcperson(String ecperson) {
		this.ecperson = ecperson;
	}

	@Column(name = "ECPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getEcphone() {
		return this.ecphone;
	}

	public void setEcphone(String ecphone) {
		this.ecphone = ecphone;
	}

	@Column(name = "ECRELATION", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getEcrelation() {
		return this.ecrelation;
	}

	public void setEcrelation(String ecrelation) {
		this.ecrelation = ecrelation;
	}

	@Column(name = "GENDER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "MARRIAGE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	@Column(name = "DENIEDLOGIN", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public String getDeniedlogin() {
		return this.deniedlogin;
	}

	public void setDeniedlogin(String deniedlogin) {
		this.deniedlogin = deniedlogin;
	}

	@Column(name = "RESIGN", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public String getResign() {
		return this.resign;
	}

	public void setResign(String resign) {
		this.resign = resign;
	}

	@Column(name = "INVISIBLE", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public String getInvisible() {
		return this.invisible;
	}

	public void setInvisible(String invisible) {
		this.invisible = invisible;
	}

	@Column(name = "ATTMAP", unique = false, nullable = true, insertable = true, updatable = true, length = 4000)
	@Basic(fetch = FetchType.LAZY)
	public String getAttmap() {
		return this.attmap;
	}

	public void setAttmap(String attmap) {
		this.attmap = attmap;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "MEMBER_RIGHT", joinColumns = { @JoinColumn(name = "MEMID", referencedColumnName = "MEMID") }, inverseJoinColumns = { @JoinColumn(name = "RIGHTID", referencedColumnName = "RIGHTID") })
	@JoinColumn(name = "MEMID")
	public List<UserModule> getMemberRights() {
		return this.memberRights;
	}

	public void setMemberRights(List<UserModule> memberRights) {
		this.memberRights = memberRights;
	}

	@Column(name = "DLCS", unique = false, nullable = true, insertable = true, updatable = true, scale = 10, precision = 0)
	public int getDlcs() {
		return dlcs;
	}

	public void setDlcs(int dlcs) {
		this.dlcs = dlcs;
	}

	@Column(name = "SCDLSJ", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getScdlsj() {
		return scdlsj;
	}

	public void setScdlsj(String scdlsj) {
		this.scdlsj = scdlsj;
	}

	@Column(name = "DLSJ", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getDlsj() {
		return dlsj;
	}

	public void setDlsj(String dlsj) {
		this.dlsj = dlsj;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MAINROLEID")
	@NotFound(action = NotFoundAction.IGNORE)
	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "ROL_MEM", joinColumns = { @JoinColumn(name = "MEMID") }, inverseJoinColumns = { @JoinColumn(name = "ROLID") })
	public List<RoleGenInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleGenInfo> roles) {
		this.roles = roles;
	}

}