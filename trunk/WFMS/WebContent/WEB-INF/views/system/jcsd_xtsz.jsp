<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
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
			//年级信息
			var xtszData = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'xtszglAction.do?method=getKdXtsz',
				fields:["id","key","value","bz","keyName","sfkd","sx"]
			});
			
			
			
			
			
			
			var SM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true//单选
			});
			
			var mainView = new Ext.Viewport({
				layout: 'border',
				enableTabScroll: true,
				title:'相关操作',
				collapsible:true,
				items: [
					{
	                    id:'navigate',
	                    region:'north',
						border:false,
						baseCls:'x-plain',
	                    height:40,
	                    margins:'0 4 0 4',
						html:'<table width="100%" height="100%"><tr><td width="20" rowspan="2"><img height="40" width="45" src="'+contextPath+'/imgs/navigate.png"/></td><td style="font-size:13px;padding-left:5px;color:#15428b;font-weight:bold">系统管理 >> 基础设定 >> 学校基本信息</td></tr><tr><td style="font-size:11px;padding-left:5px;color:#15428b;">本模块提供维学校基本信息，作为系统所有相关模块的基础数据</td></tr></table>'
	                },{
						region:'east',
						width:200,
						margins:'4 4 4 0',
						bodyStyle:'padding:10px',
						collapsible:true,
						xtype:'form',
						items:new Ext.form.FieldSet({
							title: '相关链接',
				            collapsible: true,
							html:'<a href="#">相关链接1</a><br/><br/><a href="#">相关链接2</a><br/><br/><a href="#">相关链接3</a><br/><br/>'
						})
					},{
						region:'center',
						margins:'4 4 4 4',
						xtype:'panel',
						autoScroll:true,
						bodyStyle:'padding:10px,text-align:center',
						items:[
							{
								xtype:'form',
								layout:'form',
								autoScroll:true,
								border:false,
								items:[
									{
										xtype:'fieldset',
										title:'学校基本信息维护',
										collapsible: true,
										width:550,
										height:400,
										items:new Ext.grid.EditorGridPanel({
											stripeRows: true,
											width:520,
											height:360,
											autoScroll:true,
											trackMouseOver:true,
									        loadMask: true,
											tbar:[
												{
													icon:'${pageContext.request.contextPath}/imgs/save.gif',
													xtype:"button",
													tooltip:'保存',
													handler:function(){
														var m = xtszData.modified.slice(0);
														var jsonArray = [];
														Ext.each( m, function(item){
															jsonArray.push(item.data);
														});
														if(jsonArray.length == 0){
															return;
														}
														var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....','提示');
														var jsonData = encodeURIComponent(Ext.encode(jsonArray));
														Ext.Ajax.request({
															method: 'POST',
															url: 'xtszglAction.do?method=updateKdXtsz',
															success: function(response){
																var rs = Ext.decode(response.responseText);
																if (rs.success) {
																	waitMsg.hide();
																	xtszData.load();
																}else{
																	Ext.Msg.alert("提示", rs.message);
																}
															},
															failure: function(){
																Ext.Msg.alert("提示", "更新失败,服务器无法连接!");
															},
															params:{data:jsonData }
														});
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														xtszData.load();
													}
												}
											],
									        sm:SM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												{
													header:'名称',
													sortable : true,
													width:150,
													dataIndex:'keyName'
												},{
													header:'值',
													sortable : true,
													width:150,
													dataIndex:'value',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'备注',
													width:180,
													sortable : true,
													dataIndex:'bz',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
													}))
												}
											]),
											//数据
											store:xtszData
										})
									}
								]
							}
						]
					}
				]
			});
			xtszData.load();
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='{qxlx}'>

  	<select id="sel_qxlx" style="display:none">
  		<option value="2">只读</option>
  		<option value="10">只编辑</option>
  		<option value="210">全部</option>
  	</select>
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
