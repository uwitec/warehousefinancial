<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <%
  Object ps[] = (Object[])request.getAttribute("dlyh");
   %>
		<style type="text/css">
			a{
				color:#1d376a;
				text-decoration:none;
			}
			
			.text{
				font-size:12px;
				font-family:黑体;
			}
			#userInfo_left{
				margin:0;
				padding:5px 0px 0px 5px;					
				float:left;		
			}
			#userInfo_right{
				margin:0;
				padding-top:5px;	
				float:left;			
			}
			
			#userInfo_right ul{
				margin:0;
				padding:0;
				list-style-type:none;
			}
			#userInfo_right ul li{
				padding-left:10px;
				padding-top:3px;
				height:19px;
				font-size:13px;
				//border-bottom:1px dashed #ccc; //绘出下边界虚线
			}
			
			#userInfo_line{
				border:0;
				border-bottom:1px dashed #ccc;
				height:5px;
				margin:0;
				padding:0;
				margin-bottom:5px;
				font-size:1px;
				line-height:0px;
				clear:both;		
			}
			
			#userInfo_bottom{
				margin:0;
				padding-top:2px;	
				text-align:center;		
			}
		</style>
		
		<div id="userInfo_left">
			<!-- <img src="imgs/default-zp.jpg" width="100" height="110"> -->
			<img src="<%=ps[0] %>" width="100" height="110">
		</div>
		<div id="userInfo_right">
			<ul>
				<li>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<%=ps[1] %></li>
				<li>用&nbsp;&nbsp;户&nbsp;&nbsp;名：<%=ps[2] %></li>
				<li>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：<%=ps[3] %></li>
				<li>办公电话：<%=ps[4] %></li>
				<li>个人电话：<%=ps[5] %></li>
		</div>
		<div id="userInfo_line"></div>
		<!-- <div id="userInfo_bottom">
			<table width="100%">
			<tr>
				<td align="center"><a href="#">个人信息</a></td>
				<td align="center"><a href="<%=basePath %>/system/yhglAction.do?method=initYhmmwh&id=42025" target="_blank">安全中心</a></td>
				</tr>
			</table>
		</div> -->
	</body>
</html>
