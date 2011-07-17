package com.wfms.common.function.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wfms.common.function.attribute.FusionChart;
import com.wfms.common.function.attribute.FusionUnit;
import com.wfms.common.function.attribute.StatConditionBean;
import com.wfms.common.function.attribute.StatResultSetBean;
import com.wfms.common.function.attribute.StatUnit;
import com.wfms.common.function.attribute.fusion3.Category;
import com.wfms.common.function.attribute.fusion3.CategoryUnit;
import com.wfms.common.function.attribute.fusion3.Chart;
import com.wfms.common.function.attribute.fusion3.Data;
import com.wfms.common.function.attribute.fusion3.DataSet;
import com.wfms.common.function.dao.IStatDefineDao;
import com.wfms.common.function.dao.impl.CommonStatDAO;
import com.wfms.common.function.entity.StatDefine;

@Component
public class StatUtil {

	// 三个统计图默认标题
	private String categoryTitle = "分类统计";
	private String itemTitle = "项目统计";
	private String generalTitle = "综合分布统计图";

	private double[][] data;
	private double[][] retData;
	List<String> rowKeys;
	List<String> columnKeys;
	private List<String> columnsHeaders;
	private StatConditionBean statCondBean;
	private StatResultSetBean rsBean;

	private IStatDefineDao statDefineDao;

	private CommonStatDAO dataStatDao;

	public void setColumnsHeaders(List<String> columnsHeaders) {
		this.columnsHeaders = columnsHeaders;
	}

	@Autowired
	public void setDataStatDao(CommonStatDAO dataStatDao) {
		this.dataStatDao = dataStatDao;
	}

	@Autowired
	public void setStatDefineDao(IStatDefineDao statDefineDao) {
		this.statDefineDao = statDefineDao;
	}

	public void setRowKeys(List<String> rowKeys) {
		this.rowKeys = rowKeys;
	}

	public void setColumnKeys(List<String> columnKeys) {
		this.columnKeys = columnKeys;
	}

	public StatUtil() {
	}

	public void initStatUtil(String statType, String columnKeyType,
			String tableName, String condition, List<String> columnKeys)
			throws ChartCreateException {
		this.columnKeys = columnKeys;
		rsBean = new StatResultSetBean();

		rowKeys = dataStatDao.getRowKeys(statType, tableName, condition);
		/*
		 * if (columnKeys == null || columnKeys.size() == 0) { throw new
		 * ChartCreateException("无此统计对象的数据", 56); } if (rowKeys == null) { throw
		 * new ChartCreateException("此分类无效", 59); }
		 */
		if ((rowKeys.isEmpty()) || (columnKeys.isEmpty())) {
			throw new ChartCreateException("未查到有效数据", 72);
		}
		if (!dataStatDao.initStatResultSetBean(rsBean, statType, rowKeys,
				columnKeyType, columnKeys, tableName, condition)) {
			throw new ChartCreateException("统计失败", 76);
		}
	}

	public void initStatUtil(StatConditionBean condBean)
			throws ChartCreateException {
		this.statCondBean = condBean;
		rsBean = new StatResultSetBean();

		if (!dataStatDao.initStatResultSetBean(this, rsBean, condBean)) {
			throw new ChartCreateException("统计失败", 85);
		}
	}

	public void initStatUtil(StatResultSetBean rsBean,
			StatConditionBean condBean) throws ChartCreateException {
		this.rsBean = rsBean;
		this.statCondBean = condBean;
		if (!dataStatDao.initStatResultSetBean(this, rsBean, condBean)) {
			throw new ChartCreateException("统计失败", 85);
		}
	}

