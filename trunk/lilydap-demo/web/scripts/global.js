if (typeof(LilyDAP) != "object") {
    var LilyDAP = {}
}
LilyDAP.is_ie = /msie/.test(navigator.userAgent.toLowerCase());
LilyDAP.$import = function(jsfile, onloadfun, defer) {
    LilyDAP._IMPORT_JSFILE = LilyDAP._IMPORT_JSFILE || {};
    if (!LilyDAP._IMPORT_JSFILE[jsfile]) {
        var s = "script";
        LilyDAP._IMPORT_JSFILE[jsfile] = 1;
        var obj = document.createElement("script");
        obj.src = jsfile;
        if (defer) {
            obj.defer = "defer";
            obj.setAttribute("defer", "defer")
        }
        if (onloadfun) {
            if (typeof onloadfun != "function") {
                var onloadfunstr = onloadfun;
                onloadfun = function() {
                    try {
                        eval(onloadfunstr)
                    } catch(e) {}
                }
            }
            if (LilyDAP.is_ie) {
                obj.onreadystatechange = function() {
                    if (this.readyState == "complete" || this.readyState == "loaded") {
                        onloadfun()
                    }
                }
            } else {
                obj.onload = onloadfun
            }
        }
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(obj, s)
    }
};

/* This function is used to set cookies */
function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

/* This function is used to get cookies */
function getCookie(name) {
	var prefix = name + "=" 
	var start = document.cookie.indexOf(prefix) 

	if (start==-1) {
		return null;
	}
	
	var end = document.cookie.indexOf(";", start+prefix.length) 
	if (end==-1) {
		end=document.cookie.length;
	}

	var value=document.cookie.substring(start+prefix.length, end) 
	return unescape(value);
}

/* This function is used to delete cookies */
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}
