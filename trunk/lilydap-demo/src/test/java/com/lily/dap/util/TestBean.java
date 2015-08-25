package com.lily.dap.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;

@Ignore
public class TestBean {
	private String field1 = "";

	private int field2 = 0;

	private double field3 = 0.0;

	private Date field4 = new Date();

	public TestBean() {
		
	}
	
	public TestBean(String field1, int field2, double field3, Date field4) {
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
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

	@Override
	public String toString() {
		return "TestBean [field1=" + field1 + ", field2=" + field2
				+ ", field3=" + field3 + ", field4=" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(field4) + "]";
	}
}
