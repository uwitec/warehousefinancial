<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>学生信息</title>
	<!-- Extjs导入 -->
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/sq_viewport.js"></script>
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
			
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
		        url: 'bjglAction.do?method=getBzrbjByBjId&id=${bj.id}',
				fields:["id","bzr.bzrxm","srsj","xrsj","zt"]
			});
			var mainFrame = new MianFrame();
			var SM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true,//单选
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});
			var editForm = new Ext.FormPanel({
				layout:'form',
				width:'600',
				bodyStyle:'padding:10px',
				border:false,
				items:[
					{
						xtype:'fieldset',
						title:'学号生成规则',
						layout:'form',
						collapsible: true,
						width:550,
						height:340,
						defaultType:'textfield',
						items:new Ext.grid.GridPanel({
							stripeRows: true,
							width:525,
							height:300,
							autoScroll:true,
							trackMouseOver:true,
					        loadMask: true,
							tbar:[
								{
									icon:'${pageContext.request.contextPath}/imgs/add.png',
									xtype:"button",
									tooltip:'添加班主任',
									closeAction:'hide',
									handler:function(){
										addZwjsWin.show();
									}
								},{
									icon:'${pageContext.request.contextPath}/imgs/delete.png',
									xtype:"button",
									tooltip:'卸任班主任',
									handler:function(){
									}
								},{
									icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
									xtype:"button",
									tooltip:'刷新',
									handler:function(){
										dataStore.load();
									}
								}
							],
					        sm:SM,
							//列模型
					       	cm:new Ext.grid.ColumnModel([
								SM,
								{
									header:'学号',
									sortable : true,
									dataIndex:'bzr.bzrxm'
								},{
									header:'上任时间',
									sortable : true,
									dataIndex:'srsj'
								},{
									header:'卸任时间',
									sortable : true,
									dataIndex:'xrsj',
									renderer:function(value){
										return value == '' ? '至今' : value;
									}
								},{
									header:'是否现任',
									sortable : true,
									width:60,
									dataIndex:'zt',
									renderer:function(value){
										return value == '1' ? '<font style="color:red">现任</font>' : "原任"
									}
								}
							]),
							//数据
							store:dataStore
						})
					}
				]
			});
			dataStore.load(callback,function(){});
			Ext.getCmp('editPanel').add(editForm);
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='${qxlx}'>
  <table width="100%" height='100%' >
  	<tr>
  		<td align="center" valign="middle">
			<div id='loadDiv' style="font-size:12px;">
			<img width='50' height='50' src='${pageContext.request.contextPath}/imgs/login-load.gif'>
			数据加载中，请稍后 . . .
			</div>
		</td>
  	</tr>
  </table>				
  </body>
</html>
