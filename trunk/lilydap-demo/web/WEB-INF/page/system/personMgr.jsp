<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/ext-files.jsp"%>
</head>
<script type="text/javascript">
	var enabledStore = new Ext.data.SimpleStore({
		fields: ['code', 'value'],
		data: [[true, '是'], [false, '否']]
	});
	<lilydap:dictionaryExtSimpleStore name="sexStore" catalog="sex"/>
	<lilydap:dictionaryExtSimpleStore name="personStateStore" catalog="personState" selectText="-显示所有-"/>
	
	//--------------------------- Person Query Condition Form Layout -----------------------------------
	<lilydap:extFormPanel name="personQueryConditionForm" model="organize.Person" title="人员维护"
			labelAlign="right" labelWidth="60" frame="true" propertys="autoHeight:true, bodyStyle:'padding:15px 0'">
		<lilydap:extFormFields>
			<lilydap:extFormColumns>
				<lilydap:extFormColumn propertys="width:200">
					<lilydap:extFormTextField id="condition.username.like" name="condition.username.like" propertys="fieldLabel:'用户名'" tabIndex="97" anchor="95%" />
				</lilydap:extFormColumn>
				<lilydap:extFormColumn propertys="width:200">
					<lilydap:extFormTextField id="condition.name.like" name="condition.name.like" propertys="fieldLabel:'姓名'" tabIndex="98" anchor="95%" />
				</lilydap:extFormColumn>
				<lilydap:extFormColumn propertys="width:200">
					<lilydap:extFormCombo store="personStateStore" displayField="value" valueField="id" value="$ignore$"
							propertys="fieldLabel:'人员状态', hiddenName:'condition.state'" triggerAction="all" editable="false" tabIndex="99" anchor="95%" />
				</lilydap:extFormColumn>
			</lilydap:extFormColumns>
		</lilydap:extFormFields>
		<lilydap:extTopBarButton text="注册人员&nbsp;&nbsp;" iconCls="common-button-add" propertys="disabled:false" handler="personQueryConditionForm_onAdd" />
		<lilydap:extTopBarButton text="删除人员&nbsp;&nbsp;" iconCls="common-button-delete" propertys="disabled:false" handler="personQueryConditionForm_onRemove" />
		<lilydap:extTopBarButton text="-"  propertys="xtype: 'tbseparator'" />
		<lilydap:extTopBarButton text="激活人员&nbsp;&nbsp;" iconCls="common-button-lock_open" propertys="disabled:false" handler="personQueryConditionForm_onEnable" />
		<lilydap:extTopBarButton text="锁定人员&nbsp;&nbsp;" iconCls="common-button-lock"propertys="disabled:false" handler="personQueryConditionForm_onDisable" />
		<lilydap:extTopBarButton text="-"  propertys="xtype: 'tbseparator'"/>
		<lilydap:extTopBarButton text="停用人员&nbsp;&nbsp;" iconCls="common-button-stop" propertys="disabled:false" handler="personQueryConditionForm_onStop" />
		<lilydap:extTopBarButton text="-"  propertys="xtype: 'tbseparator'"/>
		<lilydap:extTopBarButton text="导入人员&nbsp;&nbsp;" iconCls="common-button-commit" propertys="disabled:false" handler="handleUploadWindow" />
	</lilydap:extFormPanel>
	
	installQueryOnFieldChanged(personQueryConditionForm, personQueryConditionForm_onQuery);
	
	//--------------------------- Person Grid Layout -----------------------------------
	<lilydap:extGridPanel name="personGrid" model="organize.Person">
		<lilydap:extJsonStore name="personStore" url="person!query.action" totalProperty="total" root="data" propertys="remoteSort: true" />
		<lilydap:extSelectionModel name="checkboxSelectionModel" />
		<lilydap:extColumnModel name="personColumnModel">
			<lilydap:extColumn clazz="Ext.grid.RowNumberer" propertys="width:20" />
			<lilydap:extColumn name="checkboxSelectionModel" />
			<lilydap:extColumn field="id" propertys="hidden:true" />
			<lilydap:extColumn field="username" width="100" propertys="align:'center', sortable:true" />
			<lilydap:extColumn field="name" width="110" propertys="align:'center', sortable:true" />
			<lilydap:extColumn field="mobile" width="90" propertys="align:'center'" />
			<lilydap:extColumn field="phone" width="90" propertys="align:'center'" />
			<lilydap:extDictionaryColumn field="state" store="personStateStore" valueField="id" displayField="value" width="60" propertys="align:'center', renderer: stateRender" />
			<lilydap:extDictionaryColumn field="enabled" store="enabledStore" valueField="code" displayField="value" width="60" propertys="align:'center', renderer: enabledRender" />
		</lilydap:extColumnModel>
		 <lilydap:extPageingToolbar name="pageingToolbar" pageSize="20" />
	</lilydap:extGridPanel>
	
	function enabledRender(v, p, record) {
		var valueField = this.valueField;
		var index = this.store.findBy(function(record) {
			if (record.get(valueField) == v)
				return true;
		});
		
		if(index != -1) {
			var val = this.store.getAt(index).get(this.displayField);
			
			if (v) {
				return "<font color='green'><b>" + val + "</b></font>";
			} else {
				return "<font color='red'><b>" + val + "</b></font>";
			}
		} else {
			return v;
		}
	}
	
	function stateRender(v, p, record) {
		var valueField = this.valueField;
		var index = this.store.findBy(function(record) {
			if (record.get(valueField) == v)
				return true;
		});
		
		if(index != -1) {
			var val = this.store.getAt(index).get(this.displayField);
			
			if ("0" == v) {
				return "<font color='green'><b>" + val + "</b></font>";
			} else {
				return "<font color='red'><b>" + val + "</b></font>";
			}
		} else {
			return v;
		}
	}
	
	personGrid.on("rowdblclick", personGrid_onRowdbclick);
	
	pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);

	var pwdForm = new Ext.FormPanel({
	    labelAlign: 'right',
	    labelWidth: 75,
	    frame:true,
	    model:true,
		bodyStyle:'padding:10px 10px 0',
	    defaults: {width: 280},
	    bodyStyle:'padding:10px 5px 0',
	    method:'POST', 
	    items:[{
	       xtype:'hidden',
	       name: 'username'
	    },{
           xtype:'textfield',
           fieldLabel: '新&nbsp;口&nbsp;令',
           id: 'newPwd',
           name: 'newPwd',
           inputType: 'password',
           allowBlank:false,
           anchor:'95%'
	    },{
           xtype:'textfield',
           fieldLabel: '确认口令',
           id: 'confirmPwd',
           name: 'confirmPwd',
           allowBlank:false,
           inputType: 'password',
           anchor:'95%'
        }]
    });

	var pwdWin = new Ext.Window({
	    id:'pwd-win',
	    layout:'fit',
	    width:340,
	    height:150,
	    modal:true,
	    plain: true,
	    buttonAlign:'center',
	    title:'修改登录口令',
	    items:pwdForm,
	    buttons: [{
	        text:'保存',
	        iconCls: 'common-button-save',
	        handler:pwdForm_onSave
	    },{
	        text: '取消',
	         iconCls: 'common-button-cancel',
	        handler: function(){
	            pwdWin.hide();
	        }
	    }]
	});
	
	//--------------------------- Person Form Layout -----------------------------------
	<lilydap:extFormPanel name="personForm" model="organize.Person" labelAlign="right" labelWidth="80" frame="true"
			url="person!save.action" propertys="defaults:{width:480}, bodyStyle:'padding:5px 5px 5px 1px'">
		<lilydap:extJsonReader root="data">
			<lilydap:extJsonReaderConvert fields="workDate" func="readJsonDate" />
			<lilydap:extJsonReaderConvert fields="cardSendDate" func="readJsonDate" />
			<lilydap:extJsonReaderConvert fields="examineDate" func="readJsonDate" />
		</lilydap:extJsonReader>
		<lilydap:extFormFields>
			<lilydap:extFormHidden field="id" />
			<lilydap:extFormColumns>
				<lilydap:extFormColumn width=".5">
					<lilydap:extFormTextField field="username" propertys="fieldLabel:'用户编号', allowBlank:false, maxLength:'25'" anchor="98%" />
					<lilydap:extFormCombo field="sex" store="sexStore" displayField="value" triggerAction="all" editable="false"
							propertys="fieldLabel:'性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别'" anchor="98%" />
					<lilydap:extFormTextField field="phone" propertys="fieldLabel:'联系电话', maxLength:'15'" anchor="98%" />
				</lilydap:extFormColumn>
				<lilydap:extFormColumn width=".5">
					<lilydap:extFormTextField field="name" propertys="fieldLabel:'姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名', maxLength:'15'" anchor="98%" />
					<lilydap:extFormDateField field="birthday" propertys="fieldLabel:'出生日期'" readOnly="true" format="Y-m-d" anchor="98%" />
					<lilydap:extFormTextField field="mobile" propertys="fieldLabel:'绑定手机', validateValue: function(value) {return checkMobile(value,this,true);}" anchor="98%" />
				</lilydap:extFormColumn>
			</lilydap:extFormColumns>
			<lilydap:extFormTextField field="password" propertys="fieldLabel:'初始密码', allowBlank:false, maxLength:'127'" anchor="99%" />
			<lilydap:extFormTextArea field="des" propertys="fieldLabel:'备注', height: 100" anchor="99%" />
		</lilydap:extFormFields>
	</lilydap:extFormPanel>

	var personWin = new Ext.Window({
		id:'person-win',
		layout:'fit',
		width:510,
		height:300,
		closeAction:'hide',
		plain: true,
		modal:true,
		resizable :false,
		buttonAlign:'center',
		title:'人员维护',
		items:personForm,
		buttons: [{
			text:'更改密码',
			id:'updatebutton',
			iconCls: 'common-button-password',
			handler:function(){
				pwdWin.show();
				pwdForm.form.reset();
				var uname=personForm.form.findField("username").getValue()
				pwdForm.form.findField("username").setValue(uname);
			}
		},{
			text: '保存',
			disabled: false,
			iconCls: 'common-button-save',
			handler: personForm_onSave
		},{
			text: '取消',
			iconCls: 'common-button-cancel',
			handler: function(){
				personWin.hide();
			}
		}]
	});

	Ext.onReady(function(){
		//--------------------------- Viewport Layout -----------------------------------		
		var viewport = new Ext.Viewport({
			layout:'border',
	        items:[{
				layout:'fit',
	            region:'north',
				height:100,
				border:false,
	            margins:'0 0 0 0',
				items:[personQueryConditionForm]
	        },{
				layout:'fit',
	            region:'center',
				border:false,
	            margins:'0 0 0 0',
				items:[personGrid]
	        }]
		});
	});
