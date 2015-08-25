package com.lily.dap.util.jackson;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.junit.Ignore;

@Ignore
public class TestBean {
	private String field1 = "";

	private int field2 = 0;

	private double field3 = 0.0;

	private Date field4 = new Date();
	
	private boolean field5 = false;

	public TestBean() {
		
	}
	
	public TestBean(String field1, int field2, double field3, Date field4, boolean field5) {
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public int getField2() {
		return field2;
	}

	public void setField2(int field2) {
		this.field2 = field2;
	}

	public double getField3() {
		return field3;
	}

	public void setField3(double field3) {
		this.field3 = field3;
	}

	public Date getField4() {
		return field4;
	}

	public void setField4(Date field4) {
		this.field4 = field4;
	}

	@JsonIgnore
	public boolean isField5() {
		return field5;
	}

	public void setField5(boolean field5) {
		this.field5 = field5;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TestBean))
			return false;
		
		TestBean bean = (TestBean)obj;
		return field1.equals(bean.getField1()) && field2 == bean.getField2() && field3 == bean.getField3() && field4.equals(bean.getField4()) && field5 == bean.isField5();
	}

	@Override
	public String toString() {
		return "TestBean [field1=" + field1 + ", field2=" + field2
				+ ", field3=" + field3 + ", field4=" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(field4)+ ", field5=" + field5 + "]";
	}
}
