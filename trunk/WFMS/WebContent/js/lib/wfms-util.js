var wfms = {};
wfms.getAppName = function(){
	return '/WFMS';
}
APPNAME = wfms.getAppName();
Ext.form.field.Base.prototype.msgTarget = 'side';
Ext.tip.QuickTipManager.init();
//Ext.QuickTips.init();
Ext.BLANK_IMAGE_URL = (Ext.isIE6 || Ext.isIE7) ? wfms.getAppName()+'/ext4/resources/s.gif' : 'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
Ext.Ajax.timeout = 60000*3;
wfms.defaultUrl = function(){
	return wfms.getAppName()+'/login.html?t='+new Date().getTime();
}

function reloadvaildcode(){
    var a = Ext.select('img.vaildcode_img');
	a.first().dom.setAttribute('src',wfms.getAppName()+'/web/VerifyCodeServlet?t='+Math.random());
}

//cfg-{masker,url,params,scope,method}
wfms.openLink = function(cfg){
	cfg = typeof cfg == 'object' ? cfg : {};
	if(cfg.masker){
		cfg.masker.el.mask('Please wait...','openLinkLoading');
	}	
	Ext.Ajax.request({
	    url: cfg.url||wfms.defaultUrl(),
	    params :cfg.params||{e:''},
	    method : cfg.method||'post',
	    success : function(response, opts){
	      	if(cfg.masker){cfg.masker.el.unmask();}
	      	var rs = response.responseText?Ext.decode(response.responseText):{};		
			if(rs.success){
				if(Ext.isFunction(cfg.onSuccess)){
					Ext.Function.bind(cfg.onSuccess,cfg.scope||window,[rs, opts])();
				}
			}else{
				Ext.Msg.show({
				     title:'Message',
				     msg: rs.msg||'Error!!',
				     width : 280,
				     buttons: Ext.Msg.OK,
				     icon: Ext.Msg.WARNING,
				     scope : cfg.scope,
				     closable : false,
				     fn : function(btn){
				     	if(Ext.isFunction(cfg.onFailure)){
				     		Ext.Function.bind(cfg.onFailure,cfg.scope||window,[rs, opts,btn])();
						}
				     }
				});
			}				
      },
      failure : function(response, opts){
	      	if(cfg.masker){cfg.masker.el.unmask();}
			var rs = response.responseText?Ext.decode(response.responseText):{};
			Ext.Msg.show({
			     title:'Message',
			     msg: rs.msg||'Error!!',
			     width : 280,
			     buttons: Ext.Msg.OK,
			     icon: Ext.Msg.WARNING,
			     closable : false,
			     scope : cfg.scope,
			     fn : function(btn){
			     	if(Ext.isFunction(cfg.onFailure)){
			     		Ext.Function.bind(cfg.onFailure,cfg.scope||window,[rs, opts,btn])();
					}
			     }
			});
      }
	});
}