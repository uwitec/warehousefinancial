<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>学生信息</title>
	<!-- Extjs导入 -->
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common_viewport.js"></script>
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
		        url: contextPath+'/system/zwglAction.do?method=getAllZw',
				fields:[
					{
						name:'rolid'
					},{
						name:'name'
					},
					{
						name:'depart.id'
					},
					{
						name:'depart.name'
					},{
						name:'createTime'
					},{
						name:'synopsis'
					}
				]
			});
			
			var departStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        //url: contextPath+'/system/departAction.do?method=getAllDeparts',
		        url: contextPath+'/rsgl/bmglAction.do?method=getAllBmForList',
				fields:[
					{
						name:'depid'
					},{
						name:'name'
					}
				]
			});
			
			//列表
			var dataGrid = new Ext.grid.EditorGridPanel({
				stripeRows: true,
				autoScroll:true,
				groupField:'depart.id',
				trackMouseOver:true,
				border:false,
		        loadMask: true,
		        sm:checkboxModel,
				//列模型
		       	cm:new Ext.grid.ColumnModel([
					{
						header:'编号',
						sortable : true,
						dataIndex:'rolid'
					},
					{
						header:'职务名称',
						sortable : true,
						dataIndex:'name',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'职务描述',
						dataIndex:'synopsis',
						sortable : true,
						width:180,
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'所属部门',
						sortable : true,
						dataIndex:'depart.name'
					},{
						header:'创建时间',
						sortable : true,
						dataIndex:'createTime'
					}
				]),
				//数据
				store:dataStore
			});
			//添加列表
			dataPanel.add(dataGrid);
			//初始化列表工具栏
			var upd = {
				id:'sqBtn',
				tooltip:"角色授予",
				icon:'${pageContext.request.contextPath}/imgs/jssq-btn.gif',
				handler:function(){
					var r = checkboxModel.getSelected();
					if(r == null){
						return ;
					}
					id = r.get('rolid');
					location.href="zwglAction.do?method=getZw&id="+id;
				}
			};
			initDataTbar([_add,_delete,_save,"-",_refresh,"-",upd]);
			
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '职务基本信息',
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
						name:'rolid',
						width:150
					},
	            	{
						fieldLabel:'职务名称',
						name:'name'
					},{
						fieldLabel:'职务描述',
						name:'synopsis'
					},{
						fieldLabel:'创建时间',
						name:'createTime'
					}
       			]
			}));
			//设置添加窗口大小
			addWindow.setWidth(330);
//			//addWindow.on('afterrender',function(win){
//				//departStore.load();
//			//});
			addWindow.setTitle('增加职务');
			addForm.add(new Ext.form.FieldSet({
	            title: '职务基本信息',
	            autoScroll:true,
	            labelWidth:55,
	            autoHeight:true,
	            defaultType: 'textfield',
				defaults:{width:200},
	            items :[
					{
						triggerAction:'all',
						hiddenName:'depart',
						fieldLabel:'所属部门',
						xtype:'combo',
						valueField:'depid',
						displayField:'name',
						store:departStore
					},
	            	{
						fieldLabel:'职务名称',
						allowBlank:false,
						name:'name'
					},{
						fieldLabel:'职务描述',
						xtype:'textarea',
						height:50,
						name:'synopsis'
					}
       			]
			}));
			
			//按钮事件控制***************************************
			
			//刷新
			Ext.getCmp('refreshBtn').on('click',function(){
				dataStore.reload();
			});
			//添加
			Ext.getCmp('addFormSaveBtn').on('click',function(){
				if(addForm.form.isValid()){
					addForm.form.doAction('submit',{
						url:'zwglAction.do?method=addZw',
						method:'post',
						waitTitle:'提示信息',                                                    
                        waitMsg:'数据更新中，请稍候...',
                        success:function(form,action){
                        	addForm.form.reset();
                        	if(action.result.success){
								dataStore.load();
                				addWindow.hide();
                        	}else{
                        		Ext.Msg.alert('添加失败', message);
                        	}
                        },
                        failure:function(){
                        	addForm.form.reset();
                            Ext.Msg.alert('操作失败','服务器无法连接，请稍后重试！');
                       	}
					});
				}
			});
			//删除
			Ext.getCmp('deleteBtn').on('click',function(){
				var r = checkboxModel.getSelected();
				if(r == null){
					return ;
				}
				id = r.get('rolid');
				if(r.get('deleteable') == "0"){
					Ext.Msg.alert("提示","该职务不能被删除！");
				}
				Ext.Msg.confirm("删除提示","您选择了职务:"+r.get('name')+",是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							'zwglAction.do?method=delZw&id='+id,
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
			
			//修改
			Ext.getCmp('saveBtn').on('click',function(){
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
					url: 'zwglAction.do?method=updZw',
					success: function(response){
						var rs = Ext.decode(response.responseText);
						if (rs.success) {
							waitMsg.hide();
							dataStore.load();
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
			
			
			//实例化主界面
			var mianFrame = new MianFrame();
			setNavigate("系统管理 >> 职务管理","本模块提供维护系统所有职务基本信息、职务授权等功能");
			//列表数据加载
			dataStore.load({params:{start:0, limit:15}});
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='{qxlx}'>
  <input type="hidden" id='mkid' value='{mkid}'>
  <select id='qyzt' style="display:none">
  	<option value='1'>正常</option>
  	<option value='0'>禁用</option>
  </select>
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
