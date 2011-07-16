<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>用户登录</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="SHORTCUT ICON" href="<c:url value='/imgs/small-logo.ico'/>">
  	<script type="text/javascript" src="<c:url value='/js/login.js'/>"></script>
	<script type="text/javascript">
	</script>
	<style type="text/css">
		body{
			background-color:#132d52;
		}
		#main_table{
			width:1024px;
			height:600px;
			background-image:url('<c:url value="/imgs/login-bg.gif"/>');
		}
		#login_input{
			width:303px;
			height:95px;
			margin-top:76px;
			margin-left:400px;
		}
		.lu{
			border:1px solid #347196;
			width:76px;
			height:27px;
			margin:1px 3px 3px 1px;
			background-image:url('<c:url value="/imgs/login-btn.gif"/>');
			cursor:hand;
		}
		.ld{
			border:1px solid #347196;
			width:76px;
			height:27px;
			margin:2px 2px 2px 2px;
			background-image:url('<c:url value="/imgs/login-btn.gif"/>');
			cursor:hand;
		}
		.ru{
			border:1px solid #347196;
			width:76px;
			height:27px;
			margin:1px 3px 3px 1px;
			background-image:url('<c:url value="/imgs/reset-btn.gif"/>');
			cursor:hand;
		}
		.rd{
			border:1px solid #347196;
			width:76px;
			height:27px;
			margin:2px 2px 2px 2px;
			background-image:url('<c:url value="/imgs/reset-btn.gif"/>');
			cursor:hand;
		}
	</style>
  </head>
  <body>
  <table cellpadding="0" cellspacing="0" border="0" width="100%" height='100%' >
  	<tr>
  		<td align="center" valign="middle">
			<table cellpadding="0" cellspacing="0" border="0" id="main_table">
				<tr>
					<td>
						<table id="login_input" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td class="input_td" id="login_yhm"></td>
										</tr>
										<tr>
											<td class="input_td" id="login_mm"></td>
										</tr>
										<!-- <tr>
											<td class="input_td" id="login_yhz"></td>
										</tr> -->
									</table>
								</td>
								<td width="100" style="padding:8px 0px 8px 0px;">
									<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="lu" onmousedown="this.className='ld';" onmouseup="this.className='lu';" id="login_btn"></div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="ru" onmousedown="this.className='rd';" onmouseup="this.className='ru';" id="reset_btn"></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
  	</tr>
  </table>
  </body>
</html>