//--------------------------- Event Handle Function -----------------------------------	
	function pwdForm_onSave() {
		if(pwdForm.getForm().isValid()){
			pwdForm.form.submit({
				url: 'person!changePwdByManager.action', 
				waitMsg: 'save data', 
				success: function(form, action){
					pwdWin.hide();
					Ext.MessageBox.alert('提示信息', action.result.tip);
				}, 
				failure:function(form, action){
					Ext.MessageBox.alert('提示信息', action.result.error);
				}
			});
		}
	}
	function personQueryConditionForm_onQuery() {
		var params = personQueryConditionForm.getForm().getValues()
		
		personStore.baseParams = personStore.baseParams || {};
		Ext.apply(personStore.baseParams, params);
		
		pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
	}
	
	function personGrid_onRowdbclick(){
		personWin.show();

		var myform = personForm.form;
		myform.findField("username").disable();
		myform.findField("password").hide();
		$("label[for=password]").hide();
		Ext.get("updatebutton").show();
		var rows = checkboxSelectionModel.getSelections();
		var id = personStore.getById(rows[0].id).get("id");
		personForm.load({url: 'person!get.action', method:'POST', params:{'id': id}, waitMsg: '系统正在加载数据，请稍候...'});
	}

	function personQueryConditionForm_onAdd() {
		personWin.show();
		
		var myform = personForm.form;
		myform.reset();
		myform.findField("username").enable();
		myform.findField("password").show();
		$("label[for=password]").show();
		Ext.get("updatebutton").hide();
		personForm.load({url: 'person!get.action', method:'POST', params:{'id': 0}, waitMsg: '系统正在加载数据，请稍候...'});
	}
	
	function personForm_onSave() {
		this.disabled = true;
		if(personForm.getForm().isValid()){
			personForm.form.submit({waitMsg:'系统正在保存数据，请等待...', success:personForm_onSuccess, failure:personForm_onFailure});
		}
		this.disabled = false;
	}
	
	function personForm_onFailure(form, action) {
		Ext.MessageBox.alert('', '输入的人员编码或者姓名与现有人员编码或者姓名重复，请重新输入新的人员编码或者姓名');
	}

	function personForm_onSuccess(form, action){
		personWin.hide();
		
		pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
	}
	
	function personQueryConditionForm_onRemove() {
		if (checkboxSelectionModel.getSelections().length == 0) {
			Ext.MessageBox.alert('警告信息', '没有要删除的数据，请先选择删除数据，然后进行删除！');
			return;
		}
		
		Ext.MessageBox.confirm('提示信息',' 您确认要删除吗？&nbsp;&nbsp;&nbsp;&nbsp;', 
			function(btn) {
				 if(btn == 'yes') {
					 var rows=checkboxSelectionModel.getSelections();
					 for (i = 0; i < rows.length; i++) {
						var rowData=personGrid.store.getById(rows[i].id);
						var id = rowData.get("id");
						
						Ext.Ajax.request({
							url: 'person!remove.action?id='+id, 
							method:'POST',
							success: function(response) {
								var result = Ext.decode(response.responseText);
								if (result.success) {
									var index = personStore.find("id", result.id);
									personStore.remove(personStore.getAt(index));
									
									pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
								} else
									Ext.MessageBox.alert('提示信息', result.error);
							},
							failure: function(response) {
								
							}
						}); 
					 }
				}
			}
		);
	}
	
	function personQueryConditionForm_onEnable() {
		 var rows=checkboxSelectionModel.getSelections();
		 for (i = 0; i < rows.length; i++) {
			var rowData=personGrid.store.getById(rows[i].id);
			var id = rowData.get("id");
			
			Ext.Ajax.request({
				url: 'person!enable.action?id='+id+'&enabled=true', 
				method:'POST',
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
					} else
						Ext.MessageBox.alert('', result.error);
				},
				failure: function(response) {
					
				}
			}); 
		 }
	}

	function personQueryConditionForm_onDisable() {
		 var rows=checkboxSelectionModel.getSelections();
		 for (i = 0; i < rows.length; i++) {
			var rowData=personGrid.store.getById(rows[i].id);
			var id = rowData.get("id");
			
			Ext.Ajax.request({
				url: 'person!enable.action?id='+id+'&enabled=false', 
				method:'POST',
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
					} else
						Ext.MessageBox.alert('', result.error);
				},
				failure: function(response) {
					
				}
			}); 
		 }
	}

	function personQueryConditionForm_onStop() {
		Ext.MessageBox.confirm('提示信息',' 您确认要停用人员吗？停用后，该人员状态变更为“离职”，将不能登录系统。此操作不可恢复！', function(btn) {
			if (btn == 'yes') {
				var rows=checkboxSelectionModel.getSelections();
				for (i = 0; i < rows.length; i++) {
					var rowData=personGrid.store.getById(rows[i].id);
					var id = rowData.get("id");
					
					Ext.Ajax.request({
						url: 'person!stop.action?id='+id, 
						method:'POST',
						success: function(response) {
							var result = Ext.decode(response.responseText);
							if (result.success) {
								pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
							} else
								Ext.MessageBox.alert('', result.error);
						},
						failure: function(response) {
							
						}
					}); 
				 }
			}
		});
	}

	<lilydap:extFormPanel name="uploadForm" model="com.lily.dap.entity.organize.Person" propertys="fileUpload: true,url: 'person!importExcel.action', defaults: {width: 380}, bodyStyle:'padding:10 0 0 10'">	
		<lilydap:extTopBarButton text="导入" handler="imports"/>
		<lilydap:extFormFields>	
			<lilydap:extFormTextField field="file" label="选择文件" propertys="allowBlank:false,id:'selectFileId',height:22,name: 'file', inputType: 'file'"/>		
		</lilydap:extFormFields> 
	</lilydap:extFormPanel>	


	<lilydap:extFormPanel name="uploadResult" model="com.lily.dap.entity.organize.Person" propertys="defaults: {width: 380}, bodyStyle:'padding:10 0 0 10'">		
		<lilydap:extFormFields>		
			<lilydap:extFormTextArea height="300" field="uploadResultShow" label="导入结果" propertys="id:'uploadResultShow'"/>
		</lilydap:extFormFields> 
	</lilydap:extFormPanel>	



	var uploadWindow = null;
	function handleUploadWindow(){
		if(!uploadWindow){			
			uploadWindow = new Ext.Window({
				layout:'border',
				width:660,
				height:500,
				closeAction:'hide',
				plain: true,
				title:'人员导入信息',
				items:[{
					region:'north',
					layout:'fit',
					height:120,
					items:[uploadForm]
				},{
					region:'center',
					layout:'fit',
					items:uploadResult
				}]
		  });			
		}
		uploadForm.form.reset();
		uploadResult.form.reset();
		uploadWindow.show();		
	} 

	function imports() {			
		var filePath=Ext.getCmp("selectFileId").getValue();	

		var fileNameArray = filePath.trim().split(".");
		var fileExtName = fileNameArray[fileNameArray.length-1];
		
		if(filePath.trim()=='' || fileExtName.toLowerCase()!='xls')			
			Ext.Msg.alert('提示信息','您没有选择正确的EXCEL文件。');
		else{	
			uploadForm.getForm().url='person!importExcel.action';
			uploadForm.getForm().submit({ 
			waitTitle:"提示信息",
			waitMsg:"系统正在加载数据，请稍候...",
			success: function(form, action){ 
				var jsonData = Ext.decode(action.response.responseText);
				uploadResult.getForm().findField("uploadResultShow").setValue(jsonData.msgStr);
				

			}, 
			failure: function(form, action){ 
				var jsonData = Ext.decode(action.response.responseText);
				if(jsonData.errorStr!=null && ""!=jsonData.errorStr)
					uploadResult.getForm().findField("uploadResultShow").setValue(jsonData.msgStr)						
				else
					uploadResult.getForm().findField("uploadResultShow").setValue('人员导入失败！')
			} 
			});
		} 
	}
</script>
<body>
	<div id="personGridDiv"></div>
</body>
</html>