	/**
	 * 统计辅助类构造方法
	 * 
	 * @param statType
	 *            统计类型
	 * @param columnKeyType
	 *            统计关键列
	 * @param tableName
	 *            需要查询的视图或表名
	 * @param condition
	 *            查询筛选条件
	 * @param columnKeys
	 *            统计列集合
	 * @throws ChartCreateException
	 */
	public StatUtil(String statType, String columnKeyType, String tableName,
			String condition, List<String> columnKeys, String scopeStat)
			throws ChartCreateException {

		this.columnKeys = columnKeys;
		rsBean = new StatResultSetBean();

		rowKeys = dataStatDao.getRowKeys(statType, tableName, condition);
		if (columnKeys == null || columnKeys.size() == 0) {
			throw new ChartCreateException("无此统计对象的数据", 104);
		}
		if (rowKeys == null) {
			throw new ChartCreateException("此分类无效", 107);
		}

		if ((rowKeys.isEmpty()) || (columnKeys.isEmpty())) {
			throw new ChartCreateException("未查到有效数据", 111);
		}
		if (!dataStatDao.initRsBeanByScope(rsBean, statType, rowKeys,
				columnKeyType, columnKeys, tableName, condition)) {
			throw new ChartCreateException("统计失败", 115);
		}
	}

	/**
	 * 根据StatResultSetBean初始化统计数据
	 */
	public void initData() {
		data = new double[rsBean.getRowNum()][rsBean.getColumnNum()];
		for (int i = 0; i < rsBean.getRowNum(); i++) {
			for (int j = 0; j < rsBean.getColumnNum(); j++) {
				List<String> row = (List<String>) (rsBean.getRs().get(i));
				data[i][j] = Double.parseDouble(row.get(j));
			}
		}
	}

	/**
	 * 把统计数据进行总计，并返回总计后的二维数组
	 * 
	 * @param data
	 *            需要进行总计的二维数组数据
	 * @param calcType
	 *            总计类型,若指定为percent则按百分比进行总计，其他默认为总数总计
	 * @return double[][]
	 */
	private void totalizeData(String calcType) {
		double total = 0;
		// double[][] data={{10,23,43},{20,38,12},{45,43,27},{1,1,3}};
		retData = new double[data.length + 1][data[0].length + 1];
		// System.arraycopy(data, 0, retData, 0, data.length);

		double[] rowTotal = new double[data[0].length];
		for (int i = 0; i < data.length; i++) {
			retData[i] = new double[data[i].length + 1];
			double columnTotal = 0;
			for (int j = 0; j < data[i].length; j++) {
				retData[i][j] = data[i][j];
				columnTotal += data[i][j];
				rowTotal[j] += data[i][j];
			}
			retData[i][data[i].length] = columnTotal;
		}
		for (int k = 0; k < data[0].length; k++) {
			retData[retData.length - 1][k] = rowTotal[k];
			total += retData[retData.length - 1][k];
		}
		retData[retData.length - 1][retData[0].length - 1] = total;
		if (calcType != null && calcType.equals("percent")) {
			for (int m = 0; m < data.length; m++) {
				retData[m][retData[m].length - 1] = total == 0 ? 0
						: retData[m][retData[m].length - 1] / total;
			}
			for (int n = 0; n < data[0].length; n++) {
				retData[retData.length - 1][n] = total == 0 ? 0
						: retData[retData.length - 1][n] / total;
			}
			retData[retData.length - 1][retData[0].length - 1] = 1;
		}
	}

	public String[][] buildNoneTotalArray() {

		// 构建无总计统计数组
		String[] rwScope = new String[columnKeys.size() + 1];
		String[][] dataScpe = new String[data.length][data[0].length + 1];
		for (int i = 1; i < rwScope.length; i++) {
			if (i < columnKeys.size() + 1) {
				rwScope[i] = columnKeys.get(i - 1);
			}
		}
		for (int p = 0; p < dataScpe.length; p++) {
			dataScpe[p][0] = rowKeys.get(p);
			for (int k = 1; k < dataScpe[p].length; k++) {
				if (k < data[p].length) {
					dataScpe[p][k] = String.valueOf(data[p][k]);
				}
			}
		}
		return dataScpe;
	}

