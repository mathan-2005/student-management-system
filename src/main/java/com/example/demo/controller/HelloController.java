package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class HelloController {

    @Autowired
    private StudentRepository repo;

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {

        return repo.save(student);
    }

    @GetMapping("/students")
    public List<Student> getStudents() {

        return repo.findAll();
    }

    @GetMapping("/student/{regNo}")
    public Student getStudentByRegNo(
            @PathVariable String regNo) {
                 System.out.println(regNo);

        return repo.findByRegNo(regNo);
    }

    @PutMapping("/update/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student updatedStudent) {

        Student student =
                repo.findById(id).orElse(null);

        if(student != null){

            student.setName(updatedStudent.getName());
            student.setAge(updatedStudent.getAge());
            student.setGender(updatedStudent.getGender());
            student.setRegNo(updatedStudent.getRegNo());
           student.setPassword(updatedStudent.getPassword());
            return repo.save(student);
        }

        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id){

        repo.deleteById(id);

        return "Student Deleted";
    }
  @PostMapping("/student-login")
public Student studentLogin(@RequestBody Student student){

    List<Student> students = repo.findAll();

    for(Student s : students){

        if(
            s.getRegNo() != null &&
            s.getPassword() != null &&
            s.getRegNo().trim().equals(student.getRegNo().trim()) &&
            s.getPassword().trim().equals(student.getPassword().trim())
        ){

            return s;
        }
    }

    return new Student();
}}