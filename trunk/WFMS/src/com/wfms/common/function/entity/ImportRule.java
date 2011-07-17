package com.wfms.common.function.entity;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：DrgzEntity
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:58:44 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see ImportRule
 * @version 1.0
 *
 */
public class ImportRule  implements java.io.Serializable {


    // Fields    

     private int id;
     //字段名称
     private String zdmc;
     //字段类型
     private String zdlx;
     //外部数据类型(约束�?要转换代码的数据值类�?)
     private String wbzdlx;
     //�?属表名称
     private String ssbmc;
     //导入外部数据的�??
     private String wbsjz;
     //导入后内部数据�??
     private String nbsjz;
     //参�?�表
     private String ckb;
     //外部数据参�?�字�?
     private String wbsjckzd;
     //内部数据参�?�字�?
     private String nbsjckzd;
     //分类类型代码(父级类型代码�?0的代�?)
     private String fllxdm;
     //状�??(启用,禁用)
     private String zt;
    // Constructors

    /** default constructor */
    public ImportRule() {
    }

	/** minimal constructor */
    public ImportRule(String zdmc, String zdlx, String ssbmc, String wbsjz, String nbsjz) {
        this.zdmc = zdmc;
        this.zdlx = zdlx;
        this.ssbmc = ssbmc;
        this.wbsjz = wbsjz;
        this.nbsjz = nbsjz;
    }
    
    /** full constructor */
    public ImportRule(String zdmc, String zdlx, String ssbmc, String wbsjz, String nbsjz, String ckb, String wbsjckzd, String nbsjckzd, String fllxdm) {
        this.zdmc = zdmc;
        this.zdlx = zdlx;
        this.ssbmc = ssbmc;
        this.wbsjz = wbsjz;
        this.nbsjz = nbsjz;
        this.ckb = ckb;
        this.wbsjckzd = wbsjckzd;
        this.nbsjckzd = nbsjckzd;
        this.fllxdm = fllxdm;
    }

   
    // Property accessors

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getZdmc() {
        return this.zdmc;
    }
    
    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    public String getZdlx() {
        return this.zdlx;
    }
    
    public void setZdlx(String zdlx) {
        this.zdlx = zdlx;
    }

    public String getSsbmc() {
        return this.ssbmc;
    }
    
    public void setSsbmc(String ssbmc) {
        this.ssbmc = ssbmc;
    }

    public String getWbsjz() {
        return this.wbsjz;
    }
    
    public void setWbsjz(String wbsjz) {
        this.wbsjz = wbsjz;
    }

    public String getNbsjz() {
        return this.nbsjz;
    }
    
    public void setNbsjz(String nbsjz) {
        this.nbsjz = nbsjz;
    }

    public String getCkb() {
        return this.ckb;
    }
    
    public void setCkb(String ckb) {
        this.ckb = ckb;
    }

    public String getWbsjckzd() {
        return this.wbsjckzd;
    }
    
    public void setWbsjckzd(String wbsjckzd) {
        this.wbsjckzd = wbsjckzd;
    }

    public String getNbsjckzd() {
        return this.nbsjckzd;
    }
    
    public void setNbsjckzd(String nbsjckzd) {
        this.nbsjckzd = nbsjckzd;
    }

    public String getFllxdm() {
        return this.fllxdm;
    }
    
    public void setFllxdm(String fllxdm) {
        this.fllxdm = fllxdm;
    }
    
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getWbzdlx() {
		return wbzdlx;
	}

	public void setWbzdlx(String wbzdlx) {
		this.wbzdlx = wbzdlx;
	}
   








}