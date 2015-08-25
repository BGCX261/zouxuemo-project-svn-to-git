package org.xidea.el.fn;

import java.lang.reflect.Method;

import org.xidea.el.Invocable;

abstract class JSObject implements Invocable {
	protected Method method;
	// ���ҽ�����ȫ�Լ����Ʋ���ʱ��ֻ��һ���������Ҳ���ΪObject[]��,paramΪ�գ�����Ҫ�Զ�ת��
	Class<?>[] params;

	public Object invoke(Object thiz, Object... args) throws Exception {
		if (params == null) {
			return method.invoke(this, thiz, (Object) args);
		} else {
			Object[] args2 = new Object[params.length];
			for (int i = args2.length - 1; i > 0; i--) {
				args2[i] = ECMA262Impl.ToValue(args.length >= i ? args[i - 1]
						: null, params[i]);
			}
			args2[0] = thiz;
			return method.invoke(this, args2);
		}
	}

	

	public String toString() {
		return method.getName();
	}

	static Object getArg(Object[] args, int index, Object defaultValue) {
		if (index >= 0 && index < args.length) {
			return args[index];
		} else {
			return defaultValue;
		}
	}

	static int getIntArg(Object[] args, int index, Integer defaultValue) {
		Number value = getNumberArg(args, index, defaultValue);
		return value.intValue();
	}

	static Number getNumberArg(Object[] args, int index, Number defaultValue) {
		Object value = getArg(args, index, defaultValue);
		return ECMA262Impl.ToNumber(value);
	}

	static String getStringArg(Object[] args, int index, String defaultValue) {
		Object value = getArg(args, index, null);
		if (value == null) {
			return null;
		}
		return ECMA262Impl.ToString(value);
	}
}