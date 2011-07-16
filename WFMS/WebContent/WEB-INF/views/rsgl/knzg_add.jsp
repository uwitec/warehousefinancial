<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>困难职工</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bachStoreLoad.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectRyxxWindow.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/edit_viewport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/add_viewport.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			//困难类别
			var knlbdmStore = new Ext.data.JsonStore({  
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=KNZGKNLB',
				fields:["lxdm","dmmc"]
			});
			//残疾类别
			var cjlbdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=CJLBDM',
				fields:["lxdm","dmmc"]
			});
			//劳模类型
			var lmlxdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=LMLXDM',
				fields:["lxdm","dmmc"]
			});
			//住房类型
			var zflxdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=FWCQDM',
				fields:["lxdm","dmmc"]
			});
			//单位性质
			var dwxzdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=DWXZDM',
				fields:["lxdm","dmmc"]
			});
			//户口性质  
			var hkxzdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=HKXZDM',
				fields:["lxdm","dmmc"]
			});
			//专业类别
			var zylbStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=ZYLBDM',
				fields:["lxdm","dmmc"]
			});
			//文化程度
			var whcddmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=WHCDDM',
				fields:["lxdm","dmmc"]
			});
			//国籍
			var gjdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=GJDM',
				fields:["lxdm","dmmc"]
			});
			//政治面貌
			var zzmmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=ZZMMDM',
				fields:["lxdm","dmmc"]
			});
			//健康状况
			var jkzkStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JKZKDM',
				fields:["lxdm","dmmc"]
			});
			//查询人员所有家庭成员信息
			var jtcyStore = new Ext.data.JsonStore({  
				autoDestroy: true,
				root: 'dataList', 
				totalProperty:'totalCount',
				url:contextPath+'/rsgl/ryjtcyAction.do?method=getRyjtcyByPage',
				fields:["id","ry.id","ch","xm","xb","dwdh","zzmmdm","bz","csrq"
				,"sfzh","jkzkdm","ysr","lxfs","dwhxx","sf"]
			});
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({ //数据存储
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/rsgl/knzgAction.do?method=getKnzgByPage',
				fields:["id","ry.id","ry.ryxm","ry.member.role.depart.name","ry.cjgzsj","ry.csd","ry.gjdm","ry.sjhm",
						"knlbdm","cjlbdm","sf","lmlxdm","zflxdm","jzmj","lxdh","gzrq","sshy",
						"jtzz","gzdw","dwxzdm","qyzk","sfdq","brypjsr","jtndzsr","jtrk","jtnrjsr",
						"hkszdxzqh","sfjryb","sfyydzjnl","sfwljyjt","bz","hkxzdm"]
			});
			
			//添加人员家庭成员
			var _add ={
				id:'addBtn',
				tooltip:{title:'添加信息',text:'id:addBtn'},
				icon:contextPath+'/imgs/add.png',
				handler:function(){
					addRyjtcyWin.show();
				}
			};
			//删除人员家庭成员
			var _delete ={
				id:'deleteBtn',
				tooltip:{title:'删除信息',text:'id:deleteBtn'},
				icon:contextPath+'/imgs/delete.png',
				handler:function(){
					var r = Ext.getCmp('grid_ryjtcy').getSelectionModel().getSelections()[0];   //r ：一个选中的实体记录
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了姓名为："+r.get('xm')+"的家庭成员信息，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/rsgl/ryjtcyAction.do?method=deleteRyjtcy&id='+id,
							{
								success:function(response){
									var rs = Ext.decode(response.responseText);
									if(rs.success){
										jtcyStore.reload();
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
			};
			//修改人员家庭成员
			var _save ={
				id:'saveBtn',
				tooltip:{title:'保存信息',text:'id:saveBtn'},
				icon:contextPath+'/imgs/save.gif',
				handler:function(){
					var m = jtcyStore.modified.slice(0);
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
						url: contextPath+'/rsgl/ryjtcyAction.do?method=updateRyjtcy',
						success: function(response){
							var rs = Ext.decode(response.responseText);
							if (rs.success) {
								waitMsg.hide();
								jtcyStore.reload();
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
			};
			//刷新人员家庭成员
			var _refresh ={
				id:'refreshBtn',
				tooltip:{title:'刷新',text:'id:refreshBtn'},
				icon:contextPath+'/imgs/refresh.gif',
				handler:function(){
					jtcyStore.reload();
				}
			};
			new Ext.QuickTips.init();
			var mainFrame = new MianFrame();
			setNavigate("人事管理 >> 困难职工管理 >> 添加困难职工信息","本页面提供添加困难职工的基本信息");
			var form = new Ext.form.FormPanel({
				layout:'form',
				border:false,
				bodyStyle:'padding:10px',
				defaults:{
					xtype:'fieldset',
					margins:'4 4 4 4',
					collapsible:true
				},
				items:[
					{
						title:'人员信息',
						layout:'column',
						defaults:{
							border:false,
							columnWidth:.5,
							layout:'form',
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
										id:'_ry.rybh'
								 	},{
										fieldLabel:'人员姓名',
										name:'ryxm'
								 	},{
										fieldLabel:'出生地',
										name:'csd'
								 	}
								]
							},{	
								items:[
									{
										fieldLabel:'入职时间',
										name:'rzsj'										
									},{
										fieldLabel:'国籍',
										hiddenName:'gjdm',
										mode:'local',
										xtype:'combo',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:gjdmStore		
									},{
										fieldLabel:'手机号',
										name:'sjhm'										
									}
								]							
							}
						],buttons:[
							{
								text:'选择人员',
								handler:function(){
									getRyxxListWindow(ryxxResponseFn);
									
								}
							}
						]
					},{
						
						title:'人员家庭成员',
						height:300,
						layout:'border',
						items:[
							{
								id:'grid_ryjtcy',
								xtype:'editorgrid',
								region:'center',
								tbar:[_add,_delete,_save,_refresh],
								stripeRows: true,
								autoScroll:true,
								trackMouseOver:true,
								sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
								cm:new Ext.grid.ColumnModel([
									{
										header:'与人员关系',
										sortable : true,
										dataIndex:'ch'
									},{
										header:'姓名',
										sortable : true,
										dataIndex:'xm'
									},{
										header:'政治面貌',
										dataIndex:'zzmmdm',
										editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
										mode:'local',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:zzmmStore 
										})),
										renderer:function(value){
											var result = "";
											zzmmStore.each(function(record){
												if(record.get('lxdm') == value){
													result = record.get('dmmc');
													return ;
												}
											});
											return result;
										}
									},{
										header:'单位电话',
										dataIndex:'dwdh',
										editor:new Ext.grid.GridEditor(new Ext.form.TextField({
										}))
									},{
										header:'性别',
										sortable : true,
										dataIndex:'xb',
										renderer:function(value){
											return value == 0 ? '女' : '男'; 
										}
									},{
										header:'出生日期',
										dataIndex:'csrq'
									},{
										header:'身份证号',
										sortable : true,
										dataIndex:'sfzh'
									},{
										header:'健康状况',
										dataIndex:'jkzkdm',
										editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
										mode:'local',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:jkzkStore 
										})),
										renderer:function(value){
											 var result="";
											 jkzkStore.each(function(record){
											 	if(record.get('lxdm') == value){
													result = record.get('dmmc');
													return ;
												}
											 });
											 return result;
										}
									},{
										header:'月收入',
										dataIndex:'ysr',
										editor:new Ext.grid.GridEditor(new Ext.form.TextField({
										}))
									},{
										header:'联系方式',
										dataIndex:'lxfs',
										editor:new Ext.grid.GridEditor(new Ext.form.TextField({
										}))
									},{
										header:'单位或学校',
										dataIndex:'dwhxx',
										editor:new Ext.grid.GridEditor(new Ext.form.TextField({
										}))
									},{
										header:'身份',
										dataIndex:'sf',
										editor:new Ext.grid.GridEditor(new Ext.form.TextField({
										}))
									},{
										header:'备注',
										dataIndex:'bz',
										editor:new Ext.grid.GridEditor(new Ext.form.TextField({
										}))
									}
								]),
								store:jtcyStore,
								bbar: new Ext.PagingToolbar({
						            pageSize: 15,
						            store: jtcyStore,
						            displayInfo: true,
						            beforePageText:'第',
						            afterPageText:'页，共 {0} 页',
						            displayMsg: '当前为第<font style="color:red">{0} - {1}</font> 条记录，共<font style="color:red">{2}</font> 条记录',
						            emptyMsg: '没有找到相关信息'
						        })
								}
							]
						},{
						title:'困难信息填写',
						layout:'column',
						defaults:{
							border:false,
							columnWidth:.5,
							layout:'form',
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
										id:'form_ryid' //人员ID
									},{
										fieldLabel:'困难类别',
										xtype:'combo',
										hiddenName:'knlbdm',
										mode:'local',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:knlbdmStore,
										id:'knzg_knlbdm' 
									},{
										fieldLabel:'残疾类别',
										xtype:'combo',
										hiddenName:'cjlbdm',
										mode:'local',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:cjlbdmStore,
										id:'knzg_cjlbdm' 
									},{
										fieldLabel:'身份',
										name:'sf',
										id:'knzg_sf'
									}, {
										fieldLabel:'劳模类型',
										xtype:'combo',
										hiddenName:'lmlxdm',
										id:'knzg_lmlxdm',
										mode:'local',
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:lmlxdmStore  
									}, {
										fieldLabel:'住房类型',
										xtype:'combo',
										hiddenName:'zflxdm',
										id:'knzg_zflxdm',
										mode:'local',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:zflxdmStore  
									},{
										fieldLabel:'建筑面积',
										xtype:'numberfield',
										maxValue:99999,
										name:'jzmj',
										id:'knzg_jzmj'
									},{
										fieldLabel:'联系电话',
										name:'lxdh',
										id:'knzg_lxdh'
									},{
										fieldLabel:'工作日期',
										name:'gzrq',
										id:'knzg_gzrq'
									},{
										fieldLabel:'所属行业',
										name:'sshy',
										id:'knzg_sshy'
									},{
										fieldLabel:'家庭住址',
										name:'jtzz',
										id:'knzg_jtzz'
									},{
										fieldLabel:'企业状况',
										name:'qyzk',
										id:'knzg_qyzk'
									},{
										fieldLabel:'工作单位',
										name:'gzdw',
										id:'knzg_gzdw'
									}
								]	
							},{
								items:[
									{
										fieldLabel:'单位性质',
										hiddenName:'dwxzdm',
										id:'knzg_dwxzdm',
										xtype:'combo',
										mode:'local',
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:dwxzdmStore   
									},{
										fieldLabel:'是否单亲',
										hiddenName:'sfdq',
										id:'knzg_sfdq',
										mode:'local',
										xtype:'combo',
										triggerAction:'all',
										displayField:'sname',
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									},{
										fieldLabel:'本人收入/月',
										xtype:'numberfield',
										maxValue:999999,
										allowBlank:false,
										name:'brypjsr',
										id:'knzg_brypjsr'
									},{
										fieldLabel:'家庭收入/年',
										xtype:'numberfield',
										maxValue:9999999999,
										allowBlank:false,
										name:'jtndzsr',
										id:'knzg_jtndzsr'
									},{
										fieldLabel:'家庭人口',
										xtype:'numberfield',
										maxValue:99,
										allowBlank:false,
										name:'jtrk',
										id:'knzg_jtrk'
									},{
										fieldLabel:'人均收入/年',
										xtype:'numberfield',
										maxValue:9999999999,
										allowBlank:false,
										name:'jtnrjsr',
										id:'knzg_jtnrjsr'
									},{
										fieldLabel:'户口所在地',
										name:'hkszdxzqh',
										id:'knzg_hkszdxzqh'
									},{
										fieldLabel:'户口性质',
										hiddenName:'hkxzdm',
										id:'knzg_hkxzdm',
										xtype:'combo',
										mode:'local',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:hkxzdmStore    
									},{
										fieldLabel:'是否入医保',
										hiddenName:'sfjryb',
										id:'knzg_sfjryb',
										mode:'local',
										xtype:'combo',
										triggerAction:'all',
										displayField:'sname',
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									},{
										fieldLabel:'能否自救',
										hiddenName:'sfyydzjnl',
										id:'knzg_sfyydzjnl',
										mode:'local',
										xtype:'combo',
										triggerAction:'all',
										displayField:'sname',
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									},{
										fieldLabel:'是否无人就业',
										hiddenName:'sfwljyjt',
										id:'knzg_sfwljyjt',
										mode:'local',
										xtype:'combo',
										triggerAction:'all',
										displayField:'sname',
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									}
								]
							}
						],buttons:[
							{
								text:'确定',
								handler:function(){
									if(form.form.isValid()){
										form.form.doAction('submit',{
											url:contextPath+'/rsgl/knzgAction.do?method=addKnzg',
											method:'post',
											waitTitle:'提示信息',                                                    
					                        waitMsg:'数据更新中，请稍候...',
					                        success:function(form1,action){
					                        	if(action.result.success){
													Ext.Msg.confirm('操作提示','是否继续？',function(btn){
														if(btn=="no"){
															history.back();
														}else{
															form.form.reset();
														}
													});
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
							}
						]				
					}
				]
			});
			//选择人员信息回调函数			
			function ryxxResponseFn(r){ 
				if(r != null){  //判断是否选中一条人员信息
					Ext.getCmp('_ry.rybh').setValue(r.get('rybh'));
					Ext.getCmp('form_ryid').setValue(r.get('id'));
					Ext.getCmp('_ry.id').setValue(r.get('id'));
					form.form.loadRecord(r);   //把选中的人员信息填充到页面的form中
					Ext.apply(jtcyStore.baseParams,{
						id:r.get('id')
					});
					jtcyStore.load({  //加载选中人员的家庭成员信息
						params:{
							start:0,
							limit:15
						}
					});
					getKnzgByRyId(r.get('id'));  //调用：根据人员ID查询困难职工的方法
				}
			}
			//根据人员ID查询困难职工
			function getKnzgByRyId(ryId){
				var mw = Ext.MessageBox.wait('数据更新中，请稍候...','提示信息');
				Ext.lib.Ajax.request(
					'POST',
					contextPath+'/rsgl/knzgAction.do?method=getKnzgByRyId&id='+ryId,
					{
						success:function(response){
							mw.hide();
							var rs = Ext.decode(response.responseText);
							if(rs != '' && rs != null){
								var tempStr = '';
								for(var key in rs) //增强for循环
								{
									if(Ext.getCmp('knzg_'+key) != null){  //判断是否有该控件
										Ext.getCmp('knzg_'+key).setValue(rs[key]);
									}
								}
							}
						},
						failure:function(){
							Ext.Msg.alert("提示","服务器连接失败!");
						}
					}
				);
			}
			Ext.getCmp('editPanel').add(form);
			bachLoad([knlbdmStore ,cjlbdmStore ,lmlxdmStore ,zflxdmStore ,dwxzdmStore ,hkxzdmStore,zylbStore,whcddmStore ,gjdmStore,jkzkStore,zzmmStore],function(s){
				if(s){
					dataStore.load({params:{start:0,limit:15}});
				}else{
					Ext.Msg.alert('提示','数据加载失败，请联系系统管理员！');
				}
			});
			//添加窗口
			var addRyjtcyWin  =new Ext.Window({ //弹出窗口
				width:520,
				height:370,
				modal:true,
				plain:true,
				layout:'fit', 
				title:'人员家庭成员信息', 
				closeAction:'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addRyjtcyForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/rsgl/ryjtcyAction.do?method=addRyjtcy',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
			                				addRyjtcyWin.hide();
											jtcyStore.reload();
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
							addRyjtcyWin.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'addRyjtcyForm',
						border:false,
						baseCls:'x-plain',
						autoScroll:true,
						layout:'column',
						labelAlign:'right',
						bodyStyle:'padding:10px',
						defaults:{
							xtype:'fieldset',
							anchor:'95%'
						},
						items:[
						{
							columnWidth:0.5,
							labelWidth:75,
							border:false,
							defaultType:'textfield',
							defaults:{
								anchor:'95%'
							},items:[
									{
										xtype:'hidden',
										allowBlank:false,
										name:'ry.id',
										id:'_ry.id'
									},{
										fieldLabel:'称呼',
										allowBlank:false,
										name:'ch'
									 },{
									 	fieldLabel:'姓名',
										allowBlank:false,
										name:'xm'
									 },{
									 	fieldLabel:'政治面貌',
										xtype:'combo',
										hiddenName:'zzmmdm',
										mode:'local',  
										readOnly:'true',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:zzmmStore
									 },{
									 	fieldLabel:'单位电话',
										name:'dwdh'
									 },{
									 	fieldLabel:'性别',
										xtype:'combo',
										hiddenName:'xb',
										mode:'local',
										readOnly:'true',
										triggerAction:'all',
										displayField:'dmmc',
										valueField:'lxdm',
										store:new Ext.data.JsonStore({
											data:[{lxdm:'0',dmmc:'女'},{lxdm:'1',dmmc:'男'}],
											fields:['lxdm','dmmc']		
										})
									 },{
									 	fieldLabel:'出生日期',
										name:'csrq',
										xtype:'datefield',
										format:'Y-m-d'
									 }
							]
						},{
							columnWidth:0.5,
							labelWidth:75,
							border:false,
							defaultType:'textfield',
							defaults:{
								anchor:'95%'
							},items:[
										{
										 	fieldLabel:'身份证号',
											name:'sfzh'
										 },{
										 	fieldLabel:'健康状况',
											hiddenName:'jkzkdm',
											xtype:'combo',
											mode:'local',  
											readOnly:'true',
											triggerAction:'all',
											displayField:'dmmc',
											valueField:'lxdm',
											store:jkzkStore
										 },{
										 	fieldLabel:'月收入',
											allowBlank:false,
											xtype:'numberfield',
											value:0,
											maxValue:9999999999,
											name:'ysr'	
										 },{
										 	fieldLabel:'联系方式',
											name:'lxfs'	
										 },{
										 	fieldLabel:'单位或学校',
											name:'dwhxx'	
										 },{
										 	fieldLabel:'身份',
											name:'sf'	
										 }
							]
						},{
							columnWidth:1,
							labelWidth:75,
							border:false,
							defaultType:'textarea',
							defaults:{
								anchor:'97%'
							},items:[
								{
								 	fieldLabel:'备注',
									name:'bz'
								 }
							]
						}
						]
					}
					]
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
