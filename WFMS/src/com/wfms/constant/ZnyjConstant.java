package com.wfms.constant;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ZnyjConstant
 *  <dd> 类描述：站内邮件常量
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 19, 2011 7:01:49 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see ZnyjConstant
 * @version 1.0
 *
 */
public class ZnyjConstant {
	
	public static final String MAIL_SJX = "收件箱";
	
	public static final String MAIL_FJX = "发件箱";
	
	public static final String MAIL_CGX = "草稿箱";
	
	public static final String MAIL_LJX = "垃圾箱";

	/**
	 * 草稿
	 */
	public static final String MAILSEND_ZT_CG = "0";
	/**
	 * 已发送
	 */
	public static final String MAILSEND_ZT_FS = "1";
	/**
	 * 已被阅读
	 */
	public static final String MAILSEND_ZT_YD = "2";
	/**
	 * 垃圾
	 */
	public static final String MAILSEND_ZT_LJ = "4";
	
	/**
	 * 未读
	 */
	public static final String MAILRECEIVE_ZT_WD = "0";
	/**
	 * 已读
	 */
	public static final String MAILRECEIVE_ZT_YD = "1";
	/**
	 * 垃圾
	 */
	public static final String MAILRECEIVE_ZT_LJ = "4";
}
