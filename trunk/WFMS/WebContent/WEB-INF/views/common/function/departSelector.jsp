<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>选择部门</title>
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
			var selectedTemp = {};
			var departsUrl = contextPath + '/common/organizeAction.do?method=getAllDeparts';
			new Ext.QuickTips.init();//提示信息加载
			
			var departRecord = new Ext.data.Record.create(["depid","name"]);
			var centerStore = new Ext.data.JsonStore(
			{
				fields:["depid","name"],
				data:[]
			}
			);
			var dataGrid=new Ext.grid.EditorGridPanel({
						stripeRows: true,//隔行变色
						autoScroll:true,//自动滚动条
						height:376,
						border:false,
						title:'已选部门',
						trackMouseOver:true,//高亮显示
						border:false,//去掉面板边框
				        loadMask: true,//读取数据的时候提示更新
				        store:centerStore,
				        checkOnly : true,
				        id:'departGrid',
						viewConfig:{
								forceFit:true
						},
						//列模型
				       	cm:new Ext.grid.ColumnModel([
				       	new Ext.grid.RowNumberer(),
				      	{
							header:'部门编号',
							hidden:true,
							dataIndex:'depid'
						},
						{
							header:'部门名称',
							dataIndex:'name',
							width:100
						}
						]),
						listeners:{
						"rowdblclick":function(gd, rowindex, e){
								var dep = centerStore.getAt(rowindex);
								selectedTemp[dep.get('depid')] = null;
								centerStore.removeAt(rowindex);
								centerStore.commitChanges();
								gd.getView().refresh();
						}
					}
			});
			dataGrid.on('render', function(grid){
	            var store = grid.getStore(); // Capture the Store.    
	            var view = grid.getView(); // Capture the GridView.    
	            dataGrid.tip = new Ext.ToolTip({
	                target: view.mainBody, // The overall target element.    
	                delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.    
	                trackMouse: true, // Moving within the row should not hide the tip.    
	                renderTo: document.body, // Render immediately so that tip.body can be referenced prior to the first show.    
	                listeners: { // Change content dynamically depending on which element triggered the show.    
	                    beforeshow: function updateTipBody(tip){
	                        var rowIndex = view.findRowIndex(tip.triggerElement);
	                        //tip.body.dom.innerHTML = "Over Record ID " + store.getAt(rowIndex).id;
	                    	tip.body.dom.innerHTML = '双击移除该部门';
						}
	                }
	            });
        	});
			var tree=new Ext.tree.TreePanel({
				id:'wyTree',
				collapsible:true,
				checkModel: 'cascade',
			　　 enableDD:true,
			　　 enableDrag:true,
			　　 rootVisible:true,
			　　 autoScroll:true,
				height:375,
				title:'选择部门',
				tbar:[
					{
						icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
						xtype:"button",
						tooltip:'刷新',
						handler:function(){
							Ext.getCmp('wyTree').getRootNode().reload();
						}
					},{
						xtype:"button",
						tooltip:'展开节点',
						checkModel: "cascade" ,
						icon:'${pageContext.request.contextPath}/imgs/expand-all.gif',
						handler:function(){
							this.ownerCt.ownerCt.expandAll();
						}
					},{
						xtype:"button",
						tooltip:'关闭节点',
						checkModel: "cascade" ,
						icon:'${pageContext.request.contextPath}/imgs/collapse-all.gif',
						handler:function(){
							this.ownerCt.ownerCt.collapseAll();
						}
					}
				],
				root:new Ext.tree.AsyncTreeNode({
					id:'0',
					text:'仓储财务管理系统',
					checked : false
				}),
				loader: new Ext.tree.TreeLoader({
					url:departsUrl,
					allowDrag : false
				})
			});
			
   			 tree.on('checkchange', function(node, checked) {
                 node.expand();
                 node.attributes.checked = checked;
                 node.eachChild(function(child) {
                             child.ui.toggleCheck(checked);
                             child.attributes.checked = checked;
                             child.fireEvent('checkchange', child,
                                     checked);
                         });
     		 }, tree);
			 
			var mainView = new Ext.Viewport({
				layout: 'border',
				enableTabScroll: true,
				title:'相关操作',
				collapsible:true,
				items: [
					{
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
										title:'选择部门',
										collapsible: false,
										width:550,
										layout:'column',
										defaults:{
											height:380
										},
										
										items:[
										{
										    columnWidth:.99,
										    height:60,
										    border:false,
											region:'north',
											layout:'column',
											bodyStyle:'text-valign:middle;padding-top:10px',
											items:[
											{
												bodyStyle:'margin-left:10px',
												layout:'form',
												columnWidth:.4,
												border:false,
												labelWidth : 55,
												items:{
													anchor :'90%',
													xtype:'textfield',
													fieldLabel:'部门名称',
													emptyText:'请输入部门名称',
													name:'name',
													width:190,
													allowBlank:false
												}
											},
											{
												columnWidth:.07,
												xtype:'button',
												bodyStyle:'padding-left:20px',
														width:50,
														id:'query',
														text:' 查  询',
														handler:function(){
														}
											}
											]
										},
											{
												columnWidth:.5,
												items:[tree]
											},{
												width:80,
												border:false,
												bodyStyle:'text-align:center;padding-top:150px',
												items:[
													{
														xtype:'button',
														width:50,
														id:'addDep',
														text:' => ',
														handler:function(){
														  var checkNodes = tree.getChecked();
											              Ext.each(checkNodes,function(node){
														  	if ((selectedTemp == null ||
															selectedTemp[node.id] == null)
															&&node.id!=0) {
																var depart = new departRecord({
																	depid: node.id,
																	name: node.text
																});
																centerStore.add(depart);
																selectedTemp[node.id] = node.text;
															}
														  });
														  centerStore.commitChanges();
														}
													}
												]
											},{
											columnWidth:.5,
											items:[
											{
											items:[dataGrid]
											}]
											}
										]
									}
								]
							}
						]
					}
				]
			});
			Ext.getCmp("wyTree").on('click',function(node,event){
					event.stopEvent();
			});
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='{qxlx}'>
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
