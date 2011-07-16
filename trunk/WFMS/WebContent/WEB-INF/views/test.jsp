<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<title>注册提示</title>
<%
String re = (String)request.getAttribute("restr");
 %>
</head>
<body>
    <%=re %>
</body>
</html>
