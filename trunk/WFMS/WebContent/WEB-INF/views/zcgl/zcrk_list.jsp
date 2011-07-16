<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>资产入库</title>
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
	//资产类型store
	var zclxStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/zclxAction.do?method=getZclxList',
		fields:['id',"lxdm","lxmc","bz"]
	});
	//仓库信息store
	var ckxxStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/ckxxAction.do?method=getCkxxList',
		fields:["id","ckdm","ckmc","glyid"],
		autoLoad:true
	});
	//供货商信息store
	var kyxxStore = new Ext.data.JsonStore({
      	url:contextPath+"/jxc/KhwhAction.do?method=getAllKyxxByJoin&khlb=0",
		fields:["id","jdmc"],
		autoLoad:true
	});
	//资产信息store
	var zcxxStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/zcxxAction.do?method=getZcxxList&ty=1',
		fields:['id',"zcdm","zcmc","zclx","zcgg","zcdw","scrq","bz"]
	});
	
	
	
	//资产明细store
	var zcmxStore = new Ext.data.JsonStore({
		url:contextPath+'/zcgl/zcrkAction.do?method=getRkdmxByPage',
		fields:['id',"zclx.lxdm","zclx.lxmc","zclx.id",
		'zcxx.id',"zcxx.zcdm","zcxx.zcmc","zcxx.zclx","zcxx.zcgg","zcxx.zcdw",
		"rksl","scrq","bzq","bz"]
	});
	
	//列表数据对象
	var dataStore = new Ext.data.JsonStore({
		autoDestroy: true,
		pruneModifiedRecords:true,
		root:'dataList',
		totalProperty:'totalCount',
      	url:contextPath+'/zcgl/zcrkAction.do?method=getZcrkdByPage',
		fields:["id","rkdbh","ckxx.id","ckxx.ckmc","rksj","rkqfzrxm","rkqfzrid",
		"lylx","lydh","czrid","czrxm","czsj","bz","zt"]
	});
	
	 var lylxStore =	new Ext.data.JsonStore({
		data:[{lxdm:'2',dmmc:'购买'},{lxdm:'1',dmmc:'赠送'},{lxdm:'3',dmmc:'盘存入库'},{lxdm:'0',dmmc:'转仓库'}],
		fields:['lxdm','dmmc']
	});
	zcmxStore.load();
	zclxStore.load();
	zcxxStore.load();
	
	
		infoView.add(new Ext.form.FieldSet({
        title: '资产信息',
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
        items :[]
	}));
	
	
	//列表
	var dataGrid = new Ext.grid.EditorGridPanel({
		stripeRows: true,
		autoScroll:true,
		trackMouseOver:true,
		border:false,
        loadMask: true,
        height :500,
        sm:checkboxModel,
       	cm:new Ext.grid.ColumnModel([
      		 {
				header:'入库单编号',
				dataIndex:'rkdbh'
			}, {
				header:'来源公司',
				width:200,
				dataIndex:'rkqfzrxm'
			}, {
				header:'来源单号',
				width:120,
				dataIndex:'lydh'
			},{
				header:'所属仓库',
				dataIndex:'ckxx.id',
				width:120,
				renderer:function(value){
					var result = "";
					ckxxStore.each(function(record){
						if(record.get('id') == value){
							result = record.get('ckmc');
							return ;
						}
					});
					return result;
				}				
			},{
				header:'来源类型',
				dataIndex:'lylx',
				width:60,
				renderer:function(value){
					var result = "";
					lylxStore.each(function(record){
						if(record.get('lxdm') == value){
							result = record.get('dmmc');
							return ;
						}
					});
					return result;
				}				
			}, {
				header:'费用状态',
				width:60,
				dataIndex:'zt'
			}, {
				header:'操作人',
				width:60,
				dataIndex:'czrxm'
			}, {
				header:'操作时间',
				width:80,
				dataIndex:'czsj'
			}
		]),
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
	
	var gzRecord = new Ext.data.Record.create(['id',"zclx.lxdm","zclx.lxmc","zclx.id",
		'zcxx.id',"zcxx.zcdm","zcxx.zcmc","zcxx.zclx","zcxx.zcgg","zcxx.zcdw",
		"rksl","scrq","bzq","bz","scsx","zclxId","zcxxId","zcgg","zcdw"]);
	
	//资产明细Grid
	var zcmxGrid = new Ext.grid.EditorGridPanel({
				stripeRows: true,
				autoScroll:true,
				trackMouseOver:true,
				width:460,
				height:160,
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
							if(zcmxStore.getCount() == 0){
								sx = 0;
							}else{
								sx = zcmxStore.getAt(zcmxStore.getCount()-1).get('scsx');
							}
							var r = new gzRecord({
								id:'',
								zclxId:'',
								zcxxId:'',
								zcgg:'',
								zcdw:'',
								rksl:'1',
								scrq:'',
								bzq:'',
								scsx:sx+1
							});
							zcmxStore.add(r);
						}
					},{
						icon:'${pageContext.request.contextPath}/imgs/refresh.gif',
						xtype:"button",
						tooltip:'刷新',
						handler:function(){
							zcmxStore.load();
						}
					}
				],
		       	cm:new Ext.grid.ColumnModel([
		      		{
						header:'资产类型',
						dataIndex:'zclx.id',
						width:100,
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"lxmc",
							valueField:'id',
							store:zclxStore,
							listeners  :{
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
						width:150,
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
						header:'入库数量',
						dataIndex:'rksl',
						width:70,
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
							allowBlank:false
						}))
					},{
						header:'入库价格',
						dataIndex:'rkjg',
						width:70,
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
							allowBlank:false
						}))
					},{
						header:'已付金额',
						dataIndex:'bcje',
						width:70,
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
							allowBlank:false
						}))
					},{
						header:'生产日期',
						dataIndex:'scrq',
						width:90,
						editor:new Ext.form.DateField({
							readOnly:true,
							format:'Y-m-d'
						})
					},{
						header:'保质期',
						dataIndex:'bzq',
						width:70,
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
						}))
					}
				]),
				store:zcmxStore
			});
			
			//添加列表
			dataPanel.add(dataGrid);
			initDataTbar([_add,_delete,"-",_refresh,_condition]);
			//添加窗口
			var jzqzWin = new Ext.Window({
				width:560,
				height:480,
				modal:true,
				plain:true,
				layout:'fit',
				title:'入  库',
				closeAction: 'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('jzqzForm');
							if(aqf.form.isValid()){
							
							var m = zcmxStore.modified.slice(0);
							var jsonArray = [];
							Ext.each( m, function(item){
								jsonArray.push(item.data);
							});
							if(jsonArray.length == 0){
								return;
							}
							var jsonData = encodeURIComponent(Ext.encode(jsonArray));
								aqf.form.doAction('submit',{
									url:contextPath+'/zcgl/zcrkAction.do?method=addZcrkd&data='+jsonData,
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
										zcmxStore.removeAll();
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				jzqzWin.hide();
			                				window.location = contextPath+"/zcgl/zcrkAction.do?method=initZcrk&id=42371";
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },failure:function(){
			                        	zcmxStore.removeAll();
			                            Ext.Msg.alert('操作失败','服务器无法连接，请稍后重试！');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						handler:function(){
							zcmxStore.removeAll();
							jzqzWin.hide();
						}
					}
				],
				items:new Ext.form.FormPanel({
					id:'jzqzForm',
					border:false,
					baseCls:'x-plain',
					autoScroll:true,
					labelWidth:55,
					layout:'form',
					defaultType:'fieldset',
					bodyStyle:'padding:10px',
					items:[
						{
							title:'入库单信息',
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
									},
									items:[
										{
											fieldLabel:'入库单编号',
											allowBlank:false,
											name:'rkdbh'
										},{
											fieldLabel:'所入仓库',
											xtype:'combo',
											hiddenName:'ckxx.id',
											mode:'remote',
											readOnly:true,
											triggerAction:'all',
											allowBlank:false,
											displayField:"ckmc",
											valueField:'id',
											store:ckxxStore
									   },{
									   		fieldLabel:'入库时间',
									   		xtype:'datefield',
									   		name:'rksj',
									   		allowBlank:false,
									   		value:new Date(),
									   		format:'Y-m-d'
									   },{
											fieldLabel:'',
											name:'czrxm',
											value:'${userName}',
											xtype:'hidden'
										},{
											fieldLabel:'',
											name:'czrid',
											value:'${userId}',
											xtype:'hidden'
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
									},
									items:[
										{
											fieldLabel:'来源公司',
											xtype:'combo',
											hiddenName:'rkqfzrxm',
											mode:'remote',
											readOnly:true,
											triggerAction:'all',
											allowBlank:false,
											displayField:"jdmc",
											valueField:'jdmc',
											store:kyxxStore
										},{
											fieldLabel:'来源单号',
											allowBlank:false,
											name:'lydh'
										},{
											fieldLabel:'来源类型',
											xtype:'combo',
											hiddenName:'lylx',
											mode:'local',
											allowBlank:false,
											readOnly:true,
											triggerAction:'all',
											displayField:"dmmc",
											valueField:'lxdm',
											store:lylxStore
										},{
											fieldLabel:'',
											name:'rkqfzrid',
											xtype:'hidden',
											id:'rkqfzrid'
										},{
											fieldLabel:'',
											name:'czsj',
											xtype:'datefield',
									   		format:'Y-m-d',
											hidden:true,
											value:new Date()
										}
									]
								}
							]
						},{
							title:'入库单明细',
							layout:'fit',
							collapsible:true,
							autoScroll:true,
							items:[zcmxGrid]
						}
						
					]
				})
			});
			
			//列表数据对象
			var rkdmxStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		      	url:contextPath+'/zcgl/zcrkAction.do?method=getRkdmxByPage',
				fields:['id',"zclx.lxdm","zclx.lxmc","zclx.id","rkd.ckxx.id","rkd.id","rkd.ckxx.ckmc","rkd.ckxx.glyxm","rkd.rksj",
				'zcxx.id',"zcxx.zcdm","zcxx.zcmc","zcxx.zcdw",
				"rksl","scrq","rkjg","bzq","bz","zje","bcje"]
			});
			var rkdmxGrid = new Ext.grid.EditorGridPanel({
				stripeRows: true,
				autoScroll:true,
				trackMouseOver:true,
				border:false,
		        loadMask: true,
		        height:370,
		       	cm:new Ext.grid.ColumnModel([
				      		{
								header:'物资名称',
								width:120,
								dataIndex:'zcxx.zcmc'			
							},{
								header:'物资代码',
								width:60,
								dataIndex:'zcxx.zcdm'		
							},{
								header:'所属仓库',
								width:120,
								dataIndex:'rkd.ckxx.ckmc'			
							},{
								header:'所属类别',
								width:70,
								dataIndex:'zclx.lxmc'
							},{
								header:'入库数量',
								width:70,
								dataIndex:'rksl'
							},{
								header:'应付金额',
								width:70,
								dataIndex:'zje'
							},{
								header:'已付金额',
								width:70,
								dataIndex:'bcje'
							},{
								header:'仓库管理员',
								dataIndex:'rkd.ckxx.glyxm'
							},{
								header:'生产日期',
								dataIndex:'scrq'
							},{
								header:'入库时间',
								dataIndex:'rkd.rksj'
							},{
								header:'备注',
								dataIndex:'bz'
							}
							
							
						]),
				store:rkdmxStore,
				bbar: new Ext.PagingToolbar({
		            pageSize: 15,
		            store: rkdmxStore,
		            displayInfo: true,
		            beforePageText:'第',
		            afterPageText:'页，共 {0} 页',
		            displayMsg: '当前为第<font style="color:red">{0} - {1}</font> 条记录，共<font style="color:red">{2}</font> 条记录',
		            emptyMsg: "没有找到相关信息"
		        })
			});
			//查看选中入库单所有入库资产
			var rkdmxWin = new Ext.Window({
				width:600,
				height:480,
				modal:true,
				plain:true,
				layout:'fit',
				title:'入库单详细',
				closeAction: 'hide',
				buttons:[
					{
						text:'关  闭',
						handler:function(){
							rkdmxWin.hide();
						}
					}
				],
				items:new Ext.form.FormPanel({
					id:'rkdmxForm',
					border:false,
					baseCls:'x-plain',
					autoScroll:true,
					labelWidth:55,
					layout:'form',
					bodyStyle:'padding:10px',
					items:[ rkdmxGrid]
				})
			});
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '资产信息',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:70,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'85%'
				},items:[
					{
						fieldLabel:'所属仓库',
						xtype:'combo',
						hiddenName:'ckxx.id',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"ckmc",
						valueField:'id',
						store:ckxxStore
				   },{
				   		fieldLabel:'入库时间',
				   		xtype:'datefield',
				   		name:'rksj',
				   		format:'Y-m-d'
				   },{
						fieldLabel:'来源单号',
						name:'lydh'
				   },{
						fieldLabel:'来源类型',
						xtype:'combo',
						hiddenName:'lylx',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:lylxStore
				  }
				],
				buttons:[{
					text:'查 询',
					handler:function(){
						Ext.apply(dataStore.baseParams,{
							conditions:encodeURIComponent(Ext.encode(RelativeActionView.form.getValues()))
						});   
						dataStore.load({ params: { start: 0, limit: 15 } });
					}
				},{
					text:'重 置',
					handler:function(){
						RelativeActionView.form.reset();
					}
				}]
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
							Ext.getCmp('rkqfzrid').setValue(ryId);
							Ext.getCmp('rkqfzrxm').setValue(ryName);
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
			
			dataGrid.on('rowdblclick',function(){
				var r = checkboxModel.getSelected();
				Ext.apply(rkdmxStore.baseParams,{rkdId:r.get('id')});
				rkdmxStore.reload();
				rkdmxWin.show();
			});
			
			//刷新事件
			getRefreshBtn().on('click',function(){
				dataStore.reload();
			})
			
			//添加事件
			getAddBtn().on('click',function(){
				jzqzWin.show();
			});
			
			
			//删除事件
			getDeleteBtn().on('click',function(){
				var r = checkboxModel.getSelected();  
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了："+r.get('rkdbh')+"入库单，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/zcgl/zcrkAction.do?method=deleteRkd&id='+id,
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
			//var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			setNavigate("资产管理 >> 资产入库 ","本模块提供资产入库的相关信息");
			dataStore.load({params:{start:0,limit:15}});
			lylxStore.load();
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
