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
			var xhgzData = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: contextPath+'/system/jcsdAction.do?method=getQtdm&dmlb=xhscgz',
				fields:[
					{
						name:'dmjc'
					},{
						name:'dmmc'
					},{
						name:'bz'
					}
				]
			});
			var gzRecord = new Ext.data.Record.create(["id","tjid","zdws","qsw","ws","sfbl","clz","scgzmc","scsx","qsxh"]);
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
		        url: 'bhscgzglAction.do?method=getAllBhscgz&name=xhscgz',
				fields:["id","tjid","zdws","qsw","ws","sfbl","clz","scgzmc","scsx","qsxh"]
			});
			var mainFrame = new MianFrame();
			setNavigate("系统管理 >> 基础设定 >> 学号生成规则","本模块提供维护学号生成规则功能，作为自动分班基础数据");
			
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
						width:650,
						height:340,
						defaultType:'textfield',
						items:new Ext.grid.EditorGridPanel({
							stripeRows: true,
							width:625,
							height:300,
							autoScroll:true,
							trackMouseOver:true,
					        loadMask: true,
							tbar:[
								{
									icon:'${pageContext.request.contextPath}/imgs/add.png',
									xtype:"button",
									tooltip:'新建规则',
									closeAction:'hide',
									handler:function(){
										var sx;
										if(dataStore.getCount() == 0){
											sx = 0;
										}else{
											sx = dataStore.getAt(dataStore.getCount()-1).get('scsx');
										}
										var r = new gzRecord({
											id:'0',
											tjid:'0',
											zdws:'',
											qsw:'',
											ws:'',
											sfbl:'0',
											clz:'输入常量',
											scgzmc:'xhscgz',
											scsx:sx+1,
											qsxh:''
										});
										dataStore.add(r);
									}
								},{
									icon:'${pageContext.request.contextPath}/imgs/delete.png',
									xtype:"button",
									tooltip:'删除规则',
									handler:function(){
										var r = SM.getSelected();
									if(r == null){
										return;
									}
									Ext.MessageBox.confirm("删除提示","是否确认删除该条规则?",function(btn){
										if(btn == 'yes'){
											var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....','提示');
											Ext.Ajax.request({
												method: 'POST',
												url: 'bhscgzglAction.do?method=delBhgz&id='+r.get('id'),
												success: function(response){
													var rs = Ext.decode(response.responseText);
													if (rs.success) {
														
														dataStore.commitChanges();
														dataStore.removeAll();
														dataStore.load(
															{
																callback : function(r, options, success) {  
															        if ( success == false) {  
															        	Ext.Msg.alert("错误提示","数据加载失败,请刷新页面重试！"); 
															        } 
															        else{
																		var i = 1;
																		dataStore.each(function(r){
																			r.set("scsx",i);
																			i++;
																		});
																		editData();
															        }
																} 
															}
														);
														
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
								},{
									icon:'${pageContext.request.contextPath}/imgs/save.gif',
									xtype:"button",
									tooltip:'保存规则',
									handler:function(){
										editData();
									}
								},{
									icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
									xtype:"button",
									tooltip:'刷新规则',
									handler:function(){
										dataStore.load();
									}
								}
							],
					        sm:SM,
							//列模型
					       	cm:new Ext.grid.ColumnModel([
								new Ext.grid.RowNumberer(),
								{
									header:'学号组成项',
									dataIndex:'tjid',
									editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
										allowBlank:false,
										readOnly:true,
										mode:'local',
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'dmjc',
										store:xhgzData,
										listeners:{
											"select":function(cob,r,ind){
												var sr = SM.getSelected();
												sr.set("zdws",r.get("bz"));
												if(r.get("dmjc") == 0){
													sr.set("clz","输入常量");
												}else{
													sr.set("clz","");
													if(r.get("dmjc") == 1){
														sr.set("qsxh","001");
													}else{
														sr.set("qsxh","");
													}
												}
											}
										}
									})),
									renderer:function(value){
										var rs = "";
										xhgzData.each(function(r){
											if(r.get('dmjc') == value){
												rs = r.get('dmmc');
												return;
											}
										});
										return rs;
									}
								},{
									header:'代码实际位数',
									dataIndex:'zdws'
								},{
									header:'起始位',
									dataIndex:'qsw',
									width:60,
									editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
									}))
								},{
									header:'位数',
									width:60,
									dataIndex:'ws',
									editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
									}))
								},{
									header:'起始序号',
									width:60,
									dataIndex:'qsxh',
									editor:new Ext.grid.GridEditor(new Ext.form.TextField({
									}))
								},{
									header:'不足位是否补零',
									dataIndex:'sfbl',
									editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
										mode:'local',
										readOnly:true,
										triggerAction:'all',
										displayField:"sname",
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:"1",sname:'是'},{sid:"0",sname:'否'}],
											fields:["sid","sname"]
										})
									})),
									renderer:function(value){
										return value == "1" ? '是' : '否';
									}
								},{
									header:'常量值',
									width:60,
									dataIndex:'clz',
									editor:new Ext.grid.GridEditor(new Ext.form.TextField({
									}))
								}
							]),
							//数据
							store:dataStore
						})
					}
				]
			});
			function editData(){
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
					url: 'bhscgzglAction.do?method=updateXhscgz',
					success: function(response){
						var rs = Ext.decode(response.responseText);
						if (rs.success) {
							waitMsg.hide();
							dataStore.commitChanges();
							dataStore.removeAll();
							dataStore.load();
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
			
			var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			xhgzData.load({
					callback : function(r, options, success) {  
				        if ( success == false) {  
				        	Ext.Msg.alert("错误提示","数据加载失败,请刷新页面重试！"); 
				        } 
				        else{
							dataStore.load();
							waitMsg.hide();
				        }
					} 
				});
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
