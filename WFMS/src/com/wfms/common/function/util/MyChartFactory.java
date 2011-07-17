package com.wfms.common.function.util;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：MyChartFactory
 *  <dd> 类描述：Chart生成类
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 2:03:12 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see MyChartFactory
 * @version 1.0
 *
 */
public class MyChartFactory {
	public static JFreeChart getBarChart(double[][] data,
			String[] rowKeys,
			String[] columnKeys,
			String title
			){
		StandardChartTheme standardChartTheme = new StandardChartTheme("name");//这里的"name"参数；是什么意思我也不知道，反正这样可以用 
		standardChartTheme.setLargeFont(new Font("楷体",Font.BOLD, 8));//可以改变轴向的字体 
		standardChartTheme.setRegularFont(new Font("楷体",Font.BOLD, 10));//可以改变图例的字体 
		standardChartTheme.setExtraLargeFont(new Font("宋体",Font.BOLD, 18));//可以改变图标的标题字体 
		ChartFactory.setChartTheme(standardChartTheme);//设置主题
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);
		JFreeChart chart = ChartFactory.createBarChart3D(title, null,
				null, dataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = chart.getCategoryPlot();
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		// 显示每个柱的数值，并修改该数值的字体属性
		BarRenderer3D renderer = new BarRenderer3D();
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
		// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		// 设置每个地区所包含的平行柱的之间距离
		// renderer.setItemMargin(0.3);
		plot.setRenderer(renderer);
		// 设置地区、销量的显示位置
		// 将下方的“肉类”放到上方
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// 将默认放在左边的“销量”放到右方
		//plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		
		//设置纵轴刻度
		return chart;
	}
	
	public static JFreeChart getPieChart(DefaultPieDataset data,
			String title
			){
		StandardChartTheme standardChartTheme = new StandardChartTheme("name");//这里的"name"参数；是什么意思我也不知道，反正这样可以用 
		standardChartTheme.setLargeFont(new Font("楷体",Font.BOLD, 12));//可以改变轴向的字体 
		standardChartTheme.setRegularFont(new Font("宋体",Font.BOLD, 12));//可以改变图例的字体 
		standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD, 12));//可以改变图标的标题字体 
		ChartFactory.setChartTheme(standardChartTheme);//设置主题

		PiePlot plot = new PiePlot(data);
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,
				plot, true);
		chart.setBackgroundPaint(java.awt.Color.white); // 可选，设置图片背景色
		chart.setTitle(title); 
		
		PiePlot pieplot = (PiePlot) chart.getPlot(); //通过JFreeChart 对象获得
		//pieplot.setNoDataMessage("无数据可供显示！"); // 没有数据的时候显示的内容
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
		("{0}: ({2})"), NumberFormat.getNumberInstance(),
		new DecimalFormat("0.00%")));


		// 指定图片的透明度(0.0-1.0)    
		 plot.setForegroundAlpha(1.0f);    
		// 指定显示的饼图上圆形(false)还椭圆形(true)    
		plot.setCircular(true); 
		return chart;
	}
}
