<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/ext-files.jsp"%>
</head>
<script type="text/javascript">
//--------------------------- Global Variables Definition -----------------------------------		
	var currCatalogCode, currCatalogName;
	var currDictionaryMode = 1;
	
    Ext.onReady(function(){
//--------------------------- Catalog Tree Layout -----------------------------------		
	   var catalogPanel = new Ext.tree.TreePanel({
			id:'catalog',
			autoScroll : true,
			animate : true,
			border : false,
			rootVisible : false,
			bodyStyle: 'background-color:#cad9ec;',
			root : new Ext.tree.AsyncTreeNode( {
				id : 'root',
				text : '字典分类',
				draggable : false,
				expanded : true
			}),
			loader: new Ext.tree.TreeLoader({
				dataUrl:'dictionary!listCatalog.action'
			}),
			listeners : {
				'click' : catalogPanel_onNodeClick
			}
		});
//--------------------------- Dictionary Grid Layout -----------------------------------		
<lilydap:dictionaryExtSimpleStore name="showFlagStore" catalog="zero_one"/>

		var dictionaryStore = new Ext.data.Store({ 
			url : 'dictionary!listDictionary.action',
			reader: new Ext.data.JsonReader({ 
			id:'dictionaryStore',
			successProperty :'success' 
			}, [ 
				{name: 'id', mapping:'id', type:'int'}, 
				{name: 'dictId', mapping:'dictId', type:'int'}, 
				{name: 'dictCode', mapping:'dictCode', type:'string'},
				{name: 'dictValue', mapping:'dictValue', type:'string'},
				{name: 'systemFlag', mapping:'systemFlag', type:'int'}, 
				{name: 'showFlag', mapping:'showFlag', type:'int'}
			]) 
		}); 

		var showFlagColumn = new Ext.grid.CheckColumn({
			id: 'showFlag',
			header: "是否下拉显示 ",
			dataIndex: 'showFlag'
		});

		var systemFlagColumn = new Ext.grid.CheckColumn({
		   id: 'systemFlag',
		   header: "是否系统字典 ",
		   dataIndex: 'systemFlag'
		});
	   
		var checkboxSelectionModel = new Ext.grid.CheckboxSelectionModel();
		
		var columnModel_IdValue = new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			checkboxSelectionModel, 
			{header:'ID', id:'id', dataIndex:'id', hidden:true}, 
			{header:'字典ID值 ', id:'dictId', dataIndex:'dictId'}, 
			{header:'字典值 ', id:'dictValue', dataIndex:'dictValue', editor: new Ext.form.TextField({allowBlank:false})}, 
			systemFlagColumn,
			showFlagColumn
		]); 

		var columnModel_CodeValue = new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			checkboxSelectionModel, 
			{header:'ID', id:'id', dataIndex:'id', hidden:true}, 
			{header:'字典编码值 ', id:'dictCode', dataIndex:'dictCode'}, 
			{header:'字典值 ', id:'dictValue', dataIndex:'dictValue', editor: new Ext.form.TextField({allowBlank:false})}, 
			systemFlagColumn,
			showFlagColumn
		]); 

		var columnModel_Value = new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			checkboxSelectionModel, 
			{header:'ID', id:'id', dataIndex:'id', hidden:true}, 
			{header:'字典值 ', id:'dictValue', dataIndex:'dictValue', editor: new Ext.form.TextField({allowBlank:false})}, 
			systemFlagColumn,
			showFlagColumn
		]); 
		
	    var dictionaryGrid = new Ext.grid.EditorGridPanel({
			store: dictionaryStore, 
			el: 'dictionaryGridDiv',
			sm: checkboxSelectionModel, 
			cm: columnModel_IdValue, 
			selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
			title:'数据字典',
	        autoExpandColumn:'dictValue',
			enableDragDrop : true,
			ddGroup : 'dictionaryDD', 
        	clicksToEdit:1,
			tbar:[ {
				text:'&nbsp;&nbsp;添加&nbsp;&nbsp;',
				handler:dictionaryGrid_onAddDictionary,
				iconCls:"dicture-button-add",
				disabled:true
			},{
				text:'&nbsp;&nbsp;删除&nbsp;&nbsp;',
				handler:dictionaryGrid_onRemoveDictionary,
				iconCls:"dicture-button-delete",
				disabled:true
			}],
			listeners : {
				'afteredit' : dictionaryGrid_onAfterEdit
			}
		});
