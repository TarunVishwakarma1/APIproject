package com.tv.apiproj.customs;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.tv.apiproj.dao.PersonDataDAO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CustomMethods {

    public static ResponseEntity<String> generateAllPDF(HttpServletResponse response, List<PersonDataDAO> list) {
        addDataToPDF(response,list);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static ResponseEntity<String> generatePerPersonPDF(HttpServletResponse response,List<PersonDataDAO> list) {
        addDataToPDF(response,list);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static void addDataToPDF(HttpServletResponse response, List<PersonDataDAO> data){
        String pdfName = "PersonData "+ LocalDate.now();
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename="+pdfName+".pdf");

            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Text text = new Text("Demo Text to see the Person Data");
            document.add(new Paragraph(text.setBold().setItalic().setUnderline()));

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1}));

            table.setWidth(UnitValue.createPercentValue(100));
            table.setTextAlignment(TextAlignment.CENTER);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            Cell header1 = new Cell().add(new Paragraph("ID"));
            header1.setBackgroundColor(new DeviceRgb(135, 206, 250));
            table.addHeaderCell(header1);

            Cell header2 = new Cell().add(new Paragraph("Name"));
            header2.setBackgroundColor(new DeviceRgb(135, 206, 235));
            table.addHeaderCell(header2);

            Cell header3 = new Cell().add(new Paragraph("Age"));
            header3.setBackgroundColor(new DeviceRgb(0, 191, 255));
            table.addHeaderCell(header3);

            for (PersonDataDAO datum : data) {
                Cell cell1 = new Cell().add(new Paragraph(String.valueOf(datum.getId())));
                cell1.setBackgroundColor(new DeviceGray(0.8f));
                table.addCell(cell1);

                Cell cell2 = new Cell().add(new Paragraph(String.valueOf(datum.getFullName())));
                cell2.setBackgroundColor(new DeviceGray(0.9f));
                table.addCell(cell2);

                Cell cell3 = new Cell().add(new Paragraph(String.valueOf(datum.getAge())));
                cell3.setBackgroundColor(new DeviceGray(0.7f));
                table.addCell(cell3);
            }

            document.add(table);
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
