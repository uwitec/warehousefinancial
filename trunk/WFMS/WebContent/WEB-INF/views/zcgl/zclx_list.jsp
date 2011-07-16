<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>招聘信息</title>
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
	
	//列表数据对象
	var dataStore = new Ext.data.JsonStore({
		autoDestroy: true,
		pruneModifiedRecords:true,
		root:'dataList',
		totalProperty:'totalCount',
      	url:contextPath+'/zcgl/zclxAction.do?method=getZclxByPage',
		fields:['id',"lxdm","lxmc","bz"]
	});
		infoView.add(new Ext.form.FieldSet({
        title: '资产类型信息',
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
				fieldLabel:'类型代码',
				name:'lxdm'
			},{
				fieldLabel:'类型名称',
				name:'lxmc'
			},{
				fieldLabel:'备注',
				name:'bz'
			}
		]
	}));
	//列表
	var dataGrid = new Ext.grid.EditorGridPanel({//可编辑的表格面板
		stripeRows: true,//隔行变色
		autoScroll:true,//自动滚动条
		trackMouseOver:true,//高亮显示
		border:false,//去掉面板边框
        loadMask: true,//读取数据的时候提示更新
        sm:checkboxModel,
       	cm:new Ext.grid.ColumnModel([
      		 {
				header:'类型代码',
				dataIndex:'lxdm',
				width:200,
				editor:new Ext.grid.GridEditor(new Ext.form.TextField({
					allowBlank:false
				}))
			}, {
				header:' 类型名称',
				dataIndex:'lxmc',
				width:200,
				editor:new Ext.grid.GridEditor(new Ext.form.TextField({
					allowBlank:false
				}))
			}, {
				header:'备注',
				dataIndex:'bz',
				width:200,
				editor:new Ext.grid.GridEditor(new Ext.form.TextField({
					allowBlank:false
				}))
			}
		]),
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
			//添加窗口
			var addZclxWin  =new Ext.Window({ //弹出窗口
				width:300,
				height:250,
				modal:true,
				plain:true,
				layout:'fit', 
				title:'添加资产类型信息', 
				closeAction:'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addZclxForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/zcgl/zclxAction.do?method=addZclx',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				addZclxWin.hide();
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
							addZclxWin.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'addZclxForm',
						border:false,
						baseCls:'x-plain',
						autoScroll:true,
						layout:'form', 
						labelAlign:'right',
						labelWidth:60,
						bodyStyle:'padding:10px',
						defaults:{
							anchor:'95%'
						},
						defaultType:'textfield',
						items:[
								{
									fieldLabel:'类型代码',
									name:'lxdm',
									allowBlank:false
								}, {
									fieldLabel:'类型名称',
									name:'lxmc',
									allowBlank:false
								},{
									fieldLabel:'备注',
									xtype:'textarea',
									height:80,
									name:'bz'
								}
						]
					}
				]
				});
			
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '资产类型',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:70,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'85%'
				},
	            items:[
					{
						fieldLabel:'类型代码',
						name:'lxdm'
					},{
						fieldLabel:'类型名称',
						name:'lxmc'
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
				addZclxWin.show();
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
					url: contextPath+'/zcgl/zclxAction.do?method=updateZclx',
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
				Ext.Msg.confirm("删除提示","您选择了："+r.get('lxmc')+"资产类型信息，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/zcgl/zclxAction.do?method=deleteZclx&id='+id,
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
			setNavigate("资产管理 >> 资产类型维护 ","本模块提供资产类型的基本信息");
			//var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			dataStore.load({params:{start:0,limit:15}});
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
