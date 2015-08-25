package org.xidea.el;



public interface Reference{
	/**
	 * ���ݴ���ı���������,���ñ��ʽ��Ӧ������ֵ
	 * @see Reference#setValue(Object)
	 * @param context ������
	 * @return
	 */
	public Object setValue(Object value);
	public Object getValue();
	public Reference next(Object key);
	public Class<? extends Object> getType();
	public Object getBase();
	public Object getName();
}