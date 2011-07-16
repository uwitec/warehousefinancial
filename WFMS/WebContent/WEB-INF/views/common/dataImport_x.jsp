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
			var drbStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=DRB',
				fields:["dmjc","dmmc"]
			});
			var tableStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: contextPath+'/common/dataImportAction.do?method=getTableColumns',
				fields:["id","columnName","comments","matchcontent","tablename","dataType","dataDefault","nullable","dataLength","dataPrecision","dataScale"]
			});
			
			var importStore = new Ext.data.JsonStore({
				data:[],
				fields:["columnName","ppzt"]
			});
			var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			
			var dataSm = new Ext.grid.CheckboxSelectionModel();
			var tableSm = new Ext.grid.CheckboxSelectionModel();
			var tableGrid = new Ext.grid.GridPanel({
				flex : 5.5,
				autoScroll:true,
				loadMask: true,
				//enableDragDrop : true,
       			stripeRows: true,
				store:tableStore,
				columnLines: true,
				sm:tableSm,
				tbar:{
					height:28,
					items:[
						"导入表：",{
							xtype:'combo',
							triggerAction:'all',
							modal:'local',
							id:'sel_drb',
							readOnly:true,
							displayField:"dmmc",
							valueField:'dmjc',
							store:drbStore,
							listeners:{
								"select":function(cob,r,ind){
									tableStore.load({params:{tableName:r.get('dmjc')}});
								}
							}
						}
					]
				},
				cm:new Ext.grid.ColumnModel([
					tableSm,
					{
						header:'字段',
						width:45,
						dataIndex:'columnName'
					},{
						header:'必须匹配',
						width:60,
						dataIndex:'nullable',
						renderer:function(v){
							if(v == "Y" || v == "y"){
								return '否';
							}else{
								return '<font style="color:red">是</font>';
							}
						}
					},{
						header:'字段名',
						dataIndex:'comments'
					},{
						header:'源字段',
						dataIndex:'matchcontent'
					}
				])
			});
			var dataGrid = new Ext.grid.GridPanel({
				autoScroll:true,
				flex : 3,
				loadMask: true,
				stripeRows: true,
				enableDragDrop:true,
				sm:dataSm,
				columnLines: true,
				store:importStore,
				tbar:{
					height:28,
					items:[
						{
							xtype:'button',
							icon:contextPath+'/imgs/import.gif',
							text:'读取源文件',
							handler:function(){
								imporWin.show();
							}
						}
					]
				},
				cm:new Ext.grid.ColumnModel([
					dataSm,
					{
						header:'导入字段',
						width:90,
						dataIndex:'columnName'
					},{
						header:'状态',
						width:60,
						dataIndex:'ppzt',
						renderer:function(val){
							return val == "1" ? '已匹配' : '<font style="color:red">未匹配</font>';
						}
					}
				])
			});
			
			var importPanel = new Ext.form.FormPanel({
			   	layout:'form',
			   	labelWidth:55,
			  	height:90,
				enctype:"multipart/form-data",
				baseCls:'x-plain',
				bodyStyle:'padding:10px',
			   	fileUpload: true,  
			   	items: [
			    	{
		              	xtype:'textfield',
		              	inputType:'file',
						allowBlank:false,
		              	fieldLabel: '选择文件', 
		              	name:"file",
		              	id:'importuser_value_text',                 
		           		anchor:'95%'
			         },{
						hiddenName:'isPreview',
						fieldLabel:'生成预览',
						xtype:'combo',
						mode:'local',
						readOnly:true,
						anchor:'95%',
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						value:'0',
						store:new Ext.data.JsonStore({
							data:[{sid:"1",sname:'是'},{sid:"0",sname:'否'}],
							fields:["sid","sname"]
						})			
					 },{
					 	xtype:'hidden',
						name:'matchData',
						id:'match_data'
					 },{
					 	xtype:'hidden',
						name:'isReserve',
						id:'data_reverse'
					 }
			    ]        
			});
			var imporWin = new Ext.Window({
				width:300,
				height:150,
				modal:true,
				plain:true,
				title:'请选择Excel文件',
				closeAction:'hide',
				items:importPanel,
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var value = Ext.getCmp('importuser_value_text').getValue();
							if(value.substring(value.length-4,value.length)==""){
								Ext.Msg.alert("提示","导入文件不能为空，请选择后缀为xls的数据源文件！");
								return;
							}
							if(value.substring(value.length-4,value.length)!=".xls"){
								Ext.Msg.alert("提示","只能导入后缀为xls的文件，请重新选择数据源文件！");
								Ext.getCmp('importuser_value_text').setValue('');
								return;
							}
							if (importPanel.getForm().isValid()) {
								importPanel.getForm().submit({
									waitMsg: '数据读取中，请稍后...',
									waitTitle: '提示',
									isUpload:true,
									method: 'POST',
									url:'dataImportAction.do?method=loadColumnHeader',
									success: function(form, action){
										tableStore.reload();
										importStore.removeAll();
										importStore.commitChanges();
										
										
										var aryHeader = eval(action.result.header);
										var strData = action.result.data;//数据
										
										var strColumn = "[new Ext.grid.RowNumberer(),";//数据行
										var strFiled = "[";//数据行
										
										var importColumns = new Ext.data.Record.create(["columnName","ppzt"]);
										for(var i = 0;i<aryHeader.length;i++){
											var r = new importColumns({
												columnName:aryHeader[i],
												ppzt:'0'
											});
											importStore.add(r);
											//创建动态列
											if (i == aryHeader.length - 1) {
												strColumn += "{header:'" + aryHeader[i] + "',dataIndex:'column" + i + "'}]";
												strFiled += "{name:'column" + i + "'}]";
											}else {
												strColumn += "{header:'" + aryHeader[i] + "',dataIndex:'column" + i + "'},";
												strFiled += "{name:'column" + i + "'},";
											}
										}
										
										var importC = new Ext.grid.ColumnModel(new Ext.util.JSON.decode(strColumn));
										var importS = new Ext.data.JsonStore({
											data:new Ext.util.JSON.decode(strData),
											autoDestroy:true,
									        fields: new Ext.util.JSON.decode(strFiled)
									    });
										Ext.getCmp('ylGrid').setTitle("预览文件："+action.result.title);
										Ext.getCmp('ylGrid').reconfigure(importS,importC);
										imporWin.hide();
									},
									failure: function(form,action){
										Ext.getCmp('importuser_value_text').setValue('');
										Ext.Msg.alert("导入提示",action.result.msg);
									}
								});
								
							}						
						}
					},{
						text:'取 消',
						handler:function(){
							imporWin.hide();
						}
					}
				] 
			});
			
			new Ext.form.FieldSet({
				width:650,
				height:350,
				title:'字段匹配',
				collapsible: true,
				renderTo:'import_panel',
				layout:'hbox',
				layoutConfig:{ align : 'stretch' },
				items:[
					tableGrid,
					{
						border:false,
						flex : 1.5,
						bodyStyle:'text-align:center;padding-top:80px',
						layout:'form',
						defaults:{border:false},
						items:[
							{
								xtype:'panel',
								height:40,
								items:[
									{
										width:80,
										xtype:'button',
										icon:contextPath+'/imgs/to_left.gif',
										text:'字段匹配',
										handler:function(){
											var ts = tableSm.getSelections();
											var ds = dataSm.getSelections();
											var row = ts.length > ds.length ? ds : ts;
											for(var i=0;i<row.length;i++){
												
												if(ts[i].get('matchcontent') != ""){
													importStore.each(function(r){
														if(r.get('columnName') == ts[i].get('matchcontent')){
															r.set('ppzt','0');
															return ;
														}
													});
												}
												
												ts[i].set('matchcontent',ds[i].get('columnName'));
												ds[i].set('ppzt','1');
											}
											importStore.commitChanges();
											tableStore.commitChanges();
										}
									}
								]
							},{
								xtype:'panel',
								height:40,
								items:[{
									width:80,
									xtype:'button',
									icon:contextPath+'/imgs/delete.png',
									text:'撤销匹配',
									handler:function(){
										var ts = tableSm.getSelections();
										for(var i =0;i<ts.length;i++){
											importStore.each(function(r){
												if(r.get('columnName') == ts[i].get('matchcontent')){
													r.set('ppzt','0');
													return ;
												}
											});
											ts[i].set('matchcontent','');
										}
										
										importStore.commitChanges();
										tableStore.commitChanges();
									}
								}]
							},{
								xtype:'panel',
								height:40,
								items:[{
									width:80,
									xtype:'button',
									text:'自动匹配',
									icon:contextPath+'/imgs/update.gif',
									handler:function(){
										importStore.each(function(ir){
											tableStore.each(function(tr){
												if(ir.get('columnName') == tr.get('comments')){
													tr.set('matchcontent',ir.get('columnName'));
													ir.set('ppzt','1');
													return ;
												}
											});
										});
										
										importStore.commitChanges();
										tableStore.commitChanges();
									}
								}]
							},{
								xtype:'panel',
								height:40,
								items:[{
									width:80,
									xtype:'button',
									text:'执行导入',
									icon:contextPath+'/imgs/save.gif',
									handler:function(){								
										var value = Ext.getCmp('importuser_value_text').getValue();
										
										if(value == "" || typeof(value) == 'undefined'){
											Ext.Msg.confirm("提示","导入文件不能为空，请选择后缀为xls的数据源文件，是否现在导入源文件",function(btn){
												if(btn == "yes"){
													imporWin.show();
												}
											});
											return;
										}
										if(value.substring(value.length-4,value.length)!=".xls"){
											Ext.Msg.alert("提示","只能导入后缀为xls的文件，请重新选择数据源文件！");
											Ext.getCmp('importuser_value_text').setValue('');
											return;
										}
										Ext.Msg.show({
											title:'导入提示',
											msg: '是否覆盖系统中已经存在的数据，单击[yes]则覆盖已有数据，单击[no]则不覆盖，单击[cancel]取消操作。',
											buttons: Ext.Msg.YESNOCANCEL,
											fn: function(btn){
												if(btn == 'yes'){
													Ext.getCmp('data_reverse').setValue('1');
													dataImport();
												}else if(btn == "no"){
													Ext.getCmp('data_reverse').setValue('0');
													dataImport();
												}else{
													this.close();
												}
											}
										});
									}
								}]
							}
						]
					},
					dataGrid
				]
			})
			new Ext.form.FieldSet({
				height:440,
				title:'导入数据预览',
				width:650,
				layout:'hbox',
				collapsible: true,
				renderTo:'data_panel',
				layoutConfig:{ align : 'stretch' },
				items:[{
					autoScroll:true,
					xtype:'grid',
					stripeRows: true,
					id:'ylGrid',
					loadMask: true,
					columnLines: true,
					store:new Ext.data.JsonStore(),
					title:'预览文件：',
					cm:new Ext.grid.ColumnModel([])
				}]
			});
			
			var ddrow = new Ext.dd.DropTarget(tableGrid.view.mainBody, {
		        ddGroup : 'GridDD',
		        copy    : true,
		        notifyDrop : function(dd, e, data) {
					// 选中了多少行
	            	var rows = data.selections;
					 // 拖动到第几行
					var index = dd.getDragData(e).rowIndex; 
		            if (typeof(index) == "undefined") {
		                return;
		            }
		            // 修改store
		            for(i = 0; i < rows.length; i++ ,index++) {
						if (tableStore.getCount() > index) {
							var rs = rows[i].get('columnName');
							if(tableStore.getAt(index).get('matchcontent') != ""){
								importStore.each(function(r){
									if(r.get('columnName') == tableStore.getAt(index).get('matchcontent')){
										r.set('ppzt','0');
										return ;
									}
								});
							}
							tableStore.each(function(r){
								if(r.get('matchcontent') == rs){
									r.set('matchcontent','');
									return ;
								}
							});
							tableStore.getAt(index).set('matchcontent',rs);
							rows[i].set('ppzt','1');
						}
		            }
					importStore.commitChanges();
					tableStore.commitChanges();
					return true;
		        }
		    });
			function dataImport(){
				if (importPanel.getForm().isValid()) {
					var dataArray = [];
					var isImport = true;
					var thePPMsg = "";
					tableStore.each(function(r){
						if(r.get('matchcontent') != '')
						{
							dataArray.push(r.data);
						}
						if((r.get('nullable') == "N" || r.get('nullable') == "n" ) && r.get('matchcontent') == ''){
							isImport = false;
							thePPMsg ="字段：["+ r.get('comments')+"] 为必须匹配项，请匹配完成后导入！";
							return;
						}
					});
					if(!isImport){
						Ext.Msg.alert("导入提示",thePPMsg);
						return;
					}
					if(dataArray == null || dataArray.length == 0){
						Ext.Msg.alert("导入提示","您至少需要匹配一项数据进行导入！");
						return;
					}
					Ext.getCmp('match_data').setValue(Ext.encode(dataArray));
					
					importPanel.getForm().submit({
						waitMsg: '数据读取中，请稍后...',
						waitTitle: '提示',
						isUpload:true,
						method: 'POST',
						url:'dataImportAction.do?method=dataValidate',
						success: function(form, action){			
							imporWin.hide();
							Ext.Msg.alert("提示",action.result.message);
						},
						failure: function(form,action){
							var msg="数据导入失败！\n\n";
							var rsList = action.result.errorList;
							for(var i=0;i<rsList.length;i++)
							{
								msg += "导入文件第 "+rsList[i].row+' 行错误：\n'+rsList[i].msg+'\n------------------------------------------------------------------------------------------------\n\n';
							}
							Ext.getCmp('error_text').setValue(msg);
							errorWin.show();
						}
					});
				}
			}
			
			var errorWin = new Ext.Window({
				title:'导入提示',
				width:500,
				closeAction:'hide',
				height:300,
				modal:true,
				layout:'fit',
				items:[{
					xtype:'textarea',
					readOnly:true,
					id:'error_text'
				}]
			});
			
			drbStore.load({
				callback: function(r, options, success){
					if(success){
						if(parent.getTableName() != ''){
							var tn = parent.getTableName();
							Ext.getCmp('sel_drb').setValue(tn);
							tableStore.load({params:{tableName:tn}});
						}
						waitMsg.hide();
					}else{
						Ext.Msg.alert("错误提示","数据加载失败！"); 
					}
				}
			});	
			
		});
		
	  </script>
	</head>
  <body enctype="multipart/form-data">
  <input type="hidden" id='qx' value='${qxlx}'>
  <table style='margin:10px;width:95%' border='0' >
  	<tr>
  		<td style="width:100%;" id="import_panel">
		</td>
  	</tr>
	<tr>
  		<td style="width:100%;" id="data_panel">
		</td>
  	</tr>
  </table>		
  </body>
</html>
