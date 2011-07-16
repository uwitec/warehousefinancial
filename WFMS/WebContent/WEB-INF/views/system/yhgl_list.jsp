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
		        url: 'yhglAction.do?method=getAllYh',
				root: 'dataList',
				totalProperty:'totalCount',
				fields:[
					{
						name:'memid'
					},{
						name:'loginid'
					},{
						name:'password'
					},{
						name:'username'
					},{
						name:'mobilephone'
					},{
						name:'role'
					},{
						name:'email'
					},{
						name:'dlsj'
					},{
						name:'scdlsj'
					},{
						name:'dlcs'
					},{
						name:'resign'
					},{
						name:'deniedlogin'
					}
				]
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
						header:'姓名',
						sortable : true,
						dataIndex:'username',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'登录账号',
						dataIndex:'loginid',
						sortable : true
					},{
						header:'登录密码',
						sortable : true,
						dataIndex:'password',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							inputType:'password',
							allowBlank:false
						})),
						renderer:function(val){
							var len = val.length;
							var rs = "";
							for(var i=0;i<len;i++){
								rs += "●";
							}
							return rs;
						}
					},{
						header:'状态',
						sortable : true,
						dataIndex:'deniedlogin',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							allowBlank:false,
							transform:'qyzt',
							triggerAction:'all',
							lazyRender:true,
							readOnly:true
						})),
						renderer:function(value){
							return value == 'false' ? '正常' : '<span style="color:red">禁用</span>';
						}
					},
					{
						header:'用户职务',
						sortable:true,
						dataIndex:'role',
						renderer:function(v){
							return (v == null) ? "" : v.name;
						}
					},
					{
						header:'用户所在部门',
						sortable : true,
						dataIndex:'role',
						renderer:function(v){
							return (v == null || v.depart==null) ? "" : v.depart.name;
						}
					},{
						header:'联系电话',
						sortable : true,
						dataIndex:'mobilephone',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'电子邮箱',
						sortable : true,
						dataIndex:'email',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'登录次数',
						sortable : true,
						dataIndex:'dlcs'
					}
				]),
				//数据
				store:dataStore,
				//分页
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
			//初始化列表工具栏
			var upd = {
				id:'sqBtn',
				tooltip:"用户授权",
				icon:'${pageContext.request.contextPath}/imgs/jssq-btn.gif',
				handler:function(){
					var r = checkboxModel.getSelected();
					if(r == null){
						return ;
					}
					id = r.get('memid');
					location.href="yhglAction.do?method=yhsq&id="+id;
				}
			};
			
			initDataTbar([_add,_delete,_save,"-",_refresh,"-",upd]);
			
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '用户基本信息',
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
						fieldLabel:'姓名',
						name:'username'
					},{
						fieldLabel:'联系电话',
						name:'mobilephone'
					},{
						fieldLabel:'登录账号',
						name:'loginid'
					},{
						fieldLabel:'电子邮箱',
						name:'email'
					},{
						fieldLabel:'登录次数',
						name:'dlcs'
					}
       			]
			}));
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '用户查询',
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
						fieldLabel:'用户名',
						name:'loginid'
					},{
						fieldLabel:'姓名',
						name:'username'
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
			//设置添加窗口大小
			addWindow.setWidth(330);
			addWindow.setTitle('增加用户');
			addForm.add(new Ext.form.FieldSet({
	            title: '用户基本信息',
	            autoScroll:true,
	            labelWidth:55,
	            autoHeight:true,
	            defaultType: 'textfield',
				defaults:{width:200},
	            items :[
	            	{
						fieldLabel:'姓名',
						allowBlank:false,
						name:'username'
					},{
						fieldLabel:'联系电话',
						name:'mobilephone'
					},{
						fieldLabel:'登录账号',
						allowBlank:false,
						name:'loginid'
					},{
						fieldLabel:'登录密码',
						allowBlank:false,
						name:'password'
					},{
						fieldLabel:'电子邮箱',
						name:'dzyx'
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
						url:'yhglAction.do?method=addYh',
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
				id = r.get('memid');
				Ext.Msg.confirm("删除提示","您选择了用户:"+r.get('username')+",是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							'yhglAction.do?method=delYh&id='+id,
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
					url: 'yhglAction.do?method=updYh',
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
			//完善人员信息事件
			function doAddRy(){
				alert();
				var r = checkboxModel.getSelected();
				if(r == null){
				return ;
				}
				Ext.Msg.confirm("提示","是否确认完善用户："+r.get('username')+" 的信息",function(){
					if(btn == 'yes'){
						location.href="";
					}
				});
			}
			
			
			//实例化主界面
			var mianFrame = new MianFrame();
			setNavigate("系统管理 >> 用户管理","本模块提供维护系统所有用户基本信息、用户授权等功能");
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
