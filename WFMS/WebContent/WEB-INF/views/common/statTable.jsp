<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String referer = request.getHeader("referer");
	if(referer!=null && referer.indexOf("statTable")!=-1){
		response.setContentType("application/msexcel;charset=GBK");
		response.setHeader("Content-disposition","inline; filename=report.xls");
 		out.println(request.getParameter("excelData"));
 		out.flush();
 		out.close();
 		return;
 	}
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>统计表格</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
		#tags LI .selectTag {
			BACKGROUND-POSITION: left top;
			MARGIN-BOTTOM: -2px;
			POSITION: relative;
			HEIGHT: 25px;
		}
		li{
			font-family:Arial;
			font-size:12px;
		}
		table
			{
				border-collapse:collapse; 
			}
		table th
			{
				font-size:13px;
				background-color:#CAE8EA;
			}
		table td
			{
				font-size:12px;
				border:1px solid #B2D5CF;
				background-color:#F5FAFE;
				text-align:center;
			}
	</style>
	<script type="text/javascript">
		function selectTag(showContent,selfObj){
			// 操作标签
			var tag = document.getElementById('tags').getElementsByTagName('li');
			var taglength = tag.length;
			for(i=0; i<taglength; i++){
				tag[i].className = "";
			}
			if(selfObj!=null && selfObj!='undefined')
				selfObj.parentNode.className = 'selectTag';
			
			// 操作内容
			for(var j=4;j<7;j++){
				document.getElementById('tagContent'+j).style.display='none';
			}
			document.getElementById(showContent).style.display = 'block';
			var tag4Display=document.getElementById('tagContent4').style.display;
			var tag5Display=document.getElementById('tagContent5').style.display;
			var tag6Display=document.getElementById('tagContent6').style.display;
			if(tag4Display=='block' || tag5Display=='block' || tag6Display=='block')
			{
				document.getElementById('li_changeView').style.display='block';	
			}
			else
			{
				document.getElementById("li_changeView").style.display="none";
			}
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
	</script>
  </head>
  <body>
  	<form action="statTable.jsp" method="post">
	<input type="hidden" value="" name="excelData" id="excelData"/>
  	<div>
  		<ul style="list-style:none;" id="tags">
  			<li style="background-color:#EDFBFF;" id="li_changeView">
				<span style="background-color:#EDFBFF;">
				<input style="background-color:#EDFBFF;" type="radio" style="" name="tabletype" value="normal" checked="checked" onclick="changeTotalizeView(this);"> 无总计</input>
				<input style="background-color:#EDFBFF;" type="radio" style="" name="tabletype" value="count" onclick="changeTotalizeView(this);">总数总计</input>
				<input style="background-color:#EDFBFF;" type="radio" style="" name="tabletype" value="percent" onclick="changeTotalizeView(this);">百分数总计</input>
				<img style="margin-top:4px;cursor:hand;" onclick="expToExcel();" alt="导出到excel" src="<c:url value='/imgs/excel.gif'/>" valign="buttom"/>
				</span>
			</li>
  		</ul>
  	</div>
    <DIV class="tagContent" width="100%" id="tagContent4" style="text-align:center;display:block;">
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
			<DIV class="tagContent" width="100%" style="text-align:center;display:none;" id="tagContent5">
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
			<DIV class="tagContent" width="100%" style="text-align:center;display:none;" id="tagContent6">
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
</html>
