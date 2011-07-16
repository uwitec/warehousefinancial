/**
 *******************************************************************************
 * 文件名：BarcodeAction.java
 *
 * 描述：
 * 
 * 创建日期：Mar 10, 2010 6:09:53 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.env.EnvironmentFactory;
import net.sourceforge.barbecue.linear.code39.Code39Barcode;
import net.sourceforge.barbecue.output.OutputException;


	public class BarcodeAction extends javax.servlet.http.HttpServlet {

		public void execute(
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			doRequest(request,response);
		}

		private void doRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException {
			String data = getRequiredParameter(req, "data");
			String type = getParameter(req, "type");
			String appId = getParameter(req, "appid");
			Integer width = getParameterAsInteger(req, "width");
			Integer height = getParameterAsInteger(req, "height");
			Integer resolution = getParameterAsInteger(req, "resolution");
			boolean checksum = getParameterAsBoolean(req, "checksum", false);
			boolean headless = getParameterAsBoolean(req, "headless", true);
			boolean drawText = false;

			if (headless) {
				EnvironmentFactory.setHeadlessMode();
			} else {
				drawText = getParameterAsBoolean(req, "drawText", false);
			}

			Barcode barcode = getBarcode(type, "xunersoft"+data, appId, checksum);
			barcode.setDrawingText(drawText);

			if (width != null) {
				barcode.setBarWidth(width.intValue());
			}
			if (height != null) {
				barcode.setBarHeight(height.intValue());
			}
			if (resolution != null) {
				barcode.setResolution(resolution.intValue());
			}

			try {
				outputBarcodeImage(res, barcode);
			} catch (IOException e) {
				throw new ServletException("Could not output barcode", e);
			} catch (OutputException e) {
				throw new ServletException("Could not output barcode", e);
			}
		}

		private String getRequiredParameter(HttpServletRequest req, String name) throws ServletException {
			String value = getParameter(req, name);
			if (value == null) {
				throw new ServletException("Parameter " + name + " is required");
			}
			return value;
		}

		private boolean getParameterAsBoolean(HttpServletRequest req, String name, boolean def) {
			String value = getParameter(req, name);
			if (value == null) {
				return def;
			}
			return Boolean.valueOf(value).booleanValue();
		}

		private Integer getParameterAsInteger(HttpServletRequest req, String name) {
			String value = getParameter(req, name);
			if (value == null) {
				return null;
			}
			return new Integer(value);
		}

		private String getParameter(HttpServletRequest req, String name) {
			return req.getParameter(name);
		}

		protected Barcode getBarcode(String type, String data, String appId, boolean checkSum) throws ServletException {
			if (type == null || type.length() == 0) {
				try {
					return BarcodeFactory.createCode128B(data);
				} catch (BarcodeException e) {
					throw new ServletException("BARCODE ERROR", e);
				}
			} else if (isType(type, new String[] {"UCC128"})) {
				if (appId == null) {
					throw new ServletException("UCC128 barcode type requires the appid parameter");
				}
				try {
					return BarcodeFactory.createUCC128(appId, data);
				} catch (BarcodeException e) {
					throw new ServletException("BARCODE ERROR", e);
				}
			} else if (isType(type, Code39Barcode.TYPES)) {
				try {
					return BarcodeFactory.createCode39(data, checkSum);
				} catch (BarcodeException e) {
					throw new ServletException("BARCODE ERROR", e);
				}
			}

			try {
				return (Barcode) getMethoda(type).invoke(null, new Object[] {data});
			} catch (NoSuchMethodException e) {
				throw new ServletException("Invalid barcode type: " + type);
			} catch (SecurityException e) {
				throw new ServletException("Could not create barcode of type: " + type
										   + "; Security exception accessing BarcodeFactory");
			} catch (IllegalAccessException e) {
				throw new ServletException("Could not create barcode of type: " + type
										   + "; Illegal access to BarcodeFactory");
			} catch (InvocationTargetException e) {
				throw new ServletException("Could not create barcode of type: " + type
										   + "; Could not invoke BarcodeFactory");
			}
		}

		private boolean isType(String value, String[] types) {
			for (int i = 0; i < types.length; i++) {
				String type = types[i];
				if (value.equalsIgnoreCase(type)) {
					return true;
				}
			}
			return false;
		}

		private Method getMethoda(String type) throws NoSuchMethodException {
			Method[] methods = BarcodeFactory.class.getMethods();

			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if ((method.getParameterTypes().length == 1) && matches(method, type)) {
					return method;
				}
			}

			throw new NoSuchMethodException();
		}

		private boolean matches(Method method, String type) {
			return method.getName().startsWith("create") && method.getName().substring(6).equalsIgnoreCase(type);
		}

		private void outputBarcodeImage(HttpServletResponse res, Barcode barcode) throws IOException, OutputException {
			res.setContentType("image/png");
			ServletOutputStream out = res.getOutputStream();
			BarcodeImageHandler.writePNG(barcode, out);
			out.flush();
			out.close();
		}

}
