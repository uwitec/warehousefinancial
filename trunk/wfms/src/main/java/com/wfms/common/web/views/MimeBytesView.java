package com.wfms.common.web.views;

import java.io.OutputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MimeBytesView extends AbstractMimeView
{
  protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String fileName = (String)model.get("fileName");
    byte[] fileData = (byte[])model.get("fileData");

    if ((fileName == null) || (fileData == null))
      return;
    buildHeader(response, fileName, fileData.length);

    OutputStream out = response.getOutputStream();
    out.write(fileData);
    out.flush();
  }
}