<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<title></title>
<!-- Extjs导入 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css">
</link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js">
</script>
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
        var dispRole = false;
        var organizeUrl = contextPath + '/common/organizeAction.do?method=getAllOrganize&dispRole=';
        var selectedTemp = {};
        var selectedStore = new Ext.data.JsonStore();
        selectedStore.on('add', function(store, r, index){
            store.each(function(record){
                selectedTemp[record.get('memid')] = record.get('username');
            });
            
        });
        selectedStore.on('remove', function(store, r, index){
            if (selectedStore.getCount() == 0) {
                selectedTemp = {};
                return;
            }
            selectedTemp = {};
            store.each(function(record){
                selectedTemp[record.get('memid')] = record.get('username');
            });
        });
        var memberStore = new Ext.data.JsonStore({//数据存储
            autoDestroy: true,//自动数据填入Store
            pruneModifiedRecords: true,//更新数据时,自动去掉编辑标记
            root: 'dataList',//读取数据的时候所读取的属性节点
            totalProperty: 'totalCount',//读取属性节点的记录数
            url: contextPath + '/common/organizeAction.do?method=getDepartMembers',
            fields: ['memid', "username", "gender","role.depart.name"]//实体属性名称
        });
        var checkboxModel1 = new Ext.grid.CheckboxSelectionModel();
        
        var memberGrid = new Ext.grid.EditorGridPanel({
            stripeRows: true,//隔行变色
            anchor: '100%',
            height: 455,
            autoScroll: true,//自动滚动条
            trackMouseOver: true,//高亮显示
            border: true,//去掉面板边框
            loadMask: true,//读取数据的时候提示更新
            store: memberStore,
            sm: checkboxModel1,//选择模型
            viewConfig: {
                forceFit: true
            },
            cm: new Ext.grid.ColumnModel([checkboxModel1, new Ext.grid.RowNumberer(), {
                header: '用户列表',
                dataIndex: 'username',
                width: 300,
                renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
                    if (record.get('gender') == null || record.get('gender') == 'undefined') {
                        return ' ' + record.get('username');
                    }
                    else 
                        if (record.get('gender') == 'true') {
                            return '<img style="border:none;valign:middle;text-valign:middle" src="' + contextPath + '/imgs/boy.gif"/>' + '<span style="text-valign:top;valign:top;">' + value + '</span>';
                        }
                        else 
                            if (record.get('gender') == 'false') {
                                return '<img style="border:none;" src="' + contextPath + '/imgs/girl.gif"/>' + value;
                            }
                }
            }])
        });
        memberGrid.on('render', function(grid){
            var store = grid.getStore(); // Capture the Store.    
            var view = grid.getView(); // Capture the GridView.    
            memberGrid.tip = new Ext.ToolTip({
                target: view.mainBody, // The overall target element.    
                delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.    
                trackMouse: true, // Moving within the row should not hide the tip.    
                renderTo: document.body, // Render immediately so that tip.body can be referenced prior to the first show.    
                listeners: { // Change content dynamically depending on which element triggered the show.    
                    beforeshow: function updateTipBody(tip){
                        var rowIndex = view.findRowIndex(tip.triggerElement);
                        //tip.body.dom.innerHTML = "Over Record ID " + store.getAt(rowIndex).id;
                    	tip.body.dom.innerHTML = store.getAt(rowIndex).get('role.depart.name');
					}
                }
            });
        });
		
        var selectedGrid = new Ext.grid.EditorGridPanel({
            stripeRows: true,//隔行变色
            autoScroll: true,//自动滚动条
            height: 480,
            trackMouseOver: true,//高亮显示
            border: false,//去掉面板边框
            loadMask: true,//读取数据的时候提示更新
            sm: checkboxModel,//选择模型
            store: selectedStore,
            checkOnly: true,
            id: 'btable',
            //列模型
            cm: new Ext.grid.ColumnModel([new Ext.grid.CheckboxSelectionModel(), new Ext.grid.RowNumberer(), {
                header: '已选用户',
                dataIndex: 'username',
                width: 300
            }])
        });
		selectedGrid.on('render', function(grid){
            var store = grid.getStore(); // Capture the Store.    
            var view = grid.getView(); // Capture the GridView.    
            selectedGrid.tip = new Ext.ToolTip({
                target: view.mainBody, // The overall target element.    
                delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.    
                trackMouse: true, // Moving within the row should not hide the tip.    
                renderTo: document.body, // Render immediately so that tip.body can be referenced prior to the first show.    
                listeners: { // Change content dynamically depending on which element triggered the show.    
                    beforeshow: function updateTipBody(tip){
                        var rowIndex = view.findRowIndex(tip.triggerElement);
                        //tip.body.dom.innerHTML = "Over Record ID " + store.getAt(rowIndex).id;
                    	tip.body.dom.innerHTML = store.getAt(rowIndex).get('role.depart.name');
					}
                }
            });
        });
        var viewport = new Ext.Viewport({
            layout: 'border',
            items: [{
                region: 'north',
				frame:true,
                height: 60,
                layout: 'column',
                bodyStyle: 'text-valign:middle;padding-top:20px',
                items: [{
                    bodyStyle: 'margin-left:20px',
                    layout: 'form',
                    columnWidth: .25,
                    border: false,
                    labelWidth: 50,
                    items: {
                        anchor: '90%',
                        xtype: 'textfield',
                        fieldLabel: '用户名',
                        emptyText: '请输入用户名',
                        name: 'zsrs',
                        width: 180,
                        allowBlank: false
                    }
                }, {
                    columnWidth: .07,
                    xtype: 'button',
                    bodyStyle: 'padding-left:20px',
                    width: 50,
                    id: 'query',
                    text: ' 查  询',
                    handler: function(){
                    }
                }]
            }, {
                region: 'east',
                margins: '4 4 0 4',
                width: 300,
                layout: 'fit',
                items: [{
                    items: [selectedGrid]
                }]
            }, {
                region: 'west',
                layout: 'accordion',
                margins: '4 4 0 4',
                border: 0,
                width: 200,
                autoScroll: true,
                //sizerWidth:4,
                split: true,
                collapse: false,
                height: 480,
                layoutConfig: {
                    animate: true
                },
                items: [{
                    title: '按部门分类',
                    xtype: 'treepanel',
                    border: false,
                    id: 'departTree',
                    tbar: [],
                    root: new Ext.tree.AsyncTreeNode({
                        id: '0',
                        text: '仓储财务管理系统'
                    }),
                    loader: new Ext.tree.TreeLoader({
                        url: organizeUrl + 'false',
                        applyLoader: false
                    }),
                    height: 160
                }, {
                    title: '按角色分类',
                    xtype: 'treepanel',
                    autoScroll: true,
                    id: 'roleTree',
                    tbar: [],
                    root: new Ext.tree.AsyncTreeNode({
                        id: '0',
                        text: '仓储财务管理系统'
                    }),
                    loader: new Ext.tree.TreeLoader({
                        url: organizeUrl + 'true',
                        applyLoader: false
                    }),
                    height: 160
                }, {
                    title: '在线人员',
                    xtype: 'treepanel',
                    id: 'onlineTree',
                    tbar: [],
                    root: new Ext.tree.AsyncTreeNode({
                        id: '0',
                        text: '仓储财务管理系统'
                    }),
                    loader: new Ext.tree.TreeLoader({
                        url: organizeUrl + 'false',
                        applyLoader: false
                    }),
                    height: 160
                }]
            }, {
                margins: '4 0 0 0',
                region: 'center',
                layout: 'column',
                defaultType: 'panel',
                items: [{
                    xtype: 'panel',
                    columnWidth: .9,
                    items: [memberGrid],
                    bbar: new Ext.PagingToolbar({
                        pageSize: 15,
                        store: memberStore,
                        displayInfo: true,
                        beforePageText: '第',
                        afterPageText: '页，共 {0} 页',
                        displayMsg: '当前为第<font style="color:red">{0} - {1}</font> 条记录，共<font style="color:red">{2}</font> 条记录',
                        emptyMsg: "没有找到相关信息"
                    })
                }, {
                    frame: true,
                    border: false,
                    height: 500,
                    columnWidth: .1,
                    bodyStyle: 'text-align:center;padding-top:150px;border:none;',
                    items: [{
                        margins: '150 2 2 2',
                        xtype: 'button',
                        width: 40,
                        id: 'addMember',
                        text: ' => ',
                        handler: function(){
                            var list = memberGrid.getSelectionModel().getSelections();
                            Ext.each(list, function(r){
                                if (selectedTemp[r.get('memid')] == null) {
                                    selectedStore.add(r);
                                }
                            });
                        }
                    }, {
                        height: 3
                    }, {
                        xtype: 'button',
                        margins: '4 4 4 4',
                        paddings: '4 4 4 4',
                        bodyStyle: 'text-align:center;padding-top:15px',
                        width: 40,
                        id: 'removeMember',
                        text: ' <= ',
                        handler: function(){
                            var list = selectedGrid.getSelectionModel().getSelections();
                            Ext.each(list, function(r){
                                selectedStore.remove(r);
                            });
                        }
                    }]
                }]
                //数据
            }, {
                margins: '0 0 0 0',
                region: 'south',
				frame:true,
                layout: 'fit',
                border: false,
                bodyStyle: 'text-align:center',
                buttonAlign: 'center',
                buttons: [{
                    width: 50,
                    id: 'ok',
                    text: ' 确  定',
                    handler: function(){
                    }
                }, {
                    width: 50,
                    id: 'esc',
                    text: ' 取  消',
                    handler: function(){
                    }
                }]
            }]
        });
        Ext.getCmp("departTree").on('click', function(node, event){
            node.getUI().getAnchor().href = "javascript:void(0)";
            Ext.apply(memberStore.baseParams, {
                id: node.id
            });
            memberStore.reload();
        });
        Ext.getCmp("roleTree").on('click', function(node, event){
            node.getUI().getAnchor().href = "javascript:void(0)";
            Ext.apply(memberStore.baseParams, {
                id: node.id
            });
            memberStore.reload();
        });
    });
</script>
</head>
<body>
    <select style="display:none" id="sel_sfyxj">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
    <table width="100%" height='100%'>
        <tr>
            <td align="center" valign="middle">
                <div id='loadDiv' style="font-size:12px;">
                    <img width='50' height='50' src='${pageContext.request.contextPath}/imgs/login-load.gif'>数据初始化，请稍后 . . .
                </div>
            </td>
        </tr>
    </table>
</body>
</html>