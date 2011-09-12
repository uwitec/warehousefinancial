package com.wfms.common.web.views;

import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

public class TextView extends AbstractView
{
  public TextView()
  {
    setContentType("text/html");
  }

  protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    response.setCharacterEncoding("UTF-8");
    
    Object object = model.get("text");

    if (object != null)
      response.getWriter().print(object);
  }
}