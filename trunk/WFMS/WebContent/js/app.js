
Ext.define('wfms.App',{
	extend : 'Ext.container.Viewport',
	initComponent : function(){
		this.topPanel = Ext.create('Ext.Component',{
			cls: 'app-header',
	        region:'north',
	        el:'header',
	        border:false,
	        margins: '1 0 2 0'
		});
		this.centerPanel = Ext.create('Ext.tab.Panel',{
			margins:'0 0 0 0',
	        enableTabScroll: true,
	        region : 'center',
	        activeTab : 0,
	        layout : 'fit'
		});
		this.westPanel = Ext.create('Ext.tree.Panel', {
		    title : '菜单',
		    width : 200,
		    region : 'west',
		    collapsible : true,
		    split: true,
		    store : Ext.create('Ext.data.TreeStore', {
		        proxy: {
		            type: 'ajax',
		            url : wfms.defaultUrl(),
		            actionMethods : 'post',
		            extraParams : {clsName:'RgModelAction',methodName:'getModelTree'}
		        },
		        root: {
		        	id : '0',
		            text:"菜单",
		            expanded: true
		        }
		    }),
		    rootVisible : true,
		    listeners : {
		    	'itemclick':function(view,record,item,index,e){
		    		var id = record.get('id');
		    		var leaf = record.get('leaf');
		    		if(id!=0&&leaf){
		    			id = 'wfms-'+id;
		    			var o = Ext.getCmp(id);
					    if(!o){
                            o = this.centerPanel.add({
	                            id : id,
	                            title : record.get('text'),
	                            closable : true,
	                            loader : {url: record.get('url'),scripts:true,scope:this,loadMask:true}
                            });          
				        }
						this.centerPanel.setActiveTab(o);
			            this.centerPanel.doLayout();
		    		}	    		
		    	},scope:this
		    }
		});
		Ext.apply(this,{
			layout : 'border',
			items : [this.topPanel,this.centerPanel,this.westPanel]
		});
		this.callParent();
		this.on('afterrender',function(){
			setTimeout(function(){
                Ext.get("loading").remove();
                Ext.get("loading-mask").fadeOut({duration:1,remove:true});
            }, 100);
            this.initHtmlMenuEvent();
		},this);
	},
	sysMenu : new Ext.menu.Menu({
        items : [
            {
                text : '系统皮肤',
                menu : new Ext.menu.Menu({
                    items:[{
                        text: '蓝色海洋',
                        checked: false,
                        group: 'opts',
                        vid : 'ext-all.css'
                    },{
                        text: '钢铁战士1号',
                        checked: false,
                        group: 'opts',
                        vid : 'ext-all-xtheme-blue03.css'
                    },{
                        text: '钢铁战士2号',
                        checked: false,
                        group: 'opts',
                        vid : 'ext-all-xtheme-gray.css'
                    }],
                    listeners : {
                        'click' : function(menu,item,e){
                            //setActiveStyleSheet(item.vid);
                            changecss(item.vid);
                        },
                        'afterrender' : function(m){
                            var css = getCss();                   
                            for(var i=0;i<m.items.length;i++){
                                if(m.items.itemAt(i).vid==css){
                                    m.items.itemAt(i).checked = true;
                                }
                            }
                        }
                    }
                })
            },
            {
                text : '修改密码',
                handler : function(){
                    if(this.editPwdWin){
                        this.editPwdWin.show();
                    }else{
                        this.editPwdWin = new cy.EditPwdWin().show();
                    }                    
                },
                scope:this
            }
        ]
    }),
	initHtmlMenuEvent : function(){                    
        Ext.QuickTips.register({
            text:'访问超赢主页',
            target:Ext.get('cy-index-link')
        });
        
        Ext.get('sys-style').on('click',function(e){
            var x=Ext.get('sys-style').getX();
            var y=this.topPanel.el.getY()+this.topPanel.el.getHeight();
            this.sysMenu.showAt([x,y]);
        },this);
        
        Ext.get('calculator-link').on('click',function(e){
            
        },this);
        
        Ext.get('logout-link').on('click',function(e){
            Ext.Msg.show({
            	title : 'Message',
            	msg : '是否退出系统?',
            	icon : Ext.Msg.QUESTION,
            	buttons : Ext.Msg.YESNO,
            	fn : function(btn){
            		if(btn=='yes'){
            			var loginOutMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在退出系统,请稍候...",cls:'openLinkLoading'});
                    	loginOutMask.show();
                    	wfms.openLink({
							scope : this,
							params : {clsName : 'LoginAction',methodName : 'logout',action:'logout'},
							onSuccess : function(rs,opts){
								window.location.href = wfms.getAppName()+'/index.jsp';
							},
							onFailure : function(rs,opts,btn){
								window.location.href = wfms.getAppName()+'/index.jsp';
							}			
						});
            		}            		
            	}
            });
        },this);
	}
});

var initApp = function(){
	Ext.Ajax.request({
	    url: wfms.defaultUrl(),
	    params:{clsName : 'LoginAction',methodName : 'getUserInfo'},
	    method : 'post',
	    success : function(response, opts){
	      	var rs = response.responseText?Ext.decode(response.responseText):{};		
			if(rs.success){
				new wfms.App();
			}else{
				Ext.get('loading').remove();
		        Ext.get('loading-mask').fadeOut({
		            duration: .5,
		            remove: true
		        });
				Ext.Msg.show({
				     title:'Message',
				     msg: rs.msg||'Error!!',
				     width : 280,
				     buttons: Ext.Msg.OK,
				     icon: Ext.Msg.WARNING,
				     closable : false,
				     fn : function(btn){				     	
						window.location.href = wfms.getAppName()+'/index.jsp';
				     }
				});
				
			}				
	     },
	     failure : function(response, opts){
	     	Ext.get('loading').remove();
	        Ext.get('loading-mask').fadeOut({
	            duration: .5,
	            remove: true
	        });
	     	var rs = response.responseText?Ext.decode(response.responseText):{};
			Ext.Msg.show({
			     title:'Message',
			     msg: rs.msg||'Error!!',
			     width : 280,
			     buttons: Ext.Msg.OK,
			     icon: Ext.Msg.WARNING,
			     closable : false,
			     fn : function(btn){
					window.location.href = wfms.getAppName()+'/login.html';
			     }
			});
	     }
	});
}