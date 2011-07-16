<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>专业代码信息</title>
	<!-- Extjs导入 -->
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
	<!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/add_viewport.js"></script>-->
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
			var zydmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=ZYLBDM',
				fields:["lxdm","dmmc"]
			});
			var zylbdmData={};
			var xzdmData={};
			var xzdmStore = new Ext.data.JsonStore({  
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=XZDM',
				fields:["dmjc","dmmc"]
			});
			//右侧详细信息添加filedset
			infoView.add(new Ext.form.FieldSet({
	            title: '专业类型基本信息',
	            collapsible: true,
	            autoScroll:true,
	            labelWidth:55,
				labelAlign:'right',
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
						fieldLabel:'专业代码',
						name:'zydm'
					},{
						fieldLabel:'专业名称',
						name:'zymc'
					},{
						fieldLabel:'专业类别',
						xtype:'combo',
						name:'zylbdm',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:zydmStore
					},{
						fieldLabel:'学制',
						xtype:'combo',
						name:'xz',
						mode:'local',
						readOnly:true,
						hideTrigger:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'dmjc',
						store:xzdmStore
					},{
						fieldLabel:'标准代码',
						name:'zszydm'
					},{
						fieldLabel:'学位',
						name:'xw'
					},{
						fieldLabel:'层次',
						name:'cc'
					},{
						fieldLabel:'培养目标',
						name:'zypymb'
					},{
						fieldLabel:'培养要求',
						name:'zypyyq'
					},{
						fieldLabel:'专业课程',
						name:'zykc'
					},{
						fieldLabel:'特色课程',
						name:'tskc'
					},{
						fieldLabel:'英文名称',
						name:'zyywmc'
					},{
						fieldLabel:'优先级',
						name:'yxj'
					},{
						fieldLabel:'专业简称',
						name:'zyjc'
					},{
						fieldLabel:'收费形式',
						name:'sfxs'
					},{
						fieldLabel:'学科类别',
						name:'xklb'
					},{
						fieldLabel:'专业拼音',
						name:'zypy'
					},{
						fieldLabel:'备注',
						width:'90',
						height:'50',
						name:'bz'
					}
				]
			}));
			
			
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({ //数据存储
				autoDestroy: true,// 自动填入store
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/system/zyglAction.do?method=getZydmByPage',
				fields:["id","zydm","zymc","zylbdm","xz","xw","zypymb","zypyyq","zykc","tskc","zyywmc","yxj","ssxdm","zyjc","tjzydm",
						"cc","xklb","bz","zszydm","tjzymc","sfwy","sfsf",
						"sfexw","kxlb","tlwXw","tlwZsbm","bks","sfxs","xkjxpt","zypy"]
			});//根据属性查询
			
			//列表
			var dataGrid = new Ext.grid.EditorGridPanel({
				stripeRows: true,//隔行变色(斑马效果)
				autoScroll:true,
				trackMouseOver:true,
				border:false,
		        loadMask: true,//设置读取数据时的遮罩和提示功能
		        sm:checkboxModel,
				//列模型
		       	cm:new Ext.grid.ColumnModel([ 
					{
						header:'专业代码',
						dataIndex:'zydm',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'专业名称',
						dataIndex:'zymc',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({
							allowBlank:false
						}))
					},{
						header:'专业类别',
						dataIndex:'zylbdm',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'lxdm',
							store:zydmStore
						})),
						renderer:function(value){
							var result = "";
							result = zylbdmData[value];
							return result;
						}
					},{
						header:'学制',
						dataIndex:'xz',
						editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
							mode:'local',
							readOnly:true,
							triggerAction:'all',
							displayField:"dmmc",
							valueField:'dmjc',
							store:xzdmStore
						})),
						renderer:function(v){
							var rs = '';
							rs = xzdmData[v];
							return rs;
						}						
					},{
						header:'国家标准代码',
						dataIndex:'zszydm',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'层次',
						dataIndex:'cc',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'学位',
						dataIndex:'xw',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'培养目标',
						dataIndex:'zypymb',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'培养要求',
						dataIndex:'zypyyq',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'专业课程',
						dataIndex:'zykc',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'特色课程',
						dataIndex:'tskc',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'英文名称',
						dataIndex:'zyywmc',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'优先级',
						dataIndex:'yxj',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'专业简称',
						dataIndex:'zyjc',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'学科类别',
						dataIndex:'xklb',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'收费形式',
						dataIndex:'sfxs',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
					},{
						header:'专业拼音',
						dataIndex:'zypy',
						editor:new Ext.grid.GridEditor(new Ext.form.TextField({}))
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
			initDataTbar([_add,_delete,_save,"-",_refresh,_condition]);
			//添加窗口
			var addZydmWin  =new Ext.Window({ //弹出窗口
				width:550,
				height:380,
				modal:true,
				plain:true,
				layout:'fit', //布局方式(自动填满)
				title:'添加专业信息',
				closeAction:'hide',
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addZpxxForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/system/zyglAction.do?method=addZydm',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				addZydmWin.hide();
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
							addZydmWin.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'addZpxxForm',
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
							labelWidth:80,
							border:false,
							defaultType:'textfield',
							defaults:{
								anchor:'95%'
							},
							items:[
								{
									fieldLabel:'专业代码',
									allowBlank:false,
									name:'zydm'
								},{
									fieldLabel:'专业名称',
									allowBlank:false,
									name:'zymc'
								},{
									fieldLabel:'专业类别',
									xtype:'combo',
									hiddenName:'zylbdm',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'lxdm',
									store:zydmStore
								},{
									fieldLabel:'学制',
									xtype:'combo',
									hiddenName:'xz',
									mode:'local',
									readOnly:true,
									triggerAction:'all',
									displayField:"dmmc",
									valueField:'dmjc',
									store:xzdmStore
								},{
									fieldLabel:'国家标准代码',
									name:'zszydm'
								},{
									fieldLabel:'学位',
									name:'xw'
								},{
									fieldLabel:'层次',
									name:'cc'
								},{
									fieldLabel:'培养目标',
									name:'zypymb'
								},{
									fieldLabel:'培养要求',
									name:'zypyyq'
								}
							]
						},{
							columnWidth:0.5,
							labelWidth:75,
							border:false,
							defaultType:'textfield',
							defaults:{
							  anchor:'95%'
							},
							items:[
								{
									fieldLabel:'专业课程',
									name:'zykc'
								},{
									fieldLabel:'特色课程',
									name:'tskc'
								},{
									fieldLabel:'英文名称',
									name:'zyywmc'
								},{
									fieldLabel:'优先级',
									name:'yxj'
								},{
									fieldLabel:'专业简称',
									name:'zyjc'
								},{
									fieldLabel:'收费形式',
									name:'sfxs'
								},{
									fieldLabel:'学科类别',
									name:'xklb'
								},{
									fieldLabel:'专业拼音',
									name:'zypy'
								}
							]
						},{
							columnWidth:1,
							labelWidth:75,
							border:false,
							defaultType:'textfield',
							defaults:{
								anchor:'98%'
							},
							items:[
									{
										fieldLabel:'备注',
										xtype:'textarea',
										width:'90',
										height:'50',
										name:'bz'
									}
							]
						}
						]
					}
				]
				});
			
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '专业信息查询',
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
						fieldLabel:'专业代码',
						name:'zydm'
					},{
						fieldLabel:'专业名称',
						name:'zymc'
					},{
						fieldLabel:'专业类别',
						xtype:'combo',
						hiddenName:'zylbdm',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'lxdm',
						store:zydmStore
					},{
						fieldLabel:'学制',
						xtype:'combo',
						hiddenName:'xz',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"dmmc",
						valueField:'dmjc',
						store:xzdmStore
					},{
						fieldLabel:'层次',
						name:'cc'
					},{
						fieldLabel:'培养目标',
						name:'zypymb'
					},{
						fieldLabel:'培养要求',
						name:'zypyyq'
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
				addZydmWin.show();
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
					url: contextPath+'/system/zyglAction.do?method=updateZydm',
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
				var r = checkboxModel.getSelected();   //r ：一个选中的实体记录
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","您选择了："+r.get('zymc')+"专业信息，请确认下面是否有班级和学生关联此专业，是否确认删除?",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.MessageBox.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/system/zyglAction.do?method=deleteZydm&id='+id,
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
			setNavigate("系统管理 >> 专业代码管理 ","本模块提供专业的基本信息");
			var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			zydmStore.load({//先读出来
				callback:function(r, options, success){
					if(success){
						//dataStore.load({params:{start:0,limit:15}});
						waitMsg.hide();
						zydmStore.each(function (record){
							zylbdmData[record.get('lxdm')]=record.get('dmmc');
						});
					}else{
						Ext.Msg.alert('提示','专业类别数据加载失败!');
					}
					xzLoad;
				}
			});
			
			var xzLoad = xzdmStore.load({
				callback:function(r, options, success){
					if(success){
						xzdmStore.each(function (record){
							xzdmData[record.get('dmjc')]=record.get('dmmc');
						});
						waitMsg.hide();
					}else{
						Ext.Msg.alert('提示','学制数据加载失败!');
					}
					dataStore.load({params:{start:0,limit:15}});
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
