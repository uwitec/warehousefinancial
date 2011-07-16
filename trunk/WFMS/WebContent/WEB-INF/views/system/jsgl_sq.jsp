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
        var sel_wyid = 0;
        var sel_yyid = 0;
		var sel_yyarry = new Array();
		var sel_wyarry = new Array();
		
        var dataStore = new Ext.data.JsonStore({
            autoDestroy: true,
            pruneModifiedRecords: true,
            url: 'jsglAction.do?method=getAllJsqx&id=${js.partId}',
            root: 'jsqxList',
            fields: [{
                name: 'id'
            }, {
                name: 'right.rightId'
            }, {
                name: 'right.name'
            }, {
                name: 'right.description'
            }, {
                name: 'rightType'
            }, {
                name: 'rightStatus'
            }]
        });
        
        var checkboxModel = new Ext.grid.CheckboxSelectionModel({
            singleSelect: true,
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
            items: [{
                region: 'east',
                width: 200,
                margins: '4 4 4 0',
                bodyStyle: 'padding:10px',
                xtype: 'form',
                collapsible: true,
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
                        title: '角色信息',
                        xtype: 'fieldset',
                        collapsible: true,
                        width: 550,
                        labelWidth: 55,
                        items: [{
                            width: 200,
                            xtype: 'textfield',
                            readOnly: true,
                            fieldLabel: '角色名称',
                            value: '${js.name}'
                        }, {
                            width: 380,
                            height: 80,
                            readOnly: true,
                            xtype: 'textarea',
                            fieldLabel: '角色描述',
                            value: '${js.description}'
                        }]
                    }, {
                        xtype: 'fieldset',
                        title: '修改权限',
                        collapsible: true,
                        width: 550,
                        height: 360,
                        items: new Ext.grid.EditorGridPanel({
                            stripeRows: true,
                            width: 520,
                            height: 320,
                            autoScroll: true,
                            trackMouseOver: true,
                            loadMask: true,
                            tbar: [{
                                icon: '${pageContext.request.contextPath}/imgs/save.gif',
                                xtype: "button",
                                tooltip: '保存',
                                handler: function(){
                                    //修改角色权限
                                    var m = dataStore.modified.slice(0);
                                    var jsonArray = [];
                                    Ext.each(m, function(item){
                                        jsonArray.push(item.data);
                                    });
                                    if (jsonArray.length == 0) {
                                        return;
                                    }
                                    var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....', '提示');
                                    var jsonData = encodeURIComponent(Ext.encode(jsonArray));
                                    Ext.Ajax.request({
                                        method: 'POST',
                                        url: 'jsglAction.do?method=updJsqx&id=${js.partId}',
                                        success: function(response){
                                            var rs = Ext.decode(response.responseText);
                                            if (rs.success) {
                                                waitMsg.hide();
                                                dataStore.load();
                                            }
                                            else {
                                                Ext.Msg.alert("提示", rs.message);
                                            }
                                        },
                                        failure: function(){
                                            Ext.Msg.alert("提示", "更新失败,服务器无法连接!");
                                        },
                                        params: {
                                            data: jsonData
                                        }
                                    });
                                }
                            }, {
                                icon: '${pageContext.request.contextPath}/imgs/refresh.gif',
                                xtype: "button",
                                tooltip: '刷新',
                                handler: function(){
                                    dataStore.load();
                                }
                            }],
                            sm: checkboxModel,
                            //列模型
                            cm: new Ext.grid.ColumnModel([{
                                header: '模块名称',
                                sortable: true,
                                dataIndex: 'right.name'
                            }, {
                                header: '模块描述',
                                width: 180,
                                sortable: true,
                                dataIndex: 'right.description'
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
                            }, {
                                header: '状态',
                                dataIndex: 'rightStatus',
                                sortable: true,
                                editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
                                    allowBlank: false,
                                    transform: 'sel_qxzt',
                                    triggerAction: 'all',
                                    lazyRender: true,
                                    readOnly: true
                                })),
                                renderer: function(value){
                                    if (value == "1") {
                                        return "正常";
                                    }
                                    else {
                                        return "<font style='color:red'>禁用</font>";
                                    }
                                }
                            }]),
                            //数据
                            store: dataStore
                        })
                    }, {
                        xtype: 'fieldset',
                        title: '添加权限',
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
                            enableDD: true,
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
                                url: "gnmkAction.do?method=getJswyGnmk&id=${js.partId}",
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
                                    sel_wyarry.length=0;
									Ext.each(checkNodes, function(node){
                                        if(node.isLeaf() && node.text!=sel_wyid)
										{
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
                                        url: 'jsglAction.do?method=addJxqx',
                                        success: function(response){
                                            var rs = Ext.decode(response.responseText);
                                            if (rs.success) {
                                                sel_wyid = 0;
                                                Ext.getCmp('wyTree').getRootNode().reload();
                                                Ext.getCmp('yyTree').getRootNode().reload();
                                                dataStore.reload();
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
                                            id: '${js.partId}',
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
                            enableDD: true,
                            tbar: [{
                                xtype: "button",
                                tooltip: '删除节点',
                                id: 'delGnmk',
                                disabled: true,
                                icon: '${pageContext.request.contextPath}/imgs/delete.png',
                                handler: function(){
									var checkNodes = Ext.getCmp('yyTree').getChecked();
                                    sel_yyarry.length=0;
									Ext.each(checkNodes, function(node){
                                        if(node.isLeaf())
										{
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
                                        url: 'jsglAction.do?method=delJxqx',
                                        success: function(response){
                                            var rs = Ext.decode(response.responseText);
                                            if (rs.success) {
                                                sel_yyid = 0;
                                                Ext.getCmp('wyTree').getRootNode().reload();
                                                Ext.getCmp('yyTree').getRootNode().reload();
                                                dataStore.reload();
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
                                            id: '${js.partId}',
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
                                url: "gnmkAction.do?method=getJsyyGnmk&id=${js.partId}",
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
            else if(!checked &&  Ext.getCmp("wyTree").getChecked().length==0){
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
            event.stopEvent();
            node.attributes.checked = !node.attributes.checked;
            if (node.leaf == true && sel_yyid == 0) {
                node.getUI().getAnchor().href = "javascript:void(0)";
                sel_yyid = node.id;
                Ext.getCmp('delGnmk').enable();
            }
            else {
                sel_yyid = 0;
                Ext.getCmp('delGnmk').disable();
            }
        });
        
        Ext.getCmp("wyTree").on('click', function(node, event){
            event.stopEvent();
            node.attributes.checked = !node.attributes.checked;
            if (node.leaf == true && sel_wyid == 0) {
                node.getUI().getAnchor().href = "javascript:void(0)";
                Ext.getCmp('addGnmkBtn').enable();
                sel_wyid = node.id;
            }
            else {
                sel_wyid = 0;
                Ext.getCmp('addGnmkBtn').disable();
            }
        });
        dataStore.load();
    });
</script>
</head>
<body>
    <input type="hidden" id='qx' value='{qxlx}'>
    <select id="sel_qxzt" style="display:none">
        <option value="1">正常</option>
        <option value="0">禁用</option>
    </select>
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