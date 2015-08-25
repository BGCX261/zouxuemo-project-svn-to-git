<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<title>LilyDAP3平台演示系统</title>
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.5.2/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<style type="text/css">
<!--

body {
	font: 100% Verdana, Arial, Helvetica, sans-serif;
	padding: 0;
	text-align: center; /* 在 IE 5* 浏览器中，这会将容器居中。文本随后将在 #container 选择器中设置为默认左对齐 */
	color: #000000;
	background:#1f6fec url( <c:url value='/images/bg.jpg'/>);
	background-repeat:repeat-x;
	width:100%;
	margin:0;
	padding:0;
		
}
A:link{
    color: #ffffff;text-decoration:none
}
A:visited{
    color: #ffffff;text-decoration:none
}
A:hover {
	color: #ffffff;text-decoration:underline 
}

.input {
height: 20px;border: solid 1px #666666;background-color: #f6f7f9;padding: 1px;margin: 0;font-size:9pt;color: #666666;width:140px;
}
.btn {
display:inline; float:center; width:62px; height:20px; margin:6px 6px 0 8px; font-size:9pt; color:#666666; cursor:pointer; border:none; background-color: #cccccc;
}
 
.unchanged   
{   
    border:0;  
} 

.STYLE1 {color: #FFFFFF}
#Layer1 {
	position:absolute;
	
	z-index:1;
	left: 4px;
	top: 9px;
}

.button {

 height: 32px;
 width: 120px;
 background-repeat: no-repeat;
 cursor: pointer;
 color: #900;
 font-weight: 700;
 border-top-width: 0px;
 border-right-width: 0px;
 border-bottom-width: 0px;
 border-left-width: 0px;

}
.login{
 background-image: url(<c:url value='/images/login.jpg'/>);
}
.reset{
 background-image: url(<c:url value='/images/reset.jpg'/>);
}
-->
</style>
</head>

<body class="oneColFixCtrHdr">
<div style="background: url(<c:url value='/images/login_bg.jpg'/>) no-repeat center top; text-align:left; width:1003px; height:768px;">
<form method="post" id="loginForm" action="<c:url value="/j_spring_security_check"/>" onsubmit="saveUsername(this);return validateForm(this)" >
    <table style=" margin-top:250px;   margin-left:160px;" > 
		<tr style="height:40px" ><td  colspan=3>&nbsp;</td>
    	</tr>
	
    	<input type="hidden" name=j_username id="j_username"/> 
		
                <tr  height="60"  valign="bottom">
                  <td  align="right"><font style="font-size:10pt;text-align:right;color:#000000">			  
					<LABEL class=column for=uin><label for="j_username"class="required">用户名:&nbsp;&nbsp;</label></font>
				  </td>
                  <td width="60%" align="left">
					<input type="text" name="username" size="18" id="username" style="width:180px;background: #FFFFFF; border: 1px solid #C0C0C0"tabindex="1">
				  </td>
                </tr>

		 <tr height="60">
                 <td  align="right"><font style="font-size:10pt;text-align:right;color:#000000">	
			<LABEL class=column for=pp><label for="j_password"class="required">密 &nbsp;码:&nbsp;&nbsp;</label></font>
				  </td>
                  <td align="left">
					<input type="password" name="j_password" size="18" id="j_password" style="width:180px;background: #FFFFFF; border: 1px solid #C0C0C0" tabindex="2">
				  </td>
          </tr>   
		  <tr rowspan="3"></tr>
     	<tr >
			<td colspan=3 style="height:35x" valign="top" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;
			<input type="submit" name="login" class="button login" value="&nbsp;&nbsp;" border=0/>
			<input type="reset"  name="login" class="button reset" value="&nbsp;&nbsp;" border=0/>
			 
			
			</td>
     	</tr>
     	<tr >
     	
			<td colspan=3 style=" text-align:center;color: red; font-size:12px;">
			
			<%if(request.getParameter("error")!=null && request.getParameter("error").equals("1")){ %>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名或密码错误，请重新登录。
			<%} %>

			</td>
     	</tr>
	</table>
  <!-- end #container -->

 <div style=" width:1003px; margin:0 auto; text-align:center; margin-top:90px; font-size:12px;">
    <span class="STYLE1"><a href="http://www.lilysoft.com/china/" target="_blank">淄博百合软件开发有限公司</a>&nbsp;|&nbsp;
  &nbsp; Copyright &copy; 百合软件&nbsp;版权所有 </span>
  <div>
</form>
</div>
<script language="javascript" type="text/javascript">  
	<%--重置方法--%>
	function resetInfo(){
		document.getElementById("username").value = "";
		document.getElementById("j_password").value = "";
		document.getElementById("username").focus();
	}
</script>
</body>
</html>
<%@ include file="/scripts/login.js"%>