//--------------------------- Dictionary Grid Row DragDrop Support, Implement adjust dictionary order -----------------------------------		
		var ddropTarget = new Ext.dd.DropTarget(dictionaryGrid.getEl(), {
			ddGroup: "dictionaryDD", 
			copy:false,
			notifyDrop : ddropTarget_onNotifyDrop
		}); 
		
//--------------------------- Dictionary Form Layout -----------------------------------		
		var dictionaryForm = new Ext.FormPanel({
			labelAlign: 'left',
			labelWidth: 60,
			frame:true,
			bodyStyle:'padding:10px 10px 0',
			autoHeight:true,
			defaults: {width: 400},
			url:'dictionary!addDictionary.action',
			method:'POST',
            items:[{
                layout: 'form',
                items: [{
                    xtype:'hidden',
                    id: 'id',
                    name: 'id',
					hidden:true,
                    anchor:'95%'
                },{
                    xtype:'hidden',
                    id: 'catalogCode',
                    name: 'catalogCode',
					hidden:true,
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '字典分类',
					labelStyle: 'width:75; text-align:right',
                    id: 'catalogName',
                    name: 'catalogName',
					disabled: true,
                    anchor:'95%'
                },{
                    xtype:'numberfield',
                    fieldLabel: '字典ID值 ',
					labelStyle: 'width:75; text-align:right',
                    id: 'dictId',
                    name: 'dictId',
					allowBlank: false,
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '字典编码值 ',
					labelStyle: 'width:75; text-align:right',
                    id: 'dictCode',
                    name: 'dictCode',
					allowBlank: false,
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '字典值 ',
					labelStyle: 'width:75; text-align:right',
                    id: 'dictValue',
                    name: 'dictValue',
					allowBlank: false,
                    anchor:'95%'
                }]
			}]
		});

		var dictionaryWin = new Ext.Window({
			id:'dictionary-win',
			layout:'fit',
			width:440,
			height:180,
			closeAction:'hide',
			plain: true,
			buttonAlign:'center',
			title:'',
			items:dictionaryForm,
			buttons: [{
				text:'保存',
				disabled:false,
				iconCls:"common-button-save",
				handler:dictionaryForm_onSave
			},{
				text: '取消',
				iconCls:"common-button-cancel",
				handler: function(){
					dictionaryForm.form.reset();
					dictionaryWin.hide();
				}
			}]
		});
			
//--------------------------- Viewport Layout -----------------------------------		
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[{
					layout:'fit',
                    region:'west',
					width:200,
					border:false,
                    margins:'0 0 0 5',
					items:[catalogPanel]
                },{
					layout:'fit',
                    region:'center',
					border:false,
					items:[dictionaryGrid]
                }/*{
					layout:'border',
					region:'center',
					items:[{
						layout:'fit',
						region:'center',
						border:false,
						items:[dictionaryGrid]
					},{
						layout:'fit',
						height: 140,
						border:false,
						bodyStyle: 'background-color:#f0f0f0;',
						contentEl: 'tips',
						region:'south'
					}]
                }*/]
		});
