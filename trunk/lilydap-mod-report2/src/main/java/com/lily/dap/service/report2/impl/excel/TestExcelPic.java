package com.lily.dap.service.report2.impl.excel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TestExcelPic {
	public static void main(String[] args) {
		FileOutputStream fileOut = null;
		BufferedImage bufferImg = null;
		BufferedImage bufferImg1 = null;
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
			bufferImg = ImageIO.read(new File("C:/Documents and Settings/Administrator/My Documents/My Pictures/动物世界弱肉强食/20094911443_03.jpg"));
			bufferImg1 = ImageIO.read(new File("C:/Documents and Settings/Administrator/My Documents/My Pictures/动物世界弱肉强食/20094911443_04.jpg"));
			ImageIO.write(bufferImg, "jpeg", byteArrayOut);
			ImageIO.write(bufferImg1, "jpeg", byteArrayOut1);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("new sheet");
			// HSSFRow row = sheet1.createRow(2);
			HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
			// 用两个图片来对比
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
					(short) 0, 0, (short) 2, 4);// 控制图片的左上角,右下角的位置
			HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 512, 255,
					(short) 0, 15, (short) 10, 20);
			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut
					.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
			patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut1
					.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

			fileOut = new FileOutputStream("d:/workbook.xls");
			wb.write(fileOut);
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("io erorr :  " + io.getMessage());
		} finally {
			if (fileOut != null)
				try {
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
