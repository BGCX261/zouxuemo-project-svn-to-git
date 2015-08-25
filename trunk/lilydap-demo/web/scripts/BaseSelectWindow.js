
BaseSelectWindow = function(config) {
    Ext.apply(this, config);
    
	BaseSelectWindow.superclass.constructor.call(this, {
		modal: true,
		layout:'fit',
		autoScroll : true,
		closeAction:'close',
		plain: true,
		buttonAlign:'right',
		listeners : {
			'show' : this.onWindowShow.createDelegate(this)
		}
	});
};
 
Ext.extend(BaseSelectWindow, Ext.Window, {
    /**
    * @cfg {String} basePath
    * ��store��loader�е�url���ӵĻ���·��.
    */
	basePath: '',
    /**
    * @cfg {String} title
    * ѡ�����⣬Ĭ��"����ѡ��".
    */
	title : '\u6570\u636e\u9009\u62e9',
    /**
     * @cfg {Number} width 
     * ���ڿ��
     */
	width: 600,
    /**
     * @cfg {Number} height 
     * ���ڸ߶�
     */
	height: 400,
    /**
     * @cfg {Number} conditionFormHeight 
     * ��ѯ����Form�߶ȣ�Ĭ�Ͽ��Է���һ�в�ѯ����
     */
	conditionFormHeight: 39,
    /**
    * @cfg {String} searchUrl
    * ������URL���� 
    */
	searchUrl: '',
    /**
    * @cfg {Object} baseParams
    * �ύ����ʱ������Щ������Ϊ��ѯ����
    */
	baseParams: {},
    /**
    * @cfg {Boolean} isSingleSelect
    * �Ƿ�ѡ��true��ֻ��ѡ��һ����false������ѡ����.
    */
	isSingleSelect : false,
    /**
    * @cfg {Boolean} isSearchAfterOpen
    * �Ƿ�򿪴��ں��������������������conditionsһ���ã�����ṩ�˲�ѯ���������Ƿ�򿪴��ں������������ݣ������������ѯ������ż�������
    */
	isSearchAfterOpen: false,
    /**
    * @cfg {Boolean} isCloseAfterSelected
    * �Ƿ���ѡ���رմ��ڣ����true����ѡ�������ݲ����"ȷ��"��رմ��ڣ�����ֻ���ڵ��"ȡ��"��Źرմ���
    */
	isCloseAfterSelected: true,
    /**
    * @cfg {Boolean} isClearConditionAfterOpen
    * �Ƿ���ÿ�δ򿪴���ʱ������ϴβ�ѯ��������
    */
	isClearConditionAfterOpen: true,
    /**
    * @cfg {array} conditions
    * ��ѯ������Ԫ�����ã���form�е�items������ͬ������������˲�ѯ�������ã�����ʾ��ѯ������
    */
	conditions: null,
    /**
     * @cfg {Number} queryDelay 
     * �ı���������������ѯ֮�����ʱʱ��
     */
	queryDelay: 500,
    /**
    * @cfg {String} totalProperty
    * store��totalProperty����ֵ
    */
	totalProperty: 'totalCount', 
    /**
    * @cfg {String} root
    * store��root����ֵ
    */
	root: 'data', 
    /**
    * @cfg {String} successProperty
    * store��successProperty����ֵ
    */
	successProperty :'success',
    /**
    * @cfg {Number} pageSize
    * ��ҳʱÿҳ�ߴ磬�������Ҫ��ҳ��������Ϊ-1
    */
	pageSize: 12, 
    /**
    * @cfg {array} fields
    * store��fields����ֵ
    */
	fields: null,
    /**
    * @cfg {array} columns
    * grid��ColumnModel�Ĳ���ֵ��ֻ������Ҫ��ʾ���ֶ��У���Ҫ����PagedRowNumberer��CheckboxSelectionModel��
    */
	columns: null, 
    /**
    * @cfg {String} autoExpandColumn
    * grid���Զ���չ����
    */
    autoExpandColumn : null,
    /**
    * @cfg {String} selectFieldName
    * ѡ�к󷵻����ݵ��ֶ���
    */
	selectFieldName: 'id',
    /**
    * @cfg {String} displayFieldName
    * ѡ�к���ʾ���ݵ��ֶ���
    */
	displayFieldName: 'name',
	
//---------------------------------	����Ϊ˽�����ԣ�������ֱ������ ---------------------------------
    /**
    * @cfg {array} initValues
    * ��ʼѡ��ֵ���ַ������飬��ִ��show����ʱ����
    */
	initValues: [], 
	
    /**
    * @cfg {Ext.FormPanel} conditionForm
    * ��ѯ����Form����Ϊ��ʾ��ѯ�����Ĵ���
    */
	conditionForm: null,

    /**
    * @cfg {Object} initQueryParams
    * ��ѯ����������ʼֵ���ڵ���show����ʱ���룬��Ϊ��ʼ�Ĳ�ѯ����ֵ
    */
	initQueryParams: {},
	
    /**
    * @cfg {String} conditionValues
    * �����ǰ��ѯ�������ڣ������ڲ�ѯ�����������¼�������Ƚϲ�ѯ�����Ƿ�ı䣬�ı���ִ�в�ѯ
    */
	conditionValues: '',
	
    /**
    * @cfg {Ext.util.DelayedTask} queryTask
    * ��ѯ��ʱ����
    */
	queryTask: null,
	
    /**
    * @cfg {Ext.grid.GridPanel} grid
    * ����������Grid
    */
	grid: null,

    /**
    * @cfg {Ext.PagingToolbar} pageingToolbar
    * ��ҳ������
    */
	pageingToolbar: null,
	
//---------------------------------	����Ϊ���ط��� ---------------------------------
	show : function(initQueryParams, initValues){
		if (initQueryParams) 
			Ext.apply(this.initQueryParams, initQueryParams, {});

		if (initValues)
			if (typeof initValues == 'string')
				this.initValues = initValues.split(',');
			else
				this.initValues = initValues;
		else
			this.initValues = [];
			
		BaseSelectWindow.superclass.show.call(this);
	},
	
	initComponent : function(){
        this.addEvents(
            /**
             * @event selectdata 
             * ��ѡ���������б����ȷ���󣬱���ÿ��ѡ������ݣ����������¼�.
             * @param {Number} index ѡ����˳���
             * @param {Record} record ѡ���м�¼
             */
            'selectdata'
        );
        this.addEvents( 
            /**
             * @event ok
             * ��ѡ���������б����ȷ������ʱ����.
             * @param {String} selectValue ѡ�������ֵ�ַ�������","�ָ�
             * @param {String} selectDisplayName ѡ�����ʾ�����ַ�������","�ָ�
             */
            'ok'
        );
        
        //��������˲�ѯ����items���򴴽���ѯ����FormPanel����������ѯ��ʱ����ʵ�ֵ��û��ڲ�ѯ�����������ַ�ʱ���Զ�ִ����ʱ��ѯ����
        if (this.conditions) {
        	this.conditionForm = new Ext.FormPanel({
				labelAlign: 'right',
				labelWidth: 55,
				autoHeight:true,
				frame:true,
				bodyStyle:'padding:8px 5px 0',
				method:'POST',
	            items: this.conditions
            });
            
			this.queryTask = new Ext.util.DelayedTask(this.queryByCondition, this);            
        } 
        
        //���������б��store����Դ
		var ds = new Ext.data.JsonStore({
			url: this.basePath + this.searchUrl,
			root: this.root, 
			totalProperty: this.totalProperty, 
			successProperty: this.successProperty,
			remoteSort: true,
			fields: this.fields,
			listeners : {
				'load' : this.onGridLoad.createDelegate(this)
			}
		});
    	 
		//�����ǵ�ѡ���Ƕ�ѡ����Ӧ������ѡ������CheckBoxѡ����
		var sm;
		if (this.isSingleSelect)
			sm = new Ext.grid.RowSelectionModel({singleSelect: true});
		else {
			sm = new Ext.grid.CheckboxSelectionModel();
			
			//��Grid��Column�����У�����CheckBox������һ��
			var length = this.columns.length;
			for (var index = length; index > 0; index--)
				this.columns[index] = this.columns[index - 1];
				
			this.columns[0] = sm;
		}
			
		//����Grid���й�����
		var cm = new Ext.grid.ColumnModel(this.columns); 
		
		var gridConfig = {
			store: ds,
			sm: sm,
			cm: cm
		};

		if (this.pageSize > 0) {
			this.pageingToolbar = new Ext.PagingToolbar({
				pageSize: this.pageSize,
				store: ds,
				displayInfo: true,
				displayMsg: '\u663E\u793A\u7B2C {0} \u6761\u5230 {1} \u8BB0\u5F55\uFF0C\u5171 {2} \u6761',
				emptyMsg: '\u672A\u627E\u5230\u6570\u636E\u8BB0\u5F55'
			});

			gridConfig['bbar'] = this.pageingToolbar;
		}
		
		//����Զ���չ������
		if (this.autoExpandColumn != null)
			gridConfig['autoExpandColumn'] = this.autoExpandColumn;
		
		//����GridPanel�����˫���¼�����������Ϊ��ʵ���ڿͻ�˫��������ĳ��ʱ���ɷ��ظ��е�ѡ�����ݣ�
		this.grid = new Ext.grid.GridPanel(gridConfig);
		this.grid.on('rowdblclick', this.onGridRowdblclick, this);
        
		//���ָ���˲�ѯ�������򴴽�BorderLayout������Ϊ��ѯ����FormPanel���ײ�Ϊ�����б�Grid
		//����ֱ����ʾ�����б�Grid
		if (this.conditionForm)
			this.items = [{
				layout:"border", border:false, items:[{
					region:"north", height:this.conditionFormHeight, border:false, layout: 'fit', items: [this.conditionForm]
				},{
					region:"center", border:false, layout:"fit", items:[this.grid]
				}]
			}];
		else
			this.items = this.grid;
			
        BaseSelectWindow.superclass.initComponent.call(this);
    },
    
//---------------------------------	����Ϊ�¼������� ---------------------------------
	onRender : function(ct, position){
		//���"ѡ��"��"�ر�"��ť
		this.addButton({
			text:'\u9009\u62e9',
            handler: this.onOk.createDelegate(this)
        });
		this.addButton({
			text:'\u5173\u95ed',
            handler: this.onCancle.createDelegate(this)
        });
        
        BaseSelectWindow.superclass.onRender.call(this, ct, position);
	},
	
	onWindowShow : function(window) {
		this.center();

		//��������˲�ѯ��������Բ�ѯ�����е��ֶ�����¼�����������ʵ���ڲ�ѯ�����ı�ʱ�Զ�ִ�в�ѯ����
		//isHaveListenKeyUpEvent����֤�¼���������ֻ���ڵ�һ����ʾ��ʱ������һ��
        if (this.conditionForm && !this.isHaveListenKeyUpEvent) {
        	this.conditionForm.getForm().items.each(function(field){
        		var xtype = field.getXType();
				if (xtype == 'hidden') 
					return;
							
				var el = field.getEl();
				
				//TODO propertychange,cut,paste�¼���FF�в������ã����������FF��Ҳ��ʵ���ڼ��С�ճ�����Լ�DateField���ݸı�ʱҲ�ܴ�����ѯ
        		if (xtype == 'combo') {
        			field.on('select', this.onComboSelect, this);
        		} else if (xtype == 'datefield' || xtype == 'timefield' || xtype == 'trigger') {
        			el.on('propertychange', this.onFieldPropertyChange, this);
        		}

        		el.on('keyup', this.onFieldKeyUp, this);
        		
				//�����к�ճ��ʱҲ������ѯ����
				el.on('cut', this.onFieldCut, this);
				el.on('paste', this.onFieldPaste, this);
        		
				field.on('change', this.onFieldChange, this);
				
        		if (!this.firstConditionField)
        			this.firstConditionField = field;
	        }, this);
	        
	        this.isHaveListenKeyUpEvent = true;
        }
        
        if (this.conditionForm) {
			var basicForm = this.conditionForm.getForm();

			if (this.isClearConditionAfterOpen)
	        	basicForm.reset();

			for (paramName in this.initQueryParams) {
				var field = basicForm.findField(paramName);
				if (field != null)
					field.setValue(this.initQueryParams[paramName]);
			}
        }

		//�������Ϊ��ʱ������������ò�ѯ������������
		if (this.isSearchAfterOpen)
			this.queryByCondition(); 
        
        //�õ�һ����ѯ�����ֶλ�ý���
        if (this.firstConditionField)
        	this.firstConditionField.focus.defer(600, this.firstConditionField);
	},
	
	onGridRowdblclick : function(grid, rowIndex, e) {
		//��ֻ֤ѡ�����˫�������У�������ȡ��ѡ��
		var selectionModule = this.grid.getSelectionModel();
		selectionModule.selectRow(rowIndex, false);

		this.onOk();
	},
	
	onGridLoad : function() {
	},
	
	onComboSelect : function(field, newValue, oldValue) {
		this.queryByCondition();
	},
	
	onFieldChange : function(field, newValue, oldValue) {
		this.queryByCondition();
	},
	
	onFieldKeyUp : function(e) {
		//��ѯ�����ֶΰ����ſ�ʱ��ִ�в�ѯ��ʱ����
		//if (!e.isSpecialKey())
			this.queryTask.delay(this.queryDelay);
	},
	
	onFieldPropertyChange : function(e) {
		this.queryByCondition();
	},
	
	onFieldCut : function(e) {
		this.queryTask.delay(this.queryDelay);
	},
	
	onFieldPaste : function(e) {
		this.queryTask.delay(this.queryDelay);
	},
	
	onOk : function() {
		var selectValue = '', selectDisplayName = '';
		
		//������������Ƿ���ѡ���У�û������ʾ������
		var records = this.grid.getSelectionModel().getSelections();
		if (records.length == 0) {
			Ext.MessageBox.alert('\u63d0\u793a', '\u6ca1\u6709\u9009\u62e9\u7684\u6570\u636e\uff0c\u8bf7\u5148\u9009\u62e9\u8981\u8fd4\u56de\u7684\u6570\u636e\uff01');
			return;
		}

		//����ѡ�������ַ�������ʾ�ַ���
		
		var first = true;
		for(var index = 0; index < records.length; index++){
			var record = records[index];
			this.fireEvent("selectdata", index, record);
			
			if (first)
				first = false;
			else {
				selectValue += ',';
				selectDisplayName += ',';
			}
				
			selectValue += record.get(this.selectFieldName);
			selectDisplayName += record.get(this.displayFieldName);
		}
		
		if (this.isCloseAfterSelected)
			this.hide();
			
		this.fireEvent("ok", selectValue, selectDisplayName);
	},
	
	onCancle : function(){ 
		this.hide();
	},
	
//---------------------------------	����Ϊ����̳з��� ---------------------------------
	processQueryParam : function(params) {
		
	},
	
//---------------------------------	����Ϊ˽�з��� ---------------------------------
	queryByCondition : function() {
		var values = this.conditionForm.getForm().getValues(true);
		if (this.conditionValues == values)
			return;
			
		this.conditionValues = values;
		
		Ext.apply(this.grid.getStore().baseParams, this.conditionForm.getForm().getValues());
		this.doQuery();
	}, 
	
	doQuery : function() {
		var store = this.grid.getStore();
		this.processQueryParam(store.baseParams);

		if (this.pageingToolbar != null)
			this.pageingToolbar.doLoad(0);
		else
			store.load({params:{start:0}}); 
	}
});

