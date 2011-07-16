<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'date_back.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
		Ext.onReady(function(){
		
		initDataTbar([]);
			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("系统管理 >> 数据库备份 ","备份数据");
			var waitMsg = Ext.MessageBox.wait('提示信息','数据加载中，请稍候...');
			
		});
	</script>
  </head>
  <%String restr = (String)request.getAttribute("restr"); %>
  <body>
    <%=restr %>
  </body>
</html>
