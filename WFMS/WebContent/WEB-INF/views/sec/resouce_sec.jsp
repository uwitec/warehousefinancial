<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	if(request.getHeader("referer") !=null)
	{
		System.out.println("in");
		String comUrl=request.getHeader("referer");
		String contextPath=request.getRequestURL().
		substring(0,request.getRequestURL().indexOf(request.getRequestURI()))
		+request.getContextPath()+"/";
		System.out.println(comUrl);
		if(comUrl.indexOf(request.getContextPath()+"/login")!=-1
		|| comUrl.equals(contextPath))
		{
			System.out.println("yes");
			out.println("Ext.onReady(function(){var timer;Ext.QuickTips.init();new Ext.form.TextField({width:150,emptyText:'请输入登陆账号',renderTo:'login_yhm',value:'admin',id:'yhm',blankText:'用户账号必须输入',selectOnFocus:true,allowBlank:false});new Ext.form.TextField({renderTo:'login_mm',width:150,selectOnFocus:true,id:'mm',value:'xunersoft',blankText:'用户密码必须输入',inputType:'password',allowBlank:false});new Ext.form.ComboBox({width:150,renderTo:'login_yhz',fieldLabel:'用户组',id:'yhz',mode:'local',displayField:'name',valueField:'value',hiddenName:'value',triggerAction:'all',readOnly:true,store:new Ext.data.JsonStore({fields:['value','name'],data:[{value:'teacher',name:'教师'},{value:'student',name:'学生'}]}),listeners:{beforerender:function(){this.setRawValue('教师');this.setValue('teacher');}}});Ext.get('login_btn').on('click',function(){loginSubmit();});Ext.get('reset_btn').on('click',function(){Ext.getCmp('yhm').setValue('');Ext.getCmp('mm').setValue('');});var num=1;var status=0;function loginSubmit(){var _yhm=Ext.getCmp('yhm').getValue();var _mm=Ext.getCmp('mm').getValue();var _yhz=Ext.getCmp('yhz').getValue();if(_yhm==''||_mm==''||_yhz==''){return;}Ext.MessageBox.show({title:'登录提示',msg:'正在为您登录，请稍后 . . . ',width:350,progress:true,closable:false});num=1;msgChange(num);LoginProc(_yhm,_mm,_yhz);}Ext.get(document.body).on('keypress',function(e){if(e.getCharCode()==Ext.EventObject.ENTER){loginSubmit();}});Ext.getCmp('yhm').focus(true,true);var msgChange=function(){if(num>0&&num<=10){Ext.MessageBox.updateProgress(num/100,''+num+'%');if(num<10){num++;}}else if(num>10&&num<=95){Ext.MessageBox.updateProgress(num/100,' '+num+'%');if(num<95){if(Math.random()*10>3){num++;}}}else if(num>95&&num<=100){Ext.MessageBox.updateProgress(num/100,' '+num+'%');if(num==100){clearTimeout(timer);location.href=contextPath+'/index.html';}num++;}if(num<=100){clearTimeout(timer);timer=setTimeout(msgChange,1);}};function LoginProc(_yhm,_mm,_yhz){Ext.Ajax.request({method:'POST',url:contextPath+'/system/yhglAction.do',params:{method:'yhdl',yhm:_yhm,mm:_mm,yhz:_yhz},success:function(response){var rs=Ext.decode(response.responseText);if(rs.success){num=11;msgChange();systemInit();}else{clearTimeout(timer);Ext.Msg.alert('提示',rs.message,function(){Ext.getCmp('yhm').focus(true,true);});}},failure:function(){clearTimeout(timer);Ext.Msg.alert('提示','服务器连接失败,请稍后重试!',function(){Ext.getCmp('yhm').focus(true,true);});}});}function systemInit(){Ext.Ajax.request({method:'POST',url:contextPath+'/system/yhglAction.do',params:{method:'yhInit'},timeout:60000,success:function(response){var initRs=Ext.decode(response.responseText);if(initRs.success){num=96;msgChange();}else{clearTimeout(timer);Ext.Msg.alert('提示',rs.message,function(){Ext.getCmp('yhm').focus(true,true);});}},failure:function(){clearTimeout(timer);Ext.Msg.alert('提示','数据初始化失败,请重新登录',function(){Ext.getCmp('yhm').focus(true,true);});}});}});");
		}
		else
		{
		System.out.println("no");
			out.println("<script type='text/javascript'>alert('请正常访问');</script>");
		}
	}
	else
	{
		out.println("<script type='text/javascript'>alert('请正常访问');</script>");
	}
 %>
