<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>�û���¼ҳ��</title>
    
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
				font-family:����;
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
				//border-bottom:1px dashed #ccc; //����±߽�����
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
				<li>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����<%=ps[1] %></li>
				<li>��&nbsp;&nbsp;��&nbsp;&nbsp;����<%=ps[2] %></li>
				<li>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�䣺<%=ps[3] %></li>
				<li>�칫�绰��<%=ps[4] %></li>
				<li>���˵绰��<%=ps[5] %></li>
		</div>
		<div id="userInfo_line"></div>
		<!-- <div id="userInfo_bottom">
			<table width="100%">
			<tr>
				<td align="center"><a href="#">������Ϣ</a></td>
				<td align="center"><a href="<%=basePath %>/system/yhglAction.do?method=initYhmmwh&id=42025" target="_blank">��ȫ����</a></td>
				</tr>
			</table>
		</div> -->
	</body>
</html>
