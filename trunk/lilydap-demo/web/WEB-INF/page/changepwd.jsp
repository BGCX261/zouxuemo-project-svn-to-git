
var pwdForm = new Ext.FormPanel({
    labelAlign: 'right',
    labelWidth: 75,
    frame:true,
	bodyStyle:'padding:10px 10px 0',
    defaults: {width: 280},
    bodyStyle:'padding:10px 5px 0',
    method:'POST', 
    items:[{ 
        layout: 'form',
        items: [{
            xtype:'textfield',
            fieldLabel: '\u539F\u6709\u53E3\u4EE4',
            id: 'oldPwd',
            name: 'oldPwd',
            inputType: 'password',
            anchor:'95%'
        }, {
            xtype:'textfield',
            fieldLabel: '\u65B0&nbsp;\u53E3&nbsp;\u4EE4',
            id: 'newPwd',
            name: 'newPwd',
            inputType: 'password',
            anchor:'95%'
        }, {
            xtype:'textfield',
            fieldLabel: '\u786E\u8BA4\u53E3\u4EE4',
            id: 'confirmPwd',
            name: 'confirmPwd',
            inputType: 'password',
            anchor:'95%'
        }]
    }]
});

var pwdWin = new Ext.Window({
    id:'pwd-win',
    layout:'fit',
    width:340,
    height:183,
    plain: true,
    buttonAlign:'center',
    title:'\u4FEE\u6539\u767B\u5F55\u53E3\u4EE4',
    items:pwdForm,
    buttons: [{
        text:'\u4FDD\u5B58',
        disabled:false,
        handler:pwdForm_onSave
    },{
        text: '\u53D6\u6D88',
        handler: function(){
	        pwdForm.form.reset();
            pwdWin.close();
        }
    }]
});
//--------------------------- Event Handle Function -----------------------------------		
	function pwdForm_onSave() {
		this.disabled = true;
		pwdForm.form.submit({url:'organize/person!changePwd.action', waitMsg:'\u7CFB\u7EDF\u6B63\u5728\u4FDD\u5B58\u6570\u636E\uFF0C\u8BF7\u7B49\u5F85...', success:pwdForm_onSuccess, failure:pwdForm_onFailure});
		this.disabled = false;
	}
		
	function pwdForm_onFailure(form, action) {
		Ext.MessageBox.alert('', action.result.error);
	}

	function pwdForm_onSuccess(form, action){
//    	Ext.MessageBox.alert('', action.result.tip);
        
        pwdForm.form.reset();
    	pwdWin.close();
	}
