package com.lily.dap.service.report2.impl.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class RowSpanExample {

	public static void main(String[] args) throws DocumentException {
		System.out.println("Table in cells");
		// step1
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		try {
			// step2
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("c:\\TableInCell.pdf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// step3
//		document.open();
//		PdfPTable pt = new PdfPTable(2);
//		PdfPTable nested1 = new PdfPTable(1);
//		PdfPTable nested2 = new PdfPTable(3);
//		PdfPCell cellnest11 = new PdfPCell(new Paragraph("cell1"));
//		PdfPCell cellnest21 = new PdfPCell(new Paragraph("cell2"));
//		PdfPCell cellnest22 = new PdfPCell(new Paragraph("cel13"));
//		PdfPCell cellnest23 = new PdfPCell(new Paragraph("cel14"));
//		nested1.addCell(cellnest11);
//		nested2.addCell(cellnest21);
//		nested2.addCell(cellnest22);
//		nested2.addCell(cellnest23);
//		nested2.addCell(cellnest21);
//		nested2.addCell(cellnest22);
//		nested2.addCell(cellnest23);
//		pt.addCell(nested1);
//		pt.addCell(nested2);
//		document.add(pt);
//		document.close();

		document.open();
		PdfPTable pt = new PdfPTable(4);
		PdfPTable nested1 = new PdfPTable(1);
		PdfPCell cellnest11 = new PdfPCell(new Paragraph("cell1"));
		PdfPCell cellnest21 = new PdfPCell(new Paragraph("cell2"));
		PdfPCell cellnest22 = new PdfPCell(new Paragraph("cel13"));
		PdfPCell cellnest23 = new PdfPCell(new Paragraph("cel14"));
		nested1.addCell(cellnest11);
		pt.addCell(nested1);
		pt.addCell(cellnest21);
		pt.addCell(cellnest22);
		pt.addCell(cellnest23);
		pt.addCell(cellnest21);
		pt.addCell(cellnest22);
		pt.addCell(cellnest23);
		document.add(pt);
		document.close();
	}
}
