<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>学生信息</title>
	<!-- Extjs导入 -->
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
			//new Ext.QuickTips.init();//提示信息加载
			/*var drbStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=DRB',
				fields:["dmjc","dmmc"]
			});*/
			var statObjData=[
			['ZYID','专业'],['XB','性别'],['MZ','民族'],
			['JG','籍贯'],['XZ','学制'],['XSLBDM','学生类别'],['SYD','生源地'],
			['ZZMM','政治面貌']
			];
			var statCategoryData=[
			['专业','ZYID'],['年级','DQSZJ'],['班级','BJID']
			];
			
			//统计对象store
			var statObjStore=new Ext.data.SimpleStore({
				fields:['value','text'],
				data:statObjData
				});
			//统计分类store
			var statCategoryStore=new Ext.data.SimpleStore({
				fields:['text','value'],
				data:statCategoryData
				});
			var statObjCombo=new Ext.form.ComboBox({
				store:statObjStore,
				emptyText:'请选择',
				mode:'local',
				triggerAction:'all',
				valueField:'value',
				textField:'text',
				displayField:'text',
				hiddenName:'statObj',
				readOnly:true,
				renderTo:'stat_obj',
				id:'obj',
				minListWidth:220,
				pageSize:5
			});
			var statCategoryCombo=new Ext.form.ComboBox({
				store:statCategoryStore,
				emptyText:'请选择',
				mode:'local',
				triggerAction:'all',
				valueField:'value',
				textField:'text',
				displayField:'text',
				hiddenName:'statCategory',
				readOnly:true,
				renderTo:'stat_category',
				id:'category',
				minListWidth:220,
				pageSize:5
			});
			var button=new Ext.Button({
			    renderTo:'btn_stat',
			    width:100,
			    text:'统计',
			    handler: dataStat
			});
			
			//var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息');
			function dataStat(){
				var statObj=Ext.getCmp('obj').getRawValue();
				var statCategory=Ext.getCmp('category').getRawValue();
				
				var rowKey = Ext.getCmp('obj').getValue();
				var columnKey=Ext.getCmp('category').getValue();
				if(rowKey==columnKey)
				{
					return;
				}
				var getUrl=contextPath+'/common/dataStatAction.do?statObj='+Ext.encode(statObj)+'&statCategory='+Ext.encode(statCategory);
				getUrl+='&row='+rowKey+'&column='+columnKey;
				document.getElementById('stat_view').src=getUrl;
			}
		});
		
	  </script>
	</head>
  <body enctype="multipart/form-data">
  <input type="hidden" id='qx' value='${qxlx}'>
  <table style='margin:10px;width:95%' height="100%" border='0' >
  	<tr height="20%">
  		<td style="width:33%;" id="stat_obj">
		</td>
		<td style="width:33%;" id="stat_category">
		</td>
		<td style="width:34%" id="btn_stat"></td>
  	</tr>
	<tr height="80%">
  		<td style="width:100%;" width="100%" colspan="3"><iframe id="stat_view" src="" style="overflow:visible" width="100%" height="100%" frameborder="0"></iframe>
		</td>
  	</tr>
  </table>		
  </body>
</html>