	/**
	 * 把统计数据生成二维数组并存入某个隐式对象
	 * 
	 * @param title
	 *            表格统计头
	 * @param request
	 *            HttpServletRequest
	 */
	public void pushDataToScope(List<String> columnsHeaders, String title,
			HttpServletRequest request) {
		// 构建列头
		boolean isDesignated = (columnsHeaders != null && columnsHeaders.size() > 0);
		initData();
		// 构建无总计统计二维数组
		String[] rwScope = null;
		if (isDesignated) {
			rwScope = new String[columnsHeaders.size() + 1];
		} else {
			rwScope = new String[columnKeys.size() + 1];
		}
		rwScope[0] = title;
		String[][] dataScpe = new String[data.length][data[0].length + 1];

		if (isDesignated) {
			for (int i = 1; i < rwScope.length; i++) {
				if (i < columnsHeaders.size() + 1) {
					rwScope[i] = columnsHeaders.get(i - 1);
				}
			}
		} else {
			for (int i = 1; i < rwScope.length; i++) {
				if (i < columnKeys.size() + 1) {
					rwScope[i] = columnKeys.get(i - 1);
				}
			}
		}
		for (int p = 0; p < dataScpe.length; p++) {
			dataScpe[p][0] = rowKeys.get(p);
			for (int k = 1; k < dataScpe[p].length; k++) {
				dataScpe[p][k] = String.valueOf(data[p][k - 1]);
			}
		}
		// 构建总数总计统计二维数组
		totalizeData(null);

		String[] rowScope = null;
		if (isDesignated) {
			rowScope = new String[columnsHeaders.size() + 2];
		} else {
			rowScope = new String[columnKeys.size() + 2];
		}
		String[][] dataScope = null;

		dataScope = new String[retData.length][retData[0].length + 1];
		rowScope[0] = title;
		if (isDesignated) {
			for (int i = 1; i < rowScope.length - 1; i++) {
				rowScope[i] = columnsHeaders.get(i - 1);
			}
		} else {
			for (int i = 1; i < rowScope.length - 1; i++) {
				rowScope[i] = columnKeys.get(i - 1);
			}
		}
		rowScope[rowScope.length - 1] = "总计";

		for (int j = 0; j < dataScope.length; j++) {
			if (j < rowKeys.size()) {
				dataScope[j][0] = rowKeys.get(j);
			}
			for (int k = 1; k < dataScope[j].length; k++) {
				dataScope[j][k] = String.valueOf(retData[j][k - 1]);
			}
		}
		dataScope[dataScope.length - 1][0] = "总计";

		// 构建百分数总计统计二维数组
		totalizeData("percent");
		String[] pRowScope = null;
		if (isDesignated) {
			pRowScope = new String[columnsHeaders.size() + 2];
		} else {
			pRowScope = new String[columnKeys.size() + 2];
		}

		String[][] pDataScope = null;
		pDataScope = new String[retData.length][retData[0].length + 1];

		pRowScope[0] = title;

		if (isDesignated) {
			for (int i = 1; i < pRowScope.length - 1; i++) {
				pRowScope[i] = columnsHeaders.get(i - 1);
			}
		} else {
			for (int i = 1; i < pRowScope.length - 1; i++) {
				pRowScope[i] = columnKeys.get(i - 1);
			}
		}
		pRowScope[pRowScope.length - 1] = "总计";

		for (int j = 0; j < pDataScope.length; j++) {
			if (j < rowKeys.size()) {
				pDataScope[j][0] = rowKeys.get(j);
			}
			for (int k = 1; k < pDataScope[j].length; k++) {
				pDataScope[j][k] = String.valueOf(retData[j][k - 1]);
			}
		}
		pDataScope[dataScope.length - 1][0] = "总计";

		request.getSession().setAttribute("rowScope", rowScope);
		request.getSession().setAttribute("dataScope", dataScope);

		request.getSession().setAttribute("pRowScope", pRowScope);
		request.getSession().setAttribute("pDataScope", pDataScope);

		request.getSession().setAttribute("rwScope", rwScope);
		request.getSession().setAttribute("dataScpe", dataScpe);
	}

