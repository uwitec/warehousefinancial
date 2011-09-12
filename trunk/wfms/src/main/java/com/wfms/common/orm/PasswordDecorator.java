package com.wfms.common.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.security.auth.callback.PasswordCallback;

import org.junit.Assert;

import com.alibaba.druid.pool.DruidDataSource;

public class PasswordDecorator extends PasswordCallback {

	private String url;
	private Properties properties;

	public PasswordDecorator() {
		super("test", false);
	}

	public PasswordDecorator(String prompt, boolean echoOn) {
		super(prompt, echoOn);
	}

	public String getUrl() {
		return url;
	}

	// Druid将会通过反射来调用这个方法
	public void setUrl(String url) {
		this.url = url;
	}

	public Properties getProperties() {
		return properties;
	}

	// Druid将会通过反射来调用这个方法
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl("jdbc:mock:");

		PasswordDecorator passwordCallback = new PasswordDecorator();
		dataSource.setPasswordCallback(passwordCallback);

		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(dataSource.getUrl(), passwordCallback.getUrl());
		Assert.assertEquals(dataSource.getConnectProperties(),
				passwordCallback.getProperties());
	}

}
