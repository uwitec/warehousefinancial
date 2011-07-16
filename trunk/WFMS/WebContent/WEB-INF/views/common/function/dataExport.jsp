<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>数据导出</title>
	<!-- Extjs导入 -->
	<style type="text/css">
		.store-info{
			background:url('<c:url value="/imgs/store-info.gif"/>') left top no-repeat;
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
						html:'<table width="100%" height="100%"><tr><td width="20" rowspan="2"><img height="40" width="45" src="<c:url value="/imgs/navigate.png"/>"/></td><td style="font-size:13px;padding-left:5px;color:#15428b;font-weight:bold">综合功能 >> 数据导出</td></tr><tr><td style="font-size:11px;padding-left:5px;color:#15428b;">该模块为用户提供方便的导出类型，用户可根据自己的需要导出数据</td></tr></table>'
	                },{
						xtype:'panel',
						region:'east',
						title:'相关操作',
						iconCls:'store-info',
						width:270,
						collapsible:true,
						collapsed:true,
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
								icon:'<:url value="/imgs/back.png"/>',
								handler:function(){
									if(getTableName() != ''){
										history.back();
									}
								}
							},"->",{
								tooltip:'帮助',
								id:'helpBtn',
								icon:'<c:url value="/imgs/help.gif"/>'
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
									html:'<iframe width="100%" height="100%" frameborder="0" src="<c:url value="function/dataExport_x.html"/>"></iframe>'
								})
							}
						]
					}
				]
			});
		});
		function getTableName(){
			return '<c:out value="${tableName}"/>';
		}
	  </script>
	</head>
  <body>
  </body>
</html>
