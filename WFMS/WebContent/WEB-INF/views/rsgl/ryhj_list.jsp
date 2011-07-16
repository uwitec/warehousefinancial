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
			
			var hjlbStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=HJLBDM',
				fields:["lxdm","dmmc"]
			});
			var jljbStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JLJBDM',
				fields:["lxdm","dmmc"]
			});
			var sjdjStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=SJDJDM',
				fields:["lxdm","dmmc"]
			});
			var xklyStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=XKLYDM',
				fields:["lxdm","dmmc"]
			});
			var bmStore = new Ext.data.JsonStore({
				url:contextPath+'/rsgl/bmglAction.do?method=getAllBmForList',
				fields:["depid","name"]
			});
			
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '人员获奖信息',
				width:240,
	            collapsible: true,
	            autoScroll:true,
	            labelWidth:80,
				labelAlign:'right',
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
						allowBlank:false,
						name:'ry.rybh'
					},{
						fieldLabel:'获奖成果编号',
						allowBlank:false,
						name:'hjcgbh'
					},{
						fieldLabel:'获奖项目编号',
						name:'hjxmbh'
					},{
						fieldLabel:'获奖成果名称',
						name:'hjcgmc'
					},{
						fieldLabel:'奖励类别',
						xtype:'combo',
						name:'hjlbdm',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:hjlbStore
					},{
						fieldLabel:'奖励级别',
						xtype:'combo',
						name:'jljbdm',
						hideTrigger:true,
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:jljbStore
					},{
						fieldLabel:'受奖等级',
						xtype:'combo',
						name:'sjdjdm',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:sjdjStore
					},{
						fieldLabel:'学科领域',
						xtype:'combo',
						name:'xklydm',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:xklyStore
					},{
						fieldLabel:'受奖单位',
						name:'sjdw'
					},{
						fieldLabel:'获奖日期',
						name:'hjrq'
					},{
						fieldLabel:'备注',
						name:'bz'
					}
				]
			}));
			
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/rsgl/ryhjAction.do?method=getRyhjByPage',
				fields:["id","hjcgbh","hjxmbh","hjcgmc","hjrq","hjlbdm","jljbdm","sjdjdm","xklydm","sjdw","ry.id","ry.rybh","ry.ryxm","ry.member.role.depart.name","ry.member.role.depart.depid","bz"]
			});
			
			//列表
			var dataGrid = new Ext.grid.EditorGridPanel({
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
						header:'获奖成果编号',
						sortable : true,
						dataIndex:'hjcgbh'
					},{
						header:'获奖项目编号',
						sortable : true,
						dataIndex:'hjxmbh'
					},{
						header:'获奖成果名称',
						sortable : true,
						dataIndex:'hjcgmc'
					},{
						header:'获奖日期',
						sortable : true,
						dataIndex:'hjrq'
					},{
						header:'获奖类别',
						dataIndex:'hjlbdm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:hjlbStore
						})),
						renderer:function(value){
							var result = "";
							hjlbStore.each(function(record){//企业类型集合遍历
								if(record.get('lxdm') == value){//判断类型代码是否相同
									result = record.get('dmmc');//如果相同,返回类型代码对应的代码名称
									return ;
								}
							});
							return result;
						}
					},{
						header:'奖励级别',
						dataIndex:'jljbdm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:jljbStore
						})),
						renderer:function(value){
							var result = "";
							jljbStore.each(function(record){//企业类型集合遍历
								if(record.get('lxdm') == value){//判断类型代码是否相同
									result = record.get('dmmc');//如果相同,返回类型代码对应的代码名称
									return ;
								}
							});
							return result;
						}
					},{
						header:'授奖等级',
						sortable : true,
						dataIndex:'sjdjdm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:sjdjStore
						})),
						renderer:function(value){
							var result = "";
							sjdjStore.each(function(record){//企业类型集合遍历
								if(record.get('lxdm') == value){//判断类型代码是否相同
									result = record.get('dmmc');//如果相同,返回类型代码对应的代码名称
									return ;
								}
							});
							return result;
						}
					},{
						header:'学科领域',
						sortable : true,
						dataIndex:'xklydm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:xklyStore
						})),
						renderer:function(value){
							var result = "";
							xklyStore.each(function(record){//企业类型集合遍历
								if(record.get('lxdm') == value){//判断类型代码是否相同
									result = record.get('dmmc');//如果相同,返回类型代码对应的代码名称
									return ;
								}
							});
							return result;
						}
					},{
						header:'授奖单位',
						sortable : true,
						dataIndex:'sjdw'
					},{
						header:'备注',
						sortable : true,
						dataIndex:'bz'
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

			var addRyhjWin = new Ext.Window({
				width:550,
				height:460,
				modal:true,
				plain:true,
				layout:'fit',
				title:'添加人员异动信息',
				closeAction: 'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addRyhjForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/rsgl/ryhjAction.do?method=addRyhj',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success){
											aqf.form.reset();
											dataStore.reload();
			                				addRyhjWin.hide();
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
							addRyhjWin.hide();
						}
					}
				],
				items:new Ext.form.FormPanel({
					id:'addRyhjForm',
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
											xtype:'trigger', //trigger：combox的父级
											allowBlank:false,
											name:'rybh',
											onTriggerClick:function(){
												getRyxxListWindow(function(r){
													if(r != null){
														Ext.getCmp('sel_ryxx_id').setValue(r.get('id'));
														Ext.getCmp('addRyhjForm').form.loadRecord(r);
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
							title:'获奖信息',
							layout:'form',
							collapsible:true,
							labelWidth:55,
							labelAlign:'right',
							items:[
								{
									xtype:'panel',
									baseCls:'x-plain',
									layout:'column',
									defaults:{
										border:false,
										labelWidth:82,
										columnWidth:0.5,
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
													id:'sel_ryxx_id',
													name:'ry.id'
												},{
													fieldLabel:'获奖成果编号',
													allowBlank:false,
													name:'hjcgbh'
												},{
													fieldLabel:'获奖项目编号',
													name:'hjxmbh'
												},{
													fieldLabel:'获奖成果名称',
													allowBlank:false,
													name:'hjcgmc'
												},{
													fieldLabel:'获奖类别',
													xtype:'combo',
													hiddenName:'hjlbdm',
													allowBlank:false,
													mode:'local',  //本地读取
													readOnly:'true',
													triggerAction:'all',
													displayField:'dmmc',
													valueField:'lxdm',
													store:hjlbStore						
												}
											]
										},{
											items:[
												{
													fieldLabel:'奖励级别',
													xtype:'combo',
													hiddenName:'jljbdm',
													allowBlank:false,
													mode:'local',  //本地读取
													readOnly:'true',
													triggerAction:'all',
													displayField:'dmmc',
													valueField:'lxdm',
													store:jljbStore
												},{
													fieldLabel:'授奖等级',
													xtype:'combo',
													hiddenName:'sjdjdm',
													mode:'local',  //本地读取
													readOnly:'true',
													triggerAction:'all',
													displayField:'dmmc',
													valueField:'lxdm',
													store:sjdjStore
												},{
													fieldLabel:'学科领域',
													xtype:'combo',
													hiddenName:'xklydm',
													mode:'local',  //本地读取
													readOnly:'true',
													triggerAction:'all',
													displayField:'dmmc',
													valueField:'lxdm',
													store:xklyStore
												},{
													fieldLabel:'授奖单位名称',
													name:'sjdw'
												},{
													fieldLabel:'获奖日期',
													xtype:'datefield',
													value:new Date(),
													format:'Y-m-d',
													name:'hjrq'
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
	            title: '获奖信息查询',
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
						fieldLabel:'获奖成果',
						name:'hjcgmc'
					},{
						fieldLabel:'奖励类别',
						xtype:'combo',
						hiddenName:'hjlbdm',
						mode:'local',
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:hjlbStore
					},{
						fieldLabel:'奖励级别',
						xtype:'combo',
						hiddenName:'jljbdm',
						mode:'local',
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:jljbStore
					},{
						fieldLabel:'受奖等级',
						xtype:'combo',
						hiddenName:'sjdjdm',
						mode:'local',
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:sjdjStore
					},{
						fieldLabel:'学科领域',
						xtype:'combo',
						hiddenName:'xklydm',
						mode:'local',
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:xklyStore
					},{
						fieldLabel:'受奖单位',
						name:'sjdw'
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
				addRyhjWin.show();
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
					url: contextPath+'/rsgl/ryhjAction.do?method=updRyhj',
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
							contextPath+'/rsgl/ryhjAction.do?method=deleteRyyd&id='+id,
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
			setNavigate("人事管理 >> 职工获奖管理","本模块提供职工获奖信息管理");
	
			bachLoad([hjlbStore,jljbStore,sjdjStore,xklyStore,bmStore],function(s){
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
