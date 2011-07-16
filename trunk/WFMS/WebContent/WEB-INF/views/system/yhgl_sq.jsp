<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<title>学生信息</title>
<!-- Extjs导入 -->
<style type="text/css">
    .store-info {
        background: url('${pageContext.request.contextPath}/imgs/store-info.gif') left top no-repeat;
    }
    
    body {
        font-size: 12px;
    }
</style>
<script type="text/javascript">
    Ext.onReady(function(){
        new Ext.QuickTips.init();//提示信息加载
        var sel_yyid = 0;
        var sel_wyid = 0;
        var sel_wyarry = new Array();
        var sel_yyarry = new Array();
		
		//var zwtpl = '<tpl for="."><div class="x-combo-list-item" ext:qtitle="值" ext:qtip="{depart.name}">{depart.name:ellipsis(15)}</div></tpl>'; 
        //角色信息
        var jsData = new Ext.data.JsonStore({
            autoDestroy: true,
            url: 'yhglAction.do?method=getYhjs&id=${requestScope.yh.memid}',
            fields: [{
                name: 'part.partId'
            }, {
                name: 'part.name'
            }, {
                name: 'part.description'
            }, {
                name: 'part.createTime'
            }]
        });
        //用户未有角色信息--下拉列表信息
        var wyjsData = new Ext.data.JsonStore({
            autoDestroy: true,
            url: 'jsglAction.do?method=getYhwyJs&yhId=${requestScope.yh.memid}',
            fields: [{
                name: 'partId'
            }, {
                name: 'name'
            }]
        });
        //职务信息
        var zwData = new Ext.data.JsonStore({
            autoDestroy: true,
            url: 'yhglAction.do?method=getYhzw&id=${requestScope.yh.memid}',
            fields: [{
                name: 'id'
            }, {
                name: 'role.rolid'
            }, {
                name: 'role.name'
            }, {
                name: 'role.synopsis'
            }, {
                name: 'role.depart.name'
            }, {
                name: 'role.createTime'
            }]
        });
        //用户未有职务信息
        var wyzwData = new Ext.data.JsonStore({
            autoDestroy: true,
            url: 'zwglAction.do?method=getYhwyZw&id=${requestScope.yh.memid}',
            fields: [{
                name: 'rolid'
            }, {
                name: 'name'
            }]
        });
        //功能模块信息
        var gnmkData = new Ext.data.JsonStore({
            autoDestroy: true,
            url: 'yhglAction.do?method=getYhGnmk&id=${requestScope.yh.memid}',
            fields: [{
                name: 'moduleId'
            }, {
                name: 'name'
            }, {
                name: 'description'
            }, {
                name: 'rightType'
            }]
        });
        
        //角色添加窗口
        var addYhjsWin = new Ext.Window({
            title: '请选择需要添加的角色',
            width: 300,
            height: 120,
            closeAction: 'hide',
            modal: true,
            plain: true,
            items: new Ext.form.FormPanel({
                id: 'addYhjsForm',
                baseCls: 'x-plain',
                labelWidth: 40,
                bodyStyle: 'padding:10px',
                border: false,
                frame: true,
                items: new Ext.form.ComboBox({
                    fieldLabel: '角 色',
                    xtype: 'combo',
                    id: 'sel_jsid',
                    baseCls: 'x-plain',
                    triggerAction: 'all',
                    width: 200,
                    allowBlank: false,
                    lazyRender: true,
                    readOnly: true,
                    emptyText: '请选择角色',
                    store: wyjsData,
                    displayField: 'name',
                    valueField: 'partId'
                })
            }),
            buttons: [{
                text: '确 定',
                width: 60,
                handler: function(){
                    var yjf = Ext.getCmp('addYhjsForm');
                    if (yjf.form.isValid()) {
                        var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后', '提示');
                        addYhjsWin.hide();
                        Ext.Ajax.request({
                            method: 'POST',
                            url: 'jsglAction.do?method=addYhjs',
                            params: {
                                yhId: '${requestScope.yh.memid}',
                                jsId: Ext.getCmp('sel_jsid').getValue()
                            },
                            success: function(response){
                                var rs = Ext.decode(response.responseText);
                                Ext.getCmp('sel_jsid').setValue('');
                                if (rs.success) {
                                    waitMsg.hide();
                                    wyjsData.reload();
                                    jsData.reload();
                                }
                                else {
                                    Ext.Msg.alert("提示", rs.message);
                                }
                            },
                            failure: function(){
                                Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
                            }
                        });
                    }
                }
            }, {
                text: '取 消',
                width: 60,
                handler: function(){
                    addYhjsWin.hide();
                }
            }]
        });
        
        //职务添加窗口
        var addYhzwWin = new Ext.Window({
            title: '请选择需要添加的职务',
            width: 300,
            height: 120,
            closeAction: 'hide',
            modal: true,
            plain: true,
            items: new Ext.form.FormPanel({
                id: 'addYhzwForm',
                baseCls: 'x-plain',
                labelWidth: 40,
                bodyStyle: 'padding:10px',
                border: false,
                frame: true,
                items: new Ext.form.ComboBox({
                    fieldLabel: '职 务',
                    xtype: 'combo',
                    id: 'sel_zwid',
                    baseCls: 'x-plain',
                    triggerAction: 'all',
                    width: 200,
                    allowBlank: false,
                    lazyRender: true,
                    readOnly: true,
                    emptyText: '请选择职务',
                    store: wyzwData,
                    displayField: 'name',
                    valueField: 'rolid'
                })
            }),
            buttons: [{
                text: '确 定',
                width: 60,
                handler: function(){
                    var yjf = Ext.getCmp('addYhzwForm');
                    if (yjf.form.isValid()) {
                        var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后', '提示');
                        addYhzwWin.hide();
                        Ext.Ajax.request({
                            method: 'POST',
                            url: 'zwglAction.do?method=addYhzw',
                            params: {
                                yhId: '${requestScope.yh.memid}',
                                zwId: Ext.getCmp('sel_zwid').getValue()
                            },
                            success: function(response){
                                var rs = Ext.decode(response.responseText);
                                Ext.getCmp('sel_zwid').setValue('');
                                if (rs.success) {
                                    waitMsg.hide();
                                    wyzwData.reload();
                                    zwData.reload();
                                }
                                else {
                                    Ext.Msg.alert("提示", rs.message);
                                }
                            },
                            failure: function(){
                                Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
                            }
                        });
                    }
                }
            }, {
                text: '取 消',
                width: 60,
                handler: function(){
                    addYhzwWin.hide();
                }
            }]
        });
        
        var jsSM = new Ext.grid.CheckboxSelectionModel({
            singleSelect: true,//单选
            listeners: {
                selectionchange: function(checkboxModel){
                },
                rowselect: function(checkboxModel, ind, r){
                }
            }
        });
        var zwSM = new Ext.grid.CheckboxSelectionModel({
            singleSelect: true,//单选
            listeners: {
                selectionchange: function(checkboxModel){
                },
                rowselect: function(checkboxModel, ind, r){
                }
            }
        });
        var mkSM = new Ext.grid.CheckboxSelectionModel({
            singleSelect: true,//单选
            listeners: {
                selectionchange: function(checkboxModel){
                },
                rowselect: function(checkboxModel, ind, r){
                }
            }
        });
        
        var mainView = new Ext.Viewport({
            layout: 'border',
            enableTabScroll: true,
            title: '相关操作',
            collapsible: true,
            items: [{
                region: 'east',
                width: 200,
                margins: '4 4 4 0',
                bodyStyle: 'padding:10px',
                collapsible: true,
                xtype: 'form',
                items: new Ext.form.FieldSet({
                    title: '相关链接',
                    collapsible: true,
                    html: '<a href="#">相关链接1</a><br/><br/><a href="#">相关链接2</a><br/><br/><a href="#">相关链接3</a><br/><br/>'
                })
            }, {
                region: 'center',
                margins: '4 4 4 4',
                xtype: 'panel',
                autoScroll: true,
                bodyStyle: 'padding:10px,text-align:center',
                tbar: [{
                    text: '[ 返回 ]',
                    handler: function(){
                        history.back();
                    }
                }],
                items: [{
                    xtype: 'form',
                    layout: 'form',
                    autoScroll: true,
                    border: false,
                    items: [{
                        title: '用户基本信息',
                        xtype: 'fieldset',
                        collapsible: true,
                        width: 550,
                        labelWidth: 55,
                        defaults: {
                            xtype: 'textfield',
                            width: 300,
                            readOnly: true
                        },
                        items: [{
                            fieldLabel: '姓名',
                            value: '${requestScope.yh.username}'
                        }, {
                            fieldLabel: '联系电话',
                            value: '${requestScope.yh.mobilephone}'
                        }, {
                            fieldLabel: '电子邮箱',
                            value: '${requestScope.yh.email}'
                        }, {
                            fieldLabel: '登录次数',
                            value: '${requestScope.yh.dlcs}'
                        }]
                    }, {
                    
                        xtype: 'fieldset',
                        title: '用户职务信息',
                        collapsible: true,
                        width: 550,
                        height: 240,
                        items: new Ext.grid.GridPanel({
                            stripeRows: true,
                            width: 520,
                            height: 200,
                            autoScroll: true,
                            trackMouseOver: true,
                            loadMask: true,
                            tbar: [{
                                icon: '${pageContext.request.contextPath}/imgs/add.png',
                                xtype: "button",
                                tooltip: '添加职务',
                                handler: function(){
                                    addYhzwWin.show();
                                }
                            }, {
                                icon: '${pageContext.request.contextPath}/imgs/delete.png',
                                xtype: "button",
                                tooltip: '删除职务',
                                handler: function(){
                                    var r = zwSM.getSelected();
                                    if (r == null) {
                                        return;
                                    }
                                    var id = r.get('zw.rolid');
                                    Ext.MessageBox.confirm('删除提示', '是否确认删除该用户对应职务：' + r.get('zw.zwmc'), function(btn){
                                        if (btn == 'yes') {
                                            var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后', '提示');
                                            Ext.Ajax.request({
                                                method: 'POST',
                                                url: 'zwglAction.do?method=delYhzw',
                                                params: {
                                                    yhId: '${requestScope.yh.memid}',
                                                    zwId: id
                                                },
                                                success: function(response){
                                                    var rs = Ext.decode(response.responseText);
                                                    if (rs.success) {
                                                        waitMsg.hide();
                                                        wyzwData.reload();
                                                        zwData.reload();
                                                    }
                                                    else {
                                                        Ext.Msg.alert("提示", rs.message);
                                                    }
                                                },
                                                failure: function(){
                                                    Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
                                                }
                                            });
                                        }
                                    });
                                }
                            }, {
                                icon: contextPath + '/imgs/refresh.gif',
                                xtype: "button",
                                tooltip: '刷新',
                                handler: function(){
                                    zwData.load();
                                }
                            }],
                            sm: zwSM,
                            //列模型
                            cm: new Ext.grid.ColumnModel([zwSM, {
                                header: '职务名称',
                                sortable: true,
                                dataIndex: 'role.name'
                            }, {
								header:'所属部门',
								width: 120,
								dataIndex:'role.depart.name'
							},{
                                header: '职务描述',
                                sortable: true,
                                dataIndex: 'role.description'
                            }, {
                                header: '创建时间',
                                sortable: true,
                                dataIndex: 'role.createTime'
                            }]),
                            //数据
                            store: zwData
                        })
                    }, {
                        xtype: 'fieldset',
                        title: '用户职务下角色信息',
                        collapsible: true,
                        width: 550,
                        height: 240,
                        items: new Ext.grid.GridPanel({
                            stripeRows: true,
                            width: 520,
                            height: 200,
                            autoScroll: true,
                            trackMouseOver: true,
                            loadMask: true,
                            tbar: [
							/*{
                                icon: '${pageContext.request.contextPath}/imgs/add.png',
                                xtype: "button",
                                tooltip: '添加角色',
                                closeAction: 'hide',
                                handler: function(){
                                    addYhjsWin.show();
                                }
                            }, {
                                icon: '${pageContext.request.contextPath}/imgs/delete.png',
                                xtype: "button",
                                tooltip: '删除角色',
                                handler: function(){
                                    var r = jsSM.getSelected();
                                    if (r == null) {
                                        return;
                                    }
                                    var id = r.get('js.partId');
                                    Ext.MessageBox.confirm('删除提示', '是否确认删除该用户对应角色：' + r.get('js.jsmc'), function(btn){
                                        if (btn == 'yes') {
                                            var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后', '提示');
                                            Ext.Ajax.request({
                                                method: 'POST',
                                                url: 'jsglAction.do?method=delYhjs',
                                                params: {
                                                    yhId: '${requestScope.yh.memid}',
                                                    jsId: id
                                                },
                                                success: function(response){
                                                    var rs = Ext.decode(response.responseText);
                                                    if (rs.success) {
                                                        waitMsg.hide();
                                                        wyjsData.reload();
                                                        jsData.reload();
                                                    }
                                                    else {
                                                        Ext.Msg.alert("提示", rs.message);
                                                    }
                                                },
                                                failure: function(){
                                                    Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
                                                }
                                            });
                                        }
                                    });
                                    
                                }
                            },*/ {
                                icon: '${pageContext.request.contextPath}/imgs/refresh.gif',
                                xtype: "button",
                                tooltip: '刷新',
                                handler: function(){
                                    jsData.load();
                                }
                            }],
                            sm: jsSM,
                            //列模型
                            cm: new Ext.grid.ColumnModel([jsSM, {
                                header: '角色名称',
                                sortable: true,
                                dataIndex: 'part.name'
                            }, {
                                header: '角色描述',
                                width: 180,
                                sortable: true,
                                dataIndex: 'part.description'
                            }, {
                                header: '创建时间',
                                sortable: true,
                                dataIndex: 'part.createTime'
                            }]),
                            //数据
                            store: jsData
                        })
                    }, {
                        xtype: 'fieldset',
                        title: '控制模块权限',
                        collapsible: true,
                        width: 550,
                        height: 240,
                        items: [new Ext.grid.EditorGridPanel({
                            stripeRows: true,
                            width: 520,
                            height: 200,
                            autoScroll: true,
                            trackMouseOver: true,
                            loadMask: true,
                            tbar: [{
                                icon: '${pageContext.request.contextPath}/imgs/save.gif',
                                xtype: "button",
                                tooltip: '保存',
                                handler: function(){
                                
                                }
                            }, {
                                icon: '${pageContext.request.contextPath}/imgs/delete.png',
                                xtype: "button",
                                tooltip: '删除用户所有直接指定模块',
                                handler: function(){
                                    var r = mkSM.getSelected();
                                    if (r == null) {
                                        return;
                                    }
                                    var id = r.get('moduleId');
                                    Ext.MessageBox.confirm('删除提示', '是否确认删除该用户对应权限：' + r.get('name'), function(btn){
                                        if (btn == 'yes') {
                                            var waitMsg = Ext.MessageBox.wait('数据更新中，请稍后', '提示');
                                            Ext.Ajax.request({
                                                method: 'POST',
                                                url: contextPath + '/system/yhglAction.do?method=delYhqx',
                                                params: {
                                                    id: '${requestScope.yh.memid}',
                                                    gnmkId: id
                                                },
                                                success: function(response){
                                                    var rs = Ext.decode(response.responseText);
                                                    if (rs.success) {
                                                        waitMsg.hide();
                                                        gnmkData.reload();
                                                        Ext.getCmp('wyTree').getRootNode().reload();
                                                        Ext.getCmp('yyTree').getRootNode().reload();
                                                    }
                                                    else {
                                                        Ext.Msg.alert("提示", rs.message);
                                                    }
                                                },
                                                failure: function(){
                                                    Ext.Msg.alert("提示", "服务器连接失败,请稍后重试!");
                                                }
                                            });
                                        }
                                    });
                                }
                            }, {
                                icon: '${pageContext.request.contextPath}/imgs/refresh.gif',
                                xtype: "button",
                                tooltip: '刷新',
                                handler: function(){
                                    gnmkData.load();
                                }
                            }],
                            sm: mkSM,
                            //列模型
                            cm: new Ext.grid.ColumnModel([{
                                header: '模块名称',
                                sortable: true,
                                dataIndex: 'name'
                            }, {
                                header: '模块描述',
                                width: 180,
                                sortable: true,
                                dataIndex: 'description'
                            }, {
                                header: '权限',
                                sortable: true,
                                dataIndex: 'rightType',
                                editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
                                    allowBlank: false,
                                    transform: 'sel_qxlx',
                                    triggerAction: 'all',
                                    lazyRender: true,
                                    readOnly: true
                                })),
                                renderer: function(value){
                                    if (value == '2') {
                                        return "只读";
                                    }
                                    else 
                                        if (value == '10') {
                                            return '只编辑';
                                        }
                                        else {
                                            return '全部';
                                        }
                                }
                            }]),
                            //数据
                            store: gnmkData
                        })]
                    }, {
                        xtype: 'fieldset',
                        title: '指定模块权限',
                        collapsible: true,
                        width: 550,
                        layout: 'column',
                        defaults: {
                            height: 380
                        },
                        items: [{
                            columnWidth: .5,
                            id: 'wyTree',
                            autoScroll: true,
                            title: '待添加权限',
                            xtype: 'treepanel',
                            tbar: [{
                                icon: '${pageContext.request.contextPath}/imgs/refresh.gif',
                                xtype: "button",
                                tooltip: '刷新',
                                handler: function(){
                                    Ext.getCmp('wyTree').getRootNode().reload();
                                }
                            }, {
                                xtype: "button",
                                tooltip: '展开节点',
                                icon: '${pageContext.request.contextPath}/imgs/expand-all.gif',
                                handler: function(){
                                    this.ownerCt.ownerCt.expandAll();
                                }
                            }, {
                                xtype: "button",
                                tooltip: '关闭节点',
                                icon: '${pageContext.request.contextPath}/imgs/collapse-all.gif',
                                handler: function(){
                                    this.ownerCt.ownerCt.collapseAll();
                                }
                            }],
                            root: new Ext.tree.AsyncTreeNode({
                                id: 'root',
                                text: '功能菜单'
                            }),
                            loader: new Ext.tree.TreeLoader({
                                url: "yhglAction.do?method=getYhwyGnmk&id=${requestScope.yh.memid}",
                                applyLoader: false
                            })
                        }, {
                            width: 80,
                            border: false,
                            bodyStyle: 'text-align:center;padding-top:150px',
                            items: [{
                                xtype: 'button',
                                width: 50,
                                id: 'addGnmkBtn',
                                disabled: true,
                                text: ' => ',
                                handler: function(){
                                    var checkNodes = Ext.getCmp('wyTree').getChecked();
                                    sel_wyarry.length = 0;
                                    Ext.each(checkNodes, function(node){
                                        if (node.isLeaf()) {
                                            sel_wyarry.push(node.id);
                                        }
                                    });
                                    sel_wyid = sel_wyarry.join(';');
									if (sel_wyid == 0) {
                                        return;
                                    }
                                    var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....', '提示');
                                    Ext.Ajax.request({
                                        method: 'POST',
                                        url: contextPath + '/system/yhglAction.do?method=addYhqx',
                                        success: function(response){
                                            var rs = Ext.decode(response.responseText);
                                            if (rs.success) {
                                                sel_wyid = 0;
                                                Ext.getCmp('wyTree').getRootNode().reload();
                                                Ext.getCmp('yyTree').getRootNode().reload();
                                                gnmkData.reload();
                                                waitMsg.hide();
                                            }
                                            else {
                                                Ext.Msg.alert("提示", rs.message);
                                            }
                                        },
                                        failure: function(){
                                            Ext.Msg.alert("提示", "更新失败,服务器无法连接!");
                                        },
                                        params: {
                                            id: '${requestScope.yh.memid}',
                                            gnmkId: sel_wyid
                                        }
                                    });
                                }
                            }]
                        }, {
                            columnWidth: .5,
                            id: 'yyTree',
                            autoScroll: true,
                            title: '已有权限',
                            xtype: 'treepanel',
                            tbar: [{
                                xtype: "button",
                                tooltip: '删除节点',
                                id: 'delGnmk',
                                disabled: true,
                                icon: '${pageContext.request.contextPath}/imgs/delete.png',
                                handler: function(){
                                    var checkNodes = Ext.getCmp('yyTree').getChecked();
                                    sel_yyarry.length = 0;
                                    Ext.each(checkNodes, function(node){
                                        if (node.isLeaf()) {
                                            sel_yyarry.push(node.id);
                                        }
                                    });
                                    sel_yyid = sel_yyarry.join(';');
                                    if (sel_yyid == 0) {
                                        return;
                                    }
                                    var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....', '提示');
                                    Ext.Ajax.request({
                                        method: 'POST',
                                        url: contextPath + '/system/yhglAction.do?method=delYhqx',
                                        success: function(response){
                                            var rs = Ext.decode(response.responseText);
                                            if (rs.success) {
                                                sel_yyid = 0;
                                                Ext.getCmp('wyTree').getRootNode().reload();
                                                Ext.getCmp('yyTree').getRootNode().reload();
                                                gnmkData.reload();
                                                waitMsg.hide();
                                            }
                                            else {
                                                Ext.Msg.alert("提示", rs.message);
                                            }
                                        },
                                        failure: function(){
                                            Ext.Msg.alert("提示", "更新失败,服务器无法连接!");
                                        },
                                        params: {
                                            id: '${requestScope.yh.memid}',
                                            gnmkId: sel_yyid
                                        }
                                    });
                                }
                            }, '-', {
                                icon: '${pageContext.request.contextPath}/imgs/refresh.gif',
                                xtype: "button",
                                tooltip: '刷新',
                                handler: function(){
                                    Ext.getCmp('yyTree').getRootNode().reload();
                                }
                            }, {
                                xtype: "button",
                                tooltip: '展开节点',
                                icon: '${pageContext.request.contextPath}/imgs/expand-all.gif',
                                handler: function(){
                                    this.ownerCt.ownerCt.expandAll();
                                }
                            }, {
                                xtype: "button",
                                tooltip: '关闭节点',
                                icon: '${pageContext.request.contextPath}/imgs/collapse-all.gif',
                                handler: function(){
                                    this.ownerCt.ownerCt.collapseAll();
                                }
                            }],
                            root: new Ext.tree.AsyncTreeNode({
                                id: 'root',
                                text: '功能菜单'
                            }),
                            loader: new Ext.tree.TreeLoader({
                                url: "yhglAction.do?method=getYhyyGnmk&id=${requestScope.yh.memid}",
                                applyLoader: false
                            })
                        }]
                    }]
                }]
            }]
        });
        
        Ext.getCmp("yyTree").on('checkchange', function(node, checked){
            if (checked) {
                Ext.getCmp('delGnmk').enable();
            }
            else {
                Ext.getCmp('delGnmk').disable();
            }
            node.expand();
            node.attributes.checked = checked;
            node.eachChild(function(child){
                child.ui.toggleCheck(checked);
                child.attributes.checked = checked;
                child.fireEvent('checkchange', child, checked);
            });
        }, Ext.getCmp('yyTree'));
        
        Ext.getCmp("wyTree").on('checkchange', function(node, checked){
            if (checked) {
                Ext.getCmp('addGnmkBtn').enable();
                sel_wyid = node.id;
            }
            else 
                if (!checked && Ext.getCmp("wyTree").getChecked().length == 0) {
                    sel_wyid = 0;
                    Ext.getCmp('addGnmkBtn').disable();
                }
            node.expand();
            node.attributes.checked = checked;
            node.eachChild(function(child){
                child.ui.toggleCheck(checked);
                child.attributes.checked = checked;
                child.fireEvent('checkchange', child, checked);
            });
        }, Ext.getCmp('wyTree'));
        
        Ext.getCmp("yyTree").on('click', function(node, event){
            if (node.leaf == true) {
                node.getUI().getAnchor().href = "javascript:void(0);";
                sel_yyid = node.id;
                Ext.getCmp('delGnmk').enable();
            }
            else {
                sel_yyid = 0;
                Ext.getCmp('delGnmk').disable();
            }
        });
        Ext.getCmp("wyTree").on('click', function(node, event){
            if (node.leaf == true) {
                node.getUI().getAnchor().href = "javascript:void(0);";
                
                Ext.getCmp('addGnmkBtn').enable();
                sel_wyid = node.id;
            }
            else {
                sel_wyid = 0;
                Ext.getCmp('addGnmkBtn').disable();
            }
        });
        
        jsData.load();
        zwData.load();
        gnmkData.load();
    });
</script>
</head>
<body>
    <input type="hidden" id='qx' value='{qxlx}'>
    <select id="sel_qxlx" style="display:none">
        <option value="2">只读</option>
        <option value="10">只编辑</option>
        <option value="210">全部</option>
    </select>
    <table width="100%" height='100%'>
        <tr>
            <td align="center" valign="middle">
                <div id='loadDiv' style="font-size:12px;">
                    <img width='50' height='50' src='${pageContext.request.contextPath}/imgs/login-load.gif'>数据加载中，请稍后 . . .
                </div>
            </td>
        </tr>
    </table>
</body>
</html>