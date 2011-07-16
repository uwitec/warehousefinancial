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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/yhTree.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			new Ext.QuickTips.init();//提示信息加载
			//角色信息
			var jsData = new Ext.data.JsonStore({
				autoDestroy: true,
		        url: 'jsglAction.do?method=getZwyyJs&id=${zw.rolid}',
				fields:[
					{
						name:'partId'
					},{
						name:'name'
					},{
						name:'description'
					},{
						name:'createTime'
					}
				]
			});
			//未有角色信息
			var wyjsData = new Ext.data.JsonStore({
				autoDestroy: true,
		        url: 'jsglAction.do?method=getZwwyJs&id=${zw.rolid}',
				fields:[
					{
						name:'partId'
					},{
						name:'name'
					}
				]
			});
			//用户信息
			var yhData = new Ext.data.JsonStore({
				autoDestroy: true,
		        url: 'yhglAction.do?method=getZwyyYh&id=${zw.rolid}',
				fields:[
					{
						name:'memid'
					},{
						name:'username'
					},{
						name:'mobilephone'
					},{
						name:'email'
					},{
						name:'deniedlogin'
					}
				]
			});
			//职务未有用户信息
			var wyyhData = new Ext.data.JsonStore({
				autoDestroy: true,
		        url: 'yhglAction.do?method=getZwwyYh&id=${zw.rolid}',
				fields:[
					{
						name:'memid'
					},{
						name:'username'
					}
				]
			});
			
			//角色添加窗口
			var addZwjsWin = new Ext.Window({
				title:'请选择需要添加的角色',
				width:300,
				height:120,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addZwjsForm',
					baseCls:'x-plain',
					labelWidth:40,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					items:new Ext.form.ComboBox({
						fieldLabel:'角 色',
						xtype:'combo',
						id:'sel_jsid',
						baseCls:'x-plain',
						triggerAction: 'all',
						width:200,
						allowBlank:false,
						lazyRender:true,
						readOnly:true,
						emptyText:'请选择角色',
						store:wyjsData,
						displayField:'name',
						valueField:'partId'
					})
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var yjf = Ext.getCmp('addZwjsForm');
							if (yjf.form.isValid()) {
								var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后','提示');
								addZwjsWin.hide();
								Ext.Ajax.request({
									method: 'POST',
									url: 'jsglAction.do?method=addZwjs',
									params: {
										zwId:'${zw.rolid}',
										jsId:Ext.getCmp('sel_jsid').getValue()
									},
									success: function(response){
										var rs = Ext.decode(response.responseText);
										Ext.getCmp('sel_jsid').setValue('');
										if(rs.success){
											waitMsg.hide();
											wyjsData.reload();
											jsData.reload();
										}else{
											Ext.Msg.alert("提示", rs.message);
										}
									},
									failure: function(){
										Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
									}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addZwjsWin.hide();
						}
					}
				]
			});
			//用户添加窗口
			var addZwyhWin = new Ext.Window({
				title:'请选择需要添加的用户',
				width:300,
				height:120,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addZwyhForm',
					baseCls:'x-plain',
					labelWidth:40,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					items:[
						getyhTree()
					]
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var yjf = Ext.getCmp('addZwyhForm');
							if (yjf.form.isValid()) {
								var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后','提示');
								addZwjsWin.hide();
								Ext.Ajax.request({
									method: 'POST',
									url: 'zwglAction.do?method=addYhzw',
									params: {
										zwId:'${zw.rolid}',
										yhId:getYhValue()//Ext.getCmp('sel_yhid').getValue()
									},
									success: function(response){
										var rs = Ext.decode(response.responseText);
										Ext.getCmp('selectTreeYhValue').setValue('');
										if(rs.success){
											waitMsg.hide();
											yhData.reload();
											wyyhData.reload();
										}else{
											Ext.Msg.alert("提示", rs.message);
										}
									},
									failure: function(){
										Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
									}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addZwyhWin.hide();
						}
					}
				]
			});
		
			var jsSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true,//单选
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});
			var yhSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true,//单选
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});

			
			var mainView = new Ext.Viewport({
				layout: 'border',
				enableTabScroll: true,
				title:'相关操作',
				collapsible:true,
				items: [
					{
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
						tbar:[
							{
								text:'[ 返回 ]',
								handler:function(){
									history.back();
								}
							}
						],
						items:[
							{
								xtype:'form',
								layout:'form',
								autoScroll:true,
								border:false,
								items:[
									{
										title: '职务基本信息',
										xtype:'fieldset',
							            collapsible: true,
										width:550,
							            labelWidth:55,
										defaults:{
											xtype:'textfield',
											width:300,
											readOnly:true
										}, 
										items:[
											{
												fieldLabel:'职务名称',
												value:'${zw.name}'
											},{
												fieldLabel:'职务描述',
												value:'${zw.synopsis}'
											},{
												fieldLabel:'创建时间',
												value:'${zw.createTime}'
											}
										]
									},{
										xtype:'fieldset',
										title:'职务角色信息',
										collapsible: true,
										width:550,
										height:240,
										items:new Ext.grid.GridPanel({
											stripeRows: true,
											width:520,
											height:200,
											autoScroll:true,
											trackMouseOver:true,
									        loadMask: true,
											tbar:[
												{
													icon:'${pageContext.request.contextPath}/imgs/add.png',
													xtype:"button",
													tooltip:'添加角色',
													closeAction:'hide',
													handler:function(){
														addZwjsWin.show();
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/delete.png',
													xtype:"button",
													tooltip:'删除角色',
													handler:function(){
														var r = jsSM.getSelected();
														if(r == null){
															return ;
														}
														var id = r.get('partId');
														Ext.MessageBox.confirm('删除提示','是否确认删除该职务对应角色：'+r.get('name'),function(btn){
															if(btn == 'yes'){
																var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后','提示');
																Ext.Ajax.request({
																	method: 'POST',
																	url: 'jsglAction.do?method=delZwjs',
																	params: {
																		zwId:'${zw.rolid}',
																		jsId:id
																	},
																	success: function(response){
																		var rs = Ext.decode(response.responseText);
																		if(rs.success){
																			waitMsg.hide();
																			wyjsData.reload();
																			jsData.reload();
																		}else{
																			Ext.Msg.alert("提示", rs.message);
																		}
																	},
																	failure: function(){
																		Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
																	}
																});
															}
														});
														
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														jsData.load();
													}
												}
											],
									        sm:jsSM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												jsSM,
												{
													header:'角色名称',
													sortable : true,
													dataIndex:'name'
												},{
													header:'角色描述',
													width:180,
													sortable : true,
													dataIndex:'description'
												},{
													header:'创建时间',
													sortable : true,
													dataIndex:'createTime'
												}
											]),
											//数据
											store:jsData
										})
									},{
										xtype:'fieldset',
										title:'职务用户信息',
										collapsible: true,
										width:550,
										height:400,
										items:new Ext.grid.GridPanel({
											stripeRows: true,
											width:520,
											height:360,
											autoScroll:true,
											trackMouseOver:true,
									        loadMask: true,
											tbar:[
												{
													icon:'${pageContext.request.contextPath}/imgs/add.png',
													xtype:"button",
													tooltip:'添加用户',
													closeAction:'hide',
													handler:function(){
														addZwyhWin.show();
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/delete.png',
													xtype:"button",
													tooltip:'删除用户',
													handler:function(){
														var r = yhSM.getSelected();
														if(r == null){
															return ;
														}
														var id = r.get('memid');
														Ext.MessageBox.confirm('删除提示','是否确认删除该职务对应用户：'+r.get('username'),function(btn){
															if(btn == 'yes'){
																var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后','提示');
																Ext.Ajax.request({
																	method: 'POST',
																	url: 'zwglAction.do?method=delYhzw',
																	params: {
																		zwId:'${zw.rolid}',
																		yhId:id
																	},
																	success: function(response){
																		var rs = Ext.decode(response.responseText);
																		if(rs.success){
																			waitMsg.hide();
																			wyyhData.reload();
																			yhData.reload();
																		}else{
																			Ext.Msg.alert("提示", rs.message);
																		}
																	},
																	failure: function(){
																		Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
																	}
																});
															}
														});
														
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														yhData.load();
													}
												}
											],
									        sm:yhSM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												yhSM,
												{
													header:'用户姓名',
													sortable : true,
													dataIndex:'username'
												},{
													header:'联系方式',
													width:180,
													sortable : true,
													dataIndex:'mobilephone'
												},{
													header:'电子邮箱',
													sortable : true,
													dataIndex:'email'
												},{
													header:'状态',
													sortable : true,
													dataIndex:'deniedlogin',
													renderer:function(value){
														return value == "true" ? '<font style="color:red">禁用</font>' : '正常';
													}
												}
											]),
											//数据
											store:yhData
										})
									}
								]
							}
						]
					}
				]
			});

			yhData.load();
			jsData.load();
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='${qxlx}'>

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
