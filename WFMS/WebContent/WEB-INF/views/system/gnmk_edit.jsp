<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title></title>
	<script type="text/javascript">
		var all_sjmkid = 0;
		var boolData=[['是','1'],['否','0']];
		var boolStore=new Ext.data.SimpleStore({
			fields:['text','value'],
			data:boolData
		});
		Ext.onReady(function(){
			new Ext.QuickTips.init();//提示信息加载
			var addWin = new Ext.Window({
				width:400,
				height:270,
				title:'添加功能模块',
				layout:'fit',
				closeAction:'hide',
				modal:true,
				items:[{
					xtype:'form',
					id:'addForm',
					bodyStyle:'padding:10px',
					labelWidth:90,
					labelAlign:'right',
					buttonAlign:'right',
					defaults:{
						xtype:'textfield',
						width:250
					},
					items:[
						{
							fieldLabel:'模块名称',
							name:'name',
							allowBlank:false
						},{
							fieldLabel:'模块拼音',
							name:'honeticize'
						},{
							fieldLabel:'对应页面',
							name:'forwardPage'
						},{
							fieldLabel:'上级模块编号',
							name:'parentId',
							id:'text_sjmkid',
							readOnly:true,
							allowBlank:false
						},
						new Ext.form.ComboBox({
							//transform:"sel_sfyxj",
							mode:'local',
							store:boolStore,
							displayField:'text',
							//name:'sfyxj',
							fieldLabel:'是否有下级',
							valueField:'value',
							readOnly:true,
							xtype:'combo',
							selectOnFocus:true,
							triggerAction: 'all',
							hiddenName:'hasChild'
							//lazyRender:true
						})
						,{
							fieldLabel:'模块描述',
							name:'description'
						}
					],
					buttons:[
						{
							text:'保存',
							handler:function(){
								var addForm = Ext.getCmp('addForm');
								if(addForm.form.isValid()){
									addForm.form.doAction('submit',{
										url:'gnmkAction.do?method=addGnmk',
										method:'post',
										waitTitle:'提示信息',                                                    
				                        waitMsg:'数据更新中，请稍候...',
				                        success:function(form,action){
				                        	addForm.form.reset();
				                        	if(action.result.success){
		                        				addWin.hide();
												dataStore.load({
													params: {
														id:all_sjmkid
													}
												});
				                        	}else{
				                        		Ext.Msg.alert('添加失败', message);
				                        	}
				                        },
				                        failure:function(){
				                        	addForm.form.reset();
				                            Ext.Msg.alert('操作失败','服务器无法连接，请稍后重试！');
				                       	}
									});
								}
							}
						}
					]
				}]
			});
			
			/*function sfyxj(val){
				return val == '1' ? '是' : '否';
			}*/
			
			var checkboxModel = new Ext.grid.CheckboxSelectionModel({
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});

			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'gnmkAction.do?method=getSonGnmk',
				root: 'gnmkList',
				fields:[
					{
						name:'moduleId'
					},{
						name:'name'
					},{
						name:'honeticize'
					},{
						name:'forwardPage'
					},{
						name:'parentId'
					},{
						name:'hasChild'
					},{
						name:'description'
					}
				]
			});
			
			var mainView = new Ext.Viewport({
				layout:'border',
				enableTabScroll:true,
				items:[
					{
						region: 'west',
						margins:'4 4 4 4',
						width: 200,
						autoScroll: true,
						xtype:'treepanel',
						id:'menuTree',
						tbar:[
							{
								xtype:"button",
								tooltip:'展开节点',
								icon:'${pageContext.request.contextPath}/imgs/expand-all.gif',
								handler:function(){
									this.ownerCt.ownerCt.expandAll();
								}
							},'-',{
								xtype:"button",
								tooltip:'关闭节点',
								icon:'${pageContext.request.contextPath}/imgs/collapse-all.gif',
								handler:function(){
									this.ownerCt.ownerCt.collapseAll();
								}
							},'-',{
								icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
								xtype:"button",
								tooltip:'刷新',
								handler:function(){
									Ext.getCmp('menuTree').getRootNode().reload();
								}
							}
						],
						root:new Ext.tree.AsyncTreeNode({
							id:'0',
							text:'功能菜单'
						}),
						loader: new Ext.tree.TreeLoader({
							url:"gnmkAction.do?method=getAllTree",
							applyLoader:false
						})
					},new Ext.grid.EditorGridPanel({
						region: 'center',
						margins:'4 4 4 0',
						stripeRows: true,
						autoScroll:true,
						trackMouseOver:true,
				        loadMask: true,
				        sm:checkboxModel,
						tbar:[
							"工具栏：",{
								xtype:"button",
								tooltip:'添加新模块',
								icon:'${pageContext.request.contextPath}/imgs/add.png',
								handler:function(){
									Ext.getCmp('text_sjmkid').setValue(all_sjmkid);
									addWin.show();
								}
							},'-',{
								xtype:"button",
								tooltip:'删除新模块',
								icon:'${pageContext.request.contextPath}/imgs/delete.png',
								handler:function(){
									var r = checkboxModel.getSelected();
									if(r == null){
										return;
									}
									Ext.MessageBox.confirm("危险操作","删除会导致该模块不可用,是否确认操作?",function(btn){
										if(btn == 'yes'){
											
											var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....','提示');
											Ext.Ajax.request({
												method: 'POST',
												url: 'gnmkAction.do?method=delGnmk&id='+r.get('moduleId'),
												success: function(response){
													var rs = Ext.decode(response.responseText);
													if (rs.success) {
														waitMsg.hide();
														dataStore.load({
															params: {
																id:all_sjmkid
															}
														});
													}else{
														Ext.Msg.alert("提示", rs.message);
													}
												},
												failure: function(){
													win.show();
													Ext.Msg.alert("提示", "数据初始化失败,请重新登录");
												}
											});
										}
									});
									
									
								}
							},'-',{
								icon:'${pageContext.request.contextPath}/imgs/save.gif',
								xtype:"button",
								tooltip:'保存',
								handler:function(){
									var m = dataStore.modified.slice(0);
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
										url: 'gnmkAction.do?method=updGnmk',
										success: function(response){
											var rs = Ext.decode(response.responseText);
											if (rs.success) {
												waitMsg.hide();
												dataStore.load({
													params: {
														id:all_sjmkid
													}
												});
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
							}
						],
				       	cm:new Ext.grid.ColumnModel([
							{
								header:'编号(追加至链接末尾,控制权限)',
								sortable : true,
								dataIndex:'moduleId'
							},{
								header:'模块名称',
								dataIndex:'name',
								sortable : true,
								editor:new Ext.grid.GridEditor(new Ext.form.TextField({
									allowBlank:false
								}))
							},{
								header:'模块拼音',
								sortable : true,
								dataIndex:'honeticize',
								editor:new Ext.grid.GridEditor(new Ext.form.TextField({
								}))
							},{
								header:'对应页面',
								sortable : true,
								dataIndex:'forwardPage',
								editor:new Ext.grid.GridEditor(new Ext.form.TextField({
								}))
							},{
								header:'模块描述',
								sortable : true,
								dataIndex:'description',
								editor:new Ext.grid.GridEditor(new Ext.form.TextField({
								}))
							},{
								header:'上级模块编号',
								sortable : true,
								dataIndex:'parentId'
							},{
								header:'是否有下级',
								sortable : true,
								renderer:function(val){
									return val == '1' ? '是' : '否';
								},
								dataIndex:'hasChild'
							}
						]),
						store:dataStore
					})
				]
			});
			dataStore.load();
			
			Ext.getCmp("menuTree").on('click',function(node,event){
				node.getUI().getAnchor().href="javascript:void(0)";
				all_sjmkid = node.id;
				dataStore.load({
					params: {
						id:node.id
					}
				});
			});
		});
	</script>
  </head>
  
  <body>
  	<select style="display:none" id="sel_sfyxj">
  		<option value="1">是</option>
  		<option value="0">否</option>
  	</select>
	<table width="100%" height='100%' >
	  	<tr>
	  		<td align="center" valign="middle">
				<div id='loadDiv' style="font-size:12px;">
				<img width='50' height='50' src='${pageContext.request.contextPath}/imgs/login-load.gif'>
					数据初始化，请稍后 . . .
				</div>
			</td>
	  	</tr>
	  </table>
  </body>
</html>
