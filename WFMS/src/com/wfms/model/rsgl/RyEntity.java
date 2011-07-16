package com.wfms.model.rsgl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonMethod;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.model.system.MemberGenInfo;



/**
 * Ryb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RYB"
    ,schema="ECM"
, uniqueConstraints = {  }
)
@JsonAutoDetect(value=JsonMethod.GETTER,getterVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(value={"bzrs","lss"})
public class RyEntity  implements java.io.Serializable {


    // Fields    

     private int id;
     private String rybh;
     private String ryxm;
     private String csrq;
     private String gjdm;
     private String csd;
     private String mzdm;
     private String sfzh;
     private String zp;
     private String xx;
     private String jkqkdm;
     private String hyqk;
     private String jtcsdm;
     private String rzsj;
     private String zzsj;
     private String lzsj;
     private String bz;
     private String zt;
     private String bgsdh;
     private String sjhm;
     private String xb;
     private String zwdjdm;
     private String rylbdm;
     private String zwdjpzsj;
     private String zyjszgdjdm;
     private String zgdjhdsj;
     private String gqryjsdjdm;
     private String jsdjhdsj;
     private String zzmm;
     private String gw;
     private String ryhrdsj;
     private String byyx;
     private String zyyxz;
     private String jg;
     private String whcddm;
     private String xwdm;
     private String cjgzsj;
    /* private Set<BzrEntity> bzrs = new HashSet<BzrEntity>(0);
     private Set<LsEntity> lss = new HashSet<LsEntity>(0);*/
     private MemberGenInfo member = new MemberGenInfo();

    // Constructors
    @OneToOne(fetch=FetchType.EAGER)  
    @JoinColumn(name="MEMID")
    @NotFound(action=NotFoundAction.IGNORE)
    public MemberGenInfo getMember() {
		return member;
	}

	public void setMember(MemberGenInfo member) {
		this.member = member;
	}

	/** default constructor */
    public RyEntity() {
    }

    public RyEntity(int id)
    {
    	this.id =id;
    }
	/** minimal constructor */
    public RyEntity(int id, String rybh, String ryxm, String rzsj, String zt) {
        this.id = id;
        this.rybh = rybh;
        this.ryxm = ryxm;
        this.rzsj = rzsj;
        this.zt = zt;
    }
   
    // Property accessors
    @Id
    @Column(name="ID", unique=true, nullable=false, insertable=true, updatable=true, precision=10, scale=0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="RYBH", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getRybh() {
        return this.rybh;
    }
    
    public void setRybh(String rybh) {
        this.rybh = rybh;
    }
    
    @Column(name="RYXM", unique=false, nullable=false, insertable=true, updatable=true, length=64)

    public String getRyxm() {
        return this.ryxm;
    }
    
    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }
    
    @Column(name="CSRQ", unique=false, nullable=true, insertable=true, updatable=true, length=20)
    public String getCsrq() {
        return this.csrq;
    }
    
    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }
    
    @Column(name="GJDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getGjdm() {
        return this.gjdm;
    }
    
    public void setGjdm(String gjdm) {
        this.gjdm = gjdm;
    }
    
    @Column(name="CSD", unique=false, nullable=true, insertable=true, updatable=true, length=64)

    public String getCsd() {
        return this.csd;
    }
    
    public void setCsd(String csd) {
        this.csd = csd;
    }
    
    @Column(name="MZDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getMzdm() {
        return this.mzdm;
    }
    
    public void setMzdm(String mzdm) {
        this.mzdm = mzdm;
    }
    
    @Column(name="SFZH", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getSfzh() {
        return this.sfzh;
    }
    
    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }
    
    @Column(name="ZP", unique=false, nullable=true, insertable=true, updatable=true, length=128)

    public String getZp() {
        return this.zp;
    }
    
    public void setZp(String zp) {
        this.zp = zp;
    }
    
    @Column(name="XX", unique=false, nullable=true, insertable=true, updatable=true, length=10)

    public String getXx() {
        return this.xx;
    }
    
    public void setXx(String xx) {
        this.xx = xx;
    }
    
    @Column(name="JKQKDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getJkqkdm() {
        return this.jkqkdm;
    }
    
    public void setJkqkdm(String jkqkdm) {
        this.jkqkdm = jkqkdm;
    }
    
    @Column(name="HYQK", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getHyqk() {
        return this.hyqk;
    }
    
    public void setHyqk(String hyqk) {
        this.hyqk = hyqk;
    }
    
    @Column(name="JTCSDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getJtcsdm() {
        return this.jtcsdm;
    }
    
    public void setJtcsdm(String jtcsdm) {
        this.jtcsdm = jtcsdm;
    }
    
    @Column(name="RZSJ", unique=false, nullable=false, insertable=true, updatable=true, length=20)

    public String getRzsj() {
        return this.rzsj;
    }
    
    public void setRzsj(String rzsj) {
        this.rzsj = rzsj;
    }
    
    @Column(name="ZZSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getZzsj() {
        return this.zzsj;
    }
    
    public void setZzsj(String zzsj) {
        this.zzsj = zzsj;
    }
    
    @Column(name="LZSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getLzsj() {
        return this.lzsj;
    }
    
    public void setLzsj(String lzsj) {
        this.lzsj = lzsj;
    }
    
    @Column(name="BZ", unique=false, nullable=true, insertable=true, updatable=true, length=512)

    public String getBz() {
        return this.bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
    
    @Column(name="ZT", unique=false, nullable=false, insertable=true, updatable=true, length=1)

    public String getZt() {
        return this.zt;
    }
    
    public void setZt(String zt) {
        this.zt = zt;
    }
    
    @Column(name="BGSDH", unique=false, nullable=true, insertable=true, updatable=true, length=24)

    public String getBgsdh() {
        return this.bgsdh;
    }
    
    public void setBgsdh(String bgsdh) {
        this.bgsdh = bgsdh;
    }
    
    @Column(name="SJHM", unique=false, nullable=true, insertable=true, updatable=true, length=24)

    public String getSjhm() {
        return this.sjhm;
    }
    
    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }
    
    @Column(name="XB", unique=false, nullable=true, insertable=true, updatable=true, length=2)

    public String getXb() {
        return this.xb;
    }
    
    public void setXb(String xb) {
        this.xb = xb;
    }
    
    @Column(name="ZWDJDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getZwdjdm() {
        return this.zwdjdm;
    }
    
    public void setZwdjdm(String zwdjdm) {
        this.zwdjdm = zwdjdm;
    }
    
    @Column(name="RYLBDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getRylbdm() {
        return this.rylbdm;
    }
    
    public void setRylbdm(String rylbdm) {
        this.rylbdm = rylbdm;
    }
    
    @Column(name="ZWDJPZSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getZwdjpzsj() {
        return this.zwdjpzsj;
    }
    
    public void setZwdjpzsj(String zwdjpzsj) {
        this.zwdjpzsj = zwdjpzsj;
    }
    
    @Column(name="ZYJSZGDJDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getZyjszgdjdm() {
        return this.zyjszgdjdm;
    }
    
    public void setZyjszgdjdm(String zyjszgdjdm) {
        this.zyjszgdjdm = zyjszgdjdm;
    }
    
    @Column(name="ZGDJHDSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getZgdjhdsj() {
        return this.zgdjhdsj;
    }
    
    public void setZgdjhdsj(String zgdjhdsj) {
        this.zgdjhdsj = zgdjhdsj;
    }
    
    @Column(name="GQRYJSDJDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getGqryjsdjdm() {
        return this.gqryjsdjdm;
    }
    
    public void setGqryjsdjdm(String gqryjsdjdm) {
        this.gqryjsdjdm = gqryjsdjdm;
    }
    
    @Column(name="JSDJHDSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getJsdjhdsj() {
        return this.jsdjhdsj;
    }
    
    public void setJsdjhdsj(String jsdjhdsj) {
        this.jsdjhdsj = jsdjhdsj;
    }
    
    @Column(name="ZZMM", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getZzmm() {
        return this.zzmm;
    }
    
    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }
    
    @Column(name="GW", unique=false, nullable=true, insertable=true, updatable=true, length=30)

    public String getGw() {
        return this.gw;
    }
    
    public void setGw(String gw) {
        this.gw = gw;
    }
    
    @Column(name="RYHRDSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getRyhrdsj() {
        return this.ryhrdsj;
    }
    
    public void setRyhrdsj(String ryhrdsj) {
        this.ryhrdsj = ryhrdsj;
    }
    
    @Column(name="BYYX", unique=false, nullable=true, insertable=true, updatable=true, length=32)

    public String getByyx() {
        return this.byyx;
    }
    
    public void setByyx(String byyx) {
        this.byyx = byyx;
    }
    
    @Column(name="ZYYXZ", unique=false, nullable=true, insertable=true, updatable=true, length=64)

    public String getZyyxz() {
        return this.zyyxz;
    }
    
    public void setZyyxz(String zyyxz) {
        this.zyyxz = zyyxz;
    }
    
    @Column(name="JG", unique=false, nullable=true, insertable=true, updatable=true, length=32)

    public String getJg() {
        return this.jg;
    }
    
    public void setJg(String jg) {
        this.jg = jg;
    }
    
    @Column(name="WHCDDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getWhcddm() {
        return this.whcddm;
    }
    
    public void setWhcddm(String whcddm) {
        this.whcddm = whcddm;
    }
    
    @Column(name="XWDM", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getXwdm() {
        return this.xwdm;
    }
    
    public void setXwdm(String xwdm) {
        this.xwdm = xwdm;
    }
    
    @Column(name="CJGZSJ", unique=false, nullable=true, insertable=true, updatable=true, length=20)

    public String getCjgzsj() {
        return this.cjgzsj;
    }
    
    public void setCjgzsj(String cjgzsj) {
        this.cjgzsj = cjgzsj;
    }
}