	public void createBarChart(HttpServletRequest request, String title) {
		// 生成分析图
		initData();

		if (title != null && !"".equals(title)) {
			this.generalTitle = title;
		}
		String[] rows = new String[rsBean.getRowNum()];
		for (int i = 0; i < rsBean.getRowNum(); i++) {
			rows[i] = (String) rsBean.getRowKeys().get(i);
			if (rows[i] == null) {
				rows[i] = " ";
			}
		}
		String[] columns = null;
		if (columnsHeaders != null && columnsHeaders.size() != 0) {
			columns = new String[columnsHeaders.size()];
			for (int i = 0; i < columnsHeaders.size(); i++) {
				columns[i] = columnsHeaders.get(i);
			}
		} else {
			columns = new String[rsBean.getColumnNum()];
			for (int i = 0; i < rsBean.getColumnNum(); i++) {
				columns[i] = (String) rsBean.getColumnKeys().get(i);
			}
		}
		// 生成柱状图
		JFreeChart chart = MyChartFactory.getBarChart(data, rows, columns,
				generalTitle);
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		String filename;
		try {
			// 700是图片长度，340是图片高度
			filename = ServletUtilities.saveChartAsPNG(chart, 700, 320, info,
					request.getSession());
			String graphURL = request.getContextPath()
					+ "/servlet/DisplayChart?filename=" + filename;
			request.setAttribute("barReportName", filename);
			request.setAttribute("barReportURI", graphURL);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createPieChart(HttpServletRequest request, String title) {
		initData();

		if (title != null && !"".equals(title)) {
			this.categoryTitle = title;
		}
		// 生成行索引饼状图
		DefaultPieDataset dataset = new DefaultPieDataset();
		String[] rows = new String[rsBean.getRowNum()];
		for (int i = 0; i < rsBean.getRowNum(); i++) {
			rows[i] = (String) rsBean.getRowKeys().get(i);
		}

		String[] columns = null;
		if (columnsHeaders != null && columnsHeaders.size() != 0) {
			columns = new String[columnsHeaders.size()];
			for (int i = 0; i < columnsHeaders.size(); i++) {
				columns[i] = columnsHeaders.get(i);
			}
		} else {
			columns = new String[rsBean.getColumnNum()];
			for (int i = 0; i < rsBean.getColumnNum(); i++) {
				columns[i] = (String) rsBean.getColumnKeys().get(i);
			}
		}
		for (int i = 0; i < rows.length; i++) {
			double value = 0;
			for (int j = 0; j < columns.length; j++) {
				value += data[i][j];
			}

			dataset.setValue(rows[i], value);
		}
		JFreeChart chart = MyChartFactory.getPieChart(dataset, categoryTitle);
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		try {
			// 700是图片长度，330是图片高度
			String filename = ServletUtilities.saveChartAsPNG(chart, 700, 320,
					info, request.getSession());
			String graphURL = request.getContextPath()
					+ "/servlet/DisplayChart?filename=" + filename;
			request.setAttribute("pieRowReportName", filename);
			request.setAttribute("pieRowReportURI", graphURL);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createPieItemChart(HttpServletRequest request, String title) {
		// 生成列索引饼状图
		initData();

		if (title != null && !"".equals(title)) {
			this.itemTitle = title;
		}
		DefaultPieDataset dataset1 = new DefaultPieDataset();
		String[] rows = new String[rsBean.getRowNum()];
		for (int i = 0; i < rsBean.getRowNum(); i++) {
			rows[i] = (String) rsBean.getRowKeys().get(i);
		}

		String[] columns = null;
		if (columnsHeaders != null && columnsHeaders.size() != 0) {
			columns = new String[columnsHeaders.size()];
			for (int i = 0; i < columnsHeaders.size(); i++) {
				columns[i] = columnsHeaders.get(i);
			}
		} else {
			columns = new String[rsBean.getColumnNum()];
			for (int i = 0; i < rsBean.getColumnNum(); i++) {
				columns[i] = (String) rsBean.getColumnKeys().get(i);
			}
		}

		for (int j = 0; j < columns.length; j++) {
			double value = 0;
			for (int i = 0; i < rows.length; i++) {
				value += data[i][j];
			}

			dataset1.setValue(columns[j], value);
		}
		JFreeChart chart = MyChartFactory.getPieChart(dataset1, itemTitle);
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		try {
			// 700是图片长度，330是图片高度
			String filename = ServletUtilities.saveChartAsPNG(chart, 700, 320,
					info, request.getSession());
			String graphURL = request.getContextPath()
					+ "/servlet/DisplayChart?filename=" + filename;
			request.setAttribute("pieColumnReportName", filename);
			request.setAttribute("pieColumnReportURI", graphURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FusionChart createFusionBar(String title) {
		// 生成分析图
		initData();

		if (title != null && !"".equals(title)) {
			this.generalTitle = title;
		}
		String[] rows = new String[rsBean.getRowNum()];
		for (int i = 0; i < rsBean.getRowNum(); i++) {
			rows[i] = (String) rsBean.getRowKeys().get(i);
			if (rows[i] == null) {
				rows[i] = " ";
			}
		}
		String[] columns = null;
		if (columnsHeaders != null && columnsHeaders.size() != 0) {
			columns = new String[columnsHeaders.size()];
			for (int i = 0; i < columnsHeaders.size(); i++) {
				columns[i] = columnsHeaders.get(i);
			}
		} else {
			columns = new String[rsBean.getColumnNum()];
			for (int i = 0; i < rsBean.getColumnNum(); i++) {
				columns[i] = (String) rsBean.getColumnKeys().get(i);
			}
		}
		FusionUnit unit = new FusionUnit();
		FusionChart chart = new FusionChart();
		chart.setCaption(title);
		chart.setXAxisName(statCondBean.getStatTarget());
		chart.setYAxisName(statCondBean.getStatCategory());
		chart.setNumberSuffix("个");
		chart.setRsBean(rsBean);
		for (int i = 0; i < rows.length; i++) {
			// unit.getCategorySet().add(new
			// StatUnit(rows[i],rsBean.getRs().get(i).get(0)));
			for (int j = 0; j < columns.length; j++) {
				unit.getCategorySet().add(
						new StatUnit(rows[i], rsBean.getRs().get(i).get(j)));
				unit.getSet().add(
						new StatUnit(columns[j], rsBean.getRs().get(i).get(j)));
			}
		}
		chart.setGraph(unit);
		return chart;
	}

	public com.wfms.common.function.attribute.fusion3.FusionChart createFusionChart(
			String title) {
		// 生成分析图
		initData();

		if (title != null && !"".equals(title)) {
			this.generalTitle = title;
		}
		String[] rows = new String[rsBean.getRowNum()];
		for (int i = 0; i < rsBean.getRowNum(); i++) {
			rows[i] = (String) rsBean.getRowKeys().get(i);
			if (rows[i] == null) {
				rows[i] = " ";
			}
		}
		String[] columns = null;
		if (columnsHeaders != null && columnsHeaders.size() != 0) {
			columns = new String[columnsHeaders.size()];
			for (int i = 0; i < columnsHeaders.size(); i++) {
				columns[i] = columnsHeaders.get(i);
			}
		} else {
			columns = new String[rsBean.getColumnNum()];
			for (int i = 0; i < rsBean.getColumnNum(); i++) {
				columns[i] = (String) rsBean.getColumnKeys().get(i);
			}
		}
		com.wfms.common.function.attribute.fusion3.FusionChart fusionChart = new com.wfms.common.function.attribute.fusion3.FusionChart();
		Chart chart = new Chart();
		chart.setCaption(title);
		chart.setXaxisname(statCondBean.getStatTarget());
		chart.setYaxisname(statCondBean.getStatCategory());
		chart.setNumberSuffix("个");

		List<DataSet> dataSetList = new ArrayList<DataSet>();
		List<CategoryUnit> categorys = new ArrayList<CategoryUnit>();
		List<Data> dataList = new ArrayList<Data>();

		List<Data> categoryDataList = new ArrayList<Data>();

		for (int i = 0; i < rows.length; i++) {
			categorys.add(new CategoryUnit(new Category(rows[i])));

			DataSet dataSet = null;
			Data data = null;
			List<Data> categoryList = new ArrayList<Data>();
			for (int j = 0; j < columns.length; j++) {
				dataSet = new DataSet();
				dataSet.setSeriesname(columns[j]);
				Data categoryData = new Data();
				categoryData.setRownum(i + "");
				categoryData.setColumnnum(j + "");
				categoryData.setValue(rsBean.getRs().get(i).get(j));
				categoryList.add(categoryData);

				data = new Data();
				data.setRownum(i + "");
				data.setColumnnum(j + "");
				data.setLabel(rows[i]);
				data.setValue(rsBean.getRs().get(i).get(j));
				dataList.add(data);

				data = new Data();
				data.setRownum(i + "");
				data.setColumnnum(j + "");
				data.setLabel(columns[j]);
				data.setValue(rsBean.getRs().get(i).get(j));
				categoryDataList.add(data);
			}
			dataSet.setData(categoryList);
			dataSetList.add(dataSet);
		}
		fusionChart.setCategories(categorys);
		fusionChart.setData(dataList);
		fusionChart.setCategoryData(categoryDataList);
		fusionChart.setDataset(dataSetList);
		return fusionChart;
	}

	public List<StatDefine> getAllStatDefine() {
		return statDefineDao.getAllStatDefine();
	}

	public StatDefine getStatDefine(String tableName) {
		return statDefineDao.getStatDefine(tableName);
	}

	public StatResultSetBean getRsBean() {
		return rsBean;
	}

	public void setRsBean(StatResultSetBean rsBean) {
		this.rsBean = rsBean;
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		double total = 0;
		double[][] data = { { 10, 23, 43 }, { 20, 38, 12 }, { 45, 43, 27 },
				{ 1, 1, 3 } };
		double[][] retData = new double[data.length + 1][data[0].length + 1];
		// System.arraycopy(data, 0, retData, 0, data.length);

		double[] rowTotal = new double[data[0].length];
		for (int i = 0; i < data.length; i++) {
			retData[i] = new double[data[i].length + 1];
			double columnTotal = 0;
			for (int j = 0; j < data[i].length; j++) {
				retData[i][j] = data[i][j];
				columnTotal += data[i][j];
				rowTotal[j] += data[i][j];
			}
			retData[i][data[i].length] = columnTotal;
		}
		for (int k = 0; k < data[0].length; k++) {
			retData[retData.length - 1][k] = rowTotal[k];
			total += retData[retData.length - 1][k];
		}
		retData[retData.length - 1][retData[0].length - 1] = total;
		/*
		 * retData=new StatUtil().totalizeData(data,"percent");
		 */for (double[] d : retData) {
			for (double d1 : d) {
				System.out.println(d1 + " ");
			}
			System.out.println("\n");
		}
	}
}

class ChartCreateException extends Exception {
	private int lineNumber = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChartCreateException() {
		super();
	}

	public ChartCreateException(String msg) {
		super(msg);
	}

	public ChartCreateException(String msg, int lineNumber) {
		super(msg);
		setLineNumber(lineNumber);
	}

	@Override
	public String toString() {
		return "ChartCreateExcpetion:can not create chart from the provide data at line:"
				+ lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
