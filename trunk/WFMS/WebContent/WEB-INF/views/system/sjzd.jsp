<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>数据字典管理</title>
    
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bachStoreLoad.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/add_viewport.js"></script>
		<script type="text/javascript">
			Ext.onReady(function(){
			var nodeText = {}; //存放节点对象列表
			new Ext.QuickTips.init();
			var addWin = new Ext.Window({
				width:400,
				height:200,
				title:'添加新代码',
				layout:'fit',
				closeAction:'hide',
				modal:true,
				plain:true,
				items:[{
					xtype:'form',
					id:'addLxdmForm',
					baseCls:'x-plain',
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
							xtype:'hidden',
							name:'sjlxdm',
							id:'_sjlxdm'
						},
						{
							fieldLabel:'代码名称',
							name:'dmmc',
							allowBlank:false
						},{
							fieldLabel:'代码简称',
							name:'dmjc'
						},{
							fieldLabel:'是否可编辑',
							hiddenName:'sfkbj',
							mode:'local',
							xtype:'combo',
							readOnly:true,
							triggerAction:'all',
							displayField:"cgname",
							valueField:'cgid',
							value:'1',
							store:new Ext.data.JsonStore({
								data:[{cgid:'1',cgname:'是'}],
								fields:['cgid','cgname']
							})
						},{
							fieldLabel:'国标代码',
							name:'gbdm'
						}
					],
					buttons:[
						{
							text:'保存',
							handler:function(){
								var addForm = Ext.getCmp('addLxdmForm');
								if(addForm.form.isValid()){
									addForm.form.doAction('submit',{
										url:contextPath+'/system/lxdmglAction.do?method=addLxdm',
										method:'post',
										waitTitle:'提示信息',                                                    
				                        waitMsg:'数据更新中，请稍候...',
				                        success:function(form,action){
				                        	addForm.form.reset();
				                        	if(action.result.success){
		                        				addWin.hide();
												dataStore.reload();
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
			
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySuperdm',
				fields:["id","lxdm","dmmc","sjlxdm","dmjc","mkmc","sfkbj"]
			});
			
			var checkboxModel = new Ext.grid.CheckboxSelectionModel({
				listeners: {
			           selectionchange: function(checkboxModel) {
			           },
			           rowselect:function(checkboxModel,ind,r){
			           }
			       }
			});
			
			var mainView = new Ext.Viewport({
				layout:'border',
				width: 612,
				height:405,
				border:false,
				rootVisible: true,
				items:[
							{
			                    region:'north',
								border:false,
								baseCls:'x-plain',
			                    height:40,
			                    margins:'0 4 0 4',
								html:'<table width="100%" height="100%"><tr><td width="20" rowspan="2"><img height="40" width="45" src="<c:url value="/imgs/navigate.png"/>"/></td><td id="navigate_title" style="font-size:13px;padding-left:5px;color:#15428b;font-weight:bold">系统管理  >>  数据字典管理</td></tr><tr><td id="navigate_comment" style="font-size:11px;padding-left:5px;color:#15428b;">本模块提供类型代码的基本数据</td></tr></table>'
                       		}, {
							 	region:'west',
								layout:'fit',
								xtype: 'treepanel',
								border:true,
								id:'menuTree',
								//enableDD:true,// 是否可拖动
								singleClickExpand:true,
								autoScroll:true,
								split:true,
								width:180,
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
		        				root: new Ext.tree.AsyncTreeNode({
								id:'0',
								expand:false,
								text:'数据字典',
					            loader: new Ext.tree.TreeLoader({
									applyLoader:false,
									url:contextPath+'/system/lxdmglAction.do?method=getAllLxdmMk&nodeText='
								}),listeners:{
									'load':function(node){
											for(var i=0;i<node.childNodes.length;i++){
												nodeText[node.childNodes[i].id]=escape(escape(node.childNodes[i].text));
											}
											Ext.getCmp('menuTree').loader.dataUrl=contextPath+'/system/lxdmglAction.do?method=getAllLxdmMk&nodeText=' + Ext.encode(nodeText);
											Ext.getCmp('menuTree').on('click',function(node)
											{
												if (node.leaf) {
													Ext.apply(dataStore.baseParams, {
														superDm: encodeURIComponent(node.id)
													});
													dataStore.load({
														params: {
															start: 0,
															limit: 15
														}
													});
												}
											}
										);
									}
								}
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
											tooltip:'添加新代码',
											icon:'${pageContext.request.contextPath}/imgs/add.png',
											handler:function(){
												if(nodeText == null){
													aler('请选择所要添加到模块！');
												}else{
													if (dataStore.baseParams.superDm) {
														Ext.getCmp('_sjlxdm').setValue(dataStore.baseParams.superDm);
														addWin.show(); //弹出添加窗口
													}
												}
											}
										},{
											xtype:"button",
											tooltip:'刷新',
											icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
											handler:function(){
												dataStore.reload();
											}
										},{
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
													url: contextPath+'/system/lxdmglAction.do?method=updateLxdm',
													success: function(response){
														var rs = Ext.decode(response.responseText);
														if (rs.success) {
															waitMsg.hide();
															dataStore.reload();
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
													},
													{
														icon:'${pageContext.request.contextPath}/imgs/delete.png',
														xtype:"button",
														tooltip:'删除',
														handler:function(){
														var r = checkboxModel.getSelected();
														if(r == null){
															return;
														}
														if(r.get('sfkbj')=='0')
														{
															Ext.Msg.alert('提示','该类型代码不可编辑或删除');
															return;
														}
														Ext.MessageBox.confirm("危险操作","删除可能会导致系统无法正常试用,是否确认操作?",function(btn){
															if(btn == 'yes'){
																
																var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....','提示');
																Ext.Ajax.request({
																	method: 'POST',
																	url: contextPath+'/system/lxdmglAction.do?method=delLxdm&id='+r.get('id'),
																	success: function(response){
																		var rs = Ext.decode(response.responseText);
																		if (rs.success) {
																			waitMsg.hide();
																			dataStore.reload();
																		}else{
																			Ext.Msg.alert("提示", rs.message);
																		}
																	},
																	failure: function(){
																		Ext.Msg.alert("提示", "服务器连接失败，请重试");
																	}
																});
															}
														});
														
														
													}

													}
									],
							       	cm:new Ext.grid.ColumnModel([
										{
											header:'类型代码',
											readOnly:true,
											id:'header_lxdm',
											dataIndex:'lxdm'
										},{
											header:'代码名称',
											dataIndex:'dmmc',
											editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
										},{
											header:'代码简称',
											dataIndex:'dmjc',
											editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
										},{
											header:'是否可编辑',
											dataIndex:'sfkbj',
											renderer:function(value){
												if(value=='1')
												{
													this.editor=new Ext.grid.GridEditor(new Ext.form.ComboBox({
														mode:'local',
														readOnly:true,
														triggerAction:'all',
														displayField:"mc",
														valueField:'id',
														store:new Ext.data.JsonStore({
															data:[{id:'0',mc:'否'},{id:'1',mc:'是'}],
															fields:['id','mc']
														})
													}));
												}
												 return value =='0'?'否':'是';												
											}
										},{
											header:'国标代码',
											dataIndex:'gbdm',
											editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
										}
									]),store:dataStore,
										bbar: new Ext.PagingToolbar({
								            pageSize: 15,
								            store: dataStore,
								            displayInfo: true,
								            beforePageText:'第',
								            afterPageText:'页，共 {0} 页',
								            displayMsg: '当前为第<font style="color:red">{0} - {1}</font> 条记录，共<font style="color:red">{2}</font> 条记录',
								            emptyMsg: "没有找到相关信息"
								        })
								})			
				]
		});
			dataStore.load();
		});
		</script>
  </head>
  
  <body>
  </body>
</html>
