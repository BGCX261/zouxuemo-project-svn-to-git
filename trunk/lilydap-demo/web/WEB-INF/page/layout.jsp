<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"
	import="com.lily.dap.entity.organize.Person,
			org.springframework.security.core.userdetails.UserDetails, 
			com.lily.dap.webapp.security.RightUtils"
	pageEncoding="utf-8"	
%>
<head>
	<title>LilyDAP3平台演示系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ext-3.3.1/resources/css/ext-all.css'/>" />
	
	<script type="text/javascript" src="<c:url value='/scripts/jquery-1.5.2/jquery.min.js'/>" /></script>

	<script type="text/javascript" src="<c:url value='/scripts/ext-3.3.1/adapter/jquery/ext-jquery-adapter.js'/>" /></script>
	<script type="text/javascript" src="<c:url value='/scripts/ext-3.3.1/ext-all.js'/>" /></script>
	<script type="text/javascript" src="<c:url value='/scripts/ext-3.3.1/locale/ext-lang-zh_CN.js'/>" /></script>
	
	<script type="text/javascript" src="<c:url value='/scripts/AccordionMenuPanel.js'/>" /></script>
	
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = "<c:url value='/scripts/ext-3.3.1/resources/images/default/s.gif'/>";
	</script>
</head>
<body>
<div id="top">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="1024px" height="60px" background="images/top.jpg">&nbsp;</td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="60px" align="right" background="images/top_right.jpg">
		<table border="0" cellspacing="0" cellpadding="0" style="margin-top:-4px;">
          <tr>
            <td>
			 <p style="padding-top:4px;padding-right:10px">
		    	<a style="FONT-SIZE: 10pt;COLOR:#ffffff;FONT-FAMILY:宋体;text-decoration: none;background:url(images/silk/icons/house_go.gif) no-repeat left top; padding:4px 0 0 18px;" href="javascript:home()">返回首页</a>&nbsp;&nbsp;
				<a id="a_modifypwd" style="FONT-SIZE: 10pt;COLOR:#ffffff;FONT-FAMILY:宋体;text-decoration: none;background:url(images/silk/icons/key.gif) no-repeat left top; padding:3px 0 0 17px;" href="#">更改密码</a>&nbsp;&nbsp;
		        <a style="FONT-SIZE: 10pt;COLOR:#ffffff;FONT-FAMILY:宋体; text-decoration: none;background:url(images/silk/icons/door_out.gif) no-repeat left top; padding:3px 0 0 18px;" href="<c:url value="j_spring_security_logout"/>">注销用户</a>
		    </p>
		    <p class="x-btn x-btn-text" style="padding-top:2px;padding-right:20px;">
		<%com.lily.dap.entity.organize.Person currPerson = com.lily.dap.webapp.security.RightUtils.getCurrPerson();%>
		<marquee behavior="scroll" direction="left" width="250px" height="20px" loop="-1" scrollamount="1" scrolldelay="1" style="font:12px;" >
		<span id="user-span" style="color:blue"><%=currPerson.getName()%></span>&nbsp;&nbsp;<span id="welcome"></span>
		</marquee>
		    </p>
			</td>
          </tr>
        </table></td>
      </tr>    
    </table></td>
  </tr>
</table>
</div>  
</body>
<script>
	var homePage = 'about:blank';
	
    Ext.onReady(function(){
       var menuPanel = new AccordionMenuPanel({
       		basePath: '<%=request.getContextPath()%>',
       		accordionUrl: '/common/menu!menu.action',
       		menuUrl: '/common/menu!menu.action',
       		frameId: 'center-iframe',
       		region:'center'
       });
       
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[{
                    region:'north',
                    contentEl: 'top',
					border : false,
                    height:60
                },{
					layout:'border',
                    region:'west',
                    title:'主菜单',
					width:200,
                    split:true,
                    minSize: 175,
                    maxSize: 400,
                    collapsible: true,
                    margins:'0 0 0 5',
					items:[menuPanel]
                },{
					region:'center',
					layout:'fit',
					border : false,
					//-------------注意这里增加了一个在里边的iframe，这里很重要
					html: '<iframe id="center-iframe" frameborder="0" width=100% height=100% scrolling="auto" style="border:0px none;" src="' + homePage + '"></iframe>' 
					//-----------
                }]
			})
        });
//--------------------------- ChangePWD Form Layout -----------------------------------	
<%@ include file="changepwd.jsp"%>

function home() {
	Ext.get('center-iframe').dom.src = homePage;
}
	
Ext.get("a_modifypwd").on('click', onModifyPwd);
//dynamicClock();

function onModifyPwd() {
	pwdWin.show(this);
}

welcome()

function welcome(){
	var now = new Date()
	var hour = now.getHours() 
	var welcomeString

	if(hour < 6){welcomeString = "凌晨好！"} 
	else if (hour < 9){welcomeString = "早上好！"} 
	else if (hour < 12){welcomeString = "上午好！"} 
	else if (hour < 14){welcomeString = "中午好！"} 
	else if (hour < 17){welcomeString = "下午好！"} 
	else if (hour < 19){welcomeString = "傍晚好！"} 
	else if (hour < 22){welcomeString = "晚上好！"} 
	else {welcomeString = "夜间好！"} 

	Ext.fly('welcome').update(welcomeString);
}

</script>
</html>