Ext.ActionModel = function(config) {
    Ext.apply(this, config);
}

Ext.ActionModel.prototype = {
	type : 'default'
}

Ext.extend(Ext.ActionModel, Ext.util.Observable, {
	
});

Ext.ActionModel.Grid_Form = function(options){
    this.addEvents(
        /**
         * @event beforeload
         * 在Form装载数据前触发
         * 
         * @param {Boolean} newFlag			当前是否是新建
         * @param {object} e					事件数据对象 
         * 				{FormPanel} e.form		要装载数据的Form
         * 				{Reocrd} e.record		如果是从Grid中转载数据，则存放装载数据的那条记录
         * 				{Object} e.params		如果需要在调用Action的get方法时添加额外参数，可以在提供的这个属性上增加参数值，例如：e.params['classId'] = 1;
         * @return 如果返回false，则停止执行装载操作，并关闭窗口
         */
        'beforeload',
        /**
         * @event afterload
         * 在Form装载数据后触发
         * 
         * @param {FormPanel} form
         */
        'afterload',
        /**
         * @event beforesave
         * 在Form保存数据前触发
         * 
         * @param {FormPanel} form	要保存数据的Form
         * 		   {Object} params	如果需要在调用Action的save方法时添加额外参数，可以在提供的这个属性上增加参数值，例如：params['classId'] = 1;
         * @return 如果返回false，则停止执行保存操作
         */
        'beforesave',
        /**
         * @event aftersave
         * 在Form保存数据后触发
         * 
         * @param {BasicForm} form
         * @param {Action} action
         */
        'aftersave',
        /**
         * @event beforeremove
         * 在删除Grid中某行记录前触发
         * 
         * @param {Record} record	要删除数据的Record
         * 		   {Object} params	如果需要在调用Action的remove方法时添加额外参数，可以在提供的这个属性上增加参数值，例如：params['classId'] = 1;
         * @return 如果返回false，则停止执行删除操作
         */
        'beforeremove',
        /**
         * @event afterremove
         * 在删除Grid中某行记录后触发
         * 
         * @param {Object} result 删除后后台返回的JSON结果对象
         * @return 如果返回false，则停止执行删除后清除Grid中相应行记录的操作
         */
        'afterremove'
    );
    
	Ext.ActionModel.Grid_Form.superclass.constructor.call(this, options);
};

