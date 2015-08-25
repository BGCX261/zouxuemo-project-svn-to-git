/*
valueField
displayField
store
mode: 'local'/'remote'
editable: true/false
*/
Ext.grid.DictionaryColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
	this.valueField = this.valueField || 'id';
	this.displayField = this.displayField || 'value';
	this.mode = this.mode || 'local';
	this.editable = this.editable || false;

	if(typeof(this.allowEdit)=='undefined') this.allowEdit = true;	
	
	if (this.editable) {
		this.editor = new Ext.form.ComboBox({
			displayField:this.displayField,
			valueField: this.valueField,
			store: this.store,
			mode: this.mode,
			editable: this.allowEdit,
			typeAhead: true,
			triggerAction: 'all',
			lazyRender:true,
			listClass: 'x-combo-list-small'
		});
	}
};

Ext.grid.DictionaryColumn.prototype ={
	    renderer : function(v, p, record){
			//var index = this.store.find(this.valueField, v);
			var valueField = this.valueField;
			var index = this.store.findBy(function(record) {
				if (record.get(valueField) == v)
					return true;
			});
		   /* ��ɲ����Ӽ�ȫ��ƥ�书��	   
			* �ж����Ҫ���ҵ����ݴ���,������ѭ����while,ȡ����һ�����ݵ�valueֵ�봫������У��,�����ͬ�򷵻���Ӧ��displayField,������while.
			* �������ͬ������±�+1�����������,ֱ���ҵ�Ϊֹ.�϶������ҵ�,��Ϊ���ָ�����ݲ�����,�򲻻�ͨ��������if.
			* ���������кܶ�ȱ��,�������ʹ��.�����պ��к÷���ʱ�ٸĽ�.
			*/
			
			if(index!=-1){	
				return this.store.getAt(index).get(this.displayField);
			}else{
				return v;
			}			
			
	    }
};
