<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>教室密码修改</title>
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
					title:'输入密码',
					autoHeight:true,
					width:300,
					items:[{
						xtype:'textfield',
						name:'oldmm',
						fieldLabel:'旧密码',
						inputType:'password',
						allowBlank:false
					},{
						xtype:'textfield',
						inputType:'password',
						name:'newmm',
						fieldLabel:'新密码',
						allowBlank:false
					},{
						xtype:'textfield',
						inputType:'password',
						name:'lastmm',
						fieldLabel:'确认密码',
						allowBlank:false
					}],
					buttons:[{
						text:'确 定',
						handler:function(){
							form.getForm().submit({
								url:contextPath+"/system/yhglAction.do?method=batchInserUpdateXsmm",
								success:function(f,action){
									if(action.result.success){
										Ext.Msg.alert('提示',action.result.message);
									}else{
										Ext.Msg.alert('提示',action.result.message);
									}
								},failure:function(f,action){
									if(action.result.success){
										Ext.Msg.alert('提示',action.result.message);
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
					title : '用户密码维护',
					closable:false,
					closeAction : 'hide',
					items:[form]
				});	
			ztWin.show();
			
			
			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			//dataPanel.add(form);
			setNavigate("统一系统平台 >> 系统管理 >> 个人信息维护 >> 密码维护","用户维护个人密码");

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
