<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>仓库信息</title>
	<!-- Extjs导入 -->
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/yhTree.js"></script>
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
	
	//列表数据对象
	var dataStore = new Ext.data.JsonStore({
		autoDestroy: true,
		pruneModifiedRecords:true,
		root:'dataList',
		totalProperty:'totalCount',
      	url:contextPath+'/zcgl/ckxxAction.do?method=getCkxxByPage',
		fields:['id',"ckdm","ckmc","ckdd","glyid","glyxm","bz"]
	});
	
	//资产类型store
	var zclxStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/zclxAction.do?method=getZclxList',
		fields:['id',"lxdm","lxmc","bz"]
	});
	//资产信息store
	var zcxxStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/zcxxAction.do?method=getZcxxList',
		fields:['id',"zcdm","zcmc","zclx","zcgg","zcdw","scrq","bz"]
	});
	
	zclxStore.load();
	zcxxStore.load();
	
	//右侧详细信息
	infoView.add(new Ext.form.FieldSet({
        title: '仓库信息',
        collapsible: true,
        autoScroll:true,
        labelWidth:68,
        autoHeight:true,
        defaults: {
        	readOnly:true,
        	style: {
	            border:'none',
	            background:'none'
			},
        	anchor:'90%'
	    },
        defaultType: 'textfield',
        items :[
			{
				fieldLabel:'仓库代码',
				name:'ckdm'
			},{
				fieldLabel:'仓库名称',
				name:'ckmc'
			},{
				fieldLabel:'仓库地点',
				name:'ckdd'
			},{
				fieldLabel:'管理员',
				name:'glyxm'
			},{
				fieldLabel:'备注',
				name:'bz'
			}
		]
	}));
	
	//列表
	var dataGrid = new Ext.grid.EditorGridPanel({
		stripeRows: true,
		autoScroll:true,
		trackMouseOver:true,
		border:false,
        loadMask: true,
        sm:checkboxModel,
       	cm:new Ext.grid.ColumnModel([
      		 {
				header:'仓库代码',
				dataIndex:'ckdm'
			}, {
				header:' 仓库名称',
				dataIndex:'ckmc'
			}, {
				header:'仓库地点',
				dataIndex:'ckdd'
			}, {
				header:'管理员',
				dataIndex:'glyxm',
				allowBlank:false
			}, {
				header:'',
				dataIndex:'glyid',
				value:'11',
				hidden:true
			}, {
				header:'备注',
				dataIndex:'bz',
				editor:new Ext.grid.GridEditor(new Ext.form.TextField({
					allowBlank:false
				}))
			}
		]),
		//数据
		store:dataStore,
		bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: dataStore,
            displayInfo: true,
            beforePageText:'第',
            afterPageText:'页，共 {0} 页',
            displayMsg: '当前为第<font style="color:red">{0} - {1}</font> 条记录，共<font style="color:red">{2}</font> 条记录',
            emptyMsg: "没有找到相关信息"
        })
	});
	
	
	var ckzcStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/zcxxAction.do?method=getZcxxList',
		fields:['id',"zclx.id","zclx.lxmc","zcxx.id","zcxx.zcmc","zccssl","bz"]
	});
	ckzcStore.load();
	var gzRecord = new Ext.data.Record.create(['id',"zclx.lxmc","zclx.id",
		'zcxx.id',"zcxx.zcmc","zccssl","zclxid","zcxxid","scsx"]);
	
	//资产明细Grid
	var ckzcGrid = new Ext.grid.EditorGridPanel({
				stripeRows: true,
				autoScroll:true,
				trackMouseOver:true,
				width:400,
				height:190,
				border:false,
		        loadMask: true,
		        tbar:[
					{
						icon:'${pageContext.request.contextPath}/imgs/add.png',
						xtype:"button",
						tooltip:'添加行',
						closeAction:'hide',
						handler:function(){
							var sx;
							if(ckzcStore.getCount() == 0){
								sx = 0;
							}else{
								sx = ckzcStore.getAt(ckzcStore.getCount()-1).get('scsx');
							}
							var r = new gzRecord({
								id:'',
								zclxId:'',
								zcxxId:'',
								zccssl:'0',
								scsx:sx+1
							});
							ckzcStore.add(r);
						}
					},{
						icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
						xtype:"button",
						tooltip:'刷新',
						handler:function(){
							ckzcStore.load();
						}
					}
				],
		       	cm:new Ext.grid.ColumnModel([
		      		{
						header:'资产类型',
						dataIndex:'zclx.id',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"lxmc",
							valueField:'id',
							store:zclxStore,
							listeners :{
								'select':function(cob,r,ind){
									Ext.apply(zcxxStore.baseParams,{column:'zclx',zclx:r.get('id')});
									zcxxStore.reload();
								}
							}
						})),
						renderer:function(value){
							var result = "";
							zclxStore.each(function(record){
								if(record.get('id') == value){
									result = record.get('lxmc');
									return ;
								}
							});
							return result;
						}				
					},{
						header:'资产名称',
						dataIndex:'zcxx.id',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"zcmc",
							valueField:'id',
							store:zcxxStore
						})),
						renderer:function(value){
							var result = "";
							zcxxStore.each(function(record){
								if(record.get('id') == value){
									result = record.get('zcmc');
									return ;
								}
							});
							return result;
						}				
					},{
						header:'资产初始数量',
						dataIndex:'zccssl',
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
							allowBlank:false
						}))
					}
				]),
				store:ckzcStore
			});
			
			//添加列表
			dataPanel.add(dataGrid);
			initDataTbar([_add,_delete,_save,"-",_refresh,_condition]);
			
			//添加窗口
			var addCkxxWin  =new Ext.Window({ 
				width:550,
				height:460,
				modal:true,
				plain:true,
				layout:'fit', 
				title:'添加仓库信息', 
				closeAction:'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addCkxxForm');
							if(aqf.form.isValid()){
							
								var m = ckzcStore.modified.slice(0);
								var jsonArray = [];
								Ext.each( m, function(item){
									jsonArray.push(item.data);
								});
								if(jsonArray.length == 0){
									return;
								}
								var jsonData = encodeURIComponent(Ext.encode(jsonArray));
								
								aqf.form.doAction('submit',{
									url:contextPath+'/zcgl/ckxxAction.do?method=addCkxx&data='+jsonData,
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				addCkxxWin.hide();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },failure:function(){
			                            Ext.Msg.alert('操作失败','服务器无法连接，请稍后重试！');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						handler:function(){
							addCkxxWin.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'addCkxxForm',
						border:false,
						baseCls:'x-plain',
						autoScroll:true,
						layout:'form',
						labelAlign:'right',
						bodyStyle:'padding:10px',
						defaultType:'fieldset',
						items:[
							{
								title:'仓库信息',
								layout:'column',
								collapsible:true,
								items:[
									{
										border:false,
										labelWidth:70,
										columnWidth:.5,
										layout:'form',
										baseCls:'x-plain',
										labelAlign:'right',
										defaults:{
											xtype:'textfield',
											anchor:'95%'
										},items:[
											{
												fieldLabel:'仓库代码',
												name:'ckdm',
												allowBlank:false
											}, {
												fieldLabel:'仓库名称',
												name:'ckmc',
												allowBlank:false
											}
										]
									},{
										border:false,
										labelWidth:70,
										columnWidth:.5,
										layout:'form',
										baseCls:'x-plain',
										labelAlign:'right',
										defaults:{
											xtype:'textfield',
											anchor:'95%'
										},items:[
											{
												fieldLabel:'仓库地点',
												name:'ckdd',
												allowBlank:false
											}, {
												fieldLabel:'管理员',
												name:'glyxm',
												id:'glyxm',
												allowBlank:false/*,
												readOnly:true,
												allowBlank:false,
												listeners:{
													'focus':function(){
														selectYhWindow.show();
													}
												}*/
											}, {
												fieldLabel:'管理员编号',
												name:'glyid',
												id:'glyid',
												value:'11',
												xtype:'hidden'
											}
										]
									}
								]
							},{
								title:'仓库资产信息',
								layout:'fit',
								collapsible:true,
								autoScroll:true,
								items:[ckzcGrid]
							}					
						]
					}
				]
				});
				
			//修改窗口
			var updCkxxWin  =new Ext.Window({ //弹出窗口
				width:350,
				height:300,
				modal:true,//是否覆盖下面的控件
				plain:true,//让窗体颜色融洽
				layout:'fit', //布局方式(自动填满)
				title:'修改仓库信息', //标题
				closeAction:'hide',//关闭窗口时调用隐藏方法
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('updCkxxForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/zcgl/ckxxAction.do?method=updateCkxx',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				updCkxxWin.hide();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },failure:function(){
			                            Ext.Msg.alert('操作失败','服务器无法连接，请稍后重试！');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						handler:function(){
							updCkxxWin.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'updCkxxForm',
						border:false,
						baseCls:'x-plain',//溶于底色
						autoScroll:true,
						layout:'form', // 布局方式(列模式)
						labelAlign:'right',
						bodyStyle:'padding:10px',
						labelWidth:60,
						defaultType:'textfield',
						defaults:{
							anchor:'95%'//显示比例
						},
						items:[
									{
										fieldLabel:'',
										name:'id',
										xtype:'hidden'
									},{
										fieldLabel:'仓库代码',
										name:'ckdm',
										allowBlank:false
									}, {
										fieldLabel:'仓库名称',
										name:'ckmc',
										allowBlank:false
									}, {
										fieldLabel:'仓库地点',
										name:'ckdd',
										allowBlank:false
									}, {
										fieldLabel:'管理员',
										name:'glyxm',
										allowBlank:false/*,
										id:'upd_glyxm',
										readOnly:true,
										allowBlank:false,
										listeners:{
											'focus':function(){
												selectYhWindow.show();
											}
										}*/
									}, {
										fieldLabel:'管理员编号',
										name:'glyid',
										id:'upd_glyid',
										value:'11',
										xtype:'hidden'
									},{
										fieldLabel:'备注',
										xtype:'textarea',
										name:'bz'
									}
						]
					}
				]
				});
			
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '仓库信息',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:70,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'85%'
				},
	            items:[
					{
						fieldLabel:'仓库代码',
						name:'ckdm'
					},{
						fieldLabel:'仓库名称',
						name:'ckmc'
					},{
						fieldLabel:'仓库地点',
						name:'ckdd'
					},{
						fieldLabel:'管理员',
						name:'glyxm'
					}
				],
				buttons:[
					{
						text:'查 询',
						handler:function(){
							Ext.apply(dataStore.baseParams,{
								conditions:encodeURIComponent(Ext.encode(RelativeActionView.form.getValues()))
							});   
							dataStore.load({
								params: {
									start: 0,
									limit: 15
								}
							});
						}
					},{
						text:'重 置',
						handler:function(){
							RelativeActionView.form.reset();
						}
					}
				]
			}));
			
			var selectYhWindow = new Ext.Window({
				title:'请选择需要指定的用户',
				width:300,
				height:130,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'selectYhForm',
					baseCls:'x-plain',
					labelWidth:40,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					items:getyhTree()
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var ryId = getYhValue();
							var ryName = getYhName();
							Ext.getCmp('glyid').setValue(ryId);
							Ext.getCmp('glyxm').setValue(ryName);
							Ext.getCmp('upd_glyid').setValue(ryId);
							Ext.getCmp('upd_glyxm').setValue(ryName);
							selectYhWindow.hide();
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							selectYhWindow.hide();
						}
					}
				]
			});
			
		
			/*事件控制*****************************/
			
			//dataGrid双击事件
			dataGrid.on('rowdblclick',function(){
				var r = checkboxModel.getSelected();
				Ext.getCmp('updCkxxForm').form.loadRecord(r);
				updCkxxWin.show();
			});
			
			
			//刷新事件
			getRefreshBtn().on('click',function(){
				dataStore.reload();
			})
			//添加事件
			getAddBtn().on('click',function(){
				addCkxxWin.show();
			});
			//修改事件
			getSaveBtn().on('click',function(){
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
					url: contextPath+'/zcgl/ckxxAction.do?method=updateCkxx',
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
					params:{data:jsonData}
				});
			});
			//删除事件
			getDeleteBtn().on('click',function(){
				var r = checkboxModel.getSelected();  
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了："+r.get('ckmc')+"仓库，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/zcgl/ckxxAction.do?method=deleteCkxx&id='+id,
							{
								success:function(response){
									var rs = Ext.decode(response.responseText);
									if(rs.success){
										dataStore.reload();
										mw.hide();
									}else{
										Ext.Msg.alert("提示",rs.message);
									}
								},failure:function(){
									Ext.Msg.alert("提示","服务器连接失败!");
								}
							}
						);
					}
				});
			});

			

			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("资产管理 >> 仓库信息 ","本模块提供仓库的基本信息");
			//var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			dataStore.load({params:{start:0,limit:15}});
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='${qxlx}'>
  <input type="hidden" id='mkid' value='${mkid}'>
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
