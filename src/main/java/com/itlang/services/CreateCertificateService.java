package com.itlang.services;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Service
public class CreateCertificateService {

    public byte[] createCertificate(String name, String course) {
        String template = "src/main/resources/certificate/CertificateTemplate.pdf";
        try (InputStream templateStream = Files.newInputStream(Path.of(template));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfReader reader = new PdfReader(templateStream);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(reader, writer);

            PdfPage pdfPage = pdfDocument.getFirstPage();

            String fontFile = "src/main/resources/fonts/Bitter-BoldItalic.ttf";
            PdfFont boldItalic = PdfFontFactory.createFont(fontFile, PdfEncodings.IDENTITY_H, true);

            String fontFile1 = "src/main/resources/fonts/Bitter-Medium.ttf";
            PdfFont medium = PdfFontFactory.createFont(fontFile1, PdfEncodings.IDENTITY_H, true);

            String fontFile2 = "src/main/resources/fonts/Bitter-SemiBoldItalic.ttf";
            PdfFont semiBoldItalic = PdfFontFactory.createFont(fontFile2, PdfEncodings.IDENTITY_H, true);

            PdfCanvas canvas = new PdfCanvas(pdfPage);

            float pageWidth = pdfPage.getPageSize().getWidth();
            float textWidth = semiBoldItalic.getWidth(name, 100);
            float startX = (pageWidth - textWidth) / 2;

            DeviceRgb color = new DeviceRgb(48, 44, 45);
            DeviceRgb color2 = new DeviceRgb(92, 89, 90);

            canvas.beginText();
            canvas.setFontAndSize(semiBoldItalic, 100);
            canvas.moveText(startX, 517);
            canvas.setFillColor(color);
            canvas.showText(name);
            canvas.endText();

            textWidth = boldItalic.getWidth(course, 64);
            startX = (pageWidth - textWidth) / 2;

            canvas.beginText();
            canvas.setFontAndSize(boldItalic, 64);
            canvas.moveText(startX, 260);
            canvas.setFillColor(color2);
            canvas.showText(course);
            canvas.endText();

            LocalDate localDate = LocalDate.now();
            String date = localDate.getDayOfMonth() + "." + localDate.getMonthValue() + "." + localDate.getYear();

            canvas.beginText();
            canvas.setFontAndSize(medium, 40);
            canvas.moveText(930, 31);
            canvas.setFillColor(color);
            canvas.showText(date);
            canvas.endText();

            pdfDocument.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
