package com.wfms.common.enc;

//Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 2010-4-29 14:45:04
//Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
//Decompiler options: packimports(3) 
//Source File Name:   DriverManagerDataSource.java


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

//Referenced classes of package org.springframework.jdbc.datasource:
//         AbstractDataSource

public class DriverManagerDataSource extends AbstractDataSource
{

	 public DriverManagerDataSource()
	 {
	 }
	
	 public DriverManagerDataSource(String driverClassName, String url, String username, String password)
	     throws CannotGetJdbcConnectionException
	 {
	     setDriverClassName(driverClassName);
	     setUrl(url);
	     setUsername(username);
	     setPassword(password);
	 }
	
	 public DriverManagerDataSource(String url, String username, String password)
	     throws CannotGetJdbcConnectionException
	 {
	     setUrl(url);
	     setUsername(username);
	     setPassword(password);
	 }
	
	 public DriverManagerDataSource(String url)
	     throws CannotGetJdbcConnectionException
	 {
	     setUrl(url);
	 }
	
	 public void setDriverClassName(String driverClassName)
	     throws CannotGetJdbcConnectionException
	 {
	     if(!StringUtils.hasText(driverClassName))
	         throw new IllegalArgumentException("driverClassName must not be empty");
	     this.driverClassName = driverClassName.trim();
	     try
	     {
	         Class.forName(this.driverClassName, true, ClassUtils.getDefaultClassLoader());
	     }
	     catch(ClassNotFoundException ex)
	     {
	         throw new CannotGetJdbcConnectionException("Could not load JDBC driver class [" + this.driverClassName + "]", ex);
	     }
	     if(logger.isInfoEnabled())
	         logger.info("Loaded JDBC driver: " + this.driverClassName);
	 }
	
	 public String getDriverClassName()
	 {
	     return driverClassName;
	 }
	
	 public void setUrl(String url)
	 {
	     if(!StringUtils.hasText(url))
	     {
	         throw new IllegalArgumentException("url must not be empty");
	     } else
	     {
	         this.url = url.trim();
	         return;
	     }
	 }
	
	 public String getUrl()
	 {
	     return url;
	 }
	
	 public void setUsername(String username)
	 {
	     this.username = username;
	 }
	
	 public String getUsername()
	 {
	     return username;
	 }
	
	 public void setPassword(String password)
	 {
	     this.password = password;
	 }
	
	 public String getPassword()
	 {
		 try {
			String realInfo =  new String(Encrypt.decrypt(Encrypt.hex2byte(password.getBytes()),Encrypt.PASSWORD_CRYPT_KEY.getBytes()));
			return realInfo.substring(realInfo.indexOf(":")+1,realInfo.length());
		 } catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	 }
	
	 public void setConnectionProperties(Properties connectionProperties)
	 {
	     this.connectionProperties = connectionProperties;
	 }
	
	 public Properties getConnectionProperties()
	 {
	     return connectionProperties;
	 }
	
	 public Connection getConnection()
	     throws SQLException
	 {
	     return getConnectionFromDriverManager();
	 }
	
	 public Connection getConnection(String username, String password)
	     throws SQLException
	 {
	     return getConnectionFromDriverManager(username, password);
	 }
	
	 protected Connection getConnectionFromDriverManager()
	     throws SQLException
	 {
	     return getConnectionFromDriverManager(getUsername(), getPassword());
	 }
	
	 protected Connection getConnectionFromDriverManager(String username, String password)
	     throws SQLException
	 {
	     Properties props = new Properties(getConnectionProperties());
	     if(username != null)
	         props.setProperty("user", username);
	     if(password != null)
	         props.setProperty("password", password);
	     return getConnectionFromDriverManager(getUrl(), props);
	 }
	
	 protected Connection getConnectionFromDriverManager(String url, Properties props)
	     throws SQLException
	 {
	     if(logger.isDebugEnabled())
	         logger.debug("Creating new JDBC Connection to [" + url + "]");
	     return DriverManager.getConnection(url, props);
	 }
	
	 private String driverClassName;
	 private String url;
	 private String username;
	 private String password;
	 private Properties connectionProperties;
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
