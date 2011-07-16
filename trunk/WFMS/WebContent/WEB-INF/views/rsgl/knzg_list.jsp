<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>困难职工信息</title>
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
			//查询所有人员信息
			var ryStore = new Ext.data.JsonStore({   
				url:contextPath+'/rsgl/ryglAction.do?method=getAllRy',
				fields:["id","ryxm"]
			});
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '困难职工基本信息',
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
	            	anchor:'86%'
			    },
	            defaultType: 'textfield',
	            items :[
				    {
						fieldLabel:'人员名称',
						readOnly:true,
						name:'ry.ryxm'
					},{
						fieldLabel:'困难类别',
						xtype:'combo',
						hiddenName:'knlbdm',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:knlbdmStore 
					},{
						fieldLabel:'残疾类别',
						xtype:'combo',
						hiddenName:'cjlbdm',
						mode:'local',
						hideTrigger:true,
						readOnly:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:cjlbdmStore 
					},{
						fieldLabel:'身份',
						name:'sf'
					}, {
						fieldLabel:'劳模类型',
						xtype:'combo',
						hiddenName:'lmlxdm',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:lmlxdmStore  
					}, {
						fieldLabel:'住房类型',
						xtype:'combo',
						hiddenName:'zflxdm',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:zflxdmStore  
					},{
						fieldLabel:'建筑面积',
						xtype:'numberfield',
						maxValue:99999,
						name:'jzmj'
					},{
						fieldLabel:'联系电话',
						name:'lxdh'
					},{
						fieldLabel:'工作日期',
						readOnly:true,
						name:'gzrq'
					},{
						fieldLabel:'所属行业',
						name:'sshy'
					},{
						fieldLabel:'家庭住址',
						name:'jtzz'
					},{
						fieldLabel:'工作单位',
						name:'gzdw'
					},{
						fieldLabel:'企业状况',
						name:'qyzk'
					},{
						fieldLabel:'单位性质',
						name:'dwxzdm',
						xtype:'combo',
						mode:'local',
						hideTrigger:true,
						readOnly:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:dwxzdmStore   
					},{
						fieldLabel:'是否单亲',
						name:'sfdq',
						mode:'local',
						xtype:'combo',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						store:new Ext.data.JsonStore({
							data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
							fields:['sid','sname']
						})
					},{
						fieldLabel:'本人收入/月',
						xtype:'numberfield',
						maxValue:999999,
						name:'brypjsr'
					},{
						fieldLabel:'家庭收入/年',
						xtype:'numberfield',
						maxValue:9999999999,
						name:'jtndzsr'
					},{
						fieldLabel:'家庭人口',
						xtype:'numberfield',
						maxValue:99,
						name:'jtrk'
					},{
						fieldLabel:'人均收入/年',
						xtype:'numberfield',
						maxValue:9999999999,
						name:'jtnrjsr'
					},{
						fieldLabel:'户口所在地',
						name:'hkszdxzqh'
					},{
						fieldLabel:'户口性质',
						name:'hkxzdm',
						xtype:'combo',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:hkxzdmStore    
					},{
						fieldLabel:'是否入医保',
						name:'sfjryb',
						mode:'local',
						xtype:'combo',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						store:new Ext.data.JsonStore({
							data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
							fields:['sid','sname']
						})
					},{
						fieldLabel:'能否自救',
						name:'sfyydzjnl',
						mode:'local',
						xtype:'combo',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						store:new Ext.data.JsonStore({
							data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
							fields:['sid','sname']
						})
					},{
						fieldLabel:'是否无人就业',
						name:'sfwljyjt',
						mode:'local',
						xtype:'combo',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'sid',
						store:new Ext.data.JsonStore({
							data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
							fields:['sid','sname']
						})
					},{
						fieldLabel:'备注',
						name:'bz'	
					}
				]
			}));
			
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({ //数据存储
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/rsgl/knzgAction.do?method=getKnzgByPage',
				fields:["id","ry.id","ry.ryxm","knlbdm","cjlbdm","sf","lmlxdm","zflxdm","jzmj","lxdh","gzrq","sshy",
						"jtzz","gzdw","dwxzdm","qyzk","sfdq","brypjsr","jtndzsr","jtrk","jtnrjsr",
						"hkszdxzqh","sfjryb","sfyydzjnl","sfwljyjt","bz","hkxzdm"]
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
					checkboxModel,
					{
						header:'人员名称',
						readOnly:true,
						dataIndex:'ry.ryxm'
					},{
						header:'困难类别',
						dataIndex:'knlbdm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:knlbdmStore 
						})),
						renderer:function(value){
							var result = "";
							knlbdmStore .each(function(record){
								if(record.get('lxdm') == value){
									result = record.get('dmmc');
									return ;
								}
							});
							return result;
						}
					},{
						header:'残疾类别',
						dataIndex:'cjlbdm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:cjlbdmStore 
						})),
						renderer:function(value){
							var result = "";
							cjlbdmStore .each(function(record){
								if(record.get('lxdm') == value){
									result = record.get('dmmc');
									return ;
								}
							});
							return result;
						}
					},{
						header:'身份',
						dataIndex:'sf',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
						}))
					},{
						header:'劳模类型',
						dataIndex:'lmlxdm',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:lmlxdmStore  
						})),
						renderer:function(value){
							var result = "";
							lmlxdmStore .each(function(record){
								if(record.get('lxdm') == value){
									result = record.get('dmmc');
									return ;
								}
							});
							return result;
						}
					},{
						header:'住房类型',
						dataIndex:'zflxdm',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:zflxdmStore  
						})),
						renderer:function(value){
							var result = "";
							zflxdmStore  .each(function(record){
								if(record.get('lxdm') == value){
									result = record.get('dmmc');
									return ;
								}
							});
							return result;
						}
					},{
						header:'建筑面积',
						dataIndex:'jzmj',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							xtype:'numberfield',
							maxValue:99999,
							value:0
						}))
					},{
						header:'联系电话',
						dataIndex:'lxdh',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
						}))
					},{
						header:'工作日期',
						dataIndex:'gzrq',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							xtype:'datefield',
							format:'Y-m-d'
						}))
					},{
						header:'所属行业',
						dataIndex:'sshy',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'家庭住址',
						dataIndex:'jtzz',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'工作单位',
						dataIndex:'gzdw',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'企业状况',
						dataIndex:'gzdw',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
						}))
					},{
						header:'单位性质',
						dataIndex:'dwxzdm',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:dwxzdmStore   
						})),
						renderer:function(value){
							var result = "";
							dwxzdmStore.each(function(record){
								if(record.get('lxdm') == value){
									result = record.get('dmmc');
									return ;
								}
							});
							return result;
						}
					},{
						header:'户口性质',
						dataIndex:'hkxzdm',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:hkxzdmStore    
						})),
						renderer:function(value){
							var result = "";
							hkxzdmStore.each(function(record){
								if(record.get('lxdm') == value){
									result = record.get('dmmc');
									return ;
								}
							});
							return result;
						}
					},{
						header:'是否单亲',
						dataIndex:'sfdq',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:new Ext.data.JsonStore({
								data:[{lxdm:'0',dmmc:'否'},{lxdm:'1',dmmc:'是'}],
								fields:['lxdm','dmmc']
							})
						})),
						renderer:function(value){
							return value == '0' ? '否' : '是' ;
						}
					},{
						header:'本人月平均收入',
						dataIndex:'brypjsr',
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({}))
					},{
						header:'家庭年度总收入',
						dataIndex:'jtndzsr',
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({}))
					},{
						header:'家庭人口',
						dataIndex:'jtrk',
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({}))
					},{
						header:'家庭年人均收入',
						dataIndex:'jtnrjsr',
						editor:new Ext.grid.GridEditor(new Ext.form.NumberField({}))
					} ,{
						header:'户口所在地',
						dataIndex:'hkszdxzqh',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'是否入医保',
						dataIndex:'sfjryb',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:new Ext.data.JsonStore({
								data:[{lxdm:'0',dmmc:'否'},{lxdm:'1',dmmc:'是'}],
								fields:['lxdm','dmmc']
							})
						})),
						renderer:function(value){
							return value == '0' ? '否' : '是' ;
						}
					},{
						header:'是否有一定自救力',
						dataIndex:'sfyydzjnl',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:new Ext.data.JsonStore({
								data:[{lxdm:'0',dmmc:'否'},{lxdm:'1',dmmc:'是'}],
								fields:['lxdm','dmmc']
							})
						})),
						renderer:function(value){
							return value == '0' ? '否' : '是' ;
						}
					},{
						header:'是否零就业家庭',
						dataIndex:'sfwljyjt',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:new Ext.data.JsonStore({
								data:[{lxdm:'0',dmmc:'否'},{lxdm:'1',dmmc:'是'}],
								fields:['lxdm','dmmc']
							})
						})),
						renderer:function(value){
							return value == '0' ? '否' : '是' ;
						}
					},{
						header:'备注',
						dataIndex:'bz',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
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
			initDataTbar([_add,_update,_delete,"-",_refresh,_condition,"-",_print]);
			//添加窗口
			var addKnzgWin  =new Ext.Window({ //弹出窗口
				width:520,
				height:500,
				modal:true,
				plain:true,
				layout:'fit', //布局方式(自动填满)
				title:'添加困难职工信息', 
				closeAction:'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addKnzgForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/rsgl/knzgAction.do?method=addKnzg',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				addKnzgWin.hide();
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
							addKnzgWin.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'addKnzgForm',
						border:false,
						baseCls:'x-plain',//溶于底色
						autoScroll:true,
						layout:'column', // 布局方式(列模式)
						labelAlign:'right',
						bodyStyle:'padding:10px',
						defaults:{
							xtype:'fieldset',
							anchor:'95%'//显示比例
						},
						items:[
						{
							columnWidth:0.5,
							labelWidth:55,
							border:false,
							defaultType:'textfield',
							defaults:{
								anchor:'95%'
							},
							items:[
								{
									xtype:'hidden',
									id:'_ry.id',
									name:'ry.id'
								},{
									fieldLabel:'人员名称',
									xtype:'trigger', //trigger：combox的父级
									allowBlank:false,
									id:'_ry.ryxm',
									name:'ry.ryxm',
									onTriggerClick:function(){
										getRyxxListWindow(function(r){
											if(r != null){
												Ext.getCmp('_ry.id').setValue(r.get('id'));
												Ext.getCmp('_ry.ryxm').setValue(r.get('ryxm'));
												Ext.getCmp('addKnzgForm').form.loadRecord(r);
											}
										});
									}
								},{
									fieldLabel:'困难类别',
									xtype:'combo',
									hiddenName:'knlbdm',
									allowBlank:false,
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:knlbdmStore 
								},{
									fieldLabel:'残疾类别',
									xtype:'combo',
									hiddenName:'cjlbdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:cjlbdmStore 
								},{
									fieldLabel:'身份',
									name:'sf'
								}, {
									fieldLabel:'劳模类型',
									xtype:'combo',
									hiddenName:'lmlxdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:lmlxdmStore  
								}, {
									fieldLabel:'住房类型',
									xtype:'combo',
									hiddenName:'zflxdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:zflxdmStore   
								},{
									fieldLabel:'建筑面积',
									xtype:'numberfield',
									allowBlank:false,
									maxValue:99999,
									name:'jzmj'
								},{
									fieldLabel:'联系电话',
									name:'lxdh'
								},{
									fieldLabel:'工作日期',
									name:'gzrq'
								},{
									fieldLabel:'所属行业',
									name:'sshy'
								},{
									fieldLabel:'家庭住址',
									name:'jtzz'
								},{
									fieldLabel:'工作单位',
									name:'gzdw'
								}
							]
						},{
							columnWidth:0.5,
							labelWidth:79,
							border:false,
							defaultType:'textfield',
							defaults:{
							  anchor:'95%'
							},
							items:[
							{
								fieldLabel:'企业状况',
								name:'qyzk'
							},{
								fieldLabel:'单位性质',
								hiddenName:'dwxzdm',
								xtype:'combo',
								mode:'local',
								readOnly:true,
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:dwxzdmStore   
							},{
								fieldLabel:'是否单亲',
								hiddenName:'sfdq',
								mode:'local',
								xtype:'combo',
								readOnly:true,
								triggerAction:'all',
								displayField:"sname",
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
								name:'brypjsr'
							},{
								fieldLabel:'家庭收入/年',
								xtype:'numberfield',
								maxValue:999999999,
								allowBlank:false,
								name:'jtndzsr'
							},{
								fieldLabel:'家庭人口',
								xtype:'numberfield',
								maxValue:99,
								allowBlank:false,
								name:'jtrk'
							},{
								fieldLabel:'人均收入/年',
								xtype:'numberfield',
								maxValue:99999999,
								allowBlank:false,
								name:'jtnrjsr'
							},{
								fieldLabel:'户口所在地',
								name:'hkszdxzqh'
							},{
								fieldLabel:'户口性质',
								hiddenName:'hkxzdm',
								xtype:'combo',
								mode:'local',
								readOnly:true,
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:hkxzdmStore    
							},{
								fieldLabel:'是否入医保',
								hiddenName:'sfjryb',
								mode:'local',
								xtype:'combo',
								readOnly:true,
								triggerAction:'all',
								displayField:"sname",
								valueField:'sid',
								store:new Ext.data.JsonStore({
									data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
									fields:['sid','sname']
								})
							},{
								fieldLabel:'能否自救',
								hiddenName:'sfyydzjnl',
								mode:'local',
								xtype:'combo',
								readOnly:true,
								triggerAction:'all',
								displayField:"sname",
								valueField:'sid',
								store:new Ext.data.JsonStore({
									data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
									fields:['sid','sname']
								})
							},{
								fieldLabel:'是否无人就业',
								hiddenName:'sfwljyjt',
								mode:'local',
								xtype:'combo',
								readOnly:true,
								triggerAction:'all',
								displayField:"sname",
								valueField:'sid',
								store:new Ext.data.JsonStore({
									data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
									fields:['sid','sname']
								})
							}
							]
						},{
							columnWidth:1,
							labelWidth:55,
							border:false,
							defaultType:'textfield',
							defaults:{
								anchor:'97%'
							},
							items:[
								{
									fieldLabel:'备注',
									name:'bz',
									height:50,
									xtype:'textarea'
								}
							]
						}
						]
					}
				]
				});
			
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '困难职工信息查询',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:74,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'95%'
				},
	            items:[
								{
									fieldLabel:'困难类别',
									xtype:'combo',
									hiddenName:'knlbdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:knlbdmStore 
								},{
									fieldLabel:'残疾类别',
									xtype:'combo',
									hiddenName:'cjlbdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:cjlbdmStore 
								}, {
									fieldLabel:'劳模类型',
									xtype:'combo',
									hiddenName:'lmlxdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:lmlxdmStore  
								}, {
									fieldLabel:'住房类型',
									xtype:'combo',
									hiddenName:'zflxdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:zflxdmStore  
								},{
									fieldLabel:'建筑面积',
									name:'jzmj'
								},{
									fieldLabel:'本人收入/月',
									name:'brypjsr'
								},{
									fieldLabel:'户口性质',
									hiddenName:'hkxzdm',
									xtype:'combo',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:hkxzdmStore    
								},{
									fieldLabel:'是否入医保',
									name:'sfjryb',
									mode:'local',
									xtype:'combo',
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
			
			//打印困难职工信息
			getPrintBtn().on('click',function(){
			var r = checkboxModel.getSelected(); //获取选中的困难职工记录
					if(r == null || r.length == 0 ){
						return;
				}
				var recordId= r.get('id');
				window.open(contextPath+"/report/reportAction.do?method=printKnzg&id="+recordId,'','height=700, width=830, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			});
			//刷新事件
			getRefreshBtn().on('click',function(){
				dataStore.reload();
			})
			//添加事件
			getAddBtn().on('click',function(){
				location.href = 'knzg_add.html';
			});
			//跳转到修改页面
			getUpdateBtn().on('click',function(){
				var r = checkboxModel.getSelected(); //获取选中的困难职工记录
				if(r == null || r.length == 0 ){
					return;
				}
				var recordId= r.get('id');
				location.href=contextPath+"/rsgl/knzgAction.do?method=toUpdateKnzg&id="+recordId;
			});

			//删除事件
			getDeleteBtn().on('click',function(){
				var r = checkboxModel.getSelected();   //r ：一个选中的实体记录
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了："+r.get('ry.ryxm')+"职工的信息，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/rsgl/knzgAction.do?method=deleteKnzg&id='+id,
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
			setNavigate("人事管理 >> 困难职工 >> ","本模块提供困难职工的基本信息");
			bachLoad([knlbdmStore ,cjlbdmStore ,lmlxdmStore ,zflxdmStore ,dwxzdmStore ,hkxzdmStore ],function(s){
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
