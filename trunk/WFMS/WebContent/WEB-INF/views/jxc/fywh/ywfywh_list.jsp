<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>业务费用信息</title>
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
	<%
	String name = (String)request.getAttribute("username");
	 %>
	<script type="text/javascript">
		var lx = '';
		Ext.onReady(function(){
			/*infoView.add(new Ext.form.FieldSet({
	            title: '费用维护',
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
						fieldLabel:'消费对象',
						name:'xfzmc'
					},{
						fieldLabel:'消费时间',
						name:'xfsj'
					},{
						fieldLabel:'金额',
						name:'fy'
					},{
						fieldLabel:'记录日期',
						name:'jlsj'
					},{
						fieldLabel:'记录人',
						name:'jlrmc'
					},{
						fieldLabel:'说明',
						name:'memo'
					}
				]
			}));*/
			//列表数据对象
			var dataStore = new Ext.data.JsonStore({//数据存储
				autoDestroy: true,//自动数据填入Store
				pruneModifiedRecords:true,//更新数据时,自动去掉编辑标记
				root:'dataList',//读取数据的时候所读取的属性节点
				totalProperty:'totalCount',//读取属性节点的记录数
		        url:contextPath+'/jxc/ywfywhAction.do?method=getAllFywh',//contextPath+
				fields:['id','khmc','zt','je','jysj','jsrmc','ywdid','czrmc','czsj','memo']//实体属性名称
			});
			//费用类型
			var fylxStore =	new Ext.data.JsonStore({
				data:[{fyid:'0',ztmc:'支出'},{fyid:'1',ztmc:'收入'}],//0厂家，1经销商
				fields:['fyid','ztmc']
			});
			//费用对象
			var xfStore = new Ext.data.JsonStore({
				url:contextPath+"/jxc/KhwhAction.do?method=getAllKyxxByJoin",
				//autoLoad:true,
				fields:["id","jdmc"]
			});
			//数据业务单
			var ywdStore = new Ext.data.JsonStore({
				url:contextPath+"/jxc/ywfywhAction.do?method=getAllXfdxByJoin",
				//autoLoad:true,
				fields:["ywdid","khid"]
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
						header:'支付对象',
						dataIndex:'khmc',
						width:120
					},{
						header:'状态',
						dataIndex:'zt',
						width:80
					},{
						header:'金额(元)',
						dataIndex:'je',
						width:80
					},{
						header:'交易时间',
						dataIndex:'jysj',
						width:100
					},{
						header:'经手人名称',
						dataIndex:'jsrmc',
						width:120
					},{
						header:'业务单编号',
						dataIndex:'ywdid',
						width:120
					},{
						header:'操作人名称',
						dataIndex:'czrmc',
						width:120
					},{
						header:'操作时间',
						dataIndex:'czsj',
						width:100
					},{
						header:'消费说明',
						dataIndex:'memo',
						width:200
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
			initDataTbar([_add,_delete,"-",_refresh,_condition]);
			//添加窗口
			var addHzqyWinow = new Ext.Window({//弹出窗
				width:300,
				height:300,
				modal:true,//是不是要覆盖窗口下空间
				plain:true,//让窗体颜色融合
				layout:'fit',//布局方式：自动填满
				title:'费用维护',//标题
				closeAction: 'hide',//关闭窗口调用隐藏方法
				buttons:[
					{
						text:'确 定',
						handler:function(){
							var aqf = Ext.getCmp('addHzqyForm');
							if(aqf.form.isValid()){
								aqf.form.doAction('submit',{
									url:contextPath+'/jxc/ywfywhAction.do?method=addFywh',
									method:'post',
									waitTitle:'提示信息',                                                    
			                        waitMsg:'数据更新中，请稍候...',
			                        success:function(form,action){
			                        	if(action.result.success == true){
			                        		aqf.form.reset();
											dataStore.reload();
			                				addHzqyWinow.hide();
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
							addHzqyWinow.hide();
						}
					}
				],
				items:[
					{
						xtype:'form',
						id:'addHzqyForm',
						border:false,
						baseCls:'x-plain',//溶于底色
						autoScroll:true,
						layout:'column',
						labelAlign:'right',
						defaults:{
							xtype:'fieldset',
							border:false
						},
						items:[
							{
								columnWidth:0.9,
								labelWidth:100,
								defaultType:'textfield',
								bodyStyle:'padding-top:25px',
								defaults:{
									anchor:'98%'//显示比例
								},
								items:[
									{
										fieldLabel:'费用类型',
										hiddenName:'zt',
										xtype:'combo',
										readOnly:true,
										emptyText:'必填',
										allowBlank:false,
										triggerAction:'all',
										displayField:"ztmc",
										valueField:'fyid',
										store:fylxStore,
										mode:'local',
										listeners:{
											'select':function(cob,r,ind){
												xfStore.removeAll();
												if(r.get('fyid') == ''){
													return;
												}
												lx = r.get('fyid');
												Ext.apply(xfStore.baseParams,{column:'khlb',khlb:r.get('fyid')});
												xfStore.reload();
											}
										}
									},{
										fieldLabel:'支付对象',
										xtype:'combo',
										hiddenName:'khid',
										mode:'local',//
										readOnly:true,
										triggerAction:'all',
										allowBlank:false,
										displayField:"jdmc",
										valueField:'jdmc',
										store:xfStore,
										listeners:{
											'select':function(cob,r,ind){
												ywdStore.removeAll();
												if(r.get('khid') == ''){
													return;
												}
												Ext.apply(ywdStore.baseParams,{column:'khmc',khmc:r.get('jdmc')},{column:'fylx',fylx:lx});
												ywdStore.reload();
											}
										}
									},{
										fieldLabel:'对应业务单',
										xtype:'combo',
										hiddenName:'ywdid',
										mode:'local',//
										readOnly:true,
										triggerAction:'all',
										allowBlank:false,
										displayField:"khid",
										valueField:'ywdid',
										store:ywdStore
									},{
										fieldLabel:'费用',
										xtype:'numberfield',
										name:'fy',
										emptyText:'必填',
										allowBlank:false,
									},{
										fieldLabel:'发生日期',
										name:'jysj',
										emptyText:'必填',
										allowBlank:false,
										xtype:'datefield',
										readOnly:true,
										value:new Date(),
										format : 'Y-m-d'
									},{
										fieldLabel:'经手人名称',
										name:'jsrmc',
										emptyText:'必填',
										allowBlank:false,
										value:'<%=name %>'
									},{
										fieldLabel:'说明',
										allowBlank:true,
										name:'memo'
									}
								]
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
	            title: '费用查询',
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
						fieldLabel:'消费类型',
						name:'fylx'
					},{
						fieldLabel:'消费对象',
						name:'xfzmc'
					},{
						fieldLabel:'消费日期',
						name:'xfsj',
						allowBlank:false,
						xtype:'datefield',
						readOnly:true,
						format : 'Y-m-d'
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
				addHzqyWinow.show();
			});
			//修改事件
			/*_save
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
					url: contextPath+'/jxc/ywfywhAction.do?method=updateFywh',
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
			});*/
			//删除事件
				getDeleteBtn().on('click',function(){
				var r = checkboxModel.getSelected();
				if(r == null){
					return;
				}
				var id = r.get('id');
				Ext.Msg.confirm("删除提示","是否确认删除你选中的费用信息？",function(btn,text){
					if( btn == 'yes'){
						var mw = Ext.Msg.wait('提示信息','数据更新中，请稍候...');
						Ext.lib.Ajax.request(
							'POST',
							contextPath+'/jxc/ywfywhAction.do?method=deleteFywh&id='+id,
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
			setNavigate("费用管理 >> 业务费用信息维护 ","本模块主要维护资产帐务信息,客户二次打款或二次还款给商家！");
			dataStore.load();
			
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
