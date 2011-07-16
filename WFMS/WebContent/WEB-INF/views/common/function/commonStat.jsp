<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String referer = request.getHeader("referer");
	if(referer!=null && referer.indexOf("dataStatAction.do")!=-1){
		response.setContentType("application/msexcel;charset=GBK");
		response.setHeader("Content-disposition","inline; filename=report.xls");
 		out.println(request.getParameter("excelData"));
 		out.flush();
 		out.close();
 		return;
 	}
 %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String barReportName = (String) request
			.getAttribute("barReportName");
	String barReportURI = (String) request.getAttribute("barReportURI");
	String pieRowReportName = (String) request
			.getAttribute("pieRowReportName");
	String pieRowReportURI = (String) request
			.getAttribute("pieRowReportURI");
	String pieColumnReportName = (String) request
			.getAttribute("pieColumnReportName");
	String pieColumnReportURI = (String) request
			.getAttribute("pieColumnReportURI");
	 
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>commonStat.jsp</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="/SMS/css/editTable.css">
	<STYLE type=text/css>
BODY {
	padding:0;
	margin:0;
	FONT-SIZE: 14px;
	FONT-FAMILY: "宋体";
}

OL LI {
	MARGIN: 8px;
}

#con {
	FONT-SIZE: 12px;
	MARGIN: 0px auto;
	WIDTH: 100%;
}

#tags {
	PADDING-RIGHT: 0px;
	PADDING-LEFT: 0px;
	PADDING-BOTTOM: 0px;
	MARGIN: 0px 0px 0px 0px;
	WIDTH: 740px;
	PADDING-TOP: 0px;
	HEIGHT: 23px;
}

#tags LI {
	BACKGROUND: url(/SMS/images/tagleft.gif) no-repeat left bottom;
	FLOAT: left;
	MARGIN-RIGHT: 1px;
	LIST-STYLE-TYPE: none;
	HEIGHT: 23px;
}

#tags LI A {
	PADDING-RIGHT: 10px;
	PADDING-LEFT: 10px;
	BACKGROUND: url(/SMS/images/tagright.gif) no-repeat right bottom;
	FLOAT: left;
	PADDING-BOTTOM: 0px;
	COLOR: #999;
	LINE-HEIGHT: 23px;
	PADDING-TOP: 0px;
	HEIGHT: 23px;
	TEXT-DECORATION: none;
}

#tags LI.emptyTag {
	BACKGROUND: none transparent scroll repeat 0% 0%;
	WIDTH: 4px;
}

#tags LI.selectTag {
	BACKGROUND-POSITION: left top;
	MARGIN-BOTTOM: -2px;
	POSITION: relative;
	HEIGHT: 25px;
}

#tags LI.selectTag A {
	BACKGROUND-POSITION: right top;
	COLOR: #000;
	LINE-HEIGHT: 25px;
	HEIGHT: 25px;
}

#tagContent {
	/*BORDER-RIGHT: #aecbd4 1px solid;*/
	PADDING-RIGHT: 1px;
	BORDER-TOP: #aecbd4 1px solid;
	PADDING-LEFT: 1px;
	PADDING-BOTTOM: 1px;
	/*BORDER-LEFT: #aecbd4 1px solid;*/
	PADDING-TOP: 1px;
	BORDER-BOTTOM: #aecbd4 1px solid;
	BACKGROUND-COLOR: #fff;
}

.tagContent {
	PADDING-RIGHT: 10px;
	DISPLAY: none;
	PADDING-LEFT: 10px;
	BACKGROUND: url(/SMS/images/bg.gif) repeat-x;
	PADDING-BOTTOM: 10px;
	WIDTH: 610px;
	COLOR: #474747;
	PADDING-TOP: 10px;
	HEIGHT: 362px;
	text-align:center;
}

#tagContent DIV.selectTag {
	DISPLAY: block;
}

.STYLE1 {
	font-size: 12px;
	font-weight: bold;
}

.STYLE2 {
	font-size: 12px;
}

.tbBottom {
	border-bottom: 1px solid #EBE9D7;
}

.tbTop {
	border-top: 3px solid #FFFFFF;
}

