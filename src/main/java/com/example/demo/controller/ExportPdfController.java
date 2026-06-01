package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportPdfController {

    @Autowired
    private StudentRepository repo;

    @GetMapping("/export/pdf/{regNo}")
    public void exportPdf(
            @PathVariable String regNo,
            HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=marksheet.pdf");

        Student student = repo.findByRegNo(regNo);

        Document document = new Document();

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(new Paragraph("Student Marksheet"));

        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(2);

        table.addCell("Field");
        table.addCell("Value");

        table.addCell("ID");
        table.addCell(String.valueOf(student.getId()));

        table.addCell("Name");
        table.addCell(student.getName());

        table.addCell("Register No");
        table.addCell(student.getRegNo());

        table.addCell("Age");
        table.addCell(String.valueOf(student.getAge()));

        table.addCell("Gender");
        table.addCell(student.getGender());

        table.addCell("Tamil");
        table.addCell(String.valueOf(student.getTamil()));

        table.addCell("English");
        table.addCell(String.valueOf(student.getEnglish()));

        table.addCell("Maths");
        table.addCell(String.valueOf(student.getMaths()));

        table.addCell("Science");
        table.addCell(String.valueOf(student.getScience()));

        table.addCell("Social");
        table.addCell(String.valueOf(student.getSocial()));

        document.add(table);

        document.close();
    }
}