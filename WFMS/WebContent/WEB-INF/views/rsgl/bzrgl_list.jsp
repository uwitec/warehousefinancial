<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>学生信息</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bjTree.js"></script>
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
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '部门基本信息',
	            collapsible: true,
	            autoScroll:true,
	            labelWidth:55,
	            autoHeight:true,
	            defaults: {
	            	readOnly:true,
	            	width: 140,
	            	style: {
			            border:'none',
			            background:'none'
			        }
			    },
	            defaultType: 'textfield',
	            items :[
	            	{
						fieldLabel:'编号',
						name:'ry.rybh'
					},{
						fieldLabel:'姓名',
						name:'ry.ryxm'
					},{
						fieldLabel:'部门',
						name:'ry.member.role.depart.name'
					},{
						fieldLabel:'手机号码',
						name:'ry.sjhm'
					}
       			]
			}));
			
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				totalProperty:'totalCount',
		        url: 'bzrglAction.do?method=getAllBzrForPage',
				root: 'dataList',
				fields:["id","ry.ryxm","ry.rybh","ry.sjhm","ry.bgsdh","ry.gjdm","ry.zt"]
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
						header:'编号',
						sortable : true,
						dataIndex:'ry.rybh'
					},{
						header:'班主任姓名',
						sortable : true,
						dataIndex:'ry.ryxm'
					},{
						header:'手机号码',
						sortable : true,
						dataIndex:'ry.sjhm'
					},{
						header:'办公室电话',
						sortable : true,
						dataIndex:'ry.bgsdh'
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
			var upd = {
				tooltip:"职务授权",
				icon:'${pageContext.request.contextPath}/imgs/jssq-btn.gif',
				handler:function(){
					var r = checkboxModel.getSelected();
					if(r == null){
						return ;
					}
					id = r.get('id');
					location.href="bzrglAction.do?method=getBzrById&id="+id;
				}
			};
			initDataTbar([_add,_delete,"-",_refresh,"-",upd]);
			//人员添加窗口
			var addRyWindow = new Ext.Window({
				title:'请选择需要添加的人员',
				width:300,
				height:120,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addRyForm',
					baseCls:'x-plain',
					labelWidth:40,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					items:getRyTree()
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							var yjf = Ext.getCmp('addRyForm');
							var id = getRyValue();
							var name = getRyName();
							if (yjf.form.isValid() && id != "") {
								var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后','提示');
								Ext.Ajax.request({
									method: 'POST',
									url: 'bzrglAction.do?method=addBzr',
									params: {
										ryId:id,
										ryName:name
									},
									success: function(response){
										var rs = Ext.decode(response.responseText);
										if(rs.success){
											waitMsg.hide();
											dataStore.reload();
										}else if(rs.other =="reAdd"){
											Ext.Msg.alert("提示","该人员还没有系统用户，请现在为该人员指定系统用户！",function(){
												addYhWindow.show();
											});
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
							addRyWindow.hide();
						}
					}
				]
			});
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
			//班级添加窗口
			var addBjWindow = new Ext.Window({
				title:'请选择需要指定的班级',
				width:300,
				height:120,
				closeAction:'hide',
				modal:true,
				plain:true,
				items:new Ext.form.FormPanel({
					id:'addBjForm',
					baseCls:'x-plain',
					labelWidth:40,
					bodyStyle:'padding:10px',
					border:false,
					frame:true,
					items:getBjTree()
				}),
				buttons:[
					{
						text:'确 定',
						width:60,
						handler:function(){
							alert();
						}
					},{
						text:'取 消',
						width:60,
						handler:function(){
							addBjWindow.hide();
						}
					}
				]
			});
			
			
			
			/*事件控制*****************************/
			//刷新事件
			Ext.getCmp('refreshBtn').on('click',function(){
				dataStore.reload();
			})
			//添加事件
			Ext.getCmp('addBtn').on('click',function(){
				addRyWindow.show();
			});
			//删除事件
			Ext.getCmp('deleteBtn').on('click',function(){
				var r = checkboxModel.getSelected();
				if(r == null){
					return ;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了班主任："+r.get('ry.ryxm')+"，删除该信息后将会删除与其关联的班主任权限及所带班级记录，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/rsgl/bzrglAction.do?method=delBzr&id='+id,
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
			addWorkBtn({
				xtype:'button',
				text:'指定班级',
				width:80,
				handler:function(){
					if(checkboxModel.getSelected() == null ){
						Ext.Msg.alert("提示", "请选中一行班主任信息信息指定班级！");
					}else{
						addBjWindow.show();
					}
				}
			});

			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("人事管理 >> 班主任管理","本模块提供查看班主任基本信息、为班主任指定班级、查看班主任历史所带班级信息等功能");

			
			dataStore.load({params:{start:0,limit:15}});
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
