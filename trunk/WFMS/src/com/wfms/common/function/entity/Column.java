package com.wfms.common.function.entity;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ColumnEntity
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:58:11 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see Column
 * @version 1.0
 *
 */
public class Column  implements java.io.Serializable {


	// Fields    
	private Integer id;
    private String tablename;
    private String columnName;
    private String dataType;
    private Integer dataLength;
    private Integer dataPrecision;
    private Integer dataScale;
    private String nullable;
    private String dataDefault;
    private String comments;
    //匹配外部数据字段
    private String matchcontent;

   // Constructors

   /** default constructor */
   public Column() {
   }

   public String getTablename() {
       return this.tablename;
   }
   
   public void setTablename(String tablename) {
       this.tablename = tablename;
   }

   public String getColumnName() {
       return this.columnName;
   }
   
   public void setColumnName(String columnName) {
       this.columnName = columnName;
   }

   public String getDataType() {
       return this.dataType;
   }
   
   public void setDataType(String dataType) {
       this.dataType = dataType;
   }

   public Integer getDataLength() {
       return this.dataLength;
   }
   
   public void setDataLength(Integer dataLength) {
       this.dataLength = dataLength;
   }

   public Integer getDataPrecision() {
       return this.dataPrecision;
   }
   
   public void setDataPrecision(Integer dataPrecision) {
       this.dataPrecision = dataPrecision;
   }

   public Integer getDataScale() {
       return this.dataScale;
   }
   
   public void setDataScale(Integer dataScale) {
       this.dataScale = dataScale;
   }

   public String getNullable() {
       return this.nullable;
   }
   
   public void setNullable(String nullable) {
       this.nullable = nullable;
   }

   public String getDataDefault() {
       return this.dataDefault;
   }
   
   public void setDataDefault(String dataDefault) {
       this.dataDefault = dataDefault;
   }

   public String getComments() {
       return this.comments;
   }
   
   public void setComments(String comments) {
       this.comments = comments;
   }
  



  public boolean equals(Object other) {
        if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Column) ) return false;
		 Column castOther = ( Column ) other; 
        
		 return ( (this.getTablename()==castOther.getTablename()) || ( this.getTablename()!=null && castOther.getTablename()!=null && this.getTablename().equals(castOther.getTablename()) ) )
&& ( (this.getColumnName()==castOther.getColumnName()) || ( this.getColumnName()!=null && castOther.getColumnName()!=null && this.getColumnName().equals(castOther.getColumnName()) ) )
&& ( (this.getDataType()==castOther.getDataType()) || ( this.getDataType()!=null && castOther.getDataType()!=null && this.getDataType().equals(castOther.getDataType()) ) )
&& ( (this.getDataLength()==castOther.getDataLength()) || ( this.getDataLength()!=null && castOther.getDataLength()!=null && this.getDataLength().equals(castOther.getDataLength()) ) )
&& ( (this.getDataPrecision()==castOther.getDataPrecision()) || ( this.getDataPrecision()!=null && castOther.getDataPrecision()!=null && this.getDataPrecision().equals(castOther.getDataPrecision()) ) )
&& ( (this.getDataScale()==castOther.getDataScale()) || ( this.getDataScale()!=null && castOther.getDataScale()!=null && this.getDataScale().equals(castOther.getDataScale()) ) )
&& ( (this.getNullable()==castOther.getNullable()) || ( this.getNullable()!=null && castOther.getNullable()!=null && this.getNullable().equals(castOther.getNullable()) ) )
&& ( (this.getDataDefault()==castOther.getDataDefault()) || ( this.getDataDefault()!=null && castOther.getDataDefault()!=null && this.getDataDefault().equals(castOther.getDataDefault()) ) )
&& ( (this.getComments()==castOther.getComments()) || ( this.getComments()!=null && castOther.getComments()!=null && this.getComments().equals(castOther.getComments()) ) );
  }
  
  public int hashCode() {
        int result = 17;
        
        result = 37 * result + ( getTablename() == null ? 0 : this.getTablename().hashCode() );
        result = 37 * result + ( getColumnName() == null ? 0 : this.getColumnName().hashCode() );
        result = 37 * result + ( getDataType() == null ? 0 : this.getDataType().hashCode() );
        result = 37 * result + ( getDataLength() == null ? 0 : this.getDataLength().hashCode() );
        result = 37 * result + ( getDataPrecision() == null ? 0 : this.getDataPrecision().hashCode() );
        result = 37 * result + ( getDataScale() == null ? 0 : this.getDataScale().hashCode() );
        result = 37 * result + ( getNullable() == null ? 0 : this.getNullable().hashCode() );
        result = 37 * result + ( getDataDefault() == null ? 0 : this.getDataDefault().hashCode() );
        result = 37 * result + ( getComments() == null ? 0 : this.getComments().hashCode() );
        return result;
  }

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getMatchcontent() {
	return matchcontent;
}

public void setMatchcontent(String matchcontent) {
	this.matchcontent = matchcontent;
}   

}