package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class ExportController {

    @Autowired
    private StudentRepository repo;

    // EXPORT EXCEL
    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=students.xlsx"
        );

        List<Student> students = repo.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Students");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Age");
        header.createCell(2).setCellValue("Gender");
        header.createCell(3).setCellValue("Reg No");

        int rowNum = 1;

        for (Student s : students) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(s.getName());
            row.createCell(1).setCellValue(s.getAge());
            row.createCell(2).setCellValue(s.getGender());
            row.createCell(3).setCellValue(s.getRegNo());
        }

        workbook.write(response.getOutputStream());

        workbook.close();
    }

    // IMPORT EXCEL
    @PostMapping("/import/excel")
    public String importExcel(
            @RequestParam("file") MultipartFile file)
            throws Exception {

        InputStream inputStream = file.getInputStream();

        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            Student student = new Student();

            student.setName(
                    row.getCell(0).getStringCellValue()
            );

            student.setAge(
                    (int) row.getCell(1).getNumericCellValue()
            );

            student.setGender(
                    row.getCell(2).getStringCellValue()
            );

            student.setRegNo(
                    row.getCell(3).getStringCellValue()
            );

            repo.save(student);
        }

        workbook.close();

        return "Excel Imported Successfully";
    }
}