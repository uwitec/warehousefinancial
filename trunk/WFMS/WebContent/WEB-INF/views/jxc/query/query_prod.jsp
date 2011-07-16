<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%
	List list = (List)request.getAttribute("list");
	String stime = (String)request.getAttribute("stime");
	String etime = (String)request.getAttribute("etime");
	String[] title = (String[])request.getAttribute("title");
	 %>
	 
	 <style type="text/css">
		.listTable {
			border-collapse: collapse;
			border-spacing: 3px;
			text-align: left;
			border:1px;
		}
		.td {
		border:1px solid #808080;
		padding:3px;
		}
	</style>
  </head>
  
  <body>
    <table>
    	<tr height="5px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    	<!-- <tr height="25px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>从&nbsp;&nbsp;<%=stime %>&nbsp;&nbsp;到&nbsp;<%=etime %>，产品统计如下：</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr> -->
    	<tr>
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td><table width="1000px" border="1px" class="listTable">
    			<tr>
    				<%for(int i=1;i<title.length;i++){ %>
    				<td><%=title[i] %></td>
    				<%} %>
    			</tr>
    			<%for(int i=0;i<list.size();i++){ %>
    			<tr>
    				<%Object[] ps = (Object[])list.get(i);
    				for(int j=1;j<ps.length;j++){ %>
    				<td><%=ps[j] == null ? "0" : ps[j] %></td>
    				<%}%>
    			</tr>
    			<%}%>
    		</table></td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    	<tr height="5px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    </table>
  </body>
</html>
