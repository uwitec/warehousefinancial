package com.wfms.common.function.dao.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import xuner.web.mvc.bean.ExportBean;

import com.wfms.common.dao.ExtDao;
import com.wfms.common.function.dao.IDataExportDao;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：DataExportDAO
 *  <dd> 类描述：通用数据导出类（from db to excel）
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 2:00:39 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see DataExportDAO
 * @version 1.0
 *
 */
@Repository
public class DataExportDAO extends ExtDao implements IDataExportDao{

	private String date = "";
	
	public  boolean exportData(String title, String condition,
			String tableName, String[] colList, HttpServletResponse response) {
		if ((condition == null) || (tableName == null))
			return false;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;

		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", 
					"attachment; filename="+ URLEncoder.encode(title,"utf-8")+".xls");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		try {
			wwb = Workbook.createWorkbook(response.getOutputStream());
			ws = wwb.createSheet("sheet1",1);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		try {

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			
			if (colList == null)
				colList = getColumnName("select * from " + tableName);
			List<String> topTrCh = arrayToList(getColumnNameCN(colList,tableName));
			if (topTrCh == null || topTrCh.size() == 0)
				return false;

			String sql = "select a.* from " + tableName + " a where 1=1"
					+ condition;
			List<String[]> rs = getArrayList(sql, new String[0], colList);

			ws.getSettings().setDefaultRowHeight(400); // 
			ws.setRowView(0, 500);// 
			ws.mergeCells(0, 0, topTrCh.size() - 1, 0);//
			
			WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 16,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
			titleFormat.setAlignment(Alignment.CENTRE);
			// 
			WritableFont fieldFont = new jxl.write.WritableFont(
					WritableFont.ARIAL, 12, WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.GREEN);
			WritableCellFormat fieldFormat = new jxl.write.WritableCellFormat(
					fieldFont);
			fieldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			fieldFormat.setAlignment(Alignment.CENTRE);
			// 
			WritableFont dataFont = new jxl.write.WritableFont(
					WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableCellFormat dataFormat1 = new jxl.write.WritableCellFormat(
					dataFont);
			WritableCellFormat dataFormat2 = new jxl.write.WritableCellFormat(
					dataFont);
			dataFormat1.setBackground(jxl.format.Colour.ICE_BLUE);
			dataFormat2.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
			dataFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
			dataFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);

			ws.addCell(new Label(0, 0, title, titleFormat));

			for (int i = 0; i < topTrCh.size(); i++) {
				ws.addCell(new Label(i, 1, topTrCh.get(i), fieldFormat));
			}

			for (int i = 0; i < rs.size(); i++) {
				if (i % 2 == 0) {
					String[] r = rs.get(i);
					for (int j = 0; j < r.length; j++) {
						ws.addCell(new Label(j, i + 2, r[j], dataFormat1));
					}
				} else {
					String[] r = rs.get(i);
					for (int j = 0; j < r.length; j++) {
						ws.addCell(new Label(j, i + 2, r[j], dataFormat2));
					}
				}
			}
			wwb.write();
			wwb.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
			topTrCh = null;
			rs = null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取所有中文注释列名
	 * 
	 * @param cName
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public String[] getColumnNameCN(String cName[], String tabName)
			throws SQLException {
		String tit[] = new String[cName.length];
		String sql = "select COMMENTS from user_col_comments where table_name=? and COLUMN_NAME=?";
		for (int i = 0; i < cName.length; i++) {
			String tmp[] = getOneRs(sql, new String[] { tabName.toUpperCase(),
					cName[i].toUpperCase() }, new String[] { "COMMENTS" });
			if (tmp == null) {
				tit[i] = cName[i];
			} else {
				tit[i] = tmp[0];
				if (tit[i] == null
						|| tit[i].toLowerCase().equalsIgnoreCase("null")
						|| tit[i].equalsIgnoreCase(""))
					tit[i] = cName[i];
				if (tit[i].indexOf('(') >= 0)
					tit[i] = tit[i].substring(0, tit[i].indexOf('('));
				if (tit[i].indexOf('（') >= 0)
					tit[i] = tit[i].substring(0, tit[i].indexOf('（'));
			}
		}
		return tit;
	}
	
	protected String[] getColumnName(String sql) throws SQLException {
		String tit[] = (String[]) null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			rsmd = rs.getMetaData();
			tit = new String[rsmd.getColumnCount()];
			for (int i = 0; i < rsmd.getColumnCount(); i++)
				tit[i] = rsmd.getColumnLabel(i + 1);
		} catch (SQLException e) {
			e.printStackTrace();
			tit = new String[0];
			throw e;
		}
		return tit;
	}
	
	public List<String> arrayToList(String arr[]) {
		List<String> list = null;
		if(arr!=null)
		{
			list = new ArrayList<String>(arr.length);
			for (String s : arr) {
				list.add(s);
			}
		}
		else
			list=new ArrayList<String>(0);
		return list;
	}
	
	/**
	 * 获取记录集，返回类型为List<String[]>
	 * 
	 * @param sql
	 * @param inputValue
	 * @param outputValue
	 * @return
	 */
	public List<String[]> getArrayList(String sql, String inputValue[],
			String outputValue[]) throws SQLException {
		List<String[]> list = new ArrayList<String[]>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < inputValue.length; i++)
				stmt.setString(i + 1, inputValue[i]);
			String tmp[];
			for (rs = stmt.executeQuery(); rs.next(); list.add(tmp)) {
				tmp = new String[outputValue.length];
				for (int i = 0; i < outputValue.length; i++) {
					String tempstr = rs.getString(outputValue[i]);
					tmp[i] = tempstr == null || !outputValue[i].contains("sj")
							|| tempstr.length() != 14 ? tempstr
							: (new StringBuilder(String.valueOf(tempstr
									.substring(0, 4)))).append("年").append(
									tempstr.substring(4, 6)).append("月")
									.append(tempstr.substring(6, 8)).append(
											"日 ").append(
											tempstr.substring(8, 10)).append(
											":").append(
											tempstr.substring(10, 12)).append(
											":").append(
											tempstr.substring(12, 14))
									.toString();
					if (tmp[i] == null || tmp[i].equalsIgnoreCase(""))
						tmp[i] = "";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			list = null;
			throw e;
		}
		return list;
	}

	/**
	 * 获取单行记录，返回类型为String[]
	 * 
	 * @param sql
	 * @param inputValue
	 * @param outputValue
	 * @return
	 */
	public String[] getOneRs(String sql, String inputValue[],
			String outputValue[]) throws SQLException {
		String result[] = new String[outputValue.length];
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < inputValue.length; i++)
				stmt.setString(i + 1, inputValue[i]);
			rs = stmt.executeQuery();
			if (rs.next()) {
				for (int i = 0; i < outputValue.length; i++)
					result[i] = rs.getString(outputValue[i]);
			} else {
				result = (String[]) null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result =  null;
			throw e;
		}
		return result;
	}
	
