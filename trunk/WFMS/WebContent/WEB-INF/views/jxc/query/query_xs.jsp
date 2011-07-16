<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    	<tr>
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td><table width="600px" border="1px" class="listTable">
    			<tr>
    				<td>员工名字</td>
    				<td>销售额(元)</td>
    				<td width="1px">&nbsp;</td>
    				<td>员工名字</td>
    				<td>销售额(元)</td>
    			</tr>
    			<%
    			for(int i=0;i<list.size();i++){
    				Object[] obj = (Object[])list.get(i);
    				if(i%2 == 0){
    					%>
    				<tr>
    					<%if(i== list.size()-1){
    						for(int j=0;j<obj.length;j++){
    						%>
    						<td><%=obj[j] == null ? "0" : obj[j] %></td>
    						<%}%>
    						<td width="1px">&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>0</td>
    					</tr>
    					<%}else{
    						for(int j=0;j<obj.length;j++){ 
    					%>
    						<td><%=obj[j] == null ? "0" : obj[j] %></td>
    					<%}%>
    						<td width="1px">&nbsp;</td>
    					<%} %>
    				<%}else{
    					for(int j=0;j<obj.length;j++){
    						%>
    						<td><%=obj[j] == null ? "0" : obj[j] %></td>
    						<%}%>
    					</tr>
    					<%
    				}
    			}
    			 %>
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
