<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>学生信息</title>
	<!-- Extjs导入 -->
	<style type="text/css">
		.store-info{
			background:url('${pageContext.request.contextPath}/imgs/store-info.gif') left top no-repeat;
		}
		body{
			font-size:12px;
		}
	</style>
	<script type="text/javascript">
		Ext.onReady(function(){
			new Ext.QuickTips.init();//提示信息加载
			
			new Ext.Viewport({
				id:'mianFrame',
				layout:'border',
				items:[
					{
	                    id:'navigate',
	                    region:'north',
						border:false,
						baseCls:'x-plain',
	                    height:40,
	                    margins:'0 4 0 4',
						html:'<table width="100%" height="100%"><tr><td width="20" rowspan="2"><img height="40" width="45" src="../imgs/navigate.png"/></td><td style="font-size:13px;padding-left:5px;color:#15428b;font-weight:bold">综合功能 >> 数据导入</td></tr><tr><td style="font-size:11px;padding-left:5px;color:#15428b;">该模块为用户提供方便的导入形式，用户可根据自己的需求导入数据</td></tr></table>'
	                },{
						xtype:'panel',
						region:'east',
						title:'相关操作',
						iconCls:'store-info',
						width:270,
						collapsible:true,
						margins:'4 4 4 4',
						items:[
						]
					},{
						//列表信息模块
						xtype:'panel',
						region:'center',
						margins:'4 0 4 4',
						layout:'border',
						tbar:[
							{
								tooltip:'返回上一页面',
								icon:contextPath+'/imgs/back.png',
								handler:function(){
									if(getTableName() != ''){
										history.back();
									}
								}
							},"->",{
								tooltip:'帮助',
								id:'helpBtn',
								icon:contextPath+'/imgs/help.gif'
							}
						],
						items:[
							{
								region:'center',
								layout:'border',								
								border: false,
								items: new Ext.Panel({
									region:'center',
									border:false,
									html:'<iframe width="100%" height="100%" frameborder="0" src="dataImport_x.jsp"></iframe>'
								})
							}
						]
					}
				]
			});
		});
		function getTableName(){
			return '${tableName}';
		}
	  </script>
	</head>
  <body>
  </body>
</html>