	/**
	 * 新生报到数据导出
	 */
	public boolean exportXsbdData(String title, String condition,
			String tableName, String[] colList, HttpServletResponse response){
		try{
			Locale systime = Locale.CHINA;
			SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", systime);
			date = timeformat.format(new Date());// 求得本地机的系统时间;
			
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(title, "utf-8")
					+ ".xls");
			
			WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
			Connection conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();

			WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
			titleFormat.setAlignment(Alignment.CENTRE);
			// 
			WritableFont fieldFont = new jxl.write.WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.GREEN);
			WritableCellFormat fieldFormat = new jxl.write.WritableCellFormat(fieldFont);
			fieldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			fieldFormat.setAlignment(Alignment.CENTRE);
			// 
			WritableFont dataFont = new jxl.write.WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat dataFormat1 = new jxl.write.WritableCellFormat(dataFont);
			WritableCellFormat dataFormat2 = new jxl.write.WritableCellFormat(dataFont);
			dataFormat1.setBackground(jxl.format.Colour.ICE_BLUE);
			dataFormat2.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
			dataFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
			dataFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			//总和表
			wwb = this.getAllSheet(wwb, conn, tableName, colList, titleFormat, fieldFormat, dataFormat1, dataFormat2);
			//专业报名人数统计
			wwb = this.getZySheet(wwb, conn, tableName, titleFormat, fieldFormat, dataFormat1, dataFormat2);
			//每日报名人数
			wwb = this.getDaySheet(wwb, conn, tableName, titleFormat, fieldFormat, dataFormat1, dataFormat2);
			//区域招生人数
			wwb = this.getQySheet(wwb, conn, tableName, titleFormat, fieldFormat, dataFormat1, dataFormat2);
			//联系人统计
			wwb = this.getBySheet(wwb, conn, tableName, titleFormat, fieldFormat, dataFormat1, dataFormat2);
			
