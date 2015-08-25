package org.xidea.lite;


import java.io.Writer;

import org.xidea.el.ValueStack;


/**
 * ������Կ��Ա��Զ���ʼ����
 * ���Կ�����String ���ͣ�Boolean ���ͣ�Number���ͣ���ûȷ�����ת������Java �������ͣ�Map,Collection,List(��֧��Set)��
 * @author jindw
 */
public interface Plugin {
	public void initialize(Template template,Object[] children);
	public void execute(ValueStack context,Writer out);
}