.tbTop2 {
	border-top: 1px solid #FFFFFF;
}

.tbRight {
	border-right: 3px solid #FFFFFF;
}

.tbRight2 {
	border-right: 1px solid #FFFFFF;
}

.thRight {
	border-right: 1px solid #D6DBE8;
}

#MrJin_a {
	font-size: 12px;
	background: #fff;
	margin: 45px;
	width: 480px;
	border-collapse: collapse;
	text-align: left;
}

#MrJin_a th {
	font-size: 14px;
	font-weight: normal;
	color: #039;
	padding: 10px 8px;
	border-bottom: 2px solid #6678b1;
}

#MrJin_a td {
	color: #666;
	padding: 9px 8px 0px 8px;
}

#MrJin_a tbody tr:hover td {
	color: #03c;
}

.to-top {
	display: block;
	overflow: hidden;
	width: 0px;
	height: 0px;
	border: 6px solid #ccc;
	border-color: #ccc #fff;
	border-width: 0 6px 6px 6px;
}

.out {
	border-top: 40px #D6D3D6 solid; /*上边框宽度等于表格第一行行高*/
	width: 0px; /*让容器宽度为0*/
	height: 0px; /*让容器高度为0*/
	border-left: 80px #BDBABD solid; /*左边框宽度等于表格第一行第一格宽度*/
	position: relative; /*让里面的两个子容器绝对定位*/ b { font-style : normal;
	display: block;
	position: absolute;
	top: -40px;
	left: -40px;
	width: 35px;
}

em {
	font-style: normal;
	display: block;
	position: absolute;
	top: -25px;
	left: -70px;
	width: 55x;
}
</STYLE>
	<SCRIPT type=text/javascript>
function selectTag(showContent,selfObj){
	// 操作标签
	var tag = document.getElementById("tags").getElementsByTagName("li");
	var taglength = tag.length;
	for(i=0; i<taglength; i++){
		tag[i].className = "";
	}
	if(selfObj!=null && selfObj!="undefined")
		selfObj.parentNode.className = "selectTag";
	
	// 操作内容
	for(i=0; j=document.getElementById("tagContent"+(i+1)); i++){
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";
	var tag4Display=document.getElementById("tagContent4").style.display;
	var tag5Display=document.getElementById("tagContent5").style.display;
	var tag6Display=document.getElementById("tagContent6").style.display;
	if(tag4Display=="block" || tag5Display=="block" || tag6Display=="block")
	{
		document.getElementById("li_changeView").style.display="block";	
	}
	else
	{
		document.getElementById("li_changeView").style.display="none";
	}
}

function showTag(showContent){
	document.getElementById('tagContent'+showContent).style.display = "block";
}

function changeTotalizeView(obj){
	var selectedTag=document.getElementById('link_tabstat');
	if(obj.value=="normal")
	{
		selectTag('tagContent4',selectedTag);
	}
	else if(obj.value=="count")
	{
		selectTag('tagContent5',selectedTag);
	}
	else if(obj.value=="percent")
	{
		selectTag('tagContent6',selectedTag);
	}
}
function expToExcel(){

	var tabs=document.getElementsByTagName("table");

	var tag4Display=document.getElementById("tagContent4").style.display;
	var tag5Display=document.getElementById("tagContent5").style.display;
	var tag6Display=document.getElementById("tagContent6").style.display;

	var elTable;
	
	if(tag4Display=="block")
	{
		elTable = document.all.bodyTable[0]; 
	}
	else if(tag5Display=="block")
	{
		elTable = document.all.bodyTable[1]; 
	}
	else if(tag6Display=="block")
	{
		elTable = document.all.bodyTable[2]; 
	}
  //var w=window.open("about:blank","Excel","widht=0,height=0");   
  //w.document.write(elTable.outerHTML);   
  //w.document.execCommand('Saveas',false,'C:\\log.xls'); 
  	document.getElementById("excelData").value=elTable.outerHTML;
  	document.forms[0].target="_self";
  	document.forms[0].submit();
}
</SCRIPT>
</head>
<body style="overflow-x:scroll;" onLoad="showTag('4');" bgcolor="#E7F3F8">
	<form action="commonStat.jsp" method="post">
	<input type="hidden" value="" name="excelData" id="excelData"/>
	<DIV id="con" height="100%" align="left" style="margin:0;padding:0;">
		<UL id=tags>
			<li>
				<A onClick="selectTag('tagContent1',this)" href="javascript:void(0)">按对象统计</A>
			</li>
			<li>
				<A onClick="selectTag('tagContent2',this)" href="javascript:void(0)">按分类统计</A>
			</li>
			<li>
				<A onClick="selectTag('tagContent3',this)" href="javascript:void(0)">综合统计</A>
			</li>
			<li class="selectTag">
				<A id="link_tabstat" onClick="selectTag('tagContent4',this)" href="javascript:void(0)">统计表格</A>
			</li>
			<!--  <span  id="li_changeView" style="display:block;background-img:url('');">
				表格类型：
				<input type="radio" style="" name="tabletype" value="normal" checked="checked" onclick="changeTotalizeView(this);" />无总计
				<input type="radio" style="" name="tabletype" value="count" onclick="changeTotalizeView(this);"/>总数总计
				<input type="radio" style="" name="tabletype" value="percent" onclick="changeTotalizeView(this);"/>百分数总计
			</span>-->
			<li style="background-color:#EDFBFF;" id="li_changeView">
				<span style="background-color:#EDFBFF;">
				<input style="background-color:#EDFBFF;" type="radio" style="" name="tabletype" value="normal" checked="checked" onclick="changeTotalizeView(this);"> 无总计</input>
				<input style="background-color:#EDFBFF;" type="radio" style="" name="tabletype" value="count" onclick="changeTotalizeView(this);">总数总计</input>
				<input style="background-color:#EDFBFF;" type="radio" style="" name="tabletype" value="percent" onclick="changeTotalizeView(this);">百分数总计</input>
				<img style="margin-top:4px;cursor:hand;" onclick="expToExcel();" alt="导出到excel" src=contextPath+"/imgs/excel.gif" valign="buttom"/>
				</span>
			</li>
		</UL>
		<DIV id="tagContent" align="center">
			<DIV class="tagContent" id="tagContent1">
				<img src="${requestScope.pieColumnReportURI}" width=700 height=330 border=0
					usemap="#${requestScope.pieColumnReportURI}"/>
			</DIV>
			<DIV class="tagContent" align="center" id="tagContent2">
					<img src="${requestScope.pieRowReportURI}" width=700 height=330 border=0
						usemap="#${requestScope.pieRowReportURI}"/>
			</DIV>
			<DIV class="tagContent" align="center" id="tagContent3">
					<img src="${requestScope.barReportURI}" width=700 height=330 border=0
						usemap="#${requestScope.barReportURI }"/>
			</DIV>
			<DIV class="tagContent" width="100%" id="tagContent4" style="text-align:center;">
				<table id="bodyTable" name="dataTable" height="5px" width="100%" cellspacing="0" cellpadding="0">
					<thead id="thd">
						<tr>
							<c:forEach items="${rwScope}" var="rowTh" varStatus="status">
								<c:choose>
									<c:when test="${status.index==0}">
										<th width="1%" nowrap="nowrap"
											style="border: none; border-bottom: 1ps solid #939393;">
											${ rowTh}
										</th>
									</c:when>
									<c:otherwise>
										<th nowrap="nowrap">
											${ rowTh}
										</th>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</thead>
					<tbody id="tbd">
						<c:forEach items="${dataScpe}" var="dataList">
							<tr>
								<c:forEach items="${dataList}" var="data" varStatus="status">
									<c:choose>
										<c:when test="${status.index == 0}">
											<td nowrap="nowrap" align="center" class="tfc">
												${data}
											</td>
										</c:when>
										<c:otherwise>
											<td nowrap="nowrap" align="center" height="27">
												<fmt:formatNumber value="${data}" maxFractionDigits="0" />
											</td>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</DIV>
			<DIV class="tagContent" width="100%" style="text-align:center;" id="tagContent5">
				<table id="bodyTable" name="cdataTable" height="5px" width="100%" cellspacing="0" cellpadding="0">
					<thead id="thd">
						<tr>
							<c:forEach items="${rowScope}" var="rowTh" varStatus="status">
								<c:choose>
									<c:when test="${status.index==0}">
										<th width="1%" nowrap="nowrap"
											style="border: none; border-bottom: 1ps solid #939393;">
											${ rowTh}
										</th>
									</c:when>
									<c:otherwise>
										<th nowrap="nowrap">
											${ rowTh}
										</th>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</thead>
					<tbody id="tbd">
						<c:forEach items="${dataScope}" var="dataList">
							<tr>
								<c:forEach items="${dataList}" var="data" varStatus="status">
									<c:choose>
										<c:when test="${status.index == 0}">
											<td nowrap="nowrap" align="center" class="tfc">
												${data}
											</td>
										</c:when>
										<c:otherwise>
											<td nowrap="nowrap" align="center" height="27">
												<fmt:formatNumber value="${data}" maxFractionDigits="0" />
											</td>

										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</DIV>
			<DIV class="tagContent" width="100%" style="text-align:center;" id="tagContent6">
				<table id="bodyTable" name="pdataTable" height="5px" width="100%" cellspacing="0" cellpadding="0">
					<thead id="thd">
						<tr>
							<c:forEach items="${pRowScope}" var="rowTh" varStatus="status">
								<c:choose>
									<c:when test="${status.index==0}">
										<th width="1%" nowrap="nowrap"
											style="border: none; border-bottom: 1ps solid #939393;">
											${ rowTh}
										</th>
									</c:when>
									<c:otherwise>
										<th nowrap="nowrap">
											${ rowTh}
										</th>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</thead>
					<tbody id="tbd">
						<c:forEach items="${pDataScope}" var="dataList" varStatus="sta">
							<c:if test="${sta.last}">
								<tr>
									<c:forEach items="${dataList}" var="data" varStatus="status">
											<c:choose>
												<c:when test="${status.index == 0}">
													<td nowrap="nowrap" align="center" class="tfc">
														${data}
													</td>
												</c:when>
												<c:otherwise>
													<td nowrap="nowrap" align="center" height="27">
														<fmt:formatNumber value="${data}" minFractionDigits="2" type="percent" />
													</td>
												</c:otherwise>
											</c:choose>
									</c:forEach>
								</tr>
							</c:if>
							<c:if test="${!sta.last}">
								<tr>
									<c:forEach items="${dataList}" var="data" varStatus="status">
										<c:if test="${status.last }">
											<c:choose>
												<c:when test="${status.index == 0}">
													<td nowrap="nowrap" align="center" class="tfc">
														${data}
													</td>
												</c:when>
												<c:otherwise>
													<td nowrap="nowrap" align="center" height="27">
														<fmt:formatNumber value="${data}" minFractionDigits="2" type="percent" />
													</td>
												</c:otherwise>
											</c:choose>
										</c:if>
										<c:if test="${!status.last}">	
											<c:choose>
												<c:when test="${status.index == 0}">
													<td nowrap="nowrap" align="center" class="tfc">
														${data}
													</td>
												</c:when>
												<c:otherwise>
													<td nowrap="nowrap" align="center" height="27">
														<fmt:formatNumber value="${data}" maxFractionDigits="0" />
													</td>
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</DIV>
		</DIV>
	</DIV>
	</form>
	<iframe width="0" height="0" id="expIfram" name="expIfram"></iframe>
</body>
<%
		if (session != null && session.getAttribute("rwScope") != null) {
			session.removeAttribute("rwScope");
		}
		if (session != null
				&& session.getAttribute("dataScpe") != null) {
			session.removeAttribute("dataScpe");
		}
	if (session != null && session.getAttribute("rowScope") != null) {
			session.removeAttribute("rowScope");
		}
		if (session != null
				&& session.getAttribute("dataScope") != null) {
			session.removeAttribute("dataScope");
		}
	if (session != null && session.getAttribute("pRowScope") != null) {
			session.removeAttribute("pRowScope");
		}
		if (session != null
				&& session.getAttribute("pDataScope") != null) {
			session.removeAttribute("pDataScope");
		}
%>
</html:html>
