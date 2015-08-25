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
		   /* 孙成才增加简单全字匹配功能	   
			* 判断如果要查找的数据存在,建立死循环的while,取出第一个数据的value值与传过来的校验,如果相同则返回其应的displayField,并结束while.
			* 如果不相同则采用下标+1后继续向后查找,直到找到为止.肯定可以找到,因为如果指定数据不存在,则不会通过最外层的if.
			* 本方法还有很多缺陷,建议谨慎使用.或者日后有好方法时再改进.
			*/
			
			if(index!=-1){	
				return this.store.getAt(index).get(this.displayField);
			}else{
				return v;
			}			
			
	    }
};
