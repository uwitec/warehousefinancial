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
body {
	background-color: #ffffff;
	
}

#main {
	text-align: center;
	height: 512px;
	background: url('<c:url value="/imgs/login-bg.jpg"/>') no-repeat top
		center;
}

#input {
	width: 303px;
	height: 45px;
	margin-top: 260px;
	margin-left: 500px;
}

.input_hidden {
	position: absolute;
	top: -1000px;
}

#button {
	position: relative;
	top: 8px;
	left: 260px;
	width: 303px;
	height: 95px;
}

.lu {
	border: 1px solid #347196;
	float: left;
	width: 76px;
	height: 27px;
	margin: 1px 3px 3px 1px;
	background-image: url('<c:url value="/imgs/login-btn.gif"/>');
	cursor: hand;
}

.ld {
	border: 1px solid #347196;
	float: left;
	width: 76px;
	height: 27px;
	margin: 2px 2px 2px 2px;
	background-image: url('<c:url value="/imgs/login-btn.gif"/>');
	cursor: hand;
}

.ru {
	border: 1px solid #347196;
	float: left;
	width: 76px;
	height: 27px;
	margin: 1px 3px 3px 1px;
	background-image: url('<c:url value="/imgs/reset-btn.gif"/>');
	cursor: hand;
}

.rd {
	border: 1px solid #347196;
	float: left;
	width: 76px;
	height: 27px;
	margin: 2px 2px 2px 2px;
	background-image: url('<c:url value="/imgs/reset-btn.gif"/>');
	cursor: hand;
}
</style>
</head>
<body>
	<div id="main">
		<div id="input">
			<ul>
				<li class="input_td" id="login_yhm"></li>
				<li class="input_td" id="login_mm"></li>
				<li class="input_hidden" id="login_yhz"></li>
			</ul>
		</div>
		<div id="button">
			<div class="lu" onmousedown="this.className='ld';"
				onmouseup="this.className='lu';" id="login_btn"></div>
			<div class="ru" onmousedown="this.className='rd';"
				onmouseup="this.className='ru';" id="reset_btn"></div>
		</div>
	</div>

</body>
</html>
