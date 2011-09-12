package com.wfms.common.web.views;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.BoldStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.wfms.common.attribute.ExportBean;
import com.wfms.common.util.DateUtil;

public class JExcelView extends AbstractJExcelView
{
  public JExcelView()
  {
    setContentType("application/vnd.ms-excel;charset=utf-8");
  }

  protected void buildExcelDocument(Map<String, Object> model, WritableWorkbook wwb, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    buildJExcelSheet(wwb, model);
    String paramName = request.getParameter("fileName");
    if (StringUtils.isEmpty(paramName))
    {
      paramName = DateUtil.getFormatTimeString();
    }
    String fileName = new String(
      new String((paramName + ".xls").getBytes(), "ISO-8859-1"));
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-Disposition", "attachment; filename=" + 
      fileName);
  }

  protected void buildJExcelSheet(WritableWorkbook wwb, Map model)
    throws Exception
  {
    List exportBeanList = 
      (List)model
      .get("exportBeanList");

    WritableSheet ws = null;
    if ((exportBeanList == null) || (exportBeanList.size() == 0)) {
      return;
    }
    for (int i = 0; i < exportBeanList.size(); ++i) {
      ExportBean exportBean = (ExportBean)exportBeanList.get(i);
      int sheetCount = wwb.getNumberOfSheets();
      String sheetName = exportBean.getSheetName();
      if ((sheetName != null) && (!"".equals(sheetName)))
        ws = wwb.createSheet(sheetName, sheetCount);
      else {
        ws = wwb.createSheet("sheet" + sheetCount, sheetCount);
      }

      try
      {
        setDefaultHighWidth(ws);
        ws.mergeCells(0, 0, exportBean.getHeader().size() - 1, 0);

        ws.addCell(
          new Label(0, 0, exportBean.getTableTitle(), 
          getTitleFormat()));

        for (int j = 0; j < exportBean.getHeader().size(); ++j) {
          ws.addCell(
            new Label(j, 1, 
            (String)exportBean.getHeader().get(j), getHeaderFormat()));
        }

        for (int k = 0; k < exportBean.getTableContent().size(); ++k)
          if (k % 2 == 0) {
            String[] r = (String[])exportBean.getTableContent().get(k);
            for (int m = 0; m < r.length; ++m)
              ws.addCell(
                new Label(m, k + 2, r[m], 
                getOddFormat()));
          }
          else {
            String[] r = (String[])exportBean.getTableContent().get(k);
            for (int n = 0; n < r.length; ++n)
              ws.addCell(
                new Label(n, k + 2, r[n], 
                getEvenFormat()));
          }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  public static WritableFont getTitleFont()
  {
    WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 16, 
      WritableFont.BOLD, false, 
      UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
    return titleFont;
  }

  public static WritableFont getHeaderFont()
  {
    WritableFont fieldFont = new WritableFont(WritableFont.ARIAL, 
      12, WritableFont.BOLD, false, 
      UnderlineStyle.NO_UNDERLINE, Colour.GREEN);
    return fieldFont;
  }

  public static WritableFont getContentFont()
  {
    WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 
      11, WritableFont.NO_BOLD, false, 
      UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
    return dataFont;
  }

  public static WritableCellFormat getTitleFormat()
  {
    WritableCellFormat titleFormat = new WritableCellFormat(getTitleFont());
    try {
      titleFormat.setAlignment(Alignment.CENTRE);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    return titleFormat;
  }

  public static WritableCellFormat getHeaderFormat()
  {
    WritableCellFormat fieldFormat = new WritableCellFormat(
      getHeaderFont());
    try {
      fieldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    try {
      fieldFormat.setAlignment(Alignment.CENTRE);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    return fieldFormat;
  }

  public static WritableCellFormat getContentFormat()
  {
    return new WritableCellFormat(getContentFont());
  }

  public static WritableCellFormat getOddFormat()
  {
    WritableCellFormat oddCellFormat = new WritableCellFormat(
      getContentFont());
    try {
      oddCellFormat.setBackground(Colour.VERY_LIGHT_YELLOW);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    try {
      oddCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    return oddCellFormat;
  }

  public static WritableCellFormat getEvenFormat()
  {
    WritableCellFormat evenCellFormat = new WritableCellFormat(
      getContentFont());
    try {
      evenCellFormat.setBackground(Colour.ICE_BLUE);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    try {
      evenCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    } catch (WriteException e) {
      e.printStackTrace();
    }
    return evenCellFormat;
  }

  public static WritableFont getWritableFont(WritableFont.FontName fontName, int fontSize, BoldStyle boldStyle, boolean it, UnderlineStyle under, Colour colour)
  {
    WritableFont dataFont = new WritableFont(fontName, fontSize, 
      WritableFont.NO_BOLD, it, under, colour);
    return dataFont;
  }

  public static WritableCellFormat getBorderCellStyle(WritableFont font, Colour backColor, Border border, BorderLineStyle bls)
  {
    WritableCellFormat cellStyle = new WritableCellFormat(font);
    try {
      cellStyle.setAlignment(Alignment.CENTRE);
      cellStyle.setBackground(backColor);
      cellStyle.setBorder(border, bls);
    } catch (WriteException e) {
      e.printStackTrace();
    }

    return cellStyle;
  }

  public static void setDefaultHighWidth(WritableSheet sheet)
  {
    sheet.getSettings().setDefaultRowHeight(400);
    try {
      sheet.setRowView(0, 500);
    } catch (RowsExceededException e) {
      e.printStackTrace();
    }
  }
}