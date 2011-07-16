<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>权限信息</title>
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
			
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '权限基本信息',
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
						fieldLabel:'权限名称',
						name:'name'
					},{
						fieldLabel:'权限描述',
						name:'description'
					},{
						fieldLabel:'父级编号',
						name:'parentId'
					}
       			]
			}));
			
			
			//功能模块数据对象
			var gnmkStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'gnmkAction.do?method=getAllForCmb',
				fields:[
					{
						name:'rightId'
					},{
						name:'name'
					}
				]
			});
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({
				autoDestroy: true,
				pruneModifiedRecords:true,
		        url: 'qxglAction.do?method=getAllQx',
				root: 'qxList',
				fields:[
					{
						name:'rightId'
					},{
						name:'name'
					},{
						name:'description'
					},
					{
						name:'parentId'
					},{
						name:'rightStatus'
					}
				]
			});
			//状态数据对象
			var ztStory=new Ext.data.SimpleStore({fields:['value','text'],data:[['1','是'],['0','否']]});
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
						header:'权限名称',
						sortable : true,
						dataIndex:'name',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'父级编号',
						dataIndex:'parentId',
						sortable : true
					},{
						header:'权限描述',
						width:180,
						sortable : true,
						dataIndex:'description',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'是否启用',
						width:100,
						dataIndex:'rightStatus',
						sortable : true,
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							transform:'sel_qxzt',
							triggerAction:'all',
							lazyRender:true,
							readOnly:true
						})),
						renderer:function(value){
							return value == '1' ? '是' : '否';
						}
					}
				]),
				viewConfig:{
					forceFit:true,
					sortAscText:'升序',
					sortDescText:'降序',
					columnsText:'显示的列'
				},
				//数据
				store:dataStore
			});
			//添加列表
			dataPanel.add(dataGrid);
			//初始化列表工具栏
			var upd = {
				id:'sqBtn',
				tooltip:"角色授权",
				icon:'${pageContext.request.contextPath}/imgs/jssq-btn.gif',
				handler:function(){
					var r = checkboxModel.getSelected();
					if(r == null){
						return ;
					}
					id = r.get('rightId');
					location.href="qxglAction.do?method=getQx&id="+id;
				}
			};
			initDataTbar([_save,"-",_refresh]);
			//initDataTbar([_add,_delete,_save,upd,_refresh]);
			
			
			//设置添加窗口大小
			addWindow.setHeight(400);
			addWindow.setWidth(350);
			addForm.add(new Ext.form.FieldSet({
				title: '权限信息',
	            collapsible: true,
	            autoScroll:true,
	            labelWidth:80,
	            autoHeight:true,
				margins:'10 10 10 10',
	            defaults: {
	            	width: 180
			    },
	            defaultType: 'textfield',
	            items :[
	            	{
						fieldLabel:'权限名称',
						name:'name',
						allowBlank:false
					},
					/*new Ext.form.ComboBox({
							name:'gnmk.id',
							fieldLabel:'所属功能模块',
							xtype:'combo',
							triggerAction: 'all',
							lazyRender:true,
							store:gnmkStore,
							displayField:'mkmc',
							valueField:'id'
					}),*/
					new Ext.form.ComboBox({
							name:'rightStatus',
							fieldLabel:'是否启用',
							xtype:'combo',
							mode:'local',
							triggerAction: 'all',
							readOnly:true,
							store:ztStory,
							displayField:'text',
							valueField:'value'
					}),
					{
						fieldLabel:'权限描述',
						name:'description',
						xtype:'textarea',
						height:80
					}
       			]
			}));
			/*事件控制*****************************/
			//添加事件
			/*Ext.getCmp('addFormSaveBtn').on('click',function(){
				if(addForm.form.isValid()){
					addForm.form.doAction('submit',{
						url:'${pageContext.request.contextPath}/system/qxglAction.do?method=addQx',
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
			
			//删除事件
			Ext.getCmp('deleteBtn').on('click',function(){
				var r = checkboxModel.getSelected();
				if(r == null){
					return ;
				}
				id = r.get('id');
				Ext.Msg.confirm("删除提示","您一共选择了权限:"+r.get('qxmc')+",是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							'${pageContext.request.contextPath}/system/qxglAction.do?method=delQx&id='+id,
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
			});*/
			//修改事件
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
					url: '${pageContext.request.contextPath}/system/qxglAction.do?method=updQx',
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
			//刷新事件
			Ext.getCmp('refreshBtn').on('click',function(){
				dataStore.load();
			})

			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			
			setNavigate("系统管理 >> 权限管理","本模块提供维护系统所有权限信息等功能");
			
			gnmkStore.load();
			dataStore.load();
		});
		
	</script>
  </head>
  <body>
  <select style='display:none' id='sel_qxzt'>
  	<option value='1'>是</option>
  	<option value='0'>否</option>
  </select>
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
