<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
	//Ext.QuickTips.init();
	//Ext.BLANK_IMAGE_URL = '<c:url value="/scripts/ext/images/default/s.gif"/>';
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
				region: 'north',
		        margins:'0 0 5 0',
		        border: false,
				height: 40,
		        html: '<div style="background:#e2e8e7 url(<c:url value='/images/banner-bk.gif'/>) top right repeat-x; border:0px solid #44626a;"><img src="<c:url value="/images/banner-title.gif"/>"/></div>' 
			},{
                title:'<c:out value="${userSession.userName}"/>',
                region: 'west',
                width: 200,
                collapsible: true,
                margins:'0 0 5 0',
			    animCollapse:false,
			    animFloat:false,
                split:true,
                html: '<iframe frameborder="0" width="100%" height="100%" src="<c:url value="/common/main/menu.html"/>"/>'
            },{
		        region:'center',
		        collapsible: true,
		        margins:'0 0 5 0',
		        html: '<iframe name="centerF" frameborder="0" width="100%" height="100%" src=""/>'
			}
		]
    });
});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>