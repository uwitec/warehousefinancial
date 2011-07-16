<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>批量生成学生密码</title>
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
			
			//initDataTbar([_add,_delete,_refresh,upd]);
			
			
			var form = new Ext.form.FormPanel({
				//title:'密码维护',
				labelAlign:'right',
				//layout:'form',
				items:[{
					xtype:'fieldset',
					title:'选择密码生成类型',
					autoHeight:true,
					width:300,
					items:[{
						xtype:'radio',
						boxLabel:'学号',
						name:'type',
						inputValue:'0',
						checked:true
					},{
						xtype:'radio',
						boxLabel:'身份证',
						name:'type',
						inputValue:'1'
					},{
						xtype:'radio',
						boxLabel:'其他',
						name:'type',
						inputValue:'2'
					},{
						xtype:'textfield',
						name:'mm',
						fieldLabel:'密码',
						maxLength:32,
						minLentth:6,
						allowBlank:true
					},{
						xtype:'numberfield',
						name:'xh',
						fieldLabel:'学号',
						allowNegative:false,
						allowDecimals:false,
						maxValue:99999999,
						minValue:11111111,
						allowBlank:true
					}],
					buttons:[{
						text:'确 定',
						handler:function(){
							form.getForm().submit({
								url:contextPath+"/system/yhglAction.do?method=batchInserXsmm",
								success:function(f,action){
									if(action.result.success){
										Ext.Msg.alert('提示','初始密码设置成功！');
									}else{
										Ext.Msg.alert('提示',action.result.message);
									}
								},failure:function(f,action){
									if(action.result.success){
										Ext.Msg.alert('提示','初始密码设置成功！');
									}else{
										Ext.Msg.alert('提示',action.result.message);
									}
								}
							});
						}
					},{
						text:'重 置',
						handler:function(){
							form.form.reset();
						}
					}]
				}]
			});
			var ztWin = new Ext.Window({ 
					width : 350,
					height : 250,
					modal : true,
					plain : true,
					layout : 'fit',
					title : '学生密码维护',
					closable:false,
					closeAction : 'hide',
					items:[form]
				});	
			ztWin.show();
			
			
			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			//dataPanel.add(form);
			setNavigate("统一系统平台 >> 辅助功能 >> 学生密码维护","维护学生密码");

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
