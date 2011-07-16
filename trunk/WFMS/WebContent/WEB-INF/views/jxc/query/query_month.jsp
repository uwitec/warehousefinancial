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
	String[] js = (String[])list.get(0);
	String[] fy = (String[])list.get(1);
	List ckhz = (List)list.get(2);
	List rkhz = (List)list.get(3);
	String stime = (String)request.getAttribute("stime");
	String etime = (String)request.getAttribute("etime");
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
    		<td>从&nbsp;&nbsp;<%=stime %>&nbsp;&nbsp;到&nbsp;<%=etime %>，公司费用统计如下：</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    	<tr height="5px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr> -->
    	<tr height="25px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td><%=fy[0] %>：<%=fy[1] %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=fy[2] %>：<%=fy[3] %></td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    	<tr height="5px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    	<tr height="15px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>出库统计</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    	<tr>
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td><table width="600px" border="1px" class="listTable">
    			<tr>
    				<td>出库类型</td>
    				<td>产品名称</td>
    				<td>产品规格</td>
    				<td>单位</td>
    				<td>数量</td>
    				<td>金额(元)</td>
    			</tr>
    			<%
    			for(int i=0;i<ckhz.size();i++){
    				Object[] obj = (Object[])ckhz.get(i);
    				%>
    				<tr>
    					<%for(int j=0;j<obj.length;j++){ 
    					if(j==0){
    						if(String.valueOf(obj[j]).equals("0")){
    							obj[j] = "报废";
    						}else if(String.valueOf(obj[j]).equals("1")){
    							obj[j] = "借用";
    						}else if(String.valueOf(obj[j]).equals("2")){
    							obj[j] = "消耗";
    						}else if(String.valueOf(obj[j]).equals("3")){
    							obj[j] = "盘存";
    						}
    					}
    					%>
    						<td><%=obj[j] == null ? "0" : obj[j] %></td>
    					<%}%>
    				</tr>
    				<%
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
    	<tr height="15px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>入库统计</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    	<tr>
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td><table width="600px" border="1px" class="listTable">
    			<tr>
    				<td>入库类型</td>
    				<td>产品名称</td>
    				<td>产品规格</td>
    				<td>单位</td>
    				<td>数量</td>
    				<td>金额(元)</td>
    			</tr>
    			<%
    			for(int i=0;i<rkhz.size();i++){
    				Object[] obj = (Object[])rkhz.get(i);
    				%>
    				<tr>
    					<%for(int j=0;j<obj.length;j++){
    					if(j==0){
    						if(String.valueOf(obj[j]).equals("0")){
    							obj[j] = "转仓库";
    						}else if(String.valueOf(obj[j]).equals("1")){
    							obj[j] = "赠送";
    						}else if(String.valueOf(obj[j]).equals("2")){
    							obj[j] = "购买";
    						}else if(String.valueOf(obj[j]).equals("3")){
    							obj[j] = "归还";
    						}
    					}
    					 %>
    						<td><%=obj[j] == null ? "0" : obj[j] %></td>
    					<%}%>
    				</tr>
    				<%
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
    	<tr height="25px">
    		<td></td>
    		<td>月末总计：收入：<%=js[0] %>&nbsp;&nbsp;入库支出：<%=js[1] %>&nbsp;&nbsp;费用支出：<%=js[2] %>&nbsp;&nbsp;结余：<%=js[3] %></td>
    		<td></td>
    	</tr>
    	<tr height="5px">
    		<td width="5%">&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;</td>
    		<td width="5%">&nbsp;&nbsp;</td>
    	</tr>
    </table>
  </body>
</html>
