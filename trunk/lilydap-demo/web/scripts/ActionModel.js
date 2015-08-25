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
         * ��Formװ������ǰ����
         * 
         * @param {Boolean} newFlag			��ǰ�Ƿ����½�
         * @param {object} e					�¼����ݶ��� 
         * 				{FormPanel} e.form		Ҫװ�����ݵ�Form
         * 				{Reocrd} e.record		����Ǵ�Grid��ת�����ݣ�����װ�����ݵ�������¼
         * 				{Object} e.params		�����Ҫ�ڵ���Action��get����ʱ��Ӷ���������������ṩ��������������Ӳ���ֵ�����磺e.params['classId'] = 1;
         * @return �������false����ִֹͣ��װ�ز��������رմ���
         */
        'beforeload',
        /**
         * @event afterload
         * ��Formװ�����ݺ󴥷�
         * 
         * @param {FormPanel} form
         */
        'afterload',
        /**
         * @event beforesave
         * ��Form��������ǰ����
         * 
         * @param {FormPanel} form	Ҫ�������ݵ�Form
         * 		   {Object} params	�����Ҫ�ڵ���Action��save����ʱ��Ӷ���������������ṩ��������������Ӳ���ֵ�����磺params['classId'] = 1;
         * @return �������false����ִֹͣ�б������
         */
        'beforesave',
        /**
         * @event aftersave
         * ��Form�������ݺ󴥷�
         * 
         * @param {BasicForm} form
         * @param {Action} action
         */
        'aftersave',
        /**
         * @event beforeremove
         * ��ɾ��Grid��ĳ�м�¼ǰ����
         * 
         * @param {Record} record	Ҫɾ�����ݵ�Record
         * 		   {Object} params	�����Ҫ�ڵ���Action��remove����ʱ��Ӷ���������������ṩ��������������Ӳ���ֵ�����磺params['classId'] = 1;
         * @return �������false����ִֹͣ��ɾ������
         */
        'beforeremove',
        /**
         * @event afterremove
         * ��ɾ��Grid��ĳ�м�¼�󴥷�
         * 
         * @param {Object} result ɾ�����̨���ص�JSON�������
         * @return �������false����ִֹͣ��ɾ�������Grid����Ӧ�м�¼�Ĳ���
         */
        'afterremove'
    );
    
	Ext.ActionModel.Grid_Form.superclass.constructor.call(this, options);
};

Ext.extend(Ext.ActionModel.Grid_Form, Ext.ActionModel, {
    /**
    * @cfg {Ext.GridPanel} grid
    * ������ʾ�����б��Grid����
    */
	grid : null,
    /**
    * @cfg {Ext.FormPanel} form
    * ������ʾ������ϸ��Form����
    */
	form : null,
    /**
    * @cfg {Ext.Window} win
    * ���Form�����Window����
    */
	win : null,
	
    /**
    * @cfg {String} gridIdFieldName
    * Grid��ID�ֶ�����Ĭ��Ϊ'id'
    */
	gridIdFieldName : 'id',
    /**
    * @cfg {String} formLoadIdFieldName
    * Formװ��ʱ�ļ����ֶ�����Ĭ��Ϊ'id'
    */
	formLoadIdFieldName : 'id',
    /**
    * @cfg {String} getUrl
    * װ��Form��URL����
    */
	getUrl : '',
    /**
    * @cfg {String} removeUrl
    * ɾ�����ݼ�¼��URL����
    */
	removeUrl : '',
    /**
    * @cfg {String} saveUrl
    * �������ݼ�¼��URL����
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