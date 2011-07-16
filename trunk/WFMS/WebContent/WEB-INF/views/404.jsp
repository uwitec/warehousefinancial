<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
</head>

<p>
    <fmt:message key="404.message">
        <fmt:param><c:url value="/mainMenu.html"/></fmt:param>
    </fmt:message>
	访问源:<%=request.getHeader("referer")%><%System.out.println(request.getHeader("referer"));%>
</p>
<p style="text-align: center; margin-top: 20px">
	<img src="<c:url value='/imgs/bg_yeshj.gif'/>"/>
    <a href="http://www.xunersoft.com"
        title="Xuner technology .Inc, click to Zoom In">
</p>