//--------------------------- Event Handle Function -----------------------------------		
		function  catalogPanel_onNodeClick(node){
			if (node.attributes.type == 'catalog')	{
				currCatalogCode = node.attributes.code;
				currCatalogName = node.attributes.text;
				currDictionaryMode = node.attributes.mode;
			
				dictionaryGrid.setTitle("数据字典 - " + currCatalogName);
				
				var cm;
				if (currDictionaryMode == 1) {
					cm = columnModel_IdValue;
				} else if (currDictionaryMode == 2) {
					cm = columnModel_CodeValue;
				} else {
					cm = columnModel_Value;
				}
				
//				dictionaryStore.url = 'dictionary.do?method=listDictionary&catalog=' + currCatalogCode;
//				dictionaryStore.proxy = new Ext.data.HttpProxy({url: 'dictionary.do?method=listDictionary&catalog=' + currCatalogCode});
				dictionaryStore.baseParams = dictionaryStore.baseParams || {};
				dictionaryStore.baseParams["catalog"] = currCatalogCode;
				
				dictionaryGrid.reconfigure(dictionaryStore, cm);
				dictionaryStore.load({params:{start:0}}); 
				
				dictionaryGrid.getTopToolbar().setDisabled(false);
			}
		}
		
		function dictionaryGrid_onAfterEdit(e) {
			var dictValue = e.value;
			var id = e.record.data.id;
			
			Ext.Ajax.request({url: 'dictionary!modifyDictionary.action?id='+id+"&dictValue="+escape(escape(dictValue)), method:'GET'}); 
		}
				 
		function dictionaryGrid_onRemoveDictionary(){
			if (checkboxSelectionModel.getSelections().length == 0) {
				Ext.MessageBox.alert('警告信息', '没有要删除的数据，请先选择删除数据，然后进行删除！');
				return;
			}
			
			Ext.MessageBox.confirm('', 
				'是否确实要删除数据字典数据？', 
				function(btn) {
					 if(btn == 'yes') {
						 var rows=checkboxSelectionModel.getSelections();
						 for (i = 0; i < rows.length; i++) {
							var rowData=dictionaryGrid.store.getById(rows[i].id);
							var id = rowData.get("id");
							
							var systemFlag = rowData.get("systemFlag");
							if (systemFlag == 1) {
								Ext.MessageBox.alert('', '字典记录[ ' + rowData.get("dictValue") + ' ]是系统定义字典，不允许删除！');
								continue;
							}
							
							Ext.Ajax.request({
								url: 'dictionary!removeDictionary.action?id='+id, 
								method:'POST',
								success: function(response) {
									//dictionaryStore.remove(rowData);
									var result = Ext.decode(response.responseText);
									if (result.success) {
										var index = dictionaryStore.find("id", result.id);
										dictionaryStore.remove(dictionaryStore.getAt(index));
									} else
										Ext.MessageBox.alert('', result.errors);
								},
								failure: function(response) {
									
								}
							}); 
						 }
					}
				}
			);
		}
		
		function dictionaryGrid_onAddDictionary(){
			dictionaryWin.show();

			var myform = dictionaryForm.form;
			myform.reset();
			myform.findField("catalogCode").setValue(currCatalogCode);
			myform.findField("catalogName").setValue(currCatalogName);
			
			if (currDictionaryMode == 1) {
				myform.findField("dictId").enable();
				myform.findField("dictCode").disable();
			} else if (currDictionaryMode == 2) {
				myform.findField("dictId").disable();
				myform.findField("dictCode").enable();
			} else {
				myform.findField("dictId").disable();
				myform.findField("dictCode").disable();
			}
		} 
		
				 
		function dictionaryForm_onSave(){
			this.disabled = true;
			dictionaryForm.form.submit({url:'dictionary!addDictionary.action', waitMsg:'submit, wait...', success:dictionaryForm_onSuccess, failure:dictionaryForm_onFailure});
			this.disabled = false;
		}
		
		function dictionaryForm_onFailure(form, action) {
			Ext.MessageBox.alert('', '添加的数据字典编码与已有字典编码重复，请重新输入新的字典编码');
		}
	
		function dictionaryForm_onSuccess(form, action){
			dictionaryWin.hide();
			
			dictionaryStore.load({params:{start:0}}); 
		}
		
		function ddropTarget_onNotifyDrop(dd, e, data) {
			var target = Ext.lib.Event.getTarget(e);
			var rindex = dictionaryGrid.getView().findRowIndex(target); // index of row where item is dropped
			var lindex = data.rowIndex;

			if (rindex === false) return false;
			if (rindex == lindex) return false;
			
			var order = rindex - lindex;
			
			var rows = dictionaryGrid.getSelectionModel().getSelections();
			var nbRows = rows.length;
			var cindex = dd.getDragData(e).rowIndex;

			for(i = 0; i < nbRows; i++) 
			{
				var rowData = dictionaryStore.getById(rows[i].id); 
				var id = rowData.get("id");

				Ext.Ajax.request({
					url: 'dictionary!adjustDictionaryOrder.action&id='+id+"&order="+order, 
					method:'POST',
					success: function(response) {
						dictionaryStore.load({params:{start:0}}); 
					}
				}); 
			}
		}
	});
</script>
<body>
	<div id="dictionaryGridDiv"></div>
    <div id="tips" style="font-size:12px; padding:5px 5px 5px 5px; color:#666666; font-weight:800"></div>
</body>
</html>