Ext.extend(Ext.ActionModel.Grid_Form, Ext.ActionModel, {
    /**
    * @cfg {Ext.GridPanel} grid
    * 负责显示数据列表的Grid对象
    */
	grid : null,
    /**
    * @cfg {Ext.FormPanel} form
    * 负责显示数据明细的Form对象
    */
	form : null,
    /**
    * @cfg {Ext.Window} win
    * 存放Form对象的Window对象
    */
	win : null,
	
    /**
    * @cfg {String} gridIdFieldName
    * Grid中ID字段名，默认为'id'
    */
	gridIdFieldName : 'id',
    /**
    * @cfg {String} formLoadIdFieldName
    * Form装载时的检索字段名，默认为'id'
    */
	formLoadIdFieldName : 'id',
    /**
    * @cfg {String} getUrl
    * 装载Form的URL链接
    */
	getUrl : '',
    /**
    * @cfg {String} removeUrl
    * 删除数据记录的URL链接
    */
	removeUrl : '',
    /**
    * @cfg {String} saveUrl
    * 保存数据记录的URL链接
    */
	saveUrl : '',
	
// ---------------- public function ----------------
	initialize : function(grid, form, win) {
		this.grid = grid;
		this.form = form;
		this.win = win;
		
		if (this.form)
			this.form.getForm().on('actioncomplete', this.form_onActioncomplete, this);
	},
	
	handleLoadFormDataFromGrid : function(config) {
		//Ext.apply(this, config);
		
		if (this.grid) { 
			if (config && config.handleClickEvent == true)
				this.grid.on('rowclick', this.grid_onRowdblclick, this);
			else
				this.grid.on('rowdblclick', this.grid_onRowdblclick, this);
		}
	},
	
	handleNewFormData : function(config) {
		//Ext.apply(this, config);
		
		this.showWin();
		this.form.getForm().reset();
		
		var e = {form: this.form, params: {}};
		if (this.fireEvent("beforeload", true, e) == false) {
			this.hideWin();
			return;
		}
		
		var params = e.params;
		
		this.form.load({
			url: this.getUrl, 
			params:params, 
			method:'POST',
			waitTitle:'\u8BF7\u7B49\u5F85...',
			waitMsg: '\u7cfb\u7edf\u6b63\u5728\u52a0\u8f7d\u6570\u636e\uff0c\u8bf7\u7a0d\u5019...'
		});
	},
	
	handleSaveFormData : function(config) {
		//Ext.apply(this, config);
		
		if (this.form && this.form.getForm().isValid() ) {
			var params = {};
			if (this.fireEvent("beforesave", this.form, params) == false)
				return;
			
			this.form.getForm().submit({
				url:this.saveUrl, 
				params:params, 
				waitTitle:'\u8BF7\u7B49\u5F85...',
				waitMsg:'\u7cfb\u7edf\u6b63\u5728\u4fdd\u5b58\u6570\u636e\uff0c\u8bf7\u7b49\u5f85...', 
				success: (function(form, action) {
					form.reset();
					this.hideWin();
					
					this.fireEvent("aftersave", form, action);
				}), 
				failure: function(form, action) {
					Ext.MessageBox.alert('\u63d0\u793a\u4fe1\u606f', action.result.error);
				},
				scope: this
			});
		}
	},
	
	handleRemoveGridSelectedData : function(config) {
		//Ext.apply(this, config);
		
		if (this.grid) {
			if (this.grid.getSelectionModel().getSelections().length == 0) {
				Ext.MessageBox.alert('\u63D0\u793A\u4FE1\u606F', '\u6ca1\u6709\u8981\u5220\u9664\u7684\u6570\u636e\uff0c\u8bf7\u5148\u9009\u62e9\u5220\u9664\u6570\u636e\uff0c\u7136\u540e\u8fdb\u884c\u5220\u9664\uff01');
				return;
			}
			
			Ext.MessageBox.confirm('\u63d0\u793a\u4fe1\u606f', '\u60a8\u786e\u8ba4\u8981\u5220\u9664\u5417\uff1f&nbsp;&nbsp;&nbsp;&nbsp;', function(btn) {if(btn == 'yes') {
				 var selectionModel = this.grid.getSelectionModel();
				 var store = this.grid.getStore();
				 var rows = selectionModel.getSelections();
				 
				 for (var i = 0; i < rows.length; i++) {
					var record = store.getById(rows[i].id);
					var params = {};
					if (this.fireEvent("beforeremove", record, params) == false)
						continue;
				 	
					params[this.gridIdFieldName] = record.get(this.gridIdFieldName);
					
					Ext.Ajax.request({method:'POST',
						url: this.removeUrl, 
						params: params,
						success: (function(response) {
							var result = Ext.decode(response.responseText);
							if (result.success) {
								if (this.fireEvent("afterremove", result) == true) {
									if (result && result[this.gridIdFieldName]) {
										var store = this.grid.getStore();
										var index = store.find(this.gridIdFieldName, result[this.gridIdFieldName]);
										
										store.remove(store.getAt(index));
									}
								}
							} else
								Ext.MessageBox.alert('\u63d0\u793a\u4fe1\u606f', result.error);
						}),   
						scope: this
					}); 
				 }
			}}, this);
		}
	},
	
// ---------------- event handle ----------------
	grid_onRowdblclick : function(grid, rowIndex, e) {
		var record = this.grid.getStore().getAt(rowIndex);
		if (record) {
			this.showWin();
			
			var e = {form: this.form, record: record, params: {}};
			if (this.fireEvent("beforeload", false, e) == false){
				this.hideWin();
				return;
			}
			
			var id = record.get(this.gridIdFieldName);
			var params = e.params;
			params[this.formLoadIdFieldName] = id;
			
			this.form.load({
				url: this.getUrl, 
				method:'POST', 
				params:params, 
				waitTitle:'\u8BF7\u7B49\u5F85...',
				waitMsg: '\u7cfb\u7edf\u6b63\u5728\u52a0\u8f7d\u6570\u636e\uff0c\u8bf7\u7a0d\u5019...'
			});
		}
	},
	
	form_onActioncomplete : function(form, action) {
		if ('load' == action.type) {
			this.fireEvent("afterload", this.form);
		} else if ('submit' == action.type) {
			
		}
	},
	
// ---------------- private function ----------------
	showWin : function() { 
		if (this.win != null) {
			if(!this.win.events){
	            this.win = new Ext.Window(this.win);
	        }
	        
			this.win.show();
		}
	},
	hideWin : function() {
		if (this.win != null) {
			if(!this.win.events){
	            this.win = new Ext.Window(this.win);
	        } else {
				this.win.hide();
	        }
		}
	}
});