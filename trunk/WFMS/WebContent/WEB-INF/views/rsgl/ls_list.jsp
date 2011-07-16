<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>教师信息</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ryTree.js"></script>
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
			
			var lsxzStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=LSXZDM',
				fields:["lxdm","dmmc"]
			});
			var jydwStore = new Ext.data.JsonStore({
				url: contextPath+'/rsgl/jydwglAction.do?method=getAllJydw',
				fields:["id","jydwmc"]
			});
			
			var bmStore =  new Ext.data.JsonStore({
				url:contextPath+'/rsgl/bmglAction.do?method=getAllBmForList',
				fields:["depid","name"]
			});
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '教师基本信息',
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
	            	anchor:'99%'
			    },
	            defaultType: 'textfield',
	            items :[
					{
						fieldLabel:'教师编号',
						name:'lsbh'
					},{
						fieldLabel:'教师姓名',
						name:'ry.ryxm'
					},{
						fieldLabel:'教研单位',
						name:'jydw.jydwmc'
					},{
						fieldLabel:'教师性质',
						name:'lsxzdm',
						xtype:'combo',
						readOnly:true,
						hideTrigger:true,
						displayField:"dmmc",
						valueField:'lxdm',
						mode:'local',
						store:lsxzStore
					}
				]
			}));
			
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/rsgl/lsglAction.do?method=getAllLs',
				fields:["id","lsbh","lsxm","ry.id","ry.ryxm","jydw.jydwmc","jydw.id","lsxzdm"]
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
						header:'教师编号',
						dataIndex:'lsbh',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'教师姓名',
						sortable : true,
						dataIndex:'lsxm'
					},{
						header:'教研单位',
						sortable : true,
						dataIndex:'jydw.jydwmc',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							readOnly:true,
							triggerAction:'all',
							mode:'local',
							displayField:"jydwmc",
							valueField:'id',
							store:jydwStore,
							allowBlank:false
						})),
						renderer:function(v){
							var rs = "";
							jydwStore.each(function(r){
								if(v == r.get('id')){
									rs = r.get('jydwmc');
									return;	
								}
							});
							return rs;
						}
					},{
						header:'教师性质',
						sortable : true,
						dataIndex:'lsxzdm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							readOnly:true,
							triggerAction:'all',
							mode:'local',
							displayField:"dmmc",
							valueField:'lxdm',
							store:lsxzStore,
							allowBlank:false
						})),
						renderer:function(v){
							var rs = "";
							lsxzStore.each(function(r){
								if(v == r.get('lxdm')){
									rs = r.get('dmmc');
									return;	
								}
							});
							return rs;
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
			
			//添加用户窗口
			var addYhWindow = new Ext.Window({
				title:'请输入用户账号，默认密码为：0',
				width:300,
				height:150,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addYhForm',
					baseCls:'x-plain',
					labelWidth:40,
					bodyStyle:'padding:10px',
					defaultType:'textfield',
					border:false,
					frame:true,
					items:[
						{
							fieldLabel:'账号',
							width:200,
							id:'dlzh'
						},{
							fieldLabel:'密码',
							readOnly:true,
							width:200,
							id:'dlmm',
							value:'0'
						}
					]
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var addform = Ext.getCmp('addYhForm');
							var id = getRyValue();
							var dlzh = Ext.getCmp('dlzh').getValue();
							var dlmm = Ext.getCmp('dlmm').getValue();
							if (addform.form.isValid() && id != "") {
								var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后','提示');
								Ext.Ajax.request({
									method: 'POST',
									url: contextPath+'/rsgl/ryglAction.do?method=addYhForRy',
									params: {
										ryId:id,
										dlzh:dlzh,
										dlmm:dlmm
									},
									success: function(response){
										addform.form.reset();
										var rs = Ext.decode(response.responseText);
										if(rs.success){
											addYhWindow.hide();
											waitMsg.hide();
										}else{
											Ext.Msg.alert("提示", rs.message);
										}
									},
									failure: function(){
										Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
									}
								});
							}
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addYhWindow.hide();
						}
					}
				]
			});
			
			//添加窗口
			var addLsWin = new Ext.Window({
				width:360,
				height:230,
				modal:true,
				plain:true,
				layout:'fit',
				title:'添加教师信息',
				closeAction: 'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addLsForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/rsgl/lsglAction.do?method=addLs',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.other == "addYh"){
											Ext.Msg.alert("提示","该人员还没有系统用户，请现在为该人员指定系统用户！",function(){
												addYhWindow.show();
											});
										}else if(action.result.success){
											aqf.form.reset();
											dataStore.reload();
			                				addLsWin.hide();
			                        	}else{
			                        		Ext.Msg.alert('添加失败', message);
			                        	}
			                        },failure:function(){
			                        	aqf.form.reset();
			                            Ext.Msg.alert('操作失败','服务器无法连接，请稍后重试！');
			                       	}
								});
							}
						}
					},{
						text:'取 消',
						handler:function(){
							addLsWin.hide();
						}
					}
				],
				items:new Ext.form.FormPanel({
					id:'addLsForm',
					border:false,
					baseCls:'x-plain',
					autoScroll:true,
					labelWidth:55,
					labelAlign:'right',
					defaultType:'textfield',
					bodyStyle:'padding:10px',
					defaults:{
						emptyText:'必填',
						anchor:"95%"
					},
					items:[
						{
							fieldLabel:'教师编号',
							allowBlank:false,
							name:'lsbh'
						},{
							xtype:'hidden',
							id:'addFormRyId',
							name:'ry.id'
						},{
							xtype:'hidden',
							name:'kcid',
							value:'0'
						},{
							xtype:'hidden',
							name:'lsxm',
							value:'0'
						},
						getRyTree('addFormRyId'),
						{
							fieldLabel:'教研单位',
							hiddenName:'jydw.id',
							xtype:'combo',
							readOnly:true,
							triggerAction:'all',
							mode:'local',
							displayField:"jydwmc",
							valueField:'id',
							store:jydwStore,
							allowBlank:false
						},{
							fieldLabel:'教师性质',
							hiddenName:'lsxzdm',
							xtype:'combo',
							readOnly:true,
							triggerAction:'all',
							mode:'local',
							displayField:"dmmc",
							valueField:'lxdm',
							store:lsxzStore,
							allowBlank:false
						}
					]
				})
			});
			
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '教师信息查询',
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
						fieldLabel:'教师编号',
						name:'lsbh'
					},{
						fieldLabel:'教师姓名',
						name:'ry.ryxm'
					},{
						fieldLabel:'教研单位',
						hiddenName:'jydw.id',
						triggerAction:'all',
						xtype:'combo',
						readOnly:true,
						displayField:"jydwmc",
						valueField:'id',
						mode:'local',
						store:jydwStore
					},{
						fieldLabel:'所属部门',
						hiddenName:'ry.member.role.depart.depid',
						triggerAction:'all',
						xtype:'combo',
						readOnly:true,
						displayField:"name",
						valueField:'depid',
						mode:'local',
						store:bmStore
					},{
						fieldLabel:'教师性质',
						hiddenName:'lsxzdm',
						triggerAction:'all',
						xtype:'combo',
						readOnly:true,
						displayField:"dmmc",
						valueField:'lxdm',
						mode:'local',
						store:lsxzStore
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
				addLsWin.show();
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
				Ext.Msg.confirm("删除提示","您选择了教师："+r.get('ry.ryxm')+"，请确认该老师下没有教学任务等相关信息，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/rsgl/lsglAction.do?method=deleteLs&id='+id,
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
			setNavigate("人事管理 >> 教师管理","本模块提供教师基本信息维护功能");
			
			var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			bmStore.load({
				callback:function(r,o,s){
					if(s){
						lsxzStore.load({
							callback: function(r, options, success){
								if(success){
									jydwStore.load({
										callback: function(r, options, success){
											if(success){
												dataStore.load({params:{start:0,limit:15}});
												waitMsg.hide();
											}else{
												Ext.Msg.alert("错误提示","数据加载失败！"); 
											}
										}
									});
								}else{
									Ext.Msg.alert("错误提示","数据加载失败！"); 
								}
							}
						});
					}else{
						Ext.Msg.alert("错误提示","数据加载失败！");
					}
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
