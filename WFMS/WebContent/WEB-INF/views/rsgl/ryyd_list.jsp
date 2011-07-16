<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>学生信息</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectRyxxWindow.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bachStoreLoad.js"></script>
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
			
			var ydlbStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=RYYDLBDM',
				fields:["lxdm","dmmc"]
			});
			var bmStore = new Ext.data.JsonStore({
				url:contextPath+'/rsgl/bmglAction.do?method=getAllBmForList',
				fields:["depid","name"]
			});
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '人员异动信息',
	            collapsible: true,
	            autoScroll:true,
	            labelWidth:55,
	            autoHeight:true,
	            defaults: {
	            	readOnly:true,
	            	style: {
			            border:'none',
			            background:'none'
					},
	            	anchor:'95%'
			    },
	            defaultType: 'textfield',
	            items :[
					{
						fieldLabel:'人员编号',
						name:'ry.rybh'
					},{
						fieldLabel:'人员姓名',
						name:'ry.ryxm'
					},{
						fieldLabel:'所在部门',
						name:'ry.member.role.depart.name'
					},{
						fieldLabel:'异动类别',
						name:'ydlbdm',
						xtype:'combo',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						store:ydlbStore
					},{
						fieldLabel:'异动原因',
						name:'ydyy'
					},{
						fieldLabel:'异动时间',
						name:'ydsj'
					},{
						fieldLabel:'经办人',
						name:'jbr'
					},{
						fieldLabel:'审核人',
						name:'shr'
					},{
						fieldLabel:'是否离职',
						name:'srlz',
						xtype:'combo',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						store:new Ext.data.JsonStore({
							data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
							fields:['sid','sname']
						})
					}
				]
			}));
			
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/rsgl/ryydAction.do?method=getRyydByPage',
				fields:["id","ydlbdm","ydyy","ydsj","jbr","shr","shrq","sflz","ydqBm","ydhBm","ry.id","ry.rybh","ry.ryxm","ry.member.role.depart.name","ry.member.role.depart.depid","bz"]
			});
			
			//列表
			var dataGrid = new Ext.grid.GridPanel({
				stripeRows: true,
				autoScroll:true,
				trackMouseOver:true,
				border:false,
		        loadMask: true,
		        sm:checkboxModel,
				//列模型
		       	cm:new Ext.grid.ColumnModel([
					{
						header:'职工编号',
						dataIndex:'ry.rybh'
					},{
						header:'职工姓名',
						sortable : true,
						dataIndex:'ry.ryxm'
					},{
						header:'所属部门',
						sortable : true,
						dataIndex:'ry.member.role.depart.name'
					},{
						header:'异动类别',
						sortable : true,
						dataIndex:'ydlbdm',
						renderer:function(v){
							var rs = '';
							ydlbStore.each(function(r){
								if(r.get('lxdm') == v){
									rs = r.get('dmmc');
									return ;
								}
							});
							return rs;
						}
					},{
						header:'异动原因',
						sortable : true,
						dataIndex:'ydyy'
					},{
						header:'异动时间',
						sortable : true,
						dataIndex:'ydsj'
					},{
						header:'经办人',
						sortable : true,
						dataIndex:'jbr'
					},{
						header:'审核人',
						sortable : true,
						dataIndex:'shr'
					},{
						header:'审核日期',
						sortable : true,
						dataIndex:'shrq'
					},{
						header:'是否离职',
						sortable : true,
						dataIndex:'sflz',
						renderer:function(v){
							return v == '1' ? '是' : '否';
						}
					},{
						header:'异动前所在部门',
						sortable : true,
						dataIndex:'ydqBm',
						renderer:function(v){
							return v == null ? '' : v.name;
						}
					},{
						header:'异动后所在部门',
						sortable : true,
						dataIndex:'ydhBm',
						renderer:function(v){
							return v == null ? '' : v.name;
						}
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
			//添加列表
			dataPanel.add(dataGrid);
			initDataTbar([_add,_delete,_save,"-",_refresh,_condition]);

			var addRyydWin = new Ext.Window({
				width:550,
				height:420,
				modal:true,
				plain:true,
				layout:'fit',
				title:'添加人员异动信息',
				closeAction: 'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addRyydForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/rsgl/ryydAction.do?method=addRyyd',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success){
											aqf.form.reset();
											dataStore.reload();
			                				addRyydWin.hide();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },
			                        failure:function(){
			                            Ext.Msg.alert('操作失败','服务器无法连接，请稍后重试！');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						handler:function(){
							addRyydWin.hide();
						}
					}
				],
				items:new Ext.form.FormPanel({
					id:'addRyydForm',
					border:false,
					baseCls:'x-plain',
					autoScroll:true,
					labelWidth:55,
					layout:'form',
					defaultType:'fieldset',
					bodyStyle:'padding:10px',
					items:[
						{
							title:'人员信息',
							layout:'column',
							collapsible:true,
							defaults:{
								border:false,
								labelWidth:55,
								columnWidth:.5,
								layout:'form',
								baseCls:'x-plain',
								labelAlign:'right',
								defaults:{
									xtype:'textfield',
									readOnly:true,
									anchor:'95%'
								}
							},
							items:[
								{
									items:[
										{
											fieldLabel:'人员编号',
											xtype:'trigger',
											allowBlank:false,
											name:'rybh',
											onTriggerClick:function(){
												getRyxxListWindow(function(r){
													if(r != null){
														Ext.getCmp('sel_ryxx_id').setValue(r.get('id'));
														Ext.getCmp('addRyydForm').form.loadRecord(r);
													}
												});
											}
										},{
											fieldLabel:'所在部门',
											name:'member.role.depart.name'
										}
									]
								},{
									items:[
										{
											fieldLabel:'人员姓名',
											name:'ryxm'
										},{
											fieldLabel:'在职状态',
											name:'zt',
											xtype:'combo',
											readOnly:true,
											displayField:"ztmc",
											valueField:'zt',
											mode:'local',
											store:new Ext.data.JsonStore({
												data:[{zt:'0',ztmc:'离职'},{zt:'1',ztmc:'在职'}],
												fields:["zt","ztmc"]
											})
										}
									]
								}
							]
						},{
							title:'异动信息',
							layout:'form',
							collapsible:true,
							labelWidth:70,
							labelAlign:'right',
							items:[
								{
									xtype:'panel',
									baseCls:'x-plain',
									layout:'column',
									defaults:{
										border:false,
										labelWidth:70,
										columnWidth:.5,
										layout:'form',
										baseCls:'x-plain',
										labelAlign:'right',
										defaults:{
											xtype:'textfield',
											anchor:'95%'
										}
									},
									items:[
										{
											items:[
												{
													xtype:'hidden',
													name:'ry.id',
													id:'sel_ryxx_id'
												},{
													fieldLabel:'异动类别',
													hiddenName:'ydlbdm',
													xtype:'combo',
													readOnly:true,
													allowBlank:false,
													displayField:"dmmc",
													valueField:'lxdm',
													mode:'local',
													triggerAction:'all',
													store:ydlbStore
												},{
													fieldLabel:'异动时间',
													xtype:'datefield',
													name:'ydsj',
													value:new Date(),
													allowBlank:false,
													format:'Y-m-d'
												},{
													fieldLabel:'异动原因',
													name:'ydyy'
												},{
													fieldLabel:'是否离职',
													hiddenName:'sflz',
													xtype:'combo',
													readOnly:true,
													displayField:"ztmc",
													valueField:'zt',
													triggerAction:'all',
													mode:'local',
													value:'1',
													store:new Ext.data.JsonStore({
														data:[{zt:'0',ztmc:'否'},{zt:'1',ztmc:'是'}],
														fields:["zt","ztmc"]
													})
												}
											]
										},{
											items:[
												{
													fieldLabel:'异动后部门',
													hiddenName:'ydhBm.depid',
													xtype:'combo',
													mode:'local',
													readOnly:true,
													triggerAction:'all',
													displayField:"name",
													valueField:'depid',
													store:bmStore
												},{
													fieldLabel:'经办人',
													allowBlank:false,
													value:'${userName}',
													name:'jbr'
												},{
													fieldLabel:'审核人',
													name:'shr'
												},{
													fieldLabel:'审核日期',
													xtype:'datefield',
													name:'shrq',
													format:'Y-m-d'
												}
											]
										},{
											columnWidth:1,
											items:[
												{
													fieldLabel:'备注',
													xtype:'textarea',
													anchor:'97.5%',
													name:'bz',
													height:60
												}
											]
													
										}
									]
								}
							]
						}
					]
				})
			});
			
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '异动信息查询',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:55,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'95%'
				},
	            items:[
					{
						fieldLabel:'人员编号',
						name:'ry.rybh'
					},{
						fieldLabel:'人员姓名',
						name:'ry.ryxm'
					},{
						fieldLabel:'所在部门',
						name:'ry.member.role.depart.name'
					},{
						fieldLabel:'异动类别',
						hiddenName:'ydlbdm',
						xtype:'combo',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:ydlbStore
					},{
						fieldLabel:'异动原因',
						name:'ydyy'
					},{
						fieldLabel:'异动时间',
						name:'ydsj'
					},{
						fieldLabel:'是否离职',
						name:'srlz',
						xtype:'combo',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						store:new Ext.data.JsonStore({
							data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
							fields:['sid','sname']
						})
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
			
			
			/*事件控制*****************************/
			
			//刷新事件
			getRefreshBtn().on('click',function(){
				dataStore.reload();
			})
			//添加事件
			getAddBtn().on('click',function(){
				addRyydWin.show();
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
					url: contextPath+'/rsgl/lsglAction.do?method=updateLs',
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
			});
			//删除事件
			getDeleteBtn().on('click',function(){
				var r = checkboxModel.getSelected();
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了人员："+r.get('ry.ryxm')+"的异动信息（注：即使删除异动信息，人员异动后的结果也不会发生变化），是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/rsgl/ryydAction.do?method=deleteRyyd&id='+id,
							{
								success:function(response){
									var rs = Ext.decode(response.responseText);
									if(rs.success){
										dataStore.reload();
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
			});

			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("人事管理 >> 职工异动管理","本模块提供职工异动信息管理");
	
			bachLoad([ydlbStore,bmStore],function(s){
				if(s){
					dataStore.load({params:{start:0,limit:15}});
				}else{
					Ext.Msg.alert('提示','数据加载失败，请联系系统管理员！');
				}
			});
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
