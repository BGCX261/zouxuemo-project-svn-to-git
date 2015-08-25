<script type="text/javascript">
    if (getCookie("username") != null) {
		$("#username").value = getCookie("username");
		
        $("#j_password").focus();
    } else {
        $("#username").focus();
    }
	
    
    function saveUsername(theForm) {
        var expires = new Date();
        expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
		
        setCookie("username", theForm.username.value, expires, "<c:url value="/"/>");
    }
    
    function validateForm(form) {
		var pass = validateRequired(form); 
		if (pass) {
			var username = form.username.value;

			form.j_username.value = 'person|' + username;
		}
		
        return pass;
    } 
    
    function passwordHint() {
        if ($("#username").value.length == 0) {
            alert("The <fmt:message key="label.username"/> field must be filled in to get a password hint sent to you.");
            $("#username").focus();
        } else {
            location.href="<c:url value="/passwordHint.html"/>?username=" + $("#username").value;     
        }
    }
    
    function required () { 
        this.aa = new Array("username", "\u7528\u6237\u540D\u4E3A\u5FC5\u586B\u9879\uFF01", new Function ("varName", " return this[varName];"));
        this.ab = new Array("j_password", "\u5BC6\u7801\u4E3A\u5FC5\u586B\u9879\uFF01", new Function ("varName", " return this[varName];"));
    } 

	// This function is used by the login screen to validate user/pass
	// are entered. 
	function validateRequired(form) {                                    
		var bValid = true;
		var focusField = null;
		var i = 0;                                                                                          
		var fields = new Array();                                                                           
		oRequired = new required();                                                                         
																											
		for (x in oRequired) {                                                                              
			if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
			   if (i == 0)
				  focusField = form[oRequired[x][0]]; 
				  
			   fields[i++] = oRequired[x][1];
				
			   bValid = false;                                                                             
			}                                                                                               
		}                                                                                                   
																										   
		if (fields.length > 0) {
		   focusField.focus();
		   alert(fields.join('\n'));                                                                      
		}                                                                                                   
																										   
		return bValid;                                                                                      
	}
</script>
