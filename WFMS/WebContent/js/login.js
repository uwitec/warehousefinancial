Ext.define('wfms.LoginWin', {
    extend: 'Ext.window.Window',
    banner : Ext.create('Ext.Component',{
    	region : 'north',
		height : 100,
		margins : '1 1 1 1',
	    autoEl: {
	        tag: 'div',
	        style : 'background-image:url('+APPNAME+'/images/loginimg.jpg);background-repeat:no-repeat;overflow:auto;zoom:1;'
	    }
    }),
    form : Ext.create('Ext.form.Panel',{
		region : 'center',		
		border : false,
		bodyPadding: 20,			
		defaults : {selectOnFocus:true,msgTarget: 'side',xtype:'textfield',maxLength:45,/*allowBlank:false,*/labelAlign : 'right'},
		items: [{
	        fieldLabel: '账号',
	        name: 'loginId',
	        fieldCls : 'loginUser',
	        anchor:'90%',
	        listeners : {
	        	'afterrender':function(o){			        		
	        		new Ext.util.KeyMap(o.el, {
					    key: 13,
					    fn: function(){
					    	if(o.isValid()){
			        			o.nextSibling().focus(true,true);
			        		}else{
			        			o.focus(true,true);
			        		}
					    },
					    scope: o
					});		        		
	        	},scope:this
	        }
	    },{
	        fieldLabel: '密码',
	        name: 'loginPwd',
	        inputType : 'password',
	        fieldCls : 'loginKey',
	        anchor:'90%',
	        listeners : {
	        	'afterrender':function(o){
	        		new Ext.util.KeyMap(o.el, {
					    key: 13,
					    fn: function(){
					    	if(o.isValid()){
			        			o.nextSibling().focus(true,true);
			        		}else{
			        			o.focus(true,true);
			        		}
					    },
					    scope: o
					});		        		
	        	},scope:this
	        }
	    },{
	    	xtype:'combo',
	    	fieldLabel: '登录到',
	    	//editable : false,
	    	name : 'url',
	    	value:'/index.html',
	    	anchor:'90%',
		    store: Ext.create('Ext.data.Store',{
		    	fields: ['url', 'name'],
			    data : [
			        {"url":"/index.html", "name":"后台管理模块"}
			    ]
		    }),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'url',
	        listeners : {
	        	'afterrender':function(o){
	        		new Ext.util.KeyMap(o.el, {
					    key: 13,
					    fn: function(){
					    	if(o.isValid()){
			        			o.nextSibling().focus(true,true);
			        		}else{
			        			o.focus(true,true);
			        		}
					    },
					    scope: o
					});		        		
	        	},scope:this
	        }
	    },{
	        fieldLabel: '验证码',
	        name: 'verify',
	        fieldCls : 'verifyKey',
	        width : 250,
	        //maxLength : 4,
	        //minLength : 4,
	        listeners : {
	        	'afterrender':function(o){
	        		var bd = o.getEl().dom;
	               	var bd2 = Ext.get(bd.parentNode);
	               	bd2.createChild([{
		                    tag: 'span',
		                    html: '<a href="#" onclick="reloadvaildcode()">'
		                },{
					        tag: 'img',
					        cls: 'vaildcode_img',
					        qtip : '点击重新取得',
					        src: wfms.getAppName()+'/VerifyCodeServlet?t='+Math.random()
					    }
			    	]);
				    
	        		new Ext.util.KeyMap(o.el, {
					    key: 13,
					    fn: function(){
					    	if(o.isValid())
					    	o.up('form').up('window').down('button').focus();
					    },
					    scope: o
					});
	        	},scope:this
	        }
	    }]
    }),
    initComponent: function() {
    	Ext.apply(this,{
	    	title : '登录',
			width : 378,
			height : 320,
			resizable : false,
			closable : false,
			layout : 'border',
			items : [
				this.form,this.banner
			],
			buttonAlign : 'right',
			buttons : [
				{text:'登录',scope:this,handler:function(){this.login();}},
				{text:'清空',scope:this,name:'reset',handler:function(){
					this.down('form').getForm().reset();
					this.down('form').getForm().findField('loginId').focus(true,true);
				}}
			]
	    });
        this.callParent();
        //keel.LoginWin.superclass.initComponent.call(this);
        this.on('show',function(){
        	this.down('form').getForm().findField('loginId').focus(true,true);
        },this);
    },
    login : function(){
    	if(this.down('form').getForm().isValid()){
    		this.el.mask('Please wait...','openLinkLoading');
    		this.down('button').setDisabled(true);
    		this.down('[name=reset]').setDisabled(true);
			var params = this.down('form').getForm().getValues();
			params.clsName = 'LoginAction';
			params.methodName = 'login';
			params.action = 'login';
			wfms.openLink({
				url:'system/user_manage/login.do',
				params : params,
				scope : this,
				onSuccess : function(rs,opts){
					params = null;
					var url = this.down('form').getForm().findField('url').getValue();
					window.location.href = APPNAME+url;
				},
				onFailure : function(rs,opts,btn){
					reloadvaildcode();
					this.down('form').getForm().findField('loginId').focus(true,true);
					this.down('form').getForm().findField('verify').reset();
					this.el.unmask();
					this.down('button').setDisabled(false);
    				this.down('button').nextSibling('button').setDisabled(false);
				}			
			});
		}
    }
});

var initLogin = function(){
	var loginWin = Ext.create('wfms.LoginWin');
	var b=Ext.get("loading");
    var a=Ext.get("loading-mask");
    a.setOpacity(0.9);
    a.shift({
        x:b.getX(),
        y:b.getY(),
        width:b.getWidth(),
        height:b.getHeight(),
        remove:true,
        duration:1,
        opacity:0.3,
        easing:"bounceOut",
        callback:function(){
            b.fadeOut({duration:0.3,remove:true});
        }
    });
	loginWin.show();
}