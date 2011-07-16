<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>学生信息</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
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
			
			var mzStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=MZDM',
				fields:["id","dmmc"]
			});
			var bmStore =  new Ext.data.JsonStore({
				url:contextPath+'/rsgl/bmglAction.do?method=getAllBmForList',
				fields:["depid","name"]
			});
			
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '人员基本信息',
	            collapsible: true,
	            autoScroll:true,
	            labelWidth:70,
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
						fieldLabel:'人员编号',
						name:'rybh'
					},{
						fieldLabel:'人员姓名',
						name:'ryxm'
					},{
						fieldLabel:'部门',
						name:'member.role.depart.name'
					},{
						fieldLabel:'国籍',
						name:'gjdm'
					},{
						fieldLabel:'出生地',
						name:'csd'
					},{
						fieldLabel:'民族',
						name:'mzdm'
					},{
						fieldLabel:'手机号码',
						name:'sjhm'
					},{
						fieldLabel:'办公室电话',
						name:'bgsdh'
					},{
						fieldLabel:'入职时间',
						name:'rzsj'
					},{
						fieldLabel:'在职状态',
						name:'zt',
						xtype:'combo',
						readOnly:true,
						displayField:"ztmc",
						valueField:'zt',
						hideTrigger:true,
						mode:'local',
						store:new Ext.data.JsonStore({
							data:[{zt:'0',ztmc:'离职'},{zt:'1',ztmc:'在职'}],
							fields:["zt","ztmc"]
						})
					}
       			]
			}));
			
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				totalProperty:'totalCount',
		        url: 'ryglAction.do?method=getAllRyxx',
				root: 'dataList',
				fields:["id","member.role.depart.name","rybh","ryxm","csrq","bgsdh","sjhm","gjdm",
				"csd","mzdm","sfzh","zp","xx","jkqkdm","hyqk","jtcsdm","rzsj","zzsj","lzsj","bz","zt"]
			});
			
			var kdlbStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=KCLBDM',
				fields:["lxdm","dmmc"]
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
						header:'人员编号',
						sortable : true,
						dataIndex:'rybh'
					},{
						header:'人员姓名',
						sortable : true,
						dataIndex:'ryxm'
					},{
						header:'部门',
						dataIndex:'member.role.depart.name',
						sortable : true
					},{
						header:'国籍',
						sortable : true,
						dataIndex:'gjdm'
					},{
						header:'出生地',
						sortable : true,
						dataIndex:'csd'
					},{
						header:'民族',
						sortable : true,
						dataIndex:'mzdm'
					},{
						header:'手机号码',
						sortable : true,
						dataIndex:'sjhm'
					},{
						header:'办公室电话',
						sortable : true,
						dataIndex:'bgsdh'
					},{
						header:'入职时间',
						sortable : true,
						dataIndex:'rzsj'
					},{
						header:'在职状态',
						sortable : true,
						dataIndex:'zt',
						renderer:function(value){
							return value == 0 ? '离职' : '在职'; 
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
			initDataTbar([_add,_delete,_update,"-",_refresh,_condition]);
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '人员查询',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:55,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'99%'
				},
	            items:[
					{
						fieldLabel:'人员编号',
						name:'rybh'
					},{
						fieldLabel:'人员姓名',
						name:'ryxm'
					},{
						fieldLabel:'部门',
						hiddenName:'member.role.depart.depid',
						xtype:'combo',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"name",
						valueField:'depid',
						store:bmStore
					},{
						fieldLabel:'民族',
						name:'mzdm',
						xtype:'combo',
						mode:'local',
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'dmmc',
						store:mzStore
					},{
						fieldLabel:'入职时间',
						name:'rzsj'
					},{
						fieldLabel:'在职状态',
						hiddenName:'zt',
						xtype:'combo',
						triggerAction:'all',
						readOnly:true,
						displayField:"ztmc",
						valueField:'zt',
						mode:'local',
						store:new Ext.data.JsonStore({
							data:[{zt:'0',ztmc:'离职'},{zt:'1',ztmc:'在职'}],
							fields:["zt","ztmc"]
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
			
			//删除事件
			Ext.getCmp('deleteBtn').on('click',function(){
				var r = checkboxModel.getSelected();
				if(r == null){
					return ;
				}
				id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了人员:"+r.get('ryxm')+",是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							'bmglAction.do?method=delBm&id='+id,
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
			//刷新事件
			Ext.getCmp('refreshBtn').on('click',function(){
				dataStore.reload();
			})
			//添加事件
			Ext.getCmp('addBtn').on('click',function(){
				location.href=contextPath+"/rsgl/rygl_add.html";
			});
			//修改事件
			Ext.getCmp('updateBtn').on('click',function(){
				var ary = checkboxModel.getSelections();
				if(ary.length == 0){
					return ;
				}
				var ids = "";
				for(var i=0;i<ary.length;i++){
					ids+=ary[i].get('id')+",";
				}
				location.href=contextPath+'/rsgl/ryglAction.do?method=toUdataRy&ids='+ids;
			});
			//删除事件
			Ext.getCmp('deleteBtn').on('click',function(){
				var ary = checkboxModel.getSelections();
				if(ary.length == 0){
					return ;
				}
				var ids = "";
				for(var i=0;i<ary.length;i++){
					ids+=ary[i].get('id')+",";
				}
				Ext.Msg.confirm("删除提示","您选择了"+ary.length+"条职工数据,是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/rsgl/ryglAction.do?method=delRyxx&ids='+ids,
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
			setNavigate("人事管理 >> 职工管理","学校所有职工基本信息的维护，该模块作为其他相关模块的基础数据");
			var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			mzStore.load({
				callback: function(r, options, success){
					if(success){
						bmStore.load({
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
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='{qxlx}'>
  <input type="hidden" id='mkid' value='{mkid}'>
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
