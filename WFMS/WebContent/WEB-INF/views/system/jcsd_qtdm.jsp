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
			var njData = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'jcsdAction.do?method=getQtdm&dmlb=nj',
				fields:[
					{
						name:'id'
					},{
						name:'dmjc'
					},{
						name:'dmmc'
					},{
						name:'dmlb'
					},{
						name:'bz'
					}
				]
			});
			//学年信息
			var xnData = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'jcsdAction.do?method=getQtdm&dmlb=xn',
				fields:[
					{
						name:'id'
					},{
						name:'dmjc'
					},{
						name:'dmmc'
					},{
						name:'dmlb'
					},{
						name:'bz'
					}
				]
			});
			//学期信息
			var xqData = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'jcsdAction.do?method=getQtdm&dmlb=xq',
				fields:[
					{
						name:'id'
					},{
						name:'dmjc'
					},{
						name:'dmmc'
					},{
						name:'dmlb'
					},{
						name:'bz'
					}
				]
			});
			//学月信息
			var xyData = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'jcsdAction.do?method=getQtdm&dmlb=xy',
				fields:[
					{
						name:'id'
					},{
						name:'dmjc'
					},{
						name:'dmmc'
					},{
						name:'dmlb'
					},{
						name:'bz'
					}
				]
			});
			//周次信息
			var zcData = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'jcsdAction.do?method=getQtdm&dmlb=zc',
				fields:[
					{
						name:'id'
					},{
						name:'dmjc'
					},{
						name:'dmmc'
					},{
						name:'dmlb'
					},{
						name:'bz'
					}
				]
			});
			
			
			//添加年级窗口
			var addNjWin = new Ext.Window({
				width:400,
				title:'添加年级',
				height:220,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addNjForm',
					baseCls:'x-plain',
					labelWidth:55,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					defaultType:'textfield',
					items:[
						{
							fieldLabel:'年级简称',
							name:'qtdm.dmjc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'年级全称',
							name:'qtdm.dmmc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'备注',
							name:'qtdm.bz',
							width:300,
							xtype:'textarea',
							height:60
						},{
							name:'qtdm.dmlb',
							value:'nj',
							hidden:true,
							allowBlank:false
						}
					]
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var addForm = Ext.getCmp('addNjForm');
							if(addForm.form.isValid()){
								addForm.form.doAction('submit',{
									url:'jcsdAction.do?method=addQtdm',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	addForm.form.reset();
			                        	if(action.result.success){
			                				addNjWin.hide();
											njData.reload();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },
			                        failure:function(){
			                        	addForm.form.reset();
			                            Ext.Msg.alert('操作失败','代码简称不能重复，请检查后重试');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addNjWin.hide();
						}
					}
				]
			});
			
			
			//添加学年窗口
			var addXnWin = new Ext.Window({
				width:400,
				title:'添加学年',
				height:220,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addXnForm',
					baseCls:'x-plain',
					labelWidth:55,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					defaultType:'textfield',
					items:[
						{
							fieldLabel:'学年简称',
							name:'qtdm.dmjc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'学年全称',
							name:'qtdm.dmmc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'备注',
							name:'qtdm.bz',
							width:300,
							xtype:'textarea',
							height:60
						},{
							name:'qtdm.dmlb',
							value:'xn',
							hidden:true,
							allowBlank:false
						}
					]
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var addForm = Ext.getCmp('addXnForm');
							if(addForm.form.isValid()){
								addForm.form.doAction('submit',{
									url:'jcsdAction.do?method=addQtdm',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	addForm.form.reset();
			                        	if(action.result.success){
			                				addXnWin.hide();
											xnData.reload();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },
			                        failure:function(){
			                        	addForm.form.reset();
			                            Ext.Msg.alert('操作失败','代码简称不能重复，请检查后重试');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addXnWin.hide();
						}
					}
				]
			});
			
			
			//添加学期窗口
			var addXqWin = new Ext.Window({
				width:400,
				title:'添加学期',
				height:220,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addXqForm',
					baseCls:'x-plain',
					labelWidth:55,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					defaultType:'textfield',
					items:[
						{
							fieldLabel:'学期简称',
							name:'qtdm.dmjc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'学期全称',
							name:'qtdm.dmmc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'备注',
							name:'qtdm.bz',
							width:300,
							xtype:'textarea',
							height:60
						},{
							name:'qtdm.dmlb',
							value:'xq',
							hidden:true,
							allowBlank:false
						}
					]
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var addForm = Ext.getCmp('addXqForm');
							if(addForm.form.isValid()){
								addForm.form.doAction('submit',{
									url:'jcsdAction.do?method=addQtdm',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	addForm.form.reset();
			                        	if(action.result.success){
			                				addXqWin.hide();
											xqData.reload();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },
			                        failure:function(){
			                        	addForm.form.reset();
			                            Ext.Msg.alert('操作失败','代码简称不能重复，请检查后重试');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addXqWin.hide();
						}
					}
				]
			});
			
			//添加学月窗口
			var addXyWin = new Ext.Window({
				width:400,
				title:'添加学月',
				height:220,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addXyForm',
					baseCls:'x-plain',
					labelWidth:55,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					defaultType:'textfield',
					items:[
						{
							fieldLabel:'学月简称',
							name:'qtdm.dmjc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'学月全称',
							name:'qtdm.dmmc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'备注',
							name:'qtdm.bz',
							width:300,
							xtype:'textarea',
							height:60
						},{
							name:'qtdm.dmlb',
							value:'xy',
							hidden:true,
							width:300,
							allowBlank:false
						}
					]
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var addForm = Ext.getCmp('addXyForm');
							if(addForm.form.isValid()){
								addForm.form.doAction('submit',{
									url:'jcsdAction.do?method=addQtdm',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	addForm.form.reset();
			                        	if(action.result.success){
			                				addXyWin.hide();
											xyData.reload();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },
			                        failure:function(){
			                        	addForm.form.reset();
			                            Ext.Msg.alert('操作失败','代码简称不能重复，请检查后重试');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addXyWin.hide();
						}
					}
				]
			});
			
			
			//添加周次窗口
			var addZcWin = new Ext.Window({
				width:400,
				title:'添加周次',
				height:220,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addZcForm',
					baseCls:'x-plain',
					labelWidth:55,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					defaultType:'textfield',
					items:[
						{
							fieldLabel:'周次简称',
							name:'qtdm.dmjc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'周次全称',
							name:'qtdm.dmmc',
							width:300,
							allowBlank:false
						},{
							fieldLabel:'备注',
							name:'qtdm.bz',
							width:300,
							xtype:'textarea',
							height:60
						},{
							name:'qtdm.dmlb',
							value:'zc',
							hidden:true,
							allowBlank:false
						}
					]
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var addForm = Ext.getCmp('addZcForm');
							if(addForm.form.isValid()){
								addForm.form.doAction('submit',{
									url:'jcsdAction.do?method=addQtdm',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	addForm.form.reset();
			                        	if(action.result.success){
			                				addZcWin.hide();
											zcData.reload();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },
			                        failure:function(){
			                        	addForm.form.reset();
			                            Ext.Msg.alert('操作失败','代码简称不能重复，请检查后重试');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addZcWin.hide();
						}
					}
				]
			});
			
			
			var njSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true,//单选
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});
			var xnSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true,//单选
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});
			var xqSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true,//单选
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});
			var xySM = new Ext.grid.CheckboxSelectionModel({
				singleSelect:true,//单选
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});
			var zcSM = new Ext.grid.CheckboxSelectionModel({
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
	                    id:'navigate',
	                    region:'north',
						border:false,
						baseCls:'x-plain',
	                    height:40,
	                    margins:'0 4 0 4',
						html:'<table width="100%" height="100%"><tr><td width="20" rowspan="2"><img height="40" width="45" src="<c:url value="/imgs/navigate.png"/>"/></td><td style="font-size:13px;padding-left:5px;color:#15428b;font-weight:bold">系统管理 >> 基础设定 >> 公共代码维护</td></tr><tr><td style="font-size:11px;padding-left:5px;color:#15428b;">本模块提供维护公共代码信息功能，作为系统所有相关模块的基础数据</td></tr></table>'
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
										title:'年级代码维护',
										collapsible: true,
										width:550,
										height:240,
										items:new Ext.grid.EditorGridPanel({
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
													tooltip:'添加年级',
													handler:function(){
														addNjWin.show();
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/delete.png',
													xtype:"button",
													tooltip:'删除年级',
													handler:function(){
														var r = njSM.getSelected();
														if(r == null){
															return ;
														}
														allDelete(r.get('id'),r.get('dmmc'),njData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/save.gif',
													xtype:"button",
													tooltip:'保存',
													handler:function(){
														var m = njData.modified.slice(0);
														var jsonArray = [];
														Ext.each( m, function(item){
															jsonArray.push(item.data);
														});
														if(jsonArray.length == 0){
															return;
														}
														allUdate(jsonArray,njData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														njData.load();
													}
												}
											],
									        sm:njSM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												{
													header:'年级简称',
													sortable : true,
													dataIndex:'dmjc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'年级全称',
													sortable : true,
													dataIndex:'dmmc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'备注',
													width:280,
													sortable : true,
													dataIndex:'bz',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
													}))
												}
											]),
											//数据
											store:njData
										})
									},{
										xtype:'fieldset',
										title:'学年代码维护',
										collapsible: true,
										width:550,
										height:240,
										items:new Ext.grid.EditorGridPanel({
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
													tooltip:'添加学年',
													handler:function(){
														addXnWin.show();
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/delete.png',
													xtype:"button",
													tooltip:'删除学年',
													handler:function(){
														var r = xnSM.getSelected();
														if(r == null){
															return ;
														}
														allDelete(r.get('id'),r.get('dmmc'),xnData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/save.gif',
													xtype:"button",
													tooltip:'保存',
													handler:function(){
														var m = xnData.modified.slice(0);
														var jsonArray = [];
														Ext.each( m, function(item){
															jsonArray.push(item.data);
														});
														if(jsonArray.length == 0){
															return;
														}
														allUdate(jsonArray,xnData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														xnData.load();
													}
												}
											],
									        sm:xnSM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												{
													header:'学年简称',
													sortable : true,
													dataIndex:'dmjc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'学年全称',
													sortable : true,
													dataIndex:'dmmc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'备注',
													width:280,
													sortable : true,
													dataIndex:'bz',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
													}))
												}
											]),
											//数据
											store:xnData
										})
									},{
										xtype:'fieldset',
										title:'学期代码维护',
										collapsible: true,
										width:550,
										height:240,
										items:new Ext.grid.EditorGridPanel({
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
													tooltip:'添加学期',
													handler:function(){
														addXqWin.show();
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/delete.png',
													xtype:"button",
													tooltip:'删除学期',
													handler:function(){
														var r = xqSM.getSelected();
														if(r == null){
															return ;
														}
														allDelete(r.get('id'),r.get('dmmc'),xqData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/save.gif',
													xtype:"button",
													tooltip:'保存',
													handler:function(){
														var m = xqData.modified.slice(0);
														var jsonArray = [];
														Ext.each( m, function(item){
															jsonArray.push(item.data);
														});
														if(jsonArray.length == 0){
															return;
														}
														allUdate(jsonArray,xqData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														xqData.load();
													}
												}
											],
									        sm:xqSM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												{
													header:'学期简称',
													sortable : true,
													dataIndex:'dmjc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'学期全称',
													sortable : true,
													dataIndex:'dmmc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'备注',
													width:280,
													sortable : true,
													dataIndex:'bz',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
													}))
												}
											]),
											//数据
											store:xqData
										})
									},{
										xtype:'fieldset',
										title:'学月代码维护',
										collapsible: true,
										width:550,
										height:240,
										items:new Ext.grid.EditorGridPanel({
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
													tooltip:'添加学月',
													handler:function(){
														addXyWin.show();
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/delete.png',
													xtype:"button",
													tooltip:'删除学月',
													handler:function(){
														var r = xySM.getSelected();
														if(r == null){
															return ;
														}
														allDelete(r.get('id'),r.get('dmmc'),xyData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/save.gif',
													xtype:"button",
													tooltip:'保存',
													handler:function(){
														var m = xyData.modified.slice(0);
														var jsonArray = [];
														Ext.each( m, function(item){
															jsonArray.push(item.data);
														});
														if(jsonArray.length == 0){
															return;
														}
														allUdate(jsonArray,xyData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														xyData.load();
													}
												}
											],
									        sm:xySM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												{
													header:'学月简称',
													sortable : true,
													dataIndex:'dmjc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'学月全称',
													sortable : true,
													dataIndex:'dmmc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'备注',
													width:280,
													sortable : true,
													dataIndex:'bz',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
													}))
												}
											]),
											//数据
											store:xyData
										})
									},{
										xtype:'fieldset',
										title:'周次代码维护',
										collapsible: true,
										width:550,
										height:240,
										items:new Ext.grid.EditorGridPanel({
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
													tooltip:'添加周次',
													handler:function(){
														addZcWin.show();
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/delete.png',
													xtype:"button",
													tooltip:'删除周次',
													handler:function(){
														var r = zcSM.getSelected();
														if(r == null){
															return ;
														}
														allDelete(r.get('id'),r.get('dmmc'),zcData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/save.gif',
													xtype:"button",
													tooltip:'保存',
													handler:function(){
														var m = zcData.modified.slice(0);
														var jsonArray = [];
														Ext.each( m, function(item){
															jsonArray.push(item.data);
														});
														if(jsonArray.length == 0){
															return;
														}
														allUdate(jsonArray,zcData);
													}
												},{
													icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
													xtype:"button",
													tooltip:'刷新',
													handler:function(){
														zcData.load();
													}
												}
											],
									        sm:zcSM,
											//列模型
									       	cm:new Ext.grid.ColumnModel([
												{
													header:'周次简称',
													sortable : true,
													dataIndex:'dmjc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'周次全称',
													sortable : true,
													dataIndex:'dmmc',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
														allowBlank:false
													}))
												},{
													header:'备注',
													width:280,
													sortable : true,
													dataIndex:'bz',
													editor:new Ext.grid.GridEditor(new Ext.form.TextField({
													}))
												}
											]),
											//数据
											store:zcData
										})
									}
								]
							}
						]
					}
				]
			});
			njData.load();
			xnData.load();
			xqData.load();
			xyData.load();
			zcData.load();
			
			function allDelete(id,name,store){
				Ext.Msg.confirm("删除提示","您选择了代码:"+name+",删除后该代码所有对应数据将失效，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							'jcsdAction.do?method=delQtdm&id='+id,
							{
								success:function(response){
									var rs = Ext.decode(response.responseText);
									if(rs.success){
										store.reload();
										mw.hide();
									}else{
										Ext.Msg.alert("提示",rs.message);
									}
								},
								failure:function(){
									Ext.Msg.alert("提示","服务器连接失败!");
								}
							}
						);
					}
				});
			}
			function allUdate(data,store){
				var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....','提示');
				var jsonData = encodeURIComponent(Ext.encode(data));
				Ext.Ajax.request({
					method: 'POST',
					url: 'jcsdAction.do?method=updQtdm',
					success: function(response){
						var rs = Ext.decode(response.responseText);
						if (rs.success) {
							waitMsg.hide();
							store.load();
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
