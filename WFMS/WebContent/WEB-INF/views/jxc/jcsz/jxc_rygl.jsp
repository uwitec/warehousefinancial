<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>人员管理</title>
	<!-- Extjs导入 -->
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
			/*var qylxStore = new Ext.data.JsonStore({
				url:contextPath+'/jxc/JxcRyglAction.do?method=getAllRygl',
				fields:["yhmc","xxdz","fzr","dh","qydz","sxgsmc","memo"]
			});*/
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '人员基本信息',
	            collapsible: true,
	            autoScroll:true,
	            labelWidth:68,
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
						fieldLabel:'员工名称',
						name:'yhmc'
					},{
						fieldLabel:'联系电话',
						name:'lxdh'
					},{
						fieldLabel:'性别',
						name:'xb'
					},{
						fieldLabel:'身份证号',
						name:'sfzh'
					},{
						fieldLabel:'入职时间',
						name:'rzsj'
					},{
						fieldLabel:'离职事件',
						name:'lzsj'
					},{
						fieldLabel:'是否在职',
						name:'zt'
					},{
						fieldLabel:'备注',
						name:'memo'
					}
				]
			}));
			
			var xbstore = new Ext.data.SimpleStore({
				fields:['xb','xbmc'],
				data:[['男','男'],['女','女']]
			})
			var ztstore = new Ext.data.SimpleStore({
				fields:['zt','ztmc'],
				data:[['在职','在职'],['离职','离职']]
			})
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({//数据存储
				autoDestroy: true,//自动数据填入Store
				pruneModifiedRecords:true,//更新数据时,自动去掉编辑标记
				root:'dataList',//读取数据的时候所读取的属性节点
				totalProperty:'totalCount',//读取属性节点的记录数
		       url:contextPath+'/jxc/JxcRyglAction.do?method=getAllRygl',
				fields:['id',"ygmc","lxdh","xb","sfzh","rzsj","zt","lzsj","deptmc","memo"]//实体属性名称
			});
			
			//列表
			var dataGrid = new Ext.grid.EditorGridPanel({//可编辑的表格面板
				stripeRows: true,//隔行变色
				autoScroll:true,//自动滚动条
				trackMouseOver:true,//高亮显示
				border:false,//去掉面板边框
		        loadMask: true,//读取数据的时候提示更新
		        sm:checkboxModel,//选择模型
				//列模型
		       	cm:new Ext.grid.ColumnModel([
		      	 {
						header:'员工名称',
						dataIndex:'ygmc',
						width:100,
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'联系电话',
						dataIndex:'lxdh',
						width:100,
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'性别',
						dataIndex:'xb',
						width:40,
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							hiddenName:'xb',
							xtype:'combo',
							readOnly:true,
							emptyText:'必填',
							allowBlank:false,
							triggerAction:'all',
							displayField:"xbmc",
							valueField:'xb',
							store:xbstore,
							mode:'local'
						}))
					},{
						header:'部门名称',
						dataIndex:'deptmc',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
						}))
					},{
						header:'身份证号',
						dataIndex:'sfzh',
						width:200,
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'入职时间',
						dataIndex:'rzsj',
						width:120,
						editor:new Ext.grid.GridEditor(new Ext.form.DateField({
							allowBlank:false,
							format : 'Y-m-d'
						}))
					},{
						header:'是否在职',
						dataIndex:'zt',
						width:80,
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							hiddenName:'zt',
							xtype:'combo',
							readOnly:true,
							emptyText:'必填',
							allowBlank:false,
							triggerAction:'all',
							displayField:"ztmc",
							valueField:'zt',
							store:ztstore,
							mode:'local'
						}))
					},{
						header:'离职时间',
						dataIndex:'lzsj',
						width:120,
						editor:new Ext.grid.GridEditor(new Ext.form.DateField({
							format : 'Y-m-d'
						}))
					},{
						header:'备注',
						dataIndex:'memo',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
						}))
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
			
			//初始化工具栏
			initDataTbar([_add,_delete,_save,"-",_refresh,_condition]);
			//添加窗口
			var addRyglWinow = new Ext.Window({//弹出窗
				width:500,
				height:220,
				modal:true,//是不是要覆盖窗口下空间
				plain:true,//让窗体颜色融合
				layout:'fit',//布局方式：自动填满
				title:'添加人员信息',//标题
				closeAction: 'hide',//关闭窗口调用隐藏方法
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addRyglForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/jxc/JxcRyglAction.do?method=addRygl',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				addRyglWinow.hide();
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
							addRyglWinow.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'addRyglForm',
						border:false,
						baseCls:'x-plain',//溶于底色
						autoScroll:true,
						layout:'column',
						labelAlign:'right',
						bodyStyle:'padding:10px',
						defaults:{
							xtype:'fieldset',
							border:false
						},
						items:[
							{
								columnWidth:0.5,
								labelWidth:75,
								defaultType:'textfield',
								defaults:{
									anchor:'95%'//显示比例
								},
								items:[{
									fieldLabel:'员工名称',
									allowBlank:false,
									name:'ygmc'
								},{
									fieldLabel:'性别',
									hiddenName:'xb',
									xtype:'combo',
									readOnly:true,
									emptyText:'必填',
									allowBlank:false,
									triggerAction:'all',
									displayField:"xbmc",
									valueField:'xb',
									store:xbstore,
									mode:'local'
								},{
									fieldLabel:'入职时间',
									allowBlank:false,
									name:'rzsj',
									emptyText:'必填',
									readOnly:true,
									xtype:'datefield',
									format : 'Y-m-d'
								},{
									fieldLabel:'离职时间',
									name:'lzsj',
									readOnly:true,
									xtype:'datefield',
									format : 'Y-m-d'
								}]
							},{
								columnWidth:0.5,
								labelWidth:75,
								defaultType:'textfield',
								defaults:{
									anchor:'95%'//显示比例
								},
								items:[{
									fieldLabel:'联系电话',
									name:'lxdh',
									allowBlank:false
								},{
									fieldLabel:'身份证号',
									allowBlank:false,
									name:'sfzh'
								},{
									fieldLabel:'是否在职',
									hiddenName:'zt',
									xtype:'combo',
									readOnly:true,
									emptyText:'必填',
									allowBlank:false,
									triggerAction:'all',
									displayField:"ztmc",
									valueField:'zt',
									store:ztstore,
									mode:'local'
								},{
									fieldLabel:'部门',
									allowBlank:false,
									name:'deptmc'
								}]
							},{
								columnWidth:1,
								labelWidth:40,
								defaultType:'textfield',
								defaults:{
									anchor:'97%'//显示比例
								}
								
								
							}
						]
					}
				]
			});
			
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '人员查询',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:68,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'95%'
				},
	            items:[
					{
						fieldLabel:'员工名称',
						name:'yhmc'
					},{
						fieldLabel:'电话',
						name:'lxdh'
					},{
						fieldLabel:'是否在职',
						hiddenName:'zt',
						xtype:'combo',
						readOnly:true,
						triggerAction:'all',
						displayField:"ztmc",
						valueField:'zt',
						store:ztstore,
						mode:'local'
					},{
						fieldLabel:'部门',
						name:'deptmc'
					}
				],
				buttons:[
					{
						text:'查 询',
						handler:function(){
							Ext.apply(dataStore.baseParams,{
								conditions:encodeURIComponent(Ext.encode(RelativeActionView.form.getValues()))//设置条件form中的值到store中
							});   
							dataStore.load({
								params: {//读取是传给后台的参数
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
				addRyglWinow.show();
			});
			//修改事件
			getSaveBtn().on('click',function(){
				var m = dataStore.modified.slice(0);//收集页面列表修改数据
				var jsonArray = [];//放修改数据的数组
				Ext.each( m, function(item){//遍历修改数据
					jsonArray.push(item.data);//修改数据放入数组
				});
				if(jsonArray.length == 0){
					return;
				}
				var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....','提示');
				var jsonData = encodeURIComponent(Ext.encode(jsonArray));//Ext.encode(jsonArray)：把json实体转换成字符串
				Ext.Ajax.request({
					method: 'POST',
					url: contextPath+'/jxc/JxcRyglAction.do?method=updateRygl',
					success: function(response){
						var rs = Ext.decode(response.responseText)//Ext.decode():把字符串转换成对象
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
					params:{data:jsonData }   //把参数jsonData传到后台
				});
			});
			//删除事件
			getDeleteBtn().on('click',function(){
				var r = checkboxModel.getSelected();
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了员工："+r.get('yhmc')+"，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.Msg.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/jxc/JxcRyglAction.do?method=deleteRygl&id='+id,
							{
								success:function(response){
									var rs = Ext.decode(response.responseText);//字符串转换为json属性实体
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
			setNavigate("基础管理 >> 人员维护 ","本模块维护员工基本信息");
			var waitMsg = Ext.MessageBox.wait('提示信息','数据加载中，请稍候...');
			dataStore.load({//先读出来
				callback:function(r, options, success){
					if(success){
						//dataStore.load({params:{start:0,limit:15}});
						waitMsg.hide();
					}else{
						Ext.Msg.alert('提示','人员类型数据加载失败!');
					}
				}
			});
			
		});
		
	</script>
  </head>
  <body>
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