			wwb.write();
			wwb.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private WritableWorkbook getAllSheet(WritableWorkbook wwb, Connection conn, String tableName,
			String[] colList, WritableCellFormat titleFormat, WritableCellFormat fieldFormat, WritableCellFormat dataFormat1, WritableCellFormat dataFormat2) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(this.getAllSql(colList, tableName));
			rs = stmt.executeQuery();

			// 获得列名注释
			List<String> topTrCh = arrayToList(getColumnNameCN(colList, tableName));
			
			WritableSheet ws = wwb.createSheet("总和原表", 1);

			ws.getSettings().setDefaultRowHeight(400); // 
			ws.setRowView(0, 500);// 
			ws.mergeCells(0, 0, topTrCh.size() - 1, 0);//

			ws.addCell(new Label(0, 0, "学生报名名单", titleFormat));

			ws.addCell(new Label(0, 1, "序号", fieldFormat));
			for (int i = 1; i <= topTrCh.size(); i++) {
				ws.addCell(new Label(i, 1, topTrCh.get(i-1), fieldFormat));
			}
			
			int i = 0;
			while (rs.next()) {
				if(i %2 == 0){
					ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat1));
					for(int j=1;j<=colList.length;j++){
						ws.addCell(new Label(j, i + 2, rs.getString(j), dataFormat1));
					}
				}else{
					ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat2));
					for(int j=1;j<=colList.length;j++){
						ws.addCell(new Label(j, i + 2, rs.getString(j), dataFormat2));
					}
				}
				i++;
			}
			rs = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return wwb;
	}
	
	private WritableWorkbook getZySheet(WritableWorkbook wwb, Connection conn, String tableName,
			WritableCellFormat titleFormat, WritableCellFormat fieldFormat, WritableCellFormat dataFormat1, WritableCellFormat dataFormat2) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(this.getZySql(tableName));
			rs = stmt.executeQuery();
			
			WritableSheet ws = wwb.createSheet("专业报名人数统计", 2);

			ws.getSettings().setDefaultRowHeight(400); // 
			ws.setRowView(0, 500);// 
			ws.mergeCells(0, 0, 5, 0);//

			ws.addCell(new Label(0, 0, "重庆技工学校"+date.substring(0,4)+"年新生专业分布表", titleFormat));
			String[] topTrCh = new String[]{"序号","专业级别","专业名称","学年","报名人数"};
			for (int i = 0; i < topTrCh.length; i++) {
				ws.addCell(new Label(i, 1, topTrCh[i], fieldFormat));
			}
			
			int count = 1;
			int cc = 0;
			String row = "";
			//String zy = "";
			String r = "";
			String n = "";
			List<String[]> arr = new ArrayList();
			while (rs.next()) {
				cc ++;
				String[] ps = new String[4];
				for(int i=1;i<=ps.length;i++){
					if(i == 1){
						r = rs.getString(i);
						if(n.equals(r)){
							count++;
						}else{
							//zy += r+",";
							if(cc != 1){
								row += count+",";
							}
							count = 1;
							n = r;
						}
						ps[i] = r;
					}else{
						ps[i] = rs.getString(i);
					}
				}
				arr.add(ps);
			}
			rs = null;
			
			row += count+",";
			
			WritableFont fieldFont = new jxl.write.WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat fmat = new jxl.write.WritableCellFormat(fieldFont);
			fmat.setBorder(Border.ALL, BorderLineStyle.THIN);
			fmat.setAlignment(Alignment.CENTRE);
			
			int kk = 0;
			String[] rows = row.split("[,]");
			//String[] zys = zy.split("[,]");
			count = 0;
			cc = 0;
			for(String[] ps:arr){
				ws.addCell(new Label(0, kk + 2, String.valueOf(kk), fmat));
				r = ps[0];
				if(kk == 0 || kk == cc){
					ws.addCell(new Label(1, Integer.parseInt(rows[count]) + 2, ps[0], fmat));
					for(int i=1;i<ps.length;i++){
						ws.addCell(new Label(i+1, kk + 2, ps[i], fmat));
					}
					cc = Integer.parseInt(rows[count])+cc;
					count++;
				}else{
					for(int i=1;i<ps.length;i++){
						ws.addCell(new Label(i+1, kk + 2, ps[i], fmat));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return wwb;
	}
	
	private WritableWorkbook getDaySheet(WritableWorkbook wwb, Connection conn, String tableName,
			WritableCellFormat titleFormat, WritableCellFormat fieldFormat, WritableCellFormat dataFormat1, WritableCellFormat dataFormat2) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(this.getDaySql(tableName));
			rs = stmt.executeQuery();

			WritableSheet ws = wwb.createSheet("每日报名人数", 3);

			ws.getSettings().setDefaultRowHeight(400); // 
			ws.setRowView(0, 500);// 
			ws.mergeCells(0, 0, 2, 0);//

			ws.addCell(new Label(0, 0, "每日报名人数", titleFormat));

			String[] topTrCh = new String[]{"序号","日期","报名人数"};
			for (int i = 0; i < topTrCh.length; i++) {
				ws.addCell(new Label(i, 1, topTrCh[i], fieldFormat));
			}
			
			int i = 0;
			int count = 0;
			String bmrs = "";
			while (rs.next()) {
				if(i %2 == 0){
					ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat1));
					ws.addCell(new Label(1, i + 2, rs.getString(1), dataFormat1));
					bmrs = rs.getString(2);
					count += Integer.parseInt(bmrs);
					ws.addCell(new Label(2, i + 2, bmrs, dataFormat1));
				}else{
					ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat2));
					ws.addCell(new Label(1, i + 2, rs.getString(1), dataFormat2));
					bmrs = rs.getString(2);
					count += Integer.parseInt(bmrs);
					ws.addCell(new Label(2, i + 2, bmrs, dataFormat2));
				}
				i++;
			}
			rs = null;
			if(i %2 == 0){
				ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat1));
				ws.addCell(new Label(1, i + 2, "合计", dataFormat1));
				ws.addCell(new Label(2, i + 2, count+"", dataFormat1));
			}else{
				ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat2));
				ws.addCell(new Label(1, i + 2, "合计", dataFormat2));
				ws.addCell(new Label(2, i + 2, count+"", dataFormat2));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return wwb;
	}
	
	private WritableWorkbook getQySheet(WritableWorkbook wwb, Connection conn, String tableName,
			WritableCellFormat titleFormat, WritableCellFormat fieldFormat, WritableCellFormat dataFormat1, WritableCellFormat dataFormat2) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(this.getQySql(tableName));
			rs = stmt.executeQuery();

			WritableSheet ws = wwb.createSheet("区域招生统计", 4);

			ws.getSettings().setDefaultRowHeight(400); // 
			ws.setRowView(0, 500);// 
			ws.mergeCells(0, 0, 2, 0);//

			ws.addCell(new Label(0, 0, "区域招生统计", titleFormat));

			String[] topTrCh = new String[]{"序号","地区","人数"};
			for (int i = 0; i < topTrCh.length; i++) {
				ws.addCell(new Label(i, 1, topTrCh[i], fieldFormat));
			}
			
			int i = 0;
			int count = 0;
			String bmrs = "";
			while (rs.next()) {
				if(i %2 == 0){
					ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat1));
					ws.addCell(new Label(1, i + 2, rs.getString(1), dataFormat1));
					bmrs = rs.getString(2);
					count += Integer.parseInt(bmrs);
					ws.addCell(new Label(2, i + 2, bmrs, dataFormat1));
				}else{
					ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat2));
					ws.addCell(new Label(1, i + 2, rs.getString(1), dataFormat2));
					bmrs = rs.getString(2);
					count += Integer.parseInt(bmrs);
					ws.addCell(new Label(2, i + 2, bmrs, dataFormat2));
				}
				i++;
			}
			rs = null;
			if(i %2 == 0){
				ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat1));
				ws.addCell(new Label(1, i + 2, "合计", dataFormat1));
				ws.addCell(new Label(2, i + 2, count+"", dataFormat1));
			}else{
				ws.addCell(new Label(0, i + 2, String.valueOf(i+1), dataFormat2));
				ws.addCell(new Label(1, i + 2, "合计", dataFormat2));
				ws.addCell(new Label(2, i + 2, count+"", dataFormat2));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return wwb;
	}
	
	private WritableWorkbook getBySheet(WritableWorkbook wwb, Connection conn, String tableName,
			WritableCellFormat titleFormat, WritableCellFormat fieldFormat, WritableCellFormat dataFormat1, WritableCellFormat dataFormat2) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(this.getBySql(tableName));
			rs = stmt.executeQuery();
			
			WritableSheet ws = wwb.createSheet("联系人数统计", 2);

			ws.getSettings().setDefaultRowHeight(400); // 
			ws.setRowView(0, 500);// 
			ws.mergeCells(0, 0, 5, 0);//

			ws.addCell(new Label(0, 0, "联系人新生统计表", titleFormat));
			String[] topTrCh = new String[]{"序号","联系人","姓名","地址","报名时间"};
			for (int i = 0; i < topTrCh.length; i++) {
				ws.addCell(new Label(i, 1, topTrCh[i], fieldFormat));
			}
			
			int count = 1;
			int cc = 0;
			String row = "";
			String r = "";
			String n = "";
			List<String[]> arr = new ArrayList();
			while (rs.next()) {
				cc ++;
				String[] ps = new String[4];
				for(int i=1;i<=ps.length;i++){
					if(i == 1){
						r = rs.getString(i);
						if(n.equals(r)){
							count++;
						}else{
							if(cc != 1){
								row += count+",";
							}
							count = 1;
							n = r;
						}
						ps[i] = r;
					}else{
						ps[i] = rs.getString(i);
					}
				}
				arr.add(ps);
			}
			rs = null;
			
			row += count+",";
			
			WritableFont fieldFont = new jxl.write.WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat fmat = new jxl.write.WritableCellFormat(fieldFont);
			fmat.setBorder(Border.ALL, BorderLineStyle.THIN);
			fmat.setAlignment(Alignment.CENTRE);
			
			int kk = 0;
			String[] rows = row.split("[,]");
			count = 0;
			cc = 0;
			for(String[] ps:arr){
				ws.addCell(new Label(0, kk + 2, String.valueOf(kk), fmat));
				r = ps[0];
				if(kk == 0 || kk == cc){
					ws.addCell(new Label(1, Integer.parseInt(rows[count]) + 2, ps[0]+"("+rows[count]+"人)", fmat));
					for(int i=1;i<ps.length;i++){
						ws.addCell(new Label(i+1, kk + 2, ps[i], fmat));
					}
					cc = Integer.parseInt(rows[count])+cc;
					count++;
				}else{
					for(int i=1;i<ps.length;i++){
						ws.addCell(new Label(i+1, kk + 2, ps[i], fmat));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return wwb;
	}
	
	private String getAllSql(String[] rows, String tableName) {
		String sql = "";
		String row = "";
		try {
			if (rows.length > 1) {
				for (int i = 0; i < rows.length; i++) {
					row += rows[i] + ",";
				}

				row = row.substring(0, row.length() - 1);
				sql = "SELECT " + row + " FROM " + tableName +" ORDER BY BMSJ";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sql;
	}
	
	private String getZySql(String tableName) {
		String sql = "";
		try {
			sql = "SELECT ZYJBDM,ZYMC,XZ,count(*) BMRS" +
					" FROM " + tableName +
					"" +//查询条件
					" GROUP BY ZYJBDM,ZYMC,XZ ORDER BY ZYJBDM";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sql;
	}
	
	private String getDaySql(String tableName) {
		String sql = "";
		try {
			sql = "SELECT BMSJ,count(*) BMRS" +
					" FROM " + tableName +
					"" +//查询条件
					" GROUP BY BMSJ ORDER BY BMSJ";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sql;
	}
	
	private String getQySql(String tableName) {
		String sql = "";
		try {
			sql = "SELECT HKSZD,count(*) BMRS" +
					" FROM " + tableName +
					"" +//查询条件
					" GROUP BY HKSZD";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sql;
	}
	
	private String getBySql(String tableName) {
		String sql = "";
		try {
			sql = "SELECT LXR,XM,HKSZD,BMSJ" +
					" FROM " + tableName +
					"" +//查询条件
					" ORDER BY LXR";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sql;
	}
	
	public ExportBean getExportBean(String title, String sql, String[] tables,
			Map matchMap) {
		ExportBean exportBean = new ExportBean();
		String[] fields = null;
		try {
			fields = super.getColumnName(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> topTrCh = null;
		try {
			topTrCh = super.arrayToList(getColumnNameCN(fields,tables));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (topTrCh == null || topTrCh.size() == 0)
			return null;
		if(matchMap!=null && matchMap.size()>0)
		{
			for(int i=0;i<topTrCh.size();i++)
			{
				if(matchMap.get(topTrCh.get(i))!=null)
				{
					String mark = topTrCh.get(i);
					topTrCh.remove(i);
					topTrCh.add(i,(String)matchMap.get(mark));
				}
			}
		}
		List<String[]> rs = null;
		try {
			rs = getArrayList(sql, new String[0], fields);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		exportBean.setTitle(title);
		exportBean.setTableTitle(title);
		exportBean.setHeader(topTrCh);
		exportBean.setTableContent(rs);
		return exportBean;
	